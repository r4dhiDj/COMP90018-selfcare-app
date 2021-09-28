package com.example.selfcare.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.selfcare.ui.theme.IBMPlexMono
import com.example.selfcare.viewmodels.MainViewModel

@Composable
fun WelcomeScreen(viewModel: MainViewModel,
                  navController: NavController){
    var username by remember { mutableStateOf("") }
    LaunchedEffect(key1 = viewModel.username.value){
        viewModel.getUsername()
        username = viewModel.username.value
    }

    Card(
        modifier = Modifier
            .fillMaxSize(),
        elevation = 8.dp
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Row(){
                Text(
                text = "Hi, $username",
                style = MaterialTheme.typography.h1
            )
            }
            Row() {
                Button(
                    modifier = Modifier.padding(end = 10.dp),
                    onClick = {
                        navController.navigate(Screen.MenuScreen.route)
                    }
                )
                {
                    Text(color = Color.White, text = "Enter")
                }
                Button(
                    onClick = {
                        navController.navigate(Screen.SettingsScreen.route)
                    }
                )
                {
                    Text(color = Color.White, text = "Set Username")
                }

            }
        }
    }

}