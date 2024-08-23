package com.k.sekiro.taskmanagementapp

import android.app.*
import android.content.Context
import android.os.*
import android.util.Log
import androidx.work.Configuration
import androidx.work.ListenableWorker
import androidx.work.Logger
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class TaskApp:Application(){



    companion object{
        const val  CHANNEL_ID = "alarmChannel"
        lateinit var manager: NotificationManager
    }


    override fun onCreate() {
        super.onCreate()
        createNotification()
    }

    private fun  createNotification(){
        val channel = NotificationChannel(CHANNEL_ID,"Alarm channel",NotificationManager.IMPORTANCE_HIGH)
        manager = getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(channel)
    }
}



