package com.kroy.sseditor.api

import com.kroy.sseditor.models.TweetListItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

interface TweetsyApi {
    @GET("/v3/b/66ec5cb6ad19ca34f8a91599/?meta=false")
    suspend fun getTweets(@Header("X-JSON-Path") category:String):Response<List<TweetListItem>>

    @GET("/v3/b/66ec5cb6ad19ca34f8a91599/?meta=false")
    @Headers("X-JSON-Path:tweets..category")
    suspend fun  getCategory():Response<List<String>>
}