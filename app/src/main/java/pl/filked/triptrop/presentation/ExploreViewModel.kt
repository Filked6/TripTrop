package pl.filked.triptrop.presentation

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pl.filked.triptrop.R
import pl.filked.triptrop.RetrofitClient
import pl.filked.triptrop.data.JourneyData
import pl.filked.triptrop.models.Adventure

fun Adventure.toJourneyData(): JourneyData {
    return JourneyData(
        journeysTitle = "Trasa", // na razie stałe
        journeyName = name,
        journeyDetails = "Brak opisu z backendu",
        journeyCost = 0,
        journeyPhoto = imageUrl,
        trailIds = trailIds
    )
}
class ExploreViewModel : ViewModel() {
    init {
        Log.d("TEST_APP", "VIEWMODEL CREATED")
    }
    var adventures by mutableStateOf<List<Adventure>>(emptyList())
        private set

    var journeys by mutableStateOf<List<List<JourneyData>>>(emptyList())
        private set

    fun loadAdventures() {
        viewModelScope.launch {
            try {

                val result = RetrofitClient.api.getAdventures()

                journeys = listOf(
                    result.map { it.toJourneyData() }
                )


            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}