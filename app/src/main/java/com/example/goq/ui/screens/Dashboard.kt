package com.example.goq.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.goq.R

@Composable
fun Dashboard(
    modifier: Modifier = Modifier,
    onEnrollmentClick: () -> Unit = {},
    onQueueClick: () -> Unit = {}
) {
    var isSidebarOpen by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        // Main Dashboard Content (dimmed when sidebar is open)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    if (isSidebarOpen) Color.Black.copy(alpha = 0.3f)
                    else Color.Transparent
                )
        ) {
            DashboardContent(
                onMenuClick = { isSidebarOpen = true },
                isDimmed = isSidebarOpen,
                onEnrollmentClick = onEnrollmentClick,
                onQueueClick = onQueueClick
            )
        }

        // Sidebar Navigation (slides in from right)
        AnimatedVisibility(
            visible = isSidebarOpen,
            enter = slideInHorizontally(
                initialOffsetX = { it },
                animationSpec = tween(300)
            ),
            exit = slideOutHorizontally(
                targetOffsetX = { it },
                animationSpec = tween(300)
            ),
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .zIndex(10f)
        ) {
            SidebarNavigation(
                onClose = { isSidebarOpen = false },
                onChangePassword = {
                    isSidebarOpen = false
                    // TODO: Navigate to change password
                },
                onSwitchAccount = {
                    isSidebarOpen = false
                    // TODO: Navigate to switch account
                },
                onLogout = {
                    isSidebarOpen = false
                    // TODO: Handle logout
                }
            )
        }
    }
}

