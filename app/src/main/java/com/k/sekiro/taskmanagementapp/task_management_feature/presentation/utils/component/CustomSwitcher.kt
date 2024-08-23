package com.k.sekiro.taskmanagementapp.task_management_feature.presentation.utils.component

import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.*
import androidx.compose.foundation.shape.*
import androidx.compose.material3.*
import androidx.compose.material3.tokens.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.platform.*
import androidx.compose.ui.semantics.*
import androidx.compose.ui.unit.*
import com.k.sekiro.taskmanagementapp.ui.theme.*
import kotlinx.coroutines.*
import kotlin.math.*

@Composable
@Suppress("ComposableLambdaParameterNaming", "ComposableLambdaParameterPosition")
fun CustomSwitch(
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
    modifier: Modifier = Modifier,
    thumbContent: (@Composable () -> Unit)? = null,
    enabled: Boolean = true,
    colors: SwitchColors = SwitchDefaults.colors(),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    val uncheckedThumbDiameter = if (thumbContent == null) {
        MaterialTheme.dimens.appDrawerDimens.switcherDimens.uncheckedThumbDiameter
    } else {
        MaterialTheme.dimens.appDrawerDimens.switcherDimens.thumbDiameter

    }

    val thumbPaddingStart = (MaterialTheme.dimens.appDrawerDimens.switcherDimens.switchHeight - uncheckedThumbDiameter) / 2
    val minBound = with(LocalDensity.current) { thumbPaddingStart.toPx() }
    val maxBound = with(LocalDensity.current) { MaterialTheme.dimens.appDrawerDimens.switcherDimens.thumbPathLength.toPx() }
    val valueToOffset = remember<(Boolean) -> Float>(minBound, maxBound) {
        { value -> if (value) maxBound else minBound }
    }

    val targetValue = valueToOffset(checked)
    val offset = remember { Animatable(targetValue) }
    val scope = rememberCoroutineScope()

    SideEffect {
        // min bound might have changed if the icon is only rendered in checked state.
        offset.updateBounds(lowerBound = minBound)
    }

    DisposableEffect(checked) {
        if (offset.targetValue != targetValue) {
            scope.launch {
                offset.animateTo(targetValue, AnimationSpec)
            }
        }
        onDispose { }
    }

    // TODO: Add Swipeable modifier b/223797571
    val toggleableModifier =
        if (onCheckedChange != null) {
            Modifier.toggleable(
                value = checked,
                onValueChange = onCheckedChange,
                enabled = enabled,
                role = Role.Switch,
                interactionSource = interactionSource,
                indication = null
            )
        } else {
            Modifier
        }

    Box(
        modifier
            .then(
                if (onCheckedChange != null) {
                    Modifier.minimumInteractiveComponentSize()
                } else {
                    Modifier
                }
            )
            .then(toggleableModifier)
            .wrapContentSize(Alignment.Center)
            .requiredSize(MaterialTheme.dimens.appDrawerDimens.switcherDimens.switchWidth, MaterialTheme.dimens.appDrawerDimens.switcherDimens.switchHeight)
    ) {
        SwitchImpl(
            checked = checked,
            enabled = enabled,
            colors = colors,
            thumbValue = offset.asState(),
            interactionSource = interactionSource,
            thumbShape = CircleShape,
            uncheckedThumbDiameter = uncheckedThumbDiameter,
            minBound = thumbPaddingStart,
            maxBound = MaterialTheme.dimens.appDrawerDimens.switcherDimens.thumbPathLength,
            thumbContent = thumbContent,
        )
    }
}


