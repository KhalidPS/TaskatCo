package com.k.sekiro.taskmanagementapp.task_management_feature.presentation.tasks_screen.component

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import com.k.sekiro.taskmanagementapp.ui.theme.*
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteTaskBackground(
    swipeDismissState:SwipeToDismissBoxState,
) {

    val color = if (swipeDismissState.dismissDirection == SwipeToDismissBoxValue.EndToStart || swipeDismissState.dismissDirection == SwipeToDismissBoxValue.StartToEnd)
        Color.Red else Color.Transparent

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color),
        contentAlignment = Alignment.CenterEnd
    ) {

        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = "delete Icon",
            tint = Color.White,
            modifier = Modifier.size(MaterialTheme.dimens.homeScreenDimens.menuIconSize)
            )

    }
}