@Composable
fun DashboardContent(
    onMenuClick: () -> Unit,
    isDimmed: Boolean,
    onEnrollmentClick: () -> Unit,
    onQueueClick: () -> Unit
) {
    val alpha = if (isDimmed) 0.7f else 1f

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        // Profile Picture
        Box(
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(x = 36.dp, y = 80.dp)
                .size(69.dp)
                .clip(CircleShape)
                .background(Color(0xFFE0E0E0)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Profile",
                modifier = Modifier.size(50.dp),
                tint = Color(0xFF9E9E9E).copy(alpha = alpha)
            )
        }

        // Greeting Text
        Text(
            text = "Hello, good day!",
            color = Color(0xff2f4431).copy(alpha = alpha),
            style = TextStyle(
                fontSize = 15.sp,
                fontWeight = FontWeight.Light
            ),
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(x = 111.dp, y = 94.dp)
        )

        // Hamburger Menu Button
        Box(
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(x = 348.dp, y = 97.dp)
                .size(31.dp, 23.dp)
                .clickable { onMenuClick() }
        ) {
            Box(
                modifier = Modifier
                    .size(11.dp)
                    .clip(CircleShape)
                    .background(color = Color(0xff3a57cf).copy(alpha = 0.54f * alpha))
            )
            Divider(
                color = Color.Black.copy(alpha = alpha),
                modifier = Modifier
                    .align(alignment = Alignment.TopStart)
                    .offset(x = 12.dp, y = 7.dp)
                    .width(19.dp)
            )
            Divider(
                color = Color.Black.copy(alpha = alpha),
                modifier = Modifier
                    .align(alignment = Alignment.TopStart)
                    .offset(x = 4.dp, y = 15.dp)
                    .width(27.dp)
            )
            Divider(
                color = Color.Black.copy(alpha = alpha),
                modifier = Modifier
                    .align(alignment = Alignment.TopStart)
                    .offset(x = 4.dp, y = 23.dp)
                    .width(27.dp)
            )
        }

        // User Name
        Text(
            text = "Aliza C. Manaoag",
            color = Color.Black.copy(alpha = alpha),
            style = TextStyle(
                fontSize = 19.sp,
                fontWeight = FontWeight.Medium
            ),
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(x = 111.dp, y = 113.dp)
        )

        // Dashboard Banner
        Box(
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(x = 36.dp, y = 165.dp)
                .size(344.dp, 104.dp)
                .clip(shape = RoundedCornerShape(10.dp))
                .background(color = Color(0xffecf0f3).copy(alpha = alpha))
        )

        // School Logo (fallback to icon)
        Box(
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(x = 259.dp, y = 165.dp)
                .size(102.dp, 98.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.School,
                contentDescription = "School logo",
                modifier = Modifier.size(80.dp),
                tint = Color(0xFF2F4431).copy(alpha = alpha)
            )
        }

        Text(
            text = "Fairview Dashboard",
            color = Color.Black.copy(alpha = alpha),
            style = TextStyle(fontSize = 15.sp),
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(x = 49.dp, y = 181.dp)
        )

        Text(
            text = "Enroll your course schedule, reserve your queue and get real-time updates.",
            color = Color.Black.copy(alpha = alpha),
            lineHeight = 1.2.em,
            style = TextStyle(
                fontSize = 10.sp,
                fontWeight = FontWeight.Light
            ),
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(x = 49.dp, y = 206.dp)
                .width(197.dp)
        )

        // Categories
        Text(
            text = "Categories",
            color = Color.Black.copy(alpha = alpha),
            textAlign = TextAlign.Center,
            style = TextStyle(fontSize = 19.sp),
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(x = 34.dp, y = 299.dp)
        )

        // Category Buttons
        Row(
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(x = 44.dp, y = 335.dp),
            horizontalArrangement = Arrangement.spacedBy(54.dp)
        ) {
            // Enroll Category
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier
                        .size(44.dp, 46.dp)
                        .clip(shape = RoundedCornerShape(10.dp))
                        .background(color = Color.White.copy(alpha = alpha))
                        .shadow(elevation = 4.dp, shape = RoundedCornerShape(10.dp))
                        .clickable { onEnrollmentClick() },
                contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.School,
                        contentDescription = "Enroll",
                        tint = Color(0xFF2F4431).copy(alpha = alpha),
                        modifier = Modifier.size(28.dp)
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Enroll",
                    color = Color.Black.copy(alpha = alpha),
                    style = TextStyle(fontSize = 15.sp)
                )
            }

            // Queue Category
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier
                        .size(44.dp, 46.dp)
                        .clip(shape = RoundedCornerShape(10.dp))
                        .background(color = Color.White.copy(alpha = alpha))
                        .shadow(elevation = 4.dp, shape = RoundedCornerShape(10.dp))
                        .clickable { onQueueClick() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.List,
                        contentDescription = "Queue",
                        tint = Color(0xFF2F4431).copy(alpha = alpha),
                        modifier = Modifier.size(28.dp)
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Queue",
                    color = Color.Black.copy(alpha = alpha),
                    style = TextStyle(fontSize = 15.sp)
                )
            }
        }

        // Student Information Card
        Box(
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(x = 20.dp, y = 433.dp)
                .size(360.dp, 334.dp)
                .clip(shape = RoundedCornerShape(10.dp))
                .background(color = Color.White.copy(alpha = alpha))
                .shadow(elevation = 4.dp, shape = RoundedCornerShape(10.dp))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Student Information",
                        color = Color.Black,
                        style = TextStyle(fontSize = 19.sp)
                    )
                    Icon(
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = "View more",
                        modifier = Modifier.size(24.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Profile Picture
                    Box(
                        modifier = Modifier
                            .size(56.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFE0E0E0)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Profile",
                            modifier = Modifier.size(40.dp),
                            tint = Color(0xFF9E9E9E)
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Aliza C. Manaoag",
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium
                            )
                        )
                        Text(
                            text = "1st Semester\nS.Y. 2024-2025",
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Light
                            )
                        )
                    }

                    Text(
                        text = "BSIT-3",
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Light
                        )
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Student Number and Phone
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(13.dp)
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Student No.",
                            style = TextStyle(fontSize = 15.sp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(34.dp)
                                .clip(RoundedCornerShape(5.dp))
                                .background(Color(0xff757575).copy(alpha = 0.11f))
                                .padding(horizontal = 14.dp),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            Text(
                                text = "349354-2025",
                                style = TextStyle(fontSize = 15.sp)
                            )
                        }
                    }

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Phone No.",
                            style = TextStyle(fontSize = 15.sp),
                            textAlign = TextAlign.End,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(34.dp)
                                .clip(RoundedCornerShape(5.dp))
                                .background(Color(0xff757575).copy(alpha = 0.11f))
                                .padding(horizontal = 14.dp),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            Text(
                                text = "09876543219",
                                style = TextStyle(fontSize = 15.sp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Address and Status
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(36.dp)
                ) {
                    Column(modifier = Modifier.weight(1.5f)) {
                        Text(
                            text = "Address",
                            style = TextStyle(fontSize = 15.sp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(34.dp)
                                .clip(RoundedCornerShape(5.dp))
                                .background(Color(0xff757575).copy(alpha = 0.11f))
                                .padding(horizontal = 10.dp),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            Text(
                                text = "#1 Fairview, Baguio City",
                                style = TextStyle(fontSize = 15.sp)
                            )
                        }
                    }

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Status",
                            style = TextStyle(fontSize = 15.sp),
                            textAlign = TextAlign.End,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(34.dp)
                                .clip(RoundedCornerShape(5.dp))
                                .background(Color(0xff757575).copy(alpha = 0.11f))
                                .padding(horizontal = 14.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Enrolled",
                                style = TextStyle(fontSize = 15.sp),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SidebarNavigation(
    onClose: () -> Unit,
    onChangePassword: () -> Unit,
    onSwitchAccount: () -> Unit,
    onLogout: () -> Unit
) {
    Box(
        modifier = Modifier
            .width(366.dp)
            .fillMaxHeight()
            .clip(RoundedCornerShape(topStart = 30.dp, bottomStart = 30.dp))
            .background(Color.White)
            .padding(32.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Back Button
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .clickable { onClose() }
                    .padding(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    modifier = Modifier.size(16.dp),
                    tint = Color.Black
                )
                Text(
                    text = "Back",
                    color = Color.Black,
                    style = TextStyle(fontSize = 14.sp)
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            // Logo
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFE0E0E0))
                    .align(Alignment.End),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.School,
                    contentDescription = "Logo",
                    tint = Color(0xFF2F4431),
                    modifier = Modifier.size(32.dp)
                )
            }

            Spacer(modifier = Modifier.height(80.dp))

            // Menu Items
            MenuButton(
                text = "Change Password",
                onClick = onChangePassword
            )

            Spacer(modifier = Modifier.height(38.dp))

            MenuButton(
                text = "Switch Account",
                onClick = onSwitchAccount
            )

            Spacer(modifier = Modifier.height(38.dp))

            MenuButton(
                text = "Logout",
                onClick = onLogout
            )
        }
    }
}

@Composable
fun MenuButton(
    text: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(35.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(Color(0xffecf1fc))
            .border(
                BorderStroke(1.dp, Color(0xffb6b5e5)),
                RoundedCornerShape(20.dp)
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = Color(0xff3d31be),
            style = TextStyle(fontSize = 14.sp)
        )
    }
}

@Preview(widthDp = 414, heightDp = 896)
@Composable
private fun DashboardPreview() {
    Dashboard(Modifier)
}