package com.kroy.composestudy.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kroy.composestudy.repository.TweetRepository
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class CategoryViewmodel @Inject constructor(private  val repository: TweetRepository):ViewModel() {

    val categories :StateFlow<List<String>>
        get() = repository.categories

    init {
        viewModelScope.launch {
            repository.getCategory()
        }
    }
}