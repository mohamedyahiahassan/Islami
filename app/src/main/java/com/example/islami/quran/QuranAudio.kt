package com.example.islami.quran

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.islami.R
import com.example.islami.ui.theme.greyBorder
import com.example.islami.ui.theme.primaryBlue
import com.example.islami.ui.theme.primaryBluebar
import com.example.islami.ui.theme.thulth
import com.example.islami.utils.sdp
import com.example.islami.utils.ssp
import kotlinx.coroutines.delay

@Composable
fun QuranAudioContent(viewModel: QuranViewModel){

    val isAudioPlaying = remember {

        mutableStateOf(true)
    }

    LaunchedEffect(
        key1 = viewModel.mediaPlayer?.currentPosition,
        key2 = isAudioPlaying.value,
        key3 = viewModel.mediaPlayer?.isPlaying) {

        delay(1000)
        viewModel.currentPosition.longValue = viewModel.mediaPlayer?.currentPosition?.toLong()!!
    }

    LaunchedEffect(viewModel.currentPosition.longValue) {
        viewModel.sliderPosition.longValue = viewModel.currentPosition.longValue
    }


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize(1f)
    ) {

        Box(
              contentAlignment = Alignment.TopCenter
                ){

            Image(
                painter = painterResource(id = R.drawable.audio_quran_background),
                contentDescription = "",
                contentScale = ContentScale.FillHeight,
                modifier = Modifier.height(520.sdp))

            Column (
                horizontalAlignment = Alignment.CenterHorizontally
            ){

                Spacer(modifier = Modifier.height(60.sdp))

                Text(
                    text = "Al-Ala",
                    color = Color.White,
                    fontSize = 52.ssp,
                    fontWeight = FontWeight.SemiBold)

                Text(
                    text = "الأعلى",
                    color = Color.White,
                    fontSize = 52.ssp,
                    fontFamily = thulth,
                    fontWeight = FontWeight.SemiBold)

                Spacer(modifier = Modifier.height(80.sdp))

                Image(
                    painter = painterResource(id = R.drawable.quran_image_audio),
                    contentDescription = "",
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier.width(230.sdp))

            }

        }

        Spacer(modifier = Modifier.height(70.sdp))

        Column (
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
        ){

            QuranSlider(
                sliderPosition = viewModel.sliderPosition.longValue.toFloat(),
                duration = viewModel.totalDuration.longValue.toFloat(),
                onValueChange = {

                    viewModel.sliderPosition.longValue = it.toLong()
                },
                onValueChangeFinished = {

                    viewModel.currentPosition.longValue = viewModel.sliderPosition.longValue

                    viewModel.mediaPlayer?.seekTo(viewModel.sliderPosition.longValue.toInt())

                }
            )


            Row{

                Text(text = viewModel.currentPosition.longValue.convertAudioDurationToTime())

                Spacer(modifier = Modifier.weight(1f))


                Text(text = viewModel.totalDuration.longValue.convertAudioDurationToTime())
            }

        }

        Spacer(modifier = Modifier.height(80.sdp))

        Row(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
                .fillMaxWidth(1f)
                .border(BorderStroke(1.sdp, greyBorder), RoundedCornerShape(10.sdp))
                .clip(RoundedCornerShape(10.sdp))

        ){

            Spacer(modifier = Modifier.weight(1f))

            MediaButton(drawable =  R.drawable.reciter_icon,  size = 32,desc = "change reciter") {

            }

            Spacer(modifier = Modifier.weight(1f))

            MediaButton(drawable =  R.drawable.skip_previous_icon, size = 48, desc = "go to previous chapter") {

                viewModel.currentPosition.longValue += 1
            }

            Spacer(modifier = Modifier.weight(1.5f))

            IconButton(
                onClick = {

                    if (isAudioPlaying.value == true){

                        isAudioPlaying.value = false

                        viewModel.pauseAudio()

                    }else{

                        isAudioPlaying.value = true

                        viewModel.playAudio()

                    }

                }) {

                if (isAudioPlaying.value == true){

                    Image(
                        painter = painterResource(id =  R.drawable.pause_icon),
                        contentDescription = "pause quran",
                        modifier = Modifier.size(48.sdp))

                }else{

                    Image(
                        painter = painterResource(id =  R.drawable.play_arrow),
                        contentDescription = "play quran",
                        modifier = Modifier.size(48.sdp))
                }


            }

            Spacer(modifier = Modifier.weight(1.5f))

            MediaButton(drawable =  R.drawable.skip_next_icon, size = 48, desc = "go to next chapter") {

            }

            Spacer(modifier = Modifier.weight(1f))

            MediaButton(drawable = R.drawable.download_icon, size = 32, desc = "download Chapter") {


            }

            Spacer(modifier = Modifier.weight(1f))

        }


    }
}

@Composable
fun MediaButton(drawable:Int,desc:String,size:Int,onClick:()->Unit){

    IconButton(
        onClick = {

            onClick()
        }) {

        Image(
            painter = painterResource(id = drawable),
            contentDescription = desc,
            modifier = Modifier.size(size.sdp))

    }
}

@Composable
fun QuranSlider(
    sliderPosition:Float,
    duration:Float,
    onValueChange:(valueChange:Float)->Unit,
    onValueChangeFinished:()->Unit){



    Slider(
        value = sliderPosition,
        onValueChange = {

            onValueChange(it)

        },
        onValueChangeFinished = {

            onValueChangeFinished()
        },

        valueRange = 0f..duration,

        colors = SliderDefaults.colors(
            thumbColor = primaryBlue,
            activeTrackColor = primaryBlue,
            inactiveTrackColor = primaryBluebar,
        ),


        )
}