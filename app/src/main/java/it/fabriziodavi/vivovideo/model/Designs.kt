package it.fabriziodavi.vivovideo.model

import it.fabriziodavi.vivovideo.model.Anchor.CENTER as C
import it.fabriziodavi.vivovideo.model.Anchor.END as E
import it.fabriziodavi.vivovideo.model.Anchor.SCALE as K
import it.fabriziodavi.vivovideo.model.Anchor.START as S
import it.fabriziodavi.vivovideo.model.Anchor.STRETCH as F
import it.fabriziodavi.vivovideo.model.DColor.ACC
import it.fabriziodavi.vivovideo.model.DColor.ACC2
import it.fabriziodavi.vivovideo.model.DColor.BLACK
import it.fabriziodavi.vivovideo.model.DColor.WHITE

/**
 * I modelli grafici, provati prima sulla tavola "Collage da stampare" (stesse misure in pixel CSS):
 * 1 Copertina · 2 Reportage · 3 Lettere-foto · 4 Bento viaggio · 5 Bento uscita in bici · 6 Il mio anno ·
 * 7 Diario ritagliato · 8 Adesivi · 9 Moodboard · 10 Duotono · 11 Pop · 12 Vetro · 13 Sport · 14 Romantico ·
 * 15 Carta d'imbarco · 16 Itinerario · 17 Street · 18 Cucina.
 */
object Designs {
    val all: List<Design> by lazy {
        listOf(wowPop(), wowWord(), wowOutline(), wowCircle(), wowSlices(), wowEcho(), wowLetters(), wowCover(), wowDouble(), wowGold(), wowHolo(), wowAction(),
            evGold3d(), wedding(), saveDate(), booth(), flash(), balloons(), disco(), vip(), laurea(), baby(), badge(), speaker(),
            actJump(), actColor(), bib(), topo(), speed(), mud(), hud(), record(), sequence(), skipass(),
            paperPop(), paperStrips(), paperTrip(), paperNews(), paperLove(), paperRecipe(), paperWall(), paperRansom(), paperSticker(), paperRiso(),
            trendColorPop(), trendHoloSlices(), notes(), chrome(), leak(), blue(), pearls(), thenNow(), windows(), dump(),
            magPop(), magQuote(), cover(), reportage(), letters(), bento3d(), bentoTrip(), bentoRide(), year(), scrap3d(), scrap(), stickers(), mood(),
            posterSpot(), posterDuo3d(), duotone(), pop(), glass(),
            trip3d(), loveDouble(), sport(), love(), ticket(), route(), street(), food(),
            grid9(), gridMasonry(), gridPop(), gridType(), shapeArch(), shapeHeart(), shapeBubbles(), shapeFlower(), shapeBlob(),
            polaStack(), polaString(), polaInstax(), polaBig(), edMinimal(), edIndex(), edCover(), edSpread())
    }

    fun byId(id: String): Design? = all.firstOrNull { it.id == id }

    private fun b(x: Number, y: Number, w: Number, h: Number, ay: Anchor = S, ax: Anchor = S) =
        DBox(x.toFloat(), y.toFloat(), w.toFloat(), h.toFloat(), ax, ay)

    private fun pts(vararg v: Float) = v.toList().chunked(2).map { Pt(it[0], it[1]) }

    private const val TAPE = 0xB8FFD666L

    /** Riquadro che sale e scende in proporzione al foglio (i collage di carta si stendono su tutta la pagina). */
    private fun bk(x: Number, y: Number, w: Number, h: Number, ax: Anchor = S) = b(x, y, w, h, K, ax)

    private const val SPARKLE = "M50 0 C54 36 64 46 100 50 C64 54 54 64 50 100 C46 64 36 54 0 50 C36 46 46 36 50 0 Z"

    private const val MUD = 0xF0553A24L


    // Effetto wow: la persona che esce dalla foto, la doppia esposizione, oro con i brillantini, olografico ----------
    private val GOLD = listOf(0xFFFFF3C4L, 0xFFD9A93BL, 0xFFFFE597L, 0xFF946615L, 0xFFF2CF6CL)
    private val CHROME = listOf(0xFFF4F6FAL, 0xFF5D6577L, 0xFFE3E7EEL, 0xFF1E2330L, 0xFF9AA3B3L)

    // W1 -----------------------------------------------------------------------------------------------
    private fun wowPop() = Design("x-wow-pop", "Sale de la foto", DesignFamily.WOW, DOrient.PORTRAIT, 794f, 1123f, ACC,
        accents = listOf(0xFF1B2BD6L, 0xFFE5432DL, 0xFF111111L, 0xFFFF6FB5L, 0xFF0F7B5FL), accents2 = listOf(CREAM, BUTTER, WHITE),
        motion = CollageMotion.DROP,
        hint = "El efecto 3D: la persona sale del borde de la foto y pasa delante del texto gigante. Hace falta una foto con una persona de cuerpo entero o de medio cuerpo",
        els = listOf(
            DText(bk(0, 30, 794, 330, F), "title", "Título", "Summer", DFont.ANTON, 330f, ACC2, DAlign.CENTER, line = 0.85f, upper = true),
            DPhoto(bk(112, 330, 570, 640), 0, rot = -3f, border = 18f, borderColor = CREAM, shadow = 34f, pop = 0.34f),
            DText(bk(470, 900, 300, 80), "sticker", "Calcomanía", "best day ever", DFont.HAND, 46f, BLACK, DAlign.CENTER, rot = 7f, pill = BUTTER, pad = 22f),
            DPath(bk(56, 300, 80, 80), SPARKLE, 100f, 100f, fill = BUTTER),
            DPath(bk(690, 360, 54, 54), SPARKLE, 100f, 100f, fill = ACC2),
            DText(bk(40, 1010, 714, 80), "place", "Lugar", "{luogo|Cerdeña} · {ANNO}", DFont.HAND, 64f, ACC2, DAlign.CENTER, rot = -2f),
        ))

    // W2 -----------------------------------------------------------------------------------------------
    private fun wowDouble() = Design("x-wow-double", "Doble exposición", DesignFamily.WOW, DOrient.PORTRAIT, 794f, 1123f, ACC,
        accents = listOf(0xFFF3EEE6L, WHITE, 0xFFE9E4DAL, 0xFFDDE7EAL), accents2 = listOf(0xFF1A1A1AL, 0xFF2340D9L, 0xFF8A3B2EL),
        motion = CollageMotion.ZOOM,
        hint = "Doble exposición: la silueta de la persona (primera foto) rellena con el paisaje o la ciudad (segunda foto), como en los carteles de cine",
        els = listOf(
            DPhoto(bk(77, 70, 640, 800), 0, fill = 1),
            DText(bk(30, 860, 734, 170), "title", "Título", "Wanderlust", DFont.SERIF_IT, 150f, ACC2, DAlign.CENTER),
            DRect(bk(337, 1040, 120, 2), ACC2),
            DText(bk(40, 1058, 714, 30), "place", "Lugar", "{luogo|Dolomitas} · {Mese} {ANNO}", DFont.MONO, 18f, ACC2, DAlign.CENTER, track = 0.3f, upper = true),
        ))

    // W3 -----------------------------------------------------------------------------------------------
    private fun wowGold() = Design("x-wow-gold", "Oro y brillitos", DesignFamily.WOW, DOrient.PORTRAIT, 794f, 1123f, 0xFF0B0B0FL,
        accents = listOf(0xFFE7C26BL, 0xFFE9E9F2L, 0xFFF2A7C8L), motion = CollageMotion.CASCADE,
        hint = "Cumpleaños, aniversarios, Año Nuevo: brillitos que centellean, textos dorados con el reflejo que pasa y los años que cuentan",
        els = listOf(
            DPattern(b(0, 0, 794, 1123, F), DPatternKind.GLITTER, ACC, 0f),
            DText(bk(0, 40, 794, 330, F), "title", "Título", "Buon\ncompleanno", DFont.SCRIPT, 160f, WHITE, DAlign.CENTER, line = 0.95f, gradient = GOLD, shine = true, shadow = true),
            DPhoto(bk(167, 390, 460, 560), 0, DShape.ARCH, border = 8f, borderColor = 0xFFD9A93BL, shadow = 40f),
            DText(bk(470, 800, 320, 300), "age", "Años", "80", DFont.ANTON, 260f, WHITE, DAlign.CENTER, rot = -8f, gradient = GOLD, shine = true, shadow = true, count = true),
            DText(bk(30, 970, 480, 90), "name", "Nombre", "Abuelo", DFont.SERIF_IT, 76f, CREAM),
            DText(bk(30, 1062, 600, 26), "date", "Fecha", "{data} · {luogo|en casa}", DFont.MONO_BOLD, 18f, 0xFFD9A93BL, track = 0.25f, upper = true),
        ))

    // W4 -----------------------------------------------------------------------------------------------
    private fun wowHolo() = Design("x-wow-holo", "Holográfico", DesignFamily.WOW, DOrient.PORTRAIT, 794f, 1123f, 0xFFEFEAF8L,
        accents = listOf(0xFF1A1033L, 0xFF2340D9L, 0xFFFF2E93L), motion = CollageMotion.DROP,
        hint = "Fondo holográfico que cambia de color, el texto cromado con reflejo y la persona recortada como una calcomanía brillante",
        els = listOf(
            DPattern(b(0, 0, 794, 1123, F), DPatternKind.HOLO, 0, 0f),
            DText(bk(0, 40, 794, 300, F), "title", "Título", "Weekend", DFont.ARCHIVO, 200f, WHITE, DAlign.CENTER, gradient = CHROME, shine = true, glow = 0x99FFFFFFL, upper = true, skew = -0.06f),
            DPhoto(bk(122, 250, 550, 730), 0, cutout = true, border = 16f, shadow = 26f),
            DPath(bk(70, 330, 70, 70), SPARKLE, 100f, 100f, fill = WHITE),
            DPath(bk(660, 780, 90, 90), SPARKLE, 100f, 100f, fill = WHITE),
            DText(bk(60, 990, 330, 56), "tag", "Etiqueta", "{luogo|Milano}", DFont.MONO_BOLD, 22f, WHITE, rot = -4f, pill = ACC, pad = 18f, upper = true, track = 0.1f),
            DText(bk(420, 1000, 330, 56), "tag2", "Etiqueta 2", "{giorno_breve}", DFont.MONO_BOLD, 22f, BLACK, DAlign.RIGHT, rot = 5f, pill = WHITE, pad = 18f, upper = true, track = 0.1f),
        ))

    // W5 -----------------------------------------------------------------------------------------------
    private fun wowAction() = Design("x-wow-action", "Polaroid 3D", DesignFamily.WOW, DOrient.PORTRAIT, 794f, 1123f, 0xFF0D0D0DL,
        accents = listOf(0xFFC6FF3DL, 0xFFFF5B14L, 0xFF00E5FFL, 0xFFFFD60AL), motion = CollageMotion.DROP,
        hint = "Deporte y aventura: la polaroid torcida de la que sale quien salta, corre o pedalea, con las líneas de velocidad y el texto que grita",
        els = listOf(
            DPattern(b(0, 0, 794, 1123, F), DPatternKind.SPEED, 0x33FFFFFFL, 0f),
            DText(bk(0, 30, 794, 300, F), "title", "Título", "Send it", DFont.ANTON, 270f, ACC, DAlign.CENTER, upper = true, skew = -0.14f),
            DPhoto(bk(137, 330, 520, 560), 0, rot = 5f, border = 22f, bottomPad = 110f, shadow = 36f, pop = 0.38f),
            DText(bk(40, 1020, 714, 80), "place", "Lugar", "{luogo|Finale Ligure} · {giorno_breve}", DFont.HAND, 60f, WHITE, DAlign.CENTER, rot = -3f),
        ))

    // W6 -----------------------------------------------------------------------------------------------
    private fun wowWord() = Design("x-wow-word", "Retrato en la palabra", DesignFamily.WOW, DOrient.PORTRAIT, 794f, 1123f, ACC,
        accents = listOf(0xFFF2EDE4L, 0xFF111111L, 0xFF2340D9L, 0xFFE5432DL), accents2 = listOf(0xFF111111L, CREAM, BUTTER),
        motion = CollageMotion.ZOOM,
        hint = "La foto dentro de una palabra gigante y la persona que sale de las letras, completa por delante. Cambia la palabra en Textos (una ciudad, un nombre)",
        els = listOf(
            DText(bk(40, 50, 714, 34), "kicker", "Arriba", "{Mese} {ANNO} · {luogo|Roma}", DFont.MONO_BOLD, 22f, ACC2, DAlign.CENTER, track = 0.3f, upper = true),
            DPhoto(bk(20, 150, 754, 720), 0, word = "Roma", wordFont = DFont.ANTON, front = true),
            DText(bk(40, 900, 714, 120), "title", "Frase", "la ciudad eterna", DFont.SERIF_IT, 96f, ACC2, DAlign.CENTER),
            DPath(bk(90, 1040, 40, 40), SPARKLE, 100f, 100f, fill = ACC2),
            DPath(bk(664, 1040, 40, 40), SPARKLE, 100f, 100f, fill = ACC2),
        ))

    // W7 -----------------------------------------------------------------------------------------------
    private fun wowOutline() = Design("x-wow-outline", "Contorno delante", DesignFamily.WOW, DOrient.PORTRAIT, 794f, 1123f, 0xFF111111L,
        accents = listOf(WHITE, BUTTER, 0xFFFF6FB5L, 0xFFC6FF3DL), motion = CollageMotion.ZOOM,
        hint = "El texto enorme pasa detrás de la persona y su contorno queda delante: se lee completo y parece 3D",
        els = listOf(
            DPhoto(bk(0, 0, 794, 1123, F), 0),
            DRect(bk(0, 760, 794, 363, F), 0, grad = listOf(0f to 0x00000000L, 1f to 0xB3000000L)),
            DText(bk(0, 150, 794, 420, F), "title", "Título", "Dream", DFont.ANTON, 400f, ACC, DAlign.CENTER, upper = true, behind = 0),
            DText(bk(0, 150, 794, 420, F), "title", "", "Dream", DFont.ANTON, 400f, ACC, DAlign.CENTER, upper = true, stroke = 3f),
            DText(bk(40, 990, 714, 70), "line", "Abajo", "{luogo|Lisboa} — {Mese} {ANNO}", DFont.SERIF_IT, 54f, WHITE, DAlign.CENTER),
            DText(bk(40, 1068, 714, 26), "small", "Pequeño", "{foto} fotos · {giorno_breve}", DFont.MONO_BOLD, 18f, WHITE, DAlign.CENTER, track = 0.3f, upper = true),
        ))

    // W8 -----------------------------------------------------------------------------------------------
    private fun wowSlices() = Design("x-wow-slices", "Foto en tiras", DesignFamily.WOW, DOrient.PORTRAIT, 794f, 1123f, ACC,
        accents = listOf(0xFFFFE14DL, 0xFFC6F04AL, 0xFFFF9EC7L, 0xFF9FD8FFL, 0xFF111111L), accents2 = listOf(0xFF111111L, WHITE, 0xFF2340D9L),
        motion = CollageMotion.ZOOM,
        hint = "La foto cortada en tiras desfasadas, como impresa y vuelta a armar: en el video las tiras llegan desde arriba y desde abajo",
        els = listOf(
            DText(bk(40, 40, 714, 200), "title", "Título", "Good\nvibes", DFont.GROTESK, 110f, ACC2, line = 0.88f),
            DPath(bk(660, 60, 90, 90), SPARKLE, 100f, 100f, fill = ACC2),
            DPhoto(bk(60, 270, 674, 700), 0, slices = 7, shadow = 14f),
            DText(bk(40, 1020, 400, 60), "tag", "Etiqueta", "{luogo|Napoli}", DFont.MONO_BOLD, 24f, ACC, rot = -3f, pill = ACC2, pad = 20f, upper = true, track = 0.1f),
            DText(bk(400, 1030, 354, 50), "date", "Fecha", "{data}", DFont.MONO_BOLD, 22f, ACC2, DAlign.RIGHT, track = 0.2f),
        ))

    // W9 -----------------------------------------------------------------------------------------------
    private fun wowEcho() = Design("x-wow-echo", "Estela", DesignFamily.WOW, DOrient.PORTRAIT, 794f, 1123f, 0xFF0B0B0BL,
        accents = listOf(0xFFC6FF3DL, 0xFFFF2E93L, 0xFF00E5FFL, 0xFFFF5B14L), motion = CollageMotion.DROP,
        hint = "Deporte y baile: la persona recortada con la estela de color detrás, como una foto en movimiento, y el texto hueco gigante",
        els = listOf(
            DPattern(b(0, 0, 794, 1123, F), DPatternKind.SPEED, WHITE, 0f),
            DText(bk(0, 40, 794, 330, F), "title", "Título", "Fast", DFont.ANTON, 360f, ACC, DAlign.CENTER, upper = true, stroke = 4f, skew = -0.12f),
            DPhoto(bk(170, 260, 560, 760), 0, cutout = true, border = 10f, shadow = 20f, echo = 4),
            DText(bk(40, 1040, 714, 50), "stats", "Datos", "{luogo|Pista} · {giorno_breve}", DFont.MONO_BOLD, 26f, WHITE, DAlign.CENTER, track = 0.25f, upper = true),
        ))

    // W10 ----------------------------------------------------------------------------------------------
    private fun wowCircle() = Design("x-wow-circle", "Sale del círculo", DesignFamily.WOW, DOrient.PORTRAIT, 794f, 1123f, ACC,
        accents = listOf(0xFFFF7A1AL, 0xFF2340D9L, 0xFF0F7B5FL, 0xFFE5432DL, 0xFF111111L), accents2 = listOf(CREAM, BUTTER, WHITE),
        motion = CollageMotion.DROP,
        hint = "La foto redonda con la persona que sobresale, el texto que gira alrededor como un sello y el título en cursiva",
        els = listOf(
            DText(bk(52, 160, 690, 690), "ring", "Texto en círculo", "Summer {ANNO} · {luogo|Cerdeña}", DFont.MONO_BOLD, 30f, ACC2, track = 0.2f, upper = true, arc = true),
            DPhoto(bk(127, 235, 540, 540), 0, DShape.CIRCLE, border = 14f, borderColor = CREAM, shadow = 30f, pop = 0.3f),
            DText(bk(30, 860, 734, 150), "title", "Título", "Nosotros dos", DFont.SERIF_IT, 140f, ACC2, DAlign.CENTER),
            DPath(bk(80, 120, 70, 70), SPARKLE, 100f, 100f, fill = BUTTER),
            DPath(bk(650, 800, 60, 60), SPARKLE, 100f, 100f, fill = ACC2),
            DText(bk(40, 1030, 714, 30), "date", "Fecha", "{data}", DFont.MONO_BOLD, 20f, ACC2, DAlign.CENTER, track = 0.3f),
        ))

    // W11 ----------------------------------------------------------------------------------------------
    private fun wowLetters() = Design("x-wow-letters", "Letras y persona", DesignFamily.WOW, DOrient.PORTRAIT, 794f, 1123f, ACC,
        accents = listOf(0xFF111111L, 0xFFF2EDE4L, 0xFF2340D9L, 0xFFFF6FB5L), accents2 = listOf(BUTTER, WHITE, 0xFF111111L),
        motion = CollageMotion.CASCADE,
        hint = "Una palabra con letras-foto (una foto por letra) y la persona de la primera foto recortada delante, grande",
        els = listOf(
            DText(bk(40, 40, 714, 34), "kicker", "Arriba", "{Mese} {ANNO}", DFont.MONO_BOLD, 22f, ACC2, DAlign.CENTER, track = 0.4f, upper = true),
            DPhotoWord(bk(20, 100, 754, 330), "letters", "Palabra", "Verano", DFont.ANTON, 1, gap = 8f, oneLine = true),
            DPhoto(bk(187, 300, 420, 720), 0, cutout = true, border = 14f, shadow = 26f),
            DText(bk(40, 1050, 714, 50), "place", "Lugar", "{luogo|Puglia}", DFont.HAND, 54f, ACC2, DAlign.CENTER, rot = -3f),
        ))

    // W12 ----------------------------------------------------------------------------------------------
    private fun wowCover() = Design("x-wow-cover", "Portada 3D", DesignFamily.WOW, DOrient.PORTRAIT, 794f, 1123f, ACC,
        accents = listOf(0xFFE10600L, 0xFF111111L, 0xFF2340D9L, 0xFFF2EDE4L), accents2 = listOf(WHITE, BUTTER, 0xFF111111L),
        motion = CollageMotion.ZOOM,
        hint = "Portada de revista con la foto enmarcada y la persona que sale sobre el encabezado, con los titulares a los lados",
        els = listOf(
            DText(bk(0, 24, 794, 230, F), "mast", "Encabezado", "Icono", DFont.SERIF, 250f, ACC2, DAlign.CENTER, line = 0.9f, upper = true),
            DPhoto(bk(60, 250, 674, 800), 0, pop = 0.22f, shadow = 20f),
            DText(bk(80, 600, 330, 200), "line1", "Título 1", "Speciale\n{Mese}", DFont.GROTESK, 56f, WHITE, line = 0.95f, shadow = true),
            DText(bk(80, 830, 330, 120), "line2", "Título 2", "Lo mejor\nde {luogo|Roma}", DFont.SERIF_IT, 40f, WHITE, shadow = true),
            DText(bk(520, 920, 190, 110), "num", "Número", "N°{mese_n}", DFont.ANTON, 80f, ACC, DAlign.CENTER, pill = WHITE, pad = 16f, rot = -6f),
            DText(bk(40, 1068, 714, 26), "small", "Pie", "{ANNO} · edición especial", DFont.MONO_BOLD, 18f, ACC2, DAlign.CENTER, track = 0.3f, upper = true),
        ))

    // ---- Stile wow nelle altre famiglie (15/11/2026): persona che esce, colore che spicca sul bianco e nero, riflettore, oro ----

    private fun evGold3d() = Design("x-ev-gold3d", "Felicitaciones 3D", DesignFamily.EVENTS, DOrient.PORTRAIT, 794f, 1123f, 0xFF0B0B0FL,
        accents = listOf(0xFFE7C26BL, 0xFFE9E9F2L, 0xFFF2A7C8L), motion = CollageMotion.DROP,
        hint = "Cumpleaños y aniversarios: la persona sale del círculo dorado, el texto gira alrededor, brillitos y años dorados que cuentan",
        els = listOf(
            DPattern(b(0, 0, 794, 1123, F), DPatternKind.GLITTER, ACC, 0f),
            DText(bk(0, 30, 794, 200, F), "title", "Título", "¡Felicidades!", DFont.SCRIPT, 190f, WHITE, DAlign.CENTER, gradient = GOLD, shine = true, shadow = true),
            DText(bk(107, 250, 580, 580), "ring", "Texto en círculo", "Feliz cumpleaños · {data}", DFont.MONO_BOLD, 26f, 0xFFD9A93BL, track = 0.25f, upper = true, arc = true),
            DPhoto(bk(167, 310, 460, 460), 0, DShape.CIRCLE, border = 10f, borderColor = 0xFFD9A93BL, shadow = 40f, pop = 0.32f),
            DText(bk(0, 830, 794, 200, F), "age", "Años", "50", DFont.ANTON, 200f, WHITE, DAlign.CENTER, gradient = GOLD, shine = true, shadow = true, count = true),
            DText(bk(40, 1030, 714, 70), "name", "Nombre", "Marco", DFont.SERIF_IT, 60f, CREAM, DAlign.CENTER),
        ))

