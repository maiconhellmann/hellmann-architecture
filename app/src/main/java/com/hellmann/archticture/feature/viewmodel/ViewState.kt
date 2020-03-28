package com.hellmann.archticture.feature.viewmodel

sealed class ViewState<out T> {
    object Loading : ViewState<Nothing>()
    data class Success<T>(val data: T) : ViewState<T>()
    data class Failed(val throwable: Throwable) : ViewState<Nothing>()
}

fun <T> T.toViewState(): ViewState.Success<T> {
    return ViewState.Success(this)
}
