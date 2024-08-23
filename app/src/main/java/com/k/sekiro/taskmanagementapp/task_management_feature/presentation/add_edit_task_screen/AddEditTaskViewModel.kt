package com.k.sekiro.taskmanagementapp.task_management_feature.presentation.add_edit_task_screen

import android.content.Context
import android.util.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.runtime.*
import androidx.core.content.ContextCompat
import androidx.lifecycle.*
import com.k.sekiro.taskmanagementapp.task_management_feature.domain.alarm.*
import com.k.sekiro.taskmanagementapp.task_management_feature.domain.converters.*
import com.k.sekiro.taskmanagementapp.task_management_feature.domain.model.*
import com.k.sekiro.taskmanagementapp.task_management_feature.domain.use_case.*
import com.k.sekiro.taskmanagementapp.task_management_feature.domain.utils.*
import com.k.sekiro.taskmanagementapp.task_management_feature.presentation.add_edit_task_screen.component.*
import dagger.hilt.android.lifecycle.*
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.text.*
import java.time.*
import java.util.*
import javax.inject.*
import com.k.sekiro.taskmanagementapp.R
import com.k.sekiro.taskmanagementapp.task_management_feature.presentation.MainActivity
import dagger.hilt.android.qualifiers.ActivityContext

@HiltViewModel
class AddEditTaskViewModel @Inject constructor(
    private val useCases: TaskUseCases,
    savedStateHandle: SavedStateHandle,
    @ApplicationContext private val context: Context,
) :ViewModel() {


    private var currentTaskId:Int = 0

    @Inject lateinit var alarmScheduler: AlarmScheduler



    private val _taskTitle = mutableStateOf(TaskTextFieldState(
        hint = ContextCompat.getContextForLanguage(context).getString(R.string.enter_title_hint)
    /*if(Locale.getDefault().language == "ar"){
            "أدخل عنوان"
        }else {
            "Enter title"
        }*/
    ))

    val taskTitle:State<TaskTextFieldState> = _taskTitle


    private val _taskDescription = mutableStateOf(TaskTextFieldState(
        hint = ContextCompat.getContextForLanguage(context).getString(R.string.enter_description_hint)
    /*if(Locale.getDefault().language == "ar"){
            "أدخل وصف"
        }else {
            "Enter description"
        }*/
    ))
    val taskDescription:State<TaskTextFieldState> = _taskDescription


    private val _taskDate = mutableStateOf(TaskTextState(
        text = SimpleDateFormat("dd-MM-yyyy",Locale.getDefault())
            .format(Calendar.getInstance().time)
    ))
    val taskDate:State<TaskTextState> = _taskDate

    private val _taskStartTime = mutableStateOf(TaskTextState(
        text = SimpleDateFormat("hh:mm a",Locale.getDefault()).format(Date())
    //LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm a"))
    ))
    val taskStartTime:State<TaskTextState> = _taskStartTime

    private val _taskEndTime = mutableStateOf(TaskTextState(
        text = Calendar.getInstance().apply {
            set(Calendar.HOUR,this.get(Calendar.HOUR_OF_DAY) + 2)
            set(Calendar.MINUTE,this.get(Calendar.MINUTE))
            set(Calendar.AM_PM,this.get(this.get(Calendar.AM_PM)))
        }.time.let {
            SimpleDateFormat("hh:mm a", Locale.getDefault()).format(it)
        }
    ))
    val taskEndTime:State<TaskTextState> = _taskEndTime


    private val _taskPriority = mutableStateOf(PriorityMenuState())
    val taskPriority:State<PriorityMenuState> = _taskPriority

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow:SharedFlow<UiEvent> = _eventFlow

    private val _restButtonEnabled = derivedStateOf {  taskTitle.value.text.isNotBlank()||  taskDescription.value.text.isNotBlank()}
    val restButtonEnabled:State<Boolean> = _restButtonEnabled

    private val _pickerInitialDate = mutableLongStateOf(
        Instant.now().toEpochMilli()
    )
    val pickerInitialDate:State<Long> = _pickerInitialDate

    private val _calenderStartTime = mutableStateOf(
        Calendar.getInstance()
    )
    val calenderStartTime:State<Calendar> = _calenderStartTime

    private val _calenderEndTime = mutableStateOf(
        Calendar.getInstance().apply {
            set(Calendar.HOUR,this.get(Calendar.HOUR_OF_DAY) + 2)
            set(Calendar.MINUTE,this.get(Calendar.MINUTE))
            set(Calendar.AM_PM,this.get(this.get(Calendar.AM_PM)))
        }
    )
    val calenderEndTime:State<Calendar> = _calenderEndTime






    init {

        savedStateHandle.get<Int>("taskId")?.let { taskId ->
            if (taskId != -1){
                viewModelScope.launch {
                    useCases.getTask(taskId)?.also { task ->
                        currentTaskId = task.id

                        Log.e("ks",task.toString())

                        _taskTitle.value = taskTitle.value.copy(
                            text = task.title,
                            isHintVisible = false,
                            enabled = !task.isEnteredTime
                        )

                        _taskDescription.value = taskDescription.value.copy(
                            text = task.description,
                            isHintVisible = false,
                            enabled = !task.isEnteredTime
                        )

                        _taskDate.value = TaskTextState(
                            text = task.getFormattedDate(),
                            clickable = !task.isEnteredTime,
                            enabled = !task.isEnteredTime
                        )

                        _taskStartTime.value = TaskTextState(
                            text = task.getFormattedStartTime(),
                            clickable = !task.isEnteredTime,
                            enabled = !task.isEnteredTime
                        )

                        _taskEndTime.value = TaskTextState(
                            text = task.getFormattedEndTime(),
                            clickable = !task.isEnteredTime,
                            enabled = !task.isEnteredTime
                        )

                        _taskPriority.value = PriorityMenuState(
                            priority = when(task.priority){
                                Priority.NORMAL.value -> Priority.NORMAL
                                Priority.HIGH.value -> Priority.HIGH
                                Priority.LOW.value -> Priority.LOW
                                else -> {Priority.NORMAL}
                            },
                            enabled = !task.isEnteredTime
                        )


                        _calenderStartTime.value = LocalTimeConverter.toCalender(task.startTime)

                        _calenderEndTime.value = LocalTimeConverter.toCalender(task.endTime)

                        _pickerInitialDate.longValue = LocalDateConverter.toCalender(task.date).timeInMillis

                    }
                }
            }
        }
    }








    fun onEvent(event:AddEditTaskEvent){
        when(event){
            is AddEditTaskEvent.EnteredTitle ->{
                _taskTitle.value = taskTitle.value.copy(
                    text = event.title
                )
            }

            is AddEditTaskEvent.ChangeTitleFocus ->{
                _taskTitle.value = taskTitle.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            taskTitle.value.text.isBlank(),
                    isFocused = event.focusState.isFocused
                )
            }

            is AddEditTaskEvent.EnteredDescription ->{
                _taskDescription.value = taskDescription.value.copy(
                    text = event.description
                )
            }

            is AddEditTaskEvent.ChangeDescriptionFocus ->{
                _taskDescription.value = taskDescription.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                    taskDescription.value.text.isBlank(),
                    isFocused = event.focusState.isFocused
                )
            }

            is AddEditTaskEvent.EnteredDate ->{
                val validateResult = useCases.validateDate(event.dateLong)
                if (validateResult.isNotBlank()){
                    viewModelScope.launch {
                        _eventFlow.emit(
                            UiEvent.ShowSnackBar(
                                validateResult
                            )
                        )
                    }
                }else {
                    _taskDate.value = taskDate.value.copy(
                        text = event.date
                    )
                    _pickerInitialDate.longValue = event.dateLong
                }

            }





            is AddEditTaskEvent.EnteredStartTime ->{
                val validateResult = useCases.validateTime(
                    time = event.time,
                    calender = event.calenderTime,
                    selectedDate = taskDate.value.text,
                    taskEndTime = calenderEndTime.value,
                    taskStartTime = calenderStartTime.value,
                    timeType = 1
                )


                if (!validateResult.isValid && validateResult.msg.isNotBlank()){
                    viewModelScope.launch {
                        _eventFlow.emit(
                            UiEvent.ShowSnackBar(validateResult.msg)
                        )
                    }
                }else {
                    _taskStartTime.value = taskStartTime.value.copy(
                        text = event.time
                    )

                    _calenderStartTime.value = event.calenderTime
                }
            }

            is AddEditTaskEvent.EnteredEndTime ->{
                val validateResult = useCases.validateTime(
                    time = event.time,
                    calender = event.calenderTime,
                    selectedDate = taskDate.value.text,
                    taskEndTime = calenderEndTime.value,
                    taskStartTime = calenderStartTime.value,
                    timeType = 2
                )


                if (!validateResult.isValid && validateResult.msg.isNotBlank()){
                    viewModelScope.launch {
                        _eventFlow.emit(
                            UiEvent.ShowSnackBar(validateResult.msg)
                        )
                    }
                }else {
                    _taskEndTime.value = taskEndTime.value.copy(
                        text = event.time
                    )
                    _calenderEndTime.value =  event.calenderTime
                }
            }

            is AddEditTaskEvent.ChoosePriority ->{
                _taskPriority.value = taskPriority.value.copy(
                    priority = event.priority
                )
            }

            is AddEditTaskEvent.BackButton ->{
                viewModelScope.launch {
                    _eventFlow.emit(UiEvent.BackButton)
                }
            }

            is AddEditTaskEvent.ResetField ->{
                _taskTitle.value = taskTitle.value.copy(
                    text = "",
                    isHintVisible = taskTitle.value.text.isBlank() || !taskTitle.value.isFocused
                )
                _taskDescription.value = taskDescription.value.copy(
                    text = "",
                    isHintVisible = taskDescription.value.text.isBlank() || !taskDescription.value.isFocused
                )
                _taskDate.value = taskDate.value.copy(
                    text = SimpleDateFormat("dd-MM-yyyy",Locale.getDefault())
                        .format(Calendar.getInstance().time)
                )

                _taskStartTime.value = taskStartTime.value.copy(
                    text = SimpleDateFormat("hh:mm a",Locale.getDefault()).format(Date())
                )
                _taskEndTime.value = taskEndTime.value.copy(
                    text = Calendar.getInstance().apply {
                        set(Calendar.HOUR,this.get(Calendar.HOUR_OF_DAY) + 2)
                        set(Calendar.MINUTE,this.get(Calendar.MINUTE))
                        set(Calendar.AM_PM,this.get(this.get(Calendar.AM_PM)))
                    }.time.let {
                        SimpleDateFormat("hh:mm a", Locale.getDefault()).format(it)
                    }
                )
                _taskPriority.value = taskPriority.value.copy(
                    priority = Priority.NORMAL
                )
            }


            is AddEditTaskEvent.SaveTask ->{
                viewModelScope.launch {
                    try {
                        val task = Task(
                            title = taskTitle.value.text,
                            description = taskDescription.value.text,
                            date = LocalDateConverter.fromCalender(LocalDateConverter.millisToCalender(pickerInitialDate.value)),
                            startTime = LocalTimeConverter.fromCalender(calenderStartTime.value),
                            endTime = LocalTimeConverter.fromCalender(calenderEndTime.value),
                            priority = taskPriority.value.priority.value,
                            isEnteredTime = false,
                            id = currentTaskId
                        )

                        useCases.addTask(
                            task
                        )
                        _eventFlow.emit(UiEvent.SaveButton)

                        if (currentTaskId != 0){
                            Log.e("ks","the current task id  :$currentTaskId")
                            useCases.scheduleReminder.schedule(task)
                        }else{
                            val id = useCases.getTaskId(task.startTime.toString(),task.date.toString())
                            Log.e("ks","the id in else  :$id")

                            useCases.scheduleReminder.schedule(task.copy(id = id))

                        }


                    }catch (e:InvalidTaskException){
                        _eventFlow.emit(
                            UiEvent.ShowSnackBar(
                                message = e.message
                            )
                        )
                    }
                }
            }

        }
    }


    sealed class UiEvent{
        data class ShowSnackBar(val message:String):UiEvent()
        object SaveButton:UiEvent()
        object BackButton:UiEvent()
    }


//Alarm Manager after add task
// disabled / enabled for field when task entered time
// find solution if a completed task with high priority is unchecked and there's a task has been added with high priority before unchecked the completed one



}