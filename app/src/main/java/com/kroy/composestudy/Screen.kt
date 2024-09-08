package com.kroy.composestudy


import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
//    BlogCategory(imgId = R.drawable.person,
//        title = "Koustav",
//        desc ="This is a sample text" )
    LazyColumn(content ={
        item { getDummyCategory().map {item->
                BlogCategory(imgId = item.imgId, title = item.title, desc = item.subTitle )
        } }
    })
}


@Composable
fun BlogCategory(imgId:Int,title:String,desc:String){
    Card (
        elevation = CardDefaults.cardElevation(8.dp),
        modifier = Modifier
            .padding(10.dp)
    ){
        Row(horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)) {
            Image(painter = painterResource(id = imgId),
                contentDescription = "bg",
                Modifier
                    .size(40.dp)
                    .weight(.2f),

                contentScale = ContentScale.FillHeight,
            )


            ItemDescription(title, desc,Modifier.weight(.8f))

        }

    }
}

@Composable
private fun ItemDescription(title: String, desc: String,modifier: Modifier) {
    Column(
        modifier
            .padding(5.dp, 5.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            fontSize = 15.sp,
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.ExtraBold,
            maxLines = 1

        )
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
