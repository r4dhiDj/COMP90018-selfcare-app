package com.example.selfcare.util

import java.util.concurrent.atomic.AtomicInteger

/**
 * COMP90018 - SelfCare
 * [RandomIntUtil] used to create a PendingIntent for [AlarmService]
 */

object RandomIntUtil {
    private val seed = AtomicInteger()

    fun getRandomInt() = seed.getAndIncrement()+ System.currentTimeMillis().toInt()

}