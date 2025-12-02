package com.example.goq.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp

// ---------- NAV STATE ----------

enum class ProfileSection {
    PROFILE,
    ADDRESS,
    CONTACT
}

/**
 * Call this from your Activity:
 *
 * setContent { ProfileRootScreen() }
 */
@Composable
fun ProfileRootScreen() {
    var currentSection by remember { mutableStateOf(ProfileSection.PROFILE) }

    ProfileScreen(
        section = currentSection,
        onTabSelected = { currentSection = it }
    )
}

// ---------- MAIN SHARED LAYOUT ----------

@Composable
fun ProfileScreen(
    section: ProfileSection,
    onTabSelected: (ProfileSection) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xffedf0f8))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // Top app bar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(125.dp)
                    .background(Color.White)
            ) {
                StateDefault(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 22.dp)
                )
                Text(
                    text = "User Information",
                    color = Color.Black,
                    lineHeight = 6.25.em,
                    style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.SemiBold),
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 16.dp)
                )
            }

            // Light background body
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xffedf0f8))
                    .padding(horizontal = 24.dp)
            ) {
                Spacer(Modifier.height(24.dp))

                // Profile image - using placeholder if drawable doesn't exist
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    // Fallback to Material Icon if image doesn't exist
                    Box(
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFE0E0E0)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Profile picture",
                            modifier = Modifier.size(80.dp),
                            tint = Color(0xFF9E9E9E)
                        )
                    }
                }

                Spacer(Modifier.height(12.dp))

                // Name & ID
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Aliza C. Manaoag",
                        color = Color.Black,
                        style = TextStyle(fontSize = 19.sp, fontWeight = FontWeight.Medium)
                    )
                    Text(
                        text = "349354-2025",
                        color = Color.Black,
                        style = TextStyle(fontSize = 15.sp)
                    )
                }

                Spacer(Modifier.height(24.dp))

                // Tabs (Profile / Address / Contact)
                TabRowProfile(
                    current = section,
                    onTabSelected = onTabSelected
                )

                Spacer(Modifier.height(24.dp))

                // Divider line
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Color.Black.copy(alpha = 0.14f))
                )

                Spacer(Modifier.height(16.dp))

                // Section title + subtitle
                when (section) {
                    ProfileSection.PROFILE -> {
                        Text(
                            text = "Personal Info",
                            color = Color.Black,
                            style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                        )
                        Spacer(Modifier.height(4.dp))
                        Text(
                            text = "Update your personal information in the ITSS office.",
                            color = Color(0xff827876),
                            style = TextStyle(fontSize = 15.sp, fontWeight = FontWeight.Light)
                        )
                    }

                    ProfileSection.ADDRESS -> {
                        Text(
                            text = "Addresses",
                            color = Color.Black,
                            style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                        )
                        Spacer(Modifier.height(4.dp))
                        Text(
                            text = "Update your personal information in the ITSS office.",
                            color = Color(0xff827876),
                            style = TextStyle(fontSize = 15.sp, fontWeight = FontWeight.Light)
                        )
                    }

                    ProfileSection.CONTACT -> {
                        Text(
                            text = "Contact Info",
                            color = Color.Black,
                            style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                        )
                        Spacer(Modifier.height(4.dp))
                        Text(
                            text = "Update your personal information in the ITSS office.",
                            color = Color(0xff827876),
                            style = TextStyle(fontSize = 15.sp, fontWeight = FontWeight.Light)
                        )
                    }
                }

                Spacer(Modifier.height(16.dp))

                // Main white card
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color.White)
                        .border(
                            BorderStroke(1.dp, Color(0xff827876).copy(alpha = 0.26f)),
                            RoundedCornerShape(20.dp)
                        )
                        .padding(vertical = 16.dp, horizontal = 18.dp)
                ) {
                    when (section) {
                        ProfileSection.PROFILE -> ProfileContent()
                        ProfileSection.ADDRESS -> AddressContent()
                        ProfileSection.CONTACT -> ContactContent()
                    }
                }

                Spacer(Modifier.height(32.dp))
            }
        }
    }
}

// ---------- TABS ----------

@Composable
fun TabRowProfile(
    current: ProfileSection,
    onTabSelected: (ProfileSection) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // PROFILE TAB
        ProfileTab(
            text = "Profile",
            isSelected = current == ProfileSection.PROFILE,
            selectedBg = Color(0xffeafff1),
            selectedBorder = Color(0xffaae7c2),
            selectedTextColor = Color(0xff37c169),
            onClick = { onTabSelected(ProfileSection.PROFILE) }
        )

        // ADDRESS TAB
        ProfileTab(
            text = "Address",
            isSelected = current == ProfileSection.ADDRESS,
            selectedBg = Color(0xffecf1fc),
            selectedBorder = Color(0xffb6b5e5),
            selectedTextColor = Color(0xff3d31be),
            onClick = { onTabSelected(ProfileSection.ADDRESS) }
        )

        // CONTACT TAB
        ProfileTab(
            text = "Contact",
            isSelected = current == ProfileSection.CONTACT,
            selectedBg = Color(0xfff9f9db),
            selectedBorder = Color(0xfff3df86),
            selectedTextColor = Color(0xfff1c853),
            onClick = { onTabSelected(ProfileSection.CONTACT) }
        )
    }
}

@Composable
private fun ProfileTab(
    text: String,
    isSelected: Boolean,
    selectedBg: Color,
    selectedBorder: Color,
    selectedTextColor: Color,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .width(112.dp)
            .height(34.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(if (isSelected) selectedBg else Color.White)
            .border(
                BorderStroke(
                    1.dp,
                    if (isSelected) selectedBorder else Color(0xffd5d5d5)
                ),
                RoundedCornerShape(20.dp)
            )
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = selectedTextColor,
            textDecoration = if (isSelected) TextDecoration.Underline else TextDecoration.None,
            style = TextStyle(fontSize = 15.sp)
        )
    }
}

