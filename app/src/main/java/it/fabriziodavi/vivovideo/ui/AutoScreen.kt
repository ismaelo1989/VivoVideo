package it.fabriziodavi.vivovideo.ui

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.provider.OpenableColumns
import android.provider.Settings
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import it.fabriziodavi.vivovideo.media.Album
import it.fabriziodavi.vivovideo.media.Gallery
import it.fabriziodavi.vivovideo.media.GalleryItem
import it.fabriziodavi.vivovideo.model.AutoOptions
import it.fabriziodavi.vivovideo.model.AutoStyle
import it.fabriziodavi.vivovideo.model.VideoFormat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/** Durate proposte per il video automatico (0 = tutto quello che c'è negli album). */
private val DURATIONS = listOf(30_000_000L, 60_000_000L, 120_000_000L, 180_000_000L, 0L)
private const val FIT_MUSIC = -1L

/** "Agosto 2026", "Luglio – Agosto 2026", "2024 – 2026". */
fun dateRangeLabel(firstMs: Long, lastMs: Long): String {
    val loc = Locale.ITALIAN
    fun f(p: String, ms: Long) = SimpleDateFormat(p, loc).format(Date(ms)).replaceFirstChar { c -> c.uppercase() }
    val y1 = f("yyyy", firstMs); val y2 = f("yyyy", lastMs)
    val m1 = f("MMMM", firstMs); val m2 = f("MMMM", lastMs)
    return when {
        y1 != y2 -> "$y1 – $y2"
        m1 != m2 -> "$m1 – $m2 $y2"
        else -> "$m2 $y2"
    }
}

/** I temi del video automatico divisi per gruppo (nel passo "Stile"). */
internal val STYLE_GROUPS: List<Pair<String, List<AutoStyle>>> = listOf(
    "Dinámicos" to listOf(AutoStyle.ENERGICO, AutoStyle.VIRALE, AutoStyle.AZIONE, AutoStyle.ESTREMO, AutoStyle.BLOCKBUSTER, AutoStyle.NEON, AutoStyle.ESTATE),
    "Viajes y naturaleza" to listOf(AutoStyle.VIAGGIO, AutoStyle.DIARIO, AutoStyle.MARE, AutoStyle.MONTAGNA, AutoStyle.NATURA),
    "Emociones" to listOf(AutoStyle.CINEMA, AutoStyle.ELEGANTE, AutoStyle.ROMANTICO, AutoStyle.MATRIMONIO, AutoStyle.SOGNO, AutoStyle.RICORDI),
    "Retro" to listOf(AutoStyle.SUPER8, AutoStyle.ANNI90, AutoStyle.GRUNGE),
)

/** Da dove prendere foto e video: scelti uno per uno o album interi. */
private enum class Source(val label: String) { ITEMS("Fotos y videos"), ALBUMS("Álbumes completos") }

private val STEPS = listOf("Qué usar", "Estilo", "Música y detalles")

/**
 * Video automatico in tre passi: 1) foto e video (scelti uno per uno o album interi), 2) stile, 3) musica, durata e titolo.
 * L'app sceglie le scene, le taglia a ritmo e aggiunge transizioni, effetti e titolo.
 */
