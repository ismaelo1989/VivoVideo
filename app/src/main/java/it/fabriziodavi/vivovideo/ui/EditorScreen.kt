package it.fabriziodavi.vivovideo.ui

import it.fabriziodavi.vivovideo.model.CollageSpec
import it.fabriziodavi.vivovideo.model.creditsRoll
import it.fabriziodavi.vivovideo.model.FadeColor
import it.fabriziodavi.vivovideo.model.AutoStyle
import it.fabriziodavi.vivovideo.model.Grade
import it.fabriziodavi.vivovideo.model.GradeMath
import it.fabriziodavi.vivovideo.model.Hsl
import it.fabriziodavi.vivovideo.model.HslBand
import it.fabriziodavi.vivovideo.model.Wheel
import androidx.compose.ui.graphics.Brush
import it.fabriziodavi.vivovideo.model.FrameMode
import it.fabriziodavi.vivovideo.model.VideoFormat
import it.fabriziodavi.vivovideo.model.Orientation
import it.fabriziodavi.vivovideo.model.frameMode
import it.fabriziodavi.vivovideo.model.withMode
import it.fabriziodavi.vivovideo.model.orientation

import it.fabriziodavi.vivovideo.model.FxCategory

import it.fabriziodavi.vivovideo.model.KeyColor

import it.fabriziodavi.vivovideo.model.LayerMask

import it.fabriziodavi.vivovideo.model.FxType

import it.fabriziodavi.vivovideo.model.TextPreset

import it.fabriziodavi.vivovideo.model.SlideshowOptions

import it.fabriziodavi.vivovideo.model.MotionStyle

import it.fabriziodavi.vivovideo.engine.LayerRenderer

import it.fabriziodavi.vivovideo.model.LayerBorder

import it.fabriziodavi.vivovideo.model.Layer

import it.fabriziodavi.vivovideo.engine.TextRenderer

import androidx.compose.runtime.rememberUpdatedState

import androidx.compose.ui.platform.LocalDensity

import androidx.compose.foundation.layout.BoxWithConstraints

import androidx.compose.ui.graphics.PathEffect

import androidx.compose.ui.graphics.drawscope.rotate

import androidx.compose.material3.OutlinedTextFieldDefaults

import it.fabriziodavi.vivovideo.model.TextAnim

import it.fabriziodavi.vivovideo.model.TextLook

import it.fabriziodavi.vivovideo.model.TextFont

import it.fabriziodavi.vivovideo.model.TextItem

import it.fabriziodavi.vivovideo.model.ClipSpeed
import it.fabriziodavi.vivovideo.model.Easing
import kotlin.math.roundToInt
import it.fabriziodavi.vivovideo.model.MusicTrack
import it.fabriziodavi.vivovideo.model.SpeedInterp
import it.fabriziodavi.vivovideo.model.SpeedLimits
import it.fabriziodavi.vivovideo.model.SpeedPreset
import it.fabriziodavi.vivovideo.model.Transform
import it.fabriziodavi.vivovideo.model.TransitionSpec
import it.fabriziodavi.vivovideo.model.TransitionType
import android.content.Intent
import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import java.io.File
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.calculatePan
import androidx.compose.foundation.gestures.calculateRotation
import androidx.compose.foundation.gestures.calculateZoom
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.ui.PlayerView
import it.fabriziodavi.vivovideo.Look
import it.fabriziodavi.vivovideo.engine.ExportState
import it.fabriziodavi.vivovideo.engine.OutputCodec
import it.fabriziodavi.vivovideo.engine.OutputFps
import it.fabriziodavi.vivovideo.engine.OutputSize
import it.fabriziodavi.vivovideo.model.Clip
import it.fabriziodavi.vivovideo.model.ClipStyle
import it.fabriziodavi.vivovideo.model.StabMath
import it.fabriziodavi.vivovideo.model.PersonMode
import it.fabriziodavi.vivovideo.model.Subtitles
import it.fabriziodavi.vivovideo.model.KineticStyle
import it.fabriziodavi.vivovideo.model.ClipAttr
import it.fabriziodavi.vivovideo.engine.ExportPreset
import kotlin.math.ln

private val ZOOMS = listOf(8f, 15f, 30f, 60f, 120f)

/** Le sei voci della barra principale, sempre visibili. */
enum class ToolGroup(val label: String, val icon: () -> ImageVector) {
    EDIT("Editar", { VVIcons.Edit }),
    AUDIO("Audio", { VVIcons.Music }),
    TEXT("Texto", { VVIcons.Text }),
    LAYER("Capas", { VVIcons.Layers }),
    EFFECTS("Efectos", { VVIcons.Effects }),
    COLOR("Color", { VVIcons.Palette }),
}

/** Strumenti e pannelli. [group] è la voce della barra principale a cui appartengono. */
enum class Tool(val label: String, val icon: () -> ImageVector, val group: ToolGroup?, val needsClip: Boolean = false) {
    SPLIT("Dividir", { VVIcons.Split }, ToolGroup.EDIT),
    SPEED("Velocidad", { VVIcons.Speed }, ToolGroup.EDIT, needsClip = true),
    DURATION("Duración", { VVIcons.Speed }, ToolGroup.EDIT, needsClip = true),
    FRAME("Encuadre", { VVIcons.Fullscreen }, ToolGroup.EDIT, needsClip = true),
    KEYFRAME("Keyframe", { VVIcons.Keyframe }, ToolGroup.EDIT, needsClip = true),
    TRANSITION("Transición", { VVIcons.Transition }, ToolGroup.EDIT),
    SLIDESHOW("Slideshow", { VVIcons.Movie }, ToolGroup.EDIT),
    FORMAT("Formato", { VVIcons.Fullscreen }, ToolGroup.EDIT),
    SPORT("Datos deportivos", { VVIcons.Speed }, ToolGroup.EDIT),
    MORE("Otro", { VVIcons.Tune }, ToolGroup.EDIT, needsClip = true),
    MUSIC("Audio", { VVIcons.Music }, ToolGroup.AUDIO),
    TEXT("Texto", { VVIcons.Text }, ToolGroup.TEXT),
    LAYER("Capas", { VVIcons.Layers }, ToolGroup.LAYER),
    EFFECTS("Efectos", { VVIcons.Effects }, ToolGroup.EFFECTS, needsClip = true),
    COLOR("Color", { VVIcons.Palette }, ToolGroup.COLOR),
    EXPORT("Exportar", { VVIcons.Export }, null),
}

/** Strumenti della seconda riga di "Modifica" (Velocità diventa Durata sulle foto). */
fun editTools(photo: Boolean) = listOf(
    Tool.SPLIT, if (photo) Tool.DURATION else Tool.SPEED, Tool.FRAME, Tool.KEYFRAME, Tool.TRANSITION, Tool.SLIDESHOW, Tool.SPORT, Tool.MORE,
)

/** Pannello che si apre toccando una voce della barra principale (null = mostra la seconda riga). */
fun ToolGroup.panel(): Tool? = when (this) {
    ToolGroup.EDIT -> null
    ToolGroup.AUDIO -> Tool.MUSIC
    ToolGroup.TEXT -> Tool.TEXT
    ToolGroup.LAYER -> Tool.LAYER
    ToolGroup.EFFECTS -> Tool.EFFECTS
    ToolGroup.COLOR -> Tool.COLOR
}

/** Schede in cima ai pannelli più ricchi. */
val MUSIC_TABS = listOf("Canción", "Ritmo", "Beats", "Voz", "Clip")
val TEXT_TABS = listOf("Estilo", "Animación", "Movimiento")
val LAYER_TABS = listOf("Aspecto", "Forma", "Animación")
/** Animazioni per i livelli (quelle lettera per lettera, glitch e luccichio sono solo per i testi). */
/** Le novità premium in testa alle liste: si vedono subito. */
// L'incrociata resta la prima (è la più usata), poi le novità.
// Prima le transizioni di tendenza (stacchi da velocity edit), poi le altre novità e le classiche.
val TRANSITIONS_NEW_FIRST: List<TransitionType> = listOf(TransitionType.CROSSFADE) + TransitionType.entries.filter { it.action } +
    TransitionType.entries.filter { it.newest } +
    TransitionType.entries.filter { it.trend } + TransitionType.entries.filter { it.premium && !it.trend } +
    TransitionType.entries.filter { !it.premium && !it.trend && !it.newest && !it.action && it != TransitionType.CROSSFADE }
val PRESETS_NEW_FIRST: List<TextPreset> = TextPreset.entries.filter { it.trend } +
    TextPreset.entries.filter { !it.trend && it.ordinal >= TextPreset.CINEMA26.ordinal } + TextPreset.entries.filter { it.ordinal < TextPreset.CINEMA26.ordinal }
val ANIMS_NEW_FIRST: List<TextAnim> = listOf(TextAnim.NONE) + TextAnim.entries.filter { it.ordinal >= TextAnim.BOUNCE.ordinal } +
    TextAnim.entries.filter { it != TextAnim.NONE && it.ordinal < TextAnim.BOUNCE.ordinal }
val LAYER_ANIMS = TextAnim.entries.filter { !it.inOnly && !it.letters && it != TextAnim.GLITCH }
val EFFECT_TABS = listOf("Efectos", "Fundidos", "Persona", "Privacy")
val COLOR_TABS = listOf("Base", "Ruedas", "HSL", "Proyecto")

