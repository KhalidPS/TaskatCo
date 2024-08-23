package com.k.sekiro.taskmanagementapp.task_management_feature.data.alarm

import android.annotation.*
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.*
import android.util.*
import com.k.sekiro.taskmanagementapp.*
import com.k.sekiro.taskmanagementapp.task_management_feature.domain.alarm.*
import com.k.sekiro.taskmanagementapp.task_management_feature.domain.converters.*
import com.k.sekiro.taskmanagementapp.task_management_feature.domain.model.*
import java.util.Calendar

class TaskAlarmScheduler(
    private val context: Context
) :AlarmScheduler {

    private val alarmManager = context.getSystemService(AlarmManager::class.java)




    override fun scheduler(task: Task) {
        val intent = Intent(context,AlarmReceiver::class.java).apply {

            putExtra("task",task)

/*            putExtra("title",task.title)
            putExtra("description",task.description)
            putExtra("startTime",task.startTime)
            putExtra("endTime",task.endTime)
            putExtra("date",task.date)
            putExtra("id",task.id)
            putExtra("priority",task.priority)*/
        }

        Log.e("ks","before setExact ")
        val timeForDate = LocalDateConverter.toCalender(task.date).apply {
            set(Calendar.HOUR_OF_DAY,task.startTime.hour)
            set(Calendar.MINUTE,task.startTime.minute)
            set(Calendar.SECOND,0)
        }
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            timeForDate.timeInMillis,
            PendingIntent.getBroadcast(
                context,
                task.id.hashCode(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }

    override fun cancel(task: Task) {
        alarmManager.cancel(
            PendingIntent.getBroadcast(
            context,
            task.id.hashCode(),
            Intent(context,AlarmReceiver::class.java),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        ))
    }
}

