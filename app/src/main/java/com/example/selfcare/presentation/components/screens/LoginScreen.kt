package com.example.selfcare.presentation.components.screens

import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.selfcare.presentation.components.Screen
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


@Composable
fun LoginScreen(navController: NavController, activityContext: ComponentActivity){
    val auth = Firebase.auth
    var email by remember {mutableStateOf("")}
    var password by remember {mutableStateOf("")}
    Card(
        modifier = Modifier
            .fillMaxSize(),
        elevation = 8.dp
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, bottom = 30.dp, start = 20.dp, end = 20.dp),
                Arrangement.SpaceBetween,
                Alignment.CenterVertically,
            ) {
                Text(text = "Login to your account", style = MaterialTheme.typography.h1)
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, bottom = 12.dp, start = 20.dp, end = 20.dp),
                Arrangement.SpaceBetween,
                Alignment.CenterVertically,
            ) {
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email
                    ),
                    label = { Text("Enter your email") })
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
                    value = password,
                    onValueChange = { password = it },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Enter your password") },
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password
                    )
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 40.dp, bottom = 12.dp, start = 20.dp, end = 20.dp),
                Arrangement.SpaceBetween,
                Alignment.CenterVertically
            )
            {
                Button(
                    onClick = {
                        navController.popBackStack()
                        navController.navigate(Screen.RegisterScreen.route)
                    }
                ){
                    Text(color = Color.White, text = "Sign-up instead")
                }
                Button(
                    enabled = !(email.trim() == "" || password.trim() ==""),
                    onClick = {
                        auth.signInWithEmailAndPassword(
                            email.trim(),
                            password.trim()
                        )
                            .addOnCompleteListener(activityContext) { task ->
                                if (task.isSuccessful) {
                                    navController.popBackStack() //so back button doesn't return to register page
                                    navController.navigate(Screen.WelcomeScreen.route)
                                } else {
                                    Log.d("Login", "Failed: ${task.exception}")
                                    Toast.makeText(
                                        activityContext, "Invalid email or password.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                    }
                ) {
                    Text(color = Color.White, text = "Login")
                }

            }

        }
    }
}

