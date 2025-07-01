package com.example.roadbook.domain

import com.example.roadbook.data.Direction
import com.example.roadbook.data.Note
import com.example.roadbook.data.Surface
import io.ticofab.androidgpxparser.parser.GPXParser
import io.ticofab.androidgpxparser.parser.domain.Gpx
import java.io.InputStream

/**
 * Convierte un GPX (InputStream) en la lista de notas (road-book).
 */
object RoadbookGenerator {

    fun from(gpxStream: InputStream): List<Note> {
        // Creamos el parser
        val parser = GPXParser()
        // Parseamos el GPX
        val gpx: Gpx? = parser.parse(gpxStream)
        val notes = mutableListOf<Note>()

        // Recorremos todas las pistas, segmentos y puntos
        gpx?.tracks?.forEach { track ->
            track.trackSegments.forEach { segment ->
                segment.trackPoints.forEach { point ->
                    notes.add(
                        Note(
                            km = 0.0,                 // se calculará más adelante
                            leg = 0.0,                // se calculará en ViewModel
                            text = "",                // placeholder
                            surface = Surface.UNKNOWN,
                            direction = Direction.STRAIGHT,
                            lat = point.latitude,
                            lon = point.longitude
                        )
                    )
                }
            }
        }

        return notes
    }
}












