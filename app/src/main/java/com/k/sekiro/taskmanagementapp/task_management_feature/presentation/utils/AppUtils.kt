package com.k.sekiro.taskmanagementapp.task_management_feature.presentation.utils

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalConfiguration
import androidx.navigation.*
import androidx.navigation.compose.*


@Composable
fun AppTheme(
    dimens: ScreensDimens,
    content:@Composable () -> Unit
) {

    val appDimens = remember {
        dimens
    }

    CompositionLocalProvider(value = LocalAppDimens provides appDimens) {
        content()
    }

}

val LocalAppDimens = compositionLocalOf {
    CompactDimens
}

val ScreenOrientation
@Composable
get() = LocalConfiguration.current.orientation


@Composable
fun getCurrentDestination(navHostController: NavHostController):String?{
    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}

