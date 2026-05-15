package pl.filked.triptrop.presentation

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay
import pl.filked.triptrop.ui.theme.OriginalSurfer
import pl.filked.triptrop.ui.theme.wheat

@Composable
fun OsmMapScreen(onBackClick: () -> Unit) {
    val context = LocalContext.current

    //Konfiguracja osmdroid
    LaunchedEffect(Unit) {
        Configuration.getInstance().userAgentValue = context.packageName
    }

    //Obsługa uprawnień do lokalizacji
    val locationPermissionRequest = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
    }

    LaunchedEffect(Unit) {
        locationPermissionRequest.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    Box(modifier = Modifier.fillMaxSize()) {

        //Mapa
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { ctx ->
                MapView(ctx).apply {
                    setTileSource(TileSourceFactory.MAPNIK)
                    setMultiTouchControls(true)

                    // Ustawienia kamery
                    controller.setZoom(15.0)

                    // Nakładka lokalizacji użytkownika
                    val locationProvider = GpsMyLocationProvider(ctx)
                    val myLocationOverlay = MyLocationNewOverlay(locationProvider, this)
                    myLocationOverlay.enableMyLocation()
                    myLocationOverlay.enableFollowLocation()

                    overlays.add(myLocationOverlay)
                }
            }
        )

        // Przycisk powrotny
        Box(
            modifier = Modifier
                .padding(top = 40.dp, start = 20.dp)
                .width(100.dp)
                .height(45.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(wheat)
                .border(2.dp, Color.Black, RoundedCornerShape(12.dp))
                .clickable { onBackClick() }
                .align(Alignment.TopStart),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Wróć",
                fontSize = 18.sp,
                fontFamily = OriginalSurfer,
                color = Color.Black
            )
        }
    }
}