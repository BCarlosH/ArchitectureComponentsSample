package com.example.bcarlosh.architecturecomponentssample.base

import android.app.Application
import com.example.bcarlosh.architecturecomponentssample.ArchitectureComponentsSampleApplication
import org.koin.core.module.Module


/**
 * We use a separate [Application] for tests to prevent initializing release modules.
 * On the contrary, we will provide inside our tests custom [Module] directly.
 */
class TIBaseApplication : ArchitectureComponentsSampleApplication() {

    override fun provideComponent() = listOf<Module>()

}