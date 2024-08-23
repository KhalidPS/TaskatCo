package com.k.sekiro.taskmanagementapp.task_management_feature.presentation.utils

sealed class Screens (val route:String){
    object Tasks: Screens("Home")
    object Analysis: Screens("Chart")
    object AddEdit:Screens("AddEdit")
}