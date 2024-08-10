package com.example.domain.repository

import com.example.domain.Resource
import com.example.domain.model.AudioFile
import com.example.domain.model.ChaptersItem
import com.example.domain.model.RecitationsItem
import com.example.domain.model.VersesItem
import kotlinx.coroutines.flow.Flow

interface QuranRepository {

    suspend fun getChapterList(): Flow<Resource<List<ChaptersItem?>?>>

    suspend fun getPages(pageNumber:Int): Flow<Resource<List<VersesItem>?>>

    suspend fun getRecitersList():Flow<Resource<List<RecitationsItem?>?>>

    suspend fun getChapterAudio(reciterId:Int,chapterId:Int):Flow<Resource<AudioFile?>>
}