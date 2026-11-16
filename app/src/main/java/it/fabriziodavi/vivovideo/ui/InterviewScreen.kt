package it.fabriziodavi.vivovideo.ui

import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import it.fabriziodavi.vivovideo.engine.Whisper
import it.fabriziodavi.vivovideo.model.Clip
import it.fabriziodavi.vivovideo.model.InterviewFormat
import it.fabriziodavi.vivovideo.model.InterviewOptions

/**
 * Intervista doppia (stile Le Iene): due video con le stesse domande. L'app ascolta i due video, trova domande e risposte
 * e monta per ogni domanda il cartello e le due risposte, alternate o a schermo diviso.
 */
@Composable
fun InterviewScreen(vm: EditorViewModel) {
    val ctx = LocalContext.current
    val busy = vm.busy
    BackHandler { if (busy != null) vm.cancelSubtitles() else vm.closeInterview() }
    var pickFirst by remember { mutableStateOf(true) }
    val picker = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) vm.pickInterviewVideo(pickFirst, uri)
    }
    fun pick(first: Boolean) {
        pickFirst = first
        picker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.VideoOnly))
    }
    var nameA by remember { mutableStateOf("") }
    var nameB by remember { mutableStateOf("") }
    var capA by remember { mutableStateOf("") }
    var capB by remember { mutableStateOf("") }
    var format by remember { mutableStateOf(InterviewFormat.ALTERNATE) }
    var cards by remember { mutableStateOf(true) }
    var auto by remember { mutableStateOf(true) }
    var typed by remember { mutableStateOf("") }
    var model by remember { mutableStateOf(Whisper.defaultModel(ctx)) }
    val qas = vm.interviewQas
    // Nessuna domanda trovata: si apre da solo il campo per scriverle (prima il pulsante restava su «Analizza» e basta).
    LaunchedEffect(qas) { if (qas != null && qas.isEmpty()) auto = false }
    val colors = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = VV.Tally, unfocusedBorderColor = VV.Line, cursorColor = VV.Tally,
        focusedTextColor = VV.Text, unfocusedTextColor = VV.Text,
    )

    Column(Modifier.fillMaxSize().background(VV.Canvas).safeDrawingPadding()) {
        Row(Modifier.fillMaxWidth().height(52.dp).padding(horizontal = 4.dp), verticalAlignment = Alignment.CenterVertically) {
            IconBtn(VVIcons.Back, "Atrás", enabled = busy == null) { vm.closeInterview() }
            Text("Entrevista doble", style = MaterialTheme.typography.titleMedium, modifier = Modifier.weight(1f))
        }
        Column(
            Modifier.fillMaxWidth().weight(1f).verticalScroll(rememberScrollState()).padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Hint("Dos personas, las mismas preguntas, dos videos. La app los escucha, encuentra las preguntas y recorta la respuesta de cada uno, y luego monta todo pregunta por pregunta. El resultado se abre en el editor, para retocarlo como los demás proyectos.")
            PanelLabel("Los dos videos")
            VideoRow("A", vm.interviewA, busy == null) { pick(true) }
            VideoRow("B", vm.interviewB, busy == null) { pick(false) }
            PanelLabel("Nombres y pies de foto (abajo, como en la TV)")
            listOf(Triple("A", nameA, capA), Triple("B", nameB, capB)).forEach { (who, n, c) ->
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    OutlinedTextField(
                        value = n, onValueChange = { v -> if (who == "A") nameA = v else nameB = v }, singleLine = true, colors = colors,
                        modifier = Modifier.weight(1f), placeholder = { Text("Nome $who", color = VV.Faint) },
                    )
                    OutlinedTextField(
                        value = c, onValueChange = { v -> if (who == "A") capA = v else capB = v }, singleLine = true, colors = colors,
                        modifier = Modifier.weight(1.3f), placeholder = { Text("Pie de foto (ej. 46 años, Guadalajara)", color = VV.Faint) },
                    )
                }
            }
            PanelLabel("Formato")
            InterviewFormat.entries.forEach { f ->
                val on = f == format
                Column(
                    Modifier.fillMaxWidth().clip(RoundedCornerShape(12.dp)).background(if (on) VV.Raised2 else VV.Raised)
                        .border(if (on) 2.dp else 0.dp, if (on) VV.Tally else Color.Transparent, RoundedCornerShape(12.dp))
                        .clickable(enabled = busy == null) { format = f }.padding(12.dp),
                ) {
                    Text(f.label, style = MaterialTheme.typography.titleSmall, color = if (on) VV.Tally else VV.Text)
                    Text(f.hint, style = MaterialTheme.typography.bodySmall)
                }
            }
            ToggleRow("Cartel con la pregunta antes de las respuestas", cards, busy == null) { cards = it }
            PanelLabel("Preguntas")
            ToggleRow("Encontrarlas sola (las frases dichas iguales en los dos, o con signo de interrogación)", auto, busy == null) { auto = it; vm.resetInterviewAnalysis() }
            if (!auto) {
                if (qas != null && qas.isEmpty()) Hint("No encontré preguntas en los videos. Escríbelas aquí, una por línea, como las dijo quien entrevista (bastan las palabras principales), y luego toca «Analizar los dos videos»: no vuelve a escuchar, queda listo al instante.")
                OutlinedTextField(
                    value = typed, onValueChange = { typed = it }, colors = colors, minLines = 3,
                    modifier = Modifier.fillMaxWidth(), placeholder = { Text("Una pregunta por línea, como la dice quien entrevista", color = VV.Faint) },
                )
            }
            Hint("Las preguntas deben oírse en los videos (quien entrevista las hace de viva voz). El reconocimiento de voz es en el teléfono; internet solo hace falta para descargar el modelo la primera vez.")
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
            if (qas != null && qas.isNotEmpty()) {
                PanelLabel("Preguntas y respuestas encontradas")
                Hint("El texto de la pregunta es el del cartel: corrígelo aquí si hace falta. Quita con ✕ las preguntas que no quieras en el video. Las respuestas no encontradas se quedan fuera solas.")
                qas.forEachIndexed { i, qa ->
                    Row(
                        Modifier.fillMaxWidth().clip(RoundedCornerShape(12.dp)).background(VV.Raised).padding(start = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Column(Modifier.weight(1f).padding(vertical = 8.dp)) {
                            // Il testo del cartello: si corregge qui se il riconoscimento ha sbagliato una parola.
                            OutlinedTextField(
                                value = qa.question, onValueChange = { vm.renameInterviewQuestion(i, it) }, colors = colors,
                                enabled = busy == null, modifier = Modifier.fillMaxWidth(), label = { Text("Domanda ${i + 1}", color = VV.Faint) },
                            )
                            Text(
                                "A: " + (qa.a?.let { sec(it.lengthUs) } ?: "no encontrada") + "   ·   B: " + (qa.b?.let { sec(it.lengthUs) } ?: "no encontrada"),
                                style = MaterialTheme.typography.bodySmall, color = if (qa.usable) VV.Dim else VV.Danger,
                            )
                        }
                        IconBtn(VVIcons.Close, "Quitar la pregunta", enabled = busy == null) { vm.removeInterviewQuestion(i) }
                    }
                }
            }
        }
        Column(Modifier.fillMaxWidth().padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            val ready = vm.interviewA != null && vm.interviewB != null
            val questions = typed.lines().map { it.trim() }.filter { it.isNotEmpty() }
            if (busy != null) {
                Text(busy, style = MaterialTheme.typography.bodyMedium)
                LinearProgressIndicator(Modifier.fillMaxWidth().height(4.dp).clip(RoundedCornerShape(2.dp)), color = VV.Tally, trackColor = VV.Raised2)
                SecondaryButton("Deshacer", Modifier.fillMaxWidth(), onClick = { vm.cancelSubtitles() })
            } else if (qas != null && qas.any { it.usable }) {
                PrimaryButton("Crear la entrevista", Modifier.fillMaxWidth()) {
                    vm.createInterview(InterviewOptions(format, nameA, nameB, capA, capB, cardUs = if (cards) 2_500_000L else 0L))
                }
                SecondaryButton("Rehacer el análisis", Modifier.fillMaxWidth(), onClick = { vm.analyzeInterview(model, "es", if (auto) emptyList() else questions) })
            } else {
                PrimaryButton("Analizar los dos videos", Modifier.fillMaxWidth(), enabled = ready && (auto || questions.isNotEmpty())) {
                    vm.analyzeInterview(model, "es", if (auto) emptyList() else questions)
                }
                if (!ready) Hint("Primero elige el video A y el video B.")
            }
        }
    }
}

/** Un video scelto (nome e durata) o il pulsante per sceglierlo. */
@Composable
private fun VideoRow(who: String, c: Clip?, enabled: Boolean, onPick: () -> Unit) {
    Row(
        Modifier.fillMaxWidth().clip(RoundedCornerShape(12.dp)).background(VV.Raised).clickable(enabled = enabled, onClick = onPick).padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(who, style = MaterialTheme.typography.titleMedium, color = VV.Tally, modifier = Modifier.padding(end = 12.dp))
        Column(Modifier.weight(1f)) {
            Text(c?.fileName?.ifBlank { "Video $who" } ?: "Elegir el video $who", style = MaterialTheme.typography.bodyMedium, maxLines = 1, overflow = TextOverflow.Ellipsis)
            if (c != null) Text("${formatTime(c.sourceDurationUs)} · ${c.width}×${c.height}", style = MaterialTheme.typography.bodySmall, color = VV.Dim)
        }
        Text(if (c == null) "Elegir" else "Cambiar", style = MaterialTheme.typography.labelLarge, color = VV.Tally)
    }
}

private fun sec(us: Long) = String.format(java.util.Locale.ITALIAN, "%.1f s", us / 1e6)
