package com.example.selfcare.service

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import com.example.selfcare.constants.Constants
import com.example.selfcare.util.RandomIntUtil

/**
 * COMP90018 - SelfCare
 * [AlarmService] used to schedule an alarm via [alarmManager]
 */

class AlarmService(private val context: Context) {
    private val alarmManager: AlarmManager? =
        context.getSystemService(Context.ALARM_SERVICE) as AlarmManager?

    fun setExactAlarm(timeInMillis: Long, title: String, content: String) {
        setAlarm(
            timeInMillis,
            getPendingIntent(
                getIntent().apply {
                    action = Constants.ACTION_SET_EXACT_ALARM
                    putExtra(Constants.EXTRA_EXACT_ALARM_TIME, timeInMillis)
                    putExtra("Title", title)
                    putExtra("Content", content)
                }
            )
        )

    }


    private fun setAlarm(
        timeInMillis: Long,
        pendingIntent: PendingIntent
    ) {
        alarmManager?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    timeInMillis,
                    pendingIntent
                )
            } else {
                alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP,
                    timeInMillis,
                    pendingIntent
                )
            }
        }

    }

    private fun getIntent() = Intent(context, AlarmReceiver::class.java)

    private fun getPendingIntent(intent: Intent) =
        PendingIntent.getBroadcast(
            context,
            RandomIntUtil.getRandomInt(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )



}