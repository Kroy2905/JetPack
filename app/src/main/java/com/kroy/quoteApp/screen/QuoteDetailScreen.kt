package com.kroy.quoteApp.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FormatQuote
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kroy.quoteApp.models.quote

@Composable
 fun QuoteDetails(quote: quote){
    Box(contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize(1f)

            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFCEF813),
                        Color(0xFFC3F33C),
                        Color(0xFF87A337),
                        Color(0xFFD8EE09)

                    )

                )
            )){
        Card (elevation = CardDefaults.cardElevation(8.dp),
            modifier = Modifier

                .padding(40.dp)){
            Column (
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(15.dp)

                    .wrapContentSize()

            ){
                Image(
                    imageVector = Icons.Rounded.FormatQuote,
                    contentDescription = "bg",
                    Modifier
                        .size(80.dp)
                        .rotate(180f),

                    contentScale = ContentScale.FillBounds,
                )

                Text(
                    text = quote.quote,
                    lineHeight = 20.sp,
                    fontSize = 20.sp,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Black,
                    maxLines = 2


                )
                Spacer(modifier = Modifier
                    .fillMaxWidth()
                    .height(5.dp)
                )


                Text(
                    text = quote.author,
                    fontSize = 20.sp,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Normal,
                    maxLines = 2


                )

            }

        }

    }
}