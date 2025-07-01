package com.example.roadbook.ui.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.roadbook.R
import com.example.roadbook.data.Direction        //  ← paquetes “data”
import com.example.roadbook.data.Note

/** Lista principal del road-book */
@Composable
fun RoadbookScreen(
    notes   : List<Note>,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(notes) { note ->
            NoteRow(note)
            Divider(thickness = 0.8.dp)
        }
    }
}

/* ---------- Fila individual ------------------------------------- */
@Composable
private fun NoteRow(note: Note) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        /* Kilómetros */
        Column(Modifier.width(72.dp)) {
            Text(String.format("%.2f km", note.km), style = MaterialTheme.typography.titleMedium)
            Text(String.format("%.2f",     note.leg), style = MaterialTheme.typography.labelSmall)
        }

        /* Flecha de dirección */
        Image(
            painter  = painterResource(id = iconFor(note.direction)),
            contentDescription = note.direction.name,
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .size(28.dp)
        )

        /* Texto descriptivo */
        Text(text = note.text, style = MaterialTheme.typography.bodyLarge)
    }
}

/* ---------- Helper flecha --------------------------------------- */
private fun iconFor(dir: Direction): Int = when (dir) {
    Direction.LEFT     -> R.drawable.ic_arrow_left
    Direction.RIGHT    -> R.drawable.ic_arrow_right
    Direction.STRAIGHT -> R.drawable.ic_arrow_up
}





