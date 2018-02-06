package com.tubandev.smack.controller

import android.app.Application
import com.tubandev.smack.utilities.SharedPrefs

/**
 * Created by sulistiyanto on 06/02/18.
 */
class App : Application() {

    companion object {
        lateinit var prefs : SharedPrefs
    }

    override fun onCreate() {
        prefs = SharedPrefs(applicationContext)
        super.onCreate()
    }
}