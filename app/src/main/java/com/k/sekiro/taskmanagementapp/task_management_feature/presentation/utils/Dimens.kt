package com.k.sekiro.taskmanagementapp.task_management_feature.presentation.utils

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.ui.unit.*


data class HomeScreenDimens(
    val spacerBetweenTopSectionAndTasksHeader: Dp = 8.dp,
    val tasksHeaderPadding: Dp = 16.dp,
    val spacerBetweenListItems: Dp = 12.dp,
    val topSectionContentPadding: Dp = 8.dp,
    val spacerBetweenLazyRowItems: Dp = 8.dp,
    val completedTaskCardRoundedCorner: Dp = 12.dp,
    val completedTaskCardElevation: Dp = 8.dp,
    val completedTaskCardPadding: Dp = 16.dp,
    val completedTaskCardHeight: Dp = 110.dp,
    val spacerBetweenCompletedTaskTitleAndTheCount: Dp = 8.dp,
    val spacerBetweenListIconAndTaskCount: Dp = 4.dp,
    val uncompletedTaskCardRoundedCorner: Dp = 12.dp,
    val uncompletedTaskCardElevation: Dp = 8.dp,
    val uncompletedTaskCardHeight: Dp = 110.dp,
    val uncompletedTaskCardPadding: Dp = 16.dp,
    val uncompletedTaskCardWidth: Dp = 200.dp,
    val uncompletedCardSpacerBetweenTaskTitleAndTaskDes: Dp = 4.dp,
    val uncompletedCardSpacerBetweenDescriptionAndTime: Dp = 4.dp,
    val uncompletedTimeIconSize: Dp = 20.dp,
    val uncompletedTaskPaddingBetweenTimeIconAndTimeTxt: Dp = 4.dp,
    val taskCardRounded: Dp = 20.dp,
    val taskCardElevation: Dp = 8.dp,
    val taskRowContainerPadding: Dp = 8.dp,
    val spacerBetweenTaskTitleAndDescription: Dp = 8.dp,
    val spacerBetweenTaskDescriptionAndTime: Dp = 16.dp,
    val taskCardDateIconSize: Dp = 24.dp,
    val taskCardPaddingDateTxt: Dp = 4.dp,
    val taskCardTimeIconSize: Dp = 24.dp,
    val taskCardPaddingTimeTxt: Dp = 4.dp,
    val spacerBetweenColumnAndTimer: Dp = 16.dp,
    val taskCardTimerWeight: Float = 0.3f,
    val spacerIfNoTimerWidth: Dp = 0.dp,
    val spacerIfNoTimerWeight: Float = 0.3f,
    val taskCardPrioritySize: Dp = 16.dp,
    val taskCardPriorityRounded: RoundedCornerShape = CircleShape,
    val menuIconSize: Dp = 24.dp,
    val tobBarWindowInsets: WindowInsets = WindowInsets(0.dp),
    val menuIconEndPadding: Dp = 0.dp,
    val menuListIconSize: Dp = 24.dp,
    val radioButtonSize: Dp = 20.dp,
    val iconToggleButtonSize: Dp = 24.dp,
    val iconToggleButtonPadding: Dp = 0.dp,
    val filterIconSize: Dp = 24.dp,
    val pendingIconSize: Dp = 24.dp,
    val spacerIfShowPendingIcon: Dp = 22.dp,
    val spaceBetweenToggleIconAndPendIcon: Dp = 0.dp,
    val spaceBetweenFilterSectionRows: Dp = 0.dp,
    val filterSectionDividerThickness: Dp = 1.dp,
    val spacerBetweenFilterSectionAndTasksCards: Dp = 0.dp,
    val tasksLazyColumnPaddingValues: Dp = 8.dp,
    val highPriorityTaskCardPadding: Dp = 4.dp,

    )


