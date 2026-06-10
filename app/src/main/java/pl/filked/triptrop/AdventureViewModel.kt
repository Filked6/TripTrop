package pl.filked.triptrop

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import pl.filked.triptrop.models.Adventure

class AdventureViewModel : ViewModel() {

    private val _adventures = MutableStateFlow<List<Adventure>>(emptyList())
    val adventures: StateFlow<List<Adventure>> = _adventures

    fun loadAdventures() {
        viewModelScope.launch {
            try {
                val result = RetrofitClient.api.getAdventures()
                _adventures.value = result
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}