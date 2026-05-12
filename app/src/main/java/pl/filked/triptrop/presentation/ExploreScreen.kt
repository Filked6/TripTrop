package pl.filked.triptrop.presentation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import pl.filked.triptrop.ui.theme.TripTropTheme

@Composable
fun ExploreScreen(){
    Text(
        text = "Odkrywaj",
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
    )
}

@Preview(showBackground = true)
@Composable
fun ExploreScreenPreview(){
    TripTropTheme {
        ExploreScreen()
    }
}