@Composable
@Suppress("ComposableLambdaParameterNaming", "ComposableLambdaParameterPosition")
private fun BoxScope.SwitchImpl(
    checked: Boolean,
    enabled: Boolean,
    colors: SwitchColors,
    thumbValue: State<Float>,
    thumbContent: (@Composable () -> Unit)?,
    interactionSource: InteractionSource,
    thumbShape: Shape,
    uncheckedThumbDiameter: Dp,
    minBound: Dp,
    maxBound: Dp,
) {
    val trackColor = colors.trackColor(enabled, checked)
    val isPressed by interactionSource.collectIsPressedAsState()

    val thumbValueDp = with(LocalDensity.current) { thumbValue.value.toDp() }
    val thumbSizeDp = if (isPressed) {
        MaterialTheme.dimens.appDrawerDimens.switcherDimens.pressedHandleWidth
    } else {
        uncheckedThumbDiameter + (MaterialTheme.dimens.appDrawerDimens.switcherDimens.thumbDiameter - uncheckedThumbDiameter) *
                ((thumbValueDp - minBound) / (maxBound - minBound))
    }

    val thumbOffset = if (isPressed) {
        with(LocalDensity.current) {
            if (checked) {
                MaterialTheme.dimens.appDrawerDimens.switcherDimens.thumbPathLength - MaterialTheme.dimens.appDrawerDimens.switcherDimens.trackOutlineWidth
            } else {
                MaterialTheme.dimens.appDrawerDimens.switcherDimens.trackOutlineWidth
            }.toPx()
        }
    } else {
        thumbValue.value
    }

    val trackShape = CircleShape
    val modifier = Modifier
        .align(Alignment.Center)
        .width(MaterialTheme.dimens.appDrawerDimens.switcherDimens.switchWidth)
        .height(MaterialTheme.dimens.appDrawerDimens.switcherDimens.switchHeight)
        .border(
            MaterialTheme.dimens.appDrawerDimens.switcherDimens.trackOutlineWidth,
            colors.borderColor(enabled, checked),
            trackShape
        )
        .background(trackColor, trackShape)

    Box(modifier) {
        val resolvedThumbColor = colors.thumbColor(enabled, checked)
        @Suppress("DEPRECATION_ERROR")
        Box(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .offset { IntOffset(thumbOffset.roundToInt(), 0) }
                .indication(
                    interactionSource = interactionSource,
                    indication = androidx.compose.material.ripple.rememberRipple(
                        bounded = false,
                        MaterialTheme.dimens.appDrawerDimens.switcherDimens.stateLayerSize / 2
                    )
                )
                .requiredSize(thumbSizeDp)
                .background(resolvedThumbColor, thumbShape),
            contentAlignment = Alignment.Center
        ) {
            if (thumbContent != null) {
                val iconColor = colors.iconColor(enabled, checked)
                CompositionLocalProvider(
                    LocalContentColor provides iconColor,
                    content = thumbContent
                )
            }
        }
    }
}




private val AnimationSpec = TweenSpec<Float>(durationMillis = 100)


@Stable
internal fun SwitchColors.trackColor(enabled: Boolean, checked: Boolean): Color =
    if (enabled) {
        if (checked) checkedTrackColor else uncheckedTrackColor
    } else {
        if (checked) disabledCheckedTrackColor else disabledUncheckedTrackColor
    }



@Stable
internal fun SwitchColors.borderColor(enabled: Boolean, checked: Boolean): Color =
    if (enabled) {
        if (checked) checkedBorderColor else uncheckedBorderColor
    } else {
        if (checked) disabledCheckedBorderColor else disabledUncheckedBorderColor
    }


@Stable
internal fun SwitchColors.thumbColor(enabled: Boolean, checked: Boolean): Color =
    if (enabled) {
        if (checked) checkedThumbColor else uncheckedThumbColor
    } else {
        if (checked) disabledCheckedThumbColor else disabledUncheckedThumbColor
    }


@Stable
internal fun SwitchColors.iconColor(enabled: Boolean, checked: Boolean): Color =
    if (enabled) {
        if (checked) checkedIconColor else uncheckedIconColor
    } else {
        if (checked) disabledCheckedIconColor else disabledUncheckedIconColor
    }