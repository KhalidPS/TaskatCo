package com.k.sekiro.taskmanagementapp.task_management_feature.presentation.utils.component

import android.util.*
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.os.LocaleListCompat
import androidx.datastore.core.*
import androidx.datastore.preferences.core.*
import com.k.sekiro.taskmanagementapp.R
import kotlinx.coroutines.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.draw.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.*
import com.k.sekiro.taskmanagementapp.ui.theme.*
import kotlin.math.exp


@Composable
fun AppDrawer(
    dataStore: DataStore<Preferences>,
    coroutineScope: CoroutineScope
) {

    Column (
        Modifier.padding(16.dp)
    ){
        DrawerHeader()

        HorizontalDivider(
            color = MaterialTheme.colorScheme.primary,
            thickness = MaterialTheme.dimens.appDrawerDimens.horizontalDividerThickness,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        DrawerContent(coroutineScope = coroutineScope,dataStore = dataStore)

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
    coroutineScope: CoroutineScope = rememberCoroutineScope()
) {


    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .fillMaxWidth()
                .clickable {
                    // TODO() //
                }
                .padding(vertical = 8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ContentPaste,
                contentDescription = null,
                modifier = Modifier.size(MaterialTheme.dimens.appDrawerDimens.iconsSize)
            )
            Text(
                text = stringResource(id = R.string.check_app_trick),
                modifier = Modifier.padding(start = MaterialTheme.dimens.appDrawerDimens.iconsTextStartPadding)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))


        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    //TODO()//
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

    var checked =  AppThemeSettings.isDarkTheme

    
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