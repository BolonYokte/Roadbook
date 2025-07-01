package com.example.roadbook.data

/**
 * Conjunto de viñetas de prueba para el road-book.
 *
 * – km       → kilometraje acumulado
 * – leg      → distancia parcial
 * – text     → descripción que se mostrará
 * – surface  → tipo de firme
 * – direction→ flecha de dirección
 * – lat/lon  → coordenadas para el mapa (placeholder)
 */
object DummyData {

    val value: List<Note> = listOf(
        Note(
            km = 0.00,
            leg = 0.00,
            text = "Straight out of staging area",
            surface = Surface.PAVED,
            direction = Direction.STRAIGHT,
            lat = 37.0000,          // ← cualquier valor
            lon = -3.0000
        ),
        Note(
            km = 0.18,
            leg = 0.18,
            text = "Stay left on main road",
            surface = Surface.PAVED,
            direction = Direction.LEFT,
            lat = 37.0005,
            lon = -3.0005
        ),
        Note(
            km = 1.02,
            leg = 0.84,
            text = "Stay left",
            surface = Surface.OFFROAD,
            direction = Direction.LEFT,
            lat = 37.0050,
            lon = -3.0010
        ),
        Note(
            km = 3.02,
            leg = 2.00,
            text = "Stay right",
            surface = Surface.OFFROAD,
            direction = Direction.RIGHT,
            lat = 37.0150,
            lon = -3.0020
        ),
        Note(
            km = 4.17,
            leg = 1.15,
            text = "Cross track",
            surface = Surface.UNKNOWN,
            direction = Direction.STRAIGHT,
            lat = 37.0200,
            lon = -3.0030
        )
        // ─ Añade más notas siguiendo el mismo patrón ─
    )
}



