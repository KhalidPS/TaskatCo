package com.k.sekiro.taskmanagementapp.task_management_feature.presentation.request_permission_screen

import android.Manifest
import android.app.*
import android.content.*
import android.content.pm.*
import android.os.*
import android.provider.*
import androidx.activity.compose.*
import androidx.activity.result.contract.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.platform.*
import androidx.compose.ui.res.*
import androidx.navigation.*
import com.k.sekiro.taskmanagementapp.R
import com.k.sekiro.taskmanagementapp.task_management_feature.presentation.*
import com.k.sekiro.taskmanagementapp.task_management_feature.presentation.utils.*

@Composable
fun RequestPermissionScreen(
    //navController:NavHostController,
    context: Activity = LocalContext.current as MainActivity,
    onGrantedShowScreen: @Composable () -> Unit
) {


    var showDialog by remember {
        mutableStateOf(false)
    }

    var goToSettings  by remember {
        mutableStateOf(false)
    }

    var permission by remember {
        mutableStateOf(Manifest.permission.SCHEDULE_EXACT_ALARM)
    }


    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { result: Boolean ->
            if (!result) {
                if (!context.shouldShowRequestPermissionRationale(permission)) {
                    goToSettings = true
                }
                showDialog = true
            }
        }
    )




    if (showDialog) {
        PermissionRationalDialog(
            onDismissRequest = { showDialog = false },
            onConfirmRequest = {
                showDialog = false
                if (goToSettings) {
                    showDialog = false
                    Intent().also { intent ->
                        intent.action = Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM
                        context.startActivity(intent)
                    }
                    showDialog = false
                } else {

                    requestPermissionLauncher.launch(permission)
                }

            }
        )
    }

    val isGranted =
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.S) {
            permission = Manifest.permission.SCHEDULE_EXACT_ALARM
            context.checkSelfPermission(Manifest.permission.SCHEDULE_EXACT_ALARM) == PackageManager.PERMISSION_GRANTED
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permission = Manifest.permission.USE_EXACT_ALARM
            context.checkSelfPermission(Manifest.permission.USE_EXACT_ALARM) == PackageManager.PERMISSION_GRANTED
        } else true


    if (isGranted) {
        showDialog = false
        onGrantedShowScreen()
    } else {
        if (context.shouldShowRequestPermissionRationale(permission)) {
            showDialog = true
        } else {
            SideEffect {
                requestPermissionLauncher.launch(permission)

            }

        }
    }


}




@Composable
fun PermissionRationalDialog(
    onDismissRequest: () -> Unit,
    onConfirmRequest: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            Button(onClick = onConfirmRequest) {
                Text(text = stringResource(id = R.string.cancel_btn))
            }
        },
        title = {
            Text(text = stringResource(id = R.string.permission_msg_title))
        },
        text = {
            Text(text = stringResource(id = R.string.permission_msg_content))
        }
    )

}