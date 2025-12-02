package com.example.goq.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.goq.R
import kotlinx.coroutines.delay
import kotlin.random.Random

enum class QueueType {
    NONE, PAYMENT, ENROLLMENT
}

enum class QueueStatus {
    WAITING, NEAR, NEXT, MISSED
}

data class QueueTicket(
    val ticketNumber: Int,
    val queueType: QueueType,
    val studentsAhead: Int,
    val estimatedWaitTime: Int, // in minutes
    val status: QueueStatus
)

@Composable
fun Queue(modifier: Modifier = Modifier) {
    var currentTicket by remember { mutableStateOf<QueueTicket?>(null) }
    var showTicketDialog by remember { mutableStateOf(false) }
    var showAlertDialog by remember { mutableStateOf(false) }
    var alertMessage by remember { mutableStateOf("") }
    var alertTitle by remember { mutableStateOf("") }

    // Simulate real-time queue movement
    LaunchedEffect(currentTicket) {
        currentTicket?.let { ticket ->
            while (ticket.status != QueueStatus.MISSED) {
                delay(Random.nextLong(5000, 10000)) // Update every 5-10 seconds

                val newStudentsAhead = maxOf(0, ticket.studentsAhead - Random.nextInt(1, 3))
                val newWaitTime = maxOf(0, newStudentsAhead * Random.nextInt(3, 6))

                val newStatus = when {
                    newStudentsAhead == 0 -> QueueStatus.NEXT
                    newStudentsAhead <= 2 -> QueueStatus.NEAR
                    Random.nextInt(100) < 5 -> QueueStatus.MISSED // 5% chance to miss
                    else -> QueueStatus.WAITING
                }

                currentTicket = ticket.copy(
                    studentsAhead = newStudentsAhead,
                    estimatedWaitTime = newWaitTime,
                    status = newStatus
                )

                // Show alerts based on status changes
                when (newStatus) {
                    QueueStatus.NEAR -> {
                        if (ticket.status != QueueStatus.NEAR) {
                            alertTitle = "Almost Your Turn!"
                            alertMessage = "You're almost there! Only ${newStudentsAhead} students ahead of you."
                            showAlertDialog = true
                        }
                    }
                    QueueStatus.NEXT -> {
                        if (ticket.status != QueueStatus.NEXT) {
                            alertTitle = "You're Next!"
                            alertMessage = "Please proceed to the counter now. You're next in line!"
                            showAlertDialog = true
                        }
                    }
                    QueueStatus.MISSED -> {
                        alertTitle = "Queue Missed"
                        alertMessage = "You missed your turn. Would you like to rejoin the queue?"
                        showAlertDialog = true
                    }
                    else -> {}
                }

                if (newStatus == QueueStatus.MISSED) break
            }
        }
    }

    Box(
        modifier = modifier
            .requiredWidth(width = 414.dp)
            .requiredHeight(height = 896.dp)
            .background(color = Color.White)
    ) {
        // Header
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "logo",
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(x = 15.dp, y = 60.dp)
                .requiredWidth(width = 69.dp)
                .requiredHeight(height = 66.dp)
        )

        Text(
            text = "QUEUE",
            color = Color(0xff2f4431),
            textAlign = TextAlign.End,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(x = 170.dp, y = 77.dp)
                .requiredWidth(width = 83.dp)
                .requiredHeight(height = 29.dp)
        )

        Text(
            text = "Welcome, Aliza!",
            color = Color(0xff2f4431),
            textAlign = TextAlign.End,
            style = TextStyle(fontSize = 20.sp),
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(x = 15.dp, y = 149.dp)
                .requiredWidth(width = 154.dp)
                .requiredHeight(height = 25.dp)
        )

        if (currentTicket == null) {
            // Selection Screen
            Text(
                text = "Choose the process where you want to generate your ticket number for:",
                color = Color.Black,
                style = TextStyle(fontSize = 12.sp),
                modifier = Modifier
                    .align(alignment = Alignment.TopStart)
                    .offset(x = 15.dp, y = 215.dp)
                    .requiredWidth(width = 353.dp)
                    .requiredHeight(height = 32.dp)
            )

            // Payment Card
            Box(
                modifier = Modifier
                    .align(alignment = Alignment.TopStart)
                    .offset(x = 23.dp, y = 310.dp)
                    .requiredWidth(width = 176.dp)
                    .requiredHeight(height = 250.dp)
                    .clip(shape = RoundedCornerShape(10.dp))
                    .background(color = Color(0xffd1dba5))
                    .border(
                        border = BorderStroke(1.dp, Color(0xffbec4bf)),
                        shape = RoundedCornerShape(10.dp)
                    )
                    .shadow(elevation = 8.dp, shape = RoundedCornerShape(10.dp))
                    .clickable {
                        val ticketNum = Random.nextInt(1000, 9999)
                        val studentsAhead = Random.nextInt(5, 20)
                        currentTicket = QueueTicket(
                            ticketNumber = ticketNum,
                            queueType = QueueType.PAYMENT,
                            studentsAhead = studentsAhead,
                            estimatedWaitTime = studentsAhead * Random.nextInt(3, 6),
                            status = QueueStatus.WAITING
                        )
                        showTicketDialog = true
                    }
            )

            // Enrollment Card
            Box(
                modifier = Modifier
                    .align(alignment = Alignment.TopStart)
                    .offset(x = 215.dp, y = 310.dp)
                    .requiredWidth(width = 176.dp)
                    .requiredHeight(height = 250.dp)
                    .clip(shape = RoundedCornerShape(10.dp))
                    .background(color = Color(0xffd1dba5))
                    .border(
                        border = BorderStroke(1.dp, Color(0xffbec4bf)),
                        shape = RoundedCornerShape(10.dp)
                    )
                    .shadow(elevation = 8.dp, shape = RoundedCornerShape(10.dp))
                    .clickable {
                        val ticketNum = Random.nextInt(1000, 9999)
                        val studentsAhead = Random.nextInt(5, 20)
                        currentTicket = QueueTicket(
                            ticketNumber = ticketNum,
                            queueType = QueueType.ENROLLMENT,
                            studentsAhead = studentsAhead,
                            estimatedWaitTime = studentsAhead * Random.nextInt(3, 6),
                            status = QueueStatus.WAITING
                        )
                        showTicketDialog = true
                    }
            )

            Image(
                painter = painterResource(id = R.drawable.pay),
                contentDescription = "pay",
                modifier = Modifier
                    .align(alignment = Alignment.TopStart)
                    .offset(x = 39.dp, y = 327.dp)
                    .requiredSize(size = 144.dp)
            )

            Image(
                painter = painterResource(id = R.drawable.enroll),
                contentDescription = "enroll",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .align(alignment = Alignment.TopStart)
                    .offset(x = 232.dp, y = 345.dp)
                    .requiredWidth(width = 141.dp)
                    .requiredHeight(height = 107.dp)
            )

            // Labels
            Box(
                modifier = Modifier
                    .align(alignment = Alignment.TopStart)
                    .offset(x = 23.dp, y = 486.dp)
                    .requiredWidth(width = 176.dp)
                    .requiredHeight(height = 74.dp)
                    .clip(shape = RoundedCornerShape(bottomStart = 10.dp, bottomEnd = 10.dp))
                    .background(color = Color.White)
                    .border(
                        border = BorderStroke(1.dp, Color(0xffbec4bf)),
                        shape = RoundedCornerShape(bottomStart = 10.dp, bottomEnd = 10.dp)
                    )
            )

            Box(
                modifier = Modifier
                    .align(alignment = Alignment.TopStart)
                    .offset(x = 215.dp, y = 486.dp)
                    .requiredWidth(width = 176.dp)
                    .requiredHeight(height = 74.dp)
                    .clip(shape = RoundedCornerShape(bottomStart = 10.dp, bottomEnd = 10.dp))
                    .background(color = Color.White)
                    .border(
                        border = BorderStroke(1.dp, Color(0xffbec4bf)),
                        shape = RoundedCornerShape(bottomStart = 10.dp, bottomEnd = 10.dp)
                    )
            )

            Text(
                text = "In-person\nPayment",
                color = Color.Black,
                textAlign = TextAlign.Center,
                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold),
                modifier = Modifier
                    .align(alignment = Alignment.TopStart)
                    .offset(x = 23.dp, y = 505.dp)
                    .requiredWidth(width = 176.dp)
                    .requiredHeight(height = 40.dp)
            )

            Text(
                text = "In-person\nEnrollment",
                color = Color.Black,
                textAlign = TextAlign.Center,
                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold),
                modifier = Modifier
                    .align(alignment = Alignment.TopStart)
                    .offset(x = 215.dp, y = 505.dp)
                    .requiredWidth(width = 176.dp)
                    .requiredHeight(height = 40.dp)
            )
        } else {
            // Ticket Display Screen
            CurrentTicketDisplay(
                ticket = currentTicket!!,
                onViewTicket = { showTicketDialog = true }
            )
        }

        // Back Button
        Box(
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(x = 40.dp, y = 721.dp)
                .requiredWidth(width = 333.dp)
                .requiredHeight(height = 41.dp)
                .clip(shape = RoundedCornerShape(20.dp))
                .clickable { currentTicket = null }
        ) {
            Box(
                modifier = Modifier
                    .requiredWidth(width = 333.dp)
                    .requiredHeight(height = 41.dp)
                    .clip(shape = RoundedCornerShape(20.dp))
                    .background(color = Color(0xffd9d9d9).copy(alpha = 0.5f))
                    .border(
                        border = BorderStroke(1.dp, Color.Black.copy(alpha = 0.25f)),
                        shape = RoundedCornerShape(20.dp)
                    )
            )
            Text(
                text = "Back",
                color = Color.Black,
                textAlign = TextAlign.Center,
                style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Bold),
                modifier = Modifier
                    .align(alignment = Alignment.TopStart)
                    .offset(x = 0.dp, y = 11.dp)
                    .requiredWidth(width = 333.dp)
                    .requiredHeight(height = 19.dp)
            )
        }
    }

    // Ticket Dialog
    if (showTicketDialog && currentTicket != null) {
        TicketDialog(
            ticket = currentTicket!!,
            onDismiss = { showTicketDialog = false }
        )
    }

    // Alert Dialog
    if (showAlertDialog) {
        AlertDialog(
            onDismissRequest = { showAlertDialog = false },
            title = { Text(alertTitle, fontWeight = FontWeight.Bold) },
            text = { Text(alertMessage) },
            confirmButton = {
                if (currentTicket?.status == QueueStatus.MISSED) {
                    TextButton(onClick = {
                        // Requeue
                        val ticketNum = Random.nextInt(1000, 9999)
                        val studentsAhead = Random.nextInt(5, 20)
                        currentTicket = currentTicket?.copy(
                            ticketNumber = ticketNum,
                            studentsAhead = studentsAhead,
                            estimatedWaitTime = studentsAhead * Random.nextInt(3, 6),
                            status = QueueStatus.WAITING
                        )
                        showAlertDialog = false
                    }) {
                        Text("Rejoin Queue")
                    }
                } else {
                    TextButton(onClick = { showAlertDialog = false }) {
                        Text("OK")
                    }
                }
            },
            dismissButton = {
                if (currentTicket?.status == QueueStatus.MISSED) {
                    TextButton(onClick = {
                        currentTicket = null
                        showAlertDialog = false
                    }) {
                        Text("Cancel")
                    }
                }
            }
        )
    }
}

