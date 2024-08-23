package com.k.sekiro.taskmanagementapp.task_management_feature.data.database.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.k.sekiro.taskmanagementapp.task_management_feature.domain.model.Task


@Database(entities = [Task::class], version = 1)
abstract class AppDatabase :RoomDatabase(){
    abstract val taskDao:TaskDao
    //abstract fun taskDao():TaskDao

    companion object{
        const val  DATABASE_NAME = "app_database"
    }

}
