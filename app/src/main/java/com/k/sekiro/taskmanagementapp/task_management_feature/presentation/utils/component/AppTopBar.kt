package com.k.sekiro.taskmanagementapp.task_management_feature.presentation.utils.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import com.k.sekiro.taskmanagementapp.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    onNavIconClicked: () -> Unit,
) {
    TopAppBar(
        title = {
            Text(
                text = appNameStyle(),
                modifier = Modifier.padding(start = MaterialTheme.dimens.homeScreenDimens.menuIconEndPadding)
                )
        },
        navigationIcon = {
            IconButton(onClick = onNavIconClicked) {
                Icon(
                    imageVector = Icons.Default.Menu, contentDescription = null,
                    modifier = Modifier.size(MaterialTheme.dimens.homeScreenDimens.menuIconSize)
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
         windowInsets = MaterialTheme.dimens.homeScreenDimens.tobBarWindowInsets
    )
}