    private fun actJump() = Design("x-act-jump", "Salto 3D", DesignFamily.ACTION, DOrient.PORTRAIT, 794f, 1123f, ACC,
        accents = listOf(0xFFC6FF3DL, 0xFFFF5B14L, 0xFF00E5FFL, 0xFFFFD60AL, WHITE), motion = CollageMotion.DROP,
        hint = "Saltos, drops, clavados: quien salta sale de la foto torcida, el texto hueco gigante y la medida que cuenta en el video",
        els = listOf(
            DPattern(b(0, 0, 794, 1123, F), DPatternKind.SPEED, BLACK, 0f),
            DText(bk(0, 20, 794, 300, F), "title", "Título", "Big air", DFont.ANTON, 260f, BLACK, DAlign.CENTER, upper = true, stroke = 4f, skew = -0.12f, rot = -4f),
            DPhoto(bk(97, 300, 600, 600), 0, rot = -6f, shadow = 34f, pop = 0.4f),
            DText(bk(60, 920, 420, 170), "stat", "Medida", "12 m", DFont.ANTON, 170f, BLACK, skew = -0.1f, count = true),
            DText(bk(470, 960, 280, 60), "tag", "Etiqueta", "{luogo|Bike park}", DFont.MONO_BOLD, 24f, ACC, DAlign.RIGHT, pill = BLACK, pad = 18f, upper = true, rot = 3f),
            DText(bk(470, 1040, 280, 30), "date", "Fecha", "{giorno_breve}", DFont.MONO_BOLD, 20f, BLACK, DAlign.RIGHT, track = 0.2f, upper = true),
        ))

    private fun actColor() = Design("x-act-color", "Color en carrera", DesignFamily.ACTION, DOrient.PORTRAIT, 794f, 1123f, 0xFF111111L,
        accents = listOf(0xFFC6FF3DL, 0xFFFF2E93L, 0xFF00E5FFL, 0xFFFF5B14L), motion = CollageMotion.ZOOM,
        hint = "El mundo en blanco y negro y tú a color, delante del texto de neón (que se lee igual gracias al contorno)",
        els = listOf(
            DPhoto(bk(0, 0, 794, 1123, F), 0, filter = DFilter.BW, front = true, colorPop = true),
            DRect(bk(0, 800, 794, 323, F), 0, grad = listOf(0f to 0x00000000L, 1f to 0xCC000000L)),
            DText(bk(0, 120, 794, 380, F), "title", "Título", "¡Vamos!", DFont.ANTON, 380f, ACC, DAlign.CENTER, upper = true, behind = 0, glow = ACC),
            DText(bk(0, 120, 794, 380, F), "title", "", "¡Vamos!", DFont.ANTON, 380f, ACC, DAlign.CENTER, upper = true, stroke = 3f),
            DText(bk(40, 1000, 714, 40), "stats", "Datos", "{luogo|Carrera} · {data}", DFont.MONO_BOLD, 26f, WHITE, DAlign.CENTER, track = 0.25f, upper = true),
            DText(bk(40, 1050, 714, 50), "note", "Frase", "never stop", DFont.HAND, 46f, ACC, DAlign.CENTER, rot = -2f),
        ))

    private fun paperPop() = Design("x-paper-pop", "Rasgado 3D", DesignFamily.PAPER, DOrient.PORTRAIT, 794f, 1123f, ACC,
        accents = listOf(0xFFC9A77CL, 0xFF9FD8FFL, 0xFFFFB3C7L, 0xFFB9C9A5L), accents2 = listOf(0xFF1A1A1AL, WHITE, 0xFFE5432DL),
        motion = CollageMotion.DROP,
        hint = "La foto sobre un trozo de papel rasgado y la persona que sale de él, con cinta adhesiva y texto a mano",
        els = listOf(
            DPattern(b(0, 0, 794, 1123, F), DPatternKind.PAPER, 0, 0f),
            DText(bk(40, 40, 714, 160), "title", "Título", "¡Qué día!", DFont.HAND, 120f, ACC2, DAlign.CENTER, rot = -4f),
            DTorn(bk(77, 260, 640, 730), CREAM, rot = -3f, seed = 5, shadow = 22f),
            DPhoto(bk(122, 300, 550, 600), 0, rot = -3f, pop = 0.3f),
            DRect(bk(90, 250, 150, 46), TAPE, rot = -30f),
            DRect(bk(560, 940, 150, 46), TAPE, rot = -25f),
            DText(bk(130, 920, 420, 50), "date", "Fecha", "{data} · {luogo|Lago de Garda}", DFont.MONO_BOLD, 20f, 0xFF3A3530L, rot = -3f, track = 0.15f, upper = true),
            DPath(bk(660, 200, 80, 80), SPARKLE, 100f, 100f, fill = ACC2),
        ))

    private fun trendColorPop() = Design("x-trend-colorpop", "Color pop", DesignFamily.TREND, DOrient.PORTRAIT, 794f, 1123f, 0xFF111111L,
        accents = listOf(BUTTER, 0xFFFF6FB5L, WHITE, 0xFFC6FF3DL), motion = CollageMotion.ZOOM,
        hint = "Todo en blanco y negro menos tú, a color: el texto a mano, las calcomanías y la fecha como en los reels",
        els = listOf(
            DPhoto(bk(0, 0, 794, 1123, F), 0, filter = DFilter.BW, front = true, colorPop = true),
            DText(bk(40, 60, 600, 160), "title", "Frase", "main\ncharacter", DFont.HAND, 90f, ACC, rot = -5f, line = 0.85f, shadow = true),
            DPath(bk(640, 80, 90, 90), SPARKLE, 100f, 100f, fill = ACC),
            DPath(bk(80, 900, 60, 60), SPARKLE, 100f, 100f, fill = WHITE),
            DText(bk(40, 990, 360, 60), "tag", "Etiqueta", "{luogo|Milano}", DFont.MONO_BOLD, 24f, BLACK, rot = -3f, pill = ACC, pad = 18f, upper = true, track = 0.1f),
            DText(bk(430, 1000, 324, 50), "date", "Fecha", "{giorno_breve}", DFont.MONO_BOLD, 24f, WHITE, DAlign.RIGHT, rot = 3f, pill = 0x66000000L, pad = 16f, upper = true),
        ))

    private fun trendHoloSlices() = Design("x-trend-holo-slices", "Tiras Y2K", DesignFamily.TREND, DOrient.PORTRAIT, 794f, 1123f, 0xFFEFEAF8L,
        accents = listOf(0xFF1A1033L, 0xFF2340D9L, 0xFFFF2E93L), motion = CollageMotion.ZOOM,
        hint = "Fondo holográfico, la foto en tiras desfasadas y el texto cromado con el reflejo que pasa",
        els = listOf(
            DPattern(b(0, 0, 794, 1123, F), DPatternKind.HOLO, 0, 0f),
            DText(bk(0, 30, 794, 240, F), "title", "Título", "Vibes", DFont.ARCHIVO, 200f, WHITE, DAlign.CENTER, gradient = CHROME, shine = true, glow = 0x99FFFFFFL, upper = true, skew = -0.06f),
            DPhoto(bk(70, 270, 654, 700), 0, slices = 6, shadow = 16f),
            DText(bk(60, 1010, 320, 56), "tag", "Etiqueta", "{luogo|Ibiza}", DFont.MONO_BOLD, 22f, WHITE, rot = -4f, pill = ACC, pad = 18f, upper = true, track = 0.1f),
            DText(bk(414, 1020, 320, 56), "date", "Fecha", "{giorno_breve}", DFont.MONO_BOLD, 22f, BLACK, DAlign.RIGHT, rot = 4f, pill = WHITE, pad = 18f, upper = true),
            DPath(bk(670, 250, 70, 70), SPARKLE, 100f, 100f, fill = WHITE),
        ))

    private fun posterSpot() = Design("x-poster-spot", "Reflector", DesignFamily.POSTER, DOrient.PORTRAIT, 794f, 1123f, 0xFF0B0B0BL,
        accents = listOf(WHITE, BUTTER, 0xFFFF3B30L, 0xFF7CC8FFL), motion = CollageMotion.ZOOM,
        hint = "Cartel de cine: el fondo desenfocado y oscuro, tú nítido bajo el reflector delante del título enorme, los créditos abajo",
        els = listOf(
            DPhoto(bk(0, 0, 794, 1123, F), 0, filter = DFilter.BLUR, dim = 0.45f, front = true, colorPop = true),
            DText(bk(0, 140, 794, 420, F), "title", "Título", "Legend", DFont.ANTON, 330f, ACC, DAlign.CENTER, upper = true, behind = 0),
            DText(bk(0, 140, 794, 420, F), "title", "", "Legend", DFont.ANTON, 330f, ACC, DAlign.CENTER, upper = true, stroke = 2.5f),
            DText(bk(40, 60, 714, 30), "kicker", "Arriba", "{luogo|Roma} presenta", DFont.MONO_BOLD, 20f, WHITE, DAlign.CENTER, track = 0.4f, upper = true),
            DText(bk(60, 990, 674, 60), "credits", "Créditos", "una producción familiar · dirigida por quien tiene el teléfono · música en vivo", DFont.MONO, 14f, 0xCCFFFFFFL, DAlign.CENTER, track = 0.15f, upper = true, wrap = true),
            DText(bk(40, 1060, 714, 34), "date", "Fecha", "en cines desde el {data}", DFont.SERIF_IT, 28f, ACC, DAlign.CENTER),
        ))

    private fun posterDuo3d() = Design("x-poster-duo3d", "Duotono 3D", DesignFamily.POSTER, DOrient.PORTRAIT, 794f, 1123f, ACC2,
        accents = listOf(0xFFFFB3C7L, 0xFFFFE14DL, 0xFF9FEFFFL, 0xFFC6FF3DL), accents2 = listOf(0xFF2340D9L, 0xFF111111L, 0xFFE5432DL, 0xFF5B2A86L),
        motion = CollageMotion.ZOOM,
        hint = "Póster en dos colores contigo a todo color delante del número gigante del año",
        els = listOf(
            DPhoto(bk(0, 0, 794, 1123, F), 0, filter = DFilter.DUOTONE, front = true, colorPop = true),
            DText(bk(0, 60, 794, 640, F), "num", "Número", "{AA|26}", DFont.ANTON, 640f, ACC, DAlign.CENTER, behind = 0),
            DText(bk(40, 980, 714, 80), "title", "Título", "{luogo|Summer}", DFont.SERIF_IT, 76f, ACC, DAlign.CENTER),
            DText(bk(40, 1062, 714, 26), "small", "Pequeño", "{foto} fotos · {ANNO}", DFont.MONO_BOLD, 18f, ACC, DAlign.CENTER, track = 0.3f, upper = true),
        ))

    private fun magPop() = Design("x-mag-pop", "Portada a color", DesignFamily.MAGAZINE, DOrient.PORTRAIT, 794f, 1123f, 0xFF111111L,
        accents = listOf(0xFFE10600L, BUTTER, WHITE, 0xFF2340D9L), motion = CollageMotion.ZOOM,
        hint = "Portada de moda: la foto en blanco y negro, tú a color delante del encabezado, los titulares a los lados",
        els = listOf(
            DPhoto(bk(0, 0, 794, 1123, F), 0, filter = DFilter.BW, front = true, colorPop = true),
            DText(bk(0, 20, 794, 250, F), "mast", "Encabezado", "Mood", DFont.SERIF, 260f, ACC, DAlign.CENTER, line = 0.9f, upper = true, behind = 0),
            DText(bk(50, 560, 300, 160), "line1", "Título 1", "La temporada\nde {luogo|Milán}", DFont.GROTESK, 40f, WHITE, line = 0.95f, shadow = true),
            DText(bk(50, 760, 300, 120), "line2", "Título 2", "{foto} tomas\nimperdibles", DFont.SERIF_IT, 36f, WHITE, shadow = true),
            DText(bk(540, 900, 200, 110), "num", "Número", "N°{mese_n}", DFont.ANTON, 76f, BLACK, DAlign.CENTER, pill = ACC, pad = 16f, rot = -6f),
            DText(bk(40, 1068, 714, 26), "small", "Pie", "{Mese} {ANNO} · edición especial", DFont.MONO_BOLD, 18f, WHITE, DAlign.CENTER, track = 0.3f, upper = true),
        ))

    private fun magQuote() = Design("x-mag-quote", "Entrevista", DesignFamily.MAGAZINE, DOrient.PORTRAIT, 794f, 1123f, ACC,
        accents = listOf(0xFFF2EDE4L, 0xFFFFE14DL, 0xFFFFB3C7L, 0xFF9FD8FFL), accents2 = listOf(0xFF111111L, 0xFFE5432DL, 0xFF2340D9L),
        motion = CollageMotion.DROP,
        hint = "Página de entrevista: comillas gigantes, la frase en cursiva grande y la persona recortada que entra por el lado",
        els = listOf(
            DText(bk(30, 10, 400, 400), "", "", "“", DFont.SERIF, 520f, ACC2),
            DText(bk(50, 260, 440, 420), "quote", "Frase", "La felicidad es ahora, no mañana.", DFont.SERIF_IT, 70f, ACC2, line = 1f, wrap = true),
            DPhoto(bk(380, 330, 414, 793), 0, cutout = true, border = 0f, shadow = 26f),
            DText(bk(50, 720, 330, 60), "name", "Nombre", "Julia, {AA} años", DFont.MONO_BOLD, 22f, ACC, pill = ACC2, pad = 18f, upper = true, rot = -3f),
            DText(bk(50, 1020, 400, 30), "small", "Pie", "entrevista · {luogo|Turín} · {Mese} {ANNO}", DFont.MONO, 16f, ACC2, track = 0.2f, upper = true),
            DRect(bk(50, 990, 120, 4), ACC2),
        ))

    private fun bento3d() = Design("x-bento-3d", "Bento 3D", DesignFamily.BENTO, DOrient.SQUARE, 756f, 756f, 0xFFEDEFF3L,
        accents = listOf(0xFF2340D9L, 0xFFE5432DL, 0xFF111111L, 0xFF0F7B5FL), motion = CollageMotion.CASCADE,
        hint = "Bento con la persona que sale de la casilla grande, un número que cuenta, una segunda foto y un sello que gira",
        els = listOf(
            DText(bk(28, 24, 420, 120), "title", "Título", "Weekend", DFont.GROTESK, 92f, 0xFF111111L),
            DPhoto(bk(28, 170, 420, 560), 0, radius = 30f, shadow = 14f, pop = 0.22f),
            DRect(bk(466, 24, 262, 240), ACC, radius = 30f),
            DText(bk(486, 40, 222, 150), "count", "Número", "{foto}", DFont.ANTON, 140f, WHITE, DAlign.CENTER, count = true),
            DText(bk(486, 196, 222, 40), "countl", "Debajo del número", "ricordi", DFont.MONO_BOLD, 22f, WHITE, DAlign.CENTER, track = 0.2f, upper = true),
            DPhoto(bk(466, 282, 262, 210), 1, radius = 30f, shadow = 14f),
            DRect(bk(466, 510, 262, 220), 0xFF111111L, radius = 30f),
            DText(bk(497, 521, 200, 200), "ring", "Sello", "{luogo|Cerdeña} · {ANNO}", DFont.MONO_BOLD, 17f, WHITE, track = 0.15f, upper = true, arc = true),
            DPath(bk(567, 591, 60, 60), SPARKLE, 100f, 100f, fill = ACC),
        ))

    private fun scrap3d() = Design("x-scrap-3d", "Recorte 3D", DesignFamily.SCRAP, DOrient.PORTRAIT, 794f, 1123f, ACC,
        accents = listOf(0xFFFFB3C7L, 0xFFFFE14DL, 0xFF9FD8FFL, 0xFFC6F04AL), motion = CollageMotion.DROP,
        hint = "Collage recortado: la persona recortada como una gran calcomanía con estela, tiras de papel, polaroids a los lados y letras recortadas",
        els = listOf(
            DPattern(b(0, 0, 794, 1123, F), DPatternKind.PAPER, 0, 0f),
            DTorn(bk(-20, 180, 834, 160, F), 0xFF111111L, rot = -4f, seed = 11, shadow = 10f),
            DTorn(bk(-20, 760, 834, 140, F), WHITE, rot = 3f, seed = 13, shadow = 10f),
            DRansom(bk(40, 40, 714, 130), "title", "Letras recortadas", "best day", seed = 9, rot = -3f),
            DPhoto(bk(40, 380, 250, 300), 1, rot = -8f, border = 12f, bottomPad = 50f, shadow = 18f),
            DPhoto(bk(520, 420, 240, 290), 2, rot = 7f, border = 12f, bottomPad = 50f, shadow = 18f),
            DPhoto(bk(177, 230, 440, 720), 0, cutout = true, border = 16f, shadow = 26f, echo = 2),
            DText(bk(40, 990, 714, 80), "note", "Frase", "{luogo|con los amigos} · {giorno_breve}", DFont.HAND, 56f, 0xFF111111L, DAlign.CENTER, rot = -2f),
            DPath(bk(660, 180, 80, 80), SPARKLE, 100f, 100f, fill = 0xFF111111L),
        ))

    private fun trip3d() = Design("x-trip-3d", "Postal 3D", DesignFamily.THEMES, DOrient.PORTRAIT, 794f, 1123f, ACC,
        accents = listOf(0xFF8FD3FFL, 0xFFF4C27AL, 0xFFE07A5FL, 0xFF9DD6B8L), motion = CollageMotion.DROP,
        hint = "Viajes: la postal con la persona que sale de ella, el sello postal que gira y los saludos escritos a mano",
        els = listOf(
            DRect(bk(57, 210, 680, 780), WHITE, rot = -3f, shadow = 30f),
            DPhoto(bk(87, 240, 620, 580), 0, rot = -3f, pop = 0.3f),
            DText(bk(97, 840, 600, 90), "title", "Saludos", "¡Saludos desde {luogo|Positano}!", DFont.HAND, 70f, 0xFF1A1A1AL, DAlign.CENTER, rot = -3f),
            DText(bk(110, 930, 560, 30), "date", "Fecha", "{data}", DFont.MONO_BOLD, 18f, 0xFF555555L, DAlign.CENTER, rot = -3f, track = 0.25f),
            DText(bk(560, 60, 200, 200), "ring", "Sello", "{luogo|Positano} · {ANNO}", DFont.MONO_BOLD, 17f, 0xFF1A1A1AL, track = 0.12f, upper = true, arc = true, rot = 12f),
            DText(bk(600, 120, 120, 80), "", "", "{giorno_breve}", DFont.MONO_BOLD, 20f, 0xFF1A1A1AL, DAlign.CENTER, rot = 12f, upper = true),
            DText(bk(40, 40, 480, 140), "kicker", "Arriba", "Wish you\nwere here", DFont.SERIF_IT, 64f, WHITE, line = 0.95f, shadow = true),
        ))

    private fun loveDouble() = Design("x-love-double", "Amor", DesignFamily.THEMES, DOrient.PORTRAIT, 794f, 1123f, ACC,
        accents = listOf(0xFFF7E8E3L, 0xFFF3EEE6L, WHITE, 0xFF1A1414L), accents2 = listOf(0xFFB0303AL, 0xFF1A1A1AL, 0xFFD9A93BL),
        motion = CollageMotion.ZOOM,
        hint = "Doble exposición romántica: su silueta (primera foto) rellena con el atardecer o el mar (segunda foto) y la cursiva dorada",
        els = listOf(
            DPhoto(bk(97, 60, 600, 790), 0, fill = 1),
            DText(bk(0, 820, 794, 190, F), "title", "Título", "Te amo", DFont.SCRIPT, 170f, WHITE, DAlign.CENTER, gradient = listOf(0xFFE9C46AL, 0xFF9A6A12L, 0xFFD4A537L, 0xFF6E4A0AL, 0xFFC99A2EL), shine = true, shadow = true),
            DText(bk(40, 1020, 714, 40), "names", "Nombres", "Ana & Luis", DFont.SERIF_IT, 40f, ACC2, DAlign.CENTER),
            DText(bk(40, 1068, 714, 26), "date", "Fecha", "{data}", DFont.MONO, 18f, ACC2, DAlign.CENTER, track = 0.3f),
        ))

    // ---- Griglie, Forme, Polaroid, Editoriale rifatte nello stile nuovo (15/11/2026) ----

    /** Contorno a cuore (0..1). */
    private val HEART_PTS: List<Pt> by lazy {
        val raw = (0 until 72).map { i ->
            val t = i / 72.0 * 2 * Math.PI
            val x = 16 * Math.pow(kotlin.math.sin(t), 3.0)
            val y = 13 * kotlin.math.cos(t) - 5 * kotlin.math.cos(2 * t) - 2 * kotlin.math.cos(3 * t) - kotlin.math.cos(4 * t)
            x to -y
        }
        norm(raw)
    }

    /** Fiore a [petals] petali tondi. */
    private fun flowerPts(petals: Int): List<Pt> = norm((0 until 120).map { i ->
        val t = i / 120.0 * 2 * Math.PI
        val r = 0.78 + 0.22 * kotlin.math.abs(kotlin.math.cos(petals * t / 2))
        r * kotlin.math.cos(t) to r * kotlin.math.sin(t)
    })

    /** Forma morbida irregolare. */
    private fun blobPts(seed: Int): List<Pt> = norm((0 until 90).map { i ->
        val t = i / 90.0 * 2 * Math.PI
        val r = 1 + 0.09 * kotlin.math.sin(3 * t + seed) + 0.06 * kotlin.math.sin(5 * t + seed * 2.1) + 0.04 * kotlin.math.cos(2 * t + seed * 0.7)
        r * kotlin.math.cos(t) to r * kotlin.math.sin(t)
    })

    private fun norm(raw: List<Pair<Double, Double>>): List<Pt> {
        val x0 = raw.minOf { it.first }; val x1 = raw.maxOf { it.first }
        val y0 = raw.minOf { it.second }; val y1 = raw.maxOf { it.second }
        return raw.map { Pt(((it.first - x0) / (x1 - x0)).toFloat(), ((it.second - y0) / (y1 - y0)).toFloat()) }
    }

    /** Riquadro della scritta nel bordo bianco sotto una polaroid ruotata di [rot] (gira con lei). */
    private fun capBox(x: Float, y: Float, w: Float, h: Float, border: Float, pad: Float, rot: Float): DBox {
        val dy = h / 2 + border + pad / 2
        val a = Math.toRadians(rot.toDouble())
        val px = x + w / 2 - dy * kotlin.math.sin(a).toFloat(); val py = y + h / 2 + dy * kotlin.math.cos(a).toFloat()
        return bk(px - w / 2, py - pad * 0.4f, w, pad * 0.8f)
    }

    // Griglie ----------------------------------------------------------------------------------------------------------
    private fun grid9(): Design {
        val els = arrayListOf<DEl>(
            DText(bk(40, 40, 500, 90), "title", "Título", "Photo dump", DFont.GROTESK, 76f, ACC2),
            DText(bk(454, 70, 300, 40), "date", "Fecha", "{Mese} {ANNO}", DFont.MONO_BOLD, 22f, ACC2, DAlign.RIGHT, track = 0.2f, upper = true),
        )
        var slot = 0
        for (i in 0 until 9) {
            val x = 40f + (i % 3) * 242f; val y = 160f + (i / 3) * 302f
            if (i == 4) {
                els += DRect(bk(x, y, 230, 290), ACC, radius = 22f)
                els += DText(bk(x + 14, y + 40, 202, 140), "center", "Al centro", "{luogo|Estate}", DFont.SERIF_IT, 50f, WHITE, DAlign.CENTER, wrap = true)
                els += DText(bk(x + 14, y + 200, 202, 60), "count", "Número", "{foto} fotos", DFont.MONO_BOLD, 22f, WHITE, DAlign.CENTER, track = 0.2f, upper = true)
            } else els += DPhoto(bk(x, y, 230, 290), slot++, radius = 22f)
        }
        return Design("x-grid-9", "Mosaico con título", DesignFamily.GRID, DOrient.PORTRAIT, 794f, 1123f, 0xFFF4F1EAL,
            accents = listOf(0xFFE5432DL, 0xFF2340D9L, 0xFF111111L, 0xFF0F7B5FL), accents2 = listOf(0xFF111111L, 0xFF2340D9L),
            els = els, motion = CollageMotion.CASCADE,
            hint = "Ocho fotos con las esquinas redondas y una casilla de color al centro con el lugar y cuántas fotos")
    }

