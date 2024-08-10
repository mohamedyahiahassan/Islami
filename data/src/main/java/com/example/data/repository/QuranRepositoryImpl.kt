package com.example.data.repository

import com.example.data.dataSource.onlineDataSource.OnlineQuranRepositoryImpl
import com.example.domain.Resource
import com.example.domain.model.AudioFile
import com.example.domain.model.ChaptersItem
import com.example.domain.model.RecitationsItem
import com.example.domain.model.VersesItem
import com.example.domain.repository.QuranRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class QuranRepositoryImpl @Inject constructor(private val onlineQuranRepositoryImpl: OnlineQuranRepositoryImpl):QuranRepository {

    override suspend fun getChapterList(): Flow<Resource<List<ChaptersItem?>?>> {

       return onlineQuranRepositoryImpl.getChapterList()
    }

    override suspend fun getPages(pageNumber: Int): Flow<Resource<List<VersesItem>?>> {

        return onlineQuranRepositoryImpl.getPages(pageNumber)
    }

    override suspend fun getRecitersList(): Flow<Resource<List<RecitationsItem?>?>> {

        return onlineQuranRepositoryImpl.getRecitersList()
    }

    override suspend fun getChapterAudio(
        reciterId: Int,
        chapterId: Int
    ): Flow<Resource<AudioFile?>> {

        return onlineQuranRepositoryImpl.getChapterAudio(reciterId, chapterId)
    }
}