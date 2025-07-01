package com.example.roadbook

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.compose.setContent
import com.example.roadbook.ui.HomeScreen
import com.example.roadbook.ui.theme.RoadbookTheme
import com.example.roadbook.vm.RoadbookViewModel

class MainActivity : ComponentActivity() {
    private val vm: RoadbookViewModel by lazy { RoadbookViewModel(application) }

    private val permissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { perms ->
            if (perms[Manifest.permission.ACCESS_FINE_LOCATION] == true &&
                perms[Manifest.permission.ACCESS_COARSE_LOCATION] == true) {
                vm.startLocationUpdates()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Pedimos permisos antes de mostrar UI
        permissionLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )

        setContent {
            RoadbookTheme {
                HomeScreen(vm = vm)
            }
        }
    }
}








