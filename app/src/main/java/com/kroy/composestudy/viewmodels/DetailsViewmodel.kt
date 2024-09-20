package com.kroy.composestudy.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kroy.composestudy.models.TweetListItem
import com.kroy.composestudy.repository.TweetRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class DetailsViewmodel @Inject constructor(private  val repository: TweetRepository,
    private  val savedStateHandle: SavedStateHandle):ViewModel() {

    val tweets :StateFlow<List<TweetListItem>>
        get() = repository.tweets

    init {
        viewModelScope.launch {
            val category = savedStateHandle.get<String>("category")?:"android"
            repository.getTweets(category)
        }
    }
}