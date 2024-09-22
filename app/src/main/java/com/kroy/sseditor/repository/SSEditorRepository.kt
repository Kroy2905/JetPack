package com.kroy.sseditor.repository

import android.util.Log
import com.kroy.sseditor.api.ApiService
import com.kroy.sseditor.models.ApiResponse
import com.kroy.sseditor.models.TweetListItem
import com.kroy.sseditor.models.userloginBody
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class SSEditorRepository @Inject constructor(private  val apiService: ApiService){



    private val _categories = MutableStateFlow<List<String>>(emptyList())
    val categories:StateFlow<List<String>>
        get() = _categories

    private val _tweets = MutableStateFlow<List<TweetListItem>>(emptyList())
    val tweets:StateFlow<List<TweetListItem>>
        get() = _tweets


    private val _userlogin = MutableStateFlow<ApiResponse.UserLoginResponse>(ApiResponse.UserLoginResponse())
    val userlogin:StateFlow<ApiResponse>
        get() = _userlogin
    suspend fun getCategory(){
        val response = apiService.getCategory()
        if(response.isSuccessful && response.body()!=null){
            // get the categories
            _categories.emit(response.body()!!)

        }
    }

    suspend fun getTweets(category:String){
        val response = apiService.getTweets("tweets[?(@.category==\"$category\")]")
        if(response.isSuccessful && response.body()!=null){
            // get the categories
            _tweets.emit(response.body()!!)

        }
    }

    suspend fun loginuser(userloginBody: userloginBody){
        Log.d("userlogin->","body = $userloginBody")
        val response = apiService.loginUser(userloginBody)
        Log.d("userlogin->","response = ${response.body()}")


        if(response.isSuccessful && response.body()!=null){
            // get the categories
            _userlogin.emit(response.body()!!)

        }
    }

    private val _allClients = MutableStateFlow<ApiResponse.AllClientResponse>(ApiResponse.AllClientResponse())
    val allClients:StateFlow<ApiResponse>
        get() = _allClients
    suspend fun getAllClients(userId:Int){
        Log.d("all users->","body = $userId")
        val response = apiService.getAllClients(userId)
        Log.d("all users->","response = ${response.body()}")

        if(response.isSuccessful && response.body()!=null){
            // get the categories
            _allClients.emit(response.body()!!)

        }
    }

    fun handleApiResponse(response: ApiResponse) {
        when (response) {
            is ApiResponse.UserLoginResponse -> {
                // Handle user login response
                val userData = response.data
                val message = response.message
                val statusCode = response.statusCode
                // Process user login data
            }
            is ApiResponse.AddClientResponse -> {
                // Handle add client response
                val newClientData = response.data
                val message = response.message
                val statusCode = response.statusCode
                // Process new client data
            }
            is ApiResponse.AllClientResponse -> {
                // Handle all clients response
                val clients = response.data ?: emptyList()
                val message = response.message
                val statusCode = response.statusCode
                // Process clients
            }
        }
    }
}