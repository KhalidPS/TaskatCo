package com.k.sekiro.taskmanagementapp.ui.theme

import android.app.Activity
import android.content.Context
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.datastore.core.*
import androidx.datastore.preferences.*
import androidx.datastore.preferences.core.*
import com.k.sekiro.taskmanagementapp.task_management_feature.presentation.MainActivity
import com.k.sekiro.taskmanagementapp.task_management_feature.presentation.utils.AppTheme
import com.k.sekiro.taskmanagementapp.task_management_feature.presentation.utils.CompactDimens
import com.k.sekiro.taskmanagementapp.task_management_feature.presentation.utils.CompactSmallDimens
import com.k.sekiro.taskmanagementapp.task_management_feature.presentation.utils.CompatMediumDimens
import com.k.sekiro.taskmanagementapp.task_management_feature.presentation.utils.ExpandedDimens
import com.k.sekiro.taskmanagementapp.task_management_feature.presentation.utils.LocalAppDimens
import com.k.sekiro.taskmanagementapp.task_management_feature.presentation.utils.MediumDimens
import javax.inject.Inject
import kotlin.properties.*

private val DarkColorScheme = darkColorScheme(
    primary = DarkPrimaryColor,
    secondary = DarkPrimaryColor,
    inversePrimary = LightPrimaryColor,
    tertiary = LightPrimaryColor,

)

private val LightColorScheme = lightColorScheme(
    primary = LightPrimaryColor,
    secondary = LightPrimaryColor,
    inversePrimary = DarkPrimaryColor,
    tertiary = DarkPrimaryColor,

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun TaskManagementAppTheme(
    darkTheme: Boolean =  AppThemeSettings.isDarkTheme,
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    activity:Activity = LocalContext.current as MainActivity,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    val window = calculateWindowSizeClass(activity = activity)
    val configuration = LocalConfiguration.current



    var typography = CompactTypography
    var appDimens = CompactDimens


    when(window.widthSizeClass){
        WindowWidthSizeClass.Compact -> {
            if (configuration.screenWidthDp <= 360){
                appDimens = CompactSmallDimens
                typography = CompactSmallTypography
            }else if (configuration.screenWidthDp < 600){
                appDimens = CompatMediumDimens
                typography = CompactMediumTypography
            }else{
                appDimens = CompactDimens
                typography = CompactTypography
            }
        }

        WindowWidthSizeClass.Medium -> {
            appDimens = MediumDimens
            typography = MediumTypography
        }

        WindowWidthSizeClass.Expanded -> {
            appDimens = ExpandedDimens
            typography = ExpandedTypography
        }
    }

    AppTheme(dimens = appDimens) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = typography,
            content = content
        )
    }



}


val MaterialTheme.dimens
    @Composable
    get() = LocalAppDimens.current

object AppThemeSettings{

    var isDarkTheme by mutableStateOf(false)
}