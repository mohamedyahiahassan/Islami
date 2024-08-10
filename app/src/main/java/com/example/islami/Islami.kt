package com.example.islami

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class Islami:Application() {

    override fun onCreate() {
        super.onCreate()

        val channel = NotificationChannel(

            "group_1",
            "Reciting Quran",
            NotificationManager.IMPORTANCE_NONE
        )

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


        notificationManager.createNotificationChannel(channel)




    }
}