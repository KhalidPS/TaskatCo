package com.k.sekiro.taskmanagementapp.task_management_feature.presentation.tasks_screen.component

import android.util.*
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.platform.*
import androidx.compose.ui.unit.*
import androidx.compose.ui.unit.LayoutDirection
import kotlinx.coroutines.*
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> SwipeToDeleteContainer(
    item:T,
    onDelete:(T) -> Unit,
    animationDuration:Int,
    modifier: Modifier = Modifier,
    content:@Composable (T) -> Unit,
) {

    var isRemoved by remember {
        mutableStateOf(false)
    }

    val isArabic = Locale.getDefault().language == "ar"
    val state = rememberSwipeToDismissBoxState(
        confirmValueChange = { value ->
            if (value == SwipeToDismissBoxValue.EndToStart) {
                isRemoved = true
                true
            } else if (value == SwipeToDismissBoxValue.StartToEnd) {
                isRemoved = true
                true
            }
            else{
                false
            }
        }
    )

    LaunchedEffect(key1 = isRemoved) {
        if (isRemoved){
            delay(animationDuration.toLong())
            onDelete(item)
        }
    }


        Box(modifier = modifier){
            AnimatedVisibility(
                visible = !isRemoved,
                exit = shrinkVertically(
                    animationSpec = tween(
                        durationMillis = animationDuration
                    )
                ),
                modifier = modifier
            ) {

                SwipeToDismissBox(
                    state = state,
                    backgroundContent = {
                        DeleteTaskBackground(swipeDismissState = state)
                    },
                    enableDismissFromStartToEnd = isArabic,
                    enableDismissFromEndToStart = !isArabic
                ) {
                    content(item)
                }

            }
        }





}