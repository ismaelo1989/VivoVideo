package it.fabriziodavi.vivovideo.online

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.sync.withLock
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.contentOrNull
import java.io.File
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

/** Servizi di musica ed effetti sonori liberi. */
enum class LibrarySource(val label: String, val needsKey: Boolean) {
    OPENVERSE("Openverse", false), JAMENDO("Jamendo", true), FREESOUND("Freesound", true)
}

/** Licenza di un brano: cosa permette. [derivatives] = si può mettere sotto un video (le "ND" no). */
data class LicenseInfo(val code: String, val commercial: Boolean, val derivatives: Boolean, val attribution: Boolean) {
    companion object {
        /** Da un codice tipo "by-nc-sa" (Openverse) e la versione. */
        fun fromCode(code: String?, version: String? = null): LicenseInfo {
            val c = (code ?: "").lowercase().trim()
            return when {
                c == "cc0" || c == "zero" -> LicenseInfo("CC0", commercial = true, derivatives = true, attribution = false)
                c == "pdm" || c == "publicdomain" -> LicenseInfo("Dominio público", commercial = true, derivatives = true, attribution = false)
                c.contains("sampling") -> LicenseInfo("CC Sampling+", commercial = !c.startsWith("nc"), derivatives = true, attribution = true)
                c.startsWith("by") -> {
                    val v = version?.takeIf { it.isNotBlank() }?.let { " $it" } ?: ""
                    LicenseInfo("CC " + c.uppercase() + v, commercial = !c.contains("nc"), derivatives = !c.contains("nd"), attribution = true)
                }
                else -> LicenseInfo(if (c.isEmpty()) "Licencia desconocida" else c.uppercase(), commercial = false, derivatives = false, attribution = true)
            }
        }

        /** Da un indirizzo tipo creativecommons.org/licenses/by-nc/3.0/ o …/publicdomain/zero/1.0/, o da un nome (Freesound). */
        fun fromUrl(url: String?): LicenseInfo {
            val u = (url ?: "").lowercase()
            if (u.contains("publicdomain/zero") || u.contains("creative commons 0") || u == "cc0") return fromCode("cc0")
            if (u.contains("publicdomain/mark")) return fromCode("pdm")
            Regex("licenses/([a-z+-]+)/([0-9.]+)").find(u)?.let { return fromCode(it.groupValues[1], it.groupValues[2]) }
            return when {
                u.contains("noncommercial") || u.contains("non commercial") -> fromCode("by-nc")
                u.contains("attribution") -> fromCode("by")
                else -> fromCode(null)
            }
        }
    }
}

/** Un brano o un effetto trovato in rete. */
data class OnlineTrack(
    val source: LibrarySource,
    val id: String,
    val title: String,
    val artist: String,
    val durationMs: Long,
    val previewUrl: String,
    val downloadUrl: String,
    val license: LicenseInfo,
    val pageUrl: String,
) {
    /** Riga da citare nella descrizione del video. */
    val credit: String get() = buildString {
        append("“").append(title).append("”")
        if (artist.isNotBlank()) append(" di ").append(artist)
        append(" (").append(source.label).append(") – ").append(license.code)
        if (pageUrl.isNotBlank()) append(" – ").append(pageUrl)
    }
    val fileName: String get() = "${source.name.lowercase()}_${id.filter { it.isLetterOrDigit() || it == '-' }.take(60)}.mp3"
}

/** Parole pronte per cercare: etichetta italiana → parole per i servizi (in inglese, come i loro archivi). */
val MUSIC_MOODS = listOf(
    "Épica" to "epic", "Allegra" to "happy", "Relajante" to "relaxing", "Cinematográfica" to "cinematic",
    "Emocionante" to "emotional", "Electrónica" to "electronic", "Rock" to "rock", "Acústica" to "acoustic",
    "Piano" to "piano", "Verano" to "summer", "Viaje" to "travel", "Romántica" to "romantic",
    "Hip hop" to "hiphop", "Lo-fi" to "lofi", "Corporate" to "corporate", "Navidad" to "christmas",
)
val SFX_WORDS = listOf(
    "Whoosh" to "whoosh", "Transición" to "swoosh transition", "Aplausos" to "applause", "Risa" to "laugh",
    "Pasos" to "footsteps", "Viento" to "wind", "Olas del mar" to "ocean waves", "Lluvia" to "rain", "Trueno" to "thunder",
    "Pájaros" to "birds", "Multitud" to "crowd", "Clic" to "click", "Notificación" to "notification", "Campanilla" to "bell",
    "Disparo de foto" to "camera shutter", "Explosión" to "explosion", "Pop" to "pop", "Beat" to "heartbeat",
)

