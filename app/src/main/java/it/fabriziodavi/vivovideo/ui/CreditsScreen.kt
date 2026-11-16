package it.fabriziodavi.vivovideo.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import it.fabriziodavi.vivovideo.model.Credits
import it.fabriziodavi.vivovideo.model.creditsRoll

/** Titoli di coda: titolo, righe "ruolo e nomi", crediti dei brani, durata e dove metterli. */
@Composable
fun CreditsScreen(vm: EditorViewModel) {
    val p = vm.project ?: return
    BackHandler { vm.showCredits = false }
    val music = p.credits()
    val text = vm.creditsText()
    val suggested = (Credits.suggestedUs(text) / 1_000_000).toInt()
    val seconds = if (vm.creditsSeconds > 0) vm.creditsSeconds else suggested
    val colors = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = VV.Tally, unfocusedBorderColor = VV.Line, cursorColor = VV.Tally,
        focusedTextColor = VV.Text, unfocusedTextColor = VV.Text,
    )

    Column(Modifier.fillMaxSize().background(VV.Canvas).safeDrawingPadding()) {
        Row(Modifier.fillMaxWidth().height(52.dp).padding(horizontal = 4.dp), verticalAlignment = Alignment.CenterVertically) {
            IconBtn(VVIcons.Back, "Atrás") { vm.showCredits = false }
            Text("Créditos finales", style = MaterialTheme.typography.titleMedium, modifier = Modifier.weight(1f))
        }
        Column(
            Modifier.fillMaxWidth().weight(1f).verticalScroll(rememberScrollState()).padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Hint("Un rollo de texto que sube despacio al final del video, como en el cine. Después lo encuentras en la línea de tiempo como un texto: puedes moverlo o alargarlo.")
            PanelLabel("Título")
            OutlinedTextField(
                value = vm.creditsTitle, onValueChange = { vm.creditsTitle = it }, modifier = Modifier.fillMaxWidth(), singleLine = true,
                placeholder = { Text("Por ejemplo: Gracias por ver", color = VV.Faint) }, colors = colors,
            )
            PanelLabel("Líneas")
            vm.creditsRows.forEachIndexed { i, r ->
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    OutlinedTextField(
                        value = r.role, onValueChange = { v -> vm.creditsRows = vm.creditsRows.toMutableList().also { it[i] = r.copy(role = v) } },
                        modifier = Modifier.weight(1f), singleLine = true, placeholder = { Text("Cargo", color = VV.Faint) }, colors = colors,
                    )
                    OutlinedTextField(
                        value = r.names, onValueChange = { v -> vm.creditsRows = vm.creditsRows.toMutableList().also { it[i] = r.copy(names = v) } },
                        modifier = Modifier.weight(1.3f), singleLine = true, placeholder = { Text("Nombres", color = VV.Faint) }, colors = colors,
                    )
                    IconBtn(VVIcons.Close, "Quitar la línea") { vm.creditsRows = vm.creditsRows.filterIndexed { k, _ -> k != i } }
                }
            }
            if (vm.creditsRows.size < 20) SecondaryButton("Agregar una línea", Modifier.fillMaxWidth(), onClick = { vm.creditsRows = vm.creditsRows + Credits.Row("", "") })
            if (music.isNotEmpty()) {
                ToggleRow("Agregar los créditos de las canciones (${music.size})", vm.creditsMusic) { vm.creditsMusic = it }
                Hint("Las canciones de la biblioteca en línea piden citar al autor: así ya queda hecho.")
            }
            PanelLabel("Dónde")
            Segmented(listOf(true, false), vm.creditsOnBlack, { if (it) "Sobre negro, después del video" else "Sobre las últimas escenas" }) { vm.creditsOnBlack = it }
            LabeledSlider("Duración", null, seconds, range = 6..30, format = { "$it s" }) { vm.creditsSeconds = it }
            if (vm.creditsSeconds == 0) Hint("Duración propuesta según las líneas: aproximadamente un segundo por línea.")
            if (text.isNotBlank()) {
                PanelLabel("Vista previa del texto")
                Column(
                    Modifier.fillMaxWidth().clip(RoundedCornerShape(12.dp)).background(androidx.compose.ui.graphics.Color.Black).padding(vertical = 14.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    text.lines().forEach { l -> Text(l, style = MaterialTheme.typography.bodySmall, color = VV.Text) }
                }
            }
            Spacer(Modifier.width(1.dp))
        }
        Column(Modifier.fillMaxWidth().padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            PrimaryButton(if (p.creditsRoll != null) "Rehacer los créditos finales" else "Poner los créditos finales", Modifier.fillMaxWidth(), enabled = text.isNotBlank()) {
                if (vm.creditsSeconds == 0) vm.creditsSeconds = suggested
                vm.applyCredits()
            }
            if (p.creditsRoll != null) SecondaryButton("Quitar los créditos finales", Modifier.fillMaxWidth(), onClick = { vm.removeCredits() })
        }
    }
}