    private fun gridMasonry(): Design {
        val els = arrayListOf<DEl>(
            DText(bk(40, 30, 714, 100), "title", "Título", "Moodboard", DFont.SERIF_IT, 96f, ACC2),
            DText(bk(40, 112, 714, 26), "date", "Fecha", "{luogo|París} · {Mese} {ANNO}", DFont.MONO_BOLD, 18f, ACC2, track = 0.25f, upper = true),
        )
        val left = listOf(400f, 280f, 230f); val right = listOf(240f, 390f, 280f)
        var y = 160f; var slot = 0
        left.forEach { h -> els += DPhoto(bk(40, y, 347, h), slot++, radius = 24f); y += h + 14 }
        y = 160f
        right.forEach { h -> els += DPhoto(bk(407, y, 347, h), slot++, radius = 24f); y += h + 14 }
        els += DText(bk(430, 380, 220, 56), "tag", "Etiqueta", "mood", DFont.HAND, 44f, BLACK, DAlign.CENTER, rot = -6f, pill = BUTTER, pad = 18f)
        els += DPath(bk(330, 560, 70, 70), SPARKLE, 100f, 100f, fill = ACC)
        return Design("x-grid-masonry", "Tablero", DesignFamily.GRID, DOrient.PORTRAIT, 794f, 1123f, 0xFFF7F4EFL,
            accents = listOf(0xFFE5432DL, 0xFF2340D9L, BUTTER), accents2 = listOf(0xFF111111L, 0xFF5B2A86L),
            els = els, motion = CollageMotion.CASCADE,
            hint = "Tablero tipo Pinterest: dos columnas de fotos de distinta altura, esquinas redondas, una calcomanía a mano")
    }

    private fun gridPop() = Design("x-grid-pop", "Cuadrícula 3D", DesignFamily.GRID, DOrient.PORTRAIT, 794f, 1123f, ACC,
        accents = listOf(0xFF111111L, 0xFF2340D9L, 0xFFE5432DL, 0xFFC6F04AL), accents2 = listOf(WHITE, BUTTER, 0xFF111111L),
        motion = CollageMotion.CASCADE,
        hint = "Cuatro fotos en cuadrícula y la persona de la primera que sale de su casilla y pasa delante del título",
        els = listOf(
            DText(bk(0, 30, 794, 240, F), "title", "Título", "Crew", DFont.ANTON, 250f, ACC2, DAlign.CENTER, upper = true),
            DPhoto(bk(40, 300, 349, 360), 1, radius = 18f),
            DPhoto(bk(405, 300, 349, 360), 2, radius = 18f),
            DPhoto(bk(40, 676, 349, 360), 3, radius = 18f),
            DPhoto(bk(405, 676, 349, 360), 0, radius = 18f, pop = 0.45f),
            DText(bk(40, 1060, 714, 30), "date", "Fecha", "{luogo|Dolomitas} · {data}", DFont.MONO_BOLD, 20f, ACC2, DAlign.CENTER, track = 0.25f, upper = true),
        ))

    private fun gridType(): Design {
        val words = listOf("We", "were", "here", "{AA|26}")
        val els = arrayListOf<DEl>()
        var slot = 0; var w = 0
        for (i in 0 until 9) {
            val x = 30f + (i % 3) * 250f; val y = 40f + (i / 3) * 350f
            if (i % 2 == 0) els += DPhoto(bk(x, y, 234, 334), slot++)
            else {
                els += DRect(bk(x, y, 234, 334), if (w % 2 == 0) ACC else ACC2)
                els += DText(bk(x + 10, y + 100, 214, 140), "w$w", "Parola ${w + 1}", words[w], DFont.ANTON, 110f, if (w % 2 == 0) ACC2 else ACC, DAlign.CENTER, upper = true)
                w++
            }
        }
        return Design("x-grid-type", "Cuadrícula de palabras", DesignFamily.GRID, DOrient.PORTRAIT, 794f, 1123f, WHITE,
            accents = listOf(0xFFE5432DL, 0xFF2340D9L, 0xFFC6F04AL, BUTTER), accents2 = listOf(0xFF111111L, CREAM, 0xFF2340D9L),
            els = els, motion = CollageMotion.CASCADE,
            hint = "Tablero de fotos y casillas de color con una palabra cada una (se leen en fila: We were here 26), para cambiar en Textos")
    }

    // Forme ------------------------------------------------------------------------------------------------------------
    private fun shapeArch() = Design("x-shape-arch", "Arcos 3D", DesignFamily.SHAPES, DOrient.PORTRAIT, 794f, 1123f, ACC,
        accents = listOf(0xFFE07A5FL, 0xFFB9C9A5L, 0xFFF4C7C3L, 0xFF8FD3FFL), accents2 = listOf(CREAM, 0xFF1A1A1AL, 0xFF6B2E1EL),
        motion = CollageMotion.DROP,
        hint = "Un arco alto con la persona que sale de él, dos círculos, un sello que gira y formas suaves detrás",
        els = listOf(
            DRect(bk(60, 150, 420, 420), ACC2, poly = blobPts(2)),
            DRect(bk(420, 640, 340, 340), ACC2, poly = blobPts(5)),
            DPhoto(bk(207, 230, 380, 600), 0, DShape.ARCH, shadow = 24f, pop = 0.25f),
            DPhoto(bk(60, 700, 220, 220), 1, DShape.CIRCLE, border = 8f, borderColor = CREAM, shadow = 16f),
            DText(bk(520, 70, 240, 240), "ring", "Sello", "{luogo|Marrakech} · {ANNO}", DFont.MONO_BOLD, 18f, ACC2, track = 0.15f, upper = true, arc = true),
            DPhoto(bk(560, 110, 160, 160), 2, DShape.CIRCLE),
            DText(bk(40, 960, 714, 120), "title", "Título", "Golden hour", DFont.SERIF_IT, 100f, ACC2, DAlign.CENTER),
        ))

    private fun shapeHeart() = Design("x-shape-heart", "Corazón", DesignFamily.SHAPES, DOrient.PORTRAIT, 794f, 1123f, ACC,
        accents = listOf(0xFFE5243BL, 0xFFFF6FB5L, 0xFF111111L, 0xFFF7E8E3L), accents2 = listOf(WHITE, CREAM, 0xFFE5243BL),
        motion = CollageMotion.ZOOM,
        hint = "La foto en forma de corazón con borde, brillitos y la cursiva con reflejo; los nombres abajo",
        els = listOf(
            DPattern(b(0, 0, 794, 1123, F), DPatternKind.GLITTER, ACC2, 0f),
            DText(bk(0, 40, 794, 200, F), "title", "Título", "Love you", DFont.SCRIPT, 170f, ACC2, DAlign.CENTER, shine = true, shadow = true),
            DPhoto(bk(97, 260, 600, 560), 0, poly = HEART_PTS, border = 14f, borderColor = ACC2, shadow = 30f),
            DText(bk(40, 880, 714, 90), "names", "Nombres", "Ana & Luis", DFont.SERIF_IT, 76f, ACC2, DAlign.CENTER),
            DText(bk(40, 990, 714, 30), "date", "Fecha", "{data}", DFont.MONO_BOLD, 20f, ACC2, DAlign.CENTER, track = 0.3f),
        ))

    private fun shapeBubbles() = Design("x-shape-bubbles", "Burbujas", DesignFamily.SHAPES, DOrient.PORTRAIT, 794f, 1123f, ACC,
        accents = listOf(0xFFFFE14DL, 0xFFC6F04AL, 0xFFFF9EC7L, 0xFF9FD8FFL), accents2 = listOf(0xFF111111L, 0xFF2340D9L, 0xFFE5432DL),
        motion = CollageMotion.CASCADE,
        hint = "Fotos en burbujas de distintos tamaños con los anillos que las siguen, título grueso y una calcomanía",
        els = listOf(
            DText(bk(40, 30, 714, 140), "title", "Título", "Bubbly", DFont.GROTESK, 130f, ACC2),
            DRect(bk(70, 190, 480, 480), ACC2, circle = true, stroke = 3f),
            DPhoto(bk(90, 210, 440, 440), 0, DShape.CIRCLE),
            DRect(bk(462, 502, 300, 300), ACC2, circle = true, stroke = 3f),
            DPhoto(bk(482, 522, 260, 260), 1, DShape.CIRCLE),
            DPhoto(bk(120, 720, 220, 220), 2, DShape.CIRCLE, border = 8f, borderColor = WHITE),
            DPhoto(bk(580, 230, 150, 150), 3, DShape.CIRCLE, border = 6f, borderColor = WHITE),
            DText(bk(380, 860, 360, 70), "tag", "Calcomanía", "pop!", DFont.HAND, 60f, ACC, DAlign.CENTER, rot = -8f, pill = ACC2, pad = 22f),
            DText(bk(40, 1050, 714, 30), "date", "Fecha", "{luogo|Rímini} · {data}", DFont.MONO_BOLD, 20f, ACC2, DAlign.CENTER, track = 0.25f, upper = true),
        ))

    private fun shapeFlower() = Design("x-shape-flower", "Flor", DesignFamily.SHAPES, DOrient.PORTRAIT, 794f, 1123f, ACC,
        accents = listOf(0xFFF6EEDFL, 0xFFFFE0EBL, 0xFFE3F1DCL, 0xFFFFF1B8L), accents2 = listOf(0xFFE5432DL, 0xFF2340D9L, 0xFF1E4D3AL),
        motion = CollageMotion.DROP,
        hint = "La foto en forma de flor con la persona que sobresale de los pétalos, una florecita y el texto a mano",
        els = listOf(
            DRect(bk(102, 180, 590, 590), ACC2, poly = flowerPts(8)),
            DPhoto(bk(132, 210, 530, 530), 0, poly = flowerPts(8), pop = 0.22f),
            DPhoto(bk(540, 700, 200, 200), 1, poly = flowerPts(6), border = 8f, borderColor = WHITE, shadow = 14f, rot = 10f),
            DText(bk(40, 40, 714, 120), "title", "Título", "en flor", DFont.HAND, 110f, ACC2, rot = -4f),
            DText(bk(40, 900, 500, 120), "line", "Frase", "{Mese} en {luogo|casa}", DFont.SERIF_IT, 60f, ACC2),
            DPath(bk(60, 800, 70, 70), SPARKLE, 100f, 100f, fill = ACC2),
        ))

    private fun shapeBlob() = Design("x-shape-blob", "Formas suaves", DesignFamily.SHAPES, DOrient.PORTRAIT, 794f, 1123f, ACC,
        accents = listOf(0xFFF2EDE4L, 0xFF111111L, 0xFFE3F1DCL, 0xFFFFE0EBL), accents2 = listOf(0xFFFF7A1AL, 0xFF2340D9L, 0xFFC6F04AL, 0xFFE5432DL),
        motion = CollageMotion.CASCADE,
        hint = "Tres fotos en formas suaves que se superponen, manchas de color detrás y el título enorme",
        els = listOf(
            DRect(bk(380, 120, 400, 380), ACC2, poly = blobPts(7)),
            DRect(bk(20, 620, 380, 360), ACC2, poly = blobPts(3)),
            DPhoto(bk(60, 180, 440, 460), 0, poly = blobPts(1), shadow = 20f),
            DPhoto(bk(420, 420, 330, 340), 1, poly = blobPts(4), shadow = 20f, border = 8f, borderColor = WHITE),
            DPhoto(bk(180, 700, 300, 300), 2, poly = blobPts(9), shadow = 20f),
            DText(bk(40, 40, 714, 130), "title", "Título", "Soft days", DFont.SERIF_IT, 110f, 0xFF111111L),
            DText(bk(480, 1010, 274, 30), "date", "Fecha", "{data}", DFont.MONO_BOLD, 20f, 0xFF111111L, DAlign.RIGHT, track = 0.25f),
        ))

    // Polaroid ---------------------------------------------------------------------------------------------------------
    private fun polaStack(): Design {
        val x = 147f; val y = 260f; val w = 500f; val h = 540f; val rot = -4f
        return Design("x-pola-stack", "Pila 3D", DesignFamily.POLAROID, DOrient.PORTRAIT, 794f, 1123f, ACC,
            accents = listOf(0xFF6E8584L, 0xFF8A7F74L, 0xFF3A3F47L, 0xFFB7A694L), motion = CollageMotion.DROP,
            hint = "Una pila de polaroids en la pared: la de delante tiene a la persona que sale de ella y el texto a mano en el borde",
            els = listOf(
                DPattern(b(0, 0, 794, 1123, F), DPatternKind.WALL, BLACK, 0f),
                DPhoto(bk(120, 300, 480, 500), 2, rot = 9f, border = 20f, bottomPad = 110f, shadow = 20f),
                DPhoto(bk(190, 280, 480, 500), 1, rot = -11f, border = 20f, bottomPad = 110f, shadow = 20f),
                DPhoto(bk(x, y, w, h), 0, rot = rot, border = 22f, bottomPad = 130f, shadow = 30f, pop = 0.3f),
                DText(capBox(x, y, w, h, 22f, 130f, rot), "cap", "Texto en la polaroid", "{luogo|nosotros} · {giorno_breve}", DFont.HAND, 56f, 0xFF1A1A1AL, DAlign.CENTER, rot = rot),
                DRect(bk(330, 220, 140, 44), TAPE, rot = -8f),
                DText(bk(40, 40, 714, 130), "title", "Título", "Memories", DFont.SCRIPT, 130f, WHITE, DAlign.CENTER, shadow = true),
            ))
    }

    private fun polaString(): Design {
        val els = arrayListOf<DEl>(
            DPattern(b(0, 0, 794, 1123, F), DPatternKind.GLITTER, 0xFFFFD58AL, 0f),
            DPath(bk(0, 150, 794, 80, F), "M0 10 C200 70 594 70 794 10", 794f, 80f, stroke = 0xFFEDE3D0L, width = 3f),
            DPath(bk(0, 570, 794, 80, F), "M0 10 C200 70 594 70 794 10", 794f, 80f, stroke = 0xFFEDE3D0L, width = 3f),
        )
        val row1 = listOf(40f, 225f, 410f, 595f); val row2 = listOf(110f, 320f, 530f)
        var slot = 0
        row1.forEachIndexed { i, x ->
            val sag = if (i == 0 || i == 3) 20f else 45f
            val r = if (i % 2 == 0) -5f else 5f
            els += DPhoto(bk(x + 12, 175 + sag, 140, 160), slot, rot = r, border = 10f, bottomPad = 40f, shadow = 14f)
            els += DRect(bk(x + 72, 160 + sag, 18, 40), 0xFFC8A27AL, radius = 3f, rot = r)
            slot++
        }
        row2.forEachIndexed { i, x ->
            val sag = if (i == 1) 50f else 30f
            val r = if (i % 2 == 0) 6f else -4f
            els += DPhoto(bk(x + 12, 595 + sag, 150, 170), slot, rot = r, border = 10f, bottomPad = 40f, shadow = 14f)
            els += DRect(bk(x + 77, 580 + sag, 18, 40), 0xFFC8A27AL, radius = 3f, rot = r)
            slot++
        }
        els += DText(bk(0, 940, 794, 160, F), "title", "Título", "Recuerdos", DFont.SCRIPT, 150f, WHITE, DAlign.CENTER, gradient = GOLD, shine = true, shadow = true)
        els += DText(bk(40, 1080, 714, 26), "date", "Fecha", "{luogo|en casa} · {ANNO}", DFont.MONO_BOLD, 18f, 0xFFEDE3D0L, DAlign.CENTER, track = 0.3f, upper = true)
        return Design("x-pola-string", "Polaroids colgadas", DesignFamily.POLAROID, DOrient.PORTRAIT, 794f, 1123f, ACC,
            accents = listOf(0xFF1B1B22L, 0xFF22303AL, 0xFF3A2430L), els = els, motion = CollageMotion.DROP,
            hint = "Siete polaroids colgadas de hilos con pinzas, lucecitas que brillan y «Recuerdos» en dorado")
    }

    private fun polaInstax(): Design {
        val els = arrayListOf<DEl>(
            DPattern(b(0, 0, 794, 1123, F), DPatternKind.HOLO, 0, 0f),
            DText(bk(0, 30, 794, 170, F), "title", "Título", "Best of {ANNO}", DFont.ARCHIVO, 110f, ACC, DAlign.CENTER, shine = true, upper = true),
        )
        val caps = listOf("{luogo|qui}", "noi", "{Mese}", "wow", "ancora!", "{giorno_breve}")
        for (i in 0 until 6) {
            val x = 50f + (i % 3) * 240f; val y = 230f + (i / 3) * 420f
            val r = listOf(-4f, 3f, -2f, 5f, -5f, 2f)[i]
            els += DPhoto(bk(x, y, 210, 270), i, rot = r, border = 12f, bottomPad = 70f, shadow = 16f)
            els += DText(capBox(x, y, 210f, 270f, 12f, 70f, r), "c$i", "Scritta ${i + 1}", caps[i], DFont.HAND, 40f, 0xFF1A1A1AL, DAlign.CENTER, rot = r)
        }
        els += DPath(bk(690, 190, 70, 70), SPARKLE, 100f, 100f, fill = WHITE)
        return Design("x-pola-instax", "Instax", DesignFamily.POLAROID, DOrient.PORTRAIT, 794f, 1123f, 0xFFEFEAF8L,
            accents = listOf(0xFF1A1033L, 0xFF2340D9L, 0xFFFF2E93L), els = els, motion = CollageMotion.CASCADE,
            hint = "Seis instax mini con una palabra escrita a mano debajo de cada una, sobre fondo holográfico, título con reflejo")
    }

    private fun polaBig(): Design {
        val x = 107f; val y = 170f; val w = 580f; val h = 640f; val rot = -2f
        return Design("x-pola-big", "Polaroid gigante", DesignFamily.POLAROID, DOrient.PORTRAIT, 794f, 1123f, ACC,
            accents = listOf(0xFFFF7A1AL, 0xFF2340D9L, 0xFFC6F04AL, 0xFFFF6FB5L, 0xFF111111L), motion = CollageMotion.DROP,
            hint = "Una polaroid enorme con texto de plumón en el borde y dos pequeñas que asoman detrás",
            els = listOf(
                DPhoto(bk(470, 110, 240, 260), 1, rot = 14f, border = 12f, bottomPad = 60f, shadow = 16f),
                DPhoto(bk(60, 700, 240, 260), 2, rot = -12f, border = 12f, bottomPad = 60f, shadow = 16f),
                DPhoto(bk(x, y, w, h), 0, rot = rot, border = 28f, bottomPad = 170f, shadow = 40f),
                DText(capBox(x, y, w, h, 28f, 170f, rot), "cap", "Texto en la polaroid", "¡verano {AA}!", DFont.HAND, 100f, 0xFF1A1A1AL, DAlign.CENTER, rot = rot),
                DRect(bk(320, 140, 160, 50), TAPE, rot = 4f),
            ))
    }

    // Editoriale -------------------------------------------------------------------------------------------------------
    private fun edMinimal() = Design("x-ed-minimal", "Minimal", DesignFamily.EDITORIAL, DOrient.PORTRAIT, 794f, 1123f, 0xFFF4F1EAL,
        accents = listOf(0xFF1A1A1AL, 0xFFB0303AL, 0xFF2340D9L), motion = CollageMotion.ZOOM,
        hint = "Página de revista de arte: mucho blanco, una foto, el título en cursiva enorme que la invade, números de página y una columnita de texto",
        els = listOf(
            DText(bk(40, 40, 200, 30), "num", "Número", "N°{mese_n}", DFont.MONO_BOLD, 18f, ACC, track = 0.2f),
            DText(bk(454, 40, 300, 30), "date", "Fecha", "{Mese} {ANNO}", DFont.MONO_BOLD, 18f, ACC, DAlign.RIGHT, track = 0.2f, upper = true),
            DRect(bk(40, 80, 714, 2), ACC),
            DPhoto(bk(220, 150, 494, 640), 0, filter = DFilter.NONE),
            DText(bk(30, 650, 740, 230), "title", "Título", "Lisboa", DFont.SERIF_IT, 210f, ACC),
            DPhoto(bk(40, 900, 200, 160), 1),
            DText(bk(270, 900, 230, 160), "text", "Texto", "Una semana entre subidas, tranvías amarillos y atardeceres sobre el río. Las fotos son de quien estuvo allí.", DFont.SERIF, 20f, ACC, wrap = true, line = 1.25f),
            DText(bk(530, 900, 224, 160), "text2", "Texto 2", "{foto} fotografías\n{luogo|Lisboa}\n{data}", DFont.MONO, 15f, ACC, DAlign.RIGHT, line = 1.6f, upper = true),
            DRect(bk(40, 1080, 714, 1), ACC),
        ))

    private fun edIndex(): Design {
        val els = arrayListOf<DEl>(
            DText(bk(40, 30, 500, 110), "title", "Título", "Index", DFont.SERIF_IT, 110f, ACC2),
            DText(bk(454, 80, 300, 30), "date", "Fecha", "{luogo|Tokio} · {ANNO}", DFont.MONO_BOLD, 18f, ACC2, DAlign.RIGHT, track = 0.2f, upper = true),
        )
        val caps = listOf("El primer día", "Las calles", "La última noche")
        for (i in 0 until 3) {
            val y = 170f + i * 310f
            els += DRect(bk(40, y - 12, 714, 2), ACC2)
            els += DText(bk(40, y, 230, 150), "", "", "0${i + 1}", DFont.ANTON, 150f, ACC)
            els += DText(bk(40, y + 170, 250, 80), "c$i", "Didascalia ${i + 1}", caps[i], DFont.SERIF_IT, 34f, ACC2, wrap = true)
            els += DPhoto(bk(300, y, 454, 280), i)
        }
        return Design("x-ed-index", "Índice", DesignFamily.EDITORIAL, DOrient.PORTRAIT, 794f, 1123f, 0xFFF4F1EAL,
            accents = listOf(0xFFE5432DL, 0xFF2340D9L, 0xFF111111L), accents2 = listOf(0xFF111111L, 0xFF2340D9L),
            els = els, motion = CollageMotion.CASCADE,
            hint = "Estilo suizo: tres fotos en fila con los números grandes 01 02 03, líneas finas y un pie de foto para cada una")
    }

    private fun edCover() = Design("x-ed-cover", "Portada minimal", DesignFamily.EDITORIAL, DOrient.PORTRAIT, 794f, 1123f, 0xFFF4F1EAL,
        accents = listOf(0xFF1A1A1AL, 0xFFB0303AL, 0xFF2340D9L), motion = CollageMotion.ZOOM,
        hint = "Portada elegante: encabezado espaciado con la persona delante, los detalles en las esquinas y un título en cursiva",
        els = listOf(
            DPhoto(bk(40, 150, 714, 780), 0),
            DText(bk(0, 70, 794, 200, F), "mast", "Encabezado", "Verano", DFont.SERIF, 170f, ACC, DAlign.CENTER, track = 0.12f, upper = true, behind = 0),
            DText(bk(40, 30, 300, 26), "vol", "Volume", "Vol. {AA}", DFont.MONO_BOLD, 16f, ACC, track = 0.2f, upper = true),
            DText(bk(454, 30, 300, 26), "num", "Número", "N°{mese_n}", DFont.MONO_BOLD, 16f, ACC, DAlign.RIGHT, track = 0.2f),
            DText(bk(40, 950, 714, 80), "title", "Título", "Los días más largos", DFont.SERIF_IT, 58f, ACC, DAlign.CENTER),
            DText(bk(40, 1040, 300, 26), "place", "Lugar", "{luogo|Sicilia}", DFont.MONO_BOLD, 16f, ACC, track = 0.2f, upper = true),
            DText(bk(454, 1040, 300, 26), "count", "Foto", "{foto} fotografías", DFont.MONO_BOLD, 16f, ACC, DAlign.RIGHT, track = 0.2f, upper = true),
        ))

