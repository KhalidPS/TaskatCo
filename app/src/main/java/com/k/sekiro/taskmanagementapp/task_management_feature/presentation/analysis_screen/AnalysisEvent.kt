package com.k.sekiro.taskmanagementapp.task_management_feature.presentation.analysis_screen

sealed class AnalysisEvent {
    object GetTodayTasks:AnalysisEvent()
}