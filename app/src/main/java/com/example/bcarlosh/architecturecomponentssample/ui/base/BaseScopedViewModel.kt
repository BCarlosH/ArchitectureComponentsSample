package com.example.bcarlosh.architecturecomponentssample.ui.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext


/**
 *
 * Extension property ViewModel.viewModelScope will be added from version 2.1.0
 * So after that there is no need to create it own scope
 *
 * Example: https://medium.com/androiddevelopers/easy-coroutines-in-android-viewmodelscope-25bffb605471
 *
 */
abstract class BaseScopedViewModel : ViewModel() {

    var parentJob = Job()

    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Default

    val scope = CoroutineScope(coroutineContext)

    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }

}