data class AnalysisScreenDimens(
    val timePieChartSize: Dp = 180.dp,
    val timePieChartArcWidth: Dp = 30.dp,
    val spacerBetweenPieChartAndItems: Dp = 16.dp,
    val spacerBetweenLazyItems: Dp = 10.dp,
    val colorBoxSize: Dp = 16.dp,
    val itemContainerHorizontalPadding: Dp = 16.dp,
    val itemContainerVerticalPadding: Dp = 20.dp,
    val tapPadding:Dp = 0.dp,
    val pagerPadding:Dp = 16.dp,
    val completedChartCanvasSize:Dp = 200.dp,
    val completedChartRadius:Float = 70f,
    val completedChartRadiusForCalculateLineOffset:Float = 320f,
    val lineBetweenArcsStroke:Float = 20f,
    val completedChartTopLineOffset:Dp = 100.dp,
    val completedChartBottomLineMultiply:Float = 1.1f,
    val completedChartColorCircleSize:Dp = 12.dp,
    val completedChartPercentageBoxWidth:Dp = 50.dp,
    val completedChartPercentageBoxPadding:Dp = 4.dp
)


data class AddEditScreenDimens(
    val taskTextFieldHeight: Dp = 60.dp,
    val taskTextFieldDescriptionHeight: Dp = 120.dp,
    val iconsSize: Dp = 24.dp,
    val topSectionPadding: Dp = 16.dp,
    val priorityBoxSize: Dp = 20.dp,
    val spacerBetweenPriorityCircleAndText: Dp = 8.dp,
    val spacerBetweenPriorityMenuItem: Dp = 0.dp,
    val priorityMenuHeight: Dp = 20.dp,
    val saveButtonPadding: Dp = 8.dp
)


data class SwitcherDimens(
    val pressedHandleWidth: Dp = 28.0.dp,
    val thumbDiameter: Dp = 24.dp,
    val uncheckedThumbDiameter: Dp = 16.dp,
    val switchWidth: Dp = 52.dp,
    val switchHeight: Dp = 32.dp,
    val thumbPadding: Dp = (switchHeight - thumbDiameter) / 2,
    val thumbPathLength: Dp = (switchWidth - thumbDiameter) - thumbPadding,
    val trackOutlineWidth: Dp = 2.0.dp,
    val stateLayerSize: Dp = 40.0.dp,

    )

data class AppDrawerDimens(
    val logoSize: Dp = 200.dp,
    val appNamePadding: Dp = 4.dp,
    val iconsSize: Dp = 24.dp,
    val iconsTextStartPadding: Dp = 4.dp,
    val spacerBetweenLanguageSelectorAndItemsOnTop: Dp = 16.dp,
    val horizontalDividerThickness: Dp = 2.dp,
    val languageSelectorWidth: Dp = 150.dp,
    val dropDownMenuExposedIconSize: Dp = 24.dp,
    val switcherDimens: SwitcherDimens = SwitcherDimens()
)


data class ScreensDimens(
    val homeScreenDimens: HomeScreenDimens = HomeScreenDimens(),
    val analysisDimens: AnalysisScreenDimens = AnalysisScreenDimens(),
    val addEditScreenDimens: AddEditScreenDimens = AddEditScreenDimens(),
    val appDrawerDimens: AppDrawerDimens = AppDrawerDimens()
)