@Composable
fun AutoScreen(vm: EditorViewModel) {
    val ctx = LocalContext.current
    val scope = rememberCoroutineScope()
    val busy = vm.busy
    var step by remember { mutableIntStateOf(0) }
    BackHandler(enabled = busy == null) { if (step > 0) step-- else vm.showAuto = false }

    var permTick by remember { mutableIntStateOf(0) }
    val permLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permTick++ }
    val full = remember(permTick) { Gallery.fullAccess(ctx) }
    val any = remember(permTick) { Gallery.anyAccess(ctx) }
    LaunchedEffect(Unit) { if (!Gallery.anyAccess(ctx)) permLauncher.launch(Gallery.permissions) }

    val albums by produceState<List<Album>?>(null, permTick) {
        value = if (Gallery.anyAccess(ctx)) withContext(Dispatchers.IO) { runCatching { Gallery.albums(ctx) }.getOrDefault(emptyList()) } else emptyList()
    }
    // Tutta la galleria, dal più recente (per la scelta una per una).
    val gallery by produceState<List<GalleryItem>?>(null, permTick) {
        value = if (Gallery.anyAccess(ctx)) withContext(Dispatchers.IO) { runCatching { Gallery.items(ctx).asReversed() }.getOrDefault(emptyList()) } else emptyList()
    }
    var source by remember { mutableStateOf(Source.ITEMS) }
    var selected by remember { mutableStateOf(setOf<String>()) }
    var picked by remember { mutableStateOf(listOf<String>()) }
    var filterAlbum by remember { mutableStateOf<Album?>(null) }
    var style by remember { mutableStateOf(AutoStyle.CINEMA) }
    var group by remember { mutableStateOf(STYLE_GROUPS.first { AutoStyle.CINEMA in it.second }.first) }
    var duration by remember { mutableStateOf(60_000_000L) }
    var format by remember { mutableStateOf(VideoFormat.AUTO) }
    var smart by remember { mutableStateOf(true) }
    var more by remember { mutableStateOf(false) }
    var musicUri by remember { mutableStateOf<Uri?>(null) }
    var title by remember { mutableStateOf("") }
    var titleTouched by remember { mutableStateOf(false) }
    val musicPicker = rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) { uri ->
        if (uri != null) { musicUri = uri; if (duration == 0L) duration = FIT_MUSIC }
    }
    // Brano scelto nella libreria in rete.
    LaunchedEffect(vm.autoMusicPicked) {
        vm.autoMusicPicked?.let { musicUri = it; vm.autoMusicPicked = null; if (duration == 0L) duration = FIT_MUSIC }
    }
    val musicName = remember(musicUri) {
        musicUri?.let { u ->
            runCatching { ctx.contentResolver.query(u, arrayOf(OpenableColumns.DISPLAY_NAME), null, null, null)?.use { c -> if (c.moveToFirst()) c.getString(0) else null } }
                .getOrNull() ?: "Canción elegida"
        }
    }
    // Elementi degli album scelti (servono per contare e per il periodo del sottotitolo).
    val albumItems by produceState<List<GalleryItem>>(emptyList(), selected, permTick) {
        value = if (selected.isEmpty()) emptyList() else withContext(Dispatchers.IO) { runCatching { Gallery.items(ctx, selected) }.getOrDefault(emptyList()) }
    }
    // Quelli che entreranno nel video, dal più vecchio.
    val chosen: List<GalleryItem> = if (source == Source.ALBUMS) albumItems else {
        val set = picked.toSet()
        gallery.orEmpty().filter { it.uri.toString() in set }.sortedBy { it.dateMs }
    }
    val subtitle = if (chosen.isEmpty()) "" else dateRangeLabel(chosen.first().dateMs, chosen.last().dateMs)
    // Titolo proposto: i nomi degli album (finché non lo si cambia a mano).
    LaunchedEffect(selected, albums, source) {
        if (!titleTouched) title = if (source == Source.ALBUMS) albums.orEmpty().filter { it.id in selected }.joinToString(" · ") { it.name }.take(40) else ""
    }

    Column(Modifier.fillMaxSize().background(VV.Canvas).safeDrawingPadding()) {
        Row(Modifier.fillMaxWidth().height(52.dp).padding(horizontal = 4.dp), verticalAlignment = Alignment.CenterVertically) {
            IconBtn(VVIcons.Back, "Atrás", enabled = busy == null) { if (step > 0) step-- else vm.showAuto = false }
            Text("Video automático", style = MaterialTheme.typography.titleMedium, modifier = Modifier.weight(1f))
        }
        StepBar(step) { if (it < step && busy == null) step = it }
        if (busy != null) {
            Column(Modifier.fillMaxWidth().padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(busy, style = MaterialTheme.typography.bodyMedium)
                LinearProgressIndicator(Modifier.fillMaxWidth().height(4.dp).clip(RoundedCornerShape(2.dp)), color = VV.Tally, trackColor = VV.Raised2)
            }
        }
        Box(Modifier.fillMaxWidth().weight(1f)) {
            when (step) {
                0 -> Column(Modifier.fillMaxSize().padding(horizontal = 16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Segmented(Source.entries, source, { it.label }) { source = it }
                    when {
                        !any -> {
                            Hint("Para ver fotos y álbumes hace falta el permiso para leer fotos y videos.")
                            PrimaryButton("Permitir el acceso a la galería", Modifier.fillMaxWidth()) { permLauncher.launch(Gallery.permissions) }
                            SecondaryButton("Abrir los ajustes de la app", Modifier.fillMaxWidth(), onClick = {
                                runCatching { ctx.startActivity(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + ctx.packageName))) }
                            })
                        }
                        source == Source.ITEMS -> {
                            if (!full) Hint("Diste acceso solo a algunas fotos: solo se ven esas.")
                            val list = gallery
                            if (list == null) Hint("Leyendo la galería…") else {
                                if (!albums.isNullOrEmpty()) PillRow(listOf<Album?>(null) + albums!!, filterAlbum, { it?.name ?: "Todas" }) { filterAlbum = it }
                                val shown = filterAlbum?.let { a -> list.filter { it.albumId == a.id } } ?: list
                                if (shown.isEmpty()) Hint("Ninguna foto o video.")
                                MediaGrid(shown, picked, { item ->
                                    val u = item.uri.toString()
                                    picked = if (u in picked) picked - u else picked + u
                                }, Modifier.fillMaxWidth().weight(1f))
                            }
                        }
                        else -> Column(Modifier.fillMaxWidth().weight(1f).verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                            Hint("La app toma las mejores fotos y videos de todo el periodo de los álbumes elegidos.")
                            if (!full) {
                                Hint("Diste acceso solo a algunas fotos: solo se ven esas. Para los álbumes completos elige \"Permitir todo\".")
                                SecondaryButton("Permitir el acceso a todo", Modifier.fillMaxWidth(), onClick = { permLauncher.launch(Gallery.permissions) })
                            }
                            when {
                                albums == null -> Hint("Leyendo los álbumes…")
                                albums!!.isEmpty() -> Hint("Ningún álbum con fotos o videos.")
                                else -> albums!!.chunked(3).forEach { row ->
                                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                        row.forEach { a ->
                                            AlbumCard(a, a.id in selected, Modifier.weight(1f)) {
                                                selected = if (a.id in selected) selected - a.id else selected + a.id
                                            }
                                        }
                                        repeat(3 - row.size) { Spacer(Modifier.weight(1f)) }
                                    }
                                }
                            }
                        }
                    }
                }
                1 -> Column(Modifier.fillMaxSize().padding(horizontal = 16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    PillRow(STYLE_GROUPS.map { it.first }, group, { it }) { group = it }
                    val list = STYLE_GROUPS.first { it.first == group }.second
                    Column(Modifier.fillMaxWidth().weight(1f).verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        list.chunked(2).forEach { row ->
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                row.forEach { s -> StyleCard(s, s == style, Modifier.weight(1f)) { style = s } }
                                if (row.size == 1) Spacer(Modifier.weight(1f))
                            }
                        }
                        Spacer(Modifier.height(4.dp))
                        Text(style.label, style = MaterialTheme.typography.titleSmall, color = VV.Tally)
                        Text(style.description, style = MaterialTheme.typography.bodySmall)
                    }
                }
                else -> Column(
                    Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    PanelLabel("Música")
                    if (musicUri == null) {
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            PrimaryButton("Biblioteca en línea", Modifier.weight(1f)) { vm.openLibrary(effects = false, forAuto = true) }
                            SecondaryButton("Del teléfono", Modifier.weight(1f), onClick = { musicPicker.launch(arrayOf("audio/*")) })
                        }
                        Hint("Con la música los cortes caen en los beats y el audio de los videos se apaga. Sin ella, se quedan los sonidos originales.")
                    } else {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(VVIcons.Music, null, tint = VV.Music, modifier = Modifier.size(18.dp))
                            Spacer(Modifier.width(8.dp))
                            Text(musicName ?: "", style = MaterialTheme.typography.bodyMedium, maxLines = 1, overflow = TextOverflow.Ellipsis, modifier = Modifier.weight(1f))
                            IconBtn(VVIcons.Close, "Quitar la canción") { musicUri = null; if (duration == FIT_MUSIC) duration = 60_000_000L }
                        }
                    }
                    PanelLabel("Duración")
                    val options = (if (musicUri != null) listOf(FIT_MUSIC) else emptyList()) + DURATIONS
                    PillRow(options, duration, { d ->
                        when (d) { FIT_MUSIC -> "Como la canción"; 0L -> "Todo"; else -> if (d < 60_000_000L) "${d / 1_000_000} s" else "${d / 60_000_000} min" }
                    }) { duration = it }
                    if (source == Source.ITEMS && duration != 0L) Hint("Con \"Todo\" entran todas las fotos y videos elegidos; con una duración fija, si son demasiados, la app usa una parte de todo el periodo.")
                    PanelLabel("Título")
                    OutlinedTextField(
                        value = title,
                        onValueChange = { title = it; titleTouched = true },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        placeholder = { Text("Título del video (opcional)", color = VV.Faint) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = VV.Tally, unfocusedBorderColor = VV.Line, cursorColor = VV.Tally,
                            focusedTextColor = VV.Text, unfocusedTextColor = VV.Text,
                        ),
                    )
                    if (subtitle.isNotEmpty() && title.isNotBlank()) Hint("Debajo del título: $subtitle")
                    // Le scelte meno usate restano chiuse finché non servono.
                    Row(
                        Modifier.fillMaxWidth().clip(RoundedCornerShape(10.dp)).clickable { more = !more }.padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(if (more) "Menos opciones" else "Más opciones: formato y dirección", style = MaterialTheme.typography.labelLarge, color = VV.Tally, modifier = Modifier.weight(1f))
                        Text(if (more) "▲" else "▼", color = VV.Tally)
                    }
                    if (more) {
                        PanelLabel("Formato")
                        PillRow(VideoFormat.entries, format, { if (it == VideoFormat.AUTO) "Automático" else it.label }) { format = it }
                        Hint(
                            if (format == VideoFormat.AUTO) "Automático: vertical u horizontal según las fotos y videos más numerosos. Las fotos en la otra orientación se ven completas sobre el fondo desenfocado."
                            else "Las fotos y videos en la otra orientación tendrán el fondo desenfocado en lugar de las barras negras."
                        )
                        ToggleRow("Dirección inteligente", smart) { smart = it }
                        Hint(
                            if (smart) "La app mira cada video y elige el mejor tramo: nítido, bien iluminado, con rostros y sonrisas. Tarda un poco más (la segunda vez queda listo al instante)."
                            else "De los videos se toma un tramo central, sin analizarlos: más rápido."
                        )
                    }
                    Spacer(Modifier.height(4.dp))
                }
            }
        }
        // Riepilogo e pulsante del passo.
        Column(Modifier.fillMaxWidth().padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            if (chosen.isNotEmpty()) {
                val v = chosen.count { it.isVideo }
                Text(
                    "${chosen.size - v} fotos, $v videos" + (if (subtitle.isNotEmpty()) " · $subtitle" else "") + if (step > 0) " · ${style.label}" else "",
                    style = MaterialTheme.typography.labelLarge, color = VV.Tally,
                )
            }
            val ready = chosen.isNotEmpty() && busy == null
            when (step) {
                0 -> PrimaryButton(
                    if (chosen.isEmpty()) (if (source == Source.ITEMS) "Elegir fotos y videos" else "Elige al menos un álbum") else "Siguiente: estilo",
                    Modifier.fillMaxWidth(), enabled = ready,
                ) { step = 1 }
                1 -> PrimaryButton("Siguiente: música y detalles", Modifier.fillMaxWidth(), enabled = ready) { step = 2 }
                else -> PrimaryButton("Crear el video", Modifier.fillMaxWidth(), enabled = ready) {
                    val handPicked = source == Source.ITEMS
                    scope.launch {
                        val all = if (handPicked) chosen else withContext(Dispatchers.IO) { Gallery.items(ctx, selected) }
                        vm.autoCreate(
                            all, musicUri,
                            AutoOptions(style = style, format = format, targetUs = duration.coerceAtLeast(0), title = title, subtitle = if (title.isBlank()) "" else subtitle,
                                seed = (System.currentTimeMillis() % 10_000).toInt(), smart = smart),
                            fitMusic = duration == FIT_MUSIC, handPicked = handPicked,
                        )
                    }
                }
            }
        }
    }
}

