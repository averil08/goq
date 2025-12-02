package com.example.goq

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.goq.ui.theme.GoQTheme
import com.example.goq.ui.screens.*
import kotlinx.coroutines.delay

enum class AppScreen {
    OPENING, ONBOARDING, FIND_UNIVERSITY, LOGIN, DASHBOARD, ENROLLMENT, QUEUE_SUMMARY, QUEUE
}

data class CourseSchedule(
    val section: String,
    val details: String
)

data class Course(
    val code: String,
    val title: String,
    val schedules: List<CourseSchedule>
)

data class SelectedSchedules(
    val cc6: String? = null,
    val cc17: String? = null,
    val cit6: String? = null,
    val cit17: String? = null
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GoQTheme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {
    var currentScreen by remember { mutableStateOf(AppScreen.OPENING) }
    var onboardingPage by remember { mutableIntStateOf(0) }
    var selectedUniversity by remember { mutableStateOf("") }
    var showUnavailableDialog by remember { mutableStateOf(false) }
    var selectedSchedules by remember { mutableStateOf(SelectedSchedules()) }

    LaunchedEffect(currentScreen) {
        if (currentScreen == AppScreen.OPENING) {
            delay(3000)
            currentScreen = AppScreen.ONBOARDING
        }
    }

    LaunchedEffect(currentScreen, onboardingPage) {
        if (currentScreen == AppScreen.ONBOARDING) {
            delay(3000)
            onboardingPage = (onboardingPage + 1) % 3
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when (currentScreen) {
            AppScreen.OPENING -> {
                OpeningScreen()
            }

            AppScreen.ONBOARDING -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    when (onboardingPage) {
                        0 -> Onboarding1()
                        1 -> Onboarding2()
                        2 -> Onboarding3()
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 35.dp, vertical = 60.dp),
                        contentAlignment = Alignment.BottomCenter
                    ) {
                        Button(
                            onClick = { currentScreen = AppScreen.FIND_UNIVERSITY },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(58.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xff4363ec)
                            ),
                            shape = RoundedCornerShape(20.dp)
                        ) {
                            Text(
                                text = "Find my University",
                                fontSize = 17.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.White
                            )
                        }
                    }
                }
            }

            AppScreen.FIND_UNIVERSITY -> {
                FindUI(
                    onUniversitySelected = { university ->
                        selectedUniversity = university
                        when (university) {
                            "Fairview College" -> {
                                currentScreen = AppScreen.LOGIN
                            }
                            else -> {
                                showUnavailableDialog = true
                            }
                        }
                    }
                )
            }

            AppScreen.LOGIN -> {
                StudentLogin(
                    selectedUniversity = selectedUniversity,
                    onBackClick = { currentScreen = AppScreen.FIND_UNIVERSITY },
                    onLoginClick = { currentScreen = AppScreen.DASHBOARD }
                )
            }

            AppScreen.DASHBOARD -> {
                Dashboard(
                    onEnrollmentClick = { currentScreen = AppScreen.ENROLLMENT },
                    onQueueClick = { currentScreen = AppScreen.QUEUE }
                )
            }

            AppScreen.ENROLLMENT -> {
                EnrollmentScreen(
                    selectedSchedules = selectedSchedules,
                    onScheduleSelected = { updatedSchedules ->
                        selectedSchedules = updatedSchedules
                    },
                    onBackClick = { currentScreen = AppScreen.DASHBOARD },
                    onQueueSummaryClick = { currentScreen = AppScreen.QUEUE_SUMMARY }
                )
            }

            AppScreen.QUEUE_SUMMARY -> {
                QueueSummaryScreen(
                    selectedSchedules = selectedSchedules,
                    onBackClick = { currentScreen = AppScreen.ENROLLMENT },
                    onGenerateTicket = { currentScreen = AppScreen.QUEUE }
                )
            }

            AppScreen.QUEUE -> {
                Queue(
                    onBackClick = { currentScreen = AppScreen.DASHBOARD }
                )
            }
        }

        if (showUnavailableDialog) {
            Dialog(onDismissRequest = { showUnavailableDialog = false }) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Not Yet Available",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF2F4431)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "This university is not yet available. Please visit your school's ITSS office for more information.",
                            fontSize = 14.sp,
                            color = Color.Gray,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        Button(
                            onClick = { showUnavailableDialog = false },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xff4363ec)
                            ),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text("OK", fontSize = 16.sp, fontWeight = FontWeight.Medium)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun EnrollmentScreen(
    selectedSchedules: SelectedSchedules,
    onScheduleSelected: (SelectedSchedules) -> Unit,
    onBackClick: () -> Unit,
    onQueueSummaryClick: () -> Unit
) {
    val courses = remember {
        listOf(
            Course(
                "CC6",
                "Emerging Technologies in IT",
                listOf(
                    CourseSchedule("Section 3A", "Mon/Wed 9:00-10:30 AM"),
                    CourseSchedule("Section 3B", "Tue/Thu 1:00-2:30 PM"),
                    CourseSchedule("Section 3C", "Mon/Wed 2:00-3:30 PM"),
                    CourseSchedule("Section 3D", "Fri 9:00-12:00 PM")
                )
            ),
            Course(
                "CC17",
                "Mobile App Development",
                listOf(
                    CourseSchedule("Section 3A", "Tue/Thu 9:00-10:30 AM"),
                    CourseSchedule("Section 3B", "Mon/Wed 11:00-12:30 PM"),
                    CourseSchedule("Section 3C", "Tue/Thu 3:00-4:30 PM"),
                    CourseSchedule("Section 3D", "Sat 1:00-4:00 PM")
                )
            ),
            Course(
                "CIT6",
                "Web Information",
                listOf(
                    CourseSchedule("Section 3A", "Mon/Wed 1:00-2:30 PM"),
                    CourseSchedule("Section 3B", "Tue/Thu 10:30-12:00 PM"),
                    CourseSchedule("Section 3C", "Mon/Wed 3:30-5:00 PM"),
                    CourseSchedule("Section 3D", "Fri 1:00-4:00 PM")
                )
            ),
            Course(
                "CIT17",
                "Capstone 1",
                listOf(
                    CourseSchedule("Section 3A", "Tue/Thu 2:30-4:00 PM"),
                    CourseSchedule("Section 3B", "Mon/Wed 10:30-12:00 PM"),
                    CourseSchedule("Section 3C", "Tue/Thu 4:30-6:00 PM"),
                    CourseSchedule("Section 3D", "Sat 9:00-12:00 PM")
                )
            )
        )
    }

    var showScheduleDialog by remember { mutableStateOf(false) }
    var selectedCourse by remember { mutableStateOf<Course?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
    ) {
        // Top Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "← Back",
                fontSize = 16.sp,
                color = Color(0xff4363ec),
                modifier = Modifier.clickable { onBackClick() }
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "Enrollment",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2F4431)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // Course Cards
            courses.forEach { course ->
                val selected = when (course.code) {
                    "CC6" -> selectedSchedules.cc6
                    "CC17" -> selectedSchedules.cc17
                    "CIT6" -> selectedSchedules.cit6
                    "CIT17" -> selectedSchedules.cit17
                    else -> null
                }

                CourseCard(
                    course = course,
                    selectedSchedule = selected,
                    onClick = {
                        selectedCourse = course
                        showScheduleDialog = true
                    }
                )
                Spacer(modifier = Modifier.height(12.dp))
            }

            // Summary Section
            if (selectedSchedules.cc6 != null && selectedSchedules.cc17 != null &&
                selectedSchedules.cit6 != null && selectedSchedules.cit17 != null
            ) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Enrollment Summary",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF2F4431)
                            )
                            Text(
                                text = "→",
                                fontSize = 24.sp,
                                color = Color(0xff4363ec),
                                modifier = Modifier.clickable { onQueueSummaryClick() }
                            )
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        SummaryItem("CC6", selectedSchedules.cc6 ?: "")
                        SummaryItem("CC17", selectedSchedules.cc17 ?: "")
                        SummaryItem("CIT6", selectedSchedules.cit6 ?: "")
                        SummaryItem("CIT17", selectedSchedules.cit17 ?: "")
                    }
                }
            } else {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFE8E8E8)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "Select schedules for all courses to see summary",
                        fontSize = 14.sp,
                        color = Color.Gray,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp)
                    )
                }
            }
        }
    }

    if (showScheduleDialog && selectedCourse != null) {
        ScheduleDialog(
            course = selectedCourse!!,
            onDismiss = { showScheduleDialog = false },
            onScheduleSelected = { schedule ->
                val updatedSchedules = when (selectedCourse!!.code) {
                    "CC6" -> selectedSchedules.copy(cc6 = schedule)
                    "CC17" -> selectedSchedules.copy(cc17 = schedule)
                    "CIT6" -> selectedSchedules.copy(cit6 = schedule)
                    "CIT17" -> selectedSchedules.copy(cit17 = schedule)
                    else -> selectedSchedules
                }
                onScheduleSelected(updatedSchedules)
                showScheduleDialog = false
            }
        )
    }
}

