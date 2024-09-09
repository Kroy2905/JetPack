package com.kroy.quoteApp.screen

import android.graphics.drawable.Icon
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FormatQuote
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kroy.quoteApp.R

@Preview
@Composable
fun PreviewBlog(){
//    Column (
//        modifier = Modifier
//            .verticalScroll(rememberScrollState())
//    ){
//        getDummyCategory().map {
//            BlogCategory(imgId = it.imgId, title = it.title, desc = it.subTitle )
//        }
//    }
    QuoteListItem(imgId = R.drawable.person,
        title = "Koustav",
        desc ="This is a sample text" )
//    LazyColumn(content ={
//        item { getDummyCategory().map {item->
//            QuoteListItem(imgId = item.imgId, title = item.title, desc = item.subTitle )
//        } }
//    })
}


@Composable
fun QuoteListItem(imgId:Int, title:String, desc:String){
    Card (
        elevation = CardDefaults.cardElevation(8.dp),
        modifier = Modifier
            .padding(10.dp)
    ){
        Row(horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .height(70.dp)
                .padding(10.dp)) {
            Image(
                imageVector = Icons.Rounded.FormatQuote,
                contentDescription = "bg",
                Modifier
                    .wrapContentWidth()
                    .fillMaxHeight()
                    .weight(.2f),

                contentScale = ContentScale.FillHeight,
            )
            ItemDescription(title, desc, Modifier.weight(.8f))

        }

    }
}

@Composable
private fun ItemDescription(title: String, desc: String,modifier: Modifier) {
    Column(
        modifier
            .padding(5.dp),
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Text(
            text = title,
            fontSize = 12.sp,
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.ExtraBold,
            maxLines = 2

        )
        Box(modifier =Modifier
            .background(Color.Gray)
            .fillMaxWidth(.4f)
            .height(1.dp)

        ){

        }
        Text(
            text = desc,
            fontSize = 10.sp,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Thin,
            maxLines = 2


        )
    }
}
data class Category (
    val imgId:Int,
    val title:String,
    val subTitle:String
)
fun getDummyCategory() : MutableList<Category>{
    val list = mutableStateListOf<Category>()
    list.add(Category(R.drawable.a, "Title a", "Subtitle a"))
    list.add(Category(R.drawable.b, "Title b", "Subtitle b"))
    list.add(Category(R.drawable.c, "Title c", "Subtitle c"))
    list.add(Category(R.drawable.d, "Title d", "Subtitle d"))
    list.add(Category(R.drawable.e, "Title e", "Subtitle e"))
    list.add(Category(R.drawable.f, "Title f", "Subtitle f"))

    list.add(Category(R.drawable.a, "Title a", "Subtitle a"))
    list.add(Category(R.drawable.b, "Title b", "Subtitle b"))
    list.add(Category(R.drawable.c, "Title c", "Subtitle c"))
    list.add(Category(R.drawable.d, "Title d", "Subtitle d"))
    list.add(Category(R.drawable.e, "Title e", "Subtitle e"))
    list.add(Category(R.drawable.f, "Title f", "Subtitle f"))


    list.add(Category(R.drawable.a, "Title a", "Subtitle a"))
    list.add(Category(R.drawable.b, "Title b", "Subtitle b"))
    list.add(Category(R.drawable.c, "Title c", "Subtitle c"))
    list.add(Category(R.drawable.d, "Title d", "Subtitle d"))
    list.add(Category(R.drawable.e, "Title e", "Subtitle e"))
    list.add(Category(R.drawable.f, "Title f", "Subtitle f"))


    list.add(Category(R.drawable.a, "Title a", "Subtitle a"))
    list.add(Category(R.drawable.b, "Title b", "Subtitle b"))
    list.add(Category(R.drawable.c, "Title c", "Subtitle c"))
    list.add(Category(R.drawable.d, "Title d", "Subtitle d"))
    list.add(Category(R.drawable.e, "Title e", "Subtitle e"))
    list.add(Category(R.drawable.f, "Title f", "Subtitle f"))
    return list
}