object Library {
    private val json = Json { ignoreUnknownKeys = true; isLenient = true }
    private const val UA = "VivoVideo/0.3 (Android; uso personal)"

    private fun enc(s: String) = URLEncoder.encode(s, "UTF-8")

    // ---------------- Indirizzi delle ricerche ----------------

    fun openverseUrl(query: String, effects: Boolean, commercialOnly: Boolean, page: Int = 1): String =
        "https://api.openverse.org/v1/audio/?q=${enc(query.ifBlank { if (effects) "whoosh" else "instrumental" })}" +
            "&category=${if (effects) "sound_effect" else "music"}" +
            "&license_type=${if (commercialOnly) "commercial,modification" else "modification"}" +
            "&page_size=20&page=$page"

    fun jamendoUrl(clientId: String, query: String, commercialOnly: Boolean, instrumental: Boolean, page: Int = 1): String =
        "https://api.jamendo.com/v3.0/tracks/?client_id=${enc(clientId)}&format=json&limit=30&offset=${(page - 1) * 30}" +
            "&audioformat=mp32&order=popularity_total&ccnd=false" +
            (if (commercialOnly) "&ccnc=false" else "") +
            (if (instrumental) "&vocalinstrumental=instrumental" else "") +
            (if (query.isBlank()) "" else "&search=${enc(query)}")

    fun freesoundUrl(key: String, query: String, effects: Boolean, page: Int = 1): String =
        "https://freesound.org/apiv2/search/?query=${enc(query)}&token=${enc(key)}" +
            "&fields=id,name,username,duration,license,previews,url&page_size=30&page=$page" +
            "&filter=${enc(if (effects) "duration:[0 TO 30]" else "duration:[20 TO 600]")}"

    // ---------------- Lettura delle risposte ----------------

    private fun JsonElement?.str(): String? = (this as? JsonPrimitive)?.contentOrNull
    private fun JsonElement?.num(): Double? = (this as? JsonPrimitive)?.contentOrNull?.toDoubleOrNull()
    private fun JsonObject.s(k: String) = this[k].str()

    /** Openverse: durata in millisecondi, file diretto in "url", licenza come codice + versione. */
    fun parseOpenverse(body: String): List<OnlineTrack> {
        val root = json.parseToJsonElement(body) as? JsonObject ?: return emptyList()
        val results = root["results"] as? JsonArray ?: return emptyList()
        return results.mapNotNull { e ->
            val o = e as? JsonObject ?: return@mapNotNull null
            val url = o.s("url") ?: return@mapNotNull null
            OnlineTrack(
                source = LibrarySource.OPENVERSE,
                id = o.s("id") ?: url.hashCode().toString(),
                title = o.s("title")?.takeIf { it.isNotBlank() } ?: "Sin título",
                artist = o.s("creator") ?: "",
                durationMs = o["duration"].num()?.toLong() ?: 0L,
                previewUrl = url,
                downloadUrl = url,
                license = LicenseInfo.fromCode(o.s("license"), o.s("license_version")),
                pageUrl = o.s("foreign_landing_url") ?: "",
            )
        }
    }

