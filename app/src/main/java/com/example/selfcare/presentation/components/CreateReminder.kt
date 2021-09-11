package com.example.selfcare.presentation.components

import android.app.DatePickerDialog
import android.content.Context
import android.util.Size
import android.widget.DatePicker
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius.Companion.Zero
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentActivity
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import java.util.*



@Composable
fun CreateReminder (
//    onClick: () -> Unit,
    activity: FragmentActivity
) {
    var expanded by remember { mutableStateOf(false) }
    val suggestions = listOf("Item1","Item2","Item3")
    var selectedText by remember { mutableStateOf("") }


    Card(
        modifier = Modifier
            .padding(top = 12.dp, bottom = 12.dp, start = 20.dp, end = 20.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(top = 12.dp, bottom = 12.dp, start = 10.dp, end = 10.dp)
        ) {
            Text(
                text = "Message"
            )
            val textState = remember { mutableStateOf(TextFieldValue()) }
            TextField(
                value = textState.value,
                onValueChange = { textState.value = it },
                modifier = Modifier
                    .padding(top = 5.dp, bottom = 5.dp, start = 8.dp, end = 8.dp)
            )
            Text(
                text = "Set Time"
            )

            TimePicker(activity)

            Text(
                text = "Repeat"
            )



            // Dropdown Box)
            DropdownMenu(expanded = expanded,
                onDismissRequest = { /*TODO*/ },
                modifier = Modifier
            ) {
                suggestions.forEach{ label ->
                    DropdownMenuItem(onClick = {
                        selectedText = label
                    }) {
                        Text(text = label)
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                Arrangement.SpaceEvenly
            ) {
                Button(onClick = { /* TODO */ }) {
                    Text(text = "Cancel")
                }
                Button(onClick = { /* TODO */ }) {
                    Text(text = "OK")
                }
            }





        }
    }
}

//@Preview
//@Composable
//fun ComposablePreview() {
//    CreateReminder()
//}