    private fun edSpread() = Design("x-ed-spread", "Doble página", DesignFamily.EDITORIAL, DOrient.LANDSCAPE, 1123f, 794f, 0xFFF4F1EAL,
        accents = listOf(0xFF1A1A1AL, 0xFFB0303AL, 0xFF2340D9L), motion = CollageMotion.ZOOM,
        hint = "Dos páginas de revista una junto a otra: a la izquierda título, texto con capitular y una foto pequeña, a la derecha la foto a página completa",
        els = listOf(
            DPhoto(bk(561, 0, 562, 794), 0),
            DRect(bk(560, 0, 2, 794), 0x33000000L),
            DText(bk(40, 40, 480, 30), "kicker", "Agenda", "Viajes · {Mese} {ANNO}", DFont.MONO_BOLD, 16f, ACC, track = 0.25f, upper = true),
            DText(bk(40, 90, 480, 220), "title", "Título", "El mar en invierno", DFont.SERIF_IT, 92f, ACC, line = 0.95f, wrap = true),
            DText(bk(40, 330, 70, 90), "", "", "S", DFont.SERIF, 100f, ACC),
            DText(bk(110, 340, 410, 200), "text", "Texto", "alimos temprano, con el termo y las ganas de ver el mar sin nadie. {luogo|Camogli} nos recibió con el sol bajo y los barcos quietos.", DFont.SERIF, 21f, ACC, wrap = true, line = 1.35f),
            DPhoto(bk(40, 560, 300, 200), 1),
            DText(bk(360, 700, 160, 60), "cap", "Pie de foto", "{data}", DFont.MONO, 14f, ACC, track = 0.15f),
        ))

    // Eventi: colori pieni, foto grandi, scritte enormi e adesivi (niente avorio e rametti da partecipazione).
    private const val CREAM = 0xFFFFF4E2L
    private const val BUTTER = 0xFFFFE14DL
    private const val HEART = "M50 88 C12 62 0 34 18 16 C34 2 48 14 50 30 C52 14 66 2 82 16 C100 34 88 62 50 88"

    // E1 ---------------------------------------------------------------------------------------------
    private fun wedding(): Design {
        val wave = "M0 60 C130 0 260 0 397 45 C534 90 664 90 794 30 L794 120 L0 120 Z"
        return Design("x-wed", "¡Novios!", DesignFamily.EVENTS, DOrient.PORTRAIT, 794f, 1123f, ACC,
            accents = listOf(0xFFE5432DL, 0xFF2340D9L, 0xFF111111L, 0xFFFF6FB5L), accents2 = listOf(CREAM, BUTTER, WHITE),
            motion = CollageMotion.ZOOM, hint = "Boda de revista: la foto a página completa, «¡Novios!» enorme detrás de ustedes y una ola de color sólido",
            els = listOf(
                DPhoto(bk(0, 0, 794, 820, F), 0),
                DText(bk(0, 30, 794, 330, F), "title", "Título", "¡Novios!", DFont.SERIF_IT, 300f, ACC2, DAlign.CENTER, line = 0.9f, behind = 0, shadow = true),
                DPath(bk(0, 730, 794, 120, F), wave, 794f, 120f, fill = ACC),
                DText(bk(540, 690, 220, 80), "sticker", "Calcomanía", "¡Viva!", DFont.HAND, 48f, BLACK, DAlign.CENTER, rot = -8f, pill = BUTTER, pad = 22f),
                DPath(bk(60, 870, 70, 70), SPARKLE, 100f, 100f, fill = ACC2),
                DPath(bk(670, 1000, 50, 50), SPARKLE, 100f, 100f, fill = ACC2),
                DText(bk(40, 850, 714, 120), "names", "Nombres", "Ana & Luis", DFont.SERIF, 104f, ACC2, DAlign.CENTER),
                DText(bk(40, 985, 714, 34), "date", "Fecha", "{data|20.06.2027}", DFont.MONO_BOLD, 26f, ACC2, DAlign.CENTER, track = 0.3f),
                DText(bk(40, 1035, 714, 50), "place", "Lugar", "{luogo|Villa junto al lago}", DFont.SERIF_IT, 40f, ACC2, DAlign.CENTER),
            ))
    }

    // E2 ---------------------------------------------------------------------------------------------
    private fun saveDate() = Design("x-save-date", "Save the date", DesignFamily.EVENTS, DOrient.PORTRAIT, 794f, 1123f, ACC,
        accents = listOf(0xFF2340D9L, 0xFFE5432DL, 0xFFFF6FB5L, 0xFF1E4D3AL), accents2 = listOf(CREAM, BUTTER, WHITE),
        motion = CollageMotion.DROP, hint = "Save the date a color sólido: la foto como una impresión apoyada torcida, la fecha gigante y un corazón a mano",
        els = listOf(
            DPhoto(bk(152, 150, 490, 530), 0, radius = 18f, rot = 3f, border = 16f, borderColor = CREAM, shadow = 24f),
            DText(bk(40, 40, 560, 120), "kicker", "Agenda", "Save the date", DFont.HAND, 92f, BUTTER, rot = -6f),
            DPath(bk(610, 70, 150, 140), HEART, 100f, 100f, stroke = BUTTER, width = 5f, draw = true),
            DText(bk(0, 730, 794, 240, F), "day", "Día", "{gg|20}.{mese_n|06}", DFont.ANTON, 250f, ACC2, DAlign.CENTER, line = 0.9f),
            DText(bk(40, 990, 714, 70), "names", "Nombres", "Ana & Luis", DFont.SERIF_IT, 66f, ACC2, DAlign.CENTER),
            DText(bk(40, 1075, 714, 26), "place", "Lugar", "{ANNO} · {luogo|Villa junto al lago}", DFont.MONO_BOLD, 20f, ACC2, DAlign.CENTER, track = 0.25f, upper = true),
        ))

    // E3 ---------------------------------------------------------------------------------------------
    private fun booth(): Design {
        // La striscia della cabina fototessere, storta: le foto girano attorno al centro della striscia.
        val rot = -4f; val cx = 230f; val cy = 565f
        val a = Math.toRadians(rot.toDouble()); val sn = kotlin.math.sin(a).toFloat(); val cs = kotlin.math.cos(a).toFloat()
        val els = arrayListOf<DEl>(
            DPattern(b(0, 0, 794, 1123, F), DPatternKind.CONFETTI, 0, 0f),
            DRect(bk(70, 70, 320, 990), CREAM, rot = rot, shadow = 22f),
        )
        for (i in 0 until 4) {
            val dy = 200f + i * 235f - cy
            val px = cx - dy * sn; val py = cy + dy * cs
            els += DPhoto(bk(px - 140, py - 105, 280, 210), i, rot = rot)
        }
        val sy = 1025f - cy
        els += DText(bk(cx - sy * sn - 140, cy + sy * cs - 15, 280, 30), "stamp", "Debajo de las fotos", "{giorno_breve} {ANNO}", DFont.MONO_BOLD, 18f, BLACK, DAlign.CENTER, track = 0.2f, rot = rot)
        els += listOf(
            DText(bk(420, 80, 350, 420), "title", "Título", "Che\nfesta!", DFont.ANTON, 190f, ACC2, line = 0.88f, upper = true),
            DPath(bk(640, 470, 120, 120), SPARKLE, 100f, 100f, fill = BUTTER),
            DText(bk(430, 560, 330, 60), "tag", "Etiqueta", "{luogo|Milano}", DFont.MONO_BOLD, 24f, BLACK, rot = 5f, pill = BUTTER, pad = 18f, upper = true, track = 0.1f),
            DText(bk(430, 650, 340, 90), "note", "Frase", "se baila hasta el amanecer", DFont.HAND, 52f, ACC2, rot = -4f, wrap = true),
            DPhoto(bk(460, 790, 280, 280), 4, DShape.CIRCLE, rot = 6f, border = 12f, borderColor = CREAM, shadow = 16f),
        )
        return Design("x-booth", "Cabina de fotos", DesignFamily.EVENTS, DOrient.PORTRAIT, 794f, 1123f, ACC,
            accents = listOf(0xFFE8322BL, 0xFFFF5FA2L, 0xFF2340D9L, 0xFF111111L), accents2 = listOf(CREAM, BUTTER, WHITE),
            motion = CollageMotion.CASCADE, els = els,
            hint = "Fiesta, boda, despedida de soltero: la tira de la cabina de fotos con cuatro tomas, textos gruesos y calcomanías")
    }

    // E4 ---------------------------------------------------------------------------------------------
    private fun flash(): Design {
        val stamp = "'{AA} {mese_n} {gg}"
        return Design("x-flash", "Desechable", DesignFamily.EVENTS, DOrient.PORTRAIT, 794f, 1123f, 0xFF0E0E0E,
            accents = listOf(0xFFFF8A1FL, 0xFFFF3B30L, 0xFFFFD60AL), motion = CollageMotion.CASCADE, grain = true,
            hint = "Las fotos con flash de la cámara desechable, con la fecha naranja impresa en la esquina: fiestas, cenas, despedidas de soltera",
            els = listOf(
                DPhoto(bk(30, 30, 734, 640), 0, radius = 6f),
                DPhoto(bk(30, 690, 357, 403), 1, radius = 6f),
                DPhoto(bk(407, 690, 357, 403), 2, radius = 6f),
                DText(bk(50, 560, 520, 90), "title", "Frase", "qué noche", DFont.HAND, 76f, WHITE, rot = -3f, shadow = true),
                DText(bk(430, 620, 314, 36), "stamp", "Fecha impresa", stamp, DFont.MONO_BOLD, 30f, ACC, DAlign.RIGHT, glow = ACC),
                DText(bk(60, 1044, 307, 30), "", "", stamp, DFont.MONO_BOLD, 24f, ACC, DAlign.RIGHT, glow = ACC),
                DText(bk(437, 1044, 307, 30), "", "", stamp, DFont.MONO_BOLD, 24f, ACC, DAlign.RIGHT, glow = ACC),
            ))
    }

    // E5 ---------------------------------------------------------------------------------------------
    private fun balloons() = Design("x-balloons", "Cumpleaños", DesignFamily.EVENTS, DOrient.PORTRAIT, 794f, 1123f, ACC,
        accents = listOf(0xFFC8F04AL, 0xFFFF6FB5L, 0xFFFF7A1AL, 0xFF7CC8FFL), motion = CollageMotion.CASCADE,
        hint = "Cumpleaños: los años gigantes con las fotos dentro de los números, el nombre escrito grande y confeti",
        els = listOf(
            DPattern(b(0, 0, 794, 1123, F), DPatternKind.CONFETTI, 0, 0f),
            DText(bk(197, 60, 400, 56), "kicker", "Agenda", "Feliz cumpleaños", DFont.MONO_BOLD, 24f, CREAM, DAlign.CENTER, pill = BLACK, pad = 24f, upper = true, track = 0.15f),
            DPhotoWord(bk(30, 140, 734, 610), "age", "Años (foto adentro)", "40", DFont.ARCHIVO, 0, gap = 16f),
            DText(bk(40, 780, 500, 200), "name", "Nombre", "Marta!", DFont.SERIF_IT, 170f, BLACK, rot = -5f),
            DPhoto(bk(574, 850, 190, 190), 2, DShape.CIRCLE, border = 10f, borderColor = WHITE, shadow = 14f, rot = 8f),
            DPath(bk(560, 780, 60, 60), SPARKLE, 100f, 100f, fill = BLACK),
            DPath(bk(60, 120, 50, 50), SPARKLE, 100f, 100f, fill = BLACK),
            DText(bk(40, 1050, 714, 30), "date", "Fecha", "{data} · {luogo|Milán}", DFont.MONO_BOLD, 20f, BLACK, DAlign.CENTER, track = 0.2f, upper = true),
        ))

    // E6 ---------------------------------------------------------------------------------------------
    private fun disco() = Design(
        "x-disco", "Disco", DesignFamily.EVENTS, DOrient.PORTRAIT, 794f, 1123f, 0xFF0D0717,
        accents = listOf(0xFFFF3EA5, 0xFF00E5FF, 0xFFB388FF, 0xFFFFE600), motion = CollageMotion.CASCADE,
        hint = "Fiesta, discoteca, Año Nuevo: la bola de espejos que brilla y los textos de neón",
        els = listOf(
            DRect(b(-200, -200, 1194, 1200), 0, radial = true, grad = listOf(0f to 0x40FFFFFFL, 1f to 0L)),
            DRect(bk(396, 0, 2, 44), 0xFF8A8A99),
            DPattern(bk(247, 40, 300, 300), DPatternKind.DISCO, WHITE, 0f),
            DPath(bk(150, 120, 60, 60), SPARKLE, 100f, 100f, fill = ACC),
            DPath(bk(600, 90, 70, 70), SPARKLE, 100f, 100f, fill = WHITE),
            DPath(bk(620, 300, 40, 40), SPARKLE, 100f, 100f, fill = ACC),
            DText(bk(30, 370, 734, 200), "title", "Título", "Saturday\nnight", DFont.HAND, 110f, WHITE, DAlign.CENTER, line = 0.85f, glow = ACC),
            DPhoto(bk(70, 600, 310, 390), 0, radius = 20f, border = 4f, borderColor = ACC),
            DPhoto(bk(414, 600, 310, 390), 1, radius = 20f, border = 4f, borderColor = ACC),
            DText(b(40, 1024, 714, 40, E), "info", "Información", "{data} · {luogo|Club} · desde las 22", DFont.MONO_BOLD, 18f, ACC, DAlign.CENTER, track = 0.2f, upper = true, glow = ACC),
        ),
    )

    // E7 ---------------------------------------------------------------------------------------------
    private fun vip(): Design {
        val ink = 0xFF101014L
        return Design("x-vip", "Pase VIP", DesignFamily.EVENTS, DOrient.PORTRAIT, 600f, 900f, ink,
            accents = listOf(0xFFFF3EA5, 0xFF2F5BFF, 0xFFFF8C00), motion = CollageMotion.SLIDE,
            hint = "Concierto, festival, evento: el pase de backstage con la tira holográfica y tu foto",
            els = listOf(
                DRect(bk(270, 0, 60, 170), ACC),
                DRect(bk(280, 150, 40, 44), 0xFF9CA3AF, radius = 6f),
                DRect(bk(90, 180, 420, 640), WHITE, radius = 24f, shadow = 20f),
                DRect(bk(90, 180, 420, 120), 0, radius = 24f, grad = listOf(0f to 0xFFB388FFL, 0.35f to 0xFF7CFFCBL, 0.7f to 0xFFFFD6E8L, 1f to 0xFF82B1FFL)),
                DText(bk(90, 214, 420, 60), "access", "Acceso", "All access", DFont.ANTON, 52f, ink, DAlign.CENTER, upper = true),
                DPhoto(bk(150, 320, 300, 300), 0, DShape.CIRCLE),
                DText(bk(100, 638, 400, 58), "name", "Nombre", "Invitado", DFont.GROTESK, 48f, ink, DAlign.CENTER),
                DText(bk(100, 700, 400, 22), "event", "Evento", "{luogo|Festival} · {ANNO}", DFont.MONO_BOLD, 16f, ACC, DAlign.CENTER, track = 0.15f, upper = true),
                DBarcode(bk(150, 742, 300, 50), ink),
            ))
    }

    // E8 ---------------------------------------------------------------------------------------------
    private fun badge(): Design {
        val ink = 0xFF111827L
        return Design("x-badge", "Gafete de congreso", DesignFamily.EVENTS, DOrient.PORTRAIT, 600f, 900f, 0xFFE9ECF1,
            accents = listOf(0xFF2F5BFF, 0xFF0F766E, 0xFFE63946), accents2 = listOf(0xFF111111, 0xFF2F5BFF, 0xFF6B7280),
            motion = CollageMotion.SLIDE, hint = "Congreso, feria, curso: el gafete con nombre, cargo, empresa y el recuadro tipo QR",
            els = listOf(
                DRect(bk(280, 0, 40, 120), ACC2),
                DRect(bk(80, 110, 440, 700), WHITE, radius = 20f, shadow = 18f),
                DRect(bk(80, 110, 440, 150), ACC, radius = 20f),
                DRect(bk(80, 200, 440, 60), ACC),
                DText(bk(108, 136, 384, 80), "event", "Evento", "Foro {luogo|Milán} {ANNO}", DFont.GROTESK, 38f, WHITE, wrap = true),
                DText(bk(108, 224, 384, 20), "date", "Fecha", "{giorno}", DFont.MONO, 13f, WHITE, track = 0.1f, upper = true),
                DPhoto(bk(110, 290, 140, 140), 0, DShape.CIRCLE),
                DText(bk(270, 300, 230, 120), "name", "Nombre", "Giulia\nRossi", DFont.GROTESK, 40f, ink, line = 1f),
                DText(bk(110, 458, 380, 30), "role", "Cargo", "Ponente", DFont.MONO_BOLD, 20f, ACC, track = 0.15f, upper = true),
                DText(bk(110, 494, 380, 30), "company", "Empresa", "Despacho Rossi & Asociados", DFont.SANS, 20f, ink),
                DPattern(bk(110, 560, 190, 190), DPatternKind.QR, ink, 0f),
                DText(bk(320, 604, 180, 30), "tag", "Etiqueta", "Speaker", DFont.MONO_BOLD, 16f, WHITE, pill = ACC, pad = 14f),
            ))
    }

    // E9 ---------------------------------------------------------------------------------------------
    private fun speaker() = Design(
        "x-speaker", "Ponente", DesignFamily.EVENTS, DOrient.PORTRAIT, 794f, 1123f, ACC2,
        accents = listOf(0xFFFFD60A, 0xFF7CFFCB, 0xFFFF5B14), accents2 = listOf(0xFF0F172A, 0xFF1A1033, 0xFF111111),
        motion = CollageMotion.SLIDE, hint = "El cartel de una ponencia: la persona recortada, el título, día, hora y sala",
        els = listOf(
            DRect(bk(300, 120, 560, 560), ACC, circle = true),
            DText(bk(48, 60, 600, 24), "kicker", "Agenda", "Keynote · {luogo|Milán}", DFont.MONO_BOLD, 16f, ACC, track = 0.2f, upper = true),
            DPhoto(bk(300, 120, 520, 680), 0, radius = 24f, border = 10f, shadow = 12f, cutout = true),
            DText(bk(48, 690, 698, 210), "title", "Título", "El futuro\nya está aquí", DFont.GROTESK, 96f, WHITE, line = 0.95f, shadow = true),
            DText(bk(48, 910, 698, 40), "name", "Nombre", "Julia Rossi", DFont.GROTESK_MED, 34f, WHITE),
            DText(bk(48, 952, 698, 24), "role", "Cargo", "CEO, Despacho Rossi", DFont.SANS, 18f, 0xFFB0B6C3),
            DText(bk(48, 1020, 698, 30), "when", "Cuándo", "{giorno} · 10:30 h · Sala A", DFont.MONO_BOLD, 18f, ACC, track = 0.1f, upper = true),
        ),
    )

    // E10 --------------------------------------------------------------------------------------------
    private fun laurea() = Design("x-laurea", "Graduación", DesignFamily.EVENTS, DOrient.PORTRAIT, 794f, 1123f, ACC,
        accents = listOf(0xFFC8102EL, 0xFF2340D9L, 0xFF111111L, 0xFF1E4D3AL), accents2 = listOf(CREAM, BUTTER, WHITE),
        motion = CollageMotion.DROP,
        hint = "Graduación: la calificación enorme que cuenta hasta 110, tú recortado como una calcomanía delante, el nombre en una etiqueta amarilla",
        els = listOf(
            DPattern(b(0, 0, 794, 1123, F), DPatternKind.CONFETTI, 0, 0f),
            DText(bk(0, 40, 794, 640, F), "grade", "Calificación", "110\ny mención", DFont.ANTON, 330f, ACC2, DAlign.CENTER, line = 0.86f, upper = true, count = true),
            DPhoto(bk(147, 330, 500, 640), 0, cutout = true, border = 14f, shadow = 18f),
            DPath(bk(70, 380, 80, 80), SPARKLE, 100f, 100f, fill = BUTTER),
            DPath(bk(660, 300, 60, 60), SPARKLE, 100f, 100f, fill = BUTTER),
            DText(bk(60, 930, 674, 100), "name", "Nombre", "Lic. Marta Bianchi", DFont.GROTESK, 50f, BLACK, DAlign.CENTER, rot = -3f, pill = BUTTER, pad = 26f),
            DText(bk(40, 1060, 714, 30), "field", "Carrera y lugar", "Ingeniería · {luogo|Politécnico} · {data}", DFont.MONO_BOLD, 20f, ACC2, DAlign.CENTER, track = 0.15f, upper = true),
        ))

    // E11 --------------------------------------------------------------------------------------------
    private fun baby(): Design {
        val els = arrayListOf<DEl>(
            DText(bk(30, 24, 420, 200), "title", "Título", "Ciao,\nSofia!", DFont.SERIF_IT, 104f, ACC2, line = 0.86f),
            DPath(bk(370, 40, 80, 80), SPARKLE, 100f, 100f, fill = ACC2),
            DPhoto(bk(170, 210, 280, 360), 0, DShape.PILL, rot = 4f, border = 10f, borderColor = WHITE, shadow = 14f),
            DPath(bk(36, 520, 40, 40), SPARKLE, 100f, 100f, fill = ACC2),
            DText(bk(30, 590, 420, 50), "date", "Fecha", "{giorno} · {luogo|Milán}", DFont.HAND, 38f, ACC2),
        )
        listOf(Triple("Peso", "3,250 kg", -6f), Triple("Longitud", "50 cm", 5f), Triple("Hora", "a las {ora|07:42}", -3f)).forEachIndexed { i, (l, v, r) ->
            els += DText(bk(18, 250 + i * 100, 200, 56), "s$i", l, v, DFont.GROTESK, 30f, WHITE, rot = r, pill = ACC2, pad = 18f, count = true)
        }
        return Design("x-baby", "Bienvenido", DesignFamily.EVENTS, DOrient.PORTRAIT, 480f, 672f, ACC,
            accents = listOf(0xFFFFE27AL, 0xFFBFE3FFL, 0xFFFFC7DDL, 0xFFC9F0D8L), accents2 = listOf(0xFF2340D9L, 0xFFE5432DL, 0xFF111111L),
            els = els, motion = CollageMotion.DROP,
            hint = "Nacimiento o bautizo: «¡Hola, Sofía!» grande, la foto en forma de píldora y peso, longitud y hora en etiquetas que cuentan en el video")
    }