@Composable
fun EditorScreen(vm: EditorViewModel, initialTool: Tool? = null, initialTab: Int = 0) {
    val project = vm.project ?: return
    val context = LocalContext.current
    val running = vm.exportState is ExportState.Running
    var zoom by remember { mutableIntStateOf(2) }
    var renaming by remember { mutableStateOf(false) }
    var activeTool by remember { mutableStateOf(initialTool) }
    // Chiudendo il pannello del testo, un testo lasciato vuoto si toglie (niente riquadri vuoti).
    LaunchedEffect(activeTool) { if (activeTool != Tool.TEXT) vm.dropBlankTexts(includeSelected = true) }
    // Voce della barra principale aperta (per "Modifica" mostra la seconda riga).
    var group by remember { mutableStateOf(initialTool?.group ?: if (vm.selectedClipId != null) ToolGroup.EDIT else null) }
    var tab by remember(activeTool) { mutableIntStateOf(if (activeTool == initialTool) initialTab else 0) }
    var clipMenu by remember { mutableStateOf(false) }
    var pasteDialog by remember { mutableStateOf(false) }
    val clip = vm.selectedClip

    // Un pannello di clip si chiude se non c'è più una clip selezionata.
    if (clip == null && activeTool?.needsClip == true) activeTool = null
    // Foto e video hanno strumenti diversi.
    if (clip != null && clip.isPhoto && activeTool == Tool.SPEED) activeTool = Tool.DURATION
    if (clip != null && !clip.isPhoto && activeTool == Tool.DURATION) activeTool = Tool.SPEED
    val shownGroup = activeTool?.group ?: group

    BackHandler {
        when {
            running -> vm.cancelExport()
            activeTool != null -> activeTool = null
            group != null -> group = null
            else -> vm.closeProject()
        }
    }

    val picker = rememberLauncherForActivityResult(ActivityResultContracts.PickMultipleVisualMedia(100)) { vm.addClips(it) }
    val collagePicker = rememberLauncherForActivityResult(ActivityResultContracts.PickMultipleVisualMedia(CollageSpec.MAX_ITEMS)) { uris ->
        if (uris.isNotEmpty()) vm.openCollage(uris, intoProject = true)
    }
    val lutPicker = rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) { uri ->
        if (uri != null) vm.importLut(uri)
    }
    val musicPicker = rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) { uri ->
        if (uri != null) { vm.addMusic(uri); activeTool = Tool.MUSIC }
    }
    val pickMusic = { musicPicker.launch(arrayOf("audio/*")) }
    val layerPicker = rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) { uri ->
        if (uri != null) { vm.addLayer(uri); activeTool = Tool.LAYER }
    }
    val pickLayer = { layerPicker.launch(arrayOf("image/*", "video/*")) }
    var askAdd by remember { mutableStateOf(false) }
    val pickVideos = { askAdd = true }
    var projectMenu by remember { mutableStateOf(false) }
    var naming by remember { mutableStateOf(false) }
    var restoreAsk by remember { mutableStateOf(false) }
    var relinkOpen by remember { mutableStateOf(false) }
    var reportOpen by remember { mutableStateOf(false) }
    var backupWithMedia by remember { mutableStateOf(false) }
    val backupSaver = rememberLauncherForActivityResult(ActivityResultContracts.CreateDocument("application/octet-stream")) { uri ->
        if (uri != null) vm.exportBackup(uri, backupWithMedia)
    }
    var relinkTarget by remember { mutableStateOf<String?>(null) }
    val relinkPicker = rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) { uri ->
        val old = relinkTarget
        if (uri != null && old != null) vm.relinkManual(old, uri)
        relinkTarget = null
    }
    val fontPicker = rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) { uri -> if (uri != null) vm.addFont(uri) }
    val telePicker = rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) { uri -> if (uri != null) vm.importTelemetry(uri) }

    /** Clip su cui lavorare: quella selezionata o, se nessuna, quella sotto l'indicatore. */
    fun targetClip() = vm.selectedClip ?: project.locate(vm.playheadUs)?.let { (i, _) -> project.clips[i] }

    fun onTool(t: Tool) {
        if (t.needsClip && vm.selectedClip == null) {
            val c = targetClip()
            if (c == null) { vm.message = "Primero agrega un video o una foto."; return }
            vm.selectedClipId = c.id
        }
        when (t) {
            Tool.SPLIT -> if (vm.selectedMusicId != null) vm.splitMusicAtPlayhead() else vm.splitAtPlayhead()
            Tool.MORE -> clipMenu = true
            Tool.TEXT -> {
                activeTool = Tool.TEXT
                if (project.texts.isEmpty()) vm.addText(auto = true)
            }
            Tool.LAYER -> {
                activeTool = Tool.LAYER
                if (project.layers.isEmpty()) pickLayer()
            }
            else -> activeTool = if (activeTool == t) null else t
        }
    }

    fun onGroup(g: ToolGroup) {
        if (shownGroup == g) { activeTool = null; group = null; return }
        group = g
        activeTool = null
        g.panel()?.let { onTool(it); if (activeTool == null && it != Tool.LAYER) group = null }
    }

    val landscape = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE
    var live by remember { mutableStateOf(LiveTransform()) }
    var fullscreen by vm::fullscreen
    BackHandler(enabled = fullscreen) { fullscreen = false }

    val titleRow: @Composable (Boolean) -> Unit = { showExport ->
        Row(
            Modifier.fillMaxWidth().height(48.dp).padding(start = 2.dp, end = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconBtn(VVIcons.Back, "Proyectos", enabled = !running) { vm.closeProject() }
            Text(
                project.name,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1, overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f).clickable(enabled = !running) { renaming = true }.padding(horizontal = 4.dp),
            )
            // La guida si apre sull'argomento dello strumento aperto (senza strumenti, sull'indice).
            IconBtn(VVIcons.Help, "Guía") { vm.pause(); vm.guideTopic = GuideIndex.topicFor(activeTool); vm.showGuide = true }
            IconBtn(VVIcons.Tune, "Proyecto", enabled = !running) { vm.pause(); projectMenu = true }
            IconBtn(VVIcons.Undo, "Deshacer", enabled = vm.canUndo && !running, onClick = vm::undo)
            IconBtn(VVIcons.Redo, "Repetir", enabled = vm.canRedo && !running, onClick = vm::redo)
            if (showExport) {
                Spacer(Modifier.width(4.dp))
                ExportPill(enabled = project.clips.isNotEmpty()) { activeTool = Tool.EXPORT }
            }
        }
    }

    val playButton: @Composable (Dp) -> Unit = { size ->
        Box(
            Modifier.size(size).clip(CircleShape)
                .background(if (vm.player != null) VV.Text else VV.Raised)
                .clickable(enabled = vm.player != null, onClick = vm::togglePlay),
            contentAlignment = Alignment.Center,
        ) { Icon(if (vm.isPlaying) VVIcons.Pause else VVIcons.Play, "Reproducir", tint = VV.Canvas) }
    }
    val timecode: @Composable () -> Unit = {
        Row(verticalAlignment = Alignment.Bottom) {
            Text(formatTime(vm.playheadUs), style = MaterialTheme.typography.labelLarge, color = Color.White)
            Text("  /  " + formatTime(project.durationUs), style = MaterialTheme.typography.labelMedium, color = VV.Dim)
        }
    }

    /** Anteprima a tutto spazio; con [overlay] i comandi stanno sopra al video. */
    val preview: @Composable (Modifier, Boolean) -> Unit = { mod, overlay ->
        Box(mod.background(Color.Black), contentAlignment = Alignment.Center) {
            val player = vm.player
            when {
                player != null -> {
                    // Con i pannelli Keyframe e Inquadra aperti si può pizzicare, trascinare e ruotare il video.
                    val framing = activeTool == Tool.FRAME
                    val gestures = (activeTool == Tool.KEYFRAME || framing) && clip != null && !running
                    AndroidView(
                        factory = { ctx -> PlayerView(ctx).apply { useController = false; setShutterBackgroundColor(android.graphics.Color.BLACK) } },
                        update = { it.player = player },
                        modifier = Modifier.fillMaxSize().graphicsLayer {
                            scaleX = live.zoom; scaleY = live.zoom
                            translationX = live.pan.x; translationY = live.pan.y
                            rotationZ = live.rotation
                        },
                    )
                    // Testo selezionato: riquadro da trascinare, pizzicare e ruotare con le dita.
                    val txt = vm.selectedText
                    if (activeTool == Tool.TEXT && txt != null && !running) TextHandleOverlay(vm, txt)
                    val lay = vm.selectedLayer
                    if (activeTool == Tool.LAYER && lay != null && !running) LayerHandleOverlay(vm, lay)
                    if (gestures) {
                        val firstClip = project.clips.firstOrNull()
                        val videoAspect = if (firstClip != null && firstClip.height > 0) firstClip.width.toFloat() / firstClip.height else 16f / 9f
                        Box(
                            Modifier.fillMaxSize().pointerInput(clip?.id) {
                                awaitEachGesture {
                                    awaitFirstDown(requireUnconsumed = false)
                                    var zoom = 1f; var pan = Offset.Zero; var rot = 0f
                                    do {
                                        val ev = awaitPointerEvent()
                                        zoom *= ev.calculateZoom(); pan += ev.calculatePan(); rot += ev.calculateRotation()
                                        live = LiveTransform(zoom, pan, rot)
                                        ev.changes.forEach { it.consume() }
                                    } while (ev.changes.any { it.pressed })
                                    if (zoom != 1f || pan != Offset.Zero || rot != 0f) {
                                        // Dimensione del video dentro l'anteprima (adattato senza tagli).
                                        val vw = minOf(size.width.toFloat(), size.height * videoAspect)
                                        val vh = vw / videoAspect
                                        val base = if (framing) vm.selectedClip?.frame ?: Transform() else vm.transformAtPlayhead()
                                        val next = base.copy(
                                            x = (base.x + pan.x / vw).coerceIn(-2f, 2f),
                                            y = (base.y + pan.y / vh).coerceIn(-2f, 2f),
                                            scale = (base.scale * zoom).coerceIn(0.2f, 5f),
                                            rotation = ((base.rotation + rot + 540f) % 360f) - 180f,
                                        )
                                        if (framing) vm.setFrame(next) else vm.setTransformAtPlayhead(next)
                                    }
                                    live = LiveTransform()
                                }
                            }
                        )
                    }
                }
                project.clips.isEmpty() -> EmptyState(onAdd = pickVideos)
                running -> Text("Vista previa en pausa durante la exportación", style = MaterialTheme.typography.bodySmall)
            }
            if (vm.preparing > 0) {
                Text(
                    vm.preparingLabel.ifBlank { if (vm.preparing == 1) "Preparando un tramo de la vista previa…" else "Preparando ${vm.preparing} tramos de la vista previa…" },
                    color = Color.White, fontSize = 12.sp,
                    modifier = Modifier.align(Alignment.TopStart).padding(8.dp)
                        .background(Color(0x99000000), RoundedCornerShape(6.dp)).padding(horizontal = 8.dp, vertical = 4.dp),
                )
            }
            if (vm.missingFiles.isNotEmpty() && vm.clipWork == null) {
                Text(
                    if (vm.missingFiles.size == 1) "1 archivo no encontrado · toca para reconectarlo" else "${vm.missingFiles.size} archivos no encontrados · toca para reconectarlos",
                    color = Color.White, fontSize = 13.sp,
                    modifier = Modifier.align(Alignment.TopCenter).padding(8.dp)
                        .background(VV.Danger, RoundedCornerShape(8.dp)).clickable { relinkOpen = true }.padding(horizontal = 10.dp, vertical = 6.dp),
                )
            }
            vm.clipWork?.let { label ->
                Column(
                    Modifier.align(Alignment.Center).padding(24.dp).background(Color(0xCC000000), RoundedCornerShape(10.dp)).padding(14.dp),
                    horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Text("$label  ${(vm.clipWorkProgress * 100).toInt()}%", color = Color.White, fontSize = 13.sp)
                    LinearProgressIndicator(
                        progress = { vm.clipWorkProgress },
                        modifier = Modifier.width(200.dp).height(4.dp).clip(RoundedCornerShape(2.dp)),
                        color = VV.Tally, trackColor = VV.Raised2,
                    )
                    TextButton(onClick = vm::cancelClipWork) { Text("Deshacer", color = VV.Dim) }
                }
            }
            // Con l'avviso dei file mancanti l'errore tecnico non si mostra (sarebbero due scritte una sopra l'altra).
            vm.previewError?.takeIf { vm.missingFiles.isEmpty() }?.let {
                Text(it, color = VV.Danger, fontSize = 12.sp, modifier = Modifier.align(Alignment.TopCenter).padding(8.dp))
            }
            if (overlay) {
                Box(
                    Modifier.align(Alignment.BottomCenter).fillMaxWidth()
                        .background(androidx.compose.ui.graphics.Brush.verticalGradient(listOf(Color.Transparent, Color(0xB3000000))))
                        // Mai sotto i tasti di navigazione di Android (se visibili).
                        .windowInsetsPadding(WindowInsets.safeDrawing)
                        .padding(horizontal = 14.dp, vertical = 8.dp)
                ) {
                    Column(Modifier.fillMaxWidth()) {
                        if (project.durationUs > 0) SeekBar(vm, project.durationUs)
                        Box(Modifier.fillMaxWidth()) {
                            Box(Modifier.align(Alignment.CenterStart)) { timecode() }
                            Box(Modifier.align(Alignment.Center)) { playButton(40.dp) }
                            Box(Modifier.align(Alignment.CenterEnd)) {
                                IconBtn(if (fullscreen) VVIcons.FullscreenExit else VVIcons.Fullscreen, "Pantalla completa", tint = Color.White) { fullscreen = !fullscreen }
                            }
                        }
                    }
                }
            } else if (project.clips.isNotEmpty()) {
                Box(Modifier.align(Alignment.TopEnd).padding(4.dp)) {
                    IconBtn(VVIcons.Fullscreen, "Pantalla completa", tint = Color.White) { fullscreen = true }
                }
            }
        }
    }

    val transport: @Composable () -> Unit = {
        Box(Modifier.fillMaxWidth().height(46.dp).padding(horizontal = 12.dp)) {
            Box(Modifier.align(Alignment.CenterStart)) { timecode() }
            Box(Modifier.align(Alignment.Center)) { playButton(38.dp) }
            Row(Modifier.align(Alignment.CenterEnd), verticalAlignment = Alignment.CenterVertically) {
                // Formato del video: sempre a vista, apre le preimpostazioni.
                val fmtOn = activeTool == Tool.FORMAT
                Box(
                    Modifier.clip(RoundedCornerShape(999.dp)).background(if (fmtOn) VV.Tally else VV.Raised)
                        .clickable(enabled = !running) { group = ToolGroup.EDIT; activeTool = if (fmtOn) null else Tool.FORMAT }
                        .padding(horizontal = 10.dp, vertical = 5.dp),
                ) {
                    val (fw, fh) = vm.previewFrameSize()
                    Text(
                        if (project.format == VideoFormat.AUTO) (if (fh > fw) "9:16" else if (fw > fh) "16:9" else "1:1") else project.format.short,
                        style = MaterialTheme.typography.labelMedium, color = if (fmtOn) VV.TallyInk else VV.Text,
                    )
                }
                IconBtn(VVIcons.Remove, "Reducir zoom", tint = VV.Dim) { if (zoom > 0) zoom-- }
                IconBtn(VVIcons.Add, "Aumentar zoom", tint = VV.Dim) { if (zoom < ZOOMS.lastIndex) zoom++ }
            }
        }
    }

    val timeline: @Composable () -> Unit = {
        Timeline(
            project = project,
            playheadUs = vm.playheadUs,
            selectedClipId = vm.selectedClipId,
            dpPerSecond = ZOOMS[zoom],
            onSeek = vm::scrubTo,
            onScrubStart = vm::pause,
            multiIds = vm.multiIds,
            onSelect = {
                if (it != null && vm.multiIds != null) { vm.toggleMulti(it); return@Timeline }
                vm.selectedClipId = it
                if (it == null) { vm.selectedMusicId = null; vm.selectedTextId = null; vm.selectedLayerId = null }
                else if (activeTool == null) group = ToolGroup.EDIT
            },
            onTrim = { id, s, e -> vm.trimClip(id, s, e) },
            onAdd = pickVideos,
            selectedMusicId = vm.selectedMusicId,
            onSelectMusic = { vm.selectedMusicId = it; activeTool = Tool.MUSIC },
            onMusicChange = vm::setMusic,
            onAddMusic = pickMusic,
            selectedTextId = vm.selectedTextId,
            onSelectText = { vm.selectText(it); activeTool = Tool.TEXT },
            onTextChange = vm::setText,
            selectedLayerId = vm.selectedLayerId,
            onSelectLayer = { vm.selectLayer(it); activeTool = Tool.LAYER },
            onLayerChange = vm::setLayerItem,
            onClipLongPress = { vm.selectedClipId = it; clipMenu = true },
            onReorder = { id, to -> vm.moveClipTo(id, to) },
            onCutTap = { i -> vm.selectedClipId = project.clips[i].id; group = ToolGroup.EDIT; activeTool = Tool.TRANSITION },
        )
    }

    val photo = targetClip()?.isPhoto == true
    val subTools = editTools(photo)
    val toolbar: @Composable () -> Unit = {
        Column(Modifier.fillMaxWidth().background(VV.Canvas)) {
            val multi = vm.multiIds
            if (multi != null) {
                MultiSelectBar(vm, multi, onPaste = { pasteDialog = true })
            } else {
            if (shownGroup == ToolGroup.EDIT) {
                Row(
                    Modifier.fillMaxWidth().background(VV.Panel).padding(horizontal = 2.dp, vertical = 2.dp),
                ) {
                    subTools.forEach { t ->
                        ToolButton(t.label, t.icon(), active = activeTool == t, enabled = !running, small = true, modifier = Modifier.weight(1f)) { onTool(t) }
                    }
                }
            }
            Row(Modifier.fillMaxWidth().padding(horizontal = 2.dp, vertical = 2.dp)) {
                ToolGroup.entries.forEach { g ->
                    ToolButton(g.label, g.icon(), active = shownGroup == g, enabled = !running, modifier = Modifier.weight(1f), underline = true) { onGroup(g) }
                }
            }
            }
        }
    }

    val tabs: List<String> = when (activeTool) {
        Tool.MUSIC -> MUSIC_TABS
        Tool.TEXT -> if (vm.selectedText != null) TEXT_TABS else emptyList()
        Tool.LAYER -> if (vm.selectedLayer != null) LAYER_TABS else emptyList()
        Tool.EFFECTS -> EFFECT_TABS
        Tool.COLOR -> COLOR_TABS
        Tool.SPORT -> if (project.telemetry != null) SPORT_TABS else emptyList()
        else -> emptyList()
    }
    val panelBody: @Composable (Tool) -> Unit = { t ->
        when (t) {
            Tool.SPEED -> SpeedPanel(vm, running)
            Tool.KEYFRAME -> KeyframePanel(vm, running)
            Tool.TRANSITION -> TransitionPanel(vm, running)
            Tool.DURATION -> PhotoPanel(vm, running)
            Tool.FRAME -> FramePanel(vm, running)
            Tool.MUSIC -> MusicPanel(vm, running, tab, pickMusic)
            Tool.TEXT -> TextPanel(vm, running, tab, onPickFont = { fontPicker.launch(arrayOf("font/ttf", "font/otf", "application/x-font-ttf", "application/x-font-otf", "application/octet-stream")) })
            Tool.LAYER -> LayerPanel(vm, running, tab, pickLayer)
            Tool.SLIDESHOW -> SlideshowPanel(vm, running)
            Tool.FORMAT -> FormatPanel(vm, running)
            Tool.SPORT -> TelemetryPanel(vm, running, tab) { telePicker.launch(arrayOf("*/*")) }
            Tool.EFFECTS -> EffectsPanel(vm, running, tab)
            Tool.COLOR -> ColorPanel(vm, running, tab) { lutPicker.launch(arrayOf("*/*")) }
            Tool.EXPORT -> ExportPanel(vm) { uri ->
                val intent = Intent(Intent.ACTION_VIEW).setDataAndType(uri, "video/mp4")
                    .addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                runCatching { context.startActivity(intent) }
            }
            else -> {}
        }
    }
    val panelHeader: @Composable (Tool) -> Unit = { t ->
        Row(Modifier.fillMaxWidth().padding(start = 16.dp, end = 4.dp, top = 2.dp), verticalAlignment = Alignment.CenterVertically) {
            Text(t.label, style = MaterialTheme.typography.titleSmall, modifier = Modifier.weight(1f))
            if (clip != null && (t.needsClip || t == Tool.TRANSITION || (t == Tool.MUSIC && tab == 4))) {
                Text("Clip ${project.clips.indexOf(clip) + 1} de ${project.clips.size}", style = MaterialTheme.typography.bodySmall)
            }
            IconBtn(VVIcons.Check, "Cerrar", enabled = !running) { activeTool = null }
        }
        if (tabs.isNotEmpty()) PanelTabs(tabs, tab) { tab = it }
    }

    Column(Modifier.fillMaxSize().background(if (fullscreen) Color.Black else VV.Canvas).then(if (landscape || fullscreen) Modifier else Modifier.safeDrawingPadding())) {
        vm.busy?.let { LinearProgressIndicator(Modifier.fillMaxWidth().height(2.dp), color = VV.Tally, trackColor = VV.Canvas) }
        when {
            fullscreen -> preview(Modifier.fillMaxSize(), true)
            landscape -> {
                // Orizzontale: il video occupa tutta l'altezza disponibile; strumenti e pannelli a destra.
                Row(Modifier.fillMaxWidth().weight(1f)) {
                    preview(Modifier.weight(1f).fillMaxHeight(), true)
                    Column(Modifier.width(300.dp).fillMaxHeight().background(VV.Panel)) {
                        titleRow(false)
                        val t = activeTool
                        if (t != null) {
                            panelHeader(t)
                            Column(
                                Modifier.fillMaxWidth().weight(1f).verticalScroll(rememberScrollState()).padding(horizontal = 16.dp, vertical = 6.dp),
                                verticalArrangement = Arrangement.spacedBy(12.dp),
                            ) { panelBody(t) }
                        } else {
                            val all = subTools + Tool.FORMAT + ToolGroup.entries.mapNotNull { it.panel() }
                            ToolGrid(all, activeTool, !running, Modifier.weight(1f)) { onTool(it) }
                            Box(Modifier.fillMaxWidth().padding(10.dp), contentAlignment = Alignment.Center) {
                                ExportPill(enabled = project.clips.isNotEmpty()) { activeTool = Tool.EXPORT }
                            }
                        }
                    }
                }
                Row(Modifier.fillMaxWidth().background(VV.Panel), verticalAlignment = Alignment.CenterVertically) {
                    Box(Modifier.weight(1f)) { timeline() }
                    Column {
                        IconBtn(VVIcons.Add, "Aumentar zoom", tint = VV.Dim) { if (zoom < ZOOMS.lastIndex) zoom++ }
                        IconBtn(VVIcons.Remove, "Reducir zoom", tint = VV.Dim) { if (zoom > 0) zoom-- }
                    }
                }
            }
            else -> {
                titleRow(true)
                preview(Modifier.fillMaxWidth().weight(1f), false)
                transport()
                timeline()
                if (activeTool == null && project.auto != null) AutoBar(vm, !running)
                activeTool?.let { t ->
                    Column(Modifier.fillMaxWidth().heightIn(max = 340.dp).background(VV.Panel)) {
                        Box(Modifier.fillMaxWidth().height(1.dp).background(VV.Line))
                        panelHeader(t)
                        Column(
                            Modifier.fillMaxWidth().weight(1f, fill = false).verticalScroll(rememberScrollState()).padding(horizontal = 16.dp, vertical = 6.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                        ) { panelBody(t) }
                    }
                }
                toolbar()
            }
        }
    }

    if (vm.collage == null) vm.message?.let { msg ->
        AlertDialog(
            onDismissRequest = { vm.message = null },
            containerColor = VV.Raised,
            confirmButton = { TextButton(onClick = { vm.message = null }) { Text("OK", color = VV.Tally) } },
            text = { Text(msg, style = MaterialTheme.typography.bodyMedium) },
        )
    }
    if (askAdd) {
        AddMediaDialog(
            onPick = { picker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo)) },
            onAlbums = { vm.openAlbumPicker(newProject = false) },
            onDismiss = { askAdd = false },
            onCollage = { collagePicker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo)) },
        )
    }
    if (clipMenu) {
        val c = vm.selectedClip
        val idx = c?.let { project.clips.indexOf(it) } ?: -1
        AlertDialog(
            onDismissRequest = { clipMenu = false },
            containerColor = VV.Raised,
            title = { Text(if (c == null) "Clip" else "Clip ${idx + 1} de ${project.clips.size}", style = MaterialTheme.typography.titleSmall) },
            text = {
                Column {
                    fun act(f: () -> Unit) { f(); clipMenu = false }
                    if (c != null) {
                        MenuRow(VVIcons.MoveLeft, "Mover antes", enabled = idx > 0) { act { vm.moveSelected(-1) } }
                        MenuRow(VVIcons.MoveRight, "Mover después", enabled = idx < project.clips.lastIndex) { act { vm.moveSelected(+1) } }
                        MenuRow(VVIcons.Copy, "Duplicar") { act { vm.duplicateSelected() } }
                        MenuRow(VVIcons.Split, "Dividir en el indicador") { act { vm.splitAtPlayhead() } }
                        if (c.collage != null) MenuRow(VVIcons.Copy, "Editar collage") { act { vm.editCollage(c.id) } }
                        if (!c.isPhoto) {
                            MenuRow(VVIcons.Pause, "Congelar imagen en el indicador") { act { vm.freezeFrame() } }
                            MenuRow(VVIcons.Effects, "Mejor momento") { act { vm.bestMoment() } }
                            MenuRow(VVIcons.MoveLeft, if (c.reversed != null) "De nuevo en el sentido normal" else "Al revés") { act { vm.toggleReverse() } }
                        }
                        if (!c.isPhoto && !c.muted) MenuRow(VVIcons.Volume, "Separar el audio") { act { vm.detachAudio() } }
                        MenuRow(VVIcons.Copy, "Copiar atributos") { act { vm.copyStyle() } }
                        if (vm.copiedStyle != null && vm.copiedStyle?.id != c.id) MenuRow(VVIcons.Copy, "Pegar atributos") { act { pasteDialog = true } }
                        MenuRow(VVIcons.Check, "Seleccionar varios clips") { act { vm.startMultiSelect() } }
                    }
                    if (project.clips.size >= 2) {
                        MenuRow(VVIcons.Shuffle, if (vm.canShuffleAgain) "Mezclar otra vez" else "Mezclar todos los clips") { act { vm.shuffleClips(null) } }
                    }
                    MenuRow(VVIcons.Add, "Agregar videos y fotos") { act { pickVideos() } }
                    if (c != null) MenuRow(VVIcons.Delete, "Eliminar", tint = VV.Danger) { act { vm.deleteSelected() } }
                }
            },
            confirmButton = { TextButton(onClick = { clipMenu = false }) { Text("Cerrar", color = VV.Dim) } },
        )
    }
    if (projectMenu) {
        AlertDialog(
            onDismissRequest = { projectMenu = false },
            containerColor = VV.Raised,
            title = { Text("Proyecto", style = MaterialTheme.typography.titleSmall) },
            text = {
                Column {
                    MenuRow(VVIcons.Copy, "Guardar como plantilla") { projectMenu = false; naming = true }
                    MenuRow(VVIcons.Undo, "Volver a los videos originales") { projectMenu = false; restoreAsk = true }
                    MenuRow(VVIcons.Export, "Copia de seguridad (ligera)") {
                        projectMenu = false; backupWithMedia = false; backupSaver.launch(project.name.ifBlank { "progetto" } + ".vivo")
                    }
                    MenuRow(VVIcons.Export, "Copia de seguridad con todos los videos") {
                        projectMenu = false; backupWithMedia = true; backupSaver.launch(project.name.ifBlank { "progetto" } + ".vivo")
                    }
                    if (vm.missingFiles.isNotEmpty()) MenuRow(VVIcons.Movie, "Reconectar los archivos perdidos") { projectMenu = false; relinkOpen = true }
                    MenuRow(VVIcons.Help, "Reportar un problema") { projectMenu = false; reportOpen = true }
                    MenuRow(VVIcons.Check, "Pruebas") { projectMenu = false; vm.showTestPlan = true }
                    Hint("Ligera: el proyecto y los archivos creados por la app (fotos importadas, voz, imágenes congeladas, canciones descargadas); los videos se quedan en la galería. Con todos los videos: se abre también en otro teléfono.")
                }
            },
            confirmButton = { TextButton(onClick = { projectMenu = false }) { Text("Cerrar", color = VV.Dim) } },
        )
    }
    if (reportOpen) ReportDialog(vm, onDismiss = { reportOpen = false })
    if (restoreAsk) {
        val n = vm.originalCount()
        AlertDialog(
            onDismissRequest = { restoreAsk = false },
            containerColor = VV.Raised,
            title = { Text("¿Volver a los videos originales?", style = MaterialTheme.typography.titleSmall) },
            text = {
                Text(
                    "El proyecto vuelve a los $n videos y fotos como los importaste: completos y en el orden de importación, sin cortes, " +
                        "velocidad, color, efectos, transiciones, textos, subtítulos, música, voz ni niveles. Los archivos del teléfono no se tocan. " +
                        "Un solo Deshacer devuelve el proyecto a como está ahora.",
                    style = MaterialTheme.typography.bodyMedium,
                )
            },
            confirmButton = { TextButton(onClick = { restoreAsk = false; vm.restoreOriginals() }, enabled = n > 0) { Text("Volver a los originales", color = VV.Tally) } },
            dismissButton = { TextButton(onClick = { restoreAsk = false }) { Text("Deshacer", color = VV.Dim) } },
        )
    }
    if (naming) {
        var name by remember { mutableStateOf(project.name) }
        AlertDialog(
            onDismissRequest = { naming = false },
            containerColor = VV.Raised,
            title = { Text("Nombre de la plantilla", style = MaterialTheme.typography.titleSmall) },
            text = {
                Column {
                    OutlinedTextField(value = name, onValueChange = { name = it }, singleLine = true)
                    Hint("La plantilla guarda ${project.clips.size} espacios con duraciones, velocidades, transiciones, efectos y colores, más textos, música y capas. Después solo hay que elegir los videos.")
                }
            },
            confirmButton = { TextButton(onClick = { vm.saveTemplate(name); naming = false }) { Text("Guardar", color = VV.Tally) } },
            dismissButton = { TextButton(onClick = { naming = false }) { Text("Deshacer", color = VV.Dim) } },
        )
    }
    if (relinkOpen) {
        AlertDialog(
            onDismissRequest = { relinkOpen = false },
            containerColor = VV.Raised,
            title = { Text("Archivos no encontrados", style = MaterialTheme.typography.titleSmall) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    if (vm.missingFiles.isEmpty()) Hint("Todos los archivos están en su lugar.")
                    vm.missingFiles.forEach { u ->
                        val info = vm.missingInfo(u)
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(info.name.ifBlank { "Archivo sin nombre" }, style = MaterialTheme.typography.bodyMedium, maxLines = 1, overflow = TextOverflow.Ellipsis, modifier = Modifier.weight(1f))
                            TextButton(onClick = { relinkTarget = u; relinkPicker.launch(arrayOf("video/*", "image/*", "audio/*")) }) { Text("Elegir", color = VV.Tally) }
                        }
                    }
                    if (vm.missingFiles.isNotEmpty()) {
                        vm.relinkNote?.let { Text(it, style = MaterialTheme.typography.bodySmall, color = VV.Tally) }
                        SecondaryButton("Buscar de nuevo en el teléfono", Modifier.fillMaxWidth(), onClick = { vm.autoRelink() })
                        Hint("La app busca sola el archivo por nombre y por tamaño. Si no lo encuentra, toca «Elegir» e indícalo tú: los cortes, efectos y todo lo demás se conservan.")
                    }
                }
            },
            confirmButton = { TextButton(onClick = { relinkOpen = false }) { Text("Cerrar", color = VV.Dim) } },
        )
    }
    if (pasteDialog) {
        vm.copiedStyle?.let { src -> PasteStyleDialog(src, targets = (vm.multiIds ?: setOfNotNull(vm.selectedClipId)).count { it != src.id }, onPaste = { vm.pasteStyle(it); pasteDialog = false }, onDismiss = { pasteDialog = false }) }
            ?: run { pasteDialog = false }
    }
    if (renaming) {
        var name by remember { mutableStateOf(project.name) }
        AlertDialog(
            onDismissRequest = { renaming = false },
            containerColor = VV.Raised,
            title = { Text("Nombre del proyecto", style = MaterialTheme.typography.titleSmall) },
            text = { OutlinedTextField(value = name, onValueChange = { name = it }, singleLine = true) },
            confirmButton = { TextButton(onClick = { vm.rename(name); renaming = false }) { Text("Guardar", color = VV.Tally) } },
            dismissButton = { TextButton(onClick = { renaming = false }) { Text("Deshacer", color = VV.Dim) } },
        )
    }
}