// ---------- CONTENT: PROFILE SECTION ----------

@Composable
fun ProfileContent() {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {

        // Full name field
        FieldLabel(text = "Full Name")
        RoundedFieldWithIcon(
            topHint = "first   middle   last name",
            value = "Aliza Cortez Manaoag",
            icon = Icons.Default.Person
        )

        // Birthday
        FieldLabel(text = "Birthday")
        SimpleRoundedField(
            value = "November 1, 2004",
            trailingIcon = Icons.Default.DateRange
        )

        // Gender
        FieldLabel(text = "Gender")
        SimpleRoundedField(
            value = "Female",
            trailingIcon = Icons.Default.Person
        )

        // Nationality
        FieldLabel(text = "Nationality")
        SimpleRoundedTextButtonField(
            value = "Filipino Citizen",
            trailingIcon = Icons.Default.Place,
            onClick = { /* TODO: open nationality picker */ }
        )
    }
}

// ---------- CONTENT: ADDRESS SECTION ----------

@Composable
fun AddressContent() {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {

        FieldLabel(text = "Student Email")
        SimpleRoundedField(
            value = "acm6732@students.fc.edu.ph",
            trailingIcon = Icons.Default.Email
        )

        FieldLabel(text = "Personal Email")
        SimpleRoundedField(
            value = "alizamanaoag@gmail.com",
            trailingIcon = Icons.Default.Email
        )

        FieldLabel(text = "Home Address")
        SimpleRoundedField(
            value = "#411 Olongapo City",
            trailingIcon = Icons.Default.Home
        )

        FieldLabel(text = "Current Address")
        SimpleRoundedField(
            value = "#75 Fairview, Baguio City",
            trailingIcon = Icons.Default.LocationOn
        )
    }
}

// ---------- CONTENT: CONTACT SECTION ----------

@Composable
fun ContactContent() {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {

        FieldLabel(text = "Emergency Contact")
        SimpleRoundedTextButtonField(
            value = "Arjo Aray Co",
            trailingIcon = Icons.Default.Person,
            onClick = { /* TODO: emergency contact picker */ }
        )

        FieldLabel(text = "Phone No.")
        SimpleRoundedTextButtonField(
            value = "09876543214",
            trailingIcon = Icons.Default.Phone,
            onClick = { /* TODO: call or edit */ }
        )

        FieldLabel(text = "Home Address")
        SimpleRoundedField(
            value = "#75 Fairview, Baguio City",
            trailingIcon = Icons.Default.Home
        )

        FieldLabel(text = "Student Phone No.")
        SimpleRoundedField(
            value = "0912734681238",
            trailingIcon = Icons.Default.Phone
        )
    }
}

// ---------- SMALL REUSABLE PARTS ----------

@Composable
fun FieldLabel(text: String) {
    Text(
        text = text,
        color = Color.Black.copy(alpha = 0.67f),
        style = TextStyle(
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        ),
        modifier = Modifier.padding(bottom = 4.dp, top = 4.dp)
    )
}

@Composable
fun RoundedFieldWithIcon(
    topHint: String,
    value: String,
    icon: ImageVector
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .clip(RoundedCornerShape(30.dp))
            .border(
                BorderStroke(1.dp, Color(0xff827876).copy(alpha = 0.26f)),
                RoundedCornerShape(30.dp)
            )
            .padding(horizontal = 16.dp, vertical = 4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color.Black.copy(alpha = 0.67f),
                modifier = Modifier.size(18.dp)
            )
            Spacer(Modifier.width(8.dp))
            Column {
                Text(
                    text = topHint,
                    color = Color(0xff827876).copy(alpha = 0.75f),
                    style = TextStyle(fontSize = 9.sp)
                )
                Text(
                    text = value,
                    color = Color(0xff827876),
                    style = TextStyle(fontSize = 15.sp)
                )
            }
        }
    }
}

@Composable
fun SimpleRoundedField(
    value: String,
    trailingIcon: ImageVector? = null
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .clip(RoundedCornerShape(30.dp))
            .border(
                BorderStroke(1.dp, Color(0xff827876).copy(alpha = 0.26f)),
                RoundedCornerShape(30.dp)
            )
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = value,
                color = Color(0xff827876),
                style = TextStyle(fontSize = 15.sp),
                modifier = Modifier.weight(1f)
            )
            if (trailingIcon != null) {
                Icon(
                    imageVector = trailingIcon,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp),
                    tint = Color.Black.copy(alpha = 0.67f)
                )
            }
        }
    }
}

@Composable
fun SimpleRoundedTextButtonField(
    value: String,
    trailingIcon: ImageVector? = null,
    onClick: () -> Unit
) {
    TextButton(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        contentPadding = PaddingValues(0.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
    ) {
        SimpleRoundedField(value = value, trailingIcon = trailingIcon)
    }
}

// ---------- BACK BUTTON ----------

@Composable
fun StateDefault(modifier: Modifier = Modifier) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .requiredWidth(85.dp)
            .requiredHeight(20.dp)
            .clip(RoundedCornerShape(8.dp))
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Arrow left",
            modifier = Modifier.size(16.dp),
            tint = Color.Black
        )
        Text(
            text = "Back",
            color = Color.Black,
            lineHeight = 6.25.em,
            style = TextStyle(fontSize = 14.sp)
        )
    }
}

@Preview(widthDp = 414, heightDp = 896, showBackground = true)
@Composable
private fun ProfileRootPreview() {
    ProfileRootScreen()
}