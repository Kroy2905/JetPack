package com.kroy.sseditor.api

import com.kroy.sseditor.models.ApiResponse
import com.kroy.sseditor.models.TweetListItem
import com.kroy.sseditor.models.addClientBody
import com.kroy.sseditor.models.editClientBody
import com.kroy.sseditor.models.userloginBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface ApiService {
    @GET("/v3/b/66ec5cb6ad19ca34f8a91599/?meta=false")
    suspend fun getTweets(@Header("X-JSON-Path") category:String):Response<List<TweetListItem>>

    @GET("/v3/b/66ec5cb6ad19ca34f8a91599/?meta=false")
    @Headers("X-JSON-Path:tweets..category")
    suspend fun  getCategory():Response<List<String>>

    @POST("login")    // API to send log details to server
    suspend fun  loginUser(@Body driverLogsBody: userloginBody,

    ) : Response <ApiResponse.UserLoginResponse>

    @GET("clients")    // API to be written here
    suspend fun getAllClients(@Query("userId") userId:Int,
    ) : Response <ApiResponse.AllClientResponse>

    @POST("clients")    // API to be written here
    suspend fun addClient(@Body driverLogsBody: addClientBody,
    ) : Response <ApiResponse.AddClientResponse>

    @PUT("clients/updateclient")    // API to be written here
    suspend fun editClient(@Query("clientId") clientId:Int,
                           @Body editClientBody: editClientBody
    ) : Response <ApiResponse.AddClientResponse>



}