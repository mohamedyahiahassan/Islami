package com.example.islami.quran

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.datastore.preferences.protobuf.Internal.BooleanList
import com.example.islami.R
import com.example.islami.ui.theme.chapterBackground
import com.example.islami.ui.theme.chapterBackground2
import com.example.islami.ui.theme.displayFontFamilyAmiri
import com.example.islami.ui.theme.naskh
import com.example.islami.ui.theme.thulth
import com.example.islami.utils.LoadingDialog
import com.example.islami.utils.sdp
import com.example.islami.utils.ssp
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ChapterScreenContent(
    viewModel: ChapterViewModel,startPage:Int,endPage:Int,lastRead:Boolean,backHandling:()->Unit) {

    val context = LocalContext.current

    BackHandler {

        viewModel.saveLastReadPage(context)

        backHandling()
    }

    LaunchedEffect(key1 = Unit) {

        if (lastRead == true){


            viewModel.startPage = startPage

            viewModel.endPage = endPage

            viewModel.getNumberOfPages()

            viewModel.getPages()

            viewModel.getLastReadPage(context)

        } else {

            viewModel.startPage = startPage

            viewModel.endPage = endPage

            viewModel.getNumberOfPages()

            viewModel.getPages()
        }
    }



    val pagerState = rememberPagerState(pageCount = {
        viewModel.numberOfPages.intValue
    })

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {

        Column(modifier = Modifier.fillMaxSize(1f)) {
            
            LoadingDialog(isLoading = viewModel.isLoading)

            HorizontalPager(
                state = pagerState,
                verticalAlignment = Alignment.Top,
                modifier = Modifier.background(
                    brush = Brush.horizontalGradient(

                        colors = listOf(
                            chapterBackground2,
                            chapterBackground,
                            chapterBackground2
                        )
                    )
                )

            ) { page ->

                val coroutineScope = rememberCoroutineScope()


                LaunchedEffect(key1 = Unit) {

                    if (lastRead == true) {

                        coroutineScope.launch {

                            pagerState.scrollToPage(viewModel.lastReadPageInt)

                        }

                        return@LaunchedEffect
                    }
                }

                LaunchedEffect(key1 = pagerState) {
                    snapshotFlow { pagerState.currentPage }.collect { page ->

                        viewModel.lastReadPage = page.toString()
                    }

                }

                if (viewModel.textToBeDisplayedInEachPage.isNotEmpty() && viewModel.isLoading.value == false) {

                    Column {

                        Spacer(modifier = Modifier.padding(10.sdp))

                        if (page == 0 && viewModel.textToBeDisplayedInEachPage[0].substringAfter("(")
                                .substringBefore(")") == "١"
                        ) {

                            ChapterTitle(chapterName = viewModel.firstChapterName)
                        }

                        Spacer(modifier = Modifier.padding(7.sdp))

                        Text(
                            text = viewModel.textToBeDisplayedInEachPage[page],
                            fontSize = 22.ssp,
                            lineHeight = 40.ssp,
                            fontFamily = displayFontFamilyAmiri

                        )


                        if (viewModel.secondChapter.isNotEmpty() && viewModel.secondChapterPage == page) {

                            ChapterTitle(chapterName = viewModel.secondChapterName)

                            Spacer(modifier = Modifier.padding(7.sdp))

                            Text(
                                text = viewModel.secondChapter[0],
                                fontSize = 22.ssp,
                                lineHeight = 40.ssp,
                                fontFamily = displayFontFamilyAmiri

                            )

                            if (viewModel.thirdChapter.isNotEmpty()) {

                                ChapterTitle(chapterName = viewModel.thirdChapterName)

                                Spacer(modifier = Modifier.padding(7.sdp))

                                Text(
                                    text = viewModel.thirdChapter[0],
                                    fontSize = 22.ssp,
                                    lineHeight = 40.ssp,
                                    fontFamily = displayFontFamilyAmiri

                                )
                            }
                        }

                        Spacer(
                            modifier = Modifier
                                .weight(1f)
                                .background(Color.Red)
                        )
                    }

                }


            }
        }
    }
}

@Composable
fun ChapterTitle(chapterName:String){
    
    Column {

        Box(
            contentAlignment = Alignment.Center
        ) {

            Image(
                painter = painterResource(id = R.drawable.chapter_title_background),
                contentDescription = "chapter title",
                modifier = Modifier
                    .fillMaxWidth(1f)
            )

            Text(
                text = "سُورَةُ  " + chapterName,
                fontSize = 28.ssp,
                fontFamily = thulth,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth(1f)
            )
        }

        Image(
            painter = painterResource(id = R.drawable.bismilah),
            contentDescription = "bismilah",
            modifier = Modifier
                .fillMaxWidth(1f)
                .height(40.sdp)
        )
    }
}

