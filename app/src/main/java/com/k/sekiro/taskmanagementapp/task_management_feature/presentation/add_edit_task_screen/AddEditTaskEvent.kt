package com.k.sekiro.taskmanagementapp.task_management_feature.presentation.add_edit_task_screen

import androidx.compose.ui.focus.*
import com.k.sekiro.taskmanagementapp.task_management_feature.domain.model.*
import java.util.Calendar

sealed class AddEditTaskEvent {
    data class EnteredTitle(val title:String):AddEditTaskEvent()
    data class ChangeTitleFocus(val focusState: FocusState):AddEditTaskEvent()
    data class EnteredDescription(val description:String):AddEditTaskEvent()
    data class ChangeDescriptionFocus(val focusState: FocusState):AddEditTaskEvent()
    data class EnteredDate(val date:String,val dateLong: Long):AddEditTaskEvent()
    data class EnteredStartTime(val time:String,val calenderTime:Calendar):AddEditTaskEvent()
    data class EnteredEndTime(val time:String,val calenderTime: Calendar):AddEditTaskEvent()
    data class ChoosePriority(val priority: Priority):AddEditTaskEvent()
    data object SaveTask:AddEditTaskEvent()
    data object ResetField:AddEditTaskEvent()
    data object BackButton:AddEditTaskEvent()
}