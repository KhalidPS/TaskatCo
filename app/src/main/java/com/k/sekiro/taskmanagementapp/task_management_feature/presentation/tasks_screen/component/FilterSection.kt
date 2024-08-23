package com.k.sekiro.taskmanagementapp.task_management_feature.presentation.tasks_screen.component

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*
import com.k.sekiro.taskmanagementapp.task_management_feature.domain.utils.*
import com.k.sekiro.taskmanagementapp.R
import com.k.sekiro.taskmanagementapp.ui.theme.*

@Preview
@Composable
fun FilterSection(
    order: TaskOrder = TaskOrder.Date(OrderType.Ascending),
    onOrderChange: (TaskOrder) -> Unit = {}
) {
    Column (
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ){
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ){
            FilterRadioButton(
                txt = stringResource(id = R.string.date),
                selected = order is TaskOrder.Date,
                onSelect = { onOrderChange(TaskOrder.Date(order.orderType)) }
            )

            FilterRadioButton(
                txt = stringResource(id = R.string.time),
                selected = order is TaskOrder.Time,
                onSelect = { onOrderChange(TaskOrder.Time(order.orderType)) }
            )

            FilterRadioButton(
                txt = stringResource(id = R.string.today),
                selected = order is TaskOrder.Today,
                onSelect = { onOrderChange(TaskOrder.Today(order.orderType)) }
            )
        }



        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            FilterRadioButton(
                txt = stringResource(id = R.string.priority),
                selected = order is TaskOrder.Priority,
                onSelect = { onOrderChange(TaskOrder.Priority(order.orderType)) }
            )

            FilterRadioButton(
                txt = stringResource(id = R.string._default),
                selected = order is TaskOrder.Default,
                onSelect = { onOrderChange(TaskOrder.Default(order.orderType)) }
            )
        }

        HorizontalDivider(
            thickness =
            MaterialTheme.dimens.homeScreenDimens.filterSectionDividerThickness
        )


        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ){
            if (order !is TaskOrder.Default ){
                FilterRadioButton(
                    txt = stringResource(id = R.string.ascending),
                    selected = order.orderType is OrderType.Ascending,
                    onSelect = { onOrderChange(order.copy(OrderType.Ascending)) }
                )

                FilterRadioButton(
                    txt = stringResource(id = R.string.descending),
                    selected = order.orderType is OrderType.Descending,
                    onSelect = { onOrderChange(order.copy(OrderType.Descending)) }
                )

                if (order is TaskOrder.Today){
                    Row (
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        CustomIconToggleButton(
                            checked = order.pendedHighPriority,
                            onCheckedChange = {
                                onOrderChange(TaskOrder.Today(order.orderType,!order.pendedHighPriority))
                            },
                            size = MaterialTheme.dimens.homeScreenDimens.iconToggleButtonSize,

                        ){
                            AnimatedVisibility(
                                visible = order.pendedHighPriority,
                                enter = fadeIn(
                                    animationSpec = tween(
                                        durationMillis = 100
                                    )
                                ),
                                exit = fadeOut(
                                    animationSpec = tween(
                                        durationMillis = 100
                                    )
                                ),
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.CheckBox,
                                    contentDescription = null,
                                    //tint = MaterialTheme.colorScheme.inversePrimary,
                                    modifier = Modifier.size(MaterialTheme.dimens.homeScreenDimens.iconToggleButtonSize)
                                )
                            }

                            AnimatedVisibility(
                                visible = !order.pendedHighPriority,
                                enter = fadeIn(
                                    animationSpec = tween(
                                        durationMillis = 100
                                    )
                                ),
                                exit = fadeOut(
                                    animationSpec = tween(
                                        durationMillis = 100
                                    )
                                )
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.CheckBoxOutlineBlank,
                                    contentDescription = null,
                                    tint = Black200,
                                    modifier = Modifier.size(MaterialTheme.dimens.homeScreenDimens.iconToggleButtonSize)
                                )
                            }
                        }

                        Text(
                            text = stringResource(id = R.string.pend_high_priority),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }

            }


        }
    }
}
