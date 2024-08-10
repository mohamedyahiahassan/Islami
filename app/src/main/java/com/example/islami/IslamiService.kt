package com.example.islami


import android.app.Notification
import android.app.Service
import android.content.Intent
import android.content.pm.ServiceInfo.FOREGROUND_SERVICE_TYPE_MEDIA_PLAYBACK
import android.content.pm.ServiceInfo.FOREGROUND_SERVICE_TYPE_SHORT_SERVICE
import android.media.MediaSession2Service
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi


class IslamiService:Service() {

    override fun onBind(intent: Intent?): IBinder? {

        return null
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        when(intent?.action){

            Actions.START.toString() -> start()
            Actions.Pause.toString() -> pause()
            Actions.STOP.toString() -> stop()

        }
        return super.onStartCommand(intent, flags, startId)
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    fun start(){

        val notification = Notification
            .Builder(this,"group_1")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Run is active")
            .setContentText("Elapsed time")
            .build()



        startForeground(1,notification,FOREGROUND_SERVICE_TYPE_MEDIA_PLAYBACK)
    }



    fun  pause(){


    }

    fun stop(){

    }

    enum class Actions {


        START, Pause ,STOP

    }
}



