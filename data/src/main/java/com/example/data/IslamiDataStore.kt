package com.example.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

val Context.quranDataStore: DataStore<Preferences> by preferencesDataStore(name = "quran")

class  QuranDataStore(context: Context){

    val dataStore = context.quranDataStore

    suspend fun addLastReadPage(value:String){

        val lastReadPage = stringPreferencesKey("last_page")

        dataStore.edit { quran->

            quran[lastReadPage] = value
        }

    }

    suspend fun readLastReadPage():String?{

        val lastReadPage =  stringPreferencesKey("last_page")

       val preferences = dataStore.data.first()

        return preferences[lastReadPage]

    }


}