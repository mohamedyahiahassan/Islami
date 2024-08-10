package com.example.islami.quran

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.islami.utils.sdp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.domain.model.ChaptersItem
import com.example.islami.R
import com.example.islami.ui.theme.greyBorder
import com.example.islami.utils.LoadingDialog
import com.example.islami.utils.ssp

@Composable
fun QuranContent(
    viewModel: QuranViewModel = viewModel(),
    onChapterClick:(chapterName:String,startingPage:Int,endingPage:Int,lastRead:Boolean)->Unit,
    onLastReadClick:(startingPage:Int,endingPage:Int,lastRead:Boolean)->Unit,
    listenToQuran:(chapterId:Int)->Unit

){

    val context = LocalContext.current

    LaunchedEffect(key1 = Unit) {

        viewModel.getLastReadPage(context)

        if (viewModel.listOfChaptersName.isEmpty()){
            viewModel.getChaptersList()
        }


    }

    Column(
     modifier = Modifier
         .fillMaxSize(1f)
    ) {
        
        LoadingDialog(isLoading = viewModel.isLoading)

        Box(
            modifier = Modifier
                .fillMaxWidth(1f)
                .clickable {
                    Log.e("last read chapter", viewModel.lastReadChapter.toString())
                    val lastReadChapter =
                        viewModel.listOfChaptersName[viewModel.lastReadChapter - 1]
                    onLastReadClick(
                        lastReadChapter.pages?.get(0)!!,
                        lastReadChapter.pages?.get(1)!!,
                        true
                    )
                }

        ) {
            Image(
                painter = painterResource(id = R.drawable.last_read_banner),
                contentDescription = "last read banner",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.fillMaxWidth(1f)
            )

            Text(
                text = "Last Read",
                color = Color.White,
                fontSize = 16.ssp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(start = 55.sdp, top = 20.sdp))

            Text(
                text = viewModel.lastReadChapterName.value,
                color = Color.White,
                fontSize = 22.ssp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 20.sdp, top = 70.sdp))
        }

        Spacer(modifier = Modifier.height(10.dp))
        LazyColumn (
            modifier = Modifier
                .fillMaxWidth(1f)
                .weight(1f)
        ){

            items(viewModel.listOfChaptersName){

                HorizontalDivider(thickness = 1.sdp, color = greyBorder)

                Spacer(modifier = Modifier.height(10.dp))

                ChapterItem(
                    it,
                    onChapterClick = {

                        onChapterClick(
                            it.nameSimple!!,
                            it.pages?.get(0) ?:1,
                            it.pages?.get(1) ?:1,
                            false
                        )
                    },
                    listenToQuran = {

                        viewModel.getReciters()

                        viewModel.chapterId = it.id!!

                        it.id?.let { it1 -> listenToQuran(it1) }
                    })

                Spacer(modifier = Modifier.height(10.dp))

            }

        }
        HorizontalDivider(thickness = 1.sdp, color = greyBorder)
    }

}

@Composable
fun ChapterItem(
    chaptersItem: ChaptersItem,
    onChapterClick:()->Unit,
    listenToQuran:()->Unit
){

    Row (
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth(1f)
            .clickable {
                onChapterClick()
            }
    ){

        Box (modifier = Modifier.size(36.sdp)){

            Image(
                painter = painterResource(id = R.drawable.chapter_number_outline_1),
                contentDescription = "chapter number outline",
                modifier = Modifier.size(36.sdp))
            
            Text(
                text = "${chaptersItem.id}",
                fontSize = 14.ssp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .align(Alignment.Center)
                )

        }
        
        Spacer(modifier = Modifier.width(15.sdp))
        
        Column {
            
            Text(
                text = chaptersItem.nameSimple?:"",
                fontSize = 16.ssp,
                color = MaterialTheme.colorScheme.tertiary,
                fontWeight = FontWeight.Medium)

            Text(
                text = "${chaptersItem.versesCount} verses",
                fontSize = 12.ssp,
                color = MaterialTheme.colorScheme.tertiary,
                fontWeight = FontWeight.Medium)
        }

        Spacer(modifier = Modifier.weight(1f))

        IconButton(
            onClick = {

                listenToQuran()
            }
        ) {

            Image(
                painter = painterResource(id = R.drawable.play_arrow),
                contentDescription = "Listen to Quran")
        }


    }
}
