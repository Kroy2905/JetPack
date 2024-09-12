package com.kroy.quoteApp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kroy.quoteApp.screen.Pages
import com.kroy.quoteApp.screen.QuoteDetails
import com.kroy.quoteApp.screen.QuoteListScreen
import com.kroy.quoteApp.ui.theme.ComposeStudyTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CoroutineScope(Dispatchers.IO).launch {
            delay(2000)
            DataManager.loadQuotesFromAsset(this@MainActivity)


        }

        setContent {


            ComposeStudyTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                   // ListViewItem(R.drawable.img,"Koustav","Android Developer")
                  //  PreviewFunction()
                  //  CircularImage()
                  //  PreviewBlog()

                    App()


                }
            }
        }
    }
}

@Composable
fun App(){

    if(DataManager.isDataLoaded.value){
        Log.d("data ==","${DataManager.data.contentToString()}")
        if(DataManager.currentPage.value == Pages.LISTING){
            Log.d("data ==","${DataManager.data.contentToString()}")
            QuoteListScreen(data = DataManager.data) {
                DataManager.switchPages(it)
            }
        }else{
            DataManager.currentQuote?.let {

                QuoteDetails(quote =it)
            }
        }

    }
    else{
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize(1f)
        ){
            Text(text = "Loading....",
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(8.dp, 24.dp)
                    .fillMaxWidth(1f),
                style = MaterialTheme.typography.titleLarge
            )
        }
    }


}




