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
import androidx.compose.material3.*
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
import pl.filked.triptrop.QuizManager
import pl.filked.triptrop.models.TropFeature
import pl.filked.triptrop.ui.theme.OriginalSurfer
import pl.filked.triptrop.ui.theme.wheat
import pl.filked.triptrop.data.QuizQuestion
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OsmMapScreen(
    target: List<TropFeature> = emptyList(),
    onBackClick: () -> Unit
) {
    val context = LocalContext.current
    val questions = remember {
        QuizManager.loadQuestions(context)
    }

    var currentQuestion by remember { mutableStateOf<QuizQuestion?>(null) }
    var answerResult by remember { mutableStateOf<String?>(null) }
    var usedQuestionIds by remember { mutableStateOf(setOf<Int>()) }

    var selectedTarget by remember { mutableStateOf<TropFeature?>(null) }
    var distanceToTarget by remember { mutableStateOf<Double?>(null) }

    val isCloseEnough = selectedTarget != null &&
            distanceToTarget != null &&
            distanceToTarget!! <= 30.0

    LaunchedEffect(Unit) {
        Configuration.getInstance().userAgentValue = context.packageName
    }

    val locationPermissionRequest = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { }

    LaunchedEffect(Unit) {
        locationPermissionRequest.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    val currentIsCloseEnough by rememberUpdatedState(isCloseEnough)

    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(
            initialValue = SheetValue.Hidden,
            skipHiddenState = false,
            confirmValueChange = { newState ->
                !(newState == SheetValue.Hidden && currentIsCloseEnough)
            }
        )
    )

    LaunchedEffect(selectedTarget) {
        if (selectedTarget != null) {
            scaffoldState.bottomSheetState.partialExpand()
        } else {
            scaffoldState.bottomSheetState.hide()
        }
    }

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetPeekHeight = if (selectedTarget != null) 100.dp else 0.dp,
        sheetContainerColor = wheat,
        sheetContent = {
            val point = selectedTarget

            if (point != null) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = point.properties.Nazwa,
                        fontSize = 22.sp,
                        fontFamily = OriginalSurfer,
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(point.properties.Obrazy.firstOrNull() ?: "")
                            .addHeader("User-Agent", "TripTropApp/1.0")
                            .crossfade(true)
                            .build(),
                        contentDescription = point.properties.Nazwa,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .border(2.dp, Color.Black.copy(0.4f), RoundedCornerShape(12.dp)),
                        contentScale = ContentScale.Crop
                    )

                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = point.properties.Info,
                        fontSize = 16.sp,
                        color = Color.DarkGray
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            val availableQuestions = questions.filter {
                                it.id !in usedQuestionIds
                            }

                            if (availableQuestions.isNotEmpty()) {
                                val randomQuestion = availableQuestions.random()

                                currentQuestion = randomQuestion
                                usedQuestionIds = usedQuestionIds + randomQuestion.id
                                answerResult = null
                            } else {
                                answerResult = "Brak dostępnych zagadek dla tej trasy."
                            }
                        }
                    ) {
                        Text("Rozwiąż zagadkę")
                    }

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
            AndroidView(
                modifier = Modifier.fillMaxSize(),
                factory = { ctx ->
                    MapView(ctx).apply {
                        setTileSource(TileSourceFactory.MAPNIK)
                        setMultiTouchControls(true)
                        controller.setZoom(18.0)

                        if (target.isNotEmpty()) {
                            controller.setCenter(
                                GeoPoint(
                                    target.first().geometry.coordinates[1], // latitude
                                    target.first().geometry.coordinates[0]  // longitude
                                )
                            )
                        }

                        target.forEach { point ->
                            val geoPoint = GeoPoint(point.geometry.coordinates[1], point.geometry.coordinates[0])

                            val marker = Marker(this).apply {
                                position = geoPoint
                                title = point.properties.Nazwa
                                snippet = point.properties.Info
                                setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)

                                setOnMarkerClickListener { _, _ ->
                                    selectedTarget = point
                                    true
                                }
                            }

                            overlays.add(marker)
                        }

                        val locationProvider = GpsMyLocationProvider(ctx)

                        val myLocationOverlay =
                            object : MyLocationNewOverlay(locationProvider, this) {
                                override fun onLocationChanged(
                                    location: Location?,
                                    source: IMyLocationProvider?
                                ) {
                                    super.onLocationChanged(location, source)

                                    val point = selectedTarget

                                    if (location != null && point != null) {
                                        val userGeo =
                                            GeoPoint(location.latitude, location.longitude)
                                        val targetGeo = GeoPoint(point.geometry.coordinates[1], point.geometry.coordinates[0])
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

    if (currentQuestion != null) {
        val question = currentQuestion!!
        val letters = listOf("A", "B", "C", "D")

        AlertDialog(
            onDismissRequest = {
                currentQuestion = null
                answerResult = null
            },
            title = {
                Text("Zagadka")
            },
            text = {
                Column {
                    Text(question.question)

                    Spacer(modifier = Modifier.height(12.dp))

                    question.answers.forEachIndexed { index, answer ->
                        Button(
                            onClick = {
                                answerResult =
                                    if (index == question.correctAnswer) {
                                        "Poprawna odpowiedź!"
                                    } else {
                                        val correct = question.answers[question.correctAnswer]
                                        "Zła odpowiedź. Poprawna odpowiedź to: ${letters[question.correctAnswer]}. $correct"
                                    }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                        ) {
                            Text("${letters[index]}. $answer")
                        }
                    }

                    if (answerResult != null) {
                        Spacer(modifier = Modifier.height(12.dp))

                        Text(answerResult!!)
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        currentQuestion = null
                        answerResult = null
                    }
                ) {
                    Text("Zamknij")
                }
            }
        )
    }
}