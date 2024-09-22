package com.kroy.sseditor.viewmodels

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kroy.sseditor.models.ApiResponse
import com.kroy.sseditor.repository.SSEditorRepository
import com.kroy.sseditor.utils.DataStoreHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClientViewModel @Inject constructor(
    private val dataStoreHelper: DataStoreHelper,
    private val repository: SSEditorRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    // Expose the DataStore data as Flow
    val isLoggedInFlow: Flow<Boolean> = dataStoreHelper.isLoggedInFlow
    val userNameFlow: Flow<Int> = dataStoreHelper.userIdFlow

    // StateFlow for all clients response
    val allClients: StateFlow<ApiResponse> get() = repository.allClients

    // Filtered response for all clients
    private val _filteredClientResponse = MutableStateFlow<ApiResponse.AllClientResponse?>(null)
    val filteredClientResponse: StateFlow<ApiResponse.AllClientResponse?> get() = _filteredClientResponse

    init {
        viewModelScope.launch {
            val userId = savedStateHandle.get<Int>("userId") ?: 0
            Log.d("received viewmodel->", "$userId")
            setUserId(userId)
            getAllClients(userId)
        }
    }

    // Functions to update user state
    private fun setIsLoggedIn(isLoggedIn: Boolean) {
        viewModelScope.launch {
            dataStoreHelper.setIsLoggedIn(isLoggedIn)
        }
    }

    private fun setUserId(userId: Int) {
        viewModelScope.launch {
            dataStoreHelper.setUserId(userId)
        }
    }

    // Fetch all clients and handle the response
    fun getAllClients(userId: Int) {
        viewModelScope.launch {
            Log.d("ClientViewModel", "Fetching all clients for userId: $userId")
            repository.getAllClients(userId)

            // Assuming repository.allClients is updated after the API call
            allClients.collect { response ->
                handleClientResponse(response)
            }
        }
    }

    // Handle and filter the API response
    private fun handleClientResponse(response: ApiResponse) {
        when (response) {
            is ApiResponse.AllClientResponse -> {
                if (response.data!!.isNotEmpty()) {
                    // Emit the successful response
                    _filteredClientResponse.value = response
                } else {
                    // Handle empty data scenario
                    _filteredClientResponse.value = ApiResponse.AllClientResponse(
                        data = emptyList(),
                        message = "No clients found",
                        statusCode = response.statusCode
                    )
                }
            }
            else -> {
                // Handle other response types if necessary
                _filteredClientResponse.value = null
            }
        }
    }
}