    /** Jamendo: durata in secondi, ascolto in "audio", scaricamento in "audiodownload" (se permesso). */
    fun parseJamendo(body: String): List<OnlineTrack> {
        val root = json.parseToJsonElement(body) as? JsonObject ?: return emptyList()
        val headers = root["headers"] as? JsonObject
        val status = headers?.s("status")
        if (status != null && status != "success") error(headers?.s("error_message")?.takeIf { it.isNotBlank() } ?: "Jamendo: solicitud rechazada")
        val results = root["results"] as? JsonArray ?: return emptyList()
        return results.mapNotNull { e ->
            val o = e as? JsonObject ?: return@mapNotNull null
            val stream = o.s("audio")?.takeIf { it.isNotBlank() } ?: return@mapNotNull null
            val allowed = o.s("audiodownload_allowed")?.toBooleanStrictOrNull() ?: true
            val dl = o.s("audiodownload")?.takeIf { allowed && it.isNotBlank() } ?: stream
            OnlineTrack(
                source = LibrarySource.JAMENDO,
                id = o.s("id") ?: return@mapNotNull null,
                title = o.s("name") ?: "Sin título",
                artist = o.s("artist_name") ?: "",
                durationMs = ((o["duration"].num() ?: 0.0) * 1000).toLong(),
                previewUrl = stream,
                downloadUrl = dl,
                license = LicenseInfo.fromUrl(o.s("license_ccurl")),
                pageUrl = o.s("shareurl") ?: o.s("shorturl") ?: "",
            )
        }
    }

    /** Freesound: durata in secondi, anteprima MP3 di qualità (basta la chiave), licenza come indirizzo o nome. */
    fun parseFreesound(body: String): List<OnlineTrack> {
        val root = json.parseToJsonElement(body) as? JsonObject ?: return emptyList()
        root.s("detail")?.let { if (root["results"] == null) error(it) }
        val results = root["results"] as? JsonArray ?: return emptyList()
        return results.mapNotNull { e ->
            val o = e as? JsonObject ?: return@mapNotNull null
            val pv = o["previews"] as? JsonObject
            val mp3 = pv?.s("preview-hq-mp3") ?: pv?.s("preview-lq-mp3") ?: return@mapNotNull null
            OnlineTrack(
                source = LibrarySource.FREESOUND,
                id = o.s("id") ?: return@mapNotNull null,
                title = o.s("name")?.substringBeforeLast('.')?.takeIf { it.isNotBlank() } ?: "Efecto",
                artist = o.s("username") ?: "",
                durationMs = ((o["duration"].num() ?: 0.0) * 1000).toLong(),
                previewUrl = mp3,
                downloadUrl = mp3,
                license = LicenseInfo.fromUrl(o.s("license")),
                pageUrl = o.s("url") ?: "",
            )
        }
    }

    /** Toglie quello che non si può usare: le licenze "ND" sempre, le "NC" se serve l'uso commerciale. */
    fun usable(list: List<OnlineTrack>, commercialOnly: Boolean): List<OnlineTrack> =
        list.filter { it.license.derivatives && (!commercialOnly || it.license.commercial) }

    // ---------------- Rete ----------------

    /** Richiesta con un secondo tentativo se il servizio è lento (Openverse a volte lo è). */
    private fun get(url: String): String = try { getOnce(url) } catch (e: java.net.SocketTimeoutException) { getOnce(url) }

    private fun getOnce(url: String): String {
        val c = URL(url).openConnection() as HttpURLConnection
        c.connectTimeout = 15_000; c.readTimeout = 30_000
        c.setRequestProperty("User-Agent", UA)
        c.setRequestProperty("Accept", "application/json")
        try {
            val code = c.responseCode
            if (code == 401 || code == 403) error("Código del servicio no válido o no autorizado ($code).")
            if (code == 429) error("Demasiadas búsquedas en poco tiempo: vuelve a intentar en un minuto.")
            if (code !in 200..299) error("El servicio respondió con un error ($code).")
            return c.inputStream.bufferedReader().use { it.readText() }
        } finally { c.disconnect() }
    }

    /** Una richiesta alla volta: quelle superate (annullate) mentre aspettano il turno non partono neppure. */
    private val turn = kotlinx.coroutines.sync.Mutex()

    suspend fun search(
        source: LibrarySource, query: String, effects: Boolean, keys: LibraryKeys,
        commercialOnly: Boolean, instrumental: Boolean, page: Int = 1,
    ): List<OnlineTrack> = turn.withLock { searchNow(source, query, effects, keys, commercialOnly, instrumental, page) }

