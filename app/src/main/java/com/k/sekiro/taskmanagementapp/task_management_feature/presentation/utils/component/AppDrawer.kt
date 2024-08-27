package com.k.sekiro.taskmanagementapp.task_management_feature.presentation.utils.component

import android.content.*
import android.content.pm.*
import android.net.*
import android.util.*
import android.widget.*
import androidx.appcompat.app.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.platform.*
import androidx.compose.ui.res.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.text.style.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*
import androidx.core.content.*
import androidx.core.os.*
import androidx.datastore.core.*
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.*
import androidx.navigation.*
import com.k.sekiro.taskmanagementapp.*
import com.k.sekiro.taskmanagementapp.R
import com.k.sekiro.taskmanagementapp.task_management_feature.presentation.utils.*
import com.k.sekiro.taskmanagementapp.ui.theme.*
import kotlinx.coroutines.*
import java.io.*


@Composable
fun AppDrawer(
    dataStore: DataStore<Preferences>,
    coroutineScope: CoroutineScope,
    navController: NavHostController,
    drawerState: DrawerState
) {

    Column (
        Modifier.padding(16.dp).fillMaxWidth(MaterialTheme.dimens.appDrawerDimens.drawerWidth)
    ){
        DrawerHeader()

        HorizontalDivider(
            color = MaterialTheme.colorScheme.primary,
            thickness = MaterialTheme.dimens.appDrawerDimens.horizontalDividerThickness,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        DrawerContent(
            coroutineScope = coroutineScope,
            dataStore = dataStore,
            navController =  navController,
            drawerState = drawerState
        )

        DrawerFooter(
            modifier = Modifier.weight(1f),
            dataStore = dataStore,
            coroutineScope = coroutineScope
        )
    }
}


@Preview
@Composable
private fun DrawerHeader() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(
                id = if (AppThemeSettings.isDarkTheme) R.drawable.logo2_dark else R.drawable.logo2_light_0_
            ),
            contentDescription = null,
            modifier = Modifier.size(MaterialTheme.dimens.appDrawerDimens.logoSize)
        )

/*        Image(
            painter = painterResource(
                id = R.drawable.todo_logo1_0_),
            contentDescription = "logo",
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
        )*/


        Text(
            text = appNameStyle() ,
            modifier = Modifier.padding(top = MaterialTheme.dimens.appDrawerDimens.appNamePadding),
            textAlign = TextAlign.Center,
        )
    }
}


@Composable
private fun DrawerContent(
    modifier: Modifier = Modifier,
    dataStore: DataStore<Preferences>,
    navController: NavHostController,
    drawerState: DrawerState,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
) {

val context = LocalContext.current

    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .fillMaxWidth()
                .clickable {
                    coroutineScope.launch {
                        drawerState.close()
                    }
                    navController.navigate(Screens.Instructions.route){
                        launchSingleTop = true
                    }
                }
                .padding(vertical = 8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ContentPaste,
                contentDescription = null,
                modifier = Modifier.size(MaterialTheme.dimens.appDrawerDimens.iconsSize)
            )
            Text(
                text = stringResource(id = R.string.instructions_before_use),
                modifier = Modifier.padding(start = MaterialTheme.dimens.appDrawerDimens.iconsTextStartPadding)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))


        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    coroutineScope.launch {
                        launch { drawerState.close() }
                        launch { shareAppAsAPK(context) }

                    }
                }
                .padding(vertical = 8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Share,
                contentDescription = null,
                modifier = Modifier.size(MaterialTheme.dimens.appDrawerDimens.iconsSize)
            )
            Text(
                text = stringResource(id = R.string.share_app),
                modifier = Modifier.padding(start = MaterialTheme.dimens.appDrawerDimens.iconsTextStartPadding)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))


        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    coroutineScope.launch {
                        drawerState.close()
                    }
                    shareAppAsLink(context)
                }
                .padding(vertical = 8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Link,
                contentDescription = null,
                modifier = Modifier.size(MaterialTheme.dimens.appDrawerDimens.iconsSize)
            )
            Text(
                text = stringResource(id = R.string.share_download_link),
                modifier = Modifier.padding(start = MaterialTheme.dimens.appDrawerDimens.iconsTextStartPadding)
            )
        }

        Spacer(modifier = Modifier.height(MaterialTheme.dimens.appDrawerDimens.spacerBetweenLanguageSelectorAndItemsOnTop))

        LocaleDropdownMenu(scope = coroutineScope,dataStore = dataStore)
        
    }
}


