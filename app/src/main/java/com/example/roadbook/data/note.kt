package com.example.roadbook.data

/** Dirección de la maniobra (flecha) */
enum class Direction { LEFT, RIGHT, STRAIGHT }

/** Tipo de firme (pavimento, pista…)  */
enum class Surface { PAVED, OFFROAD, UNKNOWN }

/** Una “viñeta” del road-book */
data class Note(
    val km        : Double,      // kilometraje acumulado
    val leg       : Double,      // tramo parcial
    val text      : String,
    val surface   : Surface,
    val direction : Direction,

    /*  NUEVO  ──────────────────────────────────────────
       Coordenadas reales para poder pintar el track en
       el mapa de la pestaña “TrackMap”.                 */
    val lat : Double,
    val lon : Double
)


