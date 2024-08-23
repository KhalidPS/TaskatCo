package com.k.sekiro.taskmanagementapp.task_management_feature.presentation.utils.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PieChart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.k.sekiro.taskmanagementapp.task_management_feature.presentation.MainActivity
import com.k.sekiro.taskmanagementapp.task_management_feature.presentation.utils.Screens
import com.k.sekiro.taskmanagementapp.ui.theme.AppThemeSettings
import com.k.sekiro.taskmanagementapp.ui.theme.DarkPrimaryColor
import com.k.sekiro.taskmanagementapp.ui.theme.LightPrimaryColor


@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun AppBottomBar(
    //selectedScreen: String = Screens.Tasks.route,
    navController: NavHostController ,
    onNavIconClicked: (Screens) -> Unit = {},
    onAddClicked: () -> Unit = {},
    modifier: Modifier = Modifier
) {

    val underFloatingButtonColor = MaterialTheme.colorScheme.background.copy(alpha = .7f)
    //val iconColor = if (selectedScreen == 0) MaterialTheme.colorScheme.inversePrimary else MaterialTheme.colorScheme.primary
    val bottomBarBackground =
        if (AppThemeSettings.isDarkTheme) Color.Black else Color.White
    val currentNavStack by navController.currentBackStackEntryAsState()

    val window = calculateWindowSizeClass(activity = LocalContext.current as MainActivity)
    val config = LocalConfiguration.current
    val screenHeight = config.screenHeightDp


    var boxHeight = (screenHeight / 5).dp
    var fabOffsetX = 36.dp
    var fabOffsetY = 10.dp
    var radiusUnderFabSize = 50.dp
    var fabSize = 72.dp
    var fabRounded = 100.dp
    var iconsSize = 22.dp


    when (window.heightSizeClass) {
        WindowHeightSizeClass.Compact -> {

        /*      boxHeight = (screenHeight / 5).dp
                fabOffsetY = 1.dp
                fabOffsetX = 36.dp
                radiusUnderFabSize = 50.dp
                fabSize = 72.dp
                fabRounded = 100.dp
                iconsSize = 22.dp*/


            if (config.screenWidthDp <= 360) {
                boxHeight = (screenHeight / 5).dp
                fabOffsetY = 1.dp
                fabOffsetX = 36.dp
                radiusUnderFabSize = 50.dp
                fabSize = 72.dp
                fabRounded = 100.dp
                iconsSize = 22.dp
            } else if (config.screenWidthDp < 600) {
                boxHeight = (screenHeight / 5).dp
                fabOffsetY = 1.dp
                fabOffsetX = 25.dp
                radiusUnderFabSize = 40.dp
                fabSize = 50.dp
                fabRounded = 100.dp
                iconsSize = 18.dp
            } else {
                boxHeight = (screenHeight / 5).dp
                fabOffsetY = 1.dp
                fabOffsetX = 36.dp
                radiusUnderFabSize = 50.dp
                fabSize = 72.dp
                fabRounded = 100.dp
                iconsSize = 22.dp
            }
        }

        WindowHeightSizeClass.Medium -> {

            /*  boxHeight = (screenHeight / 10).dp
                fabOffsetY = 1.dp
                fabOffsetX = 36.dp
                radiusUnderFabSize = 50.dp
                fabSize = 72.dp
                fabRounded = 100.dp
                iconsSize = 22.dp*/


            if (config.screenWidthDp <= 360) {
                boxHeight = (screenHeight / 8).dp
                fabOffsetY = 1.dp
                fabOffsetX = 25.dp
                radiusUnderFabSize = 40.dp
                fabSize = 50.dp
                fabRounded = 100.dp
                iconsSize = 18.dp
            } else if (config.screenWidthDp < 600) {
                boxHeight = (screenHeight / 10).dp
                fabOffsetY = 1.dp
                fabOffsetX = 36.dp
                radiusUnderFabSize = 50.dp
                fabSize = 72.dp
                fabRounded = 100.dp
                iconsSize = 22.dp
            } else {
                boxHeight = 120.dp
                fabOffsetY = 20.dp
                fabOffsetX = 60.dp
                radiusUnderFabSize = 100.dp
                fabSize = 120.dp
                fabRounded = 100.dp
                iconsSize = 50.dp
            }


        }

        WindowHeightSizeClass.Expanded -> {

            /*  boxHeight = 120.dp
                fabOffsetY = 20.dp
                fabOffsetX = 65.dp
                radiusUnderFabSize = 100.dp
                fabSize = 130.dp
                fabRounded = 100.dp
                iconsSize = 50.dp*/


            if (config.screenWidthDp < 600) {
                boxHeight = (screenHeight / 5).dp
                fabOffsetY = 1.dp
                fabOffsetX = 36.dp
                radiusUnderFabSize = 50.dp
                fabSize = 72.dp
                fabRounded = 100.dp
                iconsSize = 22.dp
            } else {
                boxHeight = 120.dp
                fabOffsetY = 20.dp
                fabOffsetX = 60.dp
                radiusUnderFabSize = 100.dp
                fabSize = 120.dp
                fabRounded = 100.dp
                iconsSize = 50.dp
            }

        }
    }




    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .height(boxHeight),
        contentAlignment = Alignment.BottomStart
    ) {
        val width = this.minWidth
        val height = this.minHeight
        Card(
            elevation = CardDefaults.cardElevation(0.dp),
            modifier = Modifier.shadow(
                /*blurRadius = 16.dp,
                color = Color(0x14000000),
                offsetY = (-4).dp*/
                elevation = 16.dp,
                ambientColor = Color(0x14000000),
                spotColor = Color(0x14000000),
                shape = RoundedCornerShape(
                    topStart = 30.dp,
                    topEnd = 30.dp,
                    bottomEnd = 0.dp,
                    bottomStart = 0.dp
                )
            ),
            shape = RoundedCornerShape(
                topStart = 30.dp,
                topEnd = 30.dp,
                bottomEnd = 0.dp,
                bottomStart = 0.dp
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(boxHeight)
                    .clip(
                        RoundedCornerShape(
                            topStart = 24.dp,
                            topEnd = 24.dp,
                            bottomStart = 0.dp,
                            bottomEnd = 0.dp
                        )
                    )
                    .graphicsLayer {
                        compositingStrategy = CompositingStrategy.Offscreen
                    }
                    .drawWithContent {
                        drawContent()

                        drawCircle(
                            color = underFloatingButtonColor,
                            radius = radiusUnderFabSize.toPx(),
                            center = Offset(x = size.width / 2, y = 0f),
                            blendMode = BlendMode.Modulate,
                        )
                    }

                    //  color = Color.Black, offsetY = (-20).dp, spread = 20.dp
                    .shadow(
                        elevation = 20.dp,
                        ambientColor = Color.Black,
                        spotColor = Color.Black,
                    )
                    .background(bottomBarBackground)
                    .padding(horizontal = 48.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconButton(
                    onClick = { onNavIconClicked(Screens.Tasks) }
                ) {
                    Icon(
                        imageVector = Icons.Default.Home,
                        contentDescription = null,
                        tint = if (currentNavStack?.destination?.route == Screens.Tasks.route) DarkPrimaryColor else LightPrimaryColor.copy(
                            alpha = .5f
                        ),
                        modifier = Modifier.size(iconsSize)
                    )
                }
                IconButton(
                    onClick = { onNavIconClicked(Screens.Analysis) }
                ) {
                    Icon(
                        imageVector = Icons.Default.PieChart,
                        contentDescription = null,
                        tint = if (currentNavStack?.destination?.route == Screens.Analysis.route) DarkPrimaryColor else LightPrimaryColor.copy(
                            alpha = .5f
                        ),
                        modifier = Modifier.size(iconsSize)
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .offset(width.div(2) - fabOffsetX, -height.div(2) + fabOffsetY)
                .shadow(
                    elevation = 8.dp,
                    shape = RoundedCornerShape(100.dp),
                    ambientColor = MaterialTheme.colorScheme.background,
                    spotColor = MaterialTheme.colorScheme.background
                )
                .size(fabSize)
                .clip(RoundedCornerShape(fabRounded))
                .background(MaterialTheme.colorScheme.primary)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    onAddClicked()
                },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "",
                tint = MaterialTheme.colorScheme.background,
                modifier = Modifier.size(iconsSize)
            )
        }
    }
}