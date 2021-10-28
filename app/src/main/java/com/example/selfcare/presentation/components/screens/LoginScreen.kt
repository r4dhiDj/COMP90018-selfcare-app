package com.example.selfcare.presentation.components.screens

import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.ui.foundation.Image
import androidx.ui.res.imageResource
import androidx.ui.res.vectorResource
import com.example.selfcare.R
import com.example.selfcare.presentation.components.Screen
import com.example.selfcare.viewmodels.MainViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


@Composable
fun LoginScreen(viewModel: MainViewModel, navController: NavController, activityContext: ComponentActivity){
    val auth = Firebase.auth
    var email by remember {mutableStateOf("")}
    var password by remember {mutableStateOf("")}
    var passwordVisibility by remember {mutableStateOf(false)}
    if (Firebase.auth.currentUser!= null) {
        navController.popBackStack()
        navController.navigate(Screen.WelcomeScreen.route)
    }


    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Spacer(modifier = Modifier.padding(20.dp))
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            contentAlignment = Alignment.TopCenter
        ){
            androidx.compose.foundation.Image(
                painter = painterResource(id = R.drawable.meditation2),
                contentDescription = "login image",
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Fit
            )
        }


        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.55f)
                .clip(RoundedCornerShape(30.dp))
                .background(Color.White)
                .padding(10.dp)
        ) {
            Text(
                text = "Log In",
                style = MaterialTheme.typography.h1
            )
            Spacer(modifier = Modifier.padding(20.dp))
            Column(horizontalAlignment = Alignment.CenterHorizontally){
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(0.8f),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email
                    ),
                    label = { Text("Enter your email") })

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    singleLine = true,
                    trailingIcon = {
                        IconButton(
                            content = {
                                Icon(
                                    painter = painterResource(id = if(passwordVisibility) R.drawable.eye_on else R.drawable.eye_off),
                                    contentDescription = "eye",
                                    tint = if(passwordVisibility) Color.Black else Color.Gray)
                            },
                            onClick = { passwordVisibility = !passwordVisibility }
                        )
                    },
                    modifier = Modifier.fillMaxWidth(0.8f),
                    label = { Text("Enter your password") },
                    visualTransformation = if(passwordVisibility)
                        VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password
                    )
                )
                Spacer(modifier = Modifier.padding(20.dp))
                Button(
                    enabled = !(email.trim() == "" || password.trim() ==""),
                    onClick = {
                        auth.signInWithEmailAndPassword(
                            email.trim(),
                            password.trim()
                        )
                            .addOnCompleteListener(activityContext) { task ->
                                if (task.isSuccessful) {
                                    viewModel.setUserEmail(email.trim())
                                    Log.d("inside on complete", email)
                                    navController.popBackStack() //so back button doesn't return to register page
                                    while(Firebase.auth.currentUser== null){
                                        Log.d("waiting to login","")
                                    }
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
                Spacer(modifier = Modifier.padding(10.dp))
                Text(
                    text = "Sign Up instead",
                    modifier = Modifier.clickable(onClick = {
                        navController.popBackStack()
                        navController.navigate(Screen.RegisterScreen.route)
                    })
                )
                Spacer(modifier = Modifier.padding(20.dp))
            }

        }
    }
}