// ---------------------------------------------------------------- pezzi comuni

@Composable
fun IconBtn(icon: ImageVector, desc: String, enabled: Boolean = true, tint: Color = VV.Text, onClick: () -> Unit) {
    Box(
        Modifier.size(42.dp).clip(RoundedCornerShape(10.dp)).clickable(enabled = enabled, onClick = onClick),
        contentAlignment = Alignment.Center,
    ) { Icon(icon, desc, tint = if (enabled) tint else VV.Faint, modifier = Modifier.size(22.dp)) }
}

@Composable
private fun ExportPill(enabled: Boolean, onClick: () -> Unit) {
    Row(
        Modifier.clip(RoundedCornerShape(999.dp)).background(if (enabled) VV.Tally else VV.Raised)
            .clickable(enabled = enabled, onClick = onClick).padding(horizontal = 14.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(VVIcons.Export, null, tint = if (enabled) VV.TallyInk else VV.Faint, modifier = Modifier.size(18.dp))
        Spacer(Modifier.width(6.dp))
        Text("Exportar", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.SemiBold, color = if (enabled) VV.TallyInk else VV.Faint)
    }
}

/** Strumenti a griglia (colonna destra in orizzontale). */
@Composable
private fun ToolGrid(tools: List<Tool>, active: Tool?, enabled: Boolean, modifier: Modifier, onClick: (Tool) -> Unit) {
    Column(modifier.fillMaxWidth().verticalScroll(rememberScrollState()).padding(horizontal = 8.dp, vertical = 4.dp)) {
        tools.chunked(4).forEach { row ->
            Row(Modifier.fillMaxWidth()) {
                row.forEach { t -> ToolButton(t.label, t.icon(), active == t, enabled, modifier = Modifier.weight(1f)) { onClick(t) } }
                repeat(4 - row.size) { Spacer(Modifier.weight(1f)) }
            }
        }
    }
}

@Composable
private fun ToolButton(
    label: String,
    icon: ImageVector,
    active: Boolean,
    enabled: Boolean,
    modifier: Modifier = Modifier,
    small: Boolean = false,
    underline: Boolean = false,
    onClick: () -> Unit,
) {
    val color = when {
        !enabled -> VV.Faint
        active -> VV.Tally
        else -> VV.Text
    }
    Column(
        modifier.clip(RoundedCornerShape(10.dp)).clickable(enabled = enabled, onClick = onClick).padding(top = if (small) 5.dp else 7.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(icon, label, tint = color, modifier = Modifier.size(if (small) 20.dp else 23.dp))
        Spacer(Modifier.height(4.dp))
        Text(
            label, fontSize = if (small) 10.sp else 11.sp, fontFamily = Plex, maxLines = 1, softWrap = false,
            fontWeight = if (active && underline) FontWeight.SemiBold else FontWeight.Normal,
            color = if (active) VV.Tally else if (!enabled) VV.Faint else VV.Dim,
        )
        Spacer(Modifier.height(if (small) 5.dp else 5.dp))
        Box(Modifier.width(18.dp).height(2.dp).clip(RoundedCornerShape(1.dp)).background(if (active && underline) VV.Tally else Color.Transparent))
    }
}

/** Barra di avanzamento sopra al video (schermo intero e orizzontale): trascinandola si sposta l'indicatore. */
@Composable
private fun SeekBar(vm: EditorViewModel, durationUs: Long) {
    var dragging by remember { mutableStateOf<Float?>(null) }
    val pos = dragging ?: (vm.playheadUs.toFloat() / durationUs).coerceIn(0f, 1f)
    androidx.compose.material3.Slider(
        value = pos,
        onValueChange = { v ->
            if (dragging == null) vm.pause()
            dragging = v
            vm.scrubTo((v * durationUs).toLong())
        },
        onValueChangeFinished = { vm.endScrub(); dragging = null },
        modifier = Modifier.fillMaxWidth().height(28.dp).semantics { contentDescription = "Avance" },
        colors = androidx.compose.material3.SliderDefaults.colors(
            thumbColor = VV.Tally, activeTrackColor = VV.Tally, inactiveTrackColor = Color(0x66FFFFFF),
        ),
    )
}

/**
 * Barra del video automatico: quale proposta è, "Altra proposta" (scene, tagli ed effetti nuovi)
 * e "Stile" per rifarla con un'altra regia. Annulla torna alla proposta di prima.
 */
@Composable
private fun AutoBar(vm: EditorViewModel, enabled: Boolean) {
    val r = vm.project?.auto ?: return
    var choose by remember { mutableStateOf(false) }
    val idle = enabled && vm.busy == null
    Row(
        Modifier.fillMaxWidth().background(VV.Panel).padding(start = 14.dp, end = 8.dp, top = 6.dp, bottom = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(VVIcons.Effects, null, tint = VV.Tally, modifier = Modifier.size(18.dp))
        Spacer(Modifier.width(8.dp))
        Column(Modifier.weight(1f)) {
            Text("Proposta ${r.round}", style = MaterialTheme.typography.labelLarge)
            Text(r.options.style.label, style = MaterialTheme.typography.bodySmall)
        }
        Box(
            Modifier.clip(RoundedCornerShape(999.dp)).background(VV.Raised).clickable(enabled = idle) { choose = true }
                .padding(horizontal = 14.dp, vertical = 8.dp),
        ) { Text("Estilo", style = MaterialTheme.typography.labelLarge) }
        Spacer(Modifier.width(8.dp))
        Box(
            Modifier.clip(RoundedCornerShape(999.dp)).background(if (idle) VV.Tally else VV.Raised).clickable(enabled = idle) { vm.anotherProposal() }
                .padding(horizontal = 14.dp, vertical = 8.dp),
        ) { Text("Otra propuesta", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.SemiBold, color = if (idle) VV.TallyInk else VV.Faint) }
    }
    if (choose) {
        AlertDialog(
            onDismissRequest = { choose = false },
            containerColor = VV.Raised,
            title = { Text("Rehacer con el estilo…", style = MaterialTheme.typography.titleSmall) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    AutoStyle.entries.forEach { st ->
                        val on = st == r.options.style
                        Column(
                            Modifier.fillMaxWidth().clip(RoundedCornerShape(10.dp)).background(if (on) VV.Raised2 else VV.Panel)
                                .border(if (on) 2.dp else 0.dp, if (on) VV.Tally else Color.Transparent, RoundedCornerShape(10.dp))
                                .clickable { choose = false; vm.anotherProposal(st) }.padding(10.dp),
                        ) {
                            Text(st.label, style = MaterialTheme.typography.labelLarge, color = if (on) VV.Tally else VV.Text)
                            Text(st.description, style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            },
            confirmButton = { TextButton(onClick = { choose = false }) { Text("Cerrar", color = VV.Dim) } },
        )
    }
}

/** Crediti dei brani presi in rete, da copiare nella descrizione del video. */
@Composable
private fun CreditsBox(credits: List<String>) {
    if (credits.isEmpty()) return
    val ctx = LocalContext.current
    val text = "Música y efectos:\n" + credits.joinToString("\n")
    Column(
        Modifier.fillMaxWidth().clip(RoundedCornerShape(12.dp)).background(VV.Raised).padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        Text("Créditos para poner en la descripción del video", style = MaterialTheme.typography.labelLarge, color = VV.Tally)
        SelectionContainer { Text(credits.joinToString("\n"), style = MaterialTheme.typography.bodySmall) }
        SecondaryButton("Copiar los créditos", onClick = {
            val cm = ctx.getSystemService(android.content.ClipboardManager::class.java)
            cm?.setPrimaryClip(android.content.ClipData.newPlainText("Créditos", text))
        })
    }
}

/** Schede in cima a un pannello. */
@Composable
private fun PanelTabs(tabs: List<String>, selected: Int, onSelect: (Int) -> Unit) {
    Row(Modifier.fillMaxWidth().padding(horizontal = 12.dp)) {
        tabs.forEachIndexed { i, label ->
            val on = i == selected
            Column(
                Modifier.weight(1f).clip(RoundedCornerShape(8.dp)).clickable { onSelect(i) }.padding(top = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    label, style = MaterialTheme.typography.labelLarge, maxLines = 1,
                    color = if (on) VV.Text else VV.Dim, fontWeight = if (on) FontWeight.SemiBold else FontWeight.Normal,
                )
                Spacer(Modifier.height(7.dp))
                Box(Modifier.fillMaxWidth().height(2.dp).background(if (on) VV.Tally else VV.Line))
            }
        }
    }
}

@Composable
private fun MenuRow(icon: ImageVector, label: String, enabled: Boolean = true, tint: Color = VV.Text, onClick: () -> Unit) {
    Row(
        Modifier.fillMaxWidth().clip(RoundedCornerShape(10.dp)).clickable(enabled = enabled, onClick = onClick)
            .padding(horizontal = 8.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(icon, null, tint = if (enabled) tint else VV.Faint, modifier = Modifier.size(20.dp))
        Spacer(Modifier.width(14.dp))
        Text(label, style = MaterialTheme.typography.bodyLarge, color = if (enabled) tint else VV.Faint)
    }
}

@Composable
private fun EmptyState(onAdd: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            Modifier.size(52.dp).clip(CircleShape).border(1.dp, VV.Line, CircleShape).clickable(onClick = onAdd),
            contentAlignment = Alignment.Center,
        ) { Icon(VVIcons.Add, "Agregar videos y fotos", tint = VV.Text) }
        Spacer(Modifier.height(10.dp))
        Text("Agrega videos y fotos para editar", style = MaterialTheme.typography.bodySmall)
    }
}

// ---------------------------------------------------------------- pannelli

@Composable
private fun SpeedPanel(vm: EditorViewModel, running: Boolean) {
    val clip = vm.selectedClip ?: return
    val project = vm.project ?: return
    Segmented(listOf(ClipSpeed.SLOW, ClipSpeed.NORMAL, ClipSpeed.FAST, ClipSpeed.RAMP), clip.speed, { it.label }, !running) { s ->
        vm.updateSelected { it.copy(speed = s) }
    }
    if (clip.speed == ClipSpeed.RAMP) {
        // Posizione dell'indicatore dentro questa clip (sul tempo originale), per orientarsi sulla curva.
        val start = project.clipStartsUs()[project.clips.indexOf(clip)]
        val inside = vm.playheadUs - start
        val frac = if (inside in 0..clip.outputUs) clip.speedCurve().sourceTimeAt(inside).toFloat() / clip.trimmedUs else null
        // Punti velocità sul fotogramma esatto: si sceglie il punto sulla timeline e lì si fissa la velocità.
        val here = vm.speedPointAtPlayhead()
        val f = vm.playheadFraction()
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconBtn(VVIcons.MoveLeft, "Punto anterior") { vm.jumpSpeedPoint(-1) }
            Box(
                Modifier.weight(1f).clip(RoundedCornerShape(12.dp)).background(if (here != null) VV.Tally else VV.Raised)
                    .clickable(enabled = !running) { if (f == null) vm.seekToSelectedStart() else vm.toggleSpeedPointAtPlayhead() }.padding(vertical = 11.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    when {
                        f == null -> "Ir al inicio del clip"
                        here == null -> "Agregar punto de velocidad aquí"
                        here.t == 0f || here.t == 1f -> "Punto de " + (if (here.t == 0f) "inizio" else "fine") + " clip"
                        else -> "Quitar el punto"
                    },
                    style = MaterialTheme.typography.labelLarge, color = if (here != null) VV.TallyInk else VV.Text,
                )
            }
            IconBtn(VVIcons.MoveRight, "Punto siguiente") { vm.jumpSpeedPoint(+1) }
        }
        if (f != null) {
            val now = here?.speed ?: SpeedInterp.valueAt(clip.curvePoints, f)
            val lnMin = kotlin.math.ln(SpeedLimits.MIN)
            val lnMax = kotlin.math.ln(SpeedLimits.MAX)
            fun toSlider(sp: Float) = ((kotlin.math.ln(sp) - lnMin) / (lnMax - lnMin) * 1000).toInt()
            fun fromSlider(v: Int): Float {
                val sp = kotlin.math.exp(lnMin + v / 1000f * (lnMax - lnMin))
                return if (kotlin.math.abs(sp - 1f) < 0.04f) 1f else sp
            }
            LabeledSlider(
                "Velocidad en este punto", null, toSlider(now), !running, range = 0..1000,
                format = { "%.2f×".format(fromSlider(it)).replace('.', ',') },
            ) { v -> vm.setSpeedAtPlayhead(fromSlider(v)) }
        }
        SpeedCurveEditor(
            points = clip.curvePoints,
            playFraction = frac,
            enabled = !running,
            onChange = { pts -> vm.updateSelected { it.copy(speedPoints = pts) } },
        )
        Row(Modifier.horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            SpeedPreset.entries.forEach { preset ->
                val on = clip.curvePoints == preset.points
                Box(
                    Modifier.clip(RoundedCornerShape(999.dp)).background(if (on) VV.Tally else VV.Raised)
                        .clickable(enabled = !running) { vm.updateSelected { it.copy(speedPoints = preset.points) } }
                        .padding(horizontal = 14.dp, vertical = 8.dp),
                ) { Text(preset.label, style = MaterialTheme.typography.labelLarge, color = if (on) VV.TallyInk else VV.Text) }
            }
        }
        Hint("Desplaza la línea de tiempo hasta el fotograma exacto y agrega ahí un punto, o arrastra los puntos en la gráfica (toca un espacio vacío para agregar, doble toque para quitar). Para empezar de cero usa \"Plana 1×\". Duración: " + formatTime(clip.outputUs))
    } else {
        Hint(
            when (clip.speed) {
                ClipSpeed.SLOW -> "Mitad de velocidad en todo el clip."
                ClipSpeed.FAST -> "Doble velocidad en todo el clip."
                else -> "Velocidad original. Elige \"Curva\" para dibujar un speed ramp."
            } + "  Durata: " + formatTime(clip.outputUs)
        )
    }
    TimelapseSection(vm, clip, running)
}

/**
 * Time-lapse: la clip da 5 a 100 volte più veloce, con le scie dei passanti (più fotogrammi mescolati in uno),
 * stabilizzata se ripresa camminando, con l'audio del posto e un lampo per uscire sulla scena dopo.
 */
@Composable
private fun TimelapseSection(vm: EditorViewModel, clip: it.fabriziodavi.vivovideo.model.Clip, running: Boolean) {
    if (clip.isPhoto) return
    PanelLabel("Time-lapse")
    val tl = clip.timelapse
    if (tl != null) {
        Hint("Time-lapse ${tl.speed}× · estelas ${tl.trails.label.lowercase()}${if (tl.moving) " · estabilizado" else ""}${if (tl.ambient) " · audio del lugar" else ""}. Duración: ${formatTime(clip.outputUs)}.")
        SecondaryButton("Volver al video original", Modifier.fillMaxWidth()) { vm.undoTimelapse() }
    }
    val srcUs = tl?.source?.let { it.trimEndUs - it.trimStartUs } ?: clip.trimmedUs
    var speed by remember(clip.id) { mutableIntStateOf(tl?.speed ?: 20) }
    var trails by remember(clip.id) { mutableStateOf(tl?.trails ?: it.fabriziodavi.vivovideo.model.TimelapseTrails.LONG) }
    var moving by remember(clip.id) { mutableStateOf(tl?.moving ?: false) }
    var ambient by remember(clip.id) { mutableStateOf(tl?.ambient ?: false) }
    var flash by remember(clip.id) { mutableStateOf(false) }
    PillRow(it.fabriziodavi.vivovideo.model.TimelapsePlan.SPEEDS, speed, { "$it×" }, !running) { speed = it }
    Hint("${formatTime(srcUs)} se convierten en ${formatTime(srcUs / speed)}." + if (srcUs / speed < 1_000_000) " Demasiado corto: elige menos velocidad." else "")
    Text("Estelas de quien se mueve", style = MaterialTheme.typography.labelMedium, color = VV.Dim)
    Segmented(it.fabriziodavi.vivovideo.model.TimelapseTrails.entries, trails, { it.label }, !running) { trails = it }
    ToggleRow("Toma caminando o en coche (estabiliza: hyperlapse)", moving, !running) { moving = it }
    ToggleRow("Conservar el sonido del lugar, bajo", ambient, !running) { ambient = it }
    ToggleRow("Salir con un destello a la escena siguiente", flash, !running) { flash = it }
    PrimaryButton(if (tl == null) "Crear el time-lapse" else "Rehacer el time-lapse", Modifier.fillMaxWidth(), enabled = !running && srcUs / speed >= 500_000) {
        vm.makeTimelapse(speed, trails, ambient, moving, flash)
    }
    Hint("Para el efecto «transeúntes que se vuelven estelas»: teléfono fijo (mejor sobre un apoyo), una calle o una plaza, al menos un minuto de grabación, 20× o 50× con estelas largas.")
}

/** Gesto in corso sul video, mostrato subito; il keyframe si salva quando si alzano le dita. */
private data class LiveTransform(val zoom: Float = 1f, val pan: Offset = Offset.Zero, val rotation: Float = 0f)

@Composable
private fun KeyframePanel(vm: EditorViewModel, running: Boolean) {
    val clip = vm.selectedClip ?: return
    val inside = vm.playheadInSelected() != null
    val here = vm.keyframeAtPlayhead()
    val v = vm.transformAtPlayhead()
    val enabled = !running && inside
    Row(verticalAlignment = Alignment.CenterVertically) {
        IconBtn(VVIcons.MoveLeft, "Keyframe anterior", enabled = clip.keyframes.isNotEmpty()) { vm.jumpKeyframe(-1) }
        Box(
            Modifier.weight(1f).clip(RoundedCornerShape(12.dp)).background(if (here != null) VV.Tally else VV.Raised)
                .clickable(enabled = enabled) { vm.toggleKeyframeAtPlayhead() }.padding(vertical = 11.dp),
            contentAlignment = Alignment.Center,
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(VVIcons.Keyframe, null, tint = if (here != null) VV.TallyInk else VV.Text, modifier = Modifier.size(18.dp))
                Spacer(Modifier.width(8.dp))
                Text(
                    if (here != null) "Quitar el keyframe" else "Agregar keyframe aquí",
                    style = MaterialTheme.typography.labelLarge, color = if (here != null) VV.TallyInk else VV.Text,
                )
            }
        }
        IconBtn(VVIcons.MoveRight, "Keyframe siguiente", enabled = clip.keyframes.isNotEmpty()) { vm.jumpKeyframe(+1) }
    }
    if (!inside) {
        Hint("Lleva el indicador dentro del clip seleccionado.")
        return
    }
    Hint(
        if (clip.keyframes.isEmpty()) "Pellizca, arrastra o gira el video con dos dedos, o usa los controles deslizantes: el keyframe se crea solo."
        else "${clip.keyframes.size} keyframes en este clip. Mueve el indicador y cambia los valores: cada cambio crea o actualiza un keyframe."
    )
    LabeledSlider("Zoom", null, (v.scale * 100).toInt(), enabled, range = 20..400, format = { "$it%" }) {
        vm.setTransformAtPlayhead(v.copy(scale = it / 100f))
    }
    LabeledSlider("Horizontal", null, (v.x * 100).toInt(), enabled, range = -100..100) {
        vm.setTransformAtPlayhead(v.copy(x = it / 100f))
    }
    LabeledSlider("Vertical", null, (v.y * 100).toInt(), enabled, range = -100..100) {
        vm.setTransformAtPlayhead(v.copy(y = it / 100f))
    }
    LabeledSlider("Rotación", null, v.rotation.toInt(), enabled, range = -180..180, format = { "$it°" }) {
        vm.setTransformAtPlayhead(v.copy(rotation = it.toFloat()))
    }
    LabeledSlider("Opacidad", "Debajo del video está el negro: bajándola se consigue un fundido.", (v.opacity * 100).toInt(), enabled, format = { "$it%" }) {
        vm.setTransformAtPlayhead(v.copy(opacity = it / 100f))
    }
    if (clip.keyframes.size >= 2) {
        PanelLabel("Curva hacia el siguiente keyframe")
        val t = vm.playheadInSelected() ?: 0L
        val current = clip.keyframes.filter { it.timeUs <= t + 40_000 }.maxByOrNull { it.timeUs }?.easing
        Segmented(Easing.entries, current ?: Easing.SMOOTH, { it.label }, !running && current != null) {
            vm.setEasingAtPlayhead(it)
        }
    }
    if (clip.keyframes.isNotEmpty()) {
        SecondaryButton("Quitar todos los keyframes del clip", onClick = vm::clearKeyframes)
    }
}

@Composable
private fun TransitionPanel(vm: EditorViewModel, running: Boolean) {
    val p = vm.project ?: return
    // Nessuna clip selezionata: prende quella sotto l'indicatore.
    if (vm.selectedClip == null) {
        LaunchedEffect(Unit) {
            p.locate(vm.playheadUs)?.let { (i, _) -> vm.selectedClipId = p.clips[i].id }
        }
    }
    val clip = vm.selectedClip ?: return
    val index = p.clips.indexOfFirst { it.id == clip.id }
    if (index < 0 || index == p.clips.lastIndex) {
        Hint("No hay ningún clip después de este: la transición se aplica entre el clip seleccionado y el siguiente.")
        return
    }
    val spec = clip.transitionOut
    PanelLabel("Hacia el clip ${index + 2}")
    val options: List<TransitionType?> = listOf(null) + TRANSITIONS_NEW_FIRST
    PillRow(options, spec?.type, { it?.label ?: "Ninguna" }, !running) { t ->
        // Le transizioni nuove partono con la loro durata (le tendine a 0,5 s passavano in un lampo).
        vm.setTransition(t?.let { TransitionSpec(it, it.defaultUs ?: spec?.durationUs ?: 500_000L) })
    }
    if (spec != null) {
        val ms = (spec.durationUs / 1000).toInt()
        LabeledSlider(
            "Duración", null, ms / 100, !running, range = 2..20,
            format = { String.format(java.util.Locale.ITALIAN, "%.1f s", it / 10f) },
        ) { v -> vm.setTransition(spec.copy(durationUs = v * 100_000L)) }
        val cross = spec.type.composite
        val real = if (cross) p.crossfades().firstOrNull { it.index == index }?.let { it.beforeUs + it.afterUs } ?: 0L
            else p.transitionHalfUs(index) * 2
        if (spec.type == TransitionType.MASK_PASS && clip.person == null) {
            Hint("Este clip todavía no tiene el recorte de la persona: hazlo en Efectos → Persona (con cualquier fondo), y la persona se quedará delante en la transición.")
        } else if (real < spec.durationUs) {
            Hint(
                if (cross && real == 0L) "No hay video más allá del corte que usar: alarga los clips con los tiradores (o elige otra transición)."
                else String.format(java.util.Locale.ITALIAN, if (cross) "Poco video más allá del corte: duración efectiva %.1f s." else "Clips cortos: duración efectiva %.1f s.", real / 1_000_000f)
            )
        }
        SecondaryButton("Aplicar a todos los cortes", selected = p.transitionOnAllCuts(spec), onClick = { vm.setTransitionAll(spec) })
    }
    Hint(spec?.type?.hint ?: "La mitad del efecto al final de este clip, la otra mitad al inicio del siguiente.")
}

@Composable
private fun MusicPanel(vm: EditorViewModel, running: Boolean, tab: Int, onAdd: () -> Unit) {
    val p = vm.project ?: return
    val enabled = !running
    // Mixer: tutti i suoni del progetto in una schermata (volume, bilanciamento, equalizzatore, compressore, uscita).
    PrimaryButton("Mezclador de audio", Modifier.fillMaxWidth(), enabled = enabled) { vm.openMixer() }
    if (tab == 4) { ClipAudioTab(vm, running); return }
    if (tab == 3) { VoiceTab(vm, running); return }
    // Nessun brano selezionato: prende quello sotto l'indicatore (o il primo).
    if (vm.selectedMusic == null && p.music.isNotEmpty()) {
        LaunchedEffect(Unit) {
            val m = p.music.firstOrNull { vm.playheadUs >= it.startUs && vm.playheadUs < it.endUs } ?: p.music.first()
            vm.selectedMusicId = m.id
        }
    }
    val t = vm.selectedMusic
    if (t == null) {
        Hint("Elige una canción del teléfono (MP3, M4A, WAV, FLAC, también MIDI) o de la biblioteca de música libre: empieza en el punto del indicador y se detiene al final del video.")
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            PrimaryButton("Biblioteca en línea", Modifier.weight(1f), enabled = enabled) { vm.openLibrary(effects = false) }
            SecondaryButton("Del teléfono", Modifier.weight(1f), onClick = { if (enabled) onAdd() })
        }
        SecondaryButton("Efectos de sonido", Modifier.fillMaxWidth(), onClick = { if (enabled) vm.openLibrary(effects = true) })
        return
    }
    val total = p.durationUs
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(VVIcons.Music, null, tint = VV.Music, modifier = Modifier.size(16.dp))
        Spacer(Modifier.width(8.dp))
        Text(t.name, style = MaterialTheme.typography.bodyMedium, maxLines = 1, overflow = TextOverflow.Ellipsis, modifier = Modifier.weight(1f))
        Text(formatTime(t.playUs(total)), style = MaterialTheme.typography.bodySmall)
    }
    when (tab) {
        0 -> {
            if (p.music.size > 1) PillRow(p.music, t, { it.name.take(18) }, enabled) { m -> vm.selectedMusicId = m.id }
            LabeledSlider("Volume", null, (t.volume * 100).toInt(), enabled, range = 0..200, format = { "$it%" }) { v ->
                vm.updateMusic { it.copy(volume = v / 100f) }
            }
            val secs = { v: Int -> String.format(java.util.Locale.ITALIAN, "%.1f s", v / 10f) }
            LabeledSlider("Fundido de entrada", null, (t.fadeInUs / 100_000).toInt(), enabled, range = 0..100, format = secs) { v ->
                vm.updateMusic { it.copy(fadeInUs = v * 100_000L) }
            }
            LabeledSlider("Fundido de salida", null, (t.fadeOutUs / 100_000).toInt(), enabled, range = 0..100, format = secs) { v ->
                vm.updateMusic { it.copy(fadeOutUs = v * 100_000L) }
            }
            val maxOffset = ((t.sourceDurationUs - MusicTrack.MIN_US) / 100_000).toInt().coerceAtLeast(0)
            LabeledSlider(
                "Empezar desde el punto de la canción", "Para que la música empiece desde el estribillo o desde otro punto.",
                (t.offsetUs / 100_000).toInt().coerceAtMost(maxOffset), enabled && maxOffset > 0, range = 0..maxOf(maxOffset, 1),
                format = { formatTime(it * 100_000L) },
            ) { v ->
                vm.updateMusic { m ->
                    val off = v * 100_000L
                    m.copy(offsetUs = off, lengthUs = minOf(m.lengthUs, m.sourceDurationUs - off).coerceAtLeast(MusicTrack.MIN_US))
                }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                SecondaryButton("Empezar desde aquí", onClick = {
                    if (enabled) vm.updateMusic { it.copy(startUs = vm.playheadUs.coerceIn(0, (total - MusicTrack.MIN_US).coerceAtLeast(0))) }
                })
                SecondaryButton("Hasta el final", onClick = {
                    if (enabled) vm.updateMusic { it.copy(lengthUs = (it.sourceDurationUs - it.offsetUs).coerceAtLeast(MusicTrack.MIN_US)) }
                })
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                SecondaryButton("Dividir aquí", onClick = { if (enabled) vm.splitMusicAtPlayhead() })
                SecondaryButton("Eliminar canción", onClick = { if (enabled) vm.deleteMusic() })
            }
            PanelLabel("Voces y música")
            ToggleRow("Bajar la música cuando se habla", p.duck, enabled) { vm.setDuck(on = it) }
            if (p.duck) LabeledSlider("Cuánto bajarla", "En la vista previa, bajo la voz en off; en el video exportado también donde se habla en los clips.", p.duckDb, enabled, range = 4..24, format = { "–$it dB" }) { vm.setDuck(db = it) }
            PanelLabel("Agregar")
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                SecondaryButton("Biblioteca", Modifier.weight(1f), onClick = { if (enabled) vm.openLibrary(effects = false) })
                SecondaryButton("Efectos de sonido", Modifier.weight(1f), onClick = { if (enabled) vm.openLibrary(effects = true) })
            }
            SecondaryButton("Canción del teléfono", Modifier.fillMaxWidth(), onClick = { if (enabled) onAdd() })
            CreditsBox(p.credits())
            Hint("En la línea de tiempo: toca la canción para seleccionarla, arrástrala para moverla, usa los tiradores para acortarla. Los fundidos se oyen también en la vista previa.")
        }
        1 -> {
            if (t.beatsUs.isEmpty()) {
                PrimaryButton("Detectar los beats automáticamente", Modifier.fillMaxWidth(), enabled = enabled, onClick = vm::detectBeats)
                Hint("En los archivos MIDI los beats ya son exactos. También puedes marcarlos a mano en la pestaña Beats.")
            } else {
                Text("${t.bpm.roundToInt()} BPM · ${t.timelineBeats(total).size} beats en el video", style = MaterialTheme.typography.bodyMedium)
                Segmented(listOf(1, 2, 4), t.beatEvery, { if (it == 1) "Todos" else "Ogni $it" }, enabled) { n ->
                    vm.updateBeats { it.copy(beatEvery = n) }
                }
                LabeledSlider(
                    "Mover los beats automáticos", "Si caen todos un poco antes o después del golpe, corrígelos aquí.",
                    (t.beatShiftUs / 10_000).toInt(), enabled, range = -30..30,
                    format = { (if (it > 0) "+" else "") + "${it * 10} ms" },
                ) { v -> vm.updateBeats { it.copy(beatShiftUs = v * 10_000L) } }
                SecondaryButton("Tiempo fuerte ↔ contratiempo (mueve medio beat)", Modifier.fillMaxWidth(), onClick = { if (enabled) vm.flipBeatPhase() })
                Hint("Si los cortes van a tempo pero caen entre un golpe y otro (en los platillos en lugar del bombo), toca aquí; vuelve a tocar para regresar.")
            }
            if (t.hasBeats) {
                PanelLabel("Cortes al ritmo")
                SecondaryButton("Alinear los cortes al ritmo", Modifier.fillMaxWidth(), onClick = { if (enabled) vm.alignCutsToBeats() })
                SecondaryButton("Dividir los videos en los beats", Modifier.fillMaxWidth(), onClick = { if (enabled) vm.splitOnBeats() })
                ToggleRow("Ajustar los cortes a los beats", vm.snapToBeats, enabled) { vm.snapToBeats = it }
                SecondaryButton("Borrar todos los beats", onClick = { if (enabled) vm.clearBeats() })
            }
        }
        else -> {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                SecondaryButton("Marcar / quitar aquí", onClick = { if (enabled) vm.toggleBeatAtPlayhead() })
                // Tap: si preme play e si tocca a tempo di musica.
                Box(
                    Modifier.weight(1f).clip(RoundedCornerShape(12.dp))
                        .background(if (vm.isPlaying) VV.Tally else VV.Raised)
                        .clickable(enabled = enabled) { if (vm.isPlaying) vm.tapBeat() else vm.togglePlay() }
                        .padding(vertical = 13.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        if (vm.isPlaying) "TAP al compás" else "▶ Escucha y toca",
                        style = MaterialTheme.typography.labelLarge,
                        color = if (vm.isPlaying) VV.TallyInk else VV.Text,
                    )
                }
            }
            if (t.hasBeats) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    SecondaryButton("◀ Beat", onClick = { vm.jumpBeat(-1) })
                    SecondaryButton("Beat ▶", onClick = { vm.jumpBeat(+1) })
                }
            }
            ToggleRow("Cortar los clips en cada toque", vm.tapCuts, enabled) { vm.tapCuts = it }
            if (vm.pendingCutCount > 0) Text("${vm.pendingCutCount} cortes listos: se hacen en la pausa", style = MaterialTheme.typography.labelMedium, color = VV.Tally)
            Hint("Marcar: agrega un beat en el punto del indicador, o quita el que ya está (también el automático). Tap: inicia la escucha y toca el botón en cada golpe; los beats a mano son blancos. Con \"Cortar los clips\" activado, cuando pones pausa el video se corta en cada punto tocado (un solo Deshacer los quita todos).")
        }
    }
}

