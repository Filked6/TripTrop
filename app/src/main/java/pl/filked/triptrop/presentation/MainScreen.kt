package pl.filked.triptrop.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import pl.filked.triptrop.data.BottomNavItem
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import pl.filked.triptrop.BackpackData
import pl.filked.triptrop.ExploreMocks
import pl.filked.triptrop.FriendData
import pl.filked.triptrop.ProfileSampleData
import pl.filked.triptrop.ui.theme.*
import kotlin.collections.listOf
import pl.filked.triptrop.models.TropFeature
import pl.filked.triptrop.RetrofitClient
import kotlinx.coroutines.launch
import androidx.compose.runtime.rememberCoroutineScope
@Composable
fun MainScreen() {
    val navController = rememberNavController()

    var currentMapTarget by remember { mutableStateOf(listOf<TropFeature>()) }
    val scope = rememberCoroutineScope()

    var totalCoins by remember { mutableStateOf(100) }

    val items = listOf(
        BottomNavItem.Explore,
        BottomNavItem.Backpack,
        BottomNavItem.Friends,
        BottomNavItem.Profile
    )

    Scaffold(
        bottomBar = {
            Column {
                HorizontalDivider(
                    thickness = 2.dp,
                    color = Color.Black
                )

                NavigationBar(
                    containerColor = lavenderBlush,
                    tonalElevation = 0.dp
                ) {
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentRoute = navBackStackEntry?.destination?.route

                    items.forEach { item ->
                        NavigationBarItem(
                            icon = {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Icon(
                                        painter = painterResource(id = item.icon),
                                        contentDescription = item.title
                                    )
                                    Spacer(modifier = Modifier.height(2.dp))

                                    Text(
                                        text = item.title,
                                        fontSize = 14.sp, // Jeśli się dalej rozjeżdża tekst ikonek na dole to zmienić na mniejsze
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            },
                            label = null,
                            alwaysShowLabel = true,
                            selected = currentRoute == item.route,
                            onClick = {
                                navController.navigate(item.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = Color.Black,
                                unselectedIconColor = Color.Black,
                                selectedTextColor = Color.Black,
                                unselectedTextColor = Color.Black,
                            )
                        )
                    }
                }
            }
        }
    ){ innerPadding ->
        NavHost(
            navController = navController,
            startDestination = BottomNavItem.Explore.route,
            modifier = Modifier.padding(innerPadding)
        ){
            composable(BottomNavItem.Explore.route) {
                val vm: ExploreViewModel = viewModel()
                LaunchedEffect(Unit) {
                    vm.loadAdventures()
                }
                ExploreScreen(
                    tripTropCoins = totalCoins,
                    journeys = vm.journeys,
                    closestJourney = ExploreMocks.featuredJourney,

                    onMapButtonClick = {
//                        scope.launch {
//                            currentMapTarget = clickedJourney.trailIds.map { id ->
//                                RetrofitClient.api.getTropById(id)
//                            }
//
//
//                            navController.navigate("map_screen")
//                        }
                    },

                    onJourneyClick = { clickedJourney ->
                        scope.launch {
                            currentMapTarget = clickedJourney.trailIds.map { id ->
                                RetrofitClient.api.getTropById(id)
                            }

                            navController.navigate("map_screen")
                        }
                    }
                )
            }
            composable(BottomNavItem.Backpack.route) {
                BackpackScreen(BackpackData.allBackpackData)
            }
            composable(BottomNavItem.Friends.route){
                FriendsScreen(FriendData.allFriendsData)
            }
            composable(BottomNavItem.Profile.route){
                ProfileScreen(ProfileSampleData.profileData)
            }
            composable("map_screen"){
                OsmMapScreen(
                    target = currentMapTarget,
                    totalCoins = totalCoins,
                    onCoinsChange = { newCoins ->
                        totalCoins = newCoins
                    },
                    onBackClick = { navController.popBackStack() }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview(){
    TripTropTheme {
        MainScreen()
    }
}