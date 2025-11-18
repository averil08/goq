package com.example.goq.ui.screens
import com.example.goq.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun OpeningScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .requiredWidth(width = 414.dp)
            .requiredHeight(height = 896.dp)
            .background(color = Color(0xff2445ce))
    ) {
        Box(
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(
                    x = 243.dp,
                    y = (-69).dp
                )
                .requiredSize(size = 245.dp)
                .clip(shape = CircleShape)
                .background(color = Color(0xffd9d9d9).copy(alpha = 0.12f))
        )
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "logo",
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(
                    x = 116.dp,
                    y = 312.dp
                )
                .requiredSize(size = 181.dp)
                .clip(shape = RoundedCornerShape(599.dp))
        )
        Text(
            text = "GoQ!",
            color = Color.White,
            textAlign = TextAlign.Center,
            style = TextStyle(
                fontSize = 29.sp
            ),
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(
                    x = 170.dp,
                    y = 506.dp
                )
        )
        Box(
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(
                    x = (-73).dp,
                    y = 704.dp
                )
                .requiredWidth(width = 243.dp)
                .requiredHeight(height = 245.dp)
                .background(color = Color(0xffd9d9d9).copy(alpha = 0.12f))
        )
        Text(
            text = "GoQ! is a mobile app that manages school enrollment queues with digital numbers and real-time updates.",
            color = Color.White,
            textAlign = TextAlign.Center,
            style = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight.Light
            ),
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(
                    x = 20.dp,
                    y = 760.dp
                )
                .requiredWidth(width = 374.dp)
        )
    }
}

@Preview(widthDp = 414, heightDp = 896)
@Composable
private fun OpeningPreview() {
    OpeningScreen(Modifier)
}