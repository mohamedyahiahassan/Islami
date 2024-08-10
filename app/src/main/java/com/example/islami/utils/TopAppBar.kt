package com.example.islami.utils

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavDestination
import com.example.islami.R
import com.example.islami.Screens

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun IslamiTopAppBar(
    currentDestination: NavDestination?,
    chapterTitle:String,
    onBackPressed:()->Unit){

    val title = remember {

        mutableStateOf("")
    }

    val backButtonShown = remember {
        mutableStateOf(false)
    }

    val showTopAppBar = remember {
        mutableStateOf(true)
    }

    LaunchedEffect(key1 = currentDestination?.route) {


        when(currentDestination?.route){

            Screens.Quran.route -> {

                backButtonShown.value = false

                title.value = "Quran"

                showTopAppBar.value = true
            }

            "${Screens.QuranChapters.route}/{startPage}/{endPage}/{lastRead}"->{

                title.value = chapterTitle

                backButtonShown.value = true

                showTopAppBar.value = false
            }

            Screens.QuranAudio.route->{

                showTopAppBar.value = false

            }



            Screens.Duaa.route ->{

                title.value = "Duaa"

                showTopAppBar.value = true
            }

            Screens.Prayers.route-> {

                title.value = "Prayer Times"

                showTopAppBar.value = true
            }

            Screens.Tasbeeh.route->{

                title.value = "Tasbeeh"

                showTopAppBar.value = true
            }

            Screens.Radio.route-> {

                title.value = "Radio"

                showTopAppBar.value = true
            }
        }
    }

    AnimatedVisibility(
        visible = showTopAppBar.value,
        enter = slideInVertically(initialOffsetY = { -it }),
        exit = slideOutVertically(targetOffsetY = { -it })) {


        TopAppBar(
            title = {
                Text(
                    text = title.value,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.ssp,
                )
            },

            navigationIcon = {

                if (backButtonShown.value) {

                    IconButton(
                        onClick = {
                            onBackPressed()

                        }) {
                        Image(
                            painter = painterResource(id = R.drawable.arrow_back),
                            contentDescription = "back button"
                        )

                    }

                }
            },

            actions = {

                IconButton(
                    onClick = { /*TODO*/ }

                ) {
                    Image(
                        painter = painterResource(id = R.drawable.search_icon),
                        contentDescription = "search"
                    )

                }

                IconButton(
                    onClick = { /*TODO*/ }

                ) {
                    Image(
                        painter = painterResource(id = R.drawable.more_settings_icon),
                        contentDescription = "more settings"
                    )

                }

            }

        )
    }
}