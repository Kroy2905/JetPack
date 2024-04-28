package com.kroy.composestudy

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.kroy.composestudy.ui.theme.ComposeStudyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PreviewFunction()
//            ComposeStudyTheme {
//                // A surface container using the 'background' color from the theme
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colorScheme.background
//                ) {
//                    Greeting("Android")
//                }
//            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}


@Composable
fun GreetingPreview() {
    ComposeStudyTheme {
        Greeting("Android")
    }

}
@Preview(showBackground = true)
@Composable
fun PreviewFunction(){
    /** Text */
//        Text(text = "Hello world",
//            fontFamily = FontFamily.Cursive,
//            fontSize = 20.sp,
//            fontWeight = FontWeight.ExtraBold,
//            color = Color.Red,
//            textAlign = TextAlign.Center
//        )
    /** Image */
//    Image(
//        painter = painterResource(id = R.drawable.ic_launcher_foreground),
//        contentDescription ="Image",
//        colorFilter = ColorFilter.tint(Color.Blue),
//        alignment = Alignment.TopCenter,
//        alpha = 0.3f,
//        contentScale = ContentScale.Inside
//
//
//        )
      /**Button*/
//    Button(onClick = {},
//        colors = ButtonDefaults.buttonColors(
//            containerColor = Color.Red,
//            contentColor = Color.Black
//
//        )
//
//        ) {
//        Column {
//            Text(text = "Button",
//                textAlign = TextAlign.Center)
//            Image(
//                painter = painterResource(id = R.drawable.ic_launcher_foreground),
//                contentDescription ="Image",
//                colorFilter = ColorFilter.tint(Color.Blue),
//                alpha = 0.3f
//            )
//        }
//
//
//
//    }
      val state = remember {
          mutableStateOf("")
      }
    TextField(value = state.value,
        onValueChange = {
                        state.value =it
        },
        label = {
            Text(text = "Enter String"
            )
        },
        placeholder = {

        }

        )
}