val CompactSmallDimens = ScreensDimens(
    homeScreenDimens = HomeScreenDimens(
        spacerBetweenTopSectionAndTasksHeader = 8.dp,
        tasksHeaderPadding = 16.dp,
        spacerBetweenListItems = 20.dp,
        topSectionContentPadding = 20.dp,
        spacerBetweenLazyRowItems = 8.dp,
        completedTaskCardRoundedCorner = 12.dp,
        completedTaskCardElevation = 8.dp,
        completedTaskCardPadding = 16.dp,
        completedTaskCardHeight = 180.dp,
        spacerBetweenCompletedTaskTitleAndTheCount = 8.dp,
        spacerBetweenListIconAndTaskCount = 8.dp,
        uncompletedTaskCardRoundedCorner = 12.dp,
        uncompletedTaskCardElevation = 8.dp,
        uncompletedTaskCardHeight = 180.dp,
        uncompletedTaskCardPadding = 16.dp,
        uncompletedTaskCardWidth = 400.dp,
        uncompletedCardSpacerBetweenTaskTitleAndTaskDes = 4.dp,
        uncompletedCardSpacerBetweenDescriptionAndTime = 4.dp,
        uncompletedTimeIconSize = 40.dp,
        uncompletedTaskPaddingBetweenTimeIconAndTimeTxt = 8.dp,
        taskCardRounded = 20.dp,
        taskCardElevation = 8.dp,
        taskRowContainerPadding = 8.dp,
        spacerBetweenTaskTitleAndDescription = 8.dp,
        spacerBetweenTaskDescriptionAndTime = 16.dp,
        taskCardDateIconSize = 40.dp,
        taskCardPaddingDateTxt = 4.dp,
        taskCardTimeIconSize = 40.dp,
        taskCardPaddingTimeTxt = 4.dp,
        spacerBetweenColumnAndTimer = 16.dp,
        taskCardTimerWeight = 0.3f,
        spacerIfNoTimerWidth = 0.dp,
        spacerIfNoTimerWeight = 0.3f,
        taskCardPrioritySize = 30.dp,
        taskCardPriorityRounded = CircleShape,
        menuIconSize = 50.dp,
        tobBarWindowInsets = WindowInsets(left = 8.dp, right = 8.dp, bottom = 12.dp, top = 12.dp),
        menuIconEndPadding = 8.dp,
        menuListIconSize = 40.dp,
        radioButtonSize = 30.dp,
        iconToggleButtonSize = 40.dp,
        iconToggleButtonPadding = 20.dp,
        filterIconSize = 40.dp,
        pendingIconSize = 40.dp,
        spacerIfShowPendingIcon = 22.dp,
        spaceBetweenFilterSectionRows = 8.dp,
        filterSectionDividerThickness = 2.dp,
        spacerBetweenFilterSectionAndTasksCards = 16.dp,
        tasksLazyColumnPaddingValues = 20.dp,
        highPriorityTaskCardPadding = 8.dp,
        spaceBetweenToggleIconAndPendIcon = 16.dp
    ),

    addEditScreenDimens = AddEditScreenDimens(
        taskTextFieldHeight = 100.dp,
        taskTextFieldDescriptionHeight = 200.dp,
        iconsSize = 40.dp,
        topSectionPadding = 32.dp,
        priorityBoxSize = 30.dp,
        spacerBetweenPriorityCircleAndText = 16.dp,
        spacerBetweenPriorityMenuItem = 20.dp,
        priorityMenuHeight = 60.dp,
        saveButtonPadding = 20.dp
    ),

    analysisDimens = AnalysisScreenDimens(
        timePieChartSize = 350.dp,
        timePieChartArcWidth = 50.dp,
        spacerBetweenPieChartAndItems = 32.dp,
        spacerBetweenLazyItems = 20.dp,
        colorBoxSize = 32.dp,
        itemContainerHorizontalPadding = 32.dp,
        itemContainerVerticalPadding = 40.dp,
        tapPadding = 8.dp,
        pagerPadding = 32.dp,
        completedChartCanvasSize = 400.dp,
        completedChartRadius = 100f,
        lineBetweenArcsStroke = 30f,
        completedChartTopLineOffset = 200.dp,
        completedChartBottomLineMultiply = 1.3f,
        completedChartColorCircleSize = 24.dp,
        completedChartPercentageBoxWidth = 100.dp,
        completedChartPercentageBoxPadding = 8.dp
    ),

    appDrawerDimens = AppDrawerDimens(
        logoSize = 200.dp,
        appNamePadding = 4.dp,
        iconsSize = 24.dp,
        iconsTextStartPadding = 4.dp,
        spacerBetweenLanguageSelectorAndItemsOnTop = 32.dp,
        horizontalDividerThickness = 4.dp,
        languageSelectorWidth = 150.dp,
        dropDownMenuExposedIconSize = 24.dp,
        switcherDimens = SwitcherDimens(
            pressedHandleWidth = 28.0.dp,
            thumbDiameter = 24.dp,
            uncheckedThumbDiameter = 16.dp,
            switchWidth = 52.dp,
            switchHeight = 32.dp,
            trackOutlineWidth = 2.0.dp,
            stateLayerSize = 40.0.dp,
        )
    )
)