/** I tre passi in alto: quello in corso in ambra; si torna indietro toccando un passo già fatto. */
@Composable
private fun StepBar(step: Int, onStep: (Int) -> Unit) {
    Row(Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 6.dp), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
        STEPS.forEachIndexed { i, label ->
            val on = i == step
            val done = i < step
            Column(Modifier.weight(1f).clip(RoundedCornerShape(8.dp)).clickable(enabled = done) { onStep(i) }.padding(vertical = 4.dp)) {
                Box(Modifier.fillMaxWidth().height(4.dp).clip(RoundedCornerShape(2.dp)).background(if (on || done) VV.Tally else VV.Raised2))
                Text(
                    "${i + 1}. $label", style = MaterialTheme.typography.labelMedium, maxLines = 1, overflow = TextOverflow.Ellipsis,
                    color = if (on) VV.Tally else if (done) VV.Text else VV.Dim, modifier = Modifier.padding(top = 4.dp),
                )
            }
        }
    }
}

@Composable
private fun StyleCard(s: AutoStyle, on: Boolean, modifier: Modifier, onClick: () -> Unit) {
    Column(
        modifier.height(96.dp).clip(RoundedCornerShape(12.dp)).background(if (on) VV.Raised2 else VV.Raised)
            .border(if (on) 2.dp else 0.dp, if (on) VV.Tally else Color.Transparent, RoundedCornerShape(12.dp))
            .clickable(onClick = onClick).padding(horizontal = 12.dp, vertical = 10.dp),
    ) {
        Text(s.label, style = MaterialTheme.typography.titleSmall, color = if (on) VV.Tally else VV.Text)
        Text(s.description, style = MaterialTheme.typography.bodySmall, maxLines = 3, overflow = TextOverflow.Ellipsis)
    }
}

