package com.kroy.quoteApp

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kroy.quoteApp.models.Quote
import com.kroy.quoteApp.screen.Pages
import java.io.IOException

object DataManager {

    var data = emptyArray<Quote>()
    var isDataLoaded = mutableStateOf(false)
    var currentPage = mutableStateOf(Pages.LISTING)
    var currentQuote:Quote? = null
    data class QuoteResponse(val quotes: Array<Quote>) // A wrapper class for the root object



    fun loadQuotesFromAsset(context: Context) {
        try {
            val inputStream = context.assets.open("quotes.json")
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            val jsonString = String(buffer, Charsets.UTF_8)

            Log.d("DataManager", "JSON String: $jsonString")

            val gson = Gson()
            val listType = object : TypeToken<QuoteResponse>() {}.type
            val quoteResponse: QuoteResponse = gson.fromJson(jsonString, listType)

            // Check if the data is populated correctly
            if (quoteResponse.quotes.isNotEmpty()) {
                Log.d("DataManager", "Quotes Loaded Successfully: ${quoteResponse.quotes.size} quotes")
            } else {
                Log.d("DataManager", "Quotes Loaded but empty")
            }

            data = quoteResponse.quotes
            isDataLoaded.value = true

        } catch (ex: IOException) {
            Log.e("DataManager", "Error loading quotes", ex)
        } catch (jsonEx: Exception) {
            Log.e("DataManager", "Error parsing JSON", jsonEx)
        }
    }


    fun switchPages(quote: Quote?){
        if (currentPage.value == Pages.LISTING){
            currentQuote = quote
            currentPage.value = Pages.DETAIL
        }

        else
            currentPage.value = Pages.LISTING
    }


}