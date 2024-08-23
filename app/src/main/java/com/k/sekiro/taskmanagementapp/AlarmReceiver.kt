package com.k.sekiro.taskmanagementapp

import android.content.*
import android.os.Build
import android.util.*
import com.k.sekiro.taskmanagementapp.task_management_feature.domain.model.*
import com.k.sekiro.taskmanagementapp.task_management_feature.domain.repository.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import javax.inject.Inject

@AndroidEntryPoint
class AlarmReceiver : BroadcastReceiver() {

    @Inject
    lateinit var repository: Repository

    override fun onReceive(context: Context?, intent: Intent?) {

        val task = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            intent?.getParcelableExtra("task",Task::class.java)!!.copy(
                isEnteredTime = true
            )
        }else{
            intent?.getParcelableExtra<Task>("task")!!.copy(
                isEnteredTime = true
            )
        }


        Log.e("ks","Enter receiver")

        CoroutineScope(Dispatchers.IO).launch {
            repository.insertTask(task)

        }

        Log.e("ks","the task inside receiver: $task")

        val i = Intent(context!!,AlarmService::class.java).apply {
            putExtra("task",task)
            action = "start_service"
        }

        context.startForegroundService(i)

    }


}