/** Scheda "Clip" del pannello Audio: volume della clip sotto l'indicatore. */
/** Voce fuori campo: si registra mentre l'anteprima scorre dall'indicatore. */
@Composable
private fun VoiceTab(vm: EditorViewModel, running: Boolean) {
    val ctx = LocalContext.current
    val p = vm.project ?: return
    val perm = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { ok ->
        if (ok) vm.startVoice() else vm.message = "Para grabar la voz hace falta el permiso del micrófono."
    }
    Hint("Lleva el indicador adonde quieras empezar, toca Grabar y habla mientras el video avanza; toca de nuevo para detener. La voz se vuelve una fila en la línea de tiempo, sincronizada.")
    Box(
        Modifier.fillMaxWidth().clip(RoundedCornerShape(14.dp)).background(if (vm.recording) VV.Danger else VV.Tally)
            .clickable(enabled = !running) {
                when {
                    vm.recording -> vm.stopVoice()
                    ctx.checkSelfPermission(android.Manifest.permission.RECORD_AUDIO) == android.content.pm.PackageManager.PERMISSION_GRANTED -> vm.startVoice()
                    else -> perm.launch(android.Manifest.permission.RECORD_AUDIO)
                }
            }.padding(vertical = 16.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            if (vm.recording) "■  Detener la grabación  ${formatTime(vm.playheadUs)}" else "●  Grabar la voz",
            style = MaterialTheme.typography.titleSmall, color = if (vm.recording) Color.White else VV.TallyInk,
        )
    }
    ToggleRow("Silenciar la vista previa mientras grabo", vm.muteWhileRecording, !vm.recording) { vm.muteWhileRecording = it }
    Hint("Con audífonos puedes dejar el audio encendido y oír el video mientras hablas; sin audífonos es mejor silenciarlo, así el micrófono graba solo tu voz.")
    ToggleRow("Bajar la música cuando se habla", p.duck, !running) { vm.setDuck(on = it) }
    val voices = p.music.filter { it.dialog }
    if (voices.isNotEmpty()) {
        PanelLabel("Voces y audio separado")
        PillRow(voices, vm.selectedMusic?.takeIf { it.dialog }, { it?.name ?: "" }, !running) { v -> if (v != null) vm.selectedMusicId = v.id }
        Hint("Con una voz seleccionada, en la pestaña Canción ajustas su volumen, fundidos, división y eliminación.")
    }
}

