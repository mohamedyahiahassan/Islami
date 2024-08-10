package com.example.data

import android.util.Log
import com.example.domain.BaseResponse
import com.example.domain.Resource
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response


suspend fun <T> safeApiCall(apiCall: suspend () -> Any?): Flow<Resource<T?>> = flow {

        emit(Resource.Loading())

    val result = try {

        val response = apiCall()

        Resource.Success(response as T?)


    }catch (e:Exception){

       Resource.Error(e.message.toString())

    }

    emit(result)
}