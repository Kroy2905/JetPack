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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kroy.quoteApp.ui.theme.ComposeStudyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
                    PreviewBlog()
                }
            }
        }
    }
}
@Preview
@Composable
fun CircularImage(){

    Image(painter = painterResource(id = R.drawable.person),
        contentDescription ="Circular Image",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .wrapContentSize()


            .size(80.dp)
            .clip(CircleShape)
            .border(2.dp, Color.LightGray, CircleShape)
            .padding(10.dp)
    )
}


@Composable
fun ListViewItem(imgID:Int , name:String ,profession:String){
    Row(horizontalArrangement = Arrangement.Start,
        modifier = Modifier
            .padding(10.dp)) {
        Column (){
            Box(
                contentAlignment = Alignment.Center
            ){
                Image(painter = painterResource(id = imgID),
                    contentDescription = "bg",
                    Modifier.size(80.dp),
                    contentScale = ContentScale.Inside,


                    )
                Image(painter = painterResource(id = R.drawable.person),
                    contentDescription = "person",
                    Modifier.
                    size(40.dp)
                    )
            }
        }
        Column(
            Modifier
                .padding(5.dp,10.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ){
            Text(
                text = name,
                fontSize = 25.sp,
                fontStyle =FontStyle.Normal,
                fontWeight = FontWeight.ExtraBold,
                maxLines = 1

                )
            Text(
                text = profession,
                fontSize = 18.sp,
                fontStyle =FontStyle.Normal,
                maxLines = 1



            )
        }

    }
}


