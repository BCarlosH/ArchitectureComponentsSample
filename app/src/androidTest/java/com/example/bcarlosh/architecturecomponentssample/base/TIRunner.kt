package com.example.bcarlosh.architecturecomponentssample.base

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner


/**
 * Custom runner to disable dependency injection.
 */
class TIRunner : AndroidJUnitRunner() {

    override fun newApplication(cl: ClassLoader, className: String, context: Context): Application {
        return super.newApplication(cl, TIBaseApplication::class.java.name, context)
    }

}