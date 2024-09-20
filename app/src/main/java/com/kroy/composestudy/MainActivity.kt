package com.kroy.composestudy

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.kroy.composestudy.ui.theme.ComposeStudyTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App2() // for navigation

//            ComposeStudyTheme {
//                // A surface container using the 'background' color from the theme
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colorScheme.background
//                ) {
//                   // ListViewItem(R.drawable.img,"Koustav","Android Developer")
//                  //  PreviewFunction()
//                  //  CircularImage()
//                //    PreviewBlog()
//                 //   Counter()
//                    App()
//                }
//            }
        }
    }
}

@Composable
fun App2() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "registration") {
        composable(route = "Login") {
            LoginScreen()
        }
       // composable(route = "main/{email}/{}/{}", arguments = listOf(
        composable(route = "main/{email}", arguments = listOf(
            navArgument("email"){
                type = NavType.StringType
            }
        )) {
           val email =  it.arguments?.getString("email")


            MainScreen(email!!)

        }
        composable(route = "registration") {
            RegsiterScreen{email->
                navController.navigate("main/$email")
            }
        }

    }
}



@Composable
fun RegsiterScreen(onclick:(emal:String)->Unit){
    Text(
        text = "Register",
        fontSize = 108.sp,
        fontStyle =FontStyle.Normal,
        maxLines = 1,
        modifier = Modifier
            .clickable {
                onclick("kroy@gmail.com")
            }



    )
}

@Composable
fun LoginScreen(){
    Text(
        text = "Login",
        fontSize = 108.sp,
        fontStyle =FontStyle.Normal,
        maxLines = 1



    )
}

@Composable
fun MainScreen(emal: String?){
    Text(
        text = "Main - $emal",
        fontSize = 108.sp,
        fontStyle =FontStyle.Normal,
        maxLines = 10



    )
}

@Composable
fun DerivedAndProduced(){
    val tableOf = remember {
        mutableStateOf(5)
    }
    val index = produceState(initialValue = 1 ){
        repeat(9){
            delay(1000)
            value+=1
        }
    }
    val message = remember {
        derivedStateOf {
            "${tableOf.value} * ${index.value} = ${tableOf.value * index.value}"
        }
    }
    Box(
        contentAlignment =  Alignment.Center,
        modifier =  Modifier.fillMaxSize(1f)
        
    ){
        Text(text = message.value)
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


@Composable
fun Row_and_Column () {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "A",

        )
        Text(
            text = "B"
        )
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.Bottom
        ){
            Text(
                text = "C",

                )
            Text(
                text = "D"
            )
        }
    }

}



@Preview(showBackground = true)
@Composable
fun PreviewFunction(){
    /** Text */
        Text(text = "Hello world",
            fontFamily = FontFamily.Cursive,
            fontSize = 20.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color.Red,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .wrapContentHeight()
                .border(10.dp, Color.Red)
                .padding(30.dp)
                .clickable {
                    Log.d("Text click ->", "Hello!")
                }
        )
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
//      val state = remember {
//          mutableStateOf("")
//      }
//    TextField(value = state.value,
//        onValueChange = {
//                        state.value =it
//        },
//        label = {
//            Text(text = "Enter String"
//            )
//        },
//        placeholder = {
//
//        }
//
//        )
}
@Composable
fun App(){
    var counter = remember{ mutableStateOf( 0) }
    DerivedAndProduced()
    LaunchedEffect(key1 = Unit){
//        delay(2000)
//        counter.value  = 10

    }
    //Counter2(value = counter.value)
}
@Composable
fun Counter2(value:Int){
val state = rememberUpdatedState(newValue = value)
    LaunchedEffect(key1 = Unit){ // whenever the key value changes , then only the block gets executed
        delay (5000) // Long running task
        Log.d("Button click","Button clicked counter increased ${state.value}")
    }

        Text(text = "Value  =  ${state.value}")

}
@Composable
fun Counter(){
    var count = remember {
        mutableStateOf(0)
    }
    var key = count.value%3==0
    LaunchedEffect(key1 = key){ // whenever the key value changes , then only the block gets executed

        Log.d("Button click","Button clicked counter increased ${count.value}")
    }
    Button(onClick = { count.value++
    }) {
        Text(text = "Increment button")
    }
}