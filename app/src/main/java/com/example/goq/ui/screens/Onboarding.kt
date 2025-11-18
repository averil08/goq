package com.example.goq.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.goq.R

// ---------------------------
// REUSABLE CLEAN LAYOUT
// ---------------------------
@Composable
fun OnboardingPage(
    title: String,
    subtitle: String,
    imageRes: Int,
    pageIndex: Int,       // 0 = first dot active, 1 = second, 2 = third
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 35.dp, vertical = 60.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Title texts
        Text(
            text = title,
            color = Color(0xff757575),
            fontSize = 19.sp,
            fontWeight = FontWeight.Light
        )

        Text(
            text = subtitle,
            color = Color(0xff4363ec),
            fontSize = 24.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 8.dp)
        )

        Spacer(Modifier.height(40.dp))

        // Main Image Box
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(320.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Color(0xffd4ddee).copy(alpha = 0.28f)),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = subtitle,
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .height(250.dp),
            )
        }

        Spacer(Modifier.height(30.dp))

        // Paging indicators
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(3) { index ->
                if (index == pageIndex) {
                    Box(
                        Modifier
                            .width(44.dp)
                            .height(10.dp)
                            .clip(RoundedCornerShape(15.dp))
                            .background(Color(0xff4363ec))
                            .padding(horizontal = 4.dp)
                    )
                } else {
                    Box(
                        Modifier
                            .size(10.dp)
                            .clip(CircleShape)
                            .background(Color(0xffd0d8f0))
                            .padding(4.dp)
                    )
                }
                Spacer(Modifier.width(10.dp))
            }
        }

        Spacer(Modifier.weight(1f))

        // Reusable bottom button
        PrimaryButton(
            text = "Find my University"
        )
    }
}


// ----------------------------------
// REUSABLE BUTTON
// ----------------------------------
@Composable
fun PrimaryButton(
    text: String,
    modifier: Modifier = Modifier,
    icon: Int? = null
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(58.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(Color(0xff4363ec)),
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            icon?.let { nonNullIcon ->
                Image(
                    painter = painterResource(id = nonNullIcon),
                    contentDescription = null,
                    modifier = Modifier
                        .size(28.dp)
                        .padding(end = 8.dp)
                )
            }

            // Always show the text
            Text(
                text = text,
                fontSize = 17.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White
            )
        }
    }
}



// ----------------------------------
// CLEAN PUBLIC SCREENS
// ----------------------------------
@Composable
fun Onboarding1() {
    OnboardingPage(
        title = "Welcome to GoQ!",
        subtitle = "Easily navigate your university",
        imageRes = R.drawable.school1,
        pageIndex = 0
    )
}

@Composable
fun Onboarding2() {
    OnboardingPage(
        title = "Welcome to GoQ!",
        subtitle = "Get your digital queue ticket for real-time updates",
        imageRes = R.drawable.qticket,
        pageIndex = 1
    )
}

@Composable
fun Onboarding3() {
    OnboardingPage(
        title = "Welcome to GoQ!",
        subtitle = "Track your course schedules",
        imageRes = R.drawable.calendar,
        pageIndex = 2
    )
}


// ----------------------------------
// PREVIEWS
// ----------------------------------
@Preview(showBackground = true)
@Composable
fun Preview1() { Onboarding1() }

@Preview(showBackground = true)
@Composable
fun Preview2() { Onboarding2() }

@Preview(showBackground = true)
@Composable
fun Preview3() { Onboarding3() }
