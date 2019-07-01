package com.example.bcarlosh.architecturecomponentssample

import android.app.Application
import com.example.bcarlosh.architecturecomponentssample.di.dbModule
import com.example.bcarlosh.architecturecomponentssample.di.networkModule
import com.example.bcarlosh.architecturecomponentssample.di.repositoryModule
import com.example.bcarlosh.architecturecomponentssample.di.viewModelFactoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level


class ArchitectureComponentsSampleApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@ArchitectureComponentsSampleApplication)
            modules(
                listOf(
                    dbModule,
                    networkModule,
                    repositoryModule,
                    viewModelFactoryModule
                )
            )
        }

    }

}