    // A1 -----------------------------------------------------------------------------------------------
    private fun bib(): Design {
        val pin = 0xFFB8BCC4L
        val els = arrayListOf<DEl>(
            DPhoto(b(0, 0, 794, 1123, F), 0),
            DRect(b(0, 0, 794, 1123, F), 0, grad = listOf(0f to 0x66000000L, 0.3f to 0L, 0.6f to 0L, 1f to 0xB3000000L)),
            DSplat(bk(520, 60, 260, 260), MUD, seed = 3),
            DSplat(bk(30, 830, 210, 210), MUD, seed = 5, rot = 40f),
            DSplat(bk(620, 930, 140, 140), MUD, seed = 9),
            DText(bk(40, 40, 500, 22), "kicker", "Agenda", "{giorno}", DFont.MONO_BOLD, 16f, WHITE, track = 0.15f, upper = true, shadow = true),
            DText(bk(40, 70, 714, 200), "title", "Título", "Finisher", DFont.ANTON, 150f, WHITE, line = 0.85f, upper = true, skew = -0.12f, shadow = true),
            DRect(bk(167, 560, 460, 330), WHITE, radius = 10f, rot = -3f, shadow = 16f),
            DRect(bk(167, 560, 460, 330), ACC, rot = -3f, poly = pts(0f, 0f, 1f, 0f, 1f, 0.18f, 0f, 0.18f)),
            DText(bk(167, 560, 460, 330), "race", "Carrera", "{luogo|Granfondo} {ANNO}", DFont.ANTON, 34f, WHITE, DAlign.CENTER, line = 1.75f, upper = true, rot = -3f),
            DText(bk(167, 560, 460, 316), "num", "Número", "127", DFont.ANTON, 200f, BLACK, DAlign.CENTER, line = 0.95f, rot = -3f, bottom = true),
        )
        for ((x, y) in listOf(182 to 618, 596 to 597, 197 to 862, 611 to 840)) {
            els += DRect(bk(x, y, 16, 16), pin, circle = true)
            els += DRect(bk(x, y, 16, 16), 0xFF6B7280, circle = true, stroke = 2f)
        }
        val stats = listOf("Tiempo" to "5:12:08", "Posición" to "214°", "Km" to "142")
        stats.forEachIndexed { i, (l, v) ->
            val x = 40 + i * 245
            els += DText(b(x, 990, 230, 16, E), "l$i", "Etichetta ${i + 1}", l, DFont.MONO, 12f, WHITE, track = 0.18f, upper = true)
            els += DText(b(x, 1010, 230, 60, E), "s$i", l, v, DFont.ANTON, 54f, if (i == 0) ACC else WHITE, count = true)
        }
        return Design("x-bib", "Número de competidor", DesignFamily.ACTION, DOrient.PORTRAIT, 794f, 1123f, BLACK,
            accents = listOf(0xFFE63946, 0xFF2F5BFF, 0xFFFF8C00, 0xFF16A34A), els = els, motion = CollageMotion.STILL,
            hint = "Granfondo, maratón, trail: el número de competidor prendido, lodo y tus tiempos")
    }

    // A2 -----------------------------------------------------------------------------------------------
    private fun topo(): Design {
        val ink = 0xFF2B2418L
        val els = arrayListOf<DEl>(
            DPattern(b(0, 0, 794, 1123, F), DPatternKind.TOPO, 0xFF8B7B5E, 22f),
            DPath(b(0, 0, 794, 1123, F), "M80 980 C160 900 120 820 220 760 C320 700 260 600 380 560 C500 520 460 420 560 380 C660 340 640 240 720 180", 794f, 1123f,
                stroke = ACC, width = 7f, dash = listOf(18f, 10f), draw = true),
            DRect(bk(70, 970, 22, 22), ACC, circle = true),
            DRect(bk(709, 169, 22, 22), ACC, circle = true),
            DText(bk(40, 40, 600, 76), "title", "Título", "{luogo|Monte Grappa}", DFont.GROTESK, 64f, ink),
            DText(bk(40, 120, 600, 18), "coords", "Subtítulo", "45.8701° N · 11.8040° E", DFont.MONO, 13f, ink, track = 0.1f),
            DPhoto(bk(150, 200, 500, 640), 0, radius = 24f, border = 14f, shadow = 12f, cutout = true),
            DRect(b(40, 900, 714, 180, E), 0xE6FFFFFF, radius = 18f, shadow = 10f),
        )
        listOf("Desnivel" to "1.640 m", "Distancia" to "48 km", "Altitud" to "1.775 m").forEachIndexed { i, (l, v) ->
            val x = 64 + i * 236
            els += DText(b(x, 920, 220, 14, E), "l$i", "Etichetta ${i + 1}", l, DFont.MONO, 11f, ink, track = 0.18f, upper = true)
            els += DText(b(x, 938, 220, 46, E), "s$i", l, v, DFont.ANTON, 40f, if (i == 0) ACC else ink, count = true)
        }
        els += DPath(b(64, 996, 666, 64, E), "M0 60 L60 52 L110 40 L170 44 L230 26 L300 30 L350 12 L400 6 L460 20 L520 18 L580 34 L640 46 L666 50 L666 64 L0 64 Z", 666f, 64f, fill = ACC)
        els += DPath(b(64, 996, 666, 64, E), "M0 60 L60 52 L110 40 L170 44 L230 26 L300 30 L350 12 L400 6 L460 20 L520 18 L580 34 L640 46 L666 50", 666f, 64f, stroke = ink, width = 2f, draw = true)
        return Design("x-topo", "Topográfica", DesignFamily.ACTION, DOrient.PORTRAIT, 794f, 1123f, 0xFFEDE8DC,
            accents = listOf(0xFFFF5B14, 0xFFE63946, 0xFF1D4ED8), els = els, motion = CollageMotion.CASCADE,
            hint = "Tú recortado como una calcomanía sobre las curvas de nivel, con el recorrido y la altimetría que se dibujan")
    }

    // A3 -----------------------------------------------------------------------------------------------
    private fun speed() = Design(
        "x-speed", "Velocidad", DesignFamily.ACTION, DOrient.PORTRAIT, 794f, 1123f, 0xFF0E0E10,
        accents = listOf(0xFFFFD60A, 0xFF00E5FF, 0xFFFF2D55), motion = CollageMotion.SLIDE,
        hint = "Descenso, sprint, curva: líneas de velocidad que corren y la velocidad máxima gigante e inclinada",
        els = listOf(
            DPhoto(b(0, 0, 794, 1123, F), 0),
            DRect(b(0, 0, 794, 1123, F), 0, grad = listOf(0f to 0x80000000L, 0.35f to 0x1A000000L, 1f to 0xCC000000L)),
            DPattern(b(0, 0, 794, 1123, F), DPatternKind.SPEED, WHITE, 0f),
            DText(bk(40, 40, 714, 120), "title", "Título", "Descenso", DFont.ANTON, 110f, WHITE, line = 0.9f, upper = true, skew = -0.2f, shadow = true),
            DText(bk(44, 520, 500, 30), "label", "Etiqueta", "Velocidad máxima", DFont.MONO_BOLD, 18f, ACC, track = 0.2f, upper = true),
            DRect(bk(-50, 650, 900, 160), ACC, rot = -8f),
            DText(bk(30, 556, 480, 260), "speed", "Velocidad", "62", DFont.ANTON, 280f, WHITE, line = 0.85f, skew = -0.25f, count = true, shadow = true),
            DText(bk(470, 690, 300, 84), "unit", "Unidades", "km/h", DFont.ANTON, 80f, BLACK, upper = true, rot = -8f, skew = -0.25f),
            DPhoto(b(480, 830, 270, 170, E), 1, radius = 10f, rot = -8f, border = 4f),
            DText(b(40, 1040, 714, 30, E), "place", "Lugar", "{luogo|Paso del Stelvio} · {data}", DFont.MONO_BOLD, 18f, WHITE, track = 0.12f, upper = true),
        ),
    )

    // A4 -----------------------------------------------------------------------------------------------
    private fun mud() = Design(
        "x-mud", "Lodo", DesignFamily.ACTION, DOrient.PORTRAIT, 794f, 1123f, 0xFF2A1F17,
        accents = listOf(MUD, 0xF03B2A1C, 0xF07A6A55), accents2 = listOf(0xFFFFD60A, 0xFFFF5B14, WHITE), motion = CollageMotion.STILL,
        hint = "MTB, enduro, gravel, cross: salpicaduras de lodo sobre la foto y un texto que grita",
        els = listOf(
            DPhoto(b(0, 0, 794, 1123, F), 0),
            DRect(b(0, 0, 794, 1123, F), 0, grad = listOf(0f to 0L, 0.55f to 0L, 1f to 0xCC000000L)),
            DSplat(bk(20, 20, 320, 320), ACC, seed = 21),
            DSplat(bk(500, 110, 280, 280), ACC, seed = 23, rot = 70f),
            DSplat(bk(330, 430, 170, 170), ACC, seed = 25),
            DSplat(bk(580, 650, 240, 240), ACC, seed = 27, rot = 20f),
            DSplat(bk(-40, 600, 220, 220), ACC, seed = 29),
            DText(b(36, 740, 400, 30, E), "tag", "Etiqueta", "Enduro · run 3", DFont.MONO_BOLD, 16f, BLACK, pill = ACC2, pad = 12f, square = true),
            DText(b(30, 790, 734, 260, E), "title", "Título", "Full\ngas", DFont.ARCHIVO, 170f, WHITE, line = 0.85f, upper = true, skew = -0.08f, shadow = true),
            DText(b(36, 1064, 714, 24, E), "sub", "Subtítulo", "{luogo|Finale Ligure} · {Mese} {ANNO}", DFont.MONO_BOLD, 16f, WHITE, track = 0.15f, upper = true),
        ),
    )

    // A5 -----------------------------------------------------------------------------------------------
    private fun hud(): Design {
        val els = arrayListOf<DEl>(
            DPhoto(b(0, 0, 600, 900, F), 0),
            DRect(b(-150, -50, 900, 1000, F), 0, radial = true, grad = listOf(0f to 0L, 0.65f to 0L, 1f to 0xA6000000L)),
        )
        for ((x, y, d) in listOf(Triple(24, 24, "M0 40 L0 0 L40 0"), Triple(536, 24, "M0 0 L40 0 L40 40"), Triple(24, 836, "M0 0 L0 40 L40 40"), Triple(536, 836, "M40 0 L40 40 L0 40")))
            els += DPath(bk(x, y, 40, 40), d, 40f, 40f, stroke = ACC, width = 4f)
        els += DRect(bk(80, 42, 14, 14), 0xFFFF2D2D, circle = true)
        els += DText(bk(102, 37, 100, 22), "", "", "REC", DFont.MONO_BOLD, 16f, WHITE)
        els += DText(bk(360, 37, 160, 22), "tc", "Tiempo", "01:24:37", DFont.MONO_BOLD, 16f, WHITE, DAlign.RIGHT)
        els += DPath(bk(150, 440, 300, 20), "M0 10 L110 10 M190 10 L300 10 M140 10 L150 18 L160 10", 300f, 20f, stroke = WHITE, width = 2f)
        els += DPath(bk(36, 620, 190, 100), "M10 92 C10 30 60 8 95 8 C130 8 180 30 180 92", 190f, 100f, stroke = 0x66FFFFFF, width = 8f)
        els += DPath(bk(36, 620, 190, 100), "M10 92 C10 30 60 8 95 8 C112 8 128 12 140 18", 190f, 100f, stroke = ACC, width = 8f, draw = true)
        els += DText(bk(36, 662, 190, 70), "speed", "Velocidad", "48", DFont.GROTESK, 64f, WHITE, DAlign.CENTER, count = true, shadow = true)
        els += DText(bk(36, 730, 190, 18), "", "", "km/h", DFont.MONO_BOLD, 13f, WHITE, DAlign.CENTER, track = 0.2f, upper = true)
        els += DText(bk(330, 650, 234, 16), "l1", "Etiqueta 1", "Altitud", DFont.MONO, 12f, WHITE, DAlign.RIGHT, track = 0.18f, upper = true)
        els += DText(bk(330, 668, 234, 44), "alt", "Altitud", "1.775 m", DFont.ANTON, 40f, WHITE, DAlign.RIGHT, count = true, shadow = true)
        els += DPath(bk(482, 727, 26, 24), "M13 22 C4 15 1 10 2 6 C3 2 9 1 13 6 C17 1 23 2 24 6 C25 10 22 15 13 22 Z", 26f, 24f, fill = 0xFFFF2D55)
        els += DText(bk(450, 720, 114, 32), "hr", "Beat", "162", DFont.ANTON, 30f, WHITE, DAlign.RIGHT, count = true, shadow = true)
        els += DText(bk(40, 864, 520, 18), "place", "Lugar", "{luogo|Bike park} · gps fix", DFont.MONO, 12f, WHITE, DAlign.CENTER, track = 0.15f, upper = true)
        return Design("x-hud", "Casco", DesignFamily.ACTION, DOrient.PORTRAIT, 600f, 900f, BLACK,
            accents = listOf(WHITE, 0xFFFFD60A, 0xFF00E5FF), els = els, motion = CollageMotion.STILL,
            hint = "La gráfica de una action cam en el casco: miras, REC, velocidad que sube, altitud y pulso")
    }

    // A6 -----------------------------------------------------------------------------------------------
    private fun record(): Design {
        val dim = 0xFFB0B6C3L
        val els = arrayListOf<DEl>(
            DPhoto(bk(0, 0, 794, 520), 0),
            DRect(bk(0, 300, 794, 222), 0, grad = listOf(0f to 0L, 1f to ACC2)),
            DRect(bk(40, 470, 150, 150), ACC, circle = true, shadow = 12f),
            DText(bk(40, 514, 150, 62), "pr", "Sello", "PR", DFont.ANTON, 64f, BLACK, DAlign.CENTER, rot = -8f),
            DText(bk(220, 480, 534, 64), "seg", "Segmento", "Subida del {luogo|Ghisallo}", DFont.GROTESK, 44f, WHITE, wrap = true),
            DText(bk(220, 550, 534, 20), "date", "Fecha", "{giorno}", DFont.MONO, 14f, dim, track = 0.1f, upper = true),
            DText(bk(40, 640, 714, 170), "time", "Tiempo", "12:47", DFont.ANTON, 180f, WHITE, line = 0.9f, count = true),
            DText(bk(40, 820, 350, 30), "rank", "Posición", "3° su 4.812", DFont.MONO_BOLD, 22f, ACC),
            DText(bk(404, 820, 350, 30), "gain", "Mejora", "−0:38 de tu mejor marca", DFont.MONO_BOLD, 22f, WHITE, DAlign.RIGHT),
        )
        val hs = listOf(120, 150, 95, 170, 140, 180, 130, 160)
        hs.forEachIndexed { i, hh ->
            val x = 40 + i * 89
            els += DRect(bk(x, 1060 - hh, 70, hh), if (hh == hs.max()) ACC else 0xFF3A3F4B, radius = 6f)
            els += DText(bk(x, 1068, 70, 16), "", "", "${i + 1}", DFont.MONO, 12f, dim, DAlign.CENTER)
        }
        return Design("x-pr", "Record", DesignFamily.ACTION, DOrient.PORTRAIT, 794f, 1123f, ACC2,
            accents = listOf(0xFFFF5B14, 0xFFFFD60A, 0xFF00E5FF), accents2 = listOf(0xFF111318, 0xFF1A1033, 0xFF0B2E2A), els = els,
            motion = CollageMotion.SLIDE, hint = "Tu récord en una subida o un segmento: tiempo que cuenta, posición y el ritmo km por km")
    }

    // A7 -----------------------------------------------------------------------------------------------
    private fun sequence(): Design {
        val w = 158.8f
        val els = arrayListOf<DEl>(
            DText(bk(40, 40, 714, 100), "title", "Título", "El salto", DFont.ANTON, 100f, WHITE, line = 0.9f, upper = true, skew = -0.15f),
            DText(bk(44, 132, 714, 20), "sub", "Subtítulo", "{luogo|Bike park} · {data}", DFont.MONO, 14f, WHITE, track = 0.12f, upper = true),
        )
        for (k in 0 until 5) {
            val bx = k * w - 30
            els += DPhoto(bk(bx, 170, w + 60, 810), k, poly = pts(0.288f, 0f, 0.986f, 0f, 0.712f, 1f, 0.014f, 1f))
            els += DText(bk(k * w + 14, 990, 120, 64), "", "", "${k + 1}", DFont.ANTON, 56f, ACC, skew = -0.15f)
        }
        els += DText(b(40, 1062, 714, 30, E), "stat", "Dato", "Vuelo: 6,4 m", DFont.MONO_BOLD, 20f, WHITE, track = 0.1f, upper = true)
        return Design("x-sequence", "Secuencia", DesignFamily.ACTION, DOrient.PORTRAIT, 794f, 1123f, BLACK,
            accents = listOf(0xFFFFD60A, 0xFFFF2D55, 0xFF00E5FF), els = els, motion = CollageMotion.WIPE,
            hint = "Cinco fotos de un salto, una curva o un viraje en tiras inclinadas y numeradas, como una foto estroboscópica")
    }

    // A8 -----------------------------------------------------------------------------------------------
    private fun skipass(): Design {
        val ink = 0xFF0F172AL; val grey = 0xFF64748BL
        val els = arrayListOf<DEl>(
            DPhoto(b(-40, -40, 680, 980, F), 0, filter = DFilter.BLUR),
            DRect(b(0, 0, 600, 900, F), 0x33FFFFFF),
            DRect(bk(60, 90, 480, 720), WHITE, radius = 28f, shadow = 20f),
            DRect(bk(276, 100, 48, 14), 0xFFDDE6EE, radius = 7f),
            DPhoto(bk(84, 124, 432, 290), 0, radius = 18f),
            DText(bk(84, 430, 432, 24), "kicker", "Agenda", "Pase de esquí diario", DFont.MONO_BOLD, 16f, ACC, track = 0.15f, upper = true),
            DText(bk(84, 458, 432, 70), "resort", "Zona de esquí", "{luogo|Cervinia}", DFont.GROTESK, 60f, ink),
            DText(bk(84, 530, 432, 20), "date", "Fecha", "{giorno}", DFont.MONO, 14f, grey),
        )
        listOf("Altitud máx." to "3.480 m", "Pista" to "Negra", "Descensos" to "14", "Desnivel" to "12.400 m").forEachIndexed { i, (l, v) ->
            val x = 84 + (i % 2) * 220; val y = 570 + (i / 2) * 64
            els += DText(bk(x, y, 210, 14), "l$i", "Etichetta ${i + 1}", l, DFont.MONO, 11f, grey, track = 0.18f, upper = true)
            els += DText(bk(x, y + 16, 210, 40), "s$i", l, v, DFont.GROTESK, 32f, ink, count = true)
        }
        els += DPath(bk(84, 704, 432, 2), "M0 1 L432 1", 432f, 2f, stroke = 0xFFB0B8C4, width = 2f, dash = listOf(6f, 6f))
        els += DBarcode(bk(84, 724, 432, 60), ink)
        return Design("x-skipass", "Skipass", DesignFamily.ACTION, DOrient.PORTRAIT, 600f, 900f, 0xFFDDE6EE,
            accents = listOf(0xFF1D4ED8, 0xFFE63946, 0xFF0F766E), els = els, motion = CollageMotion.SLIDE,
            hint = "Esquí y snowboard: el boleto del remonte con la altitud, la pista, los descensos y tu foto")
    }

    // T1 -----------------------------------------------------------------------------------------------
    private fun notes(): Design {
        val grey = 0xFF8E8E93L
        val items = listOf("clavado al amanecer", "helado doble", "atardecer desde el muelle", "volver el año que viene")
        val els = arrayListOf<DEl>(
            DText(bk(28, 30, 200, 26), "", "", "‹ Notas", DFont.SANS, 20f, ACC),
            DText(bk(372, 30, 200, 26), "", "", "Fin", DFont.SANS, 20f, ACC, DAlign.RIGHT),
            DText(bk(28, 80, 544, 18), "date", "Fecha", "{giorno} a las {ora|18:42}", DFont.SANS, 14f, grey, DAlign.CENTER),
            DText(bk(28, 112, 544, 54), "title", "Título", "{luogo|Fin de semana en el lago}", DFont.GROTESK, 44f, BLACK),
        )
        items.forEachIndexed { i, t ->
            val y = 186 + i * 40
            val done = i < 3
            els += if (done) DRect(bk(30, y + 4, 22, 22), ACC, circle = true) else DRect(bk(30, y + 4, 22, 22), grey, circle = true, stroke = 2f)
            if (done) els += DPath(bk(30, y + 4, 22, 22), "M6 11 L10 15 L16 7", 22f, 22f, stroke = WHITE, width = 2.5f, draw = true)
            els += DText(bk(62, y, 500, 30), "c$i", "Cosa ${i + 1}", t, DFont.SANS, 22f, if (done) grey else BLACK)
        }
        els += DPhoto(bk(28, 360, 544, 300), 0, radius = 14f)
        els += DPhoto(bk(28, 672, 266, 200), 1, radius = 14f)
        els += DPhoto(bk(306, 672, 266, 200), 2, radius = 14f)
        return Design("x-notes", "Notas", DesignFamily.TREND, DOrient.PORTRAIT, 600f, 900f, 0xFFFFFDF5,
            accents = listOf(0xFFE8A317, 0xFF2F5BFF, 0xFFFF4F79), els = els, motion = CollageMotion.SLIDE,
            hint = "El recuerdo como una nota del teléfono: lista marcada, fecha y fotos pegadas (el «Notes App Chic» de 2026)")
    }

    // T2 -----------------------------------------------------------------------------------------------
    private fun chrome(): Design {
        val metal = listOf(0xFFFFFFFFL, 0xFF9AA4B5L, 0xFFF4F6FAL, 0xFF5E6878L, 0xFFE9EDF3L)
        val frame = 0xFFD9DEE7L
        return Design(
            "x-chrome", "Cromo Y2K", DesignFamily.TREND, DOrient.PORTRAIT, 794f, 1123f, ACC2,
            accents = listOf(0xFFFF4FD8, 0xFF7CFFCB, 0xFF7AA2FF), accents2 = listOf(0xFF0B0B1A, 0xFF1A0B2E, 0xFF111111),
            motion = CollageMotion.CASCADE, hint = "Título en metal brillante, fotos en burbujas cromadas, estrellitas que brillan: el estilo Y2K",
            els = listOf(
                DRect(b(-200, 100, 1194, 900, F), 0, radial = true, grad = listOf(0f to 0x66FFFFFFL, 1f to 0L)),
                DRect(b(-200, 100, 1194, 900, F), ACC, radial = true, grad = listOf(0f to 0x55FFFFFFL, 0.6f to 0x22FFFFFFL, 1f to 0L)),
                DText(bk(30, 50, 734, 220), "title", "Título", "{stagione|estate}", DFont.ARCHIVO, 170f, WHITE, DAlign.CENTER, line = 0.9f, shadow = true, gradient = metal),
                DPhoto(bk(80, 300, 300, 480), 0, DShape.PILL, border = 8f, borderColor = frame, shadow = 20f),
                DPhoto(bk(420, 330, 300, 300), 1, DShape.CIRCLE, border = 8f, borderColor = frame, shadow = 20f),
                DPhoto(bk(400, 670, 320, 180), 2, DShape.PILL, border = 8f, borderColor = frame, shadow = 20f),
                DPath(bk(360, 262, 70, 70), SPARKLE, 100f, 100f, fill = WHITE),
                DPath(bk(704, 280, 50, 50), SPARKLE, 100f, 100f, fill = ACC),
                DPath(bk(60, 820, 60, 60), SPARKLE, 100f, 100f, fill = WHITE),
                DPath(bk(640, 880, 80, 80), SPARKLE, 100f, 100f, fill = WHITE),
                DPath(bk(330, 900, 40, 40), SPARKLE, 100f, 100f, fill = ACC),
                DText(bk(40, 990, 714, 60), "sub", "Subtítulo", "{luogo|Riccione} · {ANNO}", DFont.MONO_BOLD, 28f, ACC, DAlign.CENTER, track = 0.2f, upper = true),
            ),
        )
    }