    private suspend fun searchNow(
        source: LibrarySource, query: String, effects: Boolean, keys: LibraryKeys,
        commercialOnly: Boolean, instrumental: Boolean, page: Int,
    ): List<OnlineTrack> = withContext(Dispatchers.IO) {
        val list = when (source) {
            LibrarySource.OPENVERSE -> parseOpenverse(get(openverseUrl(query, effects, commercialOnly, page))).ifEmpty {
                // Molti effetti non hanno la categoria "sound_effect": si riprova senza, con "sound effect" nella ricerca.
                if (effects) parseOpenverse(get(openverseUrl(query, effects, commercialOnly, page).replace("&category=sound_effect", "").replace("?q=", "?q=sound+effect+"))) else emptyList()
            }
            LibrarySource.JAMENDO -> parseJamendo(get(jamendoUrl(keys.jamendo ?: error("Falta el código de Jamendo."), query, commercialOnly, instrumental, page)))
            LibrarySource.FREESOUND -> parseFreesound(get(freesoundUrl(keys.freesound ?: error("Falta la clave de Freesound."), query.ifBlank { if (effects) "whoosh" else "ambient music" }, effects, page)))
        }
        usable(list, commercialOnly)
    }

    /** Scarica il brano nella cartella dell'app (una volta sola). */
    suspend fun download(ctx: Context, t: OnlineTrack): File = withContext(Dispatchers.IO) {
        val dir = File(ctx.filesDir, "music/online").apply { mkdirs() }
        val f = File(dir, t.fileName)
        if (f.exists() && f.length() > 1024) return@withContext f
        val tmp = File(dir, t.fileName + ".part")
        var url = t.downloadUrl
        repeat(5) {
            val c = URL(url).openConnection() as HttpURLConnection
            c.instanceFollowRedirects = true
            c.connectTimeout = 15_000; c.readTimeout = 30_000
            c.setRequestProperty("User-Agent", UA)
            try {
                val code = c.responseCode
                if (code in 300..399) { url = c.getHeaderField("Location") ?: error("Falta la dirección del archivo"); return@repeat }
                if (code !in 200..299) error("No se pudo descargar ($code).")
                c.inputStream.use { input -> tmp.outputStream().use { input.copyTo(it) } }
                if (tmp.length() < 1024) error("El archivo descargado está vacío.")
                tmp.renameTo(f)
                return@withContext f
            } finally { c.disconnect() }
        }
        error("Demasiadas redirecciones en la descarga.")
    }
}

/** Codici dei servizi, salvati solo sul telefono. */
data class LibraryKeys(val jamendo: String?, val freesound: String?) {
    fun has(s: LibrarySource) = when (s) {
        LibrarySource.OPENVERSE -> true
        LibrarySource.JAMENDO -> !jamendo.isNullOrBlank()
        LibrarySource.FREESOUND -> !freesound.isNullOrBlank()
    }

    companion object {
        private const val PREFS = "servizi_musica"
        fun load(ctx: Context): LibraryKeys {
            val p = ctx.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            return LibraryKeys(p.getString("jamendo", null)?.trim()?.ifBlank { null }, p.getString("freesound", null)?.trim()?.ifBlank { null })
        }
        fun save(ctx: Context, k: LibraryKeys) {
            ctx.getSharedPreferences(PREFS, Context.MODE_PRIVATE).edit()
                .putString("jamendo", k.jamendo?.trim()).putString("freesound", k.freesound?.trim()).apply()
        }
        fun commercialOnly(ctx: Context) = ctx.getSharedPreferences(PREFS, Context.MODE_PRIVATE).getBoolean("commerciale", false)
        fun setCommercialOnly(ctx: Context, v: Boolean) = ctx.getSharedPreferences(PREFS, Context.MODE_PRIVATE).edit().putBoolean("commerciale", v).apply()
    }
}

/** Messaggio comprensibile per un errore di rete. */
fun friendlyError(e: Throwable, source: LibrarySource): String = when (e) {
    is java.net.UnknownHostException -> "Sin conexión a internet."
    is java.net.SocketTimeoutException -> "${source.label} no responde (a veces está lento o sobrecargado). Vuelve a intentar, prueba una búsqueda más precisa u otro servicio."
    is java.io.IOException -> "No se pudo conectar con ${source.label}: vuelve a intentar en un momento."
    else -> e.message ?: e.javaClass.simpleName
}
