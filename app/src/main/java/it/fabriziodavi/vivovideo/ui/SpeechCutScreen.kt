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
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.unit.dp
import it.fabriziodavi.vivovideo.engine.Whisper
import it.fabriziodavi.vivovideo.model.JumpCuts
import it.fabriziodavi.vivovideo.model.JumpStyle

/**
 * Pulizia del parlato: toglie le pause lunghe e (se si vuole) gli "ehm", come negli editor per podcast.
 * In modalità jump cut ([EditorViewModel.jumpMode]) a ogni taglio cambia anche l'inquadratura, con stili pronti.
 */
@Composable
fun SpeechCutScreen(vm: EditorViewModel) {
    val ctx = LocalContext.current
    val busy = vm.busy
    BackHandler { if (busy != null) vm.cancelSubtitles() else { vm.closeSpeechCut() } }
    var model by remember { mutableStateOf(Whisper.defaultModel(ctx)) }
    var lang by remember { mutableStateOf("es") }
    val jump = vm.jumpMode
    // Jump cut: ritmo serrato di serie (0,4 s), come nei video parlati sui social.
    var minPause by remember { mutableStateOf(if (jump) 400_000L else 700_000L) }
    var fillers by remember { mutableStateOf(false) }
    var style by remember { mutableStateOf(JumpStyle.CLEAN) }
    var whoosh by remember { mutableStateOf(true) }
    var maxHold by remember { mutableStateOf(JumpCuts.MAX_HOLD_US) }
    // Con Social servono le parole (sottotitoli): il riconoscimento vocale si usa anche senza togliere gli ehm.
    val forSubtitles = jump && style == JumpStyle.SOCIAL
    val transcribe = fillers || forSubtitles
    val plan = vm.speechPlan
    val total = vm.project?.durationUs ?: 0L
    val summary = remember(plan, style, whoosh, maxHold, vm.speechWords) { if (jump && plan != null) vm.jumpPreview(style, whoosh, maxHold) else null }

    Column(Modifier.fillMaxSize().background(VV.Canvas).safeDrawingPadding()) {
        Row(Modifier.fillMaxWidth().height(52.dp).padding(horizontal = 4.dp), verticalAlignment = Alignment.CenterVertically) {
            IconBtn(VVIcons.Back, "Atrás", enabled = busy == null) { vm.closeSpeechCut() }
            Text(if (jump) "Jump cut" else "Quitar pausas y «ehm»", style = MaterialTheme.typography.titleMedium, modifier = Modifier.weight(1f))
        }
        Column(
            Modifier.fillMaxWidth().weight(1f).verticalScroll(rememberScrollState()).padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            if (jump) {
                Hint("Para videos hablados: la app quita las pausas (y, si quieres, los «ehm») y en cada corte cambia el encuadre, normal y cerrado por turnos, centrado en el rostro. Así el salto del corte parece intencional. Primero ves el resumen, luego decides; un solo Deshacer quita todo.")
                PanelLabel("Estilo")
                JumpStyle.entries.forEach { st ->
                    val on = st == style
                    Column(
                        Modifier.fillMaxWidth().clip(RoundedCornerShape(12.dp)).background(if (on) VV.Raised2 else VV.Raised)
                            .border(if (on) 2.dp else 0.dp, if (on) VV.Tally else Color.Transparent, RoundedCornerShape(12.dp))
                            .clickable(enabled = busy == null) {
                                style = st
                                // Social ha bisogno delle parole: se l'analisi fatta non le ha, si rifà.
                                if (st == JumpStyle.SOCIAL && vm.speechWords.isEmpty()) vm.speechPlan = null
                            }.padding(12.dp),
                    ) {
                        Text(st.label, style = MaterialTheme.typography.titleSmall, color = if (on) VV.Tally else VV.Text)
                        Text(st.hint, style = MaterialTheme.typography.bodySmall)
                    }
                }
                if (style == JumpStyle.SOCIAL) ToggleRow("Whoosh en los destellos", whoosh, busy == null) { whoosh = it }
                PanelLabel("Cambiar encuadre como máximo cada")
                LabeledSlider("Duración máxima", null, if (maxHold >= JumpCuts.NO_HOLD_LIMIT) 41 else (maxHold / 500_000L).toInt(), busy == null, range = 2..41,
                    format = { if (it >= 41) "sin límite" else String.format(java.util.Locale.ITALIAN, "%.1f s", it / 2f) }) { maxHold = if (it >= 41) JumpCuts.NO_HOLD_LIMIT else it * 500_000L }
                Hint("Cuánto puede durar como máximo un encuadre (normal o cerrado en el rostro) cuando hablas sin pausas, de 1 a 20 s (medio segundo a la vez), o todo a la derecha «sin límite»: cambia solo en los cortes. En cada corte cambia de todos modos, así que un trozo puede ser más corto.")
            } else {
                Hint("La app escucha el audio de los clips y quita los silencios largos, dejando un respiro entre las frases. Las fotos, los clips mudos, la música y las voces en off no se tocan. Primero ves cuánto se quita, luego decides.")
            }
            PanelLabel("Pausas más largas de")
            PillRow(listOf(0L, 400_000L, 700_000L, 1_000_000L), minPause, { if (it == 0L) "Déjalas" else String.format(java.util.Locale.ITALIAN, "%.1f s", it / 1e6) }, busy == null) {
                minPause = it; vm.speechPlan = null
            }
            Hint("0,4 s: ritmo apretado de video para redes. 0,7 s: natural. 1 s: quita solo los silencios realmente largos.")
            ToggleRow("Quitar también ehm, eh, uhm, mmm", fillers, busy == null) { fillers = it; vm.speechPlan = null }
            if (transcribe) {
                Hint(
                    if (fillers) "Para las muletillas hace falta el reconocimiento de voz (en el teléfono; internet solo para descargar el modelo la primera vez)."
                    else "Para los subtítulos del estilo Social hace falta el reconocimiento de voz (en el teléfono; internet solo para descargar el modelo la primera vez).",
                )
                Whisper.Model.entries.filter { it != Whisper.Model.TINY }.forEach { m ->
                    val on = m == model
                    val ready = Whisper.isDownloaded(ctx, m)
                    Column(
                        Modifier.fillMaxWidth().clip(RoundedCornerShape(12.dp)).background(if (on) VV.Raised2 else VV.Raised)
                            .border(if (on) 2.dp else 0.dp, if (on) VV.Tally else Color.Transparent, RoundedCornerShape(12.dp))
                            .clickable(enabled = busy == null) { model = m; vm.speechPlan = null }.padding(12.dp),
                    ) {
                        Text(m.label + if (ready) "  ·  listo" else "  ·  ${m.sizeMb} MB por descargar", style = MaterialTheme.typography.titleSmall, color = if (on) VV.Tally else VV.Text)
                        Text(m.hint, style = MaterialTheme.typography.bodySmall)
                    }
                }
                Segmented(listOf("es", "it", "en", "auto"), lang, { when (it) { "es" -> "Español"; "it" -> "Italiano"; "en" -> "Inglés"; else -> "Automática" } }, busy == null) { lang = it; vm.speechPlan = null }
            }
            if (plan != null) {
                PanelLabel("Qué se quita")
                Column(Modifier.fillMaxWidth().clip(RoundedCornerShape(12.dp)).background(VV.Raised).padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    if (plan.isEmpty) Text("Nada que quitar con estos ajustes.", style = MaterialTheme.typography.bodyMedium)
                    else {
                        if (minPause > 0) Text("${plan.pauses} pausas · ${sec(plan.pauseUs)}", style = MaterialTheme.typography.bodyMedium)
                        if (fillers) Text("${plan.fillers} «ehm» y similares · ${sec(plan.fillerUs)}", style = MaterialTheme.typography.bodyMedium)
                        // Con i jump cut la nuova durata sta nel riepilogo, qui sotto.
                        if (!jump) Text("El video pasa de ${formatTime(total)} a ${formatTime(total - plan.totalUs)}", style = MaterialTheme.typography.titleSmall, color = VV.Tally)
                    }
                }
                if (jump && summary != null) {
                    PanelLabel("Resumen de los jump cuts")
                    Column(Modifier.fillMaxWidth().clip(RoundedCornerShape(12.dp)).background(VV.Raised).padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        val pct = kotlin.math.round((JumpCuts.ZOOM - 1) * 100).toInt()
                        Text(count(summary.cuts, "taglio", "tagli") + " con cambio de encuadre", style = MaterialTheme.typography.bodyMedium)
                        if (summary.reframes > 0) Text(count(summary.reframes, "cambio", "cambi") + " de encuadre en las frases largas, sin cortar", style = MaterialTheme.typography.bodyMedium)
                        Text(count(summary.zoomed, "trozo cerrado", "trozos cerrados") + " (+$pct%) en el rostro", style = MaterialTheme.typography.bodyMedium)
                        if (style == JumpStyle.DYNAMIC) Text("${summary.punches} punch-in en las frases más fuertes", style = MaterialTheme.typography.bodyMedium)
                        if (style == JumpStyle.SOCIAL) {
                            Text(count(summary.flashes, "lampo", "lampi") + if (summary.whooshes > 0) " con whoosh" else "", style = MaterialTheme.typography.bodyMedium)
                            Text(if (summary.subtitles > 0) count(summary.subtitles, "sottotitolo", "sottotitoli") + " palabra por palabra (sustituyen a los que hay)" else "Ninguna palabra oída: sin subtítulos", style = MaterialTheme.typography.bodyMedium)
                        }
                        Text("Microfundidos del audio en cada corte, sin clics.", style = MaterialTheme.typography.bodySmall, color = VV.Dim)
                        Text("El video pasa de ${formatTime(summary.beforeUs)} a ${formatTime(summary.afterUs)}", style = MaterialTheme.typography.titleSmall, color = VV.Tally)
                    }
                    if (summary.cuts + summary.reframes == 0) Hint("Ningún corte dentro de una misma toma: el encuadre no cambia. Prueba con pausas más cortas.")
                }
                if (fillers && plan.fillerCuts.isNotEmpty()) {
                    PanelLabel("Los «ehm» encontrados")
                    Hint("Escucha cada uno: ▶ como está ahora, ✂ como quedará después del corte. Quita la marca a los que quieras dejar. Los «por revisar» o «por escuchar» son dudas (quizá una palabra alargada): por defecto se quedan, marca los que sean «ehm».")
                    plan.fillerCuts.forEachIndexed { i, f ->
                        FillerRow(
                            at = f.span.startUs, lenUs = f.span.lengthUs,
                            label = when {
                                f.doubtful && f.word == null -> "posible «ehm» · por escuchar"
                                f.doubtful -> "«${f.word}»? por revisar"
                                f.word != null -> "«${f.word}»"
                                else -> "sonido de duda"
                            },
                            on = f.on,
                            onToggle = { vm.toggleFiller(i) },
                            onPlay = { vm.playFiller(i, after = false) },
                            onPlayAfter = { vm.playFiller(i, after = true) },
                        )
                    }
                }
            }
        }
        Column(Modifier.fillMaxWidth().padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            if (busy != null) {
                Text(busy, style = MaterialTheme.typography.bodyMedium)
                LinearProgressIndicator(Modifier.fillMaxWidth().height(4.dp).clip(RoundedCornerShape(2.dp)), color = VV.Tally, trackColor = VV.Raised2)
                SecondaryButton("Deshacer", Modifier.fillMaxWidth(), onClick = { vm.cancelSubtitles() })
            } else if (plan != null && !plan.isEmpty) {
                if (jump) PrimaryButton("Hacer los jump cuts", Modifier.fillMaxWidth()) { vm.applyJumpCuts(style, whoosh, maxHold) }
                else PrimaryButton("Taglia ${sec(plan.totalUs)}", Modifier.fillMaxWidth()) { vm.applySpeechCuts() }
                SecondaryButton("Rehacer el análisis", Modifier.fillMaxWidth(), onClick = { vm.analyzeSpeech(model, lang, minPause, fillers, forSubtitles) })
            } else {
                PrimaryButton("Analizar", Modifier.fillMaxWidth(), enabled = minPause > 0 || fillers) { vm.analyzeSpeech(model, lang, minPause, fillers, forSubtitles) }
            }
        }
    }
}