val CompatMediumDimens = ScreensDimens()

val CompactDimens = ScreensDimens()

val MediumDimens = ScreensDimens(
    homeScreenDimens = HomeScreenDimens(
        spacerBetweenTopSectionAndTasksHeader = 8.dp,
        tasksHeaderPadding = 16.dp,
        spacerBetweenListItems = 20.dp,
        topSectionContentPadding = 20.dp,
        spacerBetweenLazyRowItems = 8.dp,
        completedTaskCardRoundedCorner = 12.dp,
        completedTaskCardElevation = 8.dp,
        completedTaskCardPadding = 16.dp,
        completedTaskCardHeight = 180.dp,
        spacerBetweenCompletedTaskTitleAndTheCount = 8.dp,
        spacerBetweenListIconAndTaskCount = 8.dp,
        uncompletedTaskCardRoundedCorner = 12.dp,
        uncompletedTaskCardElevation = 8.dp,
        uncompletedTaskCardHeight = 180.dp,
        uncompletedTaskCardPadding = 16.dp,
        uncompletedTaskCardWidth = 400.dp,
        uncompletedCardSpacerBetweenTaskTitleAndTaskDes = 4.dp,
        uncompletedCardSpacerBetweenDescriptionAndTime = 4.dp,
        uncompletedTimeIconSize = 40.dp,
        uncompletedTaskPaddingBetweenTimeIconAndTimeTxt = 8.dp,
        taskCardRounded = 20.dp,
        taskCardElevation = 8.dp,
        taskRowContainerPadding = 8.dp,
        spacerBetweenTaskTitleAndDescription = 8.dp,
        spacerBetweenTaskDescriptionAndTime = 16.dp,
        taskCardDateIconSize = 40.dp,
        taskCardPaddingDateTxt = 4.dp,
        taskCardTimeIconSize = 40.dp,
        taskCardPaddingTimeTxt = 4.dp,
        spacerBetweenColumnAndTimer = 16.dp,
        taskCardTimerWeight = 0.3f,
        spacerIfNoTimerWidth = 0.dp,
        spacerIfNoTimerWeight = 0.3f,
        taskCardPrioritySize = 30.dp,
        taskCardPriorityRounded = CircleShape,
        menuIconSize = 50.dp,
        tobBarWindowInsets = WindowInsets(left = 8.dp, right = 8.dp, bottom = 12.dp, top = 12.dp),
        menuIconEndPadding = 8.dp,
        menuListIconSize = 40.dp,
        radioButtonSize = 30.dp,
        iconToggleButtonSize = 40.dp,
        iconToggleButtonPadding = 20.dp,
        filterIconSize = 40.dp,
        pendingIconSize = 40.dp,
        spacerIfShowPendingIcon = 22.dp,
        spaceBetweenFilterSectionRows = 8.dp,
        filterSectionDividerThickness = 2.dp,
        spacerBetweenFilterSectionAndTasksCards = 16.dp,
        tasksLazyColumnPaddingValues = 20.dp,
        highPriorityTaskCardPadding = 8.dp,
        spaceBetweenToggleIconAndPendIcon = 16.dp
    ),

    addEditScreenDimens = AddEditScreenDimens(
        taskTextFieldHeight = 100.dp,
        taskTextFieldDescriptionHeight = 200.dp,
        iconsSize = 40.dp,
        topSectionPadding = 32.dp,
        priorityBoxSize = 30.dp,
        spacerBetweenPriorityCircleAndText = 16.dp,
        spacerBetweenPriorityMenuItem = 20.dp,
        priorityMenuHeight = 60.dp,
        saveButtonPadding = 20.dp
    ),

    analysisDimens = AnalysisScreenDimens(
        timePieChartSize = 350.dp,
        timePieChartArcWidth = 50.dp,
        spacerBetweenPieChartAndItems = 32.dp,
        spacerBetweenLazyItems = 20.dp,
        colorBoxSize = 32.dp,
        itemContainerHorizontalPadding = 32.dp,
        itemContainerVerticalPadding = 40.dp,
        tapPadding = 8.dp,
        pagerPadding = 32.dp,
        completedChartCanvasSize = 400.dp,
        completedChartRadius = 100f,
        lineBetweenArcsStroke = 30f,
        completedChartTopLineOffset = 200.dp,
        completedChartBottomLineMultiply = 1.3f,
        completedChartColorCircleSize = 24.dp,
        completedChartPercentageBoxWidth = 100.dp,
        completedChartPercentageBoxPadding = 8.dp
    ),

    appDrawerDimens = AppDrawerDimens(
        logoSize = 400.dp,
        appNamePadding = 8.dp,
        iconsSize = 40.dp,
        iconsTextStartPadding = 8.dp,
        spacerBetweenLanguageSelectorAndItemsOnTop = 32.dp,
        horizontalDividerThickness = 4.dp,
        languageSelectorWidth = 200.dp,
        dropDownMenuExposedIconSize = 40.dp,
        switcherDimens = SwitcherDimens(
            pressedHandleWidth = (28.0 * 1.5).dp,
            thumbDiameter = (24 * 1.5).dp,
            uncheckedThumbDiameter = (16 * 1.5).dp,
            switchWidth = (52 * 1.5).dp,
            switchHeight = (32 * 1.5).dp,
            trackOutlineWidth = (2.0 * 1.5).dp,
            stateLayerSize = (40.0 * 1.5).dp,
        )
    )
)

