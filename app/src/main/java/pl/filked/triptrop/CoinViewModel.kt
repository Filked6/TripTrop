package pl.filked.triptrop

import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf


class CoinViewModel: ViewModel() {
    var totalCoins by mutableIntStateOf(1000)
        private set

    fun addCoins(amount: Int) {
        totalCoins += amount
    }

    fun removeCoins(amount: Int) {
        totalCoins -= amount
    }
}