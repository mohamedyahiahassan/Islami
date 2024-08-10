package com.example.islami.quran

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.wifi.WifiManager
import android.os.PowerManager
import android.util.Log
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.Constants
import com.example.data.QuranDataStore
import com.example.domain.Resource
import com.example.domain.model.ChaptersItem
import com.example.domain.model.RecitationsItem
import com.example.domain.repository.QuranRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class QuranViewModel @Inject constructor(
    private val quranRepository: QuranRepository):ViewModel() {

    val listOfChaptersName = mutableStateListOf<ChaptersItem>()

    var lastReadChapter = 0

    var lastReadChapterName = mutableStateOf<String>("")

    val isLoading = mutableStateOf(true)

    val listOfReciters= mutableStateListOf<RecitationsItem?>()

    var reciterId = 1

    var chapterId = 1

    lateinit var wifiManager: WifiManager

    lateinit var  wifiLock: WifiManager.WifiLock

    var mediaPlayer: MediaPlayer? = null

    var audioUrl = ""

    var context:Context? = null

    val totalDuration =  mutableLongStateOf(0)

    val sliderPosition = mutableLongStateOf(0)

    val currentPosition =  mutableLongStateOf(0)



    fun getChaptersList(){


        viewModelScope.launch (Dispatchers.IO){

            quranRepository.getChapterList().collect{

                when(it){
                    is Resource.Error -> {

                        Log.e("error fetching Chapters name",it.message.toString())

                        isLoading.value = false
                    }
                    is Resource.Loading -> {

                        isLoading.value = true

                    }
                    is Resource.Success -> {


                       it.data?.forEach {

                           if (it != null) {
                               listOfChaptersName.add(it)
                           }
                       }

                        isLoading.value = false
                    }
                }
            }
        }
    }

    fun getLastReadPage(context: Context){

        viewModelScope.launch (Dispatchers.IO) {

           val lastReadPage = QuranDataStore(context).readLastReadPage() ?:"1,1"

            lastReadChapter = lastReadPage.substringBefore(",").toInt()

            lastReadChapterName.value = Constants.chapterNamesEnglish[lastReadChapter-1]

        }
    }

    fun getReciters(){

        viewModelScope.launch (Dispatchers.IO){

            quranRepository.getRecitersList().collect{

                when(it){
                    is Resource.Error -> {

                        Log.e("error fetching reciters",it.message.toString())
                    }
                    is Resource.Loading -> {


                    }
                    is Resource.Success -> {

                        it.data?.let { it1 -> listOfReciters.addAll(it1) }
                    }
                }
            }
        }
    }

    fun getChapterAudio(){

        viewModelScope.launch (Dispatchers.IO){

            quranRepository.getChapterAudio(reciterId, chapterId).collect{

                when(it){
                    is Resource.Error -> {

                        Log.e("error fetching audio file",it.message.toString())
                    }
                    is Resource.Loading -> {


                    }
                    is Resource.Success -> {

                      if (!it.data?.audioUrl.isNullOrEmpty()){

                          audioUrl = it.data?.audioUrl!!

                          Log.e("dsad0",audioUrl)

                          createPlayer()
                      }


                    }
                }
            }
        }


    }

    fun createPlayer(){

        try {

            mediaPlayer = MediaPlayer()

            mediaPlayer?.apply {

                setAudioAttributes(
                    AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .build()
                )

                setWakeMode(context, PowerManager.PARTIAL_WAKE_LOCK)

                setDataSource(audioUrl)

                wifiManager = context?.getSystemService(Context.WIFI_SERVICE) as WifiManager

                wifiLock = wifiManager.createWifiLock(WifiManager.WIFI_MODE_FULL, "mylock")

                wifiLock.acquire()

                prepare()

                playAudio()

                if (mediaPlayer?.duration!=null){

                    totalDuration.value = mediaPlayer?.duration!!.toLong()

                }

            }



        } catch (e:Exception){

            Log.e("Exception playing audio",e.message.toString())
        }
    }



    fun playAudio(){

        mediaPlayer?.start()

    }

    fun pauseAudio(){

        mediaPlayer?.pause()
    }

    fun stopAudio(){

        mediaPlayer?.stop()

        mediaPlayer?.release()

        mediaPlayer = null

        wifiLock.release()

    }




}

fun Long.convertAudioDurationToTime():String{

    val totalAudioDuration : Double = (this / 1000.00) / 60

    val minutes = totalAudioDuration.toInt()

    val seconds = if(minutes == 0) totalAudioDuration * 60 else (totalAudioDuration % minutes) * 60

    if (seconds<10){

        return "$minutes:0${seconds.toInt()}"
    } else {

        return  "$minutes:${seconds.toInt()}"
    }

}