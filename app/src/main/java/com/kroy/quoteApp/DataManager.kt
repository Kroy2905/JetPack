package com.kroy.quoteApp

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kroy.quoteApp.models.quote
import java.io.IOException

object DataManager {

    var data = emptyArray<quote>()
    var isDataLoaded = mutableStateOf(false)
    data class QuoteResponse(val quotes: Array<quote>) // A wrapper class for the root object



    fun loadQuotesFromAsset(context: Context) {
        try {
            val inputStream = context.assets.open("quotes.json")
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            val jsonString = String(buffer, Charsets.UTF_8)

            // Print raw JSON string for debugging
            println("JSON String: $jsonString")

            // Parse the root object and then access the array of quotes
            val gson = Gson()
            val listType = object : TypeToken<QuoteResponse>() {}.type
            val quoteResponse: QuoteResponse = gson.fromJson(jsonString, listType)

            // Assign the quotes array to the data variable
            data = quoteResponse.quotes

            // Debugging: print the first quote to check
//            println("First Quote: ${data.firstOrNull()?.text}")
//            println("First Author: ${data.firstOrNull()?.author}")
            println(data.contentToString())
            isDataLoaded.value = true

        } catch (ex: IOException) {
            ex.printStackTrace()
        }
    }


}