@Composable
private fun DrawerFooter(
    modifier: Modifier = Modifier,
    dataStore: DataStore<Preferences>,
    coroutineScope: CoroutineScope
) {

    val checked =  AppThemeSettings.isDarkTheme

    
    Spacer(modifier = modifier)


    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth(),
    ) {
        Text(
            text = stringResource(id = R.string.theme),
            fontFamily = FontFamily(Font(R.font.bison_font)),
            fontSize = MaterialTheme.typography.labelLarge.fontSize,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.weight(1f)
        )

        CustomSwitch(
            checked = checked,
            onCheckedChange = {
                coroutineScope.launch {
                    dataStore.edit { preferences ->
                        val key = booleanPreferencesKey("isDarkTheme")
                        preferences[key] = !AppThemeSettings.isDarkTheme


                        Log.e("ks","theme in footer: ${preferences[key]}")
                    }
                }
            },
            thumbContent = {
                Icon(
                    imageVector = if (checked) Icons.Default.DarkMode else Icons.Default.LightMode,
                    contentDescription = null,
                    modifier = Modifier.padding(1.dp).size(MaterialTheme.dimens.appDrawerDimens.iconsSize),
                )
            },

            colors = SwitchDefaults.colors(
                checkedThumbColor = MaterialTheme.colorScheme.background,
                uncheckedTrackColor = MaterialTheme.colorScheme.primary,
                uncheckedBorderColor = MaterialTheme.colorScheme.inversePrimary,
                checkedBorderColor = MaterialTheme.colorScheme.inversePrimary,
                uncheckedThumbColor = Color.Black
            ),

        )

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocaleDropdownMenu(
    scope: CoroutineScope,
    dataStore:DataStore<Preferences>
) {

    val context = LocalContext.current

    var selectedLang by rememberSaveable {
        mutableStateOf(context.getString(R.string.system))
    }


    LaunchedEffect(key1 = Unit) {
        dataStore.edit {
           val lang =  it[stringPreferencesKey("appLang")]?:context.getString(R.string.system)
            if (lang == "system"){
                selectedLang = context.getString(R.string.system)
            }else{
                selectedLang = lang
            }
        }
    }

    val localeOptions = mapOf(
        R.string.en to "en",
        R.string.ar to "ar",

    ).mapKeys { stringResource(it.key) }

    // boilerplate: https://developer.android.com/reference/kotlin/androidx/compose/material/package-summary#ExposedDropdownMenuBox(kotlin.Boolean,kotlin.Function1,androidx.compose.ui.Modifier,kotlin.Function1)
    var expanded by remember {
        mutableStateOf(false)
    }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }
    ) {
        TextField(
            readOnly = true,
            value = selectedLang/*stringResource(R.string.language)*/,
            onValueChange = { },
            trailingIcon = {
                ExposedDropdownMenuDefaults.CustomTrailingIcon(
                    expanded = expanded,
                    size = MaterialTheme.dimens.appDrawerDimens.dropDownMenuExposedIconSize
                )
            },
            modifier = Modifier.menuAnchor().width(MaterialTheme.dimens.appDrawerDimens.languageSelectorWidth)
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            }
        ) {

            DropdownMenuItem(
                text = { Text(text = stringResource(R.string.system), fontSize = MaterialTheme.typography.bodyLarge.fontSize) },
                onClick = {
                    expanded = false
                    scope.launch(Dispatchers.Main){
                        delay(200)
                        AppCompatDelegate.setApplicationLocales(
                            LocaleListCompat.getEmptyLocaleList()
                        )
                    }

                    scope.launch(Dispatchers.IO){
                        dataStore.edit {
                            it[stringPreferencesKey("appLang")] = "system"
                        }
                    }
                }
            )

            localeOptions.keys.forEach { selectionLocale ->
                DropdownMenuItem(
                    onClick = {
                        expanded = false

                        scope.launch(Dispatchers.Main){
                            delay(200)
                            // set app locale given the user's selected locale
                            AppCompatDelegate.setApplicationLocales(
                                LocaleListCompat.forLanguageTags(
                                    localeOptions[selectionLocale]
                                )
                            )
                        }

                        scope.launch(Dispatchers.IO){
                            dataStore.edit {
                                it[stringPreferencesKey("appLang")] = selectionLocale
                            }
                        }


                    },
                    text = { Text(text = selectionLocale, fontSize = MaterialTheme.typography.bodyLarge.fontSize) }
                )
            }
        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun ExposedDropdownMenuDefaults.CustomTrailingIcon(
    expanded: Boolean,
    size: Dp
) {
    Icon(
        Icons.Filled.ArrowDropDown,
        null,
        Modifier.size(size).rotate(if (expanded) 180f else 0f),
    )
}


private suspend fun shareAppAsAPK(context: Context) {
    val app: ApplicationInfo = context.applicationInfo
    val originalApk = app.publicSourceDir
    val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Toast.makeText(context,throwable.message,Toast.LENGTH_SHORT).show()
    }

    withContext(Dispatchers.IO + coroutineExceptionHandler){
        try {
            //Make new directory in new location
            var tempFile: File = File(context.getExternalFilesDir(null).toString() + "/ExtractedApk")
            //If directory doesn't exists create new
            if (!tempFile.isDirectory) if (!tempFile.mkdirs()) return@withContext
            //rename apk file to app name
            tempFile = File(tempFile.path + "/" + context.getString(app.labelRes).replace(" ", "") + ".apk")
            //If file doesn't exists create new
            if (!tempFile.exists()) {
                if (!tempFile.createNewFile()) {
                    return@withContext
                }
            }
            //Copy file to new location
            val inp: InputStream = FileInputStream(originalApk)
            val out: OutputStream = FileOutputStream(tempFile)
            val buf = ByteArray(1024)
            var len: Int
            while (inp.read(buf).also { len = it } > 0) {
                out.write(buf, 0, len)
            }
            inp.close()
            out.close()
            withContext(Dispatchers.Main){
                //Open share dialog
                val intent = Intent(Intent.ACTION_SEND)
//MIME type for apk, might not work in bluetooth sahre as it doesn't support apk MIME type

                intent.type = "application/vnd.android.package-archive"
                intent.putExtra(
                    Intent.EXTRA_STREAM, FileProvider.getUriForFile(
                        context, BuildConfig.APPLICATION_ID + ".provider", File(tempFile.path)
                    )
                )
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                context.startActivity(intent)
            }

        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

}


private fun shareAppAsLink(context: Context){
    val url = "https://drive.google.com/file/d/1iQwnFCyUSVS58yr0XczHX4cCciVwpsAm/view?usp=drive_link"

   val sendIntent =  Intent(Intent.ACTION_SEND).apply {
        putExtra(Intent.EXTRA_TEXT,url)
       type = "text/plain"
    }

    val openIntent = Intent(Intent.ACTION_VIEW).apply {
        flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        data = Uri.parse(url)
    }

    val shareIntent = Intent.createChooser(
        sendIntent,
        ContextCompat.getContextForLanguage(context).getString(R.string.share_link_via)
    ).apply {
        putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(openIntent))
    }

    context.startActivity(shareIntent)
}