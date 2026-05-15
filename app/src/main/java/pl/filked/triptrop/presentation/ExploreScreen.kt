package pl.filked.triptrop.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pl.filked.triptrop.ExploreMocks
import pl.filked.triptrop.R
import pl.filked.triptrop.data.ClosestJourneyData
import pl.filked.triptrop.data.JourneyData
import pl.filked.triptrop.ui.theme.PirataOne
import pl.filked.triptrop.ui.theme.TripTropTheme
import pl.filked.triptrop.ui.theme.*

@Composable
fun JourneyBox(journey: JourneyData){
    Box(
        modifier = Modifier
            .width(250.dp)
            .height(180.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(coffeeBean)
            .border(2.dp, Color.Black, RoundedCornerShape(12.dp))
            .clickable(
                onClick = {/*TODO*/}
            )
    ){
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Box(
                modifier = Modifier
                    .height(120.dp)
                    .width(240.dp)
                    .padding(5.dp)
            ) {
                Image(
                    painter = painterResource(id = journey.journeyPhoto),
                    contentDescription = "Gnom Wrocławski",
                    modifier = Modifier
                        .matchParentSize()
                        .clip(RoundedCornerShape(12.dp))
                        .border(2.dp, Color.Black, RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )

                Box(
                    modifier = Modifier
                        .padding(top = 5.dp, end = 5.dp)
                        .width(50.dp)
                        .height(20.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(wheat)
                        .border(1.dp, Color.Black, RoundedCornerShape(12.dp))
                        .align(Alignment.TopEnd),
                    contentAlignment = Alignment.Center
                ){
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        Text(
                            text = "50",
                            fontSize = 12.sp,
                            fontFamily = OriginalSurfer,
                            style = TextStyle(
                                platformStyle = PlatformTextStyle(includeFontPadding = false),
                                lineHeightStyle = LineHeightStyle(
                                    alignment = LineHeightStyle.Alignment.Center,
                                    trim = LineHeightStyle.Trim.Both
                                )
                            )
                        )

                        Image(
                            painter = painterResource(id = R.drawable.trip_trop_coin),
                            contentDescription = "TripTrop Coin",
                            modifier = Modifier.size(12.dp)
                        )
                    }
                }
            }

            Text(
                text = "W poszukiwaniu gnomów",
                fontSize = 14.sp,
                fontFamily = OriginalSurfer,
                color = yellowGreen,
            )
            Text(
                text = "Poznaj lokalizację wszystkich małych istot\nzwiedzając stare miasto",
                fontSize = 10.sp,
                fontFamily = OriginalSurfer,
                color = alabasterGrey,
                textAlign = TextAlign.Center,
                style = TextStyle(
                    platformStyle = PlatformTextStyle(includeFontPadding = false),
                    lineHeightStyle = LineHeightStyle(
                        alignment = LineHeightStyle.Alignment.Center,
                        trim = LineHeightStyle.Trim.Both
                    )),
            )
        }
    }
}

@Composable
fun JourneyRow(journeyItems: List<JourneyData>){
    val journeyTitle = journeyItems[0].journeyName
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp, start = 10.dp)
    ){
        Column(
            modifier = Modifier
        ) {
            Text(
                text = journeyTitle,
                fontSize = 20.sp,
                fontFamily = OriginalSurfer,
                modifier = Modifier
                    .padding(start = 10.dp)
            )

            Row(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
                    .horizontalScroll(scrollState),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                journeyItems.forEach { item ->
                    JourneyBox(item)
                }
            }
        }
    }
}

