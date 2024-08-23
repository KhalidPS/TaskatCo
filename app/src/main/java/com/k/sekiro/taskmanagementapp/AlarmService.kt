package com.k.sekiro.taskmanagementapp

import android.app.*
import android.content.*
import android.media.*
import android.os.*
import android.util.*
import androidx.compose.runtime.*
import androidx.compose.ui.res.*
import androidx.core.app.*
import androidx.datastore.core.*
import androidx.datastore.preferences.core.*
import com.k.sekiro.taskmanagementapp.task_management_feature.domain.model.*
import com.k.sekiro.taskmanagementapp.task_management_feature.domain.repository.*
import dagger.hilt.android.*
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.time.Duration
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.*

@AndroidEntryPoint
class AlarmService:Service() {
    private lateinit var player: MediaPlayer

    @Inject lateinit var notify:NotificationCompat.Builder

    @Inject lateinit var dataStore:DataStore<Preferences>

    @Inject lateinit var repository:Repository

    lateinit var countDownTimer: CountDownTimer

    override fun onBind(p0: Intent?): IBinder? {
        return AlarmBinder()
    }


    var zero = ""

    var timeValue = mutableStateOf("$zero$zero:$zero$zero:$zero$zero")
        private set

    var taskId = mutableStateOf(0)
        private set

    override fun onCreate() {
        super.onCreate()
        zero = String.format(getString(R.string.numberRes),0)
        timeValue.value = "$zero$zero:$zero$zero:$zero$zero"
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        val task = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            intent?.getParcelableExtra("task", Task::class.java)?:Task()
        }else{
            intent?.getParcelableExtra<Task>("task")?:Task()
        }

        Log.e("ks","task in service" +
                "$task")

        taskId.value = task.id


        when(intent?.action){

            "stop_reminder" -> {
                player.stop()
                CoroutineScope(Dispatchers.IO).launch{
                    dataStore.data.first().also { preferences ->

                        taskId.value =  preferences[intPreferencesKey("taskId")]?:0
                    }

                    withContext(Dispatchers.Main){
                        removeStopBtn()
                    }

                }


            }

            "stop_service" -> {
                player.stop()
                countDownTimer.cancel()
                stopForeground(STOP_FOREGROUND_REMOVE)
                stopSelf()
            }

            "start_service" -> start(true,task){ value ->




                    notify.setContentTitle(value)
                        .setContentText("\"${task.title}\" " + getString(R.string.notification_msg))


                    TaskApp.manager.notify(taskId.value,notify.build())
                //}


            }

            "restart_service" ->{
                notify.clearActions()

                start(false,task){ value ->

                    notify.setContentTitle(value)
                        .setContentText("\"${task.title}\" " + getString(R.string.notification_msg))


                    TaskApp.manager.notify(taskId.value,notify.build())



                }
            }
        }




        return START_REDELIVER_INTENT
    }


    override fun onDestroy() {
        super.onDestroy()
        //player.stop()

    }

    private fun start(isPlay: Boolean, task: Task, onTick:(String) -> Unit){
        if (isPlay){
            player = MediaPlayer.create(this,R.raw.ots_l)
            player.isLooping = true
            player.start()

            Log.e("ks","inside isplay block")


            val intentForActionBtn = Intent(this,AlarmService::class.java).apply {
                action = "stop_reminder"
            }

            val pendingIntentForAction = PendingIntent.getForegroundService(this,1,intentForActionBtn,PendingIntent.FLAG_IMMUTABLE)



            notify.addAction(task.id,"Stop Reminder",pendingIntentForAction)


        }



        var isFirstTime = true

        val period = Duration.between(task.startTime,task.endTime)
/*        val hour = period.toHours()
        val minutes = period.toMinutes()
        Log.e("ks","hour:$hour , minutes:$minutes , seconds:${period.seconds}")*/
        val timeInMillis = period.seconds * 1000
        CoroutineScope(Dispatchers.IO).launch {

            dataStore.edit { preferences ->
                preferences[intPreferencesKey("taskId")] = task.id
            }
        }

                countDownTimer = object : CountDownTimer(timeInMillis,1000L) {
                    override fun onTick(millis: Long) {

                        val time = String.format(
                            Locale.getDefault(),"%02d:%02d:%02d",
                            TimeUnit.MILLISECONDS.toHours(millis),
                            TimeUnit.MILLISECONDS.toMinutes(millis)%60,
                            TimeUnit.MILLISECONDS.toSeconds(millis)%60
                        )
                        if (isFirstTime){
                            startForeground(task.id,notify.build())
                            isFirstTime = false
                        }
                        timeValue.value = time
                        onTick(time)

                    }

                    override fun onFinish() {
                        Log.e("ks","inside onFinish")
                        //here make same scope
                        CoroutineScope(Dispatchers.IO).launch {
                            repository.insertTask(task.copy(isTimerFinished = true))
                            withContext(Dispatchers.Main){
                                player.stop()
                                stopForeground(STOP_FOREGROUND_REMOVE)
                                stopSelf()
                            }
                        }

                       /* stopForeground(STOP_FOREGROUND_REMOVE)
                        player.stop()*/
                    }

                }.start()
    }






    inner class AlarmBinder:Binder(){
        fun getService():AlarmService = this@AlarmService
    }

    private fun removeStopBtn(){
        notify.clearActions()
        startForeground(taskId.value,notify.build())
    }

    private fun getFormattedTime(i:Long):String{
        if (i > 60) {
            val hour = i / 60
            val minutes = i % 60
            if (minutes >= 10) {
                return "$hour:$minutes"
            } else {
                return  "$hour:0$minutes"
            }
        } else {
            return i.toString()
        }
    }
}
