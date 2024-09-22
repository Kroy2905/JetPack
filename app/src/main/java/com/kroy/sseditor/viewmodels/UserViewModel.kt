package com.kroy.sseditor.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kroy.sseditor.models.userLoginResponse
import com.kroy.sseditor.models.userloginBody
import com.kroy.sseditor.repository.SSEditorRepository
import com.kroy.sseditor.utils.DataStoreHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class UserViewModel @Inject constructor(
    private val dataStoreHelper: DataStoreHelper
    ,private  val repository: SSEditorRepository
) : ViewModel() {

    // Expose the DataStore data as Flow
    val isLoggedInFlow: Flow<Boolean> = dataStoreHelper.isLoggedInFlow
    val userNameFlow: Flow<Int> = dataStoreHelper.userIdFlow


    // Functions to update the values
    fun setIsLoggedIn(isLoggedIn: Boolean) {
        viewModelScope.launch {
            dataStoreHelper.setIsLoggedIn(isLoggedIn)
        }
    }

    fun setUserName(userId: Int) {
        viewModelScope.launch {
            dataStoreHelper.setUserId(userId)
        }
    }

    val userlogin : StateFlow<userLoginResponse>
        get() = repository.userlogin
    fun loginuser(userloginBody: userloginBody){
        viewModelScope.launch {
            repository.loginuser(userloginBody)
        }
    }



}
