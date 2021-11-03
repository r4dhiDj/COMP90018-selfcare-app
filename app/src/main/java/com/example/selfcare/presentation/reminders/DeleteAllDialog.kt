package com.example.selfcare.presentation.reminders


import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight

/**
 * COMP90018 - SelfCare
 * [DeleteAllDialog] Dialog used to confirm with the user to delete all reminders
 */


@Composable
fun DeleteAllDialog(
    title: String,
    message: String,
    openDialog: Boolean,
    closeDialog: () -> Unit,
    onYesClicked: () -> Unit,
) {
    if (openDialog) {
        AlertDialog(
            title = {
                Text(
                    text = title,
                    fontSize = MaterialTheme.typography.h5.fontSize,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text(
                    text = message,
                    fontSize = MaterialTheme.typography.subtitle1.fontSize,
                    fontWeight = FontWeight.Normal
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        onYesClicked()
                        closeDialog()
                    }
                ) {
                    Text(text = "Yes")
                }

            },
            dismissButton = {
                OutlinedButton(
                    onClick = { closeDialog() }
                ) {
                    Text(text = "No")
                }
            },
            onDismissRequest = { closeDialog() }
        )
    }
}