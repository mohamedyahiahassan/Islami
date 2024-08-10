package com.example.islami.utils

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.example.islami.R
import com.example.islami.Screens


@Composable
fun IslamiBottomAppBar(
    currentDestination: NavDestination?,
    navigateToSelectedNavBarItem:(route:String)->Unit){

    val showBottomAppBar = remember {
        mutableStateOf(true)
    }

    LaunchedEffect(key1 = currentDestination?.route) {


        when (currentDestination?.route) {

            Screens.Quran.route -> {

                showBottomAppBar.value = true
            }

            "${Screens.QuranChapters.route}/{startPage}/{endPage}/{lastRead}" -> {

                showBottomAppBar.value = false
            }

            Screens.QuranAudio.route->{

                showBottomAppBar.value = false
            }


            Screens.Duaa.route -> {


                showBottomAppBar.value = true
            }

            Screens.Prayers.route -> {


                showBottomAppBar.value = true
            }

            Screens.Tasbeeh.route -> {


                showBottomAppBar.value = true
            }

            Screens.Radio.route -> {


                showBottomAppBar.value = true
            }
        }
    }

    AnimatedVisibility(
        visible = showBottomAppBar.value,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it })) {
        NavigationBar(
            containerColor = MaterialTheme.colorScheme.onSecondary,
            tonalElevation = 5.dp
        ) {

            NavigationBarItem(
                selected = currentDestination?.hierarchy?.any { it.route == Screens.Quran.route } == true,
                onClick = {

                    navigateToSelectedNavBarItem(Screens.Quran.route)
                },
                label = {

                    if (currentDestination?.hierarchy?.any { it.route == Screens.Quran.route } == true) {
                        Text(text = "Quran", color = MaterialTheme.colorScheme.primary)
                    }
                },
                icon = {

                    if (currentDestination?.hierarchy?.any { it.route == Screens.Quran.route } == true) {

                        Image(
                            painter = painterResource(id = R.drawable.quran_icon_nav),
                            contentDescription = "quran tab icon"
                        )
                    } else {

                        Image(
                            painter = painterResource(id = R.drawable.quran_icon_nav_unselected),
                            contentDescription = "quran tab icon"
                        )
                    }


                },
                colors = NavigationBarItemDefaults.colors(indicatorColor = Color.Transparent)
            )

            NavigationBarItem(
                selected = currentDestination?.hierarchy?.any { it.route == Screens.Duaa.route } == true,
                onClick = {

                    navigateToSelectedNavBarItem(Screens.Duaa.route)
                },
                label = {

                    if (currentDestination?.hierarchy?.any { it.route == Screens.Duaa.route } == true) {
                        Text(text = "Duaa", color = MaterialTheme.colorScheme.primary)
                    }
                },
                icon = {

                    if (currentDestination?.hierarchy?.any { it.route == Screens.Duaa.route } == true) {

                        Image(
                            painter = painterResource(id = R.drawable.duaa_icon_nav),
                            contentDescription = "duaa tab icon"
                        )
                    } else {

                        Image(
                            painter = painterResource(id = R.drawable.duaa_icon__nav_unselected),
                            contentDescription = "duaa tab icon"
                        )
                    }


                },
                colors = NavigationBarItemDefaults.colors(indicatorColor = Color.Transparent)
            )

            NavigationBarItem(
                selected = currentDestination?.hierarchy?.any { it.route == Screens.Prayers.route } == true,
                onClick = {

                    navigateToSelectedNavBarItem(Screens.Prayers.route)
                },
                label = {

                    if (currentDestination?.hierarchy?.any { it.route == Screens.Prayers.route } == true) {
                        Text(text = "Prayers", color = MaterialTheme.colorScheme.primary)
                    }
                },
                icon = {

                    if (currentDestination?.hierarchy?.any { it.route == Screens.Prayers.route } == true) {

                        Image(
                            painter = painterResource(id = R.drawable.prayer_icon_nav),
                            contentDescription = "prayers tab icon"
                        )
                    } else {

                        Image(
                            painter = painterResource(id = R.drawable.prayer_icon_nav_unselected),
                            contentDescription = "prayers tab icon"
                        )
                    }


                },
                colors = NavigationBarItemDefaults.colors(indicatorColor = Color.Transparent)
            )

            NavigationBarItem(
                selected = currentDestination?.hierarchy?.any { it.route == Screens.Tasbeeh.route } == true,
                onClick = {

                    navigateToSelectedNavBarItem(Screens.Tasbeeh.route)
                },
                label = {

                    if (currentDestination?.hierarchy?.any { it.route == Screens.Tasbeeh.route } == true) {
                        Text(text = "Tasbeeh", color = MaterialTheme.colorScheme.primary)
                    }
                },
                icon = {

                    if (currentDestination?.hierarchy?.any { it.route == Screens.Tasbeeh.route } == true) {

                        Image(
                            painter = painterResource(id = R.drawable.tasbih_icon_nav),
                            contentDescription = "tasbeeh tab icon"
                        )
                    } else {

                        Image(
                            painter = painterResource(id = R.drawable.tasbih_icon_nav_unselected),
                            contentDescription = "tasbeeh tab icon"
                        )
                    }


                },
                colors = NavigationBarItemDefaults.colors(indicatorColor = Color.Transparent)
            )

            NavigationBarItem(
                selected = currentDestination?.hierarchy?.any { it.route == Screens.Radio.route } == true,
                onClick = {

                    navigateToSelectedNavBarItem(Screens.Radio.route)
                },
                label = {

                    if (currentDestination?.hierarchy?.any { it.route == Screens.Radio.route } == true) {
                        Text(text = "Radio", color = MaterialTheme.colorScheme.primary)
                    }
                },
                icon = {

                    if (currentDestination?.hierarchy?.any { it.route == Screens.Radio.route } == true) {

                        Image(
                            painter = painterResource(id = R.drawable.radio_icon_nav),
                            contentDescription = "radio tab icon"
                        )
                    } else {

                        Image(
                            painter = painterResource(id = R.drawable.radio_icon_nav_unselected),
                            contentDescription = "radio tab icon"
                        )
                    }


                },
                colors = NavigationBarItemDefaults.colors(indicatorColor = Color.Transparent)
            )
        }
    }
    
}