@Composable
private fun ClipAudioTab(vm: EditorViewModel, running: Boolean) {
    val p = vm.project ?: return
    if (vm.selectedClip == null) {
        LaunchedEffect(Unit) { p.locate(vm.playheadUs)?.let { (i, _) -> vm.selectedClipId = p.clips[i].id } }
    }
    val clip = vm.selectedClip
    if (clip == null) { Hint("Ningún clip en el proyecto."); return }
    // Per i video parlati: toglie silenzi e "ehm" da tutto il progetto.
    SecondaryButton("Quitar pausas y «ehm»", Modifier.fillMaxWidth(), onClick = { if (!running) { vm.pause(); vm.speechPlan = null; vm.jumpMode = false; vm.showSpeechCut = true } })
    // Jump cut pronti: come sopra, e a ogni taglio cambia l'inquadratura (stili Pulito, Dinamico, Social).
    SecondaryButton("Jump cut", Modifier.fillMaxWidth(), onClick = { if (!running) { vm.pause(); vm.speechPlan = null; vm.jumpMode = true; vm.showSpeechCut = true } })
    if (clip.isPhoto) { Hint("Las fotos no tienen audio."); return }
    ToggleRow("Desactivar el audio del clip", clip.muted, !running) { v -> vm.updateSelected { it.copy(muted = v) } }
    LabeledSlider(
        "Volume", null, (clip.volume * 100).toInt(), enabled = !running && !clip.muted, range = 0..200,
        format = { "$it%" },
    ) { v -> vm.updateSelected { it.copy(volume = v / 100f) } }
    SecondaryButton("Separar el audio", Modifier.fillMaxWidth(), onClick = { if (!running) vm.detachAudio() })
    Hint("Separa el audio: se vuelve una fila aparte en la línea de tiempo; al alargarla o moverla, la voz puede empezar antes de la imagen o seguir después del corte (cortes en J y en L).")
    Hint("Toca otro clip en la línea de tiempo para ajustar su volumen. En los cambios de velocidad el audio exportado mantiene el tono natural (ajuste en Exportar).")
}

@Composable
private fun PhotoPanel(vm: EditorViewModel, running: Boolean) {
    val clip = vm.selectedClip ?: return
    val enabled = !running
    LabeledSlider(
        "Duración de la foto", null, (clip.trimmedUs / 100_000).toInt(), enabled, range = 2..150,
        format = { String.format(java.util.Locale.ITALIAN, "%.1f s", it / 10f) },
    ) { v -> vm.setPhotoDuration(v * 100_000L) }
    PanelLabel("Movimiento")
    var allPhotos by remember { mutableStateOf(false) }
    PillRow(MotionStyle.entries, MotionStyle.matching(clip.keyframes, clip.trimmedUs), { it?.label ?: "" }, enabled) { m -> if (m != null) vm.applyMotion(m, allPhotos) }
    ToggleRow("Aplicar a todas las fotos", allPhotos, enabled) { allPhotos = it }
    Hint("Los movimientos usan dos keyframes: los personalizas desde la herramienta Keyframe. Zoom fijo, posición y Ajustar/Llenar están en Encuadre. Con los beats, el tirador derecho ajusta la duración al ritmo.")
}

/** Zoom su un punto, per i tutorial: si sceglie la zona del quadro, quanto ingrandire e per quanto. */
@Composable
private fun TutorialZoom(vm: EditorViewModel, enabled: Boolean) {
    var zoom by remember { mutableStateOf(2f) }
    var hold by remember { mutableStateOf(2_500_000L) }
    PanelLabel("Zoom en un punto (tutorial)")
    Hint("Lleva el indicador adonde quieras el zoom y toca la zona del cuadro: la imagen llega suave, se queda y regresa.")
    Row(horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.CenterVertically) {
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            it.fabriziodavi.vivovideo.model.Tutorial.Spot.entries.chunked(3).forEach { row ->
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    row.forEach { s ->
                        Box(
                            Modifier.size(40.dp).clip(RoundedCornerShape(8.dp)).background(VV.Raised).clickable(enabled = enabled) { vm.zoomOnSpot(s, zoom, hold) },
                            contentAlignment = Alignment.Center,
                        ) { Text(s.label, color = VV.Tally, style = MaterialTheme.typography.titleSmall) }
                    }
                }
            }
        }
        Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            PillRow(listOf(1.5f, 2f, 2.5f), zoom, { String.format(java.util.Locale.ITALIAN, "%.1f×", it).replace(",0×", "×") }, enabled) { zoom = it }
            PillRow(listOf(1_500_000L, 2_500_000L, 4_000_000L), hold, { String.format(java.util.Locale.ITALIAN, "%.1f s", it / 1e6).replace(",0 s", " s") }, enabled) { hold = it }
        }
    }
}

@Composable
private fun FramePanel(vm: EditorViewModel, running: Boolean) {
    val clip = vm.selectedClip ?: return
    val enabled = !running
    val f = clip.frame
    Segmented(FrameMode.entries, clip.frameMode, { if (it == FrameMode.BLUR) "Desenfocado" else it.label }, enabled) { m ->
        vm.updateSelected { it.withMode(m) }
    }
    if (clip.frameMode == FrameMode.BLUR) Hint("Fondo desenfocado: la imagen completa, con detrás una copia suya desenfocada en lugar de las barras negras.")
    Hint("Pellizca, arrastra o gira la vista previa con dos dedos, o usa los controles deslizantes.")
    LabeledSlider("Zoom", null, (f.scale * 100).toInt(), enabled, range = 20..400, format = { "$it%" }) {
        vm.setFrame(f.copy(scale = it / 100f))
    }
    LabeledSlider("Horizontal", null, (f.x * 100).toInt(), enabled, range = -100..100) { vm.setFrame(f.copy(x = it / 100f)) }
    LabeledSlider("Vertical", null, (f.y * 100).toInt(), enabled, range = -100..100) { vm.setFrame(f.copy(y = it / 100f)) }
    LabeledSlider("Rotación", null, f.rotation.toInt(), enabled, range = -180..180, format = { "$it°" }) {
        vm.setFrame(f.copy(rotation = it.toFloat()))
    }
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        SecondaryButton("Girar 90°", onClick = {
            if (enabled) vm.setFrame(f.copy(rotation = ((f.rotation + 90f + 540f) % 360f) - 180f))
        })
        SecondaryButton("Restablecer", onClick = { if (enabled) { vm.setFrame(Transform()); vm.project?.let { p -> vm.updateSelected { it.withMode(p.presetModeFor(it)) } } } })
    }
    SecondaryButton("Aplicar a todos los clips", selected = vm.project?.frameOnAll(clip) == true, onClick = { if (enabled) vm.applyFrameToAll() })
    PanelLabel("Zoom en el rostro")
    val zoomed = f.scale > 1.001f
    if (!zoomed) SecondaryButton("Cerrar en el rostro", Modifier.fillMaxWidth(), onClick = { if (enabled) vm.faceZoom() })
    else {
        SecondaryButton("Alargar el zoom hasta el indicador", Modifier.fillMaxWidth(), onClick = { if (enabled) vm.extendZoomToPlayhead() })
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            SecondaryButton("Al clip siguiente", onClick = { if (enabled) vm.extendZoomToNext() })
            SecondaryButton("Quitar el zoom", onClick = { if (enabled) vm.setFrame(Transform()) })
        }
    }
    Hint("Para alargar el zoom cuanto quieras: lleva el indicador adonde debe terminar y toca «Alargar el zoom hasta el indicador». Para que empiece en un punto preciso: indicador ahí, Dividir y Cerrar en el rostro en el trozo siguiente.")
    TutorialZoom(vm, enabled)
    PanelLabel("Seguir al sujeto")
    if (clip.reframe == null) {
        SecondaryButton("Seguir al sujeto", Modifier.fillMaxWidth(), onClick = { if (enabled && vm.clipWork == null) vm.followSubject() })
        Hint("Llena el cuadro y mueve el encuadre para mantener en el centro los rostros o el sujeto: perfecto para poner un video horizontal en un proyecto vertical.")
    } else {
        Hint(if (clip.fill) "El encuadre sigue al sujeto." else "Para seguir al sujeto hace falta \"Llenar\" (arriba).")
        SecondaryButton("Dejar de seguir", onClick = { if (enabled) vm.stopFollow() })
    }
    if (!clip.isPhoto) {
        PanelLabel("Estabilización")
        val st = clip.stab
        if (st == null) {
            SecondaryButton("Estabilizar el clip", Modifier.fillMaxWidth(), onClick = { if (enabled && vm.clipWork == null) vm.stabilize() })
            Hint("Mide el temblor de la cámara y lo suaviza, con un ligero zoom para esconder los bordes. Los videos largos o en 4K/8K requieren un poco de tiempo.")
        } else {
            LabeledSlider("Fuerza", null, (st.strength * 100).toInt(), enabled, range = 0..100, format = { "$it%" }) { vm.setStabStrength(it / 100f) }
            Hint("Zoom usado: ${"%.0f".format((StabMath.plan(st).zoom - 1) * 100)}%. Más fuerza = movimiento más firme, pero más zoom.")
            SecondaryButton("Quitar la estabilización", selected = false, onClick = { if (enabled) vm.removeStab() })
        }
    }
}

private val TEXT_COLORS = listOf(
    0xFFFFFFFF, 0xFF111111, 0xFFF2B33D, 0xFFFFE14D, 0xFFFF5A4E, 0xFFFF7AB6, 0xFF5BC0FF, 0xFF4ADE80, 0xFFB794F6,
).map { it.toInt() }

