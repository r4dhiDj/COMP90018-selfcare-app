package com.example.selfcare

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * COMP90018 - SelfCare
 * Base Application class that inherits from [Application] and used for Dependency Injection
 * Serves as the application level dependency container
 */

@HiltAndroidApp
class BaseApplication : Application() {


}