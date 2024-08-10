package com.example.data

import com.example.domain.BaseResponse
import com.example.domain.Resource
import com.example.domain.model.AudioFileResponse
import com.example.domain.model.ChaptersItem
import com.example.domain.model.ChaptersListResponse
import com.example.domain.model.PagesResponse
import com.example.domain.model.RecitersResponse
import kotlinx.coroutines.flow.Flow
import org.intellij.lang.annotations.Language
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WebServices {

    @GET("chapters")
    suspend fun getChaptersList(): ChaptersListResponse

    @GET("quran/verses/uthmani")
    suspend fun getPages(

        @Query ("page_number") pageNumber:Int
    ): PagesResponse


    @GET("resources/recitations")
    suspend fun getReciters(

        @Query("language") language:String
    ):RecitersResponse

    @GET("chapter_recitations/{id}/{chapter_number}")
    suspend fun getChapterAudio(
        @Path("id")reciterId:Int,
        @Path("chapter_number")chapterId:Int
    ):AudioFileResponse
}