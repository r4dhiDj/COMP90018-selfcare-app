package com.example.selfcare.presentation.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Send
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.selfcare.R
import com.example.selfcare.ui.theme.Shapes

@Composable
fun UserArea(
    userName: String,
    @DrawableRes avatar: Int,
    message: String,
    onMessageChanged: (String) -> Unit,
    modifier: Modifier = Modifier.width(150.dp),
    onMessageAdd: () -> Unit
) {
    Card(
        border = BorderStroke(width = 1.dp, color = Color.DarkGray),
        shape = Shapes.medium,
        backgroundColor = Color.LightGray,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Column(
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ){
                Text(
                    text = userName,
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center
                )
            }
            Column(
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Image(
                    painter = painterResource(id = avatar),
                    contentDescription = "Person image",
                    modifier = Modifier.size(100.dp),
                    alignment = Alignment.TopCenter
                )
            }

            Row() {
                OutlinedTextField(
                    value = message,
                    onValueChange = onMessageChanged,
                    modifier = Modifier.weight(1.0f),
                    label = {
                        Text(text = "Message") },
                    trailingIcon = {
                        Icon(
                            Icons.Outlined.Send,
                            contentDescription = "Send Message",
                            modifier = Modifier
                                .clickable(
                                    onClick = onMessageAdd
                                )
                                .background(Color.Gray),
                            tint = Color.White
                        )
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UserAreaPreview() {
    UserArea(
        userName = "User1",
        avatar = R.drawable.robot,
        message = "Hello!",
        onMessageChanged = {},
        onMessageAdd = {}
        )
}