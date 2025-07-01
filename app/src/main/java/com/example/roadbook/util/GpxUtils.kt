package com.example.roadbook.util

import io.ticofab.androidgpxparser.parser.domain.TrackPoint
import kotlin.math.*

private const val R = 6_371_000.0   // radio Tierra (m)

/* Distancia Haversine entre dos puntos (m) */
fun distance(p1: TrackPoint, p2: TrackPoint): Double {
    val dLat = Math.toRadians(p2.latitude - p1.latitude)
    val dLon = Math.toRadians(p2.longitude - p1.longitude)
    val a = sin(dLat / 2).pow(2.0) +
            cos(Math.toRadians(p1.latitude)) *
            cos(Math.toRadians(p2.latitude)) *
            sin(dLon / 2).pow(2.0)
    return 2 * R * asin(sqrt(a))
}

/* Rumbo inicial p1→p2 (0-360°) */
fun bearing(p1: TrackPoint, p2: TrackPoint): Double {
    val φ1 = Math.toRadians(p1.latitude)
    val φ2 = Math.toRadians(p2.latitude)
    val λ  = Math.toRadians(p2.longitude - p1.longitude)
    val y  = sin(λ) * cos(φ2)
    val x  = cos(φ1) * sin(φ2) - sin(φ1) * cos(φ2) * cos(λ)
    return (Math.toDegrees(atan2(y, x)) + 360) % 360
}

/* Diferencia mínima entre dos rumbos (–180..+180) */
fun bearingDiff(a: Double, b: Double): Double =
    ((b - a + 540) % 360) - 180
