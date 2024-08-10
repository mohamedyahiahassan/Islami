package com.example.islami.quran

import android.content.Context
import android.util.Log
import com.example.data.Constants
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.QuranDataStore
import com.example.domain.Resource
import com.example.domain.model.VersesItem
import com.example.domain.repository.QuranRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ChapterViewModel @Inject constructor(
    private val quranRepository: QuranRepository
): ViewModel() {

    var startPage = 0

    var endPage = 0

    val numberOfPages = mutableIntStateOf(0)

    var pageNumber = 0

    val isLoading = mutableStateOf(true)

    // 1st Chapter
    val textToBeDisplayedInEachPage = mutableStateListOf<String>()

    var firstChapterName = ""

    // 2nd Chapter
    val secondChapter = mutableStateListOf<String>()

    var secondChapterPage = 0

    var secondChapterName = ""

    // 3rd Chapter
    val thirdChapter = mutableStateListOf<String>()

    var thirdChapterPage = 0

    var thirdChapterName = ""

    //last read

    var lastReadChapter = ""

    var lastReadPage = ""

    var lastReadPageInt = 0


    fun getNumberOfPages(){

        numberOfPages.intValue = endPage - startPage + 1
    }

    fun getPages(){

        viewModelScope.launch (Dispatchers.IO){

            for (i in startPage ..endPage){

                quranRepository.getPages(i).collect{

                    when(it){
                        is Resource.Error -> {

                            Log.e("error fetching pages",it.message.toString())

                            isLoading.value = false
                        }
                        is Resource.Loading -> {

                            isLoading.value =true

                        }
                        is Resource.Success -> {

                            val allVersesInOnePage = it.data

                            createListWithTextInEachPage(allVersesInOnePage,pageNumber)



                        }
                    }
                }

                pageNumber = pageNumber + 1
            }

            isLoading.value = false
        }
    }



    fun createListWithTextInEachPage(allVersesInOnePage:List<VersesItem>?,pageNumber:Int){

        var pageVersesCombined = ""

        if (allVersesInOnePage?.isNotEmpty() == true){

            kotlin.run lit@ {

                allVersesInOnePage.forEachIndexed { index, it ->

                    firstChapterName = getChapterName(it)

                    lastReadChapter = getLastReadChapter(it)!!


                    val verseNumber = it.verseKey?.substringAfter(":")


                    if (pageVersesCombined.isNotEmpty() && verseNumber?.toInt() == 1) {

                        firstChapterName = getChapterNameInFollowingChapter(it)

                        lastReadChapter = getLastReadChapterInFollowingChapter(it)!!

                        create2ndListWithTextInEachPage(index, allVersesInOnePage,pageNumber)


                        return@lit
                    } else {

                        pageVersesCombined =
                            pageVersesCombined + it.textUthmani + " (${verseNumber?.toInt()}) "
                    }

                }
            }

            pageVersesCombined = convertEnglishNumberToArabicNumbers(pageVersesCombined)

            textToBeDisplayedInEachPage.add(pageVersesCombined)
        }

    }

    fun create2ndListWithTextInEachPage(index:Int,allVersesInOnePage:List<VersesItem>?,pageNumber: Int){

        secondChapterPage = pageNumber

        var pageVersesCombined = ""

        for (i in index..<allVersesInOnePage?.size!!){

            val chapterNumber = allVersesInOnePage[i].verseKey?.substringBefore(":")

            secondChapterName = Constants.chapterNames[chapterNumber?.toInt()!!-1]

            val verseNumber = allVersesInOnePage[i].verseKey?.substringAfter(":")

            if (pageVersesCombined.isNotEmpty() && verseNumber?.toInt() == 1) {

                secondChapterName = Constants.chapterNames[chapterNumber?.toInt()!!-1-1]

                create3rdListWithTextInEachPage(i,allVersesInOnePage,pageNumber)

                break
            } else {

                pageVersesCombined =
                    pageVersesCombined + allVersesInOnePage[i].textUthmani + " (${verseNumber?.toInt()}) "
            }

        }

        pageVersesCombined = convertEnglishNumberToArabicNumbers(pageVersesCombined)


        secondChapter.add(pageVersesCombined)

    }

    fun create3rdListWithTextInEachPage(index:Int,allVersesInOnePage:List<VersesItem>?,pageNumber: Int){

        thirdChapterPage = pageNumber

        var pageVersesCombined = ""

        var indexOfList = 0

        for (i in index..<allVersesInOnePage?.size!!){

            val chapterNumber = allVersesInOnePage[i].verseKey?.substringBefore(":")

            thirdChapterName = Constants.chapterNames[chapterNumber?.toInt()!!-1]


            val verseNumber = allVersesInOnePage[i].verseKey?.substringAfter(":")

            if (pageVersesCombined.isNotEmpty() && verseNumber?.toInt() == 1) {

                indexOfList = index

                break
            } else {

                pageVersesCombined =
                    pageVersesCombined + allVersesInOnePage[i].textUthmani + " (${verseNumber?.toInt()}) "
            }

        }

        pageVersesCombined = convertEnglishNumberToArabicNumbers(pageVersesCombined)



        thirdChapter.add(pageVersesCombined)

    }

    fun getChapterName(versesItem: VersesItem):String{

        val chapterNumber = versesItem.verseKey?.substringBefore(":")?.toInt()!!

        return Constants.chapterNames[chapterNumber-1]
    }

    fun getLastReadChapter(versesItem: VersesItem):String?{

        return versesItem.verseKey?.substringBefore(":")
    }

    fun getChapterNameInFollowingChapter(versesItem: VersesItem):String{

        val chapterNumber = versesItem.verseKey?.substringBefore(":")?.toInt()!!

      return  Constants.chapterNames[chapterNumber -1-1]
    }

    fun getLastReadChapterInFollowingChapter(versesItem: VersesItem):String?{

        val chapterNumber = versesItem.verseKey?.substringBefore(":")?.toInt()!!

        return (chapterNumber-1).toString()
    }

    fun saveLastReadPage(context: Context){

        viewModelScope.launch (Dispatchers.IO){
           QuranDataStore(context).addLastReadPage("$lastReadChapter,$lastReadPage")
        }
    }

    fun getLastReadPage(context: Context){

        viewModelScope.launch (Dispatchers.IO) {

            val lastRead = QuranDataStore(context).readLastReadPage() ?:"1,1"

            lastReadPageInt = lastRead.substringAfter(",").toInt()
        }
    }


}

fun convertEnglishNumberToArabicNumbers(verses:String):String{

   val versesFinal = verses.replace("1","١")
        .replace("2","٢")
        .replace("3","٣")
        .replace("4","٤")
        .replace("5","٥")
        .replace("6","٦")
        .replace("7","٧")
        .replace("8","٨")
        .replace("9","٩")
        .replace("0","٠")

    return versesFinal
}
