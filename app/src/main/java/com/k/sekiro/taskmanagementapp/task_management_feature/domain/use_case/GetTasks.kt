package com.k.sekiro.taskmanagementapp.task_management_feature.domain.use_case

import com.k.sekiro.taskmanagementapp.task_management_feature.domain.model.Task
import com.k.sekiro.taskmanagementapp.task_management_feature.domain.repository.Repository
import com.k.sekiro.taskmanagementapp.task_management_feature.domain.utils.OrderType
import com.k.sekiro.taskmanagementapp.task_management_feature.domain.utils.TaskOrder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate

class GetTasks(private val repository: Repository) {

    operator fun invoke(taskOrder: TaskOrder = TaskOrder.Default(OrderType.Ascending)): Flow<List<Task>> {
        return repository.getAllTasks().map { tasks ->
            when (taskOrder.orderType) {
                is OrderType.Ascending -> {
                    when (taskOrder) {
                        is TaskOrder.Date -> tasks.sortedBy { it.date }
                        is TaskOrder.Priority -> tasks.sortedBy { it.priority }
                        //is TaskOrder.All -> tasks
                        is TaskOrder.Default -> {
                            val list = tasks.filter { it.date == LocalDate.now() }
                                .sortedBy { it.startTime }.toMutableList()
                            if (list.size > 1){
                                val highPriority = list.filter { it.priority  == 1}
                                if ((highPriority.isNotEmpty())){
                                    list.remove(highPriority[0])
                                    list.add(0, highPriority[0])
                                }
                            }

                            list
                        }

                        is TaskOrder.Today -> {

                            if (taskOrder.pendedHighPriority) {
                                val list = tasks.filter { it.date == LocalDate.now() }
                                    .sortedBy { it.startTime }.toMutableList()
                                if (list.size > 1){
                                    val highPriority = list.filter { it.priority  == 1}
                                    if ((highPriority.isNotEmpty())){
                                        list.remove(highPriority[0])
                                        list.add(0, highPriority[0])
                                    }
                                }
                                list
                            } else {
                                tasks.filter { it.date == LocalDate.now() }
                                    .sortedBy { it.startTime }
                            }

                        }

                        is TaskOrder.Time -> tasks.sortedBy { it.startTime }
                    }
                }

                is OrderType.Descending -> {
                    when (taskOrder) {
                        is TaskOrder.Date -> tasks.sortedByDescending { it.date }
                        is TaskOrder.Priority -> tasks.sortedByDescending { it.priority }
                        //is TaskOrder.All -> tasks
                        is TaskOrder.Default -> {
                            val list = tasks.filter { it.date == LocalDate.now() }
                                .sortedBy { it.startTime }.toMutableList()

                            if (list.size > 1){
                                val highPriority = list.filter { it.priority  == 1}
                                if (highPriority.isNotEmpty()){
                                    list.remove(highPriority[0])
                                    list.add(0, highPriority[0])
                                }
                            }

                            list
                        }

                        is TaskOrder.Today -> {

                            if (taskOrder.pendedHighPriority) {
                                val list = tasks.filter { it.date == LocalDate.now() }
                                    .sortedByDescending { it.startTime }.toMutableList()

                                if (list.size > 1){
                                    val highPriority = list.filter { it.priority  == 1}
                                    if (highPriority.isNotEmpty()){
                                        list.remove(highPriority[0])
                                        list.add(0, highPriority[0])
                                    }
                                }
                                list
                            } else {
                                tasks.filter { it.date == LocalDate.now() }
                                    .sortedByDescending { it.startTime }
                            }
                        }

                        is TaskOrder.Time -> tasks.sortedByDescending { it.startTime }
                    }
                }

            }
        }
    }
}
