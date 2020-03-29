package com.hellmann.archticture.feature.list

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.hellmann.archticture.feature.viewmodel.BaseViewModel
import com.hellmann.archticture.feature.viewmodel.ViewState
import com.hellmann.archticture.feature.viewmodel.toViewState
import com.hellmann.domain.usecase.GetArticlesUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/*
 * This file is part of hellmann-architeture.
 * 
 * Created by maiconhellmann on 26/05/2019
 * 
 * (c) 2019 
 */
class ArticleViewModel(
    private val useCase: GetArticlesUseCase,
     @VisibleForTesting var ioDispatcher: CoroutineDispatcher
) : BaseViewModel() {

    val state = liveData(ioDispatcher) {
        emit(ViewState.Loading)
        emit(useCase.execute().toViewState())
    }

    fun onTryAgainRequired() {
        println("onTryAgainRequired")
        viewModelScope.launch {
            // This should be different.
            // I was thinking on creating a liveData stream from DB. Then this call would not be necessary anymore.
            // The onTryAgainRequired would call the service again, which would update the data in room.
            (state as MutableLiveData).postValue(ViewState.Loading)

            withContext(ioDispatcher) {
                state.postValue(useCase.execute(forceUpdate = true).toViewState())
            }
        }
    }
}