    // T3 -----------------------------------------------------------------------------------------------
    private fun leak(): Design {
        val ink = 0xFF333333L
        return Design(
            "x-leak", "Luz cálida", DesignFamily.TREND, DOrient.PORTRAIT, 794f, 1123f, 0xFFEFE6D8,
            accents = listOf(0xFF3B2A1A, 0xFF8E3B4E, 0xFF1F3A68), motion = CollageMotion.DROP, grain = true,
            hint = "Polaroids desvaídas con las manchas de luz naranja de la película: el encanto del rollo viejo",
            els = listOf(
                DPhoto(bk(70, 90, 420, 420), 0, rot = -4f, border = 18f, bottomPad = 70f, shadow = 14f),
                DText(bk(90, 526, 420, 44), "c0", "Pie de foto 1", "{luogo|Positano}", DFont.HAND, 40f, ink, DAlign.CENTER, rot = -4f),
                DPhoto(bk(420, 420, 320, 320), 1, rot = 6f, border = 16f, bottomPad = 56f, shadow = 14f),
                DText(bk(390, 748, 320, 40), "c1", "Pie de foto 2", "{data}", DFont.HAND, 34f, ink, DAlign.CENTER, rot = 6f),
                DPhoto(bk(110, 640, 300, 300), 2, rot = -2f, border = 14f, bottomPad = 50f, shadow = 14f),
                DRect(b(300, -260, 800, 800), 0, radial = true, grad = listOf(0f to 0xD9FF7A00L, 0.35f to 0x99FF3D00L, 0.7f to 0x33FF2E00L, 1f to 0L)),
                DRect(b(-320, 640, 760, 760, E), 0, radial = true, grad = listOf(0f to 0xCCFFB000L, 0.45f to 0x66FF8A00L, 1f to 0L)),
                DText(bk(440, 860, 320, 140), "title", "Título", "{stagione|estate}\n{ANNO}", DFont.SERIF_IT, 64f, ACC, DAlign.RIGHT, line = 1f),
            ),
        )
    }

    // T4 -----------------------------------------------------------------------------------------------
    private fun blue() = Design(
        "x-blue", "Azul", DesignFamily.TREND, DOrient.PORTRAIT, 794f, 1123f, ACC,
        accents = listOf(0xFF1F4FA8, 0xFF2E5E8C, 0xFF3A3F8F, 0xFF1D6E73), motion = CollageMotion.SLIDE,
        hint = "Fondo azul con la textura de mezclilla y las nubes, fotos con el hilo blanco y un texto limpio",
        els = listOf(
            DRect(b(-200, -100, 1194, 1300, F), 0, radial = true, grad = listOf(0f to 0x33FFFFFFL, 1f to 0L)),
            DPattern(b(0, 0, 794, 1123, F), DPatternKind.PAPER, 0x22FFFFFF, 0f),
            DText(bk(60, 70, 674, 90), "title", "Título", "{luogo|Mar en invierno}", DFont.SERIF, 80f, WHITE, DAlign.CENTER),
            DText(bk(60, 172, 674, 20), "sub", "Subtítulo", "{dal_al|Diciembre 2026}", DFont.MONO, 14f, WHITE, DAlign.CENTER, track = 0.3f, upper = true),
            DPhoto(bk(60, 230, 674, 420), 0, border = 4f),
            DPhoto(bk(60, 670, 327, 380), 1, border = 4f),
            DPhoto(bk(407, 670, 327, 380), 2, border = 4f),
            DText(bk(60, 1072, 674, 20), "foot", "Pie", "fotografías y recuerdos · {ANNO}", DFont.MONO, 13f, WHITE, DAlign.CENTER, track = 0.2f),
        ),
    )

    // T5 -----------------------------------------------------------------------------------------------
    private fun pearls(): Design {
        val pearl = 0xFFF3EEE4L
        return Design(
            "x-pearls", "Perlas", DesignFamily.TREND, DOrient.PORTRAIT, 480f, 672f, ACC,
            accents = listOf(0xFF1C1A24, 0xFF14213D, 0xFF3A1F2B, 0xFF2F3E46), motion = CollageMotion.WIPE,
            hint = "Elegante: título serif y un hilo de perlas alrededor de la foto en arco (que se ensarta en el video)",
            els = listOf(
                DPhoto(b(110, 80, 260, 330, C), 0, DShape.ARCH),
                DPath(b(96, 66, 288, 358, C), "M0 358 L0 144 C0 64 64 0 144 0 C224 0 288 64 288 144 L288 358", 288f, 358f, stroke = pearl, width = 9f, dash = listOf(0.1f, 16f), draw = true),
                DText(b(20, 440, 440, 60, C), "names", "Nombres", "Sofía & Luis", DFont.SERIF_IT, 50f, pearl, DAlign.CENTER),
                DPath(b(150, 510, 180, 10, C), "M0 5 L180 5", 180f, 10f, stroke = pearl, width = 7f, dash = listOf(0.1f, 14f), draw = true),
                DText(b(20, 530, 440, 18, C), "date", "Fecha", "{data_romana}", DFont.SANS, 12f, 0xFFD9CBB0, DAlign.CENTER, track = 0.42f),
                DPhoto(b(130, 566, 90, 90, C), 1, DShape.CIRCLE, border = 3f, borderColor = pearl),
                DPhoto(b(260, 566, 90, 90, C), 2, DShape.CIRCLE, border = 3f, borderColor = pearl),
            ),
        )
    }

    // T6 -----------------------------------------------------------------------------------------------
    private fun thenNow() = Design(
        "x-then-now", "Antes y ahora", DesignFamily.TREND, DOrient.PORTRAIT, 600f, 900f, 0xFF111111,
        accents = listOf(0xFFFFD23F, 0xFFFF4FD8, 0xFF7CFFCB), motion = CollageMotion.WIPE,
        hint = "Dos fotos para comparar, una de antes y una de ahora, con los años: en el video se descubren una tras otra",
        els = listOf(
            DPhoto(bk(0, 0, 600, 450), 0),
            DPhoto(bk(0, 450, 600, 450), 1),
            DRect(bk(0, 446, 600, 8), ACC),
            DText(bk(24, 24, 300, 70), "y0", "Año de antes", "2016", DFont.ANTON, 64f, WHITE, shadow = true),
            DText(bk(34, 100, 300, 20), "l0", "Etiqueta 1", "allora", DFont.MONO_BOLD, 16f, WHITE, pill = 0x99000000, pad = 10f),
            DText(bk(24, 474, 300, 70), "y1", "Año de ahora", "{ANNO}", DFont.ANTON, 64f, ACC, shadow = true, count = true),
            DText(bk(34, 550, 300, 20), "l1", "Etiqueta 2", "adesso", DFont.MONO_BOLD, 16f, WHITE, pill = 0x99000000, pad = 10f),
            DText(bk(276, 846, 300, 40), "hand", "Texto a mano", "seguimos siendo nosotros", DFont.HAND, 34f, WHITE, DAlign.RIGHT, rot = -3f, shadow = true),
        ),
    )

    // T7 -----------------------------------------------------------------------------------------------
    private fun windows(): Design {
        val grey = 0xFFC0C0C0L
        val els = arrayListOf<DEl>()
        fun window(x: Int, y: Int, w: Int, h: Int, id: String, label: String, title: String, slot: Int) {
            els += DRect(bk(x, y, w, h), grey, shadow = 14f)
            els += DRect(bk(x, y, w, h), 0xFF404040, stroke = 2f)
            els += DRect(bk(x + 6, y + 6, w - 12, 30), ACC2)
            els += DText(bk(x + 14, y + 12, w - 110, 18), id, label, title, DFont.MONO_BOLD, 14f, WHITE)
            for (k in 0 until 3) els += DRect(bk(x + w - 88 + k * 26, y + 10, 22, 22), grey)
            els += DText(bk(x + w - 36, y + 12, 22, 18), "", "", "×", DFont.MONO_BOLD, 16f, BLACK, DAlign.CENTER)
            els += DPhoto(bk(x + 12, y + 42, w - 24, h - 54), slot)
        }
        window(60, 90, 460, 400, "w0", "Ventana 1", "ricordi_{ANNO}.jpg", 0)
        window(360, 420, 380, 330, "w1", "Ventana 2", "{luogo|spiaggia}.jpg", 1)
        window(90, 620, 300, 260, "w2", "Ventana 3", "noi.jpg", 2)
        els += DRect(bk(220, 900, 400, 170), grey, shadow = 14f)
        els += DRect(bk(220, 900, 400, 170), 0xFF404040, stroke = 2f)
        els += DRect(bk(226, 906, 388, 30), ACC2)
        els += DText(bk(236, 912, 300, 18), "", "", "Pregunta", DFont.MONO_BOLD, 14f, WHITE)
        els += DText(bk(240, 950, 360, 30), "ask", "Pregunta", "¿Guardar estos recuerdos?", DFont.SANS, 22f, BLACK, DAlign.CENTER)
        for ((k, x) in listOf(270, 440).withIndex()) {
            els += DRect(bk(x, 1010, 130, 38), grey)
            els += DRect(bk(x, 1010, 130, 38), 0xFF404040, stroke = 2f)
            els += DText(bk(x, 1018, 130, 22), "b$k", "Pulsante ${k + 1}", "Sì", DFont.SANS, 18f, BLACK, DAlign.CENTER)
        }
        els += DPath(bk(540, 1030, 34, 52), "M0 0 L0 40 L10 30 L18 50 L25 47 L17 28 L32 28 Z", 34f, 52f, stroke = BLACK, width = 2f, fill = WHITE)
        return Design("x-windows", "Ventanas", DesignFamily.TREND, DOrient.PORTRAIT, 794f, 1123f, ACC,
            accents = listOf(0xFF008080, 0xFF3A6EA5, 0xFFB565A7), accents2 = listOf(0xFF000080, 0xFF6B0F6B, 0xFF1F1F1F),
            els = els, motion = CollageMotion.CASCADE, hint = "Las fotos abiertas en ventanas de una computadora vieja, con el puntero y la pregunta «¿Guardar estos recuerdos?»")
    }

    // T8 -----------------------------------------------------------------------------------------------
    private fun dump(): Design {
        val els = arrayListOf<DEl>(
            DText(bk(30, 26, 470, 76), "title", "Título", "dump de {mese}", DFont.HAND, 72f, BLACK),
            DText(bk(32, 106, 540, 18), "sub", "Subtítulo", "{foto} fotos · {ANNO}", DFont.MONO, 13f, BLACK, track = 0.15f, upper = true),
            DPath(bk(500, 34, 56, 56), SPARKLE, 100f, 100f, fill = ACC),
            DPath(bk(548, 96, 26, 26), SPARKLE, 100f, 100f, fill = ACC),
        )
        val rots = listOf(-1.5f, 1f, -0.5f, 2f, -2f, 0.5f, 1.5f, -1f, 0.8f)
        for (i in 0 until 9) {
            val x = 30 + (i % 3) * 183; val y = 140 + (i / 3) * 240
            els += DPhoto(bk(x, y, 174, 232), i, radius = 8f, rot = rots[i])
        }
        els += DPath(bk(210, 250, 60, 60), "M30 4 C38 22 40 24 56 30 C40 36 38 38 30 56 C22 38 20 36 4 30 C20 24 22 22 30 4 Z", 60f, 60f, stroke = ACC, width = 4f, draw = true)
        els += DPath(bk(330, 846, 120, 40), "M110 30 C80 10 50 6 14 20 M28 8 L12 20 L28 32", 120f, 40f, stroke = ACC, width = 4f, draw = true)
        els += DText(bk(30, 860, 300, 34), "note", "Nota", "¿la mejor? la quinta", DFont.HAND, 30f, ACC)
        return Design("x-dump", "Dump del mes", DesignFamily.TREND, DOrient.PORTRAIT, 600f, 900f, 0xFFFAF7F2,
            accents = listOf(0xFFFF4F79, 0xFF2F5BFF, 0xFFFF8C00), els = els, motion = CollageMotion.CASCADE,
            hint = "Nueve fotos apretadas, un poco chuecas, con estrellitas y flechas a mano: el photo dump del mes que entra al ritmo")
    }

    // C1 -----------------------------------------------------------------------------------------------
    private fun paperTrip(): Design {
        val cream = 0xFFF3EBDDL; val kraft = 0xFFC8A57AL
        return Design(
            "x-paper-trip", "Viaje de papel", DesignFamily.PAPER, DOrient.PORTRAIT, 794f, 1123f, kraft,
            accents = listOf(0xFF2A7F86, 0xFFE07A5F, 0xFF3D405B, 0xFF81B29A), motion = CollageMotion.SLIDE,
            hint = "Papel kraft, una tira de color rasgada con el nombre del lugar, etiqueta de maleta y sello de pasaporte",
            els = listOf(
                DPattern(b(0, 0, 794, 1123, F), DPatternKind.PAPER, 0x22FFFFFF, 0f),
                DTorn(bk(30, 46, 734, 668), cream, rot = -2f, seed = 3),
                DPhoto(bk(70, 86, 654, 520), 0, DShape.TORN, rot = -2f, border = 6f, seed = 4),
                DRect(bk(330, 30, 130, 36), TAPE, rot = 3f),
                DTorn(bk(-40, 560, 560, 120), ACC, rot = -4f, seed = 9),
                DText(bk(30, 576, 480, 90), "title", "Título", "{luogo|Lisbona}", DFont.SERIF, 86f, WHITE, line = 0.95f, rot = -4f),
                DPhoto(bk(440, 650, 310, 290), 1, DShape.TORN, rot = 5f, border = 6f, shadow = 8f, seed = 7),
                DRect(bk(560, 630, 100, 32), TAPE, rot = -30f),
                DPath(bk(70, 690, 60, 110), "M30 0 C10 40 50 70 36 110", 60f, 110f, stroke = 0xFF7A5C3A, width = 2.5f),
                DRect(bk(66, 760, 300, 160), cream, radius = 14f, rot = -6f, shadow = 8f),
                DRect(bk(84, 825, 26, 26), kraft, circle = true),
                DText(bk(126, 784, 220, 16), "tagL", "Etiqueta", "Equipaje · {ANNO}", DFont.MONO, 12f, BLACK, track = 0.15f, upper = true, rot = -6f),
                DText(bk(126, 806, 230, 90), "tag", "Destino", "{luogo|LIS}", DFont.ANTON, 84f, BLACK, line = 0.9f, upper = true, rot = -6f),
                DText(bk(70, 975, 440, 66), "hand", "Texto a mano", "{Mese} {ANNO}", DFont.HAND, 54f, 0xFF3B2A1A, rot = -3f),
                DStamp(bk(540, 940, 200, 200), "stamp", "Sello", "{luogo|Lisbona}\n{data}", 0xFFC2412D, rot = 12f),
            ),
        )
    }

    // C2 -----------------------------------------------------------------------------------------------
    private fun paperNews(): Design {
        val news = 0xFFEFEBE3L
        return Design(
            "x-paper-news", "Primera plana", DesignFamily.PAPER, DOrient.PORTRAIT, 794f, 1123f, 0xFF1C1C1C,
            accents = listOf(0xFFE63946, 0xFF1D4ED8, 0xFFFF8C00), motion = CollageMotion.WIPE,
            hint = "Tu hazaña deportiva en primera plana: titular enorme, foto con trama de periódico, columnas y números",
            els = listOf(
                DTorn(bk(18, 18, 758, 1087), news, seed = 11, shadow = 10f),
                DText(bk(50, 52, 694, 64), "mast", "Encabezado", "Crónica del Domingo", DFont.SERIF, 62f, BLACK, DAlign.CENTER),
                DRect(bk(50, 124, 694, 3), BLACK),
                DText(bk(50, 134, 694, 18), "edition", "Subencabezado", "{giorno} · Edición especial · {luogo|Lombardía}", DFont.MONO, 12f, BLACK, DAlign.CENTER, track = 0.1f, upper = true),
                DRect(bk(50, 160, 694, 2), BLACK),
                DText(bk(50, 176, 694, 220), "title", "Título", "¡Hazaña\nen la cumbre!", DFont.ANTON, 118f, BLACK, line = 0.88f, upper = true),
                DPhoto(bk(50, 410, 694, 420), 0, filter = DFilter.HALFTONE, ink = BLACK, paper = news),
                DText(bk(50, 838, 694, 22), "cap", "Pie de foto", "El grupo al ataque de la última subida", DFont.SERIF_IT, 17f, BLACK),
                DPattern(bk(50, 872, 450, 200), DPatternKind.TEXTLINES, 0xFF8C8A85, 11f),
                DRect(bk(520, 872, 224, 200), BLACK),
                DText(bk(538, 888, 190, 14), "l1", "Etiqueta 1", "Distancia", DFont.MONO, 11f, WHITE, track = 0.18f, upper = true),
                DText(bk(538, 904, 190, 48), "s1", "Distancia", "142 km", DFont.ANTON, 44f, WHITE, count = true),
                DText(bk(538, 960, 190, 14), "l2", "Etiqueta 2", "Desnivel", DFont.MONO, 11f, WHITE, track = 0.18f, upper = true),
                DText(bk(538, 976, 190, 48), "s2", "Desnivel", "2.340 m", DFont.ANTON, 44f, ACC, count = true),
                DText(bk(538, 1034, 190, 22), "s3", "Tiempo", "Tiempo 5:12", DFont.MONO_BOLD, 16f, WHITE),
                DTorn(bk(470, 330, 300, 84), ACC, rot = -8f, seed = 21),
                DText(bk(480, 346, 280, 52), "badge", "Sello", "Record!", DFont.ANTON, 46f, WHITE, DAlign.CENTER, upper = true, rot = -8f),
                DPhoto(bk(560, 600, 200, 170), 1, DShape.TORN, rot = 4f, border = 6f, shadow = 8f, seed = 23),
            ),
        )
    }

    // C3 -----------------------------------------------------------------------------------------------
    private fun paperLove(): Design {
        val rose = 0xFF8E3B4EL
        return Design(
            "x-paper-love", "Papel pastel", DesignFamily.PAPER, DOrient.PORTRAIT, 480f, 672f, 0xFFF7EFE9,
            accents = listOf(0xFFF4C6C9, 0xFFFFD6A5, 0xFFCDB4DB, 0xFFBDE0FE), motion = CollageMotion.DROP,
            hint = "Papeles pastel rasgados en capas, los nombres escritos a mano y un corazón que se dibuja",
            els = listOf(
                DTorn(bk(-30, -30, 540, 250), ACC, rot = -5f, seed = 31, edges = 4),
                DTorn(bk(-30, 440, 540, 280), 0xFFCFDDC8, rot = 4f, seed = 33, edges = 1),
                DTorn(bk(300, 330, 220, 170), 0xFFF3E3C8, rot = 10f, seed = 35),
                DPhoto(bk(60, 70, 330, 380), 0, DShape.TORN, rot = -3f, border = 6f, shadow = 8f, seed = 37),
                DRect(bk(170, 52, 110, 30), TAPE, rot = -4f),
                DPhoto(bk(290, 360, 150, 150), 1, DShape.TORN, rot = 7f, border = 5f, shadow = 8f, seed = 39),
                DText(bk(24, 500, 432, 64), "names", "Nombres", "Julia & Marcos", DFont.HAND, 58f, rose, DAlign.CENTER, rot = -2f),
                DText(bk(24, 572, 432, 18), "date", "Fecha", "{data_romana}", DFont.SANS, 12f, 0xFF5A3A3A, DAlign.CENTER, track = 0.42f),
                DPath(bk(388, 452, 66, 58), "M33 54 C10 38 2 24 6 14 C10 4 24 2 33 14 C42 2 56 4 60 14 C64 24 56 38 33 54 Z", 66f, 58f, stroke = rose, width = 3f, draw = true),
            ),
        )
    }

    // C4 -----------------------------------------------------------------------------------------------
    private fun paperRecipe(): Design {
        val ink = 0xFF2B2B2BL
        return Design(
            "x-paper-recipe", "Receta", DesignFamily.PAPER, DOrient.PORTRAIT, 794f, 1123f, 0xFFB98B5E,
            accents = listOf(0xFFC0392B, 0xFF2B59FF, 0xFF2D6A4F), motion = CollageMotion.DROP,
            hint = "La hoja de cuaderno rasgada, las fotos con cinta, ingredientes y procedimiento escritos a mano",
            els = listOf(
                DPattern(b(0, 0, 794, 1123, F), DPatternKind.PAPER, 0x22FFFFFF, 0f),
                DTorn(bk(36, 40, 722, 1050), 0xFFFBF8F0, rot = -1f, seed = 41, edges = 1, shadow = 12f),
                DPattern(bk(36, 40, 722, 1050), DPatternKind.LINES, 0x552B6CB0, 34f, rot = -1f),
                DText(bk(110, 80, 560, 190), "title", "Título", "La pasta\nde la abuela", DFont.HAND, 86f, ACC, line = 0.95f),
                DPhoto(bk(430, 280, 290, 290), 0, rot = 5f, border = 10f, bottomPad = 34f, shadow = 12f),
                DRect(bk(520, 264, 110, 32), TAPE, rot = -6f),
                DText(bk(110, 300, 300, 40), "ingL", "Etiqueta", "Ingredientes", DFont.SERIF_IT, 34f, BLACK),
                DText(bk(110, 346, 300, 200), "ing", "Ingredientes", "· 400 g de harina\n· 4 huevos\n· una pizca de sal\n· mucha paciencia", DFont.HAND, 34f, ink, line = 1.2f),
                DPhoto(bk(110, 620, 280, 220), 1, rot = -4f, border = 10f, shadow = 12f),
                DRect(bk(190, 604, 100, 30), TAPE, rot = 5f),
                DText(bk(430, 600, 300, 40), "stepL", "Etiqueta 2", "Procedimiento", DFont.SERIF_IT, 34f, BLACK),
                DText(bk(430, 646, 300, 330), "steps", "Procedimiento", "Haz un volcán de harina, huevos al centro, amasa hasta que quede lisa. Reposa media hora y luego estira la masa delgada.",
                    DFont.HAND, 30f, ink, line = 1.15f, wrap = true),
                DStamp(bk(110, 880, 170, 170), "stamp", "Sello", "Hecho en casa\n{ANNO}", ACC, rot = -10f),
            ),
        )
    }

    // C5 -----------------------------------------------------------------------------------------------
    /** Due strisce di carta strappata sfalsate sul muro, le foto dentro e una dedica in corsivo dorato di traverso. */
    private fun paperStrips(): Design {
        val paper = 0xFFEEEBE4L; val tape = 0xC8F4F1EAL
        val gold = listOf(0xFFFFF0B8L, 0xFFD9A93BL, 0xFFFFE08AL, 0xFF9A6D16L, 0xFFF0CB66L)
        val els = arrayListOf<DEl>(
            DPattern(b(0, 0, 794, 1123, F), DPatternKind.WALL, BLACK, 0f),
            DTorn(bk(40, -20, 330, 1030), paper, seed = 3, edges = 4, shadow = 16f),
            DTorn(bk(424, 110, 330, 1040), paper, seed = 8, edges = 1, shadow = 16f),
        )
        for (i in 0 until 3) els += DPhoto(bk(62, 18 + i * 318, 286, 300), i)
        for (i in 0 until 3) els += DPhoto(bk(446, 150 + i * 318, 286, 300), 3 + i)
        els += listOf(
            DRect(bk(530, 88, 120, 40), tape, rot = -6f),
            DRect(bk(150, 985, 120, 40), tape, rot = 5f),
            DText(bk(62, 972, 286, 22), "date", "Fecha", "{data}", DFont.MONO_BOLD, 15f, 0xFF6B6660, DAlign.CENTER, track = 0.2f),
            DText(bk(-20, 340, 834, 440), "title", "Dedicatoria", "¡¡¡80 felicidades,\nabuelo!!!", DFont.SCRIPT, 150f, WHITE, DAlign.CENTER,
                line = 1.05f, rot = -18f, gradient = gold, shadow = true, count = true, shine = true),
        )
        return Design("x-paper-strips", "Tiras en la pared", DesignFamily.PAPER, DOrient.PORTRAIT, 794f, 1123f, ACC,
            accents = listOf(0xFF6E8584L, 0xFF8A7F74L, 0xFF3A3F47L, 0xFFB7A694L, 0xFF7A5C5CL), els = els, motion = CollageMotion.CASCADE,
            hint = "Cumpleaños y aniversarios: dos tiras de papel rasgado con seis fotos en la pared y la dedicatoria en cursiva dorada de lado (los años cuentan en el video)")
    }

