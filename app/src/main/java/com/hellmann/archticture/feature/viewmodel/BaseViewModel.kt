package com.hellmann.archticture.feature.viewmodel

import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {
    // TODO verify if this base class is necessary
    override fun onCleared() {
        super.onCleared()
    }
}