@Composable
fun CurrentTicketDisplay(ticket: QueueTicket, onViewTicket: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .offset(y = 220.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Status Indicator
        val statusColor = when (ticket.status) {
            QueueStatus.WAITING -> Color(0xFF4CAF50)
            QueueStatus.NEAR -> Color(0xFFFFA726)
            QueueStatus.NEXT -> Color(0xFFEF5350)
            QueueStatus.MISSED -> Color(0xFF757575)
        }

        val statusText = when (ticket.status) {
            QueueStatus.WAITING -> "In Queue"
            QueueStatus.NEAR -> "Almost Your Turn"
            QueueStatus.NEXT -> "You're Next!"
            QueueStatus.MISSED -> "Missed"
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp)
                .clip(RoundedCornerShape(15.dp))
                .background(statusColor)
                .padding(16.dp)
        ) {
            Text(
                text = statusText,
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }

        // Queue Info Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Ticket #${ticket.ticketNumber}",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2F4431)
                )

                Divider(modifier = Modifier.padding(vertical = 16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "${ticket.studentsAhead}",
                            fontSize = 36.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFFF6B6B)
                        )
                        Text("Students Ahead", fontSize = 12.sp, color = Color.Gray)
                    }

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "~${ticket.estimatedWaitTime}",
                            fontSize = 36.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF4ECDC4)
                        )
                        Text("Minutes", fontSize = 12.sp, color = Color.Gray)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = if (ticket.queueType == QueueType.PAYMENT)
                        "In-person Payment" else "In-person Enrollment",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    fontWeight = FontWeight.Medium
                )
            }
        }

        // View Full Ticket Button
        Button(
            onClick = onViewTicket,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2F4431)),
            shape = RoundedCornerShape(25.dp)
        ) {
            Text("View Full Ticket", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun TicketDialog(ticket: QueueTicket, onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
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
                    text = "Queue Ticket",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2F4431)
                )

                Divider(modifier = Modifier.padding(vertical = 16.dp))

                Text(
                    text = "Ticket Number",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                Text(
                    text = "#${ticket.ticketNumber}",
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2F4431)
                )

                Spacer(modifier = Modifier.height(20.dp))

                InfoRow("Queue Type:",
                    if (ticket.queueType == QueueType.PAYMENT) "Payment" else "Enrollment")
                InfoRow("Students Ahead:", "${ticket.studentsAhead}")
                InfoRow("Estimated Wait:", "~${ticket.estimatedWaitTime} minutes")
                InfoRow("Status:", when (ticket.status) {
                    QueueStatus.WAITING -> "Waiting"
                    QueueStatus.NEAR -> "Almost Your Turn"
                    QueueStatus.NEXT -> "You're Next"
                    QueueStatus.MISSED -> "Missed"
                })

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = onDismiss,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2F4431))
                ) {
                    Text("Close")
                }
            }
        }
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, fontSize = 14.sp, color = Color.Gray)
        Text(value, fontSize = 14.sp, fontWeight = FontWeight.Bold)
    }
}

@Preview(widthDp = 414, heightDp = 896)
@Composable
private fun QueuePreview() {
    Queue(Modifier)
}