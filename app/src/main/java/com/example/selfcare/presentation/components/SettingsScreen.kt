package com.example.selfcare.presentation.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.selfcare.R
import com.example.selfcare.ui.theme.Blue700
import com.example.selfcare.ui.theme.IBMPlexMono
import com.example.selfcare.ui.theme.Teal700
import com.example.selfcare.viewmodels.MainViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


@Composable
fun SettingsScreen(
    viewModel: MainViewModel,
    navController: NavController
    ) {

    var username by remember { mutableStateOf("") }
    var darkMode by remember { mutableStateOf(false) }
    var notifMode by remember { mutableStateOf(true) }
    var clickUpdateProfile by remember { mutableStateOf(false) }
    var email by remember { mutableStateOf("")}
    val scrollState = rememberScrollState()

    val context = LocalContext.current
    
    LaunchedEffect(key1 = viewModel.darkModeState) {
        viewModel.getDarkMode()
        viewModel.getUsername()
        viewModel.getUserEmail()
        darkMode = viewModel.darkModeState.value
        username = viewModel.displayName.value
        email = viewModel.email.value
        Log.d("inside settings email", email)
        Log.d("inside settings username", username)
    }

    Scaffold (
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Blue700)
                    .padding(15.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {

                IconButton(
                    content = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_chevron_left),
                            contentDescription = "back",
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
        },
        content = {
            Card(
                modifier = Modifier
                    .fillMaxSize(),
                elevation = 8.dp
            ) {
                Column(
                    modifier = Modifier
                        .verticalScroll(scrollState)
                ) {

                    Text(
                        text = "User Details",
                        modifier = Modifier.padding(
                            top = 20.dp,
                            bottom = 12.dp,
                            start = 20.dp,
                            end = 20.dp
                        ),
                        fontWeight = FontWeight.Medium,
                        style = TextStyle(
                            fontSize = 22.sp
                        )
                    )

                    Column (
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        //user details
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 20.dp, bottom = 12.dp, start = 20.dp, end = 20.dp),
                            Arrangement.SpaceBetween,
                            Alignment.CenterVertically,
                        ) {
                            Text(
                                text = "Username",
                                fontWeight = FontWeight.SemiBold
                            )

                            Text(
                                text = username,
                                fontWeight = FontWeight.Light
                            )
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 20.dp, bottom = 12.dp, start = 20.dp, end = 20.dp),
                            Arrangement.SpaceEvenly,
                            Alignment.CenterVertically,
                        ) {
                            Text(
                                text = "Email",
                                fontWeight = FontWeight.SemiBold
                            )

                            Text(
                                text = email,
                                fontWeight = FontWeight.Light
                            )
                        }
                    }

                    Text(
                        text = "Update Profile",
                        modifier = Modifier.padding(
                            top = 20.dp,
                            bottom = 12.dp,
                            start = 20.dp,
                            end = 20.dp
                        ),
                        fontWeight = FontWeight.Medium,
                        style = TextStyle(
                            fontSize = 22.sp
                        )
                    )

                    //update profile and save profile
                    var newUsername by remember { mutableStateOf("") }
                    var newPassword by remember { mutableStateOf("") }
                    var validChanges by remember { mutableStateOf(false) }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp, bottom = 12.dp, start = 20.dp, end = 20.dp),
                        Arrangement.SpaceBetween,
                        Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Password
                            ),
                            value = newUsername,
                            onValueChange = { newUsername = it },
                            label = { Text("Update Username") }
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp, bottom = 12.dp, start = 20.dp, end = 20.dp),
                        Arrangement.SpaceBetween,
                        Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            singleLine = true,
                            visualTransformation = PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Password
                            ),
                            value = newPassword,
                            onValueChange = { newPassword = it },
                            label = { Text("Update Password") }
                        )
                    }

                    if (newUsername.trim() != "" || newPassword.trim() !== "") {
                        validChanges = true
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp, bottom = 12.dp, start = 20.dp, end = 20.dp),
                        horizontalArrangement = Arrangement.Start,
                    ) {
                        Button(
                            enabled = validChanges,
                            onClick = {
                                if (newPassword.trim() != "") {
                                    viewModel.setUserPassword(newPassword)
                                }
                                if (newUsername.trim() != "") {
                                    username = newUsername
                                    viewModel.setUsername(newUsername)
                                }
                            }
                        ) {
                            Text(color = Color.White, text = "Save Updates")
                        }
                    }

                    Text(
                        text = "App Settings",
                        modifier = Modifier.padding(
                            top = 20.dp,
                            bottom = 12.dp,
                            start = 20.dp,
                            end = 20.dp
                        ),
                        fontWeight = FontWeight.Medium,
                        style = TextStyle(
                            fontSize = 22.sp
                        )
                    )

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
//                            modifier = Modifier.background(Blue700),
                            checked = notifMode,
                            onCheckedChange = {
                                notifMode = it
                                viewModel.setNotifMode(it)
                            },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = MaterialTheme.colors.primary,
                                checkedTrackColor = MaterialTheme.colors.primary,
                                uncheckedThumbColor = MaterialTheme.colors.onSurface,
                                uncheckedTrackColor = MaterialTheme.colors.onSurface
                            )
                        )
                    }

                    //sign out
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp, bottom = 12.dp, start = 20.dp, end = 20.dp),
                        Arrangement.SpaceBetween,
                        Alignment.CenterVertically
                    ) {
                        Text(
                            "Sign Out",
                            style = MaterialTheme.typography.h3
                        )
                        Button(
                            onClick = {
                                viewModel.signOut()
                                navController.navigate(Screen.RegisterScreen.route)
                            }
                        ) {
                            Text(color = Color.White, text = "Sign Out")
                        }
                    }


                    //delete account
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp, bottom = 12.dp, start = 20.dp, end = 20.dp),
                        Arrangement.SpaceBetween,
                        Alignment.CenterVertically
                    ) {
                        Text(
                            "Delete Account",
                            style = MaterialTheme.typography.h3
                        )
                        Button(
                            colors = ButtonDefaults.textButtonColors(
                                backgroundColor = MaterialTheme.colors.error
                            ),
                            onClick = {
                                username = ""
                                darkMode = false
                                notifMode = true
                                viewModel.setDarkMode(darkMode)
                                viewModel.setUsername(username)
                                viewModel.setNotifMode(notifMode)
                                navController.popBackStack()
                                viewModel.deleteUser()
                                while (Firebase.auth.currentUser != null) {
                                    Log.d("waiting to delete user", "")
                                }
                                navController.navigate(Screen.RegisterScreen.route)
                            }
                        ) {
                            Text(color = Color.White, text = "Delete")
                        }
                    }
                    Spacer(modifier = Modifier.padding(20.dp))
                }
            }
        }
    )
}


