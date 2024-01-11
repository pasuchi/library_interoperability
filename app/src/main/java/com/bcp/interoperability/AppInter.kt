package com.bcp.interoperability

import android.app.Application
import android.util.Log
import dagger.hilt.android.HiltAndroidApp
import java.io.File

@HiltAndroidApp
class AppInter : Application() {
    override fun onCreate() {
        super.onCreate()
    }


}