package pl.filked.triptrop.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
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
import pl.filked.triptrop.R
import pl.filked.triptrop.ui.theme.PirataOne
import pl.filked.triptrop.ui.theme.TripTropTheme
import pl.filked.triptrop.ui.theme.*

@Composable
fun JourneyBox(){

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
fun ExploreScreen(tripTropCoins: Int, distance: Int, closestJourney: String, listOfRiddlesClose: List<String>?){
    val closestJournetListSize = listOfRiddlesClose?.size ?: 0
    val smaller = closestJournetListSize == 0
    val boxHeight = if (smaller) 200.dp else 300.dp

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
                    .height(130.dp)
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
                            text = "\u2022 Jesteś blisko ($distance m)",
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
                                text = closestJourney,
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
                                    1 -> BoxForRiddles(listOfRiddlesClose!![0])
                                    2 -> {
                                        BoxForRiddles(listOfRiddlesClose!![0])
                                        BoxForRiddles(listOfRiddlesClose[1])
                                    }
                                }
                            }

                            Button(
                                onClick = {/*TODO*/},
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
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ExploreScreenPreview(){
    TripTropTheme {
        ExploreScreen(1777, 100, "Warszawski szlak\nprzeszłości", listOf("Quiz", "Rebus"))
    }
}
