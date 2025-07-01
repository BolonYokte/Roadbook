package com.example.roadbook.widgets

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.Polyline
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun TrackMap(
    notes: List<Note>,
    currentLocation: GeoPoint?,
    modifier: Modifier = Modifier
) {
    // configuración de osmdroid
    val ctx = LocalContext.current.applicationContext
    Configuration.getInstance().load(ctx, ctx.getSharedPreferences("osmdroid", 0))

    AndroidView(
        factory = { ctx ->
            MapView(ctx).apply {
                setTileSource(TileSourceFactory.MAPNIK)
                controller.setZoom(16.0)
            }
        },
        modifier = modifier,
        update = { map ->
            map.overlays.clear()

            // dibujar track
            val line = Polyline().apply {
                setPoints(notes.map { GeoPoint(it.lat, it.lon) })
            }
            map.overlays.add(line)

            // marcador posición actual
            currentLocation?.let { loc ->
                Marker(map).apply {
                    position = loc
                    map.overlays.add(this)
                }
                map.controller.animateTo(loc)
            }

            map.invalidate()
        }
    )
}