    private fun paperWall() = Design(
        "x-paper-wall", "Carteles", DesignFamily.PAPER, DOrient.PORTRAIT, 794f, 1123f, 0xFF8F8C86,
        accents = listOf(0xFFD93A2B, 0xFF1D4ED8, 0xFF16A34A, 0xFFE91E63), motion = CollageMotion.SLIDE, grain = true,
        hint = "Carteles pegados en la pared y rasgados, uno sobre otro: ciudades, conciertos, noches de salida",
        els = listOf(
            DPattern(b(0, 0, 794, 1123, F), DPatternKind.PAPER, 0x22000000, 0f),
            DTorn(bk(560, 40, 160, 60), 0xFFEDE7DA, rot = 8f, seed = 61),
            DTorn(bk(-30, 30, 560, 560), 0xFFF2C230, rot = -3f, seed = 51),
            DText(bk(20, 80, 360, 260), "title", "Título", "Sabato\nnotte", DFont.ANTON, 118f, BLACK, line = 0.86f, upper = true, rot = -3f),
            DPhoto(bk(400, 110, 440, 620), 0, DShape.TORN, rot = 2f, border = 7f, shadow = 10f, filter = DFilter.BW, seed = 53),
            DTorn(bk(-40, 610, 620, 230), ACC, rot = 4f, seed = 55),
            DText(bk(10, 650, 520, 90), "city", "Ciudad", "{luogo|Milano}", DFont.ARCHIVO, 84f, WHITE, upper = true, rot = 4f),
            DText(bk(14, 748, 520, 24), "info", "Información", "{data} · entrada libre", DFont.MONO_BOLD, 18f, WHITE, track = 0.1f, rot = 4f),
            DPhoto(bk(430, 700, 340, 340), 1, DShape.TORN, rot = -5f, border = 7f, shadow = 10f, seed = 57),
            DPhoto(bk(40, 860, 340, 230), 2, DShape.TORN, rot = -2f, border = 7f, shadow = 10f, filter = DFilter.BW, seed = 59),
            DTorn(bk(370, 1040, 170, 60), 0xFFF2C230, rot = -6f, seed = 63),
        ),
    )

    // C6 -----------------------------------------------------------------------------------------------
    private fun paperRansom() = Design(
        "x-paper-ransom", "Letras recortadas", DesignFamily.PAPER, DOrient.PORTRAIT, 794f, 1123f, 0xFFF2EFE8,
        accents = listOf(0xFFE63946, 0xFF1D4ED8, 0xFF111111), motion = CollageMotion.DROP,
        hint = "El texto hecho con letras recortadas de distintos periódicos: cada letra llega sola en el video",
        els = listOf(
            DPattern(b(0, 0, 794, 1123, F), DPatternKind.PAPER, 0x14000000, 0f),
            DRansom(bk(40, 60, 714, 380), "title", "Letras recortadas", "Saludos desde\n{luogo|Roma}"),
            DPhoto(bk(60, 470, 420, 540), 0, DShape.TORN, rot = -3f, border = 7f, shadow = 10f, seed = 71),
            DRect(bk(210, 452, 120, 34), TAPE, rot = -5f),
            DPhoto(bk(440, 520, 310, 400), 1, DShape.TORN, rot = 5f, border = 7f, shadow = 10f, seed = 73),
            DPath(bk(520, 920, 200, 120), "M10 100 C60 60 120 50 180 20 M160 12 L182 20 L172 42", 200f, 120f, stroke = ACC, width = 5f, draw = true),
            DText(bk(470, 1000, 300, 70), "hand", "Texto a mano", "besos, {ANNO}", DFont.HAND, 54f, ACC, rot = -4f),
        ),
    )

    // C7 -----------------------------------------------------------------------------------------------
    private fun paperSticker() = Design(
        "x-paper-sticker", "Calcomanía", DesignFamily.PAPER, DOrient.PORTRAIT, 794f, 1123f, ACC,
        accents = listOf(0xFFFF6B35, 0xFF2F5BFF, 0xFFFFC300, 0xFFEF476F), accents2 = listOf(0xFF1B998B, 0xFF111111, 0xFF7B2CBF, 0xFFF7F3EA),
        motion = CollageMotion.CASCADE,
        hint = "La persona recortada como una calcomanía con borde blanco, sobre papeles de colores rasgados y un título enorme",
        els = listOf(
            DTorn(bk(-40, 560, 880, 600), ACC2, rot = -6f, seed = 81, edges = 1),
            DTorn(bk(-40, -40, 520, 150), 0xFFF7F3EA, rot = 3f, seed = 83, edges = 6),
            DText(bk(40, 44, 420, 30), "kicker", "Agenda", "Diario · {Mese} {ANNO}", DFont.MONO_BOLD, 16f, BLACK, track = 0.15f, upper = true, rot = 3f),
            DText(bk(20, 130, 754, 260), "title", "Título", "{stagione|estate}", DFont.ANTON, 250f, WHITE, DAlign.CENTER, line = 0.85f, upper = true),
            DPhoto(bk(110, 230, 574, 760), 0, radius = 30f, border = 16f, shadow = 12f, cutout = true),
            DText(bk(470, 980, 300, 44), "tag", "Etiqueta", "{luogo|Puglia}", DFont.GROTESK, 30f, BLACK, rot = -4f, pill = WHITE, pad = 18f),
            DText(bk(60, 1000, 400, 70), "hand", "Texto a mano", "nosotros, así.", DFont.HAND, 58f, WHITE, rot = -3f),
            DPath(bk(640, 250, 70, 70), SPARKLE, 100f, 100f, fill = WHITE),
            DPath(bk(84, 330, 50, 50), SPARKLE, 100f, 100f, fill = WHITE),
            DPath(bk(560, 900, 170, 44), "M0 22 C20 0 40 44 60 22 C80 0 100 44 120 22 C140 0 160 44 170 22", 170f, 44f, stroke = WHITE, width = 5f, draw = true),
            DPath(bk(70, 860, 120, 110), "M10 10 C40 60 70 80 110 96 M92 80 L112 98 L88 104", 120f, 110f, stroke = WHITE, width = 5f, draw = true),
        ),
    )

    // C8 -----------------------------------------------------------------------------------------------
    private fun paperRiso(): Design {
        val paper = 0xFFF4EFE6L
        return Design(
            "x-paper-riso", "Riso", DesignFamily.PAPER, DOrient.PORTRAIT, 794f, 1123f, paper,
            accents = listOf(0xFFFF48B0, 0xFFFF6C2F, 0xFFFFE800), accents2 = listOf(0xFF0078BF, 0xFF00A95C, 0xFF3D2B56),
            motion = CollageMotion.WIPE, grain = true,
            hint = "Póster impreso en risografía: dos tintas de trama un poco desfasadas, papel con grano",
            els = listOf(
                DPattern(b(0, 0, 794, 1123, F), DPatternKind.PAPER, 0x1A000000, 0f),
                DText(bk(50, 40, 700, 150), "title", "Título", "{luogo|Tokyo}", DFont.GROTESK, 150f, ACC2, line = 0.85f, track = -0.04f),
                DPhoto(bk(60, 190, 674, 640), 0, filter = DFilter.HALFTONE, ink = ACC, paper = paper),
                DPhoto(bk(60, 190, 674, 640), 0, filter = DFilter.HALFTONE, ink = ACC2, paper = 0L, multiply = true, shiftX = 5f, shiftY = 4f),
                DText(bk(54, 846, 520, 40), "sub", "Subtítulo", "Noches de neón, {Mese} {ANNO}", DFont.MONO_BOLD, 22f, ACC, upper = true, track = 0.08f),
                DPhoto(bk(60, 900, 200, 200), 1, DShape.CIRCLE, filter = DFilter.HALFTONE, ink = ACC2, paper = 0xFFFFD6E8),
                DText(bk(280, 958, 220, 90), "info", "Información", "{giorni|3} días\n2 personas\n1 rollo", DFont.MONO, 16f, ACC2, line = 1.5f),
                DText(bk(470, 860, 280, 220), "num", "Número grande", "{AA}", DFont.ANTON, 230f, ACC2, DAlign.RIGHT, line = 0.9f, stroke = 3f),
            ),
        )
    }

    // 1 ------------------------------------------------------------------------------------------------
    private fun cover() = Design(
        "x-cover", "Portada", DesignFamily.MAGAZINE, DOrient.PORTRAIT, 794f, 1123f, 0xFF1A1714,
        accents = listOf(WHITE, 0xFFFFD23F, 0xFFFF5A36, 0xFF111111), motion = CollageMotion.STILL,
        hint = "Una foto a página completa con el título enorme: la persona pasa delante de las letras",
        els = listOf(
            DPhoto(b(0, 0, 794, 1123, F), 0),
            DRect(b(0, 0, 794, 1123, F), 0, grad = listOf(0f to 0x40000000L, 0.28f to 0L, 0.55f to 0L, 1f to 0x99000000L)),
            DText(b(40, 40, 357, 22), "issue", "Número", "N.º {mese_n}", DFont.MONO, 15f, WHITE, track = 0.13f, upper = true),
            DText(b(397, 40, 357, 22), "date", "Fecha", "{Mese} {ANNO}", DFont.MONO, 15f, WHITE, DAlign.RIGHT, track = 0.13f, upper = true),
            DText(b(30, 58, 740, 216), "title", "Título", "{stagione|estate}", DFont.SERIF, 268f, ACC, line = 0.8f, track = -0.037f, behind = 0),
            DText(b(44, 520, 270, 92, C), "line1", "Título interior", "El mar de {mese|septiembre}", DFont.SERIF_IT, 46f, WHITE, wrap = true, shadow = true),
            DRect(b(44, 634, 60, 2, C), WHITE),
            DText(b(44, 658, 270, 50, C), "line2", "Subtítulo", "{foto} fotos y un atardecer\nque no terminaba nunca", DFont.SANS, 18f, WHITE, line = 1.35f, shadow = true),
            DText(b(44, 1020, 420, 20, E), "kicker", "Agenda", "Diario de viaje", DFont.SANS, 15f, WHITE, track = 0.2f, upper = true),
            DText(b(44, 1046, 420, 20, E), "vol", "Subtítulo 2", "Vol. 3 — {luogo|Cerdeña}", DFont.MONO, 13f, WHITE),
            DText(b(500, 966, 250, 113, E), "num", "Número grande", "{AA}", DFont.SERIF, 150f, WHITE, DAlign.RIGHT, line = 0.75f, stroke = 2f),
        ),
    )

    // 2 ------------------------------------------------------------------------------------------------
    private fun reportage(): Design {
        val ink = 0xFF0E1A2BL
        return Design(
            "x-report", "Reportage", DesignFamily.MAGAZINE, DOrient.PORTRAIT, 480f, 672f, WHITE,
            accents = listOf(0xFFE8452C, ink, 0xFF1D4ED8), motion = CollageMotion.SLIDE,
            hint = "Página de revista: título que muerde la foto, dos líneas de relato y una foto en arco",
            els = listOf(
                DPhoto(b(0, 0, 480, 400, F), 0),
                DText(b(24, 24, 400, 16), "kicker", "Agenda", "Reportaje · {luogo|Liguria}", DFont.MONO, 11f, WHITE, track = 0.18f, upper = true, shadow = true),
                DText(b(22, 300, 440, 172, E), "title", "Título", "Cinque\nTerre", DFont.SERIF, 104f, ACC, line = 0.82f, track = -0.03f, font2 = DFont.SERIF_IT),
                DText(b(24, 512, 214, 96, E), "body", "Relato", "Tres días a pie por el Sentiero Azzurro, de Riomaggiore a Monterosso, con el mar siempre a la izquierda.",
                    DFont.SANS, 13f, ink, line = 1.45f, wrap = true),
                DText(b(24, 614, 214, 16, E), "date", "Date", "{dal_al|03 → 05.06.2026}", DFont.MONO, 11f, ink, track = 0.09f),
                DPhoto(b(260, 430, 196, 196, E), 1, DShape.ARCH),
                DText(b(380, 636, 76, 14, E), "", "", "p. 14", DFont.MONO, 10f, ink, DAlign.RIGHT),
            ),
        )
    }

    // 3 ------------------------------------------------------------------------------------------------
    private fun letters() = Design(
        "x-letters", "Letras-foto", DesignFamily.MAGAZINE, DOrient.PORTRAIT, 1134f, 1701f, ACC,
        accents = listOf(0xFFF2F4F7, WHITE, 0xFFFFE9D6, 0xFF101828), accents2 = listOf(0xFF13306B, 0xFF111111, WHITE, 0xFFE8452C),
        motion = CollageMotion.CASCADE, hint = "Una palabra gigante, con una foto dentro de cada letra (escribe la tuya: ROMA, 2026, NOSOTROS…)",
        els = listOf(
            DText(b(56, 64, 500, 30), "place", "Lugar", "{luogo|Sardegna}", DFont.MONO, 26f, ACC2, track = 0.115f, upper = true),
            DText(b(578, 64, 500, 30), "date", "Fecha", "{Mese} {ANNO}", DFont.MONO, 26f, ACC2, DAlign.RIGHT, track = 0.115f, upper = true),
            DPhotoWord(b(56, 150, 1022, 1320, F), "word", "Palabra", "MAR", DFont.ANTON, 0, gap = 14f),
            DText(b(56, 1545, 620, 92, E), "line", "Frase", "Cuatro calitas,\nuna por cada letra.", DFont.GROTESK, 40f, ACC2, line = 1.1f, bottom = true),
            DText(b(700, 1505, 378, 132, E), "list", "Lista", "Cala Goloritzé\nCala Mariolu\nCala Luna\nCapo Testa", DFont.MONO, 22f, ACC2, DAlign.RIGHT, line = 1.5f, bottom = true),
        ),
    )

    // 4 ------------------------------------------------------------------------------------------------
    private fun bentoTrip(): Design {
        fun x(c: Int) = 24 + c * 180
        val els = listOf(
            DPhoto(b(x(0), x(0), 348, 348), 0, radius = 28f),
            DText(b(38, 330, 200, 28), "chip", "Etiqueta", "{luogo0|Oia}", DFont.GROTESK, 14f, BLACK, pill = WHITE, pad = 14f),
            DRect(b(x(2), x(0), 348, 168), ACC, radius = 28f),
            DText(b(408, 44, 300, 16), "region", "Zona", "Cícladas", DFont.MONO, 12f, WHITE, track = 0.17f, upper = true),
            DText(b(408, 107, 316, 66), "title", "Título", "{luogo|Grecia}", DFont.GROTESK, 76f, WHITE, line = 0.85f, track = -0.04f, bottom = true),
            DRect(b(x(2), x(1), 168, 168), WHITE, radius = 28f),
            DText(b(404, 220, 140, 74), "days", "Días", "{giorni|12}", DFont.GROTESK, 92f, BLACK, line = 0.8f, track = -0.043f, count = true),
            DText(b(404, 336, 140, 20), "daysL", "Debajo de los días", "giorni", DFont.GROTESK_MED, 15f, BLACK),
            DPhoto(b(x(3), x(1), 168, 348), 1, radius = 28f),
            DPhoto(b(x(0), x(2), 348, 168), 2, radius = 28f),
            DPhoto(b(x(2), x(2), 168, 168), 3, radius = 28f),
            DPhoto(b(x(0), x(3), 168, 168), 4, radius = 28f),
            DRect(b(x(1), x(3), 168, 168), 0xFFFFD23F, radius = 28f),
            DText(b(224, 580, 130, 20), "tempL", "Encima de la temperatura", "acqua", DFont.GROTESK_MED, 15f, BLACK),
            DText(b(224, 650, 130, 66), "temp", "Temperatura", "26°", DFont.GROTESK, 80f, BLACK, line = 0.8f, track = -0.04f, count = true, bottom = true),
            DRect(b(x(2), x(3), 348, 168), 0xFF0F172A, radius = 28f),
            DText(b(408, 582, 300, 16), "month", "Mese", "{Mese} {ANNO}", DFont.MONO, 12f, WHITE, track = 0.17f, upper = true),
            DText(b(408, 650, 300, 64), "places", "Lugares", "{luoghi|Santorini · Naxos · Paros · Milos}", DFont.GROTESK, 30f, WHITE, wrap = true, bottom = true),
        )
        return Design("x-bento-trip", "Bento viaje", DesignFamily.BENTO, DOrient.SQUARE, 756f, 756f, 0xFFE9EEF4,
            accents = listOf(0xFF1D4ED8, 0xFFE8452C, 0xFF0F766E, 0xFF7C3AED), els = els, motion = CollageMotion.CASCADE,
            hint = "El viaje en recuadros: lugar, días y mes los toma de las fotos")
    }

    // 5 ------------------------------------------------------------------------------------------------
    private fun bentoRide(): Design {
        val ink = 0xFF121212L
        fun label(x: Int, y: Int, id: String, t: String, c: Long = ink) = DText(b(x + 18, y + 14, 140, 14), id, "Etiqueta", t, DFont.MONO, 11f, c, track = 0.18f, upper = true)
        val els = listOf(
            DPhoto(b(24, 24, 528, 348), 0, radius = 24f),
            DRect(b(24, 24, 528, 348), 0, radius = 24f, grad = listOf(0.45f to 0L, 1f to 0x8C000000L)),
            DText(b(46, 287, 480, 16), "date", "Fecha", "{giorno}", DFont.MONO, 12f, WHITE, track = 0.17f, upper = true),
            DText(b(46, 307, 480, 47), "title", "Título", "Vuelta de los Colli", DFont.GROTESK, 52f, WHITE, line = 0.9f, track = -0.04f),
            DRect(b(564, 24, 168, 168), ACC, radius = 24f),
            label(564, 24, "l1", "Distancia · km"),
            DText(b(582, 127, 140, 51), "km", "Distancia", "142", DFont.GROTESK, 64f, ink, line = 0.8f, track = -0.045f, count = true, bottom = true),
            DRect(b(564, 204, 168, 168), ink, radius = 24f),
            label(564, 204, "l2", "Desnivel · m", WHITE),
            DText(b(582, 318, 140, 40), "up", "Desnivel", "2.340", DFont.GROTESK, 50f, WHITE, line = 0.8f, track = -0.04f, count = true, bottom = true),
            DRect(b(24, 384, 348, 168), WHITE, radius = 24f),
            label(24, 384, "l3", "Altimetría"),
            DText(b(200, 398, 154, 14), "max", "Altitud máxima", "máx. 1.214 m", DFont.MONO, 11f, ink, DAlign.RIGHT, track = 0.1f),
            DPath(b(38, 446, 320, 92), "M0 82 L18 78 L34 70 L52 72 L70 55 L86 40 L98 22 L110 30 L124 46 L140 50 L156 36 L170 18 L182 8 L196 24 L212 44 L230 52 L246 60 L262 58 L280 72 L300 80 L300 90 L0 90 Z",
                300f, 90f, fill = ACC),
            DPath(b(38, 446, 320, 92), "M0 82 L18 78 L34 70 L52 72 L70 55 L86 40 L98 22 L110 30 L124 46 L140 50 L156 36 L170 18 L182 8 L196 24 L212 44 L230 52 L246 60 L262 58 L280 72 L300 80",
                300f, 90f, stroke = ink, width = 2.5f, draw = true),
            DPhoto(b(384, 384, 168, 168), 1, radius = 24f),
            DRect(b(564, 384, 168, 168), WHITE, radius = 24f),
            label(564, 384, "l4", "Tiempo"),
            DText(b(582, 493, 140, 45), "time", "Tiempo", "5:12", DFont.GROTESK, 56f, ink, line = 0.8f, track = -0.035f, count = true, bottom = true),
            DPhoto(b(24, 564, 168, 168), 2, radius = 24f),
            DRect(b(204, 564, 348, 168), WHITE, radius = 24f),
            DText(b(224, 580, 300, 14), "l5", "Etiqueta", "Con", DFont.MONO, 11f, ink, track = 0.18f, upper = true),
            DText(b(224, 652, 310, 64), "with", "Con quién", "Luis, Marta\ny el viento en contra", DFont.GROTESK, 30f, ink, line = 1.05f, bottom = true),
            DRect(b(564, 564, 168, 168), ACC, radius = 24f),
            label(564, 564, "l6", "Media · km/h"),
            DText(b(582, 678, 140, 40), "avg", "Media", "27,3", DFont.GROTESK, 50f, ink, line = 0.8f, track = -0.04f, count = true, bottom = true),
        )
        return Design("x-bento-ride", "Salida en bici", DesignFamily.BENTO, DOrient.SQUARE, 756f, 756f, 0xFFF1F1EC,
            accents = listOf(0xFFFF6B1A, 0xFFFFD23F, 0xFF5EEAD4, 0xFFF472B6), els = els, motion = CollageMotion.CASCADE,
            hint = "Bici, running, montaña: kilómetros, desnivel y tiempo que cuentan en el video, el perfil que se dibuja")
    }

    // 6 ------------------------------------------------------------------------------------------------
    private fun year(): Design {
        val els = arrayListOf<DEl>(
            DText(b(40, 40, 560, 226), "year", "Año", "{ANNO}", DFont.GROTESK, 250f, ACC, line = 0.74f, track = -0.056f, count = true, bottom = true),
            DText(b(560, 212, 194, 26), "title", "Título", "Mi año", DFont.GROTESK, 26f, BLACK, DAlign.RIGHT),
            DText(b(560, 244, 194, 16), "sub", "Subtítulo", "12 meses · 12 fotos", DFont.MONO, 12f, BLACK, DAlign.RIGHT, line = 1.3f, track = 0.08f),
        )
        val months = DesignTexts.monthShort
        for (i in 0 until 12) {
            val x = 40 + (i % 3) * 242; val y = 282f + (i / 3) * 203.25f
            els += DPhoto(b(x, y, 230, 191.25f, K), i, radius = 18f)
            els += DText(b(x + 10, y + 16, 90, 13, K), "", "", months[i], DFont.GROTESK, 13f, BLACK, pill = WHITE, pad = 11f, slotMonth = i)
        }
        return Design("x-year", "Mi año", DesignFamily.BENTO, DOrient.PORTRAIT, 794f, 1123f, WHITE,
            accents = listOf(0xFF111111, 0xFFE8452C, 0xFF1D4ED8, 0xFF0F766E), els = els, motion = CollageMotion.CASCADE,
            hint = "Doce fotos, una por mes: si las fotos tienen fecha, escribe solo el mes correcto")
    }

    // 7 ------------------------------------------------------------------------------------------------
    private fun scrap(): Design {
        val blue = 0xFF2B59FFL
        return Design(
            "x-scrap", "Diario recortado", DesignFamily.SCRAP, DOrient.PORTRAIT, 794f, 1123f, 0xFFFBFBF8,
            accents = listOf(0xFFFF3B30, blue, 0xFF111111, 0xFF16A34A), motion = CollageMotion.DROP,
            hint = "Fotos rasgadas y pegadas con cinta sobre papel cuadriculado, con textos a mano",
            els = listOf(
                DPattern(b(0, 0, 794, 1123, F), DPatternKind.GRID, 0x1A2B59FF, 24f),
                DPhoto(b(56, 72, 450, 330), 0, DShape.TORN_BOTTOM, rot = -3f, border = 10f, bottomPad = 20f, shadow = 10f),
                DPhoto(b(526, 128, 206, 246), 1, rot = 6f, border = 10f, bottomPad = 30f, shadow = 12f),
                DText(b(500, 384, 226, 30), "cap1", "Pie de foto", "{luogo1|Ulun Danu}", DFont.HAND, 26f, 0xFF222222, DAlign.CENTER, rot = 6f),
                DRect(b(562, 98, 120, 34), TAPE, rot = -8f),
                DText(b(62, 470, 680, 94), "title", "Título", "{luogo|Perú} → {luogo2|Bali}", DFont.HAND, 104f, blue, line = 0.9f),
                DText(b(62, 564, 520, 60), "sub", "Subtítulo", "la vuelta larga", DFont.HAND, 60f, ACC),
                DPath(b(66, 612, 310, 30), "M4 18 C60 6 120 26 180 14 C240 2 270 10 296 20", 300f, 30f, stroke = ACC, width = 5f, draw = true),
                DPath(b(470, 410, 140, 120), "M8 110 C40 70 80 60 120 18 M100 14 L122 16 L118 38", 140f, 120f, stroke = blue, width = 4f, draw = true),
                DPhoto(b(68, 684, 280, 378), 2, DShape.TORN_TOP, rot = -2f, border = 10f, shadow = 10f),
                DPhoto(b(326, 648, 174, 174), 3, DShape.CIRCLE, rot = -10f, border = 8f, shadow = 12f),
                DPhoto(b(448, 670, 280, 360), 4, rot = 3f, border = 10f, shadow = 14f),
                DRect(b(404, 650, 90, 34), TAPE, rot = -40f),
                DRect(b(664, 1010, 90, 34), TAPE, rot = -40f),
                DText(b(392, 884, 160, 18), "day", "Etiqueta", "DAY {giorni|07}", DFont.MONO_BOLD, 15f, WHITE, track = 0.13f, rot = -4f, pill = BLACK, pad = 12f, square = true),
                DText(b(606, 536, 170, 84), "note", "Nota", "y luego\n¡{luogo4|París}!", DFont.HAND, 40f, ACC, line = 1f, rot = -6f),
            ),
        )
    }

