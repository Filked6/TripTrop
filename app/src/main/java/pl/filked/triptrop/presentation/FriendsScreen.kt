package pl.filked.triptrop.presentation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun FriendsScreen(){
    Text(
        text = "Przyjaciele",
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
    )
}