@Composable
internal fun AlbumCard(a: Album, on: Boolean, modifier: Modifier, onClick: () -> Unit) {
    val ctx = LocalContext.current
    val thumb by produceState<Bitmap?>(null, a.coverUri) { value = withContext(Dispatchers.IO) { Gallery.thumbnail(ctx, a.coverUri) } }
    Column(modifier.clip(RoundedCornerShape(10.dp)).clickable(onClick = onClick)) {
        Box(
            Modifier.fillMaxWidth().aspectRatio(1f).clip(RoundedCornerShape(10.dp)).background(VV.Raised)
                .border(if (on) 3.dp else 0.dp, if (on) VV.Tally else Color.Transparent, RoundedCornerShape(10.dp)),
        ) {
            thumb?.let { Image(it.asImageBitmap(), a.name, contentScale = ContentScale.Crop, modifier = Modifier.fillMaxSize()) }
            if (on) {
                Box(
                    Modifier.align(Alignment.TopEnd).padding(6.dp).size(24.dp).clip(CircleShape).background(VV.Tally),
                    contentAlignment = Alignment.Center,
                ) { Icon(VVIcons.Check, "Elegido", tint = VV.TallyInk, modifier = Modifier.size(16.dp)) }
            }
        }
        Text(a.name, style = MaterialTheme.typography.labelLarge, maxLines = 1, overflow = TextOverflow.Ellipsis, color = if (on) VV.Tally else VV.Text,
            modifier = Modifier.padding(top = 4.dp))
        Text("${a.count}" + if (a.videos > 0) " · ${a.videos} videos" else "", style = MaterialTheme.typography.bodySmall)
    }
}
