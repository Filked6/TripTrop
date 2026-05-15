package pl.filked.triptrop.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pl.filked.triptrop.FriendData
import pl.filked.triptrop.R
import pl.filked.triptrop.data.FriendsData
import pl.filked.triptrop.ui.theme.OriginalSurfer
import pl.filked.triptrop.ui.theme.TripTropTheme
import pl.filked.triptrop.ui.theme.*

@Composable
fun Friend(
    friendData: FriendsData
){
    val name = friendData.fullName
    val photo = friendData.photoId
    Box(
        modifier = Modifier
            .padding(start = 20.dp, top = 15.dp)
            .fillMaxWidth()
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(photo),
                contentDescription = name,
                modifier = Modifier
                    .clip(CircleShape)
                    .size(70.dp)
                    .border(1.dp, Color.Black, CircleShape)
            )

            Text(
                text = name,
                fontSize = 24.sp,
                fontFamily = OriginalSurfer,
                modifier = Modifier
                    .padding(start = 20.dp)
            )
        }
    }
}

@Composable
fun FriendsScreen(friends: List<FriendsData>){
    Box(modifier = Modifier.fillMaxSize()) {
        var searchText by remember {mutableStateOf("")}

        Image(
            painter = painterResource(R.drawable.friends_background),
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
                    .padding(top = 20.dp)
                    .width(300.dp)
                    .height(60.dp)
                    .align(Alignment.CenterHorizontally)
                    .clip(RoundedCornerShape(50.dp))
                    .background(alabasterGrey)
                    .border(1.dp, Color.Black, RoundedCornerShape(50.dp)),
                contentAlignment = Alignment.Center
            ){
                Row(
                    modifier = Modifier
                ){
                    Image(
                        painter = painterResource(R.drawable.search_icon),
                        contentDescription = "Search",
                        modifier = Modifier
                            .padding(start = 15.dp)
                            .size(40.dp)
                            .align(Alignment.CenterVertically),
                    )
                    TextField(
                        value = searchText,
                        onValueChange = { searchText = it },
                        placeholder = {
                            Text(
                                "Szukaj...",
                                fontSize = 20.sp,
                                fontFamily = OriginalSurfer,
                                color = dimGrey
                                )},
                        modifier = Modifier
                            .padding(end = 10.dp)
                            .fillMaxWidth(),
                        textStyle = TextStyle(
                            fontSize = 20.sp,
                            fontFamily = OriginalSurfer
                        ),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            disabledContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                        )
                    )
                }
            }

            friends.forEach{ friend ->
                Friend(friend)
            }

            Spacer(modifier = Modifier.weight(1f))

            Box(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 10.dp),
                contentAlignment = Alignment.Center
            ) {
                Button(
                    onClick = {/*TODO*/ },
                    border = BorderStroke(2.dp, Color.Black),
                    colors = ButtonDefaults.buttonColors(containerColor = coffeeBean),
                    ) {
                    Text(
                        text = "Wyświetl ranking",
                        fontSize = 24.sp,
                        fontFamily = OriginalSurfer,
                        color = yellowGreen
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FriendsScreenPreview(){
    TripTropTheme {
        FriendsScreen(FriendData.allFriendsData)
    }
}