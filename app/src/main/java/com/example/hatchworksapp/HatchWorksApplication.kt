package com.example.hatchworksapp

import android.app.Application
import com.example.hatchworksapp.di.dataModule
import com.example.hatchworksapp.di.databaseModule
import com.example.hatchworksapp.di.domainModule
import com.example.hatchworksapp.di.networkModule
import com.example.hatchworksapp.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class HatchWorksApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@HatchWorksApplication)
            modules(
                networkModule,
                databaseModule,
                dataModule,
                domainModule,
                presentationModule
            )
        }
    }
}
