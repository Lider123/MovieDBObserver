package ru.babaets.moviedbobserver.presentation.feature.common

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel : ViewModel(), CoroutineScope {

    val progressLiveData = MutableLiveData(false)

    val errorLiveData = MutableLiveData<Throwable?>(null)

    override val coroutineContext: CoroutineContext =
        viewModelScope.coroutineContext + Dispatchers.IO + CoroutineExceptionHandler(::onError)

    protected open fun onError(context: CoroutineContext, error: Throwable) {
        error.printStackTrace()
        errorLiveData.postValue(error)
        progressLiveData.postValue(false)
    }

    protected inline fun launchWithLoading(
        dispatcher: CoroutineDispatcher = Dispatchers.Default,
        crossinline callback: suspend () -> Unit
    ) = launch(dispatcher) {
        progressLiveData.postValue(true)
        errorLiveData.postValue(null)
        callback.invoke()
        progressLiveData.postValue(false)
    }
}
