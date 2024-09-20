package com.kroy.composestudy.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kroy.composestudy.models.TweetListItem
import com.kroy.composestudy.viewmodels.DetailsViewmodel
import kotlinx.coroutines.flow.StateFlow

@Composable
fun DetailScreen() {
    val detailsViewmodel:DetailsViewmodel  = viewModel()
    val tweets: State<List<TweetListItem>> = detailsViewmodel.tweets.collectAsState()
    // Content for the detail screen goes here
    LazyColumn(userScrollEnabled = true, content = {
        items(tweets.value){
            TweetListItem(tweet = it.text)
        }
    })
}

@Composable
fun TweetListItem(tweet: String) {
    Card(
        modifier = Modifier

            .fillMaxWidth()

            .padding(16.dp),
        border = BorderStroke(1.dp, Color(0xFFCAE1F1)),
        colors = CardDefaults.cardColors(containerColor = Color(0x9FD9EBF8)) // Set the background color to blue
    ) {
        Text(
            text = tweet,
            modifier = Modifier.padding(18.dp),

            style = MaterialTheme.typography.labelLarge
        )
    }
}
