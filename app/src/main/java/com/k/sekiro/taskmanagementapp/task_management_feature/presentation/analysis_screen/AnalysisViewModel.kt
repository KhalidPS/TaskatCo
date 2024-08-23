package com.k.sekiro.taskmanagementapp.task_management_feature.presentation.analysis_screen

import android.util.*
import android.util.Log.e
import androidx.compose.runtime.*
import androidx.lifecycle.*
import com.k.sekiro.taskmanagementapp.task_management_feature.domain.model.*
import com.k.sekiro.taskmanagementapp.task_management_feature.domain.use_case.*
import com.k.sekiro.taskmanagementapp.task_management_feature.domain.utils.*
import dagger.hilt.android.lifecycle.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.*

@HiltViewModel
class AnalysisViewModel @Inject constructor(
    private val useCases: TaskUseCases
):ViewModel() {

    var job:Job? = null

    init {
        getTodayTasks()
    }

    private val _todayTasks = mutableStateOf(emptyList<Task>())
    val todayTasks:State<List<Task>> = _todayTasks

    private fun getTodayTasks(){
        job?.cancel()
        job = useCases.getTasks(TaskOrder.Today(OrderType.Ascending)).onEach {
            try {
                _todayTasks.value = it
            }catch (e:NullPointerException){
                e("ks","${e.message} at a Analysis viewModel")
            }catch (e:Exception){
                Log.e("ks","${e.message} at a Analysis viewModel")
            }

        }.launchIn(viewModelScope)
    }
}