val ExpandedDimens = ScreensDimens(
    homeScreenDimens = HomeScreenDimens(
        spacerBetweenTopSectionAndTasksHeader = 8.dp,
        tasksHeaderPadding = 16.dp,
        spacerBetweenListItems = 12.dp,
        topSectionContentPadding = 8.dp,
        spacerBetweenLazyRowItems = 8.dp,
        completedTaskCardRoundedCorner = 12.dp,
        completedTaskCardElevation = 8.dp,
        completedTaskCardPadding = 16.dp,
        completedTaskCardHeight = 150.dp,
        spacerBetweenCompletedTaskTitleAndTheCount = 8.dp,
        spacerBetweenListIconAndTaskCount = 4.dp,
        uncompletedTaskCardRoundedCorner = 12.dp,
        uncompletedTaskCardElevation = 8.dp,
        uncompletedTaskCardHeight = 110.dp,
        uncompletedTaskCardPadding = 16.dp,
        uncompletedTaskCardWidth = 120.dp,
        uncompletedCardSpacerBetweenTaskTitleAndTaskDes = 4.dp,
        uncompletedCardSpacerBetweenDescriptionAndTime = 4.dp,
        uncompletedTimeIconSize = 20.dp,
        uncompletedTaskPaddingBetweenTimeIconAndTimeTxt = 4.dp,
        taskCardRounded = 20.dp,
        taskCardElevation = 8.dp,
        taskRowContainerPadding = 8.dp,
        spacerBetweenTaskTitleAndDescription = 8.dp,
        spacerBetweenTaskDescriptionAndTime = 16.dp,
        taskCardDateIconSize = 24.dp,
        taskCardPaddingDateTxt = 4.dp,
        taskCardTimeIconSize = 24.dp,
        taskCardPaddingTimeTxt = 4.dp,
        spacerBetweenColumnAndTimer = 16.dp,
        taskCardTimerWeight = 0.3f,
        spacerIfNoTimerWidth = 0.dp,
        spacerIfNoTimerWeight = 0.3f,
        taskCardPrioritySize = 16.dp,
        taskCardPriorityRounded = CircleShape
    ),
    addEditScreenDimens = AddEditScreenDimens(

    ),
    analysisDimens = AnalysisScreenDimens(

    )
)