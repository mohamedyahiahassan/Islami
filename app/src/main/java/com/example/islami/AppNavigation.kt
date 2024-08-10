package com.example.islami

import android.content.Intent
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.islami.quran.ChapterScreenContent
import com.example.islami.quran.ChapterViewModel
import com.example.islami.quran.QuranAudioContent
import com.example.islami.quran.QuranContent
import com.example.islami.quran.QuranViewModel
import com.example.islami.ui.theme.chapterBackground2
import com.example.islami.utils.IslamiBottomAppBar
import com.example.islami.utils.IslamiTopAppBar
import com.example.islami.utils.LoadingDialog
import com.example.islami.utils.ReciterBottomSheet
import com.example.islami.utils.sdp
import dagger.hilt.android.qualifiers.ApplicationContext


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation() {

    val context = LocalContext.current

    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val sheetState = rememberModalBottomSheetState()

    var showBottomSheet by remember { mutableStateOf(false) }



    val chapterTitle = remember {

        mutableStateOf("")
    }


    Scaffold(

        bottomBar = {
                IslamiBottomAppBar(currentDestination) { route ->

                    navController.navigate(route) {

                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }

                        launchSingleTop = true

                    }
                }
            },
        topBar = {

            IslamiTopAppBar(
                currentDestination = currentDestination,
                chapterTitle = chapterTitle.value,

                onBackPressed = {
                    navController.navigateUp()

            })
        },

        ) {



            NavHost(
                navController = navController,
                startDestination = Screens.Quran.route,
                modifier = Modifier
                    .fillMaxSize(1f)
                    .background(
                        color = if (currentDestination?.route == "${Screens.QuranChapters.route}/{startPage}/{endPage}/{lastRead}") chapterBackground2 else Color.White
                    )
                    .padding(
                        start = 16.sdp,
                        end = 16.sdp,
                        top = if (currentDestination?.route == Screens.QuranAudio.route) 0.sdp else it.calculateTopPadding(),
                        bottom = it.calculateBottomPadding()
                    )
                    .verticalScroll(rememberScrollState())


            ) {

                composable(Screens.Quran.route) {

                    val viewModel = hiltViewModel<QuranViewModel>()

                    var selectedChapterId = 0
                    QuranContent(
                        viewModel = viewModel,
                        onChapterClick = {chaptertitle,startPage,endPage,lastRead->

                        chapterTitle.value = chaptertitle

                        navController.navigate("${Screens.QuranChapters.route}/${startPage}/${endPage}/${lastRead}")
                    },
                        onLastReadClick = {startPage,endPage,lastRead->

                            navController.navigate("${Screens.QuranChapters.route}/${startPage}/${endPage}/${lastRead}")
                        },
                        listenToQuran = {chapterId->

                            selectedChapterId = chapterId

                            showBottomSheet = true
                        })

                    if (showBottomSheet == true){

                        ReciterBottomSheet(
                            viewModel = viewModel,
                            sheetState = sheetState,
                            dismiss = {

                            showBottomSheet = false
                        },
                            selectReciter = {reciterId ->

                                viewModel.reciterId = reciterId

                                viewModel.context = context

                                if (viewModel.mediaPlayer!=null){

                                    viewModel.stopAudio()
                                }

                                viewModel.getChapterAudio()

                                showBottomSheet = false

                                while (viewModel.mediaPlayer?.isPlaying!=true){

                                    viewModel.isLoading.value = true

                                }

                                viewModel.isLoading.value = false

                                Intent(context.applicationContext,IslamiService::class.java).also {

                                    it.action = IslamiService.Actions.START.toString()
                                    context.startService(it)
                                }

                                navController.navigate(Screens.QuranAudio.route)


                            })
                    }

                    LoadingDialog(isLoading = viewModel.isLoading)

                }
                composable("${Screens.QuranChapters.route}/{startPage}/{endPage}/{lastRead}",

                    arguments = listOf(
                        navArgument("startPage"){

                            type = NavType.IntType
                        },
                        navArgument("endPage"){

                            type = NavType.IntType
                        },
                        navArgument("lastRead"){

                            type = NavType.BoolType
                        }
                    )
                ) {

                    val startPage = it.arguments?.getInt("startPage")

                    val endPage = it.arguments?.getInt("endPage")

                    val lastRead = it.arguments?.getBoolean("lastRead")

                    val viewModel = hiltViewModel<ChapterViewModel>()

                    if (startPage != null && endPage!=null && lastRead!=null) {
                        ChapterScreenContent(viewModel,startPage,endPage,lastRead){

                            navController.navigateUp()
                        }
                    }
                }


                composable(Screens.QuranAudio.route) {

                    val parentEntry = remember(it) {
                        navController.getBackStackEntry(Screens.Quran.route)
                    }

                    val viewModel = hiltViewModel<QuranViewModel>(parentEntry)
                    QuranAudioContent(viewModel)
                }


                composable(Screens.Duaa.route) {

                }

                composable(Screens.Prayers.route) {

                }

                composable(Screens.Tasbeeh.route) {

                }

                composable(Screens.Radio.route) {

                }
            }
        }
}

sealed class Screens(val route:String, @StringRes val resourceId: Int? = null){

    data object Quran : Screens("quran_screen",R.string.quran_nav)

    data object QuranChapters:Screens("quran_chapters_screen",)

    data object QuranAudio:Screens("quran_audio_reciter")

    data object Duaa : Screens("duaa_screen",R.string.duaa_nav)

    data object Prayers : Screens("prayers_screen",R.string.prayers_nav)

    data object Tasbeeh : Screens("tasbeh_screen",R.string.tasbeh_nav)

    data object Radio : Screens("radio_screen",R.string.radio_nav)
}