/** Un ehm della lista: spunta, quando e quanto dura, ascolto prima e dopo il taglio. */
@Composable
private fun FillerRow(at: Long, lenUs: Long, label: String, on: Boolean, onToggle: () -> Unit, onPlay: () -> Unit, onPlayAfter: () -> Unit) {
    Row(
        Modifier.fillMaxWidth().clip(RoundedCornerShape(12.dp)).background(VV.Raised).padding(start = 4.dp, end = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Checkbox(checked = on, onCheckedChange = { onToggle() }, colors = CheckboxDefaults.colors(checkedColor = VV.Tally, checkmarkColor = VV.TallyInk))
        Column(Modifier.weight(1f)) {
            Text(label, style = MaterialTheme.typography.bodyMedium, color = if (on) VV.Text else VV.Dim)
            Text("${formatTime(at)} · ${sec(lenUs)}", style = MaterialTheme.typography.bodySmall, color = VV.Dim)
        }
        TextButton(onClick = onPlay) { Text("▶", color = VV.Text) }
        TextButton(onClick = onPlayAfter) { Text("✂ ▶", color = VV.Tally) }
    }
}

/** "1 taglio", "3 tagli". */
private fun count(n: Int, one: String, many: String) = "$n " + if (n == 1) one else many

private fun sec(us: Long) = String.format(java.util.Locale.ITALIAN, "%.1f s", us / 1e6)
