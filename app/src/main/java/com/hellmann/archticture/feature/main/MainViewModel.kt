package com.hellmann.archticture.feature.main

import androidx.lifecycle.MutableLiveData
import com.hellmann.archticture.feature.viewmodel.BaseViewModel
import com.hellmann.archticture.feature.viewmodel.StateMachineSingle
import com.hellmann.archticture.feature.viewmodel.ViewState
import com.hellmann.domain.entity.Article
import com.hellmann.domain.usecase.GetArticlesUseCase
import io.reactivex.Scheduler
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy

/*
 * This file is part of hellmann-architeture.
 * 
 * Created by maiconhellmann on 26/05/2019
 * 
 * (c) 2019 
 */
class MainViewModel(
    private val useCase: GetArticlesUseCase, private val uiScheduler: Scheduler
) : BaseViewModel() {

    val state = MutableLiveData<ViewState<List<Article>>>().apply {
        value = ViewState.Loading
    }

    fun getJobs(forceUpdate: Boolean = false) {
        disposables += useCase.execute(forceUpdate = forceUpdate).compose(StateMachineSingle())
            .observeOn(uiScheduler).subscribeBy(
                onSuccess = {
                    state.postValue(it)
                },
                onError = {
                    //TODO
                }
            )
    }

    fun onTryAgainRequired() {
        getJobs(forceUpdate = true)
    }
}