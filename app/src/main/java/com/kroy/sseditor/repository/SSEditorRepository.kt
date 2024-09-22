package com.kroy.sseditor.repository

import android.util.Log
import com.kroy.sseditor.api.ApiService
import com.kroy.sseditor.models.TweetListItem
import com.kroy.sseditor.models.userLoginResponse
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


    private val _userlogin = MutableStateFlow<userLoginResponse>(userLoginResponse())
    val userlogin:StateFlow<userLoginResponse>
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
}