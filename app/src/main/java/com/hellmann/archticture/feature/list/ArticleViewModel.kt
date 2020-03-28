package com.hellmann.archticture.feature.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hellmann.archticture.feature.viewmodel.BaseViewModel
import com.hellmann.archticture.feature.viewmodel.ViewState
import com.hellmann.archticture.feature.viewmodel.toViewState
import com.hellmann.domain.entity.Article
import com.hellmann.domain.usecase.GetArticlesUseCase
import kotlinx.coroutines.Dispatchers
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
    private val useCase: GetArticlesUseCase
) : BaseViewModel() {

    val state = MutableLiveData<ViewState<List<Article>>>()

    fun getJobs(forceUpdate: Boolean = false) {
        viewModelScope.launch {
            state.value = ViewState.Loading

            withContext(Dispatchers.IO) {
                state.postValue(useCase.execute(forceUpdate = forceUpdate).toViewState())
            }
        }
    }

    fun onTryAgainRequired() {
        getJobs(forceUpdate = true)
    }
}
