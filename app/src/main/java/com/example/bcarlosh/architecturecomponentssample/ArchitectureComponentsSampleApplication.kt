package com.example.bcarlosh.architecturecomponentssample

import android.app.Application
import com.example.bcarlosh.architecturecomponentssample.di.appComponent
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level


open class ArchitectureComponentsSampleApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@ArchitectureComponentsSampleApplication)
            modules(
                provideComponent()
            )
        }

    }

    open fun provideComponent() = appComponent

}