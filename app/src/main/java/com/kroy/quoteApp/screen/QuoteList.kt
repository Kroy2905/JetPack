package com.kroy.quoteApp.screen

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.kroy.quoteApp.models.quote

@Composable
fun QuoteList(data:Array<quote>,onClick:()->Unit){
        LazyColumn(content ={
        items(data){
            QuoteListItem(quote =it ) {
                onClick()
            }


        }
    })
}