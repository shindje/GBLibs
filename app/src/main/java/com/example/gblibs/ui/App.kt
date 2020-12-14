package com.example.gblibs.ui

import android.app.Application
import com.example.gblibs.di.AppComponent
import com.example.gblibs.di.DaggerAppComponent
import com.example.gblibs.di.modules.AppModule

class App : Application() {

    companion object {
        lateinit var instance: App
    }

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        instance = this

        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }
}