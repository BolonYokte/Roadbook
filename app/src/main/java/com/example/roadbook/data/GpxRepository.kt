package com.example.roadbook.data

import android.content.Context
import io.ticofab.androidgpxparser.parser.GPXParser
import io.ticofab.androidgpxparser.parser.domain.Gpx

object GpxRepository {

    private val parser = GPXParser()

    /** Lee un GPX dentro de assets/  */
    fun load(context: Context, name: String = "track.gpx"): Gpx? =
        context.assets.open(name).use { input -> parser.parse(input) }
}
