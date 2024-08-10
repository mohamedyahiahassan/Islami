package com.example.data.dataSource.onlineDataSource

import android.util.Log
import com.example.data.WebServices
import com.example.data.safeApiCall
import com.example.domain.Resource
import com.example.domain.dataSource.onlineDataSource.OnlineQuranRepository
import com.example.domain.model.AudioFile
import com.example.domain.model.ChaptersItem
import com.example.domain.model.RecitationsItem
import com.example.domain.model.VersesItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class OnlineQuranRepositoryImpl @Inject constructor( private val webServices: WebServices): OnlineQuranRepository {

    override suspend fun getChapterList(): Flow<Resource<List<ChaptersItem?>?>> =

        safeApiCall {
            webServices.getChaptersList().chapters
        }

    override suspend fun getPages(pageNumber:Int): Flow<Resource<List<VersesItem>?>>  =

        safeApiCall {

            webServices.getPages(pageNumber).verses
        }

    override suspend fun getRecitersList(): Flow<Resource<List<RecitationsItem?>?>> =

        safeApiCall {
            webServices.getReciters("en").recitations
        }

    override suspend fun getChapterAudio(reciterId:Int, chapterId:Int):Flow<Resource<AudioFile?>> =

        safeApiCall {

            webServices.getChapterAudio(reciterId, chapterId).audioFile
        }

}