@Composable
fun CourseCard(
    course: Course,
    selectedSchedule: String?,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = course.code,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2F4431)
            )
            Text(
                text = course.title,
                fontSize = 14.sp,
                color = Color.Gray
            )

            if (selectedSchedule != null) {
                Spacer(modifier = Modifier.height(12.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD)),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = selectedSchedule,
                        fontSize = 13.sp,
                        color = Color(0xFF1976D2),
                        modifier = Modifier.padding(12.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            Button(
                onClick = onClick,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xff4363ec)
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = if (selectedSchedule != null) "Change Schedule" else "Select Schedule",
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Composable
fun SummaryItem(code: String, schedule: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Text(
            text = "$code:",
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF2F4431),
            modifier = Modifier.width(60.dp)
        )
        Text(
            text = schedule,
            fontSize = 14.sp,
            color = Color.Gray
        )
    }
}

@Composable
fun ScheduleDialog(
    course: Course,
    onDismiss: () -> Unit,
    onScheduleSelected: (String) -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = "Select Schedule for ${course.code}",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2F4431)
                )
                Text(
                    text = course.title,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(16.dp))

                course.schedules.forEach { schedule ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onScheduleSelected("${schedule.section} • ${schedule.details}")
                            },
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF8F9FA)),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = schedule.section,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFF2F4431)
                            )
                            Text(
                                text = schedule.details,
                                fontSize = 13.sp,
                                color = Color.Gray
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }

                Button(
                    onClick = onDismiss,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFE0E0E0)
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Cancel", color = Color(0xFF2F4431))
                }
            }
        }
    }
}

