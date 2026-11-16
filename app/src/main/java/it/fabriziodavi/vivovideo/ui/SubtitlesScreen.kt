package it.fabriziodavi.vivovideo.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.size
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import it.fabriziodavi.vivovideo.engine.Whisper
import it.fabriziodavi.vivovideo.model.SubPosition
import it.fabriziodavi.vivovideo.model.SubStyle
import it.fabriziodavi.vivovideo.model.Subtitles

/** Sottotitoli automatici: modello, lingua, stile e posizione, poi l'app ascolta il video. */
@Composable
fun SubtitlesScreen(vm: EditorViewModel) {
    val ctx = LocalContext.current
    val busy = vm.busy
    BackHandler { if (busy != null) vm.cancelSubtitles() else vm.showSubtitles = false }
    var model by remember { mutableStateOf(Whisper.defaultModel(ctx)) }
    var lang by remember { mutableStateOf("es") }
    var style by remember { mutableStateOf(SubStyle.POP) }
    var pos by remember { mutableStateOf(SubPosition.BOTTOM) }
    var accent by remember { mutableStateOf(Subtitles.KINETIC_HIGHLIGHT) }
    val existing = vm.project?.texts?.count { it.subtitle } ?: 0

    Column(Modifier.fillMaxSize().background(VV.Canvas).safeDrawingPadding()) {
        Row(Modifier.fillMaxWidth().height(52.dp).padding(horizontal = 4.dp), verticalAlignment = Alignment.CenterVertically) {
            IconBtn(VVIcons.Back, "Atrás", enabled = busy == null) { vm.showSubtitles = false }
            Text("Subtítulos automáticos", style = MaterialTheme.typography.titleMedium, modifier = Modifier.weight(1f))
        }
        Column(
            Modifier.fillMaxWidth().weight(1f).verticalScroll(rememberScrollState()).padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Hint("La app escucha el habla del video (audio de los clips, voz en off, audio separado; la música no) y escribe los subtítulos con los tiempos de cada palabra. Todo ocurre en el teléfono: internet solo hace falta para descargar el modelo la primera vez.")
            PanelLabel("Reconocimiento")
            Whisper.Model.entries.filter { it != Whisper.Model.TINY }.forEach { m ->
                val on = m == model
                val ready = Whisper.isDownloaded(ctx, m)
                Column(
                    Modifier.fillMaxWidth().clip(RoundedCornerShape(12.dp)).background(if (on) VV.Raised2 else VV.Raised)
                        .border(if (on) 2.dp else 0.dp, if (on) VV.Tally else Color.Transparent, RoundedCornerShape(12.dp))
                        .clickable(enabled = busy == null) { model = m }.padding(12.dp),
                ) {
                    Text(m.label + if (ready) "  ·  listo" else "  ·  ${m.sizeMb} MB por descargar", style = MaterialTheme.typography.titleSmall, color = if (on) VV.Tally else VV.Text)
                    Text(m.hint, style = MaterialTheme.typography.bodySmall)
                }
            }
            PanelLabel("Idioma del habla")
            Segmented(listOf("es", "it", "en", "auto"), lang, { when (it) { "es" -> "Español"; "it" -> "Italiano"; "en" -> "Inglés"; else -> "Automática" } }, busy == null) { lang = it }
            PanelLabel("Estilo")
            PillRow(SubStyle.entries, style, { it.label }, busy == null) { style = it }
            // Esempio dello stile scelto.
            Box(Modifier.fillMaxWidth().height(64.dp).clip(RoundedCornerShape(10.dp)).background(Color(0xFF2A2F38)), contentAlignment = Alignment.Center) {
                val sample = when (style) {
                    SubStyle.KARAOKE -> buildAnnotatedString {
                        append("Che bella ")
                        withStyle(SpanStyle(color = Color(accent))) { append("giornata") }
                        append(" oggi")
                    }
                    SubStyle.FILL -> buildAnnotatedString {
                        withStyle(SpanStyle(color = Color(accent))) { append("CHE BELLA GIOR") }
                        append("NATA")
                    }
                    SubStyle.REVEAL -> buildAnnotatedString { append("Che bella giornata…") }
                    SubStyle.SOCIAL -> buildAnnotatedString { append("Che bella giornata") }
                    SubStyle.MINIMAL -> buildAnnotatedString { append("che bella giornata oggi") }
                    // Cinetici: una parola alla volta, quelle importanti nel colore di risalto.
                    SubStyle.POP, SubStyle.GLASS, SubStyle.CHROME, SubStyle.GLITCHY, SubStyle.WAVE -> buildAnnotatedString {
                        withStyle(SpanStyle(color = Color(accent), fontSize = 26.sp)) { append("JORNADA") }
                    }
                    else -> buildAnnotatedString { append("Che bella giornata oggi") }
                }
                Text(
                    sample, color = Color.White, fontWeight = FontWeight.Bold,
                    modifier = when {
                        style == SubStyle.BOX -> Modifier.background(Color(0x99000000), RoundedCornerShape(6.dp)).padding(horizontal = 10.dp, vertical = 4.dp)
                        style == SubStyle.GLASS -> Modifier.background(Color(0x40FFFFFF), RoundedCornerShape(14.dp)).border(1.dp, Color(0x99FFFFFF), RoundedCornerShape(14.dp)).padding(horizontal = 14.dp, vertical = 4.dp)
                        else -> Modifier
                    },
                )
            }
            style.kinetic?.let { Hint(it.hint + ". Las palabras importantes (números, palabras largas, exclamaciones) se colorean solas.") }
            if (style == SubStyle.FILL) Hint("La frase completa, blanca con contorno: el color la rellena letra por letra mientras la persona habla, como en los reels.")
            if (style.kinetic != null || style == SubStyle.FILL || style == SubStyle.KARAOKE) {
                PanelLabel(if (style == SubStyle.FILL || style == SubStyle.KARAOKE) "Color de la voz" else "Color de las palabras importantes")
                Row(Modifier.horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Subtitles.ACCENTS.forEach { (name, c) ->
                        Box(
                            Modifier.size(34.dp).clip(RoundedCornerShape(17.dp)).background(Color(c))
                                .border(if (c == accent) 3.dp else 1.dp, if (c == accent) Color.White else VV.Line, RoundedCornerShape(17.dp))
                                .clickable(enabled = busy == null) { accent = c }
                                .semantics { contentDescription = name }
                        )
                    }
                }
            }
            PanelLabel("Posición")
            Segmented(SubPosition.entries, pos, { it.label }, busy == null) { pos = it }
            if (existing > 0) Hint("Ya hay $existing subtítulos automáticos: serán sustituidos por los nuevos (Deshacer para volver atrás).")
        }
        Column(Modifier.fillMaxWidth().padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            if (busy != null) {
                Text(busy, style = MaterialTheme.typography.bodyMedium)
                LinearProgressIndicator(Modifier.fillMaxWidth().height(4.dp).clip(RoundedCornerShape(2.dp)), color = VV.Tally, trackColor = VV.Raised2)
                SecondaryButton("Deshacer", Modifier.fillMaxWidth(), onClick = { vm.cancelSubtitles() })
            } else {
                PrimaryButton("Crear los subtítulos", Modifier.fillMaxWidth()) { vm.autoSubtitles(model, lang, style, pos, accent) }
                if (existing > 0) SecondaryButton("Quitar los subtítulos automáticos", Modifier.fillMaxWidth(), onClick = { vm.removeSubtitles(); vm.showSubtitles = false })
            }
        }
    }
}
