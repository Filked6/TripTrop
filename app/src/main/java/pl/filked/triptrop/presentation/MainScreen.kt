package pl.filked.triptrop.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import pl.filked.triptrop.data.BottomNavItem
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import pl.filked.triptrop.BackpackData
import pl.filked.triptrop.ExploreMocks
import pl.filked.triptrop.FriendData
import pl.filked.triptrop.R
import pl.filked.triptrop.data.ArtifactData
import pl.filked.triptrop.data.ClosestJourneyData
import pl.filked.triptrop.data.JourneyData
import pl.filked.triptrop.ui.theme.*
import kotlin.collections.listOf

@Composable
fun MainScreen() {
    val navController = rememberNavController()

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
                                Icon(
                                    painter = painterResource(id = item.icon),
                                    contentDescription = item.title
                                )
                            },
                            label = {
                                Text(
                                    text = item.title,
                                    fontWeight = FontWeight.Bold
                                )
                            },
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
            composable(BottomNavItem.Explore.route){
                ExploreScreen(
                    tripTropCoins = 1777,
                    journeys = ExploreMocks.allExploreData,
                    closestJourney = ExploreMocks.featuredJourney
                )
            }
            composable(BottomNavItem.Backpack.route) {
                BackpackScreen(BackpackData.allBackpackData)
            }
            composable(BottomNavItem.Friends.route){
                FriendsScreen(FriendData.allFriendsData)
            }
            composable(BottomNavItem.Profile.route){
                ProfileScreen()
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