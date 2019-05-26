package com.hellmann.archticture.feature.viewmodel

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

open class BaseViewModel: ViewModel() {

    val disposables = CompositeDisposable()

    override fun onCleared() {
        disposables.clear()

        super.onCleared()
    }
}