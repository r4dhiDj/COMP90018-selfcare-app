package com.example.selfcare.presentation.components

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.selfcare.R
import com.example.selfcare.ui.theme.IBMPlexMono
import com.example.selfcare.ui.theme.Pink700
import com.example.selfcare.ui.theme.SelfCareTheme

@Composable
fun SettingsScreen (navController: NavController, context: Context
) {
    val darkModeState = remember { mutableStateOf(false) }
    val notificationState = remember { mutableStateOf(true) }


    //TODO: move to where notification should be called
    val callNotif = remember { mutableStateOf(false) }


    SelfCareTheme(darkMode = darkModeState.value) {
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
                        checked = darkModeState.value,
                        onCheckedChange = { darkModeState.value = it },
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
                        checked = notificationState.value,
                        onCheckedChange = { notificationState.value = it },
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
                        onClick = { /*TODO*/ }
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
                    Button(onClick = { callNotif.value = true }) {
                        Text("Test Notification")
                    }
                }

                }

            if (callNotif.value) {
                sendNotification(
                    context = context,
                    notifTitle = "Hellothere",
                    notifText = "selfcareeirfjoeifoerijeorij"
                )
                Text("Hello")
                callNotif.value = false

            }
        }
    }
}


