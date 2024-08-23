package com.k.sekiro.taskmanagementapp.task_management_feature.presentation.tasks_screen.component

import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.*
import androidx.compose.foundation.shape.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.semantics.*
import androidx.compose.ui.unit.*

@Composable
fun CustomIconToggleButton(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: IconToggleButtonColors = IconButtonDefaults.iconToggleButtonColors(),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    size:Dp = 24.dp,
    content: @Composable () -> Unit
) {
    @Suppress("DEPRECATION_ERROR")
    (Box(
        modifier = modifier
            .minimumInteractiveComponentSize()
            .size(size)
            .clip(CircleShape)
            .background(color = colors.containerColor(enabled, checked).value)
            .toggleable(
                value = checked,
                onValueChange = onCheckedChange,
                enabled = enabled,
                role = Role.Checkbox,
                interactionSource = interactionSource,
                indication = androidx.compose.material.ripple.rememberRipple(
                    bounded = false,
                    radius =  size / 2
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        val contentColor = colors.contentColor(enabled, checked).value
        CompositionLocalProvider(LocalContentColor provides contentColor, content = content)
    })
}

@Composable
private fun IconToggleButtonColors.containerColor(enabled: Boolean, checked: Boolean): State<Color> {
    val target = when {
        !enabled -> disabledContainerColor
        !checked -> containerColor
        else -> checkedContainerColor
    }
    return rememberUpdatedState(target)
}

@Composable
private fun IconToggleButtonColors.contentColor(enabled: Boolean, checked: Boolean): State<Color> {
    val target = when {
        !enabled -> disabledContentColor
        !checked -> contentColor
        else -> checkedContentColor
    }
    return rememberUpdatedState(target)
}