@Composable
private fun TextPanel(vm: EditorViewModel, running: Boolean, tab: Int, onPickFont: () -> Unit = {}) {
    val p = vm.project ?: return
    val t = vm.selectedText
    val enabled = !running
    if (t == null) {
        if (p.texts.isNotEmpty()) {
            PanelLabel("Textos en el proyecto")
            PillRow(p.texts, null as TextItem?, { it?.text?.lines()?.firstOrNull()?.take(18) ?: "" }, enabled) { x -> if (x != null) vm.selectText(x.id) }
        }
        PrimaryButton("Agregar texto", Modifier.fillMaxWidth(), enabled = enabled, onClick = vm::addText)
        SecondaryButton("Subtítulos automáticos", Modifier.fillMaxWidth(), onClick = { if (enabled) { vm.pause(); vm.showSubtitles = true } })
        SecondaryButton(if (p.creditsRoll != null) "Créditos finales · editar" else "Créditos finales", Modifier.fillMaxWidth(), onClick = { if (enabled) { vm.pause(); vm.showCredits = true } })
        SecondaryButton("Paso numerado (tutorial)", Modifier.fillMaxWidth(), onClick = { if (enabled) vm.addStep() })
        Hint("El texto empieza en el indicador. En la línea de tiempo lo mueves arrastrándolo y cambias su duración con los tiradores.")
        return
    }
    OutlinedTextField(
        value = t.text,
        onValueChange = { v -> vm.updateText(typing = true) { it.copy(text = v) } },
        modifier = Modifier.fillMaxWidth(),
        enabled = enabled,
        textStyle = MaterialTheme.typography.bodyLarge,
        minLines = 1, maxLines = 4,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = VV.Tally, unfocusedBorderColor = VV.Line, cursorColor = VV.Tally,
            focusedTextColor = VV.Text, unfocusedTextColor = VV.Text,
        ),
    )
    when (tab) {
        0 -> {
            ToggleRow("Detrás de la persona", t.behindPerson, enabled) { vm.setTextBehind(it) }
            if (t.behindPerson) {
                Hint("El texto queda entre el fondo y la persona, que le pasa por delante. La app lo pone grande sobre los hombros, con la cabeza que solo cubre la parte baja, para que se lea. Hace falta el recorte de la persona (si falta lo hace la app, dejando el fondo como está).")
                SecondaryButton("Volver a poner detrás de la cabeza", onClick = { if (enabled) vm.placeBehind(t.id) })
                LabeledSlider("Se lee a través de la persona", null, (t.behindShow * 100).toInt(), enabled, range = 0..60, format = { if (it == 0) "no" else "$it%" }) { v ->
                    vm.updateText { it.copy(behindShow = v / 100f) }
                }
            }
            PanelLabel("Estilos listos")
            PillRow(PRESETS_NEW_FIRST, TextPreset.matching(t), { it?.label ?: "" }, enabled) { pr -> if (pr != null) vm.applyTextPreset(pr) }
            PanelLabel("Carácter")
            PillRow(TextFont.entries.map<TextFont, TextFont?> { it }, if (t.fontFile == null) t.font else null, { it?.label ?: "" }, enabled) { f -> if (f != null) vm.updateText { it.copy(font = f, fontFile = null) } }
            PanelLabel("Tus fuentes")
            Row(Modifier.horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                vm.fonts.forEach { path ->
                    SecondaryButton(File(path).nameWithoutExtension.take(18), selected = t.fontFile == path, onClick = { if (enabled) vm.updateText { it.copy(fontFile = path) } })
                }
                SecondaryButton("+ Agregar fuente", onClick = { if (enabled) onPickFont() })
            }
            if (t.fontFile != null) {
                TextButton(onClick = { t.fontFile?.let { vm.removeFont(it) } }) { Text("Quitar esta fuente de la app", color = VV.Danger) }
            }
            PanelLabel("Color")
            Row(Modifier.horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                TEXT_COLORS.forEach { c ->
                    Box(
                        Modifier.size(30.dp).clip(RoundedCornerShape(15.dp)).background(Color(c))
                            .border(if (c == t.color) 3.dp else 1.dp, if (c == t.color) VV.Tally else VV.Line, RoundedCornerShape(15.dp))
                            .clickable(enabled = enabled) { vm.updateText { it.copy(color = c) } }
                    )
                }
            }
            PanelLabel("Degradado hacia")
            Row(Modifier.horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.CenterVertically) {
                Box(
                    Modifier.clip(RoundedCornerShape(15.dp)).background(if (t.gradientTo == null) VV.Tally else VV.Raised)
                        .clickable(enabled = enabled) { vm.updateText { it.copy(gradientTo = null) } }.padding(horizontal = 12.dp, vertical = 6.dp)
                ) { Text("Ninguna", style = MaterialTheme.typography.labelMedium, color = if (t.gradientTo == null) VV.TallyInk else VV.Text) }
                TEXT_COLORS.forEach { c ->
                    Box(
                        Modifier.size(30.dp).clip(RoundedCornerShape(15.dp)).background(Color(c))
                            .border(if (c == t.gradientTo) 3.dp else 1.dp, if (c == t.gradientTo) VV.Tally else VV.Line, RoundedCornerShape(15.dp))
                            .clickable(enabled = enabled) { vm.updateText { it.copy(gradientTo = c) } }
                    )
                }
            }
            PanelLabel("Contorno y sombra")
            Segmented(TextLook.entries, t.look, { it.label }, enabled) { l -> vm.updateText { it.copy(look = l) } }
            LabeledSlider("Tamaño", null, (t.size * 1000).toInt(), enabled, range = 15..250, format = { "${it / 10}" }) { v ->
                vm.updateText(typing = true) { it.copy(size = v / 1000f) }
            }
            LabeledSlider("Espaciado entre letras", null, (t.spacing * 100).toInt(), enabled, range = -5..60) { v ->
                vm.updateText(typing = true) { it.copy(spacing = v / 100f) }
            }
            ToggleRow("Todo en mayúsculas", t.upper, enabled) { v -> vm.updateText { it.copy(upper = v) } }
        }
        1 -> {
            PanelLabel("Cinético: una palabra a la vez")
            PillRow(listOf<KineticStyle?>(null) + KineticStyle.entries, t.kinetic, { it?.label ?: "No" }, enabled) { k -> vm.setKinetic(k, all = false) }
            if (t.kinetic != null) {
                Hint(t.kinetic.hint + ". Las palabras importantes (números, palabras largas, exclamaciones) toman el color de resalte." +
                    if (t.subtitle) " En los subtítulos sigue la voz." else " Las palabras llegan una cada ~0,35 s.")
                if (t.subtitle) SecondaryButton("Usar este estilo en todos los subtítulos", Modifier.fillMaxWidth(), onClick = { if (enabled) vm.setKinetic(t.kinetic, all = true) })
                PanelLabel("Color de las palabras importantes")
                Row(Modifier.horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    (listOf(Subtitles.KINETIC_HIGHLIGHT) + TEXT_COLORS).distinct().forEach { c ->
                        Box(
                            Modifier.size(30.dp).clip(RoundedCornerShape(15.dp)).background(Color(c))
                                .border(if (c == t.highlight) 3.dp else 1.dp, if (c == t.highlight) VV.Tally else VV.Line, RoundedCornerShape(15.dp))
                                .clickable(enabled = enabled) { vm.updateText { it.copy(highlight = c) } }
                        )
                    }
                }
            }
            if (t.subtitle && t.wordsUs.isNotEmpty() && t.kinetic == null) {
                // Sottotitoli con i tempi della voce: come seguono il parlato e di che colore.
                PanelLabel("Las palabras siguen la voz")
                val modes = listOf(0 to "No", 3 to "Se colorea mientras hablas", 1 to "Karaoke", 2 to "Palabra por palabra")
                PillRow(modes.map { it.first }, t.wordMode, { m -> modes.first { it.first == m }.second }, enabled) { m -> vm.setWordMode(m, null, all = false) }
                if (t.wordMode == 1 || t.wordMode == 3) {
                    PanelLabel("Color de la voz")
                    Row(Modifier.horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        Subtitles.ACCENTS.forEach { (_, c) ->
                            Box(
                                Modifier.size(30.dp).clip(RoundedCornerShape(15.dp)).background(Color(c))
                                    .border(if (c == t.highlight) 3.dp else 1.dp, if (c == t.highlight) VV.Tally else VV.Line, RoundedCornerShape(15.dp))
                                    .clickable(enabled = enabled) { vm.setWordMode(t.wordMode, c, all = false) }
                            )
                        }
                    }
                }
                SecondaryButton("Usar en todos los subtítulos", Modifier.fillMaxWidth(), onClick = { if (enabled) vm.setWordMode(t.wordMode, t.highlight, all = true) })
            }
            PanelLabel("De entrada")
            PillRow(ANIMS_NEW_FIRST, t.animIn, { it.label }, enabled) { a -> vm.updateText { it.copy(animIn = a) } }
            PanelLabel("De salida")
            PillRow(ANIMS_NEW_FIRST.filter { !it.inOnly }, t.animOut, { it.label }, enabled) { a -> vm.updateText { it.copy(animOut = a) } }
        }
        else -> {
            PanelLabel("Posición")
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                SecondaryButton("Alto", selected = t.x == 0f && t.y == -0.36f, onClick = { vm.updateText { it.copy(x = 0f, y = -0.36f) } })
                SecondaryButton("Centro", selected = t.x == 0f && t.y == 0f, onClick = { vm.updateText { it.copy(x = 0f, y = 0f) } })
                SecondaryButton("Bajo", selected = t.x == 0f && t.y == 0.36f, onClick = { vm.updateText { it.copy(x = 0f, y = 0.36f) } })
            }
            Hint("O arrastra, pellizca y gira el recuadro en la vista previa con los dedos.")
            MotionSection(vm, t.endPose != null, enabled, t.startUs, t.durationUs) { vm.toggleTextMotion() }
        }
    }
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        SecondaryButton("Empieza aquí", Modifier.weight(1f), onClick = { if (enabled) vm.textToPlayhead(start = true) })
        SecondaryButton("Termina aquí", Modifier.weight(1f), onClick = { if (enabled) vm.textToPlayhead(start = false) })
    }
    Hint("Empieza aquí mueve el texto al indicador; Termina aquí lo hace terminar ahí. En la línea de tiempo: toca el texto y arrástralo; los tiradores de los lados cambian la duración.")
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        SecondaryButton("Nuevo", onClick = { if (enabled) vm.addText() })
        SecondaryButton("Duplicar", onClick = { if (enabled) vm.duplicateText() })
        SecondaryButton("Eliminar", onClick = { if (enabled) vm.deleteText() })
    }
    SecondaryButton("Estilo para todos los textos", selected = p.textStyleOnAll(t), onClick = { if (enabled) vm.applyTextStyleToAll() })
    SecondaryButton("Subtítulos automáticos", Modifier.fillMaxWidth(), onClick = { if (enabled) { vm.pause(); vm.showSubtitles = true } })
    SecondaryButton(if (p.creditsRoll != null) "Créditos finales · editar" else "Créditos finales", Modifier.fillMaxWidth(), onClick = { if (enabled) { vm.pause(); vm.showCredits = true } })
}

/**
 * Riquadro tratteggiato sopra all'anteprima per un elemento posizionabile (testo, immagine):
 * si trascina, si pizzica (misura) e si ruota con le dita. Le misure sono in pixel del fotogramma.
 */
@Composable
private fun PoseHandleOverlay(
    key: Any,
    frameW: Int,
    frameH: Int,
    boxW: Float,
    boxH: Float,
    x: Float,
    y: Float,
    rotation: Float,
    onGesture: (dx: Float, dy: Float, zoom: Float, rot: Float) -> Unit,
) {
    var live by remember { mutableStateOf(LiveTransform()) }
    val onG by rememberUpdatedState(onGesture)
    BoxWithConstraints(Modifier.fillMaxSize()) {
        val density = LocalDensity.current
        val wPx = with(density) { maxWidth.toPx() }
        val hPx = with(density) { maxHeight.toPx() }
        val aspect = frameW.toFloat() / frameH
        val vw = minOf(wPx, hPx * aspect)
        val vh = vw / aspect
        val k = vw / frameW
        val cx = wPx / 2 + x * vw + live.pan.x
        val cy = hPx / 2 + y * vh + live.pan.y
        val bw = boxW * k * live.zoom
        val bh = boxH * k * live.zoom
        Canvas(Modifier.fillMaxSize()) {
            rotate(rotation + live.rotation, Offset(cx, cy)) {
                drawRect(
                    VV.Tally, topLeft = Offset(cx - bw / 2, cy - bh / 2), size = androidx.compose.ui.geometry.Size(bw, bh),
                    style = Stroke(2.dp.toPx(), pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 8f))),
                )
            }
        }
        Box(
            Modifier.fillMaxSize().pointerInput(key, vw) {
                awaitEachGesture {
                    awaitFirstDown(requireUnconsumed = false)
                    var zoom = 1f; var pan = Offset.Zero; var rot = 0f
                    do {
                        val ev = awaitPointerEvent()
                        zoom *= ev.calculateZoom(); pan += ev.calculatePan(); rot += ev.calculateRotation()
                        live = LiveTransform(zoom, pan, rot)
                        ev.changes.forEach { it.consume() }
                    } while (ev.changes.any { it.pressed })
                    if (zoom != 1f || pan != Offset.Zero || rot != 0f) onG(pan.x / vw, pan.y / vh, zoom, rot)
                    live = LiveTransform()
                }
            }
        )
    }
}

private fun wrapDeg(d: Float) = ((d + 540f) % 360f) - 180f

/** Riquadro del testo selezionato (posizione iniziale o finale del movimento). */
@Composable
private fun TextHandleOverlay(vm: EditorViewModel, t: TextItem) {
    val context = LocalContext.current
    val (outW, outH) = vm.previewFrameSize()
    val (bw, bh) = remember(t.text, t.font, t.look, t.size, outW, outH) { TextRenderer.measure(context, t, outW, outH) }
    val end = if (vm.editEnd) t.endPose else null
    val mul = end?.scale ?: 1f
    PoseHandleOverlay(
        key = t.id to (end != null), frameW = outW, frameH = outH,
        boxW = bw * mul, boxH = bh * mul,
        x = end?.x ?: t.x, y = end?.y ?: t.y, rotation = end?.rotation ?: t.rotation,
    ) { dx, dy, zoom, rot ->
        vm.updateText { cur ->
            val e = cur.endPose
            if (vm.editEnd && e != null) cur.copy(endPose = e.copy(
                x = (e.x + dx).coerceIn(-0.5f, 0.5f), y = (e.y + dy).coerceIn(-0.5f, 0.5f),
                scale = (e.scale * zoom).coerceIn(0.2f, 5f), rotation = wrapDeg(e.rotation + rot),
            )) else cur.copy(
                x = (cur.x + dx).coerceIn(-0.5f, 0.5f), y = (cur.y + dy).coerceIn(-0.5f, 0.5f),
                size = (cur.size * zoom).coerceIn(0.015f, 0.25f), rotation = wrapDeg(cur.rotation + rot),
            )
        }
    }
}

/** Riquadro dell'immagine selezionata (posizione iniziale o finale del movimento). */
@Composable
private fun LayerHandleOverlay(vm: EditorViewModel, l: Layer) {
    val (outW, outH) = vm.previewFrameSize()
    val pose = vm.editedLayerPose() ?: l.pose
    val (cw, ch) = LayerRenderer.containSize(l, outW, outH)
    PoseHandleOverlay(
        key = l.id to vm.editEnd, frameW = outW, frameH = outH,
        boxW = cw * pose.scale, boxH = ch * pose.scale, x = pose.x, y = pose.y, rotation = pose.rotation,
    ) { dx, dy, zoom, rot ->
        val p = vm.editedLayerPose() ?: return@PoseHandleOverlay
        vm.setLayerPose(p.copy(
            x = (p.x + dx).coerceIn(-1f, 1f), y = (p.y + dy).coerceIn(-1f, 1f),
            scale = (p.scale * zoom).coerceIn(0.05f, 1.5f), rotation = wrapDeg(p.rotation + rot),
        ))
    }
}

/** Interruttore del movimento e scelta della posizione da modificare (inizio/fine), come in ProShow. */
@Composable
private fun MotionSection(vm: EditorViewModel, on: Boolean, enabled: Boolean, startUs: Long, durationUs: Long, onToggle: () -> Unit) {
    PanelLabel("Movimiento")
    ToggleRow("Mover de una posición a otra", on, enabled) { onToggle() }
    if (on) {
        Segmented(listOf(false, true), vm.editEnd, { if (it) "Posición final" else "Posición inicial" }, enabled) { e ->
            vm.showPoseEnd(e, startUs, durationUs)
        }
        Hint("Elige qué posición modificar y acomódala con los dedos en la vista previa: el movimiento va de una a otra de forma suave.")
    }
}

@Composable
private fun LayerPanel(vm: EditorViewModel, running: Boolean, tab: Int, onAdd: () -> Unit) {
    val p = vm.project ?: return
    val l = vm.selectedLayer
    val enabled = !running
    if (l == null) {
        if (p.layers.isNotEmpty()) {
            PanelLabel("Capas en el proyecto")
            PillRow(p.layers, null as Layer?, { it?.name?.take(18) ?: "" }, enabled) { x -> if (x != null) vm.selectLayer(x.id) }
        }
        PrimaryButton("Agregar imagen o video", Modifier.fillMaxWidth(), enabled = enabled, onClick = onAdd)
        Hint("Fotos, logos, calcomanías PNG con transparencia u otros videos (picture-in-picture) sobre el video, desde el indicador. En la línea de tiempo los mueves y cambias su duración con los tiradores.")
        PanelLabel("Resaltes para tutoriales")
        Row(Modifier.horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            it.fabriziodavi.vivovideo.model.Tutorial.Mark.entries.forEach { m ->
                SecondaryButton(m.label, onClick = { if (enabled) vm.addTutorialMark(m) })
            }
        }
        Hint("Aparecen en el centro desde el indicador durante 3 s: arrástralos adonde haga falta, pellizca para el tamaño, gira la flecha con dos dedos. Los números avanzan solos (1, 2, 3…).")
        return
    }
    val pose = vm.editedLayerPose() ?: l.pose
    Text(l.name, style = MaterialTheme.typography.bodyMedium, maxLines = 1, overflow = TextOverflow.Ellipsis)
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        SecondaryButton("Empieza aquí", Modifier.weight(1f), onClick = { if (enabled) vm.layerToPlayhead(start = true) })
        SecondaryButton("Termina aquí", Modifier.weight(1f), onClick = { if (enabled) vm.layerToPlayhead(start = false) })
    }
    when (tab) {
        0 -> {
            Hint("Arrastra, pellizca y gira el recuadro en la vista previa con dos dedos.")
            LabeledSlider("Tamaño", null, (pose.scale * 100).toInt(), enabled, range = 5..150, format = { "$it%" }) {
                vm.setLayerPose(pose.copy(scale = it / 100f))
            }
            LabeledSlider("Opacidad", null, (pose.opacity * 100).toInt(), enabled, format = { "$it%" }) {
                vm.setLayerPose(pose.copy(opacity = it / 100f))
            }
            LabeledSlider("Rotación", null, pose.rotation.toInt(), enabled, range = -180..180, format = { "$it°" }) {
                vm.setLayerPose(pose.copy(rotation = it.toFloat()))
            }
            if (l.isVideo) {
                Hint("En la vista previa el video superpuesto se ve como imagen fija; en el video exportado se mueve.")
                PanelLabel("Audio del video superpuesto")
                ToggleRow("Desactivar el audio", l.muted, enabled) { v -> vm.updateLayer { it.copy(muted = v) } }
                if (!l.muted) LabeledSlider("Volume", null, (l.volume * 100).toInt(), enabled, range = 0..200, format = { "$it%" }) { v ->
                    vm.updateLayer { it.copy(volume = v / 100f) }
                }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                SecondaryButton("Subir encima", onClick = { if (enabled) vm.moveLayerZ(+1) })
                SecondaryButton("Bajar debajo", onClick = { if (enabled) vm.moveLayerZ(-1) })
            }
        }
        1 -> {
            PanelLabel("Forma")
            PillRow(LayerMask.entries, l.shape, { it.label }, enabled) { m -> vm.updateLayer { it.copy(mask = m, rounded = false) } }
            PanelLabel(if (l.isVideo) "Marco" else "Marco y sombra")
            Segmented(LayerBorder.entries, l.border, { it.label }, enabled) { b -> vm.updateLayer { it.copy(border = b) } }
            if (!l.isVideo) ToggleRow("Sombra", l.shadow, enabled) { v -> vm.updateLayer { it.copy(shadow = v) } }
            if (l.isVideo) {
                PanelLabel("Solo la persona")
                if (l.person == null) {
                    PersonQuality(vm, enabled)
                    SecondaryButton("Recortar la persona", Modifier.fillMaxWidth(), onClick = { if (enabled && vm.clipWork == null) vm.cutLayerPerson() })
                    Hint("Quita el fondo detrás de la persona, sin pantalla verde: solo queda ella sobre el video principal.")
                } else {
                    Hint("Se ve solo la persona.")
                    SecondaryButton("Volver a poner el fondo", onClick = { if (enabled) vm.updateLayer { it.copy(person = null) } })
                }
            }
            PanelLabel("Recorte (quitar el fondo)")
            PillRow(KeyColor.entries, l.key, { it.label }, enabled) { k -> vm.updateLayer { it.copy(key = k) } }
            if (l.key != KeyColor.NONE) {
                LabeledSlider("Tolerancia", "Súbela si queda un halo de fondo, bájala si desaparece también el sujeto.", (l.keyTolerance * 100).toInt(), enabled, format = { "$it%" }) { v ->
                    vm.updateLayer { it.copy(keyTolerance = v / 100f) }
                }
            }
        }
        else -> {
            MotionSection(vm, l.endPose != null, enabled, l.startUs, l.durationUs) { vm.toggleLayerMotion() }
            PanelLabel("Animación de entrada")
            PillRow(LAYER_ANIMS, l.animIn, { it.label }, enabled) { a -> vm.updateLayer { it.copy(animIn = a) } }
            PanelLabel("Animación de salida")
            PillRow(LAYER_ANIMS, l.animOut, { it.label }, enabled) { a -> vm.updateLayer { it.copy(animOut = a) } }
        }
    }
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        SecondaryButton("Agregar", onClick = { if (enabled) onAdd() })
        SecondaryButton("Duplicar", onClick = { if (enabled) vm.duplicateLayer() })
        SecondaryButton("Eliminar", onClick = { if (enabled) vm.deleteLayer() })
    }
}

