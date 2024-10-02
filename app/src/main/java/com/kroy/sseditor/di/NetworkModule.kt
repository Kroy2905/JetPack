package com.kroy.sseditor.di

import com.kroy.sseditor.api.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {


    @Singleton
    @Provides
    fun providesRetrofit():Retrofit{
        return Retrofit.Builder()
           // .baseUrl("https://api.jsonbin.io")
            .baseUrl("http://13.202.225.156:5000/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    @Singleton
    @Provides
    fun providesTweetsyApi(retrofit: Retrofit):ApiService{
        return retrofit.create(ApiService::class.java)
    }
}