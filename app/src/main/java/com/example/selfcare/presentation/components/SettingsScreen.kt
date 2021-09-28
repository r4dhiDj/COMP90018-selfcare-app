package com.example.selfcare.presentation.components.rendering

import android.app.Application
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.selfcare.R
import com.example.selfcare.presentation.components.Screen
import com.example.selfcare.presentation.components.sendNotification
import com.example.selfcare.ui.theme.IBMPlexMono
import com.example.selfcare.ui.theme.Pink700
import com.example.selfcare.viewmodels.MainViewModel
import dagger.hilt.android.internal.Contexts.getApplication
import kotlinx.coroutines.launch

@Composable
fun SettingsScreen(
    viewModel: MainViewModel,
    navController: NavController
    ) {
    var username by remember { mutableStateOf("") }
    var darkMode by remember { mutableStateOf(false) }
    var notifMode by remember { mutableStateOf(true) }
    var firstTime by remember { mutableStateOf(false) }
    //TODO: move to where notification should be called
    var callNotif by remember { mutableStateOf(false) }
    var clickNewUsername by remember { mutableStateOf(false) }

    var context = LocalContext.current
    val scope = rememberCoroutineScope()
    
    LaunchedEffect(key1 = viewModel.darkModeState, viewModel.notifModeState, viewModel.username.value){
        viewModel.getDarkMode()
        viewModel.getNotifMode()
        viewModel.getUsername()
        darkMode = viewModel.darkModeState.value
        notifMode = viewModel.notifModeState.value
        username = viewModel.username.value
    }
    Card(
        modifier = Modifier
            .fillMaxSize(),
        elevation = 8.dp
    ) {
        Column {
            // Top bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Pink700)
                    .padding(15.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {

                IconButton(
                    content = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_chevron_left),
                            contentDescription = "settings",
                            tint = Color.White,
                            modifier = Modifier
                                .padding(10.dp)
                                .clip(RoundedCornerShape(20.dp))
                        )
                    },
                    onClick = { navController.navigate(Screen.MenuScreen.route) }
                )

                Icon(
                    painter = painterResource(id = R.drawable.ic_settings),
                    contentDescription = "settings",
                    tint = Color.White,
                    modifier = Modifier.padding(10.dp)
                )
                Text(
                    text = "Settings",
                    fontFamily = IBMPlexMono,
                    fontWeight = FontWeight.Light,
                    color = Color.White
                )
            }



            //Change username

            var maxChar = 20
            var newUsername by remember { mutableStateOf("") }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, bottom = 12.dp, start = 20.dp, end = 20.dp),
                Arrangement.SpaceBetween,
                Alignment.CenterVertically,
            ){
                Text(
                    text = "Username: $username",
                    style = MaterialTheme.typography.h3
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, bottom = 12.dp, start = 20.dp, end = 20.dp),
                horizontalArrangement = Arrangement.Start
            ){
                Button(
                    onClick = {
                        clickNewUsername = true
                    }
                )
                {
                    Text(color = Color.White, text = "Change Username")
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp, bottom = 12.dp, start = 20.dp, end = 20.dp),
                Arrangement.SpaceBetween,
                Alignment.CenterVertically
            )
            {
                OutlinedTextField(
                    value = newUsername,
                    enabled = clickNewUsername,
                    readOnly = !clickNewUsername,
                    onValueChange = { newUsername = it },
                    label = { Text("New Username") }
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp, bottom = 12.dp, start = 20.dp, end = 20.dp),
                horizontalArrangement = Arrangement.Start,
            )
            {
                Button(
                    onClick = {
                        username = newUsername
                        viewModel.setUsername(newUsername)
                        clickNewUsername = false
                    }
                ){
                    Text(color = Color.White, text = "Save New Username")
                }
            }

            //dark mode settings
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, bottom = 12.dp, start = 20.dp, end = 20.dp),
                Arrangement.SpaceBetween,
                Alignment.CenterVertically,

                ) {
                Text(
                    text = "Dark Mode",
                    style = MaterialTheme.typography.h3
                )
                Switch(
                    checked = darkMode,
                    onCheckedChange = {
                        darkMode = it
                        viewModel.setDarkMode(it)
                        } ,
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = MaterialTheme.colors.primary,
                        checkedTrackColor = MaterialTheme.colors.primary,
                        uncheckedThumbColor = MaterialTheme.colors.onSurface,
                        uncheckedTrackColor = MaterialTheme.colors.onSurface
                    )
                )
            }

            //notification settings
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp, bottom = 12.dp, start = 20.dp, end = 20.dp),
                Arrangement.SpaceBetween,
                Alignment.CenterVertically
            ) {
                Text(
                    "Notification",
                    style = MaterialTheme.typography.h3
                )
                Switch(
                    checked = notifMode,
                    onCheckedChange = {
                        notifMode = it
                        viewModel.setNotifMode(it)},
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = MaterialTheme.colors.primary,
                        checkedTrackColor = MaterialTheme.colors.primary,
                        uncheckedThumbColor = MaterialTheme.colors.onSurface,
                        uncheckedTrackColor = MaterialTheme.colors.onSurface
                    )
                )
            }



            //reset button
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp, bottom = 12.dp, start = 20.dp, end = 20.dp),
                Arrangement.SpaceBetween,
                Alignment.CenterVertically
            ) {
                Text(
                    "Reset App",
                    style = MaterialTheme.typography.h3
                )
                Button(
                    colors = ButtonDefaults.textButtonColors(
                        backgroundColor = MaterialTheme.colors.error),
                    onClick = {
                        viewModel.deleteSettingsData()
                    }
                ) {
                    Text(color = Color.White, text = "Reset")
                }
            }


            //Rows below are just to test navigation, to be move later.
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 12.dp, bottom = 12.dp, start = 20.dp, end = 20.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(onClick = { callNotif = true }) {
                    Text("Test Notification")
                }
            }

        }

        if (callNotif) {
            sendNotification(
                context = context,
                notifTitle = "Hellothere",
                notifText = "selfcareeirfjoeifoerijeorij"
            )
            Text("Hello")
            callNotif = false

        }
    }
}

