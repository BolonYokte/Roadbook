package com.example.roadbook.vm

import android.app.Application
import android.location.Location
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.github.ticofab.androidgpxparser.parser.GPXParser
import com.github.ticofab.androidgpxparser.parser.domain.GPX
import com.github.ticofab.androidgpxparser.parser.domain.TrackPoint
import com.example.roadbook.data.Direction
import com.example.roadbook.data.Note
import com.example.roadbook.data.Surface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.osmdroid.util.GeoPoint
import kotlin.math.*                     // por si más adelante calculas distancias

/**
 * ViewModel principal: carga un GPX de /assets, lo convierte a road-book,
 * expone estado para la UI y gestiona el Play/Stop.
 */
class RoadbookViewModel(app: Application) : AndroidViewModel(app) {

    /*────────  estados observables ────────*/
    private val _notes         = MutableStateFlow<List<Note>>(emptyList())
    private val _trackPts      = MutableStateFlow<List<GeoPoint>>(emptyList())
    private val _readyToStart  = MutableStateFlow(false)
    private val _isPlaying     = MutableStateFlow(false)
    private val _currentLoc    = MutableStateFlow<GeoPoint?>(null)

    val notes        : StateFlow<List<Note>>     get() = _notes
    val trackPoints  : StateFlow<List<GeoPoint>> get() = _trackPts
    val readyToStart : StateFlow<Boolean>        get() = _readyToStart
    val isPlaying    : StateFlow<Boolean>        get() = _isPlaying
    val currentLoc   : StateFlow<GeoPoint?>      get() = _currentLoc

    /*────────  GPX ────────*/
    private val gpxParser = GPXParser()
    private var startPt : GeoPoint? = null

    init {
        // 1) carga el archivo /assets/track.gpx en hilo IO
        viewModelScope.launch(Dispatchers.IO) {
            val ctx   = getApplication<Application>()
            val gpx   = ctx.assets.open("track.gpx").use { stream -> gpxParser.parse(stream) }
            val notes = gpxToRoadbook(gpx)

            _notes.value    = notes
            _trackPts.value = gpx.allTrackPoints()
            startPt         = _trackPts.value.firstOrNull()
            _readyToStart.value = _trackPts.value.isNotEmpty()
        }
    }

    /*────────  botones PLAY / STOP ────────*/
    fun startRoadbook() {
        if (!_readyToStart.value || _isPlaying.value) return
        _isPlaying.value = true
        // TODO: arrancar cronómetro y requestLocationUpdates()
    }

    fun stopRoadbook() {
        if (!_isPlaying.value) return
        _isPlaying.value = false
        // TODO: detener cronómetro y removeLocationUpdates()
    }

    /*────────  callback de localización (ejemplo) ────────*/
    private fun onLocationUpdate(loc: Location) {
        _currentLoc.value = GeoPoint(loc.latitude, loc.longitude)
        // si isPlaying == true ⇒ actualizar Km, rumbo, etc.
    }

    /*────────  helpers ────────*/

    /** Convierte un [GPX] a lista de [Note] muy básica. */
    private fun gpxToRoadbook(gpx: GPX): List<Note> {
        val result = mutableListOf<Note>()

        gpx.tracks.forEach { trk ->
            trk.trackSegments.forEach { seg ->
                seg.trackPoints.forEach { pt: TrackPoint ->
                    result += Note(
                        leg       = 0.0,               // ⇠ calcula distancia leg si quieres
                        total     = 0.0,               // ⇠ calcula total acumulado
                        surface   = Surface.UNKNOWN,
                        direction = Direction.STRAIGHT,
                        text      = pt.name ?: "",
                        lat       = pt.latitude,
                        lon       = pt.longitude
                    )
                }
            }
        }
        return result
    }

    /** Extrae todos los puntos del GPX en forma de lista de [GeoPoint]. */
    private fun GPX.allTrackPoints(): List<GeoPoint> =
        tracks.flatMap { it.trackSegments }
            .flatMap { it.trackPoints }
            .map { GeoPoint(it.latitude, it.longitude) }
}