    // 8 ------------------------------------------------------------------------------------------------
    private fun stickers() = Design(
        "x-stickers", "Calcomanías", DesignFamily.SCRAP, DOrient.PORTRAIT, 384f, 576f, ACC,
        accents = listOf(0xFF2F5BFF, 0xFFFF5A36, 0xFF16A34A, 0xFF111111), motion = CollageMotion.DROP,
        hint = "Fotos como calcomanías con borde blanco sobre un color sólido, con estrella y sello",
        els = listOf(
            DPhoto(b(31, 43, 192, 232, K), 0, radius = 13f, rot = -7f, border = 9f, shadow = 10f),
            DPhoto(b(185, 159, 172, 142, K), 1, radius = 13f, rot = 8f, border = 9f, shadow = 10f),
            DPhoto(b(69, 309, 182, 132, K), 2, DShape.PILL, rot = -3f, border = 9f, shadow = 10f),
            DPath(b(250, 22, 104, 104, K), "M50 2 L61 34 L95 34 L68 55 L78 90 L50 69 L22 90 L32 55 L5 34 L39 34 Z", 100f, 100f, stroke = WHITE, width = 5f, fill = 0xFFFFD23F, rot = 12f),
            DText(b(250, 62, 104, 26, K), "star", "Estrella", "wow", DFont.HAND, 23f, BLACK, DAlign.CENTER, rot = 12f),
            DRect(b(262, 336, 96, 96, K), 0xFFFF7AB8, circle = true, rot = -12f),
            DRect(b(262, 336, 96, 96, K), WHITE, circle = true, stroke = 5f),
            DText(b(262, 362, 96, 44, K), "badge", "Sello", "{stagione}\n'{AA}", DFont.HAND, 24f, BLACK, DAlign.CENTER, line = 0.9f, rot = -12f),
            DText(b(24, 464, 336, 30, E), "sub", "Subtítulo", "tres días, cero planes", DFont.HAND, 30f, WHITE),
            DText(b(24, 494, 336, 56, E), "title", "Título", "Weekend!", DFont.GROTESK, 62f, WHITE, line = 0.9f, track = -0.03f),
        ),
    )

    // 9 ------------------------------------------------------------------------------------------------
    private fun mood(): Design {
        val ink = 0xFF372720L
        return Design(
            "x-mood", "Moodboard", DesignFamily.SCRAP, DOrient.PORTRAIT, 480f, 672f, WHITE,
            accents = listOf(ink, 0xFF111111, 0xFF1F2A44), motion = CollageMotion.SLIDE,
            hint = "Cuatro fotos y la paleta: los colores (con nombre y código) los toma de tus fotos",
            els = listOf(
                DText(b(28, 28, 330, 50), "title", "Título", "{Mese|Septiembre} lento", DFont.SERIF_IT, 50f, ACC),
                DText(b(352, 46, 100, 14), "tag", "Etiqueta", "Mood {mese_n|09}", DFont.MONO, 10f, ACC, DAlign.RIGHT, track = 0.2f, upper = true),
                DPhoto(b(28, 90, 207, 298), 0),
                DPhoto(b(245, 90, 207, 144), 1),
                DPhoto(b(245, 244, 207, 144), 2),
                DPhoto(b(28, 398, 424, 132, F), 3),
                DText(b(28, 546, 424, 14, E), "palL", "Encima de la paleta", "Paleta tomada de las fotos", DFont.MONO, 10f, ACC, track = 0.2f, upper = true),
                DPalette(b(28, 570, 424, 74, E), 5, ACC),
            ),
        )
    }

    // 10 -----------------------------------------------------------------------------------------------
    private fun duotone(): Design {
        val cols = listOf("Dónde" to "{luogo|Fuerteventura}", "Spot" to "El Cotillo", "Cuándo" to "{Mese} {ANNO}", "Ola" to "2,1 m")
        val els = arrayListOf<DEl>(
            DPhoto(b(56, 56, 1022, 1120, F), 0, filter = DFilter.DUOTONE),
            DText(b(40, 1010, 1060, 400, E), "title", "Título", "SURF", DFont.ANTON, 470f, ACC2, line = 0.8f, track = -0.013f, upper = true),
            DRect(b(56, 1531, 1022, 3, E), ACC2),
        )
        cols.forEachIndexed { i, (l, v) ->
            val x = 56 + i * 261.5f
            els += DText(b(x, 1552, 237, 20, E), "l$i", "Etichetta ${i + 1}", l, DFont.SANS, 16f, ACC2, track = 0.125f, upper = true)
            els += DText(b(x, 1580, 237, 70, E), "v$i", "Valore ${i + 1}", v, DFont.GROTESK_MED, 30f, ACC2, line = 1.1f, wrap = true)
        }
        return Design("x-duo", "Duotono", DesignFamily.POSTER, DOrient.PORTRAIT, 1134f, 1701f, ACC,
            accents = listOf(0xFFFF6A3D, 0xFFFFD23F, 0xFFF9A8D4, 0xFFA7F3D0), accents2 = listOf(0xFF10206B, 0xFF111111, 0xFF7C2D12, 0xFF14532D),
            els = els, motion = CollageMotion.WIPE, hint = "Una foto en dos colores, el título enorme y la información en columna, como un póster suizo")
    }

    // 11 -----------------------------------------------------------------------------------------------
    private fun pop() = Design(
        "x-pop", "Pop", DesignFamily.POSTER, DOrient.PORTRAIT, 794f, 1123f, ACC,
        accents = listOf(0xFFFFD60A, 0xFFFF8FAB, 0xFF7DD3FC, 0xFFBEF264), motion = CollageMotion.CASCADE,
        hint = "Fotos en blanco y negro dentro de formas, una sola a color, sobre un color vivo",
        els = listOf(
            DText(b(44, 40, 520, 260), "title", "Título", "fuori\nporta", DFont.ARCHIVO, 156f, BLACK, line = 0.82f, track = -0.045f),
            DPhoto(b(434, 60, 400, 400), 0, DShape.CIRCLE, filter = DFilter.BW),
            DPhoto(b(44, 400, 230, 460), 1, DShape.PILL, filter = DFilter.BW),
            DPhoto(b(310, 500, 440, 330), 2, DShape.ARCH, outline = 12f),
            DPhoto(b(310, 860, 210, 210), 3, filter = DFilter.BW),
            DPhoto(b(550, 860, 200, 200), 4, DShape.CIRCLE, filter = DFilter.BW),
            DRect(b(196, 800, 140, 140), BLACK, circle = true),
            DText(b(196, 846, 140, 48), "sticker", "Sello", "{ANNO}", DFont.ARCHIVO, 34f, ACC, DAlign.CENTER, rot = -14f),
            DText(b(44, 1007, 300, 68, E), "caption", "Pie de foto", "{foto} fotos\n1 lago\n0 Wi-Fi", DFont.MONO, 15f, BLACK, line = 1.5f, bottom = true),
        ),
    )

    // 12 -----------------------------------------------------------------------------------------------
    private fun glass() = Design(
        "x-glass", "Cristal", DesignFamily.POSTER, DOrient.PORTRAIT, 384f, 576f, 0xFF8B7D74,
        accents = listOf(WHITE, 0xFFFFE4C7, 0xFF111111), motion = CollageMotion.SLIDE,
        hint = "La foto desenfocada hace de fondo, encima una placa de cristal con la foto nítida",
        els = listOf(
            DPhoto(b(-60, -60, 504, 696, F), 0, filter = DFilter.BLUR),
            DRect(b(0, 0, 384, 576, F), 0x1F28140A),
            DRect(b(28, 34, 328, 508, F), 0x29FFFFFF, radius = 30f, shadow = 20f),
            DRect(b(28, 34, 328, 508, F), 0x73FFFFFF, radius = 30f, stroke = 1f),
            DPhoto(b(42, 48, 300, 340, F), 0, radius = 20f),
            DText(b(50, 404, 284, 44, E), "title", "Título", "El último amanecer", DFont.SERIF, 44f, ACC, line = 0.95f),
            DText(b(50, 452, 140, 18, E), "place", "Lugar", "{luogo|Tarifa}", DFont.SANS, 13f, ACC, track = 0.08f),
            DText(b(194, 452, 140, 18, E), "time", "Hora", "{ora|06:52}", DFont.SANS, 13f, ACC, DAlign.RIGHT, track = 0.08f),
        ),
    )

    // 13 -----------------------------------------------------------------------------------------------
    private fun sport(): Design {
        val stats = listOf("Desnivel" to "1.820 m", "Tiempo" to "4:38", "Paso" to "10'09\"", "Altitud máx." to "2.412 m")
        val els = arrayListOf<DEl>(
            DPhoto(b(0, 0, 794, 700, F), 0, poly = pts(0f, 0f, 1f, 0f, 1f, 0.76f, 0f, 1f)),
            DRect(b(0, 0, 794, 280), 0, grad = listOf(0f to 0x73000000L, 1f to 0L)),
            DRect(b(0, 520, 794, 240, E), ACC, poly = pts(0f, 0.75f, 1f, 0f, 1f, 0.28f, 0f, 1f)),
            DText(b(40, 36, 520, 18), "kicker", "Agenda", "Trail · {data}", DFont.MONO_BOLD, 14f, WHITE, track = 0.21f, upper = true),
            DText(b(40, 62, 600, 250), "title", "Título", "Alta\nVia", DFont.ANTON, 150f, WHITE, line = 0.82f, track = -0.013f, upper = true),
            DText(b(36, 712, 400, 200, E), "km", "Número grande", "27,4", DFont.ANTON, 250f, BLACK, line = 0.8f, track = -0.024f, count = true),
            DText(b(446, 820, 110, 60, E), "unit", "Unidades", "KM", DFont.ANTON, 54f, ACC),
            DPhoto(b(564, 690, 190, 132, E), 1, radius = 14f),
            DPhoto(b(564, 834, 190, 132, E), 2, radius = 14f),
            DRect(b(40, 981, 714, 102, E), 0xFF111111, radius = 20f),
        )
        stats.forEachIndexed { i, (l, v) ->
            val x = 66 + i * 169.5f
            els += DText(b(x, 1001, 150, 14, E), "l$i", "Etichetta ${i + 1}", l, DFont.MONO, 11f, WHITE, track = 0.18f, upper = true)
            els += DText(b(x, 1019, 150, 44, E), "s$i", l, v, DFont.ANTON, 44f, if (i == 3) ACC else WHITE, count = true)
        }
        return Design("x-sport", "Sport", DesignFamily.THEMES, DOrient.PORTRAIT, 794f, 1123f, 0xFFF4F4F1,
            accents = listOf(0xFFFF5B14, 0xFF2F5BFF, 0xFFE11D48, 0xFFFFD60A), els = els, motion = CollageMotion.SLIDE,
            hint = "Trail, bici, running: corte oblicuo, el número gigante y la barra de datos (los números cuentan en el video)")
    }

    // 14 -----------------------------------------------------------------------------------------------
    private fun love(): Design {
        val ink = 0xFF3B2A2AL; val rose = 0xFFB0485BL
        return Design(
            "x-love", "Romántico", DesignFamily.THEMES, DOrient.PORTRAIT, 480f, 672f, ACC,
            accents = listOf(0xFFF8EFEC, WHITE, 0xFFEEF2EA, 0xFFF3EEE6), motion = CollageMotion.WIPE,
            hint = "Boda, aniversario, nosotros dos: fotos en arco, los nombres y la fecha en números romanos",
            els = listOf(
                DPhoto(b(95, 62, 290, 330, C), 0, DShape.ARCH),
                DText(b(300, 86, 170, 46, C), "hand", "Texto a mano", "para siempre", DFont.HAND, 40f, rose, rot = -8f),
                DText(b(20, 406, 440, 56, C), "names", "Nombres", "Julia & Marcos", DFont.SERIF, 52f, ink, DAlign.CENTER),
                DText(b(20, 474, 440, 16, C), "date", "Fecha", "{data_romana}", DFont.SANS, 12f, ink, DAlign.CENTER, track = 0.42f),
                DRect(b(220, 502, 40, 1, C), ink),
                DPhoto(b(82, 517, 96, 96, C), 1, DShape.CIRCLE),
                DPhoto(b(192, 517, 96, 96, C), 2, DShape.CIRCLE),
                DPhoto(b(302, 517, 96, 96, C), 3, DShape.CIRCLE),
            ),
        )
    }

    // 15 -----------------------------------------------------------------------------------------------
    private fun ticket(): Design {
        val ink = 0xFF0B2545L; val grey = 0xFF5B6B80L
        val fields = listOf(Triple("Fecha", "{giorno_breve}", 0), Triple("Abordaje", "{ora|06:40}", 1), Triple("Lugar", "12A", 2), Triple("Pasajeros", "2", 3))
        val els = arrayListOf<DEl>(
            DRect(b(22, 22, 532, 340, F, F), WHITE, radius = 20f, shadow = 12f),
            DPhoto(b(22, 22, 300, 340, F, F), 0, radius = 20f, corners = 1 or 8),
            DText(b(40, 296, 280, 52, E), "title", "Título", "{luogo|Santorini}", DFont.SERIF, 54f, WHITE, line = 0.9f, bottom = true, shadow = true),
            DPath(b(322, 36, 2, 312, F, E), "M1 0 L1 312", 2f, 312f, stroke = 0xFFB9C6D6, width = 2f, dash = listOf(6f, 6f)),
            DText(b(344, 40, 190, 14, S, E), "", "", "Pase de abordar", DFont.MONO, 10f, ink, track = 0.2f, upper = true),
            DText(b(344, 58, 190, 40, S, E), "route", "Tramo", "FCO → JTR", DFont.MONO_BOLD, 36f, ink, track = -0.026f),
            DText(b(344, 100, 190, 16, S, E), "cities", "Ciudad", "Roma — {luogo|Santorini}", DFont.MONO, 11f, ink),
            DBarcode(b(344, 310, 190, 34, E, E), ink),
        )
        fields.forEach { (l, v, i) ->
            val x = 344 + (i % 2) * 101; val y = 170 + (i / 2) * 46
            els += DText(b(x, y, 95, 12, S, E), "", "", l, DFont.MONO, 9f, grey, track = 0.11f, upper = true)
            els += DText(b(x, y + 13, 95, 20, S, E), "f$i", l, v, DFont.MONO_BOLD, 15f, ink)
        }
        return Design("x-ticket", "Pase de abordar", DesignFamily.THEMES, DOrient.LANDSCAPE, 576f, 384f, ACC,
            accents = listOf(0xFFD6E6F5, 0xFFFDE2C8, 0xFFE2F0D9, 0xFF0B2545), els = els, motion = CollageMotion.SLIDE,
            hint = "El viaje como un boleto de avión: el destino lo toma del lugar de la foto")
    }

    // 16 -----------------------------------------------------------------------------------------------
    private fun route(): Design {
        val ink = 0xFF18322AL
        val stops = listOf(
            listOf(98, 338, 370, 380, 0, "Roma", "2 días"), listOf(628, 528, 270, 580, 1, "Cinque Terre", "3 días"),
            listOf(158, 838, 430, 880, 0, "París", "4 días"), listOf(668, 1088, 300, 1140, 1, "Berlín", "2 días"),
        )
        val els = arrayListOf<DEl>(
            DText(b(64, 60, 800, 136), "title", "Título", "Interrail", DFont.GROTESK, 170f, ink, line = 0.8f, track = -0.047f, bottom = true),
            DText(b(859, 156, 200, 36), "year", "Año", "{ANNO}", DFont.MONO_BOLD, 32f, ink, DAlign.RIGHT),
            DPath(b(0, 0, 1123, 1587, F), "M215 455 C500 420 700 500 745 645 C790 800 400 780 275 955 C170 1100 600 1150 785 1205", 1123f, 1587f,
                stroke = ACC2, width = 7f, dash = listOf(1f, 20f), draw = true),
        )
        stops.forEachIndexed { i, st ->
            val px = st[0] as Int; val py = st[1] as Int; val lx = st[2] as Int; val ly = st[3] as Int; val right = st[4] == 1
            els += DPhoto(b(px, py, 234, 234, K), i, DShape.CIRCLE, border = 8f, shadow = 10f)
            val al = if (right) DAlign.RIGHT else DAlign.LEFT
            val w = if (right) 320 else 400
            els += DText(b(lx, ly - 8, w, 30, K), "", "", "0${i + 1}", DFont.MONO_BOLD, 26f, ACC2, al)
            els += DText(b(lx, ly + 26, w, 56, K), "c$i", "Tappa ${i + 1}", "{luogo$i|${st[5]}}", DFont.GROTESK, 62f, ink, al, line = 0.9f)
            els += DText(b(lx, ly + 90, w, 28, K), "d$i", "Días etapa ${i + 1}", st[6] as String, DFont.MONO, 24f, ink, al)
        }
        els += DRect(b(64, 1481, 995, 3, E), ink)
        els += DText(b(64, 1502, 330, 32, E), "f1", "Pie izquierdo", "{n_luoghi|4} ciudades", DFont.MONO, 26f, ink)
        els += DText(b(396, 1502, 330, 32, E), "f2", "Pie centro", "{giorni|11} días", DFont.MONO, 26f, ink, DAlign.CENTER)
        els += DText(b(729, 1502, 330, 32, E), "f3", "Pie derecho", "2.134 km en tren", DFont.MONO, 26f, ink, DAlign.RIGHT)
        return Design("x-route", "Itinerario", DesignFamily.THEMES, DOrient.PORTRAIT, 1123f, 1587f, ACC,
            accents = listOf(0xFFE6EEE3, WHITE, 0xFFF6E7D8, 0xFFDBE7F5), accents2 = listOf(0xFFE8452C, 0xFF1D4ED8, ink),
            els = els, motion = CollageMotion.CASCADE, hint = "Las etapas del viaje unidas por el recorrido punteado (que se dibuja en el video); los nombres los toma de las fotos")
    }

    // 17 -----------------------------------------------------------------------------------------------
    private fun street(): Design {
        val bg = 0xFFECECEAL; val strip = 0xFF141414L; val num = 0xFFFF9A3CL
        val els = arrayListOf<DEl>(
            DText(b(40, 40, 400, 64), "title", "Título", "Nocturno", DFont.MONO_BOLD, 58f, BLACK, track = -0.034f, upper = true),
            DText(b(440, 62, 314, 20), "roll", "Subtítulo", "Roll 03 · Ilford HP5", DFont.MONO, 13f, BLACK, DAlign.RIGHT, track = 0.15f, upper = true),
        )
        for ((row, y0, rot) in listOf(Triple(0, 140, -1f), Triple(1, 380, 0.6f))) {
            els += DRect(b(40, y0, 704, 216), strip, rot = rot)
            els += DPattern(b(40, y0, 704, 216), DPatternKind.SPROCKETS, bg, 24f, rot = rot)
            for (k in 0 until 3) {
                els += DPhoto(b(56 + k * 228, y0 + 30, 216, 156), row * 3 + k, rot = rot, filter = DFilter.BW)
                els += DText(b(60 + k * 228, y0 - 2, 40, 14), "", "", "${12 + row * 3 + k}", DFont.MONO_BOLD, 11f, num)
            }
        }
        els += DPath(b(270, 382, 280, 210), "M140 12 C220 6 272 50 268 108 C264 170 200 204 132 200 C60 196 10 160 12 104 C14 52 66 18 150 18", 280f, 210f, stroke = ACC, width = 6f, draw = true)
        els += DPhoto(b(112, 656, 428, 340), 4, rot = -2f, border = 16f, bottomPad = 30f, shadow = 16f, filter = DFilter.BW)
        els += DText(b(118, 1006, 428, 16), "caption", "Pie de foto", "{luogo4|Milán}, {ora|02:14} — fotograma 16", DFont.MONO, 13f, BLACK, rot = -2f)
        els += DText(b(590, 700, 200, 150), "note", "Nota", "¡esta!\nimprimir\n30×40", DFont.HAND, 46f, ACC, line = 0.95f, rot = -6f)
        return Design("x-street", "Street, hojas de contacto", DesignFamily.THEMES, DOrient.PORTRAIT, 794f, 1123f, bg,
            accents = listOf(0xFFFF2D2D, 0xFFFFD60A, 0xFF2F5BFF), els = els, motion = CollageMotion.CASCADE, grain = true,
            hint = "Ciudad de noche en blanco y negro: dos tiras de película, el fotograma elegido marcado con un círculo e impreso en grande")
    }

    // 18 -----------------------------------------------------------------------------------------------
    private fun food(): Design {
        val cream = 0xFFF6EFE2L
        val menu = listOf("Pakora de verduras" to "Entrada", "Biryani de pollo" to "Primero", "Dal makhani" to "Segundo", "Chai con especias" to "Fin")
        val els = arrayListOf<DEl>(
            DPhoto(b(366, -40, 480, 480), 0, DShape.CIRCLE),
            DText(b(40, 40, 330, 16), "kicker", "Agenda", "Cena en casa · {data}", DFont.MONO, 12f, ACC, track = 0.25f, upper = true),
            DText(b(40, 66, 360, 130), "title", "Título", "Sábado por la noche,\ncocina india", DFont.SERIF, 70f, cream, line = 0.92f, font2 = DFont.SERIF_IT, color2 = ACC),
        )
        menu.forEachIndexed { i, (dish, course) ->
            val y = 410 + i * 70
            els += DPhoto(b(40, y, 54, 54, C), 1 + i, DShape.CIRCLE)
            els += DText(b(108, y + 8, 214, 38, C), "m$i", "Piatto ${i + 1}", dish, DFont.SERIF, 30f, cream)
            els += DPath(b(328, y + 34, 76, 2, C), "M0 1 L76 1", 76f, 2f, stroke = 0x80F6EFE2, width = 2f, dash = listOf(2f, 6f))
            els += DText(b(380, y + 20, 120, 14, C), "c$i", "Portata ${i + 1}", course, DFont.MONO, 11f, cream, DAlign.RIGHT, track = 0.18f, upper = true)
        }
        els += DPhoto(b(546, 496, 170, 220, E), 5, DShape.ARCH)
        return Design("x-food", "Cocina", DesignFamily.THEMES, DOrient.SQUARE, 756f, 756f, ACC2,
            accents = listOf(0xFFF2A541, 0xFFFF7A59, 0xFFE9C46A), accents2 = listOf(0xFF12372A, 0xFF3B1F2B, 0xFF1F2A44, 0xFF2B2B2B),
            els = els, motion = CollageMotion.CASCADE, hint = "Una cena, una receta, un restaurante: el platillo grande y el menú con las fotos")
    }
}
