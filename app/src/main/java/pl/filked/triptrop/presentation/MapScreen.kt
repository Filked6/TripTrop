package pl.filked.triptrop.presentation

import android.Manifest
import android.location.Location
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import coil.compose.AsyncImage
import coil.request.ImageRequest
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.IMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay
import pl.filked.triptrop.ui.theme.OriginalSurfer
import pl.filked.triptrop.ui.theme.wheat

data class MapTarget(
    val latitude: Double,
    val longitude: Double,
    val title: String,
    val description: String,
    val imageUrl: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OsmMapScreen(
    target: MapTarget? = null,
    onBackClick: () -> Unit
) {
    val context = LocalContext.current

    var distanceToTarget by remember { mutableStateOf<Double?>(null) }

    val isCloseEnough = target != null && distanceToTarget != null && distanceToTarget!! <= 30.0

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

    // 1. Zapisujemy aktualny stan "czy jesteśmy blisko", aby funkcja niżej zawsze miała świeże dane
    val currentIsCloseEnough by rememberUpdatedState(isCloseEnough)

    // 2. Tworzymy stan z blokadą ukrywania
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(
            initialValue = SheetValue.Hidden,
            skipHiddenState = false,
            confirmValueChange = { newState ->
                // Jeśli próbujemy całkowicie ukryć pasek (Hidden), ALE nadal jesteśmy przy punkcie:
                if (newState == SheetValue.Hidden && currentIsCloseEnough) {
                    false // ZABLOKUJ zmianę stanu (pasek "odbije" się z powrotem do 100.dp)
                } else {
                    true  // POZWÓL na zmianę (np. do pełnego rozsunięcia lub ukrycia, gdy odejdziemy)
                }
            }
        )
    )

    LaunchedEffect(isCloseEnough) {
        if (isCloseEnough) {
            scaffoldState.bottomSheetState.partialExpand()
        } else {
            scaffoldState.bottomSheetState.hide()
        }
    }

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetPeekHeight = if (isCloseEnough) 100.dp else 0.dp,
        sheetContainerColor = wheat,
        sheetContent = {
            if (target != null) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = target.title,
                        fontSize = 22.sp,
                        fontFamily = OriginalSurfer,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(target.imageUrl)
                            .addHeader("User-Agent", "TripTropApp/1.0")
                            .crossfade(true)
                            .build(),
                        contentDescription = target.title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .border(2.dp, Color.Black.copy(0.4f), RoundedCornerShape(12.dp)),
                        contentScale = ContentScale.Crop
                    )

                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = target.description,
                        fontSize = 16.sp,
                        color = Color.DarkGray
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                }
            } else {
                Box(modifier = Modifier.height(1.dp))
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            //Mapa
            AndroidView(
                modifier = Modifier.fillMaxSize(),
                factory = { ctx ->
                    MapView(ctx).apply {
                        setTileSource(TileSourceFactory.MAPNIK)
                        setMultiTouchControls(true)

                        // Ustawienia kamery
                        controller.setZoom(18.0)

                        if (target != null) {
                            val targetGeoPoint = GeoPoint(target.latitude, target.longitude)
                            val destinationMarker = Marker(this).apply {
                                position = targetGeoPoint
                                title = target.title
                                setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                            }
                            overlays.add(destinationMarker)
                        }

                        // Nakładka lokalizacji użytkownika
                        val locationProvider = GpsMyLocationProvider(ctx)
                        val myLocationOverlay = object : MyLocationNewOverlay(locationProvider, this) {
                            override fun onLocationChanged(location: Location?, source: IMyLocationProvider?) {
                                super.onLocationChanged(location, source)

                                if (location != null && target != null) {
                                    val userGeo = GeoPoint(location.latitude, location.longitude)
                                    val targetGeo = GeoPoint(target.latitude, target.longitude)
                                    distanceToTarget = userGeo.distanceToAsDouble(targetGeo)
                                }
                            }
                        }
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
}