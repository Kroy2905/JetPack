package com.kroy.sseditor.repository

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.kroy.sseditor.api.ApiService
import com.kroy.sseditor.models.ApiResponse
import com.kroy.sseditor.models.TweetListItem
import com.kroy.sseditor.models.addClientBody
import com.kroy.sseditor.models.editClientBody
import com.kroy.sseditor.models.userloginBody
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext
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

    suspend fun loginuser(userloginBody: userloginBody,context: Context){
        Log.d("userlogin->","body = $userloginBody")
        val response = apiService.loginUser(userloginBody)
        Log.d("userlogin->","response = ${response.body()}")


        if(response.isSuccessful && response.body()!=null){
            // get the categories
            _userlogin.emit(response.body()!!)

        }else{
            // Show success toast
            withContext(Dispatchers.Main) {
                Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show()
            }

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

    private val _addClient = MutableStateFlow<ApiResponse.AddClientResponse>(ApiResponse.AddClientResponse())
    val addClients:StateFlow<ApiResponse>
        get() = _addClient
    suspend fun addClient(addClientBody: addClientBody){
        Log.d("add users->","body = $addClientBody")
        val response = apiService.addClient(addClientBody)
        Log.d("add users->","response = ${response.body()}")

        if(response.isSuccessful && response.body()!=null){
            // get the categories
            _addClient.emit(response.body()!!)

        }
    }

    private val _editClient = MutableStateFlow<ApiResponse.AddClientResponse>(ApiResponse.AddClientResponse())
    val editClients:StateFlow<ApiResponse>
        get() = _addClient
    suspend fun editClient(context: Context, clientId:Int, editClientBody: editClientBody){
        Log.d("edit users->","body = $editClientBody")
        val response = apiService.editClient(clientId = clientId,editClientBody)
        Log.d("edit users->","response = ${response.body()}")

        if(response.isSuccessful && response.body()!=null){
            // get the categories
            _editClient.emit(response.body()!!)

        }else{
            // Show success toast
            withContext(Dispatchers.Main) {
                Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show()
            }

        }
    }

}