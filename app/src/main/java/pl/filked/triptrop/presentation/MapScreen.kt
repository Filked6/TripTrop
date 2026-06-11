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
import androidx.core.content.ContextCompat.getDrawable
import pl.filked.triptrop.R
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import pl.filked.triptrop.GameConfig

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OsmMapScreen(
    target: List<TropFeature> = emptyList(),
    totalCoins: Int,
    onCoinsChange: (Int) -> Unit,
    onBackClick: () -> Unit
) {
    val context = LocalContext.current
    val questions = remember {
        QuizManager.loadQuestions(context)
    }

    var currentQuestion by remember { mutableStateOf<QuizQuestion?>(null) }
    var answerResult by remember { mutableStateOf<String?>(null) }
    var usedQuestionIds by remember { mutableStateOf(setOf<Int>()) }

    var showAdventureFinishedDialog by remember { mutableStateOf(false) }
    var adventureCoins by remember { mutableStateOf(0) }

    val rewardPerQuestion = GameConfig.COINS_PER_CORRECT_ANSWER
    val maxAdventureCoins = target.size * rewardPerQuestion

    // Zmienna przechowująca nazwy punktów, w których rozwiązano zagadkę
    var solvedPoints by remember { mutableStateOf(setOf<Pair<String, Boolean>>()) }
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

                    val isPointSolved = solvedPoints.find { it.first == point.properties.Nazwa } != null

                    if (isPointSolved) {
                        Text(
                            text = "Zagadka dla tego miejsca została już rozwiązana.",
                            color = Color.Gray,
                            fontSize = 14.sp,
                            fontFamily = OriginalSurfer
                        )
                    } else {
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
                            },
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        ) {
                            Text("Rozwiąż zagadkę")
                        }
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
                },
                update = { mapView ->
                    mapView.overlays.removeAll { it is Marker }

                    target.forEach { point ->
                        val geoPoint = GeoPoint(point.geometry.coordinates[1], point.geometry.coordinates[0])

                        val marker = Marker(mapView).apply {
                            position = geoPoint
                            title = point.properties.Nazwa
                            snippet = point.properties.Info
                            setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)

                            val pairInSolved = solvedPoints.find { it.first == point.properties.Nazwa }
                            icon = if (pairInSolved == null) {
                                getDrawable(context, R.drawable.szary_tropek)
                            } else if (pairInSolved.second) {
                                getDrawable(context, R.drawable.zielony_tropek)
                            } else {
                                getDrawable(context, R.drawable.czerwony_tropek)
                            }

                            setOnMarkerClickListener { _, _ ->
                                selectedTarget = point
                                true
                            }
                        }

                        mapView.overlays.add(marker)
                    }
                    mapView.invalidate()
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
            Box(
                modifier = Modifier
                    .padding(top = 40.dp, end = 20.dp)
                    .width(110.dp)
                    .height(40.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(wheat)
                    .border(1.dp, Color.Black.copy(0.4f), RoundedCornerShape(8.dp))
                    .align(Alignment.TopEnd),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = totalCoins.toString(),
                        fontSize = 24.sp,
                        fontFamily = OriginalSurfer,
                        modifier = Modifier.padding(end = 2.dp)
                    )

                    Image(
                        painter = painterResource(R.drawable.trip_trop_coin),
                        contentDescription = "TripTrop Coin",
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
        }
    }

    if (currentQuestion != null) {
        val question = currentQuestion!!
        val letters = listOf("A", "B", "C", "D")

        // Funkcja zamykająca okno zagadki i sprawdzająca zakończenie przygody
        val closeQuestionDialog = {
            currentQuestion = null
            answerResult = null

            // Sprawdzenie, czy wszystko jest rozwiązane (wywoływane po kliknięciu 'Zamknij')
            if (solvedPoints.size == target.size && target.isNotEmpty()) {
                showAdventureFinishedDialog = true
            }
        }

        AlertDialog(
            onDismissRequest = closeQuestionDialog,
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
                                        onCoinsChange(totalCoins + rewardPerQuestion)
                                        adventureCoins += rewardPerQuestion

                                        "Poprawna odpowiedź!\nOtrzymujesz +15 monet."
                                    } else {
                                        val correct = question.answers[question.correctAnswer]
                                        "Zła odpowiedź. Poprawna odpowiedź to: ${letters[question.correctAnswer]}. $correct"
                                    }

                                // Zapisanie punktu do rozwiązanych po kliknięciu odpowiedzi (bez wyświetlania dialogu końcowego)
                                selectedTarget?.let { selectedPoint ->
                                    val isCorrect = index == question.correctAnswer
                                    solvedPoints = solvedPoints + (selectedPoint.properties.Nazwa to isCorrect)
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
                    onClick = closeQuestionDialog
                ) {
                    Text("Zamknij")
                }
            }
        )
    }

    if (showAdventureFinishedDialog) {
        AlertDialog(
            onDismissRequest = {
                showAdventureFinishedDialog = false
            },
            title = {
                Text("Koniec przygody!")
            },
            text = {
                Text(
                    "Gratulacje!\n\nZdobyto $adventureCoins z $maxAdventureCoins możliwych monet."
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        showAdventureFinishedDialog = false
                        onBackClick()
                    }
                ) {
                    Text("OK")
                }
            }
        )
    }
}