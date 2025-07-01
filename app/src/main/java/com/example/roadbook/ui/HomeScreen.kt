package com.example.roadbook.ui

import android.location.Location
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.roadbook.data.Note
import com.example.roadbook.widgets.RoadbookScreen
import com.example.roadbook.widgets.TrackMap
import com.example.roadbook.widgets.TopBanner
import com.example.roadbook.vm.RoadbookViewModel

@Composable
fun HomeScreen(vm: RoadbookViewModel) {
    val kmTotal    by vm.trackPoints.collectAsStateWithLifecycle()
    val heading    by vm.currentLocation.collectAsStateWithLifecycle()
    val chrono     by vm.currentLocation.collectAsStateWithLifecycle()  // reemplaza por tu flow de tiempo
    val notes      by vm.notes.collectAsStateWithLifecycle()
    val ready      by vm.readyToStart.collectAsStateWithLifecycle()
    val playing    by vm.isPlaying.collectAsStateWithLifecycle()
    val currentLoc by vm.currentLocation.collectAsStateWithLifecycle()

    var selectedTab by remember { mutableIntStateOf(0) }

    Scaffold { padding ->
        Column(Modifier.fillMaxSize().padding(padding)) {
            TopBanner(
                kmTotal    = kmTotal.size,     // ajusta a tu modelo
                heading    = heading?.latitude ?: 0.0,
                chrono     =  chrono?.longitude ?: 0.0
            )

            TabRow(selectedTabIndex = selectedTab) {
                Tab(selected = selectedTab == 0, onClick = { selectedTab = 0 }) { Text("Road-book") }
                Tab(selected = selectedTab == 1, onClick = { selectedTab = 1 }) { Text("Track") }
            }

            Spacer(Modifier.height(8.dp))

            when (selectedTab) {
                0 -> {
                    Row(
                        Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(
                            onClick = { vm.startRoadbook() },
                            enabled = ready && !playing
                        ) { Text("Play") }

                        Spacer(Modifier.width(16.dp))

                        Button(
                            onClick = { vm.stopRoadbook() },
                            enabled = playing
                        ) { Text("Stop") }
                    }
                    Spacer(Modifier.height(8.dp))
                    RoadbookScreen(notes = notes, modifier = Modifier.fillMaxSize())
                }
                1 -> {
                    TrackMap(
                        notes           = notes,
                        currentLocation = currentLoc,
                        modifier        = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}





