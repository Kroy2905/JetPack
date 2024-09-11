package com.kroy.quoteApp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
        QuoteListScreen(data = DataManager.data) {

        }
    }else{
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize(1f)
        ){
            Text(text = "Loading....",
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(8.dp,24.dp)
                    .fillMaxWidth(1f),
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}