/** Formato del video e preimpostazioni per foto e video verticali/orizzontali. */
@Composable
private fun FormatPanel(vm: EditorViewModel, running: Boolean) {
    val p = vm.project ?: return
    val enabled = !running
    PanelLabel("Formato del video")
    Segmented(VideoFormat.entries, p.format, { if (it == VideoFormat.AUTO) "Auto" else it.short }, enabled) { vm.setFormat(format = it) }
    Hint(
        when (p.format) {
            VideoFormat.AUTO -> "Automático: como el primer clip (ahora " + (if (p.outputOrientation() == Orientation.PORTRAIT) "verticale" else if (p.outputOrientation() == Orientation.SQUARE) "quadrato" else "orizzontale") + ")."
            VideoFormat.VERTICAL -> "Vertical 9:16: para historias, reels y teléfono."
            VideoFormat.HORIZONTAL -> "Horizontal 16:9: para TV, computadora y YouTube."
            VideoFormat.SQUARE -> "Cuadrado 1:1: para publicaciones."
        }
    )
    PanelLabel("Fotos y videos con la misma orientación")
    Segmented(FrameMode.entries, p.sameMode, { if (it == FrameMode.BLUR) "Desenfocado" else it.label }, enabled) { vm.setFormat(same = it) }
    PanelLabel("Fotos y videos con la orientación opuesta")
    Segmented(FrameMode.entries, p.otherMode, { if (it == FrameMode.BLUR) "Desenfocado" else it.label }, enabled) { vm.setFormat(other = it) }
    val v = p.clips.count { it.orientation == Orientation.PORTRAIT }
    val o = p.clips.count { it.orientation == Orientation.LANDSCAPE }
    Hint("En el proyecto: $v verticales, $o horizontales. Llenar recorta los bordes, Ajustar muestra todo con barras negras, Desenfocado muestra todo con detrás una copia desenfocada.")
    SecondaryButton("Aplicar a todos los clips", Modifier.fillMaxWidth(), selected = p.followsPresets(), onClick = { if (enabled) vm.setFormat() })
    Hint("Los preajustes valen también para las fotos y videos que agregues después. Un clip individual lo cambias en Encuadre.")
}

@Composable
private fun SlideshowPanel(vm: EditorViewModel, running: Boolean) {
    val p = vm.project ?: return
    val enabled = !running
    val hasBeats = p.beatTimes().isNotEmpty()
    var seconds by remember { mutableStateOf(30) }          // decimi di secondo
    var rhythm by remember { mutableStateOf(hasBeats) }
    var beats by remember { mutableStateOf(4) }
    var motion by remember { mutableStateOf<MotionStyle?>(null) }
    var transition by remember { mutableStateOf<TransitionType?>(null) }
    var noTransitions by remember { mutableStateOf(false) }
    var fill by remember { mutableStateOf(true) }
    Hint("Prepara de una vez todas las fotos del proyecto (${p.clips.count { it.isPhoto }}): duración, movimiento y transiciones. Se puede deshacer.")
    PanelLabel("Duración de cada foto")
    if (hasBeats) ToggleRow("Al ritmo de la música", rhythm, enabled) { rhythm = it }
    if (rhythm && hasBeats) {
        Segmented(listOf(1, 2, 4, 8), beats, { "$it battit" + if (it == 1) "o" else "i" }, enabled) { beats = it }
    } else {
        LabeledSlider("Segundos", null, seconds, enabled, range = 10..100, format = { String.format(java.util.Locale.ITALIAN, "%.1f s", it / 10f) }) { seconds = it }
        if (!hasBeats) Hint("Con una música y sus beats, las fotos pueden cambiar al compás.")
    }
    PanelLabel("Movimiento de las fotos")
    val motions: List<MotionStyle?> = listOf(null) + MotionStyle.entries
    PillRow(motions, motion, { it?.label ?: "Vario" }, enabled) { motion = it }
    PanelLabel("Transiciones")
    val trans: List<TransitionType?> = listOf(null) + TRANSITIONS_NEW_FIRST
    PillRow(listOf<Any?>("none") + trans, if (noTransitions) "none" else transition, { a ->
        when (a) { "none" -> "Ninguna"; null -> "Varias"; else -> (a as TransitionType).label }
    }, enabled) { a ->
        noTransitions = a == "none"
        transition = a as? TransitionType
    }
    ToggleRow("Fotos a pantalla completa (llenar el cuadro)", fill, enabled) { fill = it }
    Box(
        Modifier.fillMaxWidth().clip(RoundedCornerShape(12.dp)).background(VV.Tally)
            .clickable(enabled = enabled) {
                vm.createSlideshow(SlideshowOptions(
                    photoUs = seconds * 100_000L,
                    beatsPerPhoto = if (rhythm && hasBeats) beats else 0,
                    motion = motion, transition = transition, noTransitions = noTransitions, fill = fill,
                ))
            }.padding(vertical = 13.dp),
        contentAlignment = Alignment.Center,
    ) { Text("Crear presentación", style = MaterialTheme.typography.labelLarge, color = VV.TallyInk) }
}

@Composable
private fun EffectsPanel(vm: EditorViewModel, running: Boolean, tab: Int) {
    val clip = vm.selectedClip ?: return
    val p = vm.project ?: return
    val enabled = !running
    if (tab == 0) { FxTab(vm, clip, p, enabled); return }
    if (tab == 2) { PersonTab(vm, clip, enabled); return }
    if (tab == 3) { PrivacyTab(vm, clip, enabled); return }
    val secs = { v: Int -> if (v == 0) "No" else String.format(java.util.Locale.ITALIAN, "%.1f s", v / 10f) }
    val maxTenths = (clip.outputUs / 2 / 100_000).toInt().coerceIn(1, 50)
    PanelLabel("Fundidos del clip")
    LabeledSlider("De entrada", null, (clip.fadeInUs / 100_000).toInt().coerceAtMost(maxTenths), enabled, range = 0..maxTenths, format = secs) { v ->
        vm.updateSelected { it.copy(fadeInUs = v * 100_000L) }
    }
    LabeledSlider("De salida", null, (clip.fadeOutUs / 100_000).toInt().coerceAtMost(maxTenths), enabled, range = 0..maxTenths, format = secs) { v ->
        vm.updateSelected { it.copy(fadeOutUs = v * 100_000L) }
    }
    if (clip.fadeInUs > 0 || clip.fadeOutUs > 0) {
        Segmented(FadeColor.entries, clip.fadeColor, { "Dal/al " + it.label.lowercase() }, enabled) { c -> vm.updateSelected { it.copy(fadeColor = c) } }
        if (!clip.isPhoto) ToggleRow("También el audio (en el video exportado)", clip.fadeAudio, enabled) { v -> vm.updateSelected { it.copy(fadeAudio = v) } }
    }
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        SecondaryButton("Abrir y cerrar el video", selected = p.videoEndsFaded(), onClick = { if (enabled) vm.toggleVideoEnds() })
        if (clip.fadeInUs > 0 || clip.fadeOutUs > 0) SecondaryButton("A todos los clips", selected = p.fadesOnAll(clip), onClick = { if (enabled) vm.applyFadesToAll() })
    }
    Hint("\"Abrir y cerrar el video\": fundido desde negro en el primer clip (1 s) y a negro en el último (1,5 s). Encendido está en ámbar; tócalo de nuevo para quitarlos.")
}

@Composable
private fun FxTab(vm: EditorViewModel, clip: it.fabriziodavi.vivovideo.model.Clip, p: it.fabriziodavi.vivovideo.model.Project, enabled: Boolean) {
    var category by remember { mutableStateOf(FxCategory.SOCIAL) }
    Row(Modifier.horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
        FxCategory.entries.forEach { cat ->
            val on = cat == category
            val count = clip.fx.count { if (cat == FxCategory.TREND) it.type.trendy else it.type.category == cat }
            Box(
                Modifier.clip(RoundedCornerShape(10.dp)).background(if (on) VV.Raised2 else VV.Canvas)
                    .border(1.dp, if (on) VV.Tally else VV.Line, RoundedCornerShape(10.dp))
                    .clickable { category = cat }.padding(horizontal = 12.dp, vertical = 7.dp)
            ) {
                Text(cat.label + if (count > 0) " · $count" else "", style = MaterialTheme.typography.labelMedium, color = if (on) VV.Tally else VV.Dim)
            }
        }
    }
    Row(Modifier.horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        FxType.entries.filter { if (category == FxCategory.TREND) it.trendy else it.category == category }.forEach { t ->
            val on = clip.fx.any { it.type == t }
            Box(
                Modifier.clip(RoundedCornerShape(18.dp)).background(if (on) VV.Tally else VV.Raised)
                    .clickable(enabled = enabled) { vm.toggleFx(t) }.padding(horizontal = 14.dp, vertical = 9.dp)
            ) { Text(t.label, style = MaterialTheme.typography.labelLarge, color = if (on) VV.TallyInk else VV.Text) }
        }
    }
    clip.fx.filter { !it.type.toggle }.forEach { f ->
        LabeledSlider(f.type.label, null, (f.amount * 100).toInt(), enabled, format = { "$it%" }) { v -> vm.setFxAmount(f.type, v / 100f) }
    }
    if (clip.fx.any { it.type.onBeats } && p.beatTimes().isEmpty()) {
        Hint("\"Pulsa al ritmo\", \"Destellos al ritmo\" y \"Golpe al ritmo\" usan los beats de la música: agrega una canción y detecta los beats desde el panel Música.")
    }
    PanelLabel("Blanco y negro desde un punto")
    var bwFade by remember { mutableStateOf(true) }
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        SecondaryButton("Blanco y negro desde aquí", onClick = { if (enabled) vm.bwFromPlayhead(true, bwFade) })
        SecondaryButton("Color desde aquí", onClick = { if (enabled) vm.bwFromPlayhead(false, bwFade) })
    }
    ToggleRow("Paso suave (0,6 s)", bwFade, enabled) { bwFade = it }
    Hint("Lleva el indicador adonde quieras el cambio: desde ahí hasta el final el video pasa a blanco y negro (o vuelve a color). Puedes hacerlo cuantas veces quieras, como en los videos de YouTube.")
    if (clip.fx.isNotEmpty()) {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            SecondaryButton("Aplicar a todos los clips", selected = p.fxOnAll(clip), onClick = { if (enabled) vm.applyFxToAll() })
            SecondaryButton("Quitar todos", onClick = { if (enabled) vm.updateSelected { it.copy(fx = emptyList()) } })
        }
    } else {
        Hint("Toca un efecto para encenderlo o apagarlo; se pueden combinar incluso de categorías distintas.")
    }
}

@Composable
private fun ColorPanel(vm: EditorViewModel, running: Boolean, tab: Int, onPickLut: () -> Unit) {
    val p = vm.project ?: return
    val c = p.color
    val enabled = !running
    if (tab == 3) {
        vm.selectedClip?.let { clip ->
            PanelLabel("Este clip")
            ToggleRow("Grabado en LOG: convertir a colores normales", clip.log, enabled) { v -> vm.updateSelected { it.copy(log = v) } }
        }
        PanelLabel("Todo el proyecto")
        val look = runCatching { Look.valueOf(c.look) }.getOrDefault(Look.NONE)
        PillRow(Look.entries, look, { it.label }, enabled) { l -> vm.updateColor { it.copy(look = l.name) } }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(c.lutName ?: "Sin LUT", style = MaterialTheme.typography.bodyMedium, modifier = Modifier.weight(1f), maxLines = 1, overflow = TextOverflow.Ellipsis)
            TextButton(onClick = onPickLut, enabled = enabled) { Text(if (c.lutPath == null) "Cargar .cube" else "Cambiar", color = VV.Tally) }
            if (c.lutPath != null) TextButton(onClick = { vm.updateColor { it.copy(lutPath = null, lutName = null) } }, enabled = enabled) { Text("Quitar", color = VV.Dim) }
        }
        LabeledSlider("Altas luces / cielo", null, c.highlights, enabled) { v -> vm.updateColor { it.copy(highlights = v) } }
        LabeledSlider("Reducción de ruido", "Suaviza el cielo y las zonas uniformes manteniendo los bordes.", c.denoise, enabled) { v -> vm.updateColor { it.copy(denoise = v) } }
        return
    }
    // Le schede della clip lavorano sulla clip selezionata (o su quella sotto l'indicatore).
    if (vm.selectedClip == null) {
        LaunchedEffect(Unit) { p.locate(vm.playheadUs)?.let { (i, _) -> vm.selectedClipId = p.clips[i].id } }
    }
    val clip = vm.selectedClip ?: return
    val g = clip.grade
    Histogram(vm, clip)
    fun set(change: (Grade) -> Grade) { vm.updateSelected { it.copy(grade = change(it.grade)) } }
    fun pct(v: Float) = (v * 100).roundToInt()
    val signed = { v: Int -> if (v > 0) "+$v" else "$v" }
    when (tab) {
        0 -> {
            LabeledSlider("Exposición", null, pct(g.exposure), enabled, range = -200..200,
                format = { String.format(java.util.Locale.ITALIAN, "%+.1f EV", it / 100f) }) { v -> set { it.copy(exposure = v / 100f) } }
            LabeledSlider("Contraste", null, pct(g.contrast), enabled, range = -100..100, format = signed) { v -> set { it.copy(contrast = v / 100f) } }
            LabeledSlider("Altas luces", null, pct(g.highlights), enabled, range = -100..100, format = signed) { v -> set { it.copy(highlights = v / 100f) } }
            LabeledSlider("Sombras", null, pct(g.shadows), enabled, range = -100..100, format = signed) { v -> set { it.copy(shadows = v / 100f) } }
            LabeledSlider("Temperatura", "Hacia el azul (–) o hacia el naranja (+).", pct(g.temperature), enabled, range = -100..100, format = signed) { v -> set { it.copy(temperature = v / 100f) } }
            LabeledSlider("Tinte", "Hacia el verde (–) o hacia el magenta (+).", pct(g.tint), enabled, range = -100..100, format = signed) { v -> set { it.copy(tint = v / 100f) } }
            LabeledSlider("Saturación", null, pct(g.saturation), enabled, range = -100..100, format = signed) { v -> set { it.copy(saturation = v / 100f) } }
            LabeledSlider("Intensidad", "Aviva los colores apagados sin tocar la piel.", pct(g.vibrance), enabled, range = -100..100, format = signed) { v -> set { it.copy(vibrance = v / 100f) } }
        }
        1 -> {
            Hint("Arrastra el punto hacia un color para empujar sombras, tonos medios o luces en esa dirección; cuanto más lejos del centro, más fuerte.")
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                ColorWheel("Sombras", g.shadowWheel, enabled) { w -> set { it.copy(shadowWheel = w) } }
                ColorWheel("Tonos medios", g.midWheel, enabled) { w -> set { it.copy(midWheel = w) } }
                ColorWheel("Luces", g.highWheel, enabled) { w -> set { it.copy(highWheel = w) } }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                SecondaryButton("Look cine", Modifier.weight(1f), onClick = {
                    if (enabled) set { it.copy(shadowWheel = Wheel(190f, 0.5f), highWheel = Wheel(30f, 0.4f), contrast = maxOf(it.contrast, 0.15f)) }
                })
                SecondaryButton("Reiniciar ruedas", Modifier.weight(1f), onClick = { if (enabled) set { it.copy(shadowWheel = Wheel(), midWheel = Wheel(), highWheel = Wheel()) } })
            }
        }
        else -> {
            var band by remember { mutableStateOf(HslBand.ORANGE) }
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                HslBand.entries.forEach { b ->
                    val on = b == band
                    val changed = g.band(b) != Hsl()
                    Box(
                        Modifier.size(32.dp).clip(CircleShape).background(Color(b.argb))
                            .border(if (on) 3.dp else if (changed) 2.dp else 0.dp, if (on) VV.Text else VV.Tally, CircleShape)
                            .clickable { band = b },
                    )
                }
            }
            val h = g.band(band)
            Text(band.label, style = MaterialTheme.typography.labelLarge, color = VV.Tally)
            LabeledSlider("Tono", null, pct(h.hue), enabled, range = -100..100, format = signed) { v -> set { it.withBand(band, h.copy(hue = v / 100f)) } }
            LabeledSlider("Saturación", null, pct(h.sat), enabled, range = -100..100, format = signed) { v -> set { it.withBand(band, h.copy(sat = v / 100f)) } }
            LabeledSlider("Brillo", null, pct(h.lum), enabled, range = -100..100, format = signed) { v -> set { it.withBand(band, h.copy(lum = v / 100f)) } }
            Hint("Cambia un solo color: por ejemplo el cielo más azul (Azul: saturación +) o el verde del pasto menos intenso, sin tocar el resto.")
        }
    }
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        SecondaryButton("Copiar", Modifier.weight(1f), onClick = { vm.copiedGrade = g })
        SecondaryButton("Pegar", Modifier.weight(1f), onClick = { vm.copiedGrade?.let { cg -> if (enabled) set { cg } } })
        SecondaryButton("Restablecer", Modifier.weight(1f), onClick = { if (enabled) set { Grade() } })
    }
    SecondaryButton("Aplicar a todos los clips", Modifier.fillMaxWidth(), selected = p.clips.size > 1 && p.clips.all { it.grade == g }, onClick = { if (enabled) vm.applyGradeToAll() })
}

