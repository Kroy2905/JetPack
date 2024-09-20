package com.kroy.composestudy.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kroy.composestudy.models.TweetListItem
import com.kroy.composestudy.repository.TweetRepository
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailsViewmodel @Inject constructor(private  val repository: TweetRepository):ViewModel() {

    val twwets :StateFlow<List<TweetListItem>>
        get() = repository.tweets

    init {
        viewModelScope.launch {
            repository.getTweets("android")
        }
    }
}