package com.k.sekiro.taskmanagementapp.task_management_feature.domain.utils

sealed class TaskOrder(val orderType: OrderType){
    class Date(orderType: OrderType):TaskOrder(orderType)
    class Time(orderType: OrderType):TaskOrder(orderType)

    //descending -> high-low // ascending -> low-high
    class Priority(orderType: OrderType):TaskOrder(orderType)

    //class PriorityForToday(orderType: OrderType):TaskOrder(orderType)


    class Default(orderType: OrderType):TaskOrder(orderType)

    // descending // ascending // pended
    class Today(orderType: OrderType,val pendedHighPriority:Boolean = true):TaskOrder(orderType)


    //class All(orderType: OrderType):TaskOrder(orderType)


    fun copy(orderType: OrderType):TaskOrder{
        return when(this){
            is Time-> Time(orderType)
            is Date -> Date(orderType)
            is Today -> Today(orderType,pendedHighPriority)
            is Priority -> Priority(orderType)
            is Default -> Default(orderType)
        }
    }


}