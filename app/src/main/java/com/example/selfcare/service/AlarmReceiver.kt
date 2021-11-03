package com.example.selfcare.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.compose.material3.ExperimentalMaterial3Api
import com.example.selfcare.constants.Constants
import com.example.selfcare.util.sendNotification

/**
 * COMP90018 - SelfCare
 * [AlarmReceiver] after receiving a scheduled alarm, used to send a push notification to the
 * phone
 */

@ExperimentalMaterial3Api
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