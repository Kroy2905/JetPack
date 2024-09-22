package com.kroy.sseditor.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kroy.sseditor.models.TweetListItem
import com.kroy.sseditor.repository.SSEditorRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class SelectTimeViewModel @Inject constructor(private  val savedStateHandle: SavedStateHandle):ViewModel() {

    private val _name = MutableStateFlow("")
    val name: StateFlow<String> get() = _name

    private val _id = MutableStateFlow(0)
    val id: StateFlow<Int> get() = _id

    init {
        val nameFromSavedState = savedStateHandle.get<String>("name") ?: ""
        val idFromSavedState = savedStateHandle.get<Int>("id") ?: 0

        viewModelScope.launch {
            _name.value = nameFromSavedState
            _id.value = idFromSavedState
        }
    }
}