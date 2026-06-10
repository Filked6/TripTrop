package pl.filked.triptrop

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import pl.filked.triptrop.presentation.MainScreen
import pl.filked.triptrop.ui.theme.TripTropTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("TEST_APP", "MAIN ACTIVITY START")
        enableEdgeToEdge()

        setContent {
            TripTropTheme {

                MainScreen()

            }
        }
    }
}