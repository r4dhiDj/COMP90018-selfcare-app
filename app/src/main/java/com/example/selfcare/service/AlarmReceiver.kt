package com.example.selfcare.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.selfcare.constants.Constants
import com.example.selfcare.presentation.components.sendNotification


class AlarmReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {

        when(intent!!.action) {
            Constants.ACTION_SET_EXACT_ALARM -> {
                sendNotification(
                    context = context!!,
                    notifTitle = intent.getStringExtra("Title").toString(),
                    notifText = intent.getStringExtra("Content").toString()
                )
            }
        }


    }




}