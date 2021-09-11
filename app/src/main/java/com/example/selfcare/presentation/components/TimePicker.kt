package com.example.selfcare.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentActivity
import com.google.android.material.timepicker.MaterialTimePicker

@Composable
fun TimePicker(activity: FragmentActivity) {

    Column(modifier = Modifier.padding(16.dp)){

        var hour by remember { mutableStateOf(0) }
        var minute by remember { mutableStateOf(0) }

        fun showDatePicker() {
            val picker = MaterialTimePicker.Builder().build()
            activity?.let {
                picker.show(it.supportFragmentManager, picker.toString())
                picker.addOnPositiveButtonClickListener {
                    hour = picker.hour
                    minute = picker.minute
                }
                picker.addOnNegativeButtonClickListener {

                }
            }
        }

        Button(onClick = { showDatePicker() }) {
            Text("$hour:$minute")
        }

    }
}