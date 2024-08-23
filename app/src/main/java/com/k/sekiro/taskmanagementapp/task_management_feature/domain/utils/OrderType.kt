package com.k.sekiro.taskmanagementapp.task_management_feature.domain.utils

sealed class OrderType {
    object Ascending:OrderType()
    object Descending:OrderType()
}