package com.example.selfcare.components.chat

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun UserInput(
    message: String,
    onMessageChanged: (String) -> Unit,
    onMessageAdd: () -> Unit,
    onFocusChange: (Boolean) -> Unit,
    focusState: Boolean,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
        ,
        horizontalArrangement = Arrangement.End
    ) {
        Surface(
            elevation = 12.dp,
        ) {
            Box(
                modifier = Modifier
                .height(64.dp)
                .weight(1f)
            ) {

                var lastFocusState by remember { mutableStateOf(false) }
                val keyboardController = LocalSoftwareKeyboardController.current

                BasicTextField(
                    value = message,
                    singleLine = true,
                    onValueChange = { onMessageChanged(it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterStart)
                        .padding(start = 32.dp)
                        .onFocusChanged { state ->
                            if (lastFocusState != state.isFocused) {
                                onFocusChange(state.isFocused)
                            }
                            lastFocusState = state.isFocused
                        },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Send
                    ),
                    keyboardActions = KeyboardActions(onSend = {
                        onMessageAdd()
                        keyboardController?.hide()
                    }),
                    cursorBrush =  SolidColor(LocalContentColor.current),
                    textStyle = LocalTextStyle.current.copy(color = LocalContentColor.current)
                )

                if (message.isEmpty() && !focusState) {
                    Text(
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(start = 32.dp),
                        text = "Enter a message",
                    )
                }

            }
        }
    }



}