@Composable
fun QueueSummaryScreen(
    selectedSchedules: SelectedSchedules,
    onBackClick: () -> Unit,
    onGenerateTicket: () -> Unit = {}
) {
    var showTicketDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
    ) {
        // Top Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "← Back",
                fontSize = 16.sp,
                color = Color(0xff4363ec),
                modifier = Modifier.clickable { onBackClick() }
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "Queue Summary",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2F4431)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = "Your Selected Schedules",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF2F4431)
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    QueueScheduleItem("CC6 - Emerging Technologies in IT", selectedSchedules.cc6 ?: "")
                    QueueScheduleItem("CC17 - Mobile App Development", selectedSchedules.cc17 ?: "")
                    QueueScheduleItem("CIT6 - Web Information", selectedSchedules.cit6 ?: "")
                    QueueScheduleItem("CIT17 - Capstone 1", selectedSchedules.cit17 ?: "")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { showTicketDialog = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xff4363ec)
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "Generate Queue Ticket",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }

    if (showTicketDialog) {
        Dialog(onDismissRequest = { showTicketDialog = false }) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "✓",
                        fontSize = 48.sp,
                        color = Color(0xFF4CAF50)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Queue Ticket Generated",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF2F4431)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Your queue ticket has been generated successfully. Please proceed to the enrollment office.",
                        fontSize = 14.sp,
                        color = Color.Gray,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Button(
                        onClick = {
                            showTicketDialog = false
                            onGenerateTicket() },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xff4363ec)
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("OK", fontSize = 16.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun QueueScheduleItem(courseTitle: String, schedule: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = courseTitle,
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF2F4431)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD)),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = schedule,
                fontSize = 13.sp,
                color = Color(0xFF1976D2),
                modifier = Modifier.padding(12.dp)
            )
        }
    }
}