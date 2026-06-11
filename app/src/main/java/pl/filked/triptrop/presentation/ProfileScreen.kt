package pl.filked.triptrop.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import pl.filked.triptrop.ProfileSampleData
import pl.filked.triptrop.R
import pl.filked.triptrop.data.ProfileData
import pl.filked.triptrop.ui.theme.TripTropTheme
import pl.filked.triptrop.ui.theme.*

@Composable
fun BigButton(text: String){
    Box(
        modifier = Modifier
            .padding(top = 10.dp, bottom = 5.dp)
            .width(250.dp)
            .height(50.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(if(text == "Wyloguj") wheat else coffeeBean)
            .border(1.dp, Color.Black.copy(alpha = 0.4f), RoundedCornerShape(10.dp))
            .clickable(
                onClick = {/*TODO*/}
            ),
        contentAlignment = Alignment.Center,
    ){
        Text(
            text = text,
            fontSize = 24.sp,
            fontFamily = OriginalSurfer,
            color = if(text == "Wyloguj") Color.Black else yellowGreen
        )
        if (text == "Sklep monet"){
            Image(
                painter = painterResource(R.drawable.coinssss),
                contentDescription = "Coins",
                modifier = Modifier
                    .size(50.dp)
                    .offset(x = (-100).dp, y = 10.dp)
            )
        }
    }
}

@Composable
fun SmallBox(text: String, value: Int){
    Box(
        modifier = Modifier
            .size(70.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(wheat)
            .border(1.dp, Color.Black.copy(alpha = 0.4f), RoundedCornerShape(10.dp)),
    ){
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            Text(
                text = text,
                fontSize = 12.sp,
                fontFamily = OriginalSurfer,
            )
            Text(
                text = value.toString(),
                fontSize = 24.sp,
                fontFamily = OriginalSurfer
            )
        }
    }
}

@Composable
fun ProfileScreen(
    profile: ProfileData,
    tripTropCoins: Int
){
    val name = profile.name
    val photo = profile.photoId
    val artifacts = profile.artifacts
    val journeys = profile.journets
    val tropy = profile.tropy

    val scrollState = rememberScrollState()

    val listOfProfileThingies = mapOf(
        "Artefakty" to artifacts,
        "Przygody" to journeys,
        "Tropy" to tropy
    )

    val listOfButtons = listOf(
        "Zakupione trasy",
        "Sklep monet",
        "Ustawienia konta",
        "Centrum pomocy",
        "Wyloguj"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(R.drawable.bck_profile),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 20.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Box(
                modifier = Modifier
                    .width(250.dp)
                    .height(320.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(coffeeBean)
                    .border(2.dp, Color.Black.copy(alpha = 0.4f), RoundedCornerShape(20.dp))
            ){
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Image(
                        painter = painterResource(photo),
                        contentDescription = "Avatar",
                        modifier = Modifier
                            .padding(top = 10.dp)
                            .size(180.dp)
                            .clip(CircleShape)
                            .border(2.dp, yellowGreen, CircleShape)
                    )
                    Text(
                        text = name,
                        fontSize = 24.sp,
                        fontFamily = OriginalSurfer,
                        color = yellowGreen,
                        modifier = Modifier
                            .padding(bottom = 10.dp, top = 10.dp)
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        listOfProfileThingies.forEach { (key, value) ->
                            SmallBox(key, value)
                        }
                    }
                }
            }
            Text(
                text = "Twoje saldo:",
                fontSize = 24.sp,
                fontFamily = OriginalSurfer,
                modifier = Modifier
                    .padding(top  = 10.dp, bottom = 10.dp)
            )
            Box(
                modifier = Modifier
                    .zIndex(1f),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .width(130.dp)
                        .height(50.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(wheat)
                        .border(1.dp, Color.Black.copy(alpha = 0.4f), RoundedCornerShape(10.dp))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = tripTropCoins.toString(),
                            fontSize = 28.sp,
                            fontFamily = OriginalSurfer,
                            modifier = Modifier
                                .padding(end = 4.dp)
                        )
                        Image(
                            painter = painterResource(R.drawable.bigger_coin),
                            contentDescription = "Trip Trop coin",
                            modifier = Modifier
                                .size(30.dp)
                        )
                    }
                }
                Image(
                    painter = painterResource(R.drawable.treasure_chest),
                    contentDescription = "Treasure Chest",
                    modifier = Modifier
                        .size(70.dp)
                        .offset(x= 110.dp, y = 20.dp)
                )
            }

            listOfButtons.forEach { item ->
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.zIndex(if (item == "Sklep monet") 1f else 0f)
                ) {
                    BigButton(item)

                    if (item == "Sklep monet") {
                        Image(
                            painter = painterResource(R.drawable.coinssss),
                            contentDescription = "Coins",
                            modifier = Modifier
                                .size(50.dp)
                                .offset(x = (-110).dp, y = 13.dp)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview(){
    TripTropTheme {
        ProfileScreen(
            profile = ProfileSampleData.profileData,
            tripTropCoins = 1000 // ZMIANA: Przykładowa wartość do podglądu
        )
    }
}