/** Istogramma del fotogramma sotto l'indicatore, già corretto: luminosità (grigio) e canali rosso, verde, blu. */
@Composable
private fun Histogram(vm: EditorViewModel, clip: it.fabriziodavi.vivovideo.model.Clip) {
    val ctx = LocalContext.current
    val pixels by androidx.compose.runtime.produceState<IntArray?>(null, clip.id, clip.trimStartUs, vm.playheadUs / 500_000) {
        value = vm.sampleFrame(ctx, clip)
    }
    val px = pixels
    val bins = remember(px, clip.grade) { px?.let { GradeMath.histogram(it, clip.grade) } }
    Box(Modifier.fillMaxWidth().height(64.dp).clip(RoundedCornerShape(8.dp)).background(Color(0xFF0B0C0F))) {
        if (bins == null) {
            Text("Histograma", style = MaterialTheme.typography.labelSmall, color = VV.Faint, modifier = Modifier.align(Alignment.Center))
        } else {
            Canvas(Modifier.fillMaxSize().padding(horizontal = 4.dp, vertical = 4.dp)) {
                val n = bins[0].size
                val maxV = (bins.flatMap { it.toList() }.sortedDescending().getOrNull(n / 8) ?: 1).coerceAtLeast(1).toFloat()
                fun path(b: IntArray): Path = Path().apply {
                    moveTo(0f, size.height)
                    for (i in 0 until n) lineTo(i * size.width / (n - 1), size.height - (b[i] / maxV).coerceAtMost(1f) * size.height)
                    lineTo(size.width, size.height); close()
                }
                drawPath(path(bins[0]), Color(0x55FFFFFF))
                drawPath(path(bins[1]), Color(0xFFFF4D4D), style = Stroke(1.2.dp.toPx()))
                drawPath(path(bins[2]), Color(0xFF4DFF6A), style = Stroke(1.2.dp.toPx()))
                drawPath(path(bins[3]), Color(0xFF4D8BFF), style = Stroke(1.2.dp.toPx()))
            }
            val total = bins[0].sum().coerceAtLeast(1)
            val clipHi = bins[0].last() * 100 / total
            val clipLo = bins[0].first() * 100 / total
            if (clipHi >= 3 || clipLo >= 3) {
                Text(
                    listOfNotNull(if (clipHi >= 3) "Luces quemadas $clipHi%" else null, if (clipLo >= 3) "Sombras cerradas $clipLo%" else null).joinToString(" · "),
                    style = MaterialTheme.typography.labelSmall, color = VV.Tally,
                    modifier = Modifier.align(Alignment.TopEnd).padding(6.dp),
                )
            }
        }
    }
}

/** Ruota colore: il punto indica la direzione (tinta) e la forza della spinta. */
@Composable
private fun ColorWheel(label: String, value: Wheel, enabled: Boolean, onChange: (Wheel) -> Unit) {
    var live by remember(value) { mutableStateOf(value) }
    val onCh by rememberUpdatedState(onChange)
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Canvas(
            Modifier.size(92.dp).pointerInput(enabled) {
                if (!enabled) return@pointerInput
                awaitEachGesture {
                    val down = awaitFirstDown()
                    fun at(p: Offset): Wheel {
                        val cx = size.width / 2f; val cy = size.height / 2f
                        val dx = p.x - cx; val dy = p.y - cy
                        val r = kotlin.math.sqrt(dx * dx + dy * dy) / (minOf(cx, cy))
                        val deg = ((Math.toDegrees(kotlin.math.atan2(dy, dx).toDouble()).toFloat()) + 360f) % 360f
                        return Wheel(deg, r.coerceIn(0f, 1f).let { if (it < 0.06f) 0f else it })
                    }
                    live = at(down.position)
                    do {
                        val ev = awaitPointerEvent()
                        ev.changes.forEach { ch -> ch.consume(); live = at(ch.position) }
                    } while (ev.changes.any { it.pressed })
                    onCh(live)
                }
            }
        ) {
            val r = size.minDimension / 2f
            drawCircle(Brush.sweepGradient(listOf(Color.Red, Color.Yellow, Color.Green, Color.Cyan, Color.Blue, Color.Magenta, Color.Red)), r)
            drawCircle(Brush.radialGradient(listOf(Color(0xFF8A8F98), Color(0x008A8F98)), center, r), r)
            drawCircle(Color(0x55000000), r, style = Stroke(1.dp.toPx()))
            val a = Math.toRadians(live.hue.toDouble())
            val p = Offset(center.x + (kotlin.math.cos(a) * live.amount * r).toFloat(), center.y + (kotlin.math.sin(a) * live.amount * r).toFloat())
            drawCircle(Color.White, 7.dp.toPx(), p)
            drawCircle(Color.Black, 7.dp.toPx(), p, style = Stroke(1.5.dp.toPx()))
        }
        Text(label, style = MaterialTheme.typography.labelMedium, modifier = Modifier.padding(top = 4.dp))
        Text(if (value.amount > 0f) "${(value.amount * 100).roundToInt()}%" else "—", style = MaterialTheme.typography.labelSmall, color = VV.Dim,
            modifier = Modifier.clickable(enabled = enabled) { onChange(Wheel()) })
    }
}

@Composable
private fun ExportPanel(vm: EditorViewModel, onOpen: (android.net.Uri) -> Unit) {
    val o = vm.exportOptions
    val state = vm.exportState
    val running = state is ExportState.Running
    PanelLabel("Listo para")
    Row(Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        ExportPreset.entries.forEach { pr ->
            SecondaryButton(pr.label, selected = pr.matches(o)) { if (!running) vm.exportOptions = pr.applyTo(o) }
        }
    }
    ExportPreset.entries.firstOrNull { it.matches(o) }?.let { Hint(it.hint) }
    vm.exportEstimate()?.let { Text(it, style = MaterialTheme.typography.bodyMedium, color = VV.Tally) }
    PanelLabel("Resolución")
    Segmented(OutputSize.entries, o.size, { it.label }, !running) { vm.exportOptions = o.copy(size = it, maxBitrate = null) }
    PanelLabel("Fotogramas por segundo")
    Segmented(OutputFps.entries, o.fps, { it.label }, !running) { vm.exportOptions = o.copy(fps = it, maxBitrate = null) }
    PanelLabel("Codec")
    Segmented(OutputCodec.entries, if (o.hdr && vm.hasHdr) OutputCodec.H265 else o.codec, { it.label }, !running) {
        vm.exportOptions = o.copy(codec = it, maxBitrate = null, hdr = o.hdr && it == OutputCodec.H265)
    }
    if (vm.hasHdr) {
        ToggleRow("HDR 10 bits, como lo grabaste", o.hdr, !running) { vm.exportOptions = o.copy(hdr = it, codec = if (it) OutputCodec.H265 else o.codec) }
        Hint(if (o.hdr) "Luces y colores plenos en las pantallas HDR (YouTube, Instagram, galería del teléfono). Se usa H.265. Si algo no lo soporta, la app exporta en SDR y te lo avisa en el resultado."
            else "En el proyecto hay videos HDR: ahora se convierten a SDR, el formato que se ve bien en todas partes.")
        // Dettaglio: un video 4K esportato in 1080p perde finezza (le luci HDR restano).
        val src4k = vm.project?.clips?.any { !it.isPhoto && minOf(it.width, it.height) >= 2000 } == true
        if (src4k && o.size != OutputSize.ORIGINAL && o.size != OutputSize.P2160) {
            Hint("Los videos del proyecto están en 4K: en ${o.size.label} se pierde un poco de detalle. Para tener la misma nitidez del original elige Resolución original o 4K.")
        }
    }
    ToggleRow("Audio con tono natural en los cambios de velocidad", o.keepPitch, !running) { vm.exportOptions = o.copy(keepPitch = it) }
    ToggleRow("Volumen uniformado: ${vm.project?.mix?.target?.label ?: "–14 LUFS"} (se cambia en Audio → Mezclador)", o.loudness, !running) { vm.exportOptions = o.copy(loudness = it) }
    PanelLabel("Experimental: fundidos cruzados y videos superpuestos")
    ToggleRow("En el video exportado", vm.multiExportEnabled, !running) { vm.setMultiExport(it) }
    Hint("En la vista previa los fundidos cruzados y videos superpuestos se preparan con antelación (unos segundos después de un cambio). Si la app se cierra por un error, esta opción se apaga sola.")
    PanelLabel("Vista previa")
    ToggleRow("Copias ligeras para videos 4K, 8K y 120 fps", vm.useProxies, !running) { vm.setProxies(it) }
    Hint("Solo para la vista previa, que corre más fluida: el video exportado usa siempre los originales.")
    when (state) {
        is ExportState.Running -> {
            Text(state.label + (state.percent?.let { "  $it%" } ?: ""), style = MaterialTheme.typography.bodyMedium)
            LinearProgressIndicator(
                progress = { (state.percent ?: 0) / 100f },
                modifier = Modifier.fillMaxWidth().height(4.dp).clip(RoundedCornerShape(2.dp)),
                color = VV.Tally, trackColor = VV.Raised2,
            )
            SecondaryButton("Deshacer", onClick = vm::cancelExport)
        }
        is ExportState.Done -> {
            ResultBox("Guardado en Film/VivoVideo", state.details) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    PrimaryButton("Abrir el video") { onOpen(state.uri) }
                    SecondaryButton("Cerrar", onClick = vm::dismissExportResult)
                }
            }
            CreditsBox(vm.project?.credits().orEmpty())
        }
        is ExportState.Failed -> ResultBox("La exportación no se pudo completar", state.message) {
            Hint("Haz una captura de pantalla de este mensaje y mándasela a Claude.")
            SecondaryButton("Cerrar", onClick = vm::dismissExportResult)
        }
        ExportState.Idle -> PrimaryButton("Exportar el video", Modifier.fillMaxWidth(), enabled = vm.project?.clips?.isNotEmpty() == true, onClick = vm::startExport)
    }
}

@Composable
fun PrimaryButton(text: String, modifier: Modifier = Modifier, enabled: Boolean = true, onClick: () -> Unit) {
    Box(
        modifier.clip(RoundedCornerShape(12.dp)).background(if (enabled) VV.Tally else VV.Raised)
            .clickable(enabled = enabled, onClick = onClick).padding(horizontal = 18.dp, vertical = 13.dp),
        contentAlignment = Alignment.Center,
    ) { Text(text, style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.SemiBold, color = if (enabled) VV.TallyInk else VV.Faint) }
}

/** Pulsante secondario; con [selected] si illumina in ambra (per le scelte che restano attive). */
@Composable
fun SecondaryButton(text: String, modifier: Modifier = Modifier, selected: Boolean = false, onClick: () -> Unit) {
    Box(
        modifier.clip(RoundedCornerShape(12.dp)).background(if (selected) VV.Tally else VV.Raised).clickable(onClick = onClick)
            .padding(horizontal = 18.dp, vertical = 13.dp),
        contentAlignment = Alignment.Center,
    ) { Text(text, style = MaterialTheme.typography.labelLarge, color = if (selected) VV.TallyInk else VV.Text, fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal) }
}

@Composable
private fun ResultBox(title: String, body: String, actions: @Composable () -> Unit) {
    Column(
        Modifier.fillMaxWidth().clip(RoundedCornerShape(12.dp)).background(VV.Raised).padding(14.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(title, style = MaterialTheme.typography.titleSmall)
        SelectionContainer { Text(body, style = MaterialTheme.typography.bodySmall) }
        actions()
    }
}


/** Barra della selezione multipla, al posto degli strumenti. */
@Composable
private fun MultiSelectBar(vm: EditorViewModel, ids: Set<String>, onPaste: () -> Unit) {
    Column(Modifier.fillMaxWidth().background(VV.Panel).padding(horizontal = 12.dp, vertical = 8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                when {
                    ids.isNotEmpty() -> "${ids.size} clips elegidos"
                    vm.copiedStyle != null -> "Toca los clips donde pegar"
                    else -> "Toca los clips que quieres elegir"
                },
                style = MaterialTheme.typography.titleSmall, color = VV.Text, modifier = Modifier.weight(1f),
            )
            TextButton(onClick = vm::selectAllClips) { Text("Todas", color = VV.Tally) }
            TextButton(onClick = vm::endMultiSelect) { Text("Fin", color = VV.Dim) }
        }
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            if (vm.copiedStyle != null) SecondaryButton("Pegar atributos", Modifier.weight(1f), onClick = { if (ids.isNotEmpty()) onPaste() })
            else SecondaryButton("Copiar atributos", Modifier.weight(1f), onClick = vm::copyStyle)
            SecondaryButton("Eliminar", Modifier.weight(1f), onClick = vm::deleteMulti)
        }
        // Rimescola: le clip scelte (almeno due); subito dopo, un altro ordine.
        if (vm.copiedStyle == null) SecondaryButton(
            if (vm.canShuffleAgain) "Mezclar otra vez" else "Mezclar los clips elegidos", Modifier.fillMaxWidth(),
            onClick = { if (vm.canShuffleAgain || ids.size >= 2) vm.shuffleClips(ids) else vm.message = "Elige al menos dos clips para mezclar." },
        )
        if (vm.copiedStyle == null) Hint("Copia los atributos del clip resaltado, luego elige los clips donde pegarlos.")
    }
}

/** Quali attributi incollare: di serie quelli che la clip copiata ha davvero. */
@Composable
private fun PasteStyleDialog(src: Clip, targets: Int, onPaste: (Set<ClipAttr>) -> Unit, onDismiss: () -> Unit) {
    val present = remember(src) { ClipStyle.present(src) }
    var chosen by remember(src) { mutableStateOf(present.ifEmpty { ClipAttr.entries.toSet() }) }
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = VV.Raised,
        title = { Text("Pegar atributos en $targets clips", style = MaterialTheme.typography.titleSmall) },
        text = {
            Column {
                ClipAttr.entries.forEach { a ->
                    ToggleRow(a.label + if (a in present) "" else " (normale)", a in chosen) { on -> chosen = if (on) chosen + a else chosen - a }
                }
                Hint("Los keyframes se adaptan a la duración de cada clip. La velocidad y el volumen no cambian las fotos.")
            }
        },
        confirmButton = { TextButton(onClick = { onPaste(chosen) }, enabled = chosen.isNotEmpty() && targets > 0) { Text("Pegar", color = VV.Tally) } },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Deshacer", color = VV.Dim) } },
    )
}


private val PERSON_COLORS = listOf(0xFF101418, 0xFFFFFFFF, 0xFF1F6FEB, 0xFF2DA44E, 0xFFF2B33D, 0xFFE5484D, 0xFF8E4EC6).map { it.toInt() }

/** Scontorno della persona: cosa fare dello sfondo. */
/** Privacy: oscura volti, targhe e un oggetto scelto, in tutta la clip (riconosciuti sul telefono). */
@Composable
private fun PrivacyTab(vm: EditorViewModel, clip: Clip, enabled: Boolean) {
    val r = clip.redact
    val busy = vm.clipWork != null
    if (r != null) {
        PanelLabel("Oculto")
        val what = listOfNotNull(if (r.faces) "volti" else null, if (r.plates) "targhe" else null, if (r.objectSeed != null) "un objeto" else null).joinToString(", ")
        Hint("En este clip: $what. " + if (clip.isPhoto) "${r.found} elementos cubiertos." else "Cubiertos en ${r.found} fotogramas de ${r.count}.")
        PillRow(it.fabriziodavi.vivovideo.model.RedactStyle.entries, r.style, { it.label }, enabled) { vm.changeRedactStyle(it) }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            SecondaryButton("Rehacer", onClick = { if (enabled && !busy) vm.redactClip() })
            SecondaryButton("Quitar", onClick = { if (enabled) vm.removeRedact() })
        }
        Hint("Si acortas o alargas mucho el clip, o cambias qué ocultar más abajo, toca Rehacer.")
    } else {
        PanelLabel("Ocultar rostros, placas y objetos")
        Hint("La app encuentra qué cubrir en cada fotograma del clip, en el teléfono y sin internet, y lo sigue mientras se mueve.")
    }
    ToggleRow("Rostros", vm.redactFaces, enabled) { vm.redactFaces = it }
    ToggleRow("Placas", vm.redactPlates, enabled) { vm.redactPlates = it }
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(if (vm.redactSeed != null) "Objeto elegido" else "Un objeto", style = MaterialTheme.typography.bodyMedium, modifier = Modifier.weight(1f))
        SecondaryButton(if (vm.redactSeed != null) "Cambiar" else "Tócalo en la imagen", onClick = { if (enabled && !busy) vm.openObjectPick() })
        if (vm.redactSeed != null) IconBtn(VVIcons.Close, "Ningún objeto") { vm.clearObject() }
    }
    if (r == null) {
        PillRow(it.fabriziodavi.vivovideo.model.RedactStyle.entries, vm.redactStyle, { it.label }, enabled) { vm.redactStyle = it }
        PrimaryButton(if (clip.isPhoto) "Ocultar" else "Analizar y ocultar", Modifier.fillMaxWidth(), enabled = enabled && !busy) { vm.redactClip() }
        if (!clip.isPhoto) Hint("Tarda más o menos lo que dura el clip. Para el objeto: lleva el indicador donde se vea bien y tócalo.")
    }
}

/** Scelta del modello di scontorno: veloce (sul telefono) o preciso (Google). */
@Composable
private fun PersonQuality(vm: EditorViewModel, enabled: Boolean) {
    Segmented(listOf(false, true), vm.personPrecise, { if (it) "Preciso (Google)" else "Rápido" }, enabled) { vm.personPrecise = it }
    Hint(if (vm.personPrecise) "Borde más fiel incluso en cabello y objetos en la mano; más lento (unas 2–4 veces la duración del clip). La primera vez Google descarga el modelo: hace falta internet."
        else "En el teléfono, sin internet, más o menos lo que dura el clip.")
}

@Composable
private fun PersonTab(vm: EditorViewModel, clip: Clip, enabled: Boolean) {
    if (clip.isPhoto) { Hint("El recorte de la persona funciona en los videos."); return }
    val fx = clip.person
    if (fx == null) {
        PanelLabel("Persona y fondo")
        Hint("La app reconoce a la persona en cada fotograma y te deja desenfocar el fondo, cambiarlo por un color o ponerlo en blanco y negro.")
        PersonQuality(vm, enabled)
        PrimaryButton("Recortar la persona", Modifier.fillMaxWidth(), enabled = enabled && vm.clipWork == null) { vm.cutPerson() }
        return
    }
    PanelLabel("Fondo")
    PillRow(PersonMode.entries.filter { it != PersonMode.REMOVE }, fx.mode, { it.label }, enabled) { vm.setPersonMode(it) }
    if (fx.mode == PersonMode.COLOR) {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            PERSON_COLORS.forEach { c ->
                Box(
                    Modifier.size(30.dp).clip(RoundedCornerShape(15.dp)).background(Color(c))
                        .border(if (c == fx.color) 3.dp else 1.dp, if (c == fx.color) VV.Tally else VV.Line, RoundedCornerShape(15.dp))
                        .clickable(enabled = enabled) { vm.setPersonColor(c) }
                )
            }
        }
    }
    Hint((if (fx.precise) "Recorte preciso (Google). " else "Recorte rápido. ") + "Si acortas o alargas mucho el clip, rehaz el recorte.")
    PersonQuality(vm, enabled)
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        SecondaryButton("Rehacer el recorte", onClick = { if (enabled && vm.clipWork == null) vm.cutPerson(fx.mode) })
        SecondaryButton("Quitar", onClick = { if (enabled) vm.removePerson() })
    }
}