@Composable
fun BoxForRiddles(riddleType: String){
    val typesOfRiddles = mapOf(
        "Quiz" to R.drawable.quiz_icon,
        "Rebus" to R.drawable.rebus_icon
    )

    Box(
        modifier = Modifier
            .padding(top = 10.dp)
            .height(90.dp)
            .width(110.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(wheat)
            .border(2.dp, Color.Black, RoundedCornerShape(10.dp))
    ){
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            Image(
                painter = painterResource(typesOfRiddles[riddleType] ?: R.drawable.quiz_icon),
                contentDescription = riddleType,
                modifier = Modifier.size(40.dp)
            )
            Text(
                text = riddleType,
                fontSize = 14.sp,
                fontFamily = OriginalSurfer
            )
        }
    }
}
@Composable
fun MainTextStyle(text : String, textSize: Int, color: Color, drawStyle: DrawStyle = Fill){
    Text(
        text = text,
        fontSize = textSize.sp,
        fontFamily = PirataOne,
        color = color,
        style = TextStyle(
            drawStyle = drawStyle,
            platformStyle = PlatformTextStyle(includeFontPadding = false),
            lineHeightStyle = LineHeightStyle(
                alignment = LineHeightStyle.Alignment.Center,
                trim = LineHeightStyle.Trim.Both
            )
        )
    )
}
@Composable
fun ExploreScreen(tripTropCoins: Int, journeys: List<List<JourneyData>>, closestJourney: ClosestJourneyData, onMapButtonClick: () -> Unit){
    val closestJournetListSize = closestJourney.listOfRiddles?.size ?: 0
    val smaller = closestJournetListSize == 0
    val boxHeight = if (smaller) 200.dp else 300.dp
    val dist = closestJourney.distance

    val scrollStateColumn = rememberScrollState()

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(R.drawable.background),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
        ){
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(110.dp)
                    .clip(RoundedCornerShape(bottomEnd = 24.dp, bottomStart = 24.dp))
                    .background(coffeeBean)
                    .border(2.dp, Color.Black, RoundedCornerShape(bottomEnd = 24.dp, bottomStart = 24.dp))
                    .padding(top = 10.dp)
            ){
                Row{
                    Column(
                        modifier = Modifier
                            .padding(start = 24.dp)
                    ){
                        Box {
                            MainTextStyle("TripTrop", 40, yellowGreen, drawStyle = Stroke(width = 10f, join = StrokeJoin.Round))
                            MainTextStyle("TripTrop", 40, forestMoss)
                        }
                        Box {
                            MainTextStyle("Na tropie przygody", 20, yellowGreen, drawStyle = Stroke(width = 10f, join = StrokeJoin.Round))
                            MainTextStyle("Na tropie przygody", 20, forestMoss)
                        }
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    Box(
                        modifier = Modifier
                            .padding(top = 20.dp, end = 20.dp)
                            .width(110.dp)
                            .height(40.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(wheat)
                            .border(1.dp, Color.Black, RoundedCornerShape(8.dp)),
                        contentAlignment = Alignment.Center
                    ){
                        Row{
                            Text(
                                text = tripTropCoins.toString(),
                                fontSize = 24.sp,
                                fontFamily = OriginalSurfer,
                                modifier = Modifier.padding(end = 2.dp)
                            )
                            Image(
                                painter = painterResource(R.drawable.trip_trop_coin),
                                contentDescription = "TripTrop Coin",
                                modifier = Modifier
                                    .size(30.dp)
                            )
                        }
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollStateColumn)
            ){
                Box(
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .width(350.dp)
                        .height(boxHeight)
                        .align(Alignment.CenterHorizontally)
                        .clip(RoundedCornerShape(16.dp))
                        .background(coffeeBean)
                        .border(2.dp, Color.Black, RoundedCornerShape(16.dp)),
                ){
                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(
                            text = "\u2022 Jesteś blisko ($dist m)",
                            fontSize = 16.sp,
                            fontFamily = OriginalSurfer,
                            color = yellowGreen,
                            modifier = Modifier
                                .padding(start = 20.dp, top = 20.dp, bottom = 10.dp)
                        )

                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = closestJourney.journeyName,
                                textAlign = TextAlign.Center,
                                fontSize = 24.sp,
                                fontFamily = OriginalSurfer,
                                color = yellowGreen
                            )

                            Row(
                                modifier = Modifier
                                    .padding(start = 30.dp, end = 30.dp)
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ){
                                when(closestJournetListSize){
                                    0 -> Spacer(modifier = Modifier.height(5.dp))
                                    1 -> BoxForRiddles(closestJourney.listOfRiddles!![0])
                                    2 -> {
                                        BoxForRiddles(closestJourney.listOfRiddles!![0])
                                        BoxForRiddles(closestJourney.listOfRiddles[1])
                                    }
                                }
                            }

                            Button(
                                onClick = {onMapButtonClick()},
                                border = BorderStroke(2.dp, Color.Black),
                                colors = ButtonDefaults.buttonColors(containerColor = forestMoss),
                                modifier = Modifier
                                    .padding(top = 20.dp)
                                    .width(170.dp)
                                    .height(40.dp)
                            ){
                                Text(
                                    text = "Wykup trasę",
                                    fontSize = 16.sp,
                                    fontFamily = OriginalSurfer,
                                    color = yellowGreen
                                )
                            }
                        }
                    }
                }
                journeys.forEach{ journey ->
                    JourneyRow(journey)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ExploreScreenPreview(){
    TripTropTheme {
        ExploreScreen(
            tripTropCoins = 1777,
            journeys = ExploreMocks.allExploreData,
            closestJourney = ExploreMocks.featuredJourney,
            onMapButtonClick = {}
        )
    }
}
