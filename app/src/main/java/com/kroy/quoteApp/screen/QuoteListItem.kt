package com.kroy.quoteApp.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kroy.quoteApp.R
import com.kroy.quoteApp.models.Quote




@Composable
fun QuoteListItem(quote: Quote, onClick:(quote:Quote)->Unit){
    Card (
        elevation = CardDefaults.cardElevation(8.dp),
        modifier = Modifier
            .padding(10.dp)
            .clickable { onClick(quote) }

    ){
        Row(horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .wrapContentHeight()
                .padding(10.dp)) {
            Image(
                imageVector = Icons.Rounded.FormatQuote,
                contentDescription = "bg",
                Modifier
                    .size(60.dp)

                    .weight(.2f),

                contentScale = ContentScale.Fit,
            )
            ItemDescription(quote.quote, quote.author, Modifier.weight(.8f))

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
            fontSize = 14.sp,
            lineHeight =20.sp,
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Normal,
            maxLines = 4,
            modifier = Modifier



        )
        Box(modifier = Modifier
            .padding(0.dp,5.dp)
            .background(Color.Gray)
            .fillMaxWidth(.4f)
            .height(1.dp)

        ){

        }
        Text(
            text = desc,
            fontSize = 14.sp,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.ExtraBold,
            maxLines = 2


        )
    }
}
enum class Pages{
    LISTING,
    DETAIL
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
