package com.k.sekiro.taskmanagementapp.di

import android.app.*
import android.content.*
import androidx.core.app.*
import androidx.core.content.ContextCompat
import androidx.datastore.core.*
import androidx.datastore.preferences.*
import androidx.datastore.preferences.core.*
import androidx.room.*
import com.k.sekiro.taskmanagementapp.*
import com.k.sekiro.taskmanagementapp.R
import com.k.sekiro.taskmanagementapp.task_management_feature.data.alarm.*
import com.k.sekiro.taskmanagementapp.task_management_feature.data.database.local.*
import com.k.sekiro.taskmanagementapp.task_management_feature.data.repository.*
import com.k.sekiro.taskmanagementapp.task_management_feature.domain.alarm.*
import com.k.sekiro.taskmanagementapp.task_management_feature.domain.repository.*
import com.k.sekiro.taskmanagementapp.task_management_feature.domain.use_case.*
import com.k.sekiro.taskmanagementapp.task_management_feature.presentation.*
import dagger.*
import dagger.hilt.*
import dagger.hilt.android.qualifiers.*
import dagger.hilt.components.*
import javax.inject.*
import kotlin.reflect.*

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideDatabase(app: Application): AppDatabase =
        Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        ).build()


    @Singleton
    @Provides
    fun provideRepository(database: AppDatabase): Repository {
        return RepositoryImpl(database.taskDao)
    }


    @Singleton
    @Provides
    fun provideTaskUseCase(
        repository: Repository,alarmScheduler: AlarmScheduler,@ApplicationContext context: Context
    ): TaskUseCases {
        return TaskUseCases(
            addTask = AddTask(repository,context),
            addTasks = AddTasks(repository),
            getTask = GetTask(repository),
            getTasks = GetTasks(repository),
            deleteTask = DeleteTask(repository),
            deleteTasks = DeleteTasks(repository),
            validateTime = ValidateTime(context),
            validateDate = ValidateDate(context),
            getTaskId = GetTaskId(repository),
            scheduleReminder = ScheduleReminder(alarmScheduler)
        )
    }

    @Singleton
    @Provides
    fun provideAlarmManager(@ApplicationContext context:Context):AlarmScheduler{
        return TaskAlarmScheduler(context)
    }


    @Singleton
    @Provides
    fun provideDataStore(@ApplicationContext context: Context):DataStore<Preferences>{
        return PreferenceDataStoreFactory.create {
                context.preferencesDataStoreFile("${context.cacheDir}/taskmanagmentapp.preferences_pb")
            }
        }


    @Singleton
    @Provides
    fun provideNotification(@ApplicationContext context: Context):NotificationCompat.Builder{



        val i = Intent(context, MainActivity::class.java)

        val pendingIntent = PendingIntent.getActivity(context,0,i, PendingIntent.FLAG_IMMUTABLE)



        return NotificationCompat.Builder(context, TaskApp.CHANNEL_ID)
            .setSmallIcon(R.drawable.logo2_light_0_)
            .setOnlyAlertOnce(true)
            .setAutoCancel(false)
            .setOngoing(true)
            .setContentIntent(pendingIntent)

    }


    }








