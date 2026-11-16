package it.fabriziodavi.vivovideo.ui

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import it.fabriziodavi.vivovideo.media.Album
import it.fabriziodavi.vivovideo.media.Gallery
import it.fabriziodavi.vivovideo.media.GalleryItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/** Album interi da mettere in un progetto (nuovo o aperto), in ordine di data. */
@Composable
fun AlbumPickerScreen(vm: EditorViewModel) {
    val forNew = vm.albumPick ?: return
    val ctx = LocalContext.current
    BackHandler { vm.albumPick = null }
    var permTick by remember { mutableIntStateOf(0) }
    val permLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permTick++ }
    val full = remember(permTick) { Gallery.fullAccess(ctx) }
    val any = remember(permTick) { Gallery.anyAccess(ctx) }
    LaunchedEffect(Unit) { if (!Gallery.anyAccess(ctx)) permLauncher.launch(Gallery.permissions) }
    val albums by produceState<List<Album>?>(null, permTick) {
        value = if (Gallery.anyAccess(ctx)) withContext(Dispatchers.IO) { runCatching { Gallery.albums(ctx) }.getOrDefault(emptyList()) } else emptyList()
    }
    var selected by remember { mutableStateOf(setOf<String>()) }
    val items by produceState<List<GalleryItem>>(emptyList(), selected, permTick) {
        value = if (selected.isEmpty()) emptyList() else withContext(Dispatchers.IO) { runCatching { Gallery.items(ctx, selected) }.getOrDefault(emptyList()) }
    }
    var confirmMany by remember { mutableStateOf(false) }

    Column(Modifier.fillMaxSize().background(VV.Canvas).safeDrawingPadding()) {
        Row(Modifier.fillMaxWidth().height(52.dp).padding(horizontal = 4.dp), verticalAlignment = Alignment.CenterVertically) {
            IconBtn(VVIcons.Back, "Atrás") { vm.albumPick = null }
            Text(if (forNew) "Proyecto nuevo desde un álbum" else "Agregar álbum", style = MaterialTheme.typography.titleMedium, modifier = Modifier.weight(1f))
        }
        Column(
            Modifier.fillMaxWidth().weight(1f).verticalScroll(rememberScrollState()).padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Hint("Toca uno o más álbumes: todas las fotos y videos entran en el proyecto por orden de fecha. Luego los cortas, reordenas o quitas como quieras.")
            when {
                !any -> {
                    Hint("Para ver los álbumes hace falta el permiso para leer fotos y videos.")
                    PrimaryButton("Permitir el acceso a la galería", Modifier.fillMaxWidth()) { permLauncher.launch(Gallery.permissions) }
                    SecondaryButton("Abrir los ajustes de la app", Modifier.fillMaxWidth(), onClick = {
                        runCatching { ctx.startActivity(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + ctx.packageName))) }
                    })
                }
                albums == null -> Hint("Leyendo los álbumes…")
                else -> {
                    if (!full) {
                        Hint("Diste acceso solo a algunas fotos: solo se ven esas.")
                        SecondaryButton("Permitir el acceso a todo", Modifier.fillMaxWidth(), onClick = { permLauncher.launch(Gallery.permissions) })
                    }
                    if (albums!!.isEmpty()) Hint("Ningún álbum con fotos o videos.")
                    albums!!.chunked(3).forEach { row ->
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
            Spacer(Modifier.height(4.dp))
        }
        Box(Modifier.fillMaxWidth().padding(16.dp)) {
            val v = items.count { it.isVideo }
            PrimaryButton(
                if (items.isEmpty()) "Elige al menos un álbum" else "Agregar ${items.size - v} fotos y $v videos",
                Modifier.fillMaxWidth(), enabled = items.isNotEmpty(),
            ) { if (items.size > 150) confirmMany = true else vm.addAlbumItems(items) }
        }
    }
    if (confirmMany) {
        AlertDialog(
            onDismissRequest = { confirmMany = false },
            containerColor = VV.Raised,
            title = { Text("${items.size} elementi", style = MaterialTheme.typography.titleSmall) },
            text = { Text("Son muchos: la lectura tardará un poco y el proyecto será largo. Para elegir solo los mejores momentos está el Video automático.", style = MaterialTheme.typography.bodyMedium) },
            confirmButton = { TextButton(onClick = { confirmMany = false; vm.addAlbumItems(items) }) { Text("Agregar todo", color = VV.Tally) } },
            dismissButton = { TextButton(onClick = { confirmMany = false }) { Text("Deshacer", color = VV.Dim) } },
        )
    }
}

/** Da dove prendere foto e video: scelta singola o album interi. */
@Composable
fun AddMediaDialog(onPick: () -> Unit, onAlbums: () -> Unit, onDismiss: () -> Unit, onCollage: (() -> Unit)? = null) {
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = VV.Raised,
        title = { Text("Agregar fotos y videos", style = MaterialTheme.typography.titleSmall) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                PrimaryButton("Elegir fotos y videos", Modifier.fillMaxWidth()) { onDismiss(); onPick() }
                SecondaryButton("Álbumes completos", Modifier.fillMaxWidth(), onClick = { onDismiss(); onAlbums() })
                if (onCollage != null) SecondaryButton("Collage de fotos", Modifier.fillMaxWidth(), onClick = { onDismiss(); onCollage() })
            }
        },
        confirmButton = { TextButton(onClick = onDismiss) { Text("Deshacer", color = VV.Dim) } },
    )
}
