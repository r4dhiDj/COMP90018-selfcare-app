package com.example.selfcare.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.selfcare.data.model.defaultMessage
import com.example.selfcare.ui.theme.Pink200
import com.example.selfcare.ui.theme.Pink700
import com.example.selfcare.ui.theme.SelfCareTheme

@Composable
fun ChatScreen(navController: NavController) {
    val messages = defaultMessage
    SelfCareTheme(darkMode = false) {
        Scaffold (
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Chat to the mascot",
                            style = MaterialTheme.typography.h1,
                            color = Color.White
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            navController.navigate(Screen.MenuScreen.route)
                        }) {
                            Icon(Icons.Filled.ArrowBack, "backIcon", tint = Color.White)
                        }
                    },
                    backgroundColor = Pink700,
                )
            },
            content = {
//                TODO("a lazyColumn containing all the message cards")
//                TODO("a message card component")
//                TODO("Data class for message")
//                TODO("viewmodel for storing all chat histories")
//                TODO("Internet connection")
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(40.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(messages) { msg ->
                        ChatCard(msg)
                    }
                }
            },
            backgroundColor = Pink200,
        )
    }
}