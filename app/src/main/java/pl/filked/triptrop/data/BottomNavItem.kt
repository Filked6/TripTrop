package pl.filked.triptrop.data

import androidx.annotation.DrawableRes
import pl.filked.triptrop.R


sealed class BottomNavItem(val route: String, val title: String, @DrawableRes val icon: Int) {
    object Explore: BottomNavItem("Explore", "Odkrywaj", R.drawable.odkrywaj)
    object Backpack: BottomNavItem("Backpack", "Plecak", R.drawable.plecak)
    object Friends: BottomNavItem("Friends", "Przyjaciele", R.drawable.znajomi)
    object Profile: BottomNavItem("Profile", "Profil", R.drawable.profil)
}