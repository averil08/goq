package com.example.goq.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.goq.R

// ==================== FIND UNIVERSITY SCREEN (FIRST SCREEN) ====================
@Composable
fun FindUI(
    modifier: Modifier = Modifier,
    onUniversitySelected: (String) -> Unit = {}
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        Text(
            text = "What's your University?",
            color = Color(0xff4363ec),
            textAlign = TextAlign.Center,
            style = TextStyle(
                fontSize = 26.sp,
                fontWeight = FontWeight.Medium
            ),
            modifier = Modifier
                .align(Alignment.TopStart)
                .offset(x = 66.dp, y = 117.dp)
        )

        Text(
            text = "Choose your school from the list",
            color = Color(0xff757575),
            textAlign = TextAlign.Center,
            style = TextStyle(fontSize = 18.sp),
            modifier = Modifier
                .align(Alignment.TopStart)
                .offset(x = 77.dp, y = 161.dp)
        )

        // University Cards
        Column(
            modifier = Modifier
                .align(Alignment.TopStart)
                .offset(x = 66.dp, y = 219.dp),
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            // Fairview College
            UniversityCard(
                name = "Fairview College",
                logo = R.drawable.fairview,
                onClick = { onUniversitySelected("Fairview College") }
            )

            // University of Academia
            UniversityCard(
                name = "University of Academia",
                logo = R.drawable.ua,
                onClick = { onUniversitySelected("University of Academia") }
            )

            // University Del Mundo
            UniversityCard(
                name = "University Del Mundo",
                logo = R.drawable.delmundo,
                onClick = { onUniversitySelected("University Del Mundo") }
            )

            // TPLEX College
            UniversityCard(
                name = "TPLEX College",
                logo = R.drawable.tplex,
                onClick = { onUniversitySelected("TPLEX College") }
            )
        }
    }
}

@Composable
fun UniversityCard(
    name: String,
    logo: Int,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .requiredWidth(292.dp)
            .requiredHeight(72.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(color = Color(0xfffffdfd))
            .border(
                border = BorderStroke(1.dp, Color(0xffd0d8e2)),
                shape = RoundedCornerShape(10.dp)
            )
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 19.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Image(
                painter = painterResource(id = logo),
                contentDescription = "$name logo",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(60.dp)
            )
            Text(
                text = name,
                color = Color(0xff071a2b),
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            )
        }
    }
}

// ==================== STUDENT LOGIN SCREEN (SECOND SCREEN) ====================
@Composable
fun StudentLogin(
    modifier: Modifier = Modifier,
    selectedUniversity: String = "",
    onBackClick: () -> Unit = {},
    onLoginClick: () -> Unit = {}
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color(0xffecf0f3))
    ) {
        // Back Arrow
        IconButton(
            onClick = onBackClick,
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(x = 33.dp, y = 52.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                modifier = Modifier.size(32.dp),
                tint = Color.Black
            )
        }

        // Logo/Icon
        Box(
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(x = 130.dp, y = 120.dp)
                .size(150.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "App Logo",
                modifier = Modifier.size(150.dp),
                tint = Color(0xff4363ec)
            )
        }

        // Welcome Text with University Name
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(x = 68.dp, y = 382.dp)
                .width(277.dp)
        ) {
            Text(
                text = "Welcome Back!",
                color = Color.Black,
                textAlign = TextAlign.Center,
                style = TextStyle(
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Medium
                )
            )

            if (selectedUniversity.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = selectedUniversity,
                    color = Color(0xff4363ec),
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                )
            }
        }

        // Subtitle
        Text(
            text = "Log in to access student enrollment portal and get your queue ticket online",
            color = Color.Black,
            textAlign = TextAlign.Center,
            style = TextStyle(
                fontSize = 13.sp,
                fontWeight = FontWeight.Light
            ),
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(x = 68.dp, y = 453.dp)
                .requiredWidth(width = 277.dp)
        )

        // Login Form
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(x = 40.dp, y = 505.dp)
                .width(334.dp)
                .padding(24.dp)
        ) {
            // Email Field
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "Email",
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                )
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    placeholder = { Text("Enter your email") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xff4363ec),
                        unfocusedBorderColor = Color(0xffd0d8e2)
                    ),
                    shape = RoundedCornerShape(8.dp)
                )
            }

            // Password Field
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "Password",
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                )
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    placeholder = { Text("Enter your password") },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xff4363ec),
                        unfocusedBorderColor = Color(0xffd0d8e2)
                    ),
                    shape = RoundedCornerShape(8.dp)
                )
            }

            // Login Button
            Button(
                onClick = onLoginClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xff4363ec)
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "Log In",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                )
            }

            // Forgot Password Link
            Text(
                text = "Forgot Password?",
                color = Color(0xff4363ec),
                textDecoration = TextDecoration.Underline,
                style = TextStyle(fontSize = 14.sp),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .clickable { /* Handle forgot password */ }
            )
        }
    }
}

// ==================== PREVIEWS ====================
@Preview(widthDp = 414, heightDp = 896)
@Composable
private fun FindUIPreview() {
    FindUI()
}

@Preview(widthDp = 414, heightDp = 896)
@Composable
private fun StudentLoginPreview() {
    StudentLogin(selectedUniversity = "Fairview College")
}