package com.k.sekiro.taskmanagementapp.task_management_feature.presentation.add_edit_task_screen.component

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.res.*
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.unit.*
import com.k.sekiro.taskmanagementapp.R
import com.k.sekiro.taskmanagementapp.task_management_feature.domain.model.*
import com.k.sekiro.taskmanagementapp.task_management_feature.presentation.add_edit_task_screen.*
import com.k.sekiro.taskmanagementapp.task_management_feature.presentation.utils.component.*
import com.k.sekiro.taskmanagementapp.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskPriorityMenu(
    onEvent:(AddEditTaskEvent) -> Unit,
    taskPriority:Priority,
    enabled:Boolean = true
) {


    var isExpended by remember {
        mutableStateOf(false)
    }

    ExposedDropdownMenuBox(
        expanded = isExpended,
        onExpandedChange = {
            isExpended = !isExpended
        }) {

        OutlinedTextField(
            value = "",
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth(.7f)
                .menuAnchor(),
            readOnly = true,
            placeholder = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.height(MaterialTheme.dimens.addEditScreenDimens.priorityMenuHeight)
                ) {
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(MaterialTheme.dimens.addEditScreenDimens.priorityBoxSize)
                            .background(
                                when (taskPriority) {
                                    Priority.HIGH -> HighPriorityColor
                                    Priority.NORMAL -> NormalPriorityColor
                                    Priority.LOW -> LowPriorityColor
                                }
                            )
                    )
                    Spacer(modifier = Modifier.width(MaterialTheme.dimens.addEditScreenDimens.spacerBetweenPriorityCircleAndText))
                    Text(
                        text = when(taskPriority){
                            Priority.HIGH -> stringResource(R.string.high_priority)
                            Priority.NORMAL -> stringResource(R.string.normal_priority)
                            Priority.LOW -> stringResource(R.string.low_priority)
                        },
                        style = TextStyle(
                            fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    )
                }
            },
            supportingText = {
                Text(
                    text = stringResource(R.string.priority),
                    fontSize = (MaterialTheme.typography.bodyLarge.fontSize.value - 2).sp
                )
            },
            trailingIcon = {
                
                ExposedDropdownMenuDefaults.CustomTrailingIcon(
                    expanded = isExpended,
                    size = MaterialTheme.dimens.addEditScreenDimens.iconsSize
                )

            },

            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = LightPrimaryColor,
                unfocusedBorderColor = LightPrimaryColor,



                ),
            enabled = enabled
        )
        ExposedDropdownMenu(
            expanded = isExpended,
            onDismissRequest = {
                isExpended = false
            },
        )
        {
            DropdownMenuItem(
                text = {
                    Row (
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Box(
                            modifier = Modifier
                                .clip(CircleShape)
                                .size(MaterialTheme.dimens.addEditScreenDimens.priorityBoxSize)
                                .background(HighPriorityColor)
                        )
                        Spacer(modifier = Modifier.width(MaterialTheme.dimens.addEditScreenDimens.spacerBetweenPriorityCircleAndText))
                        Text(
                            text = stringResource(id = R.string.high_priority),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                },
                onClick = {
                    isExpended = false
                    onEvent(AddEditTaskEvent.ChoosePriority(Priority.HIGH))
                }
            )

            Spacer(modifier = Modifier.height(MaterialTheme.dimens.addEditScreenDimens.spacerBetweenPriorityMenuItem))


            DropdownMenuItem(
                text = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(CircleShape)
                                .size(MaterialTheme.dimens.addEditScreenDimens.priorityBoxSize)
                                .background(LowPriorityColor)
                        )
                        Spacer(modifier = Modifier.width(MaterialTheme.dimens.addEditScreenDimens.spacerBetweenPriorityCircleAndText))
                        Text(
                            text = stringResource(id = R.string.low_priority),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                },
                onClick = {
                    isExpended = false
                    onEvent(AddEditTaskEvent.ChoosePriority(Priority.LOW))
                }
            )

            Spacer(modifier = Modifier.height(MaterialTheme.dimens.addEditScreenDimens.spacerBetweenPriorityMenuItem))

            DropdownMenuItem(
                text = {
                    Row (
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Box(
                            modifier = Modifier
                                .clip(CircleShape)
                                .size(MaterialTheme.dimens.addEditScreenDimens.priorityBoxSize)
                                .background(NormalPriorityColor)
                        )
                        Spacer(modifier = Modifier.width(MaterialTheme.dimens.addEditScreenDimens.spacerBetweenPriorityCircleAndText))
                        Text(
                            text = stringResource(id = R.string.normal_priority),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                },
                onClick = {
                    isExpended = false
                    onEvent(AddEditTaskEvent.ChoosePriority(Priority.NORMAL))
                }
            )
        }


    }
}