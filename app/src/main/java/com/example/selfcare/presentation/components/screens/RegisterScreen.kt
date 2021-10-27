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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.selfcare.R
import com.example.selfcare.data.model.User
import com.example.selfcare.presentation.components.Screen
import com.example.selfcare.viewmodels.MainViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


@Composable
fun RegisterScreen(viewModel: MainViewModel, navController: NavController, activityContext: ComponentActivity){
    val auth = Firebase.auth
    var email by remember {mutableStateOf("")}
    var password by remember {mutableStateOf("")}
    var passwordVisibility by remember {mutableStateOf(true)}
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
                painter = painterResource(id = R.drawable.meditation),
                contentDescription = "register image",
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
                text = "Sign Up",
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
                    enabled = !(email.trim() == "" || password.trim() == ""),
                    onClick = {
                        auth.createUserWithEmailAndPassword(
                            email.trim(),
                            password.trim()
                        )
                            .addOnCompleteListener(activityContext) { task ->
                                if (task.isSuccessful) {
                                    viewModel.setUserEmail(email.trim())
                                    navController.popBackStack() //so back button doesn't return to register page
                                    while (Firebase.auth.currentUser == null) {
                                        Log.d("waiting to register", "")
                                    }
                                    val user = User(email.trim(), 0)
                                    val database =
                                        Firebase.database("https://kotlin-self-care-default-rtdb.firebaseio.com/").reference
                                    database.child("users").child(Firebase.auth.currentUser!!.uid)
                                        .setValue(user)
                                    navController.navigate(Screen.WelcomeScreen.route)
                                } else {
                                    Log.d("Auth", "Failed: ${task.exception}")
                                    Toast.makeText(
                                        activityContext, "Please enter a valid email.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                    }

                ) {
                    Text(color = Color.White, text = "Sign Up")
                }
                Spacer(modifier = Modifier.padding(10.dp))
                Text(
                    text = "Log In instead",
                    modifier = Modifier.clickable(onClick = {
                        navController.popBackStack()
                        navController.navigate(Screen.LoginScreen.route)
                    })
                )
                Spacer(modifier = Modifier.padding(20.dp))
            }

        }
    }
}





/**
@Composable
fun RegisterScreen(viewModel: MainViewModel, navController: NavController, activityContext: ComponentActivity){
    val auth = Firebase.auth
    var email by remember {mutableStateOf("")}
    var password by remember {mutableStateOf("")}
    if (Firebase.auth.currentUser!= null) {
        navController.popBackStack()
        navController.navigate(Screen.WelcomeScreen.route)
        Log.d("inside register screen check user" , Firebase.auth.currentUser.toString())
    }
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
                Text(text = "Register as a new user", style = MaterialTheme.typography.h1)
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
                        navController.navigate(Screen.LoginScreen.route)
                    }
                ) {
                    Text(color = Color.White, text = "Login instead")
                }
                Button(
                    enabled = !(email.trim() == "" || password.trim() == ""),
                    onClick = {
                        auth.createUserWithEmailAndPassword(
                            email.trim(),
                            password.trim()
                        )
                            .addOnCompleteListener(activityContext) { task ->
                                if (task.isSuccessful) {
                                    viewModel.setUserEmail(email.trim())
                                    navController.popBackStack() //so back button doesn't return to register page
                                    while(Firebase.auth.currentUser== null){
                                        Log.d("waiting to register","")
                                    }
                                    val user = User(email.trim(), 0)
                                    val database = Firebase.database("https://kotlin-self-care-default-rtdb.firebaseio.com/").reference
                                    database.child("users").child(Firebase.auth.currentUser!!.uid).setValue(user)
                                    navController.navigate(Screen.WelcomeScreen.route)
                                } else {
                                    Log.d("Auth", "Failed: ${task.exception}")
                                    Toast.makeText(
                                        activityContext, "Please enter a valid email.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                    }
                ) {
                    Text(color = Color.White, text = "Register")
                }

            }

        }
    }
}
*/
