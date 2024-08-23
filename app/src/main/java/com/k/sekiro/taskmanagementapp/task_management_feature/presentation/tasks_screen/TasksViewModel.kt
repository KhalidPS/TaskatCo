package com.k.sekiro.taskmanagementapp.task_management_feature.presentation.tasks_screen

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.runtime.*
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.lifecycle.*
import com.k.sekiro.taskmanagementapp.AlarmService
import com.k.sekiro.taskmanagementapp.task_management_feature.domain.model.*
import com.k.sekiro.taskmanagementapp.task_management_feature.domain.use_case.TaskUseCases
import com.k.sekiro.taskmanagementapp.task_management_feature.domain.utils.*
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import com.k.sekiro.taskmanagementapp.R
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    private val useCases: TaskUseCases,
    private val dataStore: DataStore<Preferences>,
     @ApplicationContext private val  context: Context
) : ViewModel() {

    private val _state = mutableStateOf(TasksState())
    val state:State<TasksState> = _state

    private val _uiEvent = MutableSharedFlow<TaskScreenUiEvent>()
    val uiEvent:SharedFlow<TaskScreenUiEvent> = _uiEvent

    private var job:Job? = null

    private var recentlyDeletedTask: Task? = null


    init {
        getTodayTasks()

        getTasks(TaskOrder.Default(OrderType.Ascending))
    }




    fun onEvent(event: TasksEvent){
        when(event){
            is TasksEvent.Filter -> {
                    if (event.taskOrder is TaskOrder.Today && state.value.taskOrder is TaskOrder.Today && event.taskOrder.pendedHighPriority != (state.value.taskOrder as TaskOrder.Today).pendedHighPriority){
                        getTasks(event.taskOrder)
                    }
                    if (state.value.taskOrder::class == event.taskOrder::class &&
                        state.value.taskOrder.orderType::class == event.taskOrder.orderType::class){
                        return
               /*         if (state.value.taskOrder.orderType == null && event.taskOrder.orderType == null){
                            return
                        }else if (state.value.taskOrder.orderType!!::class == event.taskOrder.orderType!!::class){
                            return
                        }*/
                }
                getTasks(event.taskOrder)
            }

            is TasksEvent.DeleteTask -> {
                recentlyDeletedTask = event.task.copy(
                    isRestored = true,
                    id = 0
                )
                viewModelScope.launch {

                    useCases.deleteTask(task = event.task)
                    if (!event.task.isEnteredTime){
                        useCases.scheduleReminder.cancel(event.task)

                    }else {

                        val prefId = dataStore.data.first()

                        Log.e("ks","prefId: ${prefId[intPreferencesKey("taskId")]}")
                        Log.e("ks","task id: ${event.task.id}")
                            if (prefId[intPreferencesKey("taskId")] == event.task.id && !event.task.isTimerFinished){
                                Log.e("ks","before intent")
                                context.startForegroundService(
                                    Intent(context, AlarmService::class.java).apply {
                                        action = "stop_service"
                                    }
                                )
                                Log.e("ks","after intent")
                            }

                    }

                    _uiEvent.emit(TaskScreenUiEvent.ShowSnackBar("${event.task.title} ${context.getString(R.string.deleted_task_msg)}"))
                }

            }

            is TasksEvent.RestoreTask -> {
                viewModelScope.launch {
                    useCases.addTask(recentlyDeletedTask?: return@launch)
                    val id = useCases.getTaskId(recentlyDeletedTask!!.startTime.toString(),recentlyDeletedTask!!.date.toString())
                    if (!recentlyDeletedTask!!.isEnteredTime){
                        useCases.scheduleReminder.schedule(recentlyDeletedTask!!.copy(id = id))
                    }else if(recentlyDeletedTask!!.isEnteredTime && !recentlyDeletedTask!!.isTimerFinished && !recentlyDeletedTask!!.isCompleted){
                        context.startService(
                            Intent(context, AlarmService::class.java).apply {
                                putExtra("task",recentlyDeletedTask!!.copy(id = id, startTime = LocalTime.now()))
                                action = "restart_service"
                            }
                        )
                    }

                }
            }

            is TasksEvent.Checked -> {
                viewModelScope.launch {
                    useCases.addTask(event.task)
                }
                if (event.task.isEnteredTime && event.task.isCompleted && !event.task.isTimerFinished){
                    context.startForegroundService(
                        Intent(context,AlarmService::class.java).apply {
                            action = "stop_service"
                        }
                    )
                }else if (event.task.isEnteredTime && !event.task.isCompleted && !event.task.isTimerFinished){
                    context.startService(
                        Intent(context,AlarmService::class.java).apply {
                            putExtra("task",event.task.copy(startTime = LocalTime.now()))
                            action = "restart_service"
                        }
                    )
                }
            }

            is TasksEvent.ToggleFilterSection -> {
                _state.value = state.value.copy(
                    isOrderSectionVisible = !state.value.isOrderSectionVisible
                )
            }
        }
    }


    private fun getTasks(taskOrder: TaskOrder){
        job?.cancel()
        job = useCases.getTasks(taskOrder)
            .onEach { tasks ->
            _state.value = state.value.copy(
                tasks = tasks,
                taskOrder = taskOrder
            )
        }.launchIn(
            viewModelScope
        )
    }

    private fun getTodayTasks(){
        useCases.getTasks(TaskOrder.Today(OrderType.Ascending)).onEach {
           _state.value =  state.value.copy(
                todayTasks = it
            )
        }.launchIn(viewModelScope)
    }

}


 sealed class TaskScreenUiEvent{
    data class ShowSnackBar(val msg:String):TaskScreenUiEvent()
}

