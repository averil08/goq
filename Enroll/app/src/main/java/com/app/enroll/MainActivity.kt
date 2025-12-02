package com.app.enroll

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView

class MainActivity : AppCompatActivity() {

    // Screens
    private lateinit var homeScroll: ScrollView
    private lateinit var enrollmentScroll: NestedScrollView
    private lateinit var queueScroll: NestedScrollView

    // Overlays
    private lateinit var scheduleOverlay: RelativeLayout
    private lateinit var menuOverlay: RelativeLayout

    // Home / navigation
    private lateinit var menuIcon: ImageView
    private lateinit var enrollCategory: LinearLayout
    private lateinit var enrollHatIcon: ImageView
    private lateinit var queueCategory: LinearLayout

    // Enrollment top bar
    private lateinit var backText: TextView

    // Course buttons
    private lateinit var course1Button: Button
    private lateinit var course2Button: Button
    private lateinit var course3Button: Button
    private lateinit var course4Button: Button

    // Selected schedule cards
    private lateinit var cc6SelectedCard: LinearLayout
    private lateinit var cc6SelectedDetails: TextView
    private lateinit var cc17SelectedCard: LinearLayout
    private lateinit var cc17SelectedDetails: TextView
    private lateinit var cit6SelectedCard: LinearLayout
    private lateinit var cit6SelectedDetails: TextView
    private lateinit var cit17SelectedCard: LinearLayout
    private lateinit var cit17SelectedDetails: TextView

    // Enrollment summary
    private lateinit var summaryPlaceholder: LinearLayout
    private lateinit var summaryCard: LinearLayout
    private lateinit var summaryExpandIcon: ImageView
    private lateinit var summaryCc6Details: TextView
    private lateinit var summaryCc17Details: TextView
    private lateinit var summaryCit6Details: TextView
    private lateinit var summaryCit17Details: TextView

    // Schedule dialog
    private lateinit var dialogTitle: TextView
    private lateinit var dialogSubtitle: TextView
    private lateinit var option1Layout: LinearLayout
    private lateinit var option2Layout: LinearLayout
    private lateinit var option3Layout: LinearLayout
    private lateinit var option4Layout: LinearLayout
    private lateinit var option1Section: TextView
    private lateinit var option1Details: TextView
    private lateinit var option2Section: TextView
    private lateinit var option2Details: TextView
    private lateinit var option3Section: TextView
    private lateinit var option3Details: TextView
    private lateinit var option4Section: TextView
    private lateinit var option4Details: TextView
    private lateinit var dialogCancelButton: Button

    // Menu overlay
    private lateinit var menuBackText: TextView
    private lateinit var btnChangePassword: Button
    private lateinit var btnSwitchAccount: Button
    private lateinit var btnLogout: Button

    // Queue summary page
    private lateinit var queueBackText: TextView
    private lateinit var generateTicketButton: Button
    private lateinit var queueCc6Schedule: TextView
    private lateinit var queueCc17Schedule: TextView
    private lateinit var queueCit6Schedule: TextView
    private lateinit var queueCit17Schedule: TextView

    // State
    private var currentCourseIndex: Int = 0 // 1 = CC6, 2 = CC17, 3 = CIT6, 4 = CIT17
    private var selectedCc6: String? = null
    private var selectedCc17: String? = null
    private var selectedCit6: String? = null
    private var selectedCit17: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Screens
        homeScroll = findViewById(R.id.homeScroll)
        enrollmentScroll = findViewById(R.id.enrollmentScroll)
        queueScroll = findViewById(R.id.queueScroll)

        // Overlays
        scheduleOverlay = findViewById(R.id.scheduleOverlay)
        menuOverlay = findViewById(R.id.menuOverlay)

        // Home
        menuIcon = findViewById(R.id.menuIcon)
        enrollCategory = findViewById(R.id.enrollCategory)
        enrollHatIcon = findViewById(R.id.enrollHatIcon)
        queueCategory = findViewById(R.id.queueCategory)

        // Enrollment top bar
        backText = findViewById(R.id.backText)

        // Course buttons
        course1Button = findViewById(R.id.course1Button)
        course2Button = findViewById(R.id.course2Button)
        course3Button = findViewById(R.id.course3Button)
        course4Button = findViewById(R.id.course4Button)

        // Selected cards
        cc6SelectedCard = findViewById(R.id.cc6SelectedCard)
        cc6SelectedDetails = findViewById(R.id.cc6SelectedDetails)
        cc17SelectedCard = findViewById(R.id.cc17SelectedCard)
        cc17SelectedDetails = findViewById(R.id.cc17SelectedDetails)
        cit6SelectedCard = findViewById(R.id.cit6SelectedCard)
        cit6SelectedDetails = findViewById(R.id.cit6SelectedDetails)
        cit17SelectedCard = findViewById(R.id.cit17SelectedCard)
        cit17SelectedDetails = findViewById(R.id.cit17SelectedDetails)

        // Summary
        summaryPlaceholder = findViewById(R.id.summaryPlaceholder)
        summaryCard = findViewById(R.id.summaryCard)
        summaryExpandIcon = findViewById(R.id.summaryExpandIcon)
        summaryCc6Details = findViewById(R.id.summaryCc6Details)
        summaryCc17Details = findViewById(R.id.summaryCc17Details)
        summaryCit6Details = findViewById(R.id.summaryCit6Details)
        summaryCit17Details = findViewById(R.id.summaryCit17Details)

        // Dialog views
        dialogTitle = findViewById(R.id.dialogTitle)
        dialogSubtitle = findViewById(R.id.dialogSubtitle)
        option1Layout = findViewById(R.id.option1Layout)
        option2Layout = findViewById(R.id.option2Layout)
        option3Layout = findViewById(R.id.option3Layout)
        option4Layout = findViewById(R.id.option4Layout)
        option1Section = findViewById(R.id.option1Section)
        option1Details = findViewById(R.id.option1Details)
        option2Section = findViewById(R.id.option2Section)
        option2Details = findViewById(R.id.option2Details)
        option3Section = findViewById(R.id.option3Section)
        option3Details = findViewById(R.id.option3Details)
        option4Section = findViewById(R.id.option4Section)
        option4Details = findViewById(R.id.option4Details)
        dialogCancelButton = findViewById(R.id.dialogCancelButton)

        // Menu overlay
        menuBackText = findViewById(R.id.menuBackText)
        btnChangePassword = findViewById(R.id.btnChangePassword)
        btnSwitchAccount = findViewById(R.id.btnSwitchAccount)
        btnLogout = findViewById(R.id.btnLogout)

        // Queue summary page
        queueBackText = findViewById(R.id.queueBackText)
        generateTicketButton = findViewById(R.id.generateTicketButton)
        queueCc6Schedule = findViewById(R.id.queueCc6Schedule)
        queueCc17Schedule = findViewById(R.id.queueCc17Schedule)
        queueCit6Schedule = findViewById(R.id.queueCit6Schedule)
        queueCit17Schedule = findViewById(R.id.queueCit17Schedule)

        // ---------- NAVIGATION ----------

        // Home → Enrollment
        val goToEnrollment: (View) -> Unit = {
            homeScroll.visibility = View.GONE
            queueScroll.visibility = View.GONE
            enrollmentScroll.visibility = View.VISIBLE
        }
        enrollCategory.setOnClickListener(goToEnrollment)
        enrollHatIcon.setOnClickListener(goToEnrollment)

        // Enrollment back → Home
        backText.setOnClickListener {
            enrollmentScroll.visibility = View.GONE
            queueScroll.visibility = View.GONE
            homeScroll.visibility = View.VISIBLE
        }

        // Menu icon → open menu overlay
        menuIcon.setOnClickListener {
            menuOverlay.visibility = View.VISIBLE
        }

        // Menu overlay back
        menuBackText.setOnClickListener {
            menuOverlay.visibility = View.GONE
        }

        btnChangePassword.setOnClickListener {
            Toast.makeText(this, "Change Password clicked", Toast.LENGTH_SHORT).show()
        }
        btnSwitchAccount.setOnClickListener {
            Toast.makeText(this, "Switch Account clicked", Toast.LENGTH_SHORT).show()
        }
        btnLogout.setOnClickListener {
            Toast.makeText(this, "Logout clicked", Toast.LENGTH_SHORT).show()
        }

        // ---------- COURSE BUTTONS / DIALOG ----------

        course1Button.setOnClickListener {
            currentCourseIndex = 1
            openScheduleDialog("CC6", "Emerging Technologies in IT")
        }

        course2Button.setOnClickListener {
            currentCourseIndex = 2
            openScheduleDialog("CC17", "Mobile App Development")
        }

        course3Button.setOnClickListener {
            currentCourseIndex = 3
            openScheduleDialog("CIT6", "Web Information")
        }

        course4Button.setOnClickListener {
            currentCourseIndex = 4
            openScheduleDialog("CIT17", "Capstone 1")
        }

        dialogCancelButton.setOnClickListener {
            scheduleOverlay.visibility = View.GONE
        }

        val optionClickListener = View.OnClickListener { view ->
            val (sectionView, detailsView) = when (view.id) {
                R.id.option1Layout -> option1Section to option1Details
                R.id.option2Layout -> option2Section to option2Details
                R.id.option3Layout -> option3Section to option3Details
                R.id.option4Layout -> option4Section to option4Details
                else -> return@OnClickListener
            }

            val scheduleText = "${sectionView.text} • ${detailsView.text}"
            applySelectedSchedule(scheduleText)
            scheduleOverlay.visibility = View.GONE
        }

        option1Layout.setOnClickListener(optionClickListener)
        option2Layout.setOnClickListener(optionClickListener)
        option3Layout.setOnClickListener(optionClickListener)
        option4Layout.setOnClickListener(optionClickListener)

        // ---------- SUMMARY & QUEUE SUMMARY ----------

        summaryExpandIcon.setOnClickListener {
            openQueueSummary()
        }

        queueBackText.setOnClickListener {
            queueScroll.visibility = View.GONE
            enrollmentScroll.visibility = View.VISIBLE
        }

        generateTicketButton.setOnClickListener {
            Toast.makeText(this, "Queue ticket generated.", Toast.LENGTH_SHORT).show()
        }

        // Start on home screen
        homeScroll.visibility = View.VISIBLE
        enrollmentScroll.visibility = View.GONE
        queueScroll.visibility = View.GONE
        scheduleOverlay.visibility = View.GONE
        menuOverlay.visibility = View.GONE
    }

    private fun openScheduleDialog(courseCode: String, courseTitle: String) {
        dialogTitle.text = "Select Schedule for $courseCode"
        dialogSubtitle.text = courseTitle

        // Options are already designed in XML (Section 3A, 3B, 3C, 3D)
        scheduleOverlay.visibility = View.VISIBLE
    }

    private fun applySelectedSchedule(scheduleText: String) {
        when (currentCourseIndex) {
            1 -> {
                selectedCc6 = scheduleText
                cc6SelectedCard.visibility = View.VISIBLE
                cc6SelectedDetails.text = scheduleText
                course1Button.text = "Change Schedule"
            }
            2 -> {
                selectedCc17 = scheduleText
                cc17SelectedCard.visibility = View.VISIBLE
                cc17SelectedDetails.text = scheduleText
                course2Button.text = "Change Schedule"
            }
            3 -> {
                selectedCit6 = scheduleText
                cit6SelectedCard.visibility = View.VISIBLE
                cit6SelectedDetails.text = scheduleText
                course3Button.text = "Change Schedule"
            }
            4 -> {
                selectedCit17 = scheduleText
                cit17SelectedCard.visibility = View.VISIBLE
                cit17SelectedDetails.text = scheduleText
                course4Button.text = "Change Schedule"
            }
        }

        updateSummary()
    }

    private fun updateSummary() {
        val allSelected =
            selectedCc6 != null && selectedCc17 != null && selectedCit6 != null && selectedCit17 != null

        if (allSelected) {
            summaryPlaceholder.visibility = View.GONE
            summaryCard.visibility = View.VISIBLE

            summaryCc6Details.text = selectedCc6
            summaryCc17Details.text = selectedCc17
            summaryCit6Details.text = selectedCit6
            summaryCit17Details.text = selectedCit17
        } else {
            summaryCard.visibility = View.GONE
            summaryPlaceholder.visibility = View.VISIBLE
        }
    }

    private fun openQueueSummary() {
        if (selectedCc6 == null || selectedCc17 == null || selectedCit6 == null || selectedCit17 == null) {
            Toast.makeText(
                this,
                "Please select schedules for all courses first.",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        // Copy selected schedules into queue summary
        queueCc6Schedule.text = selectedCc6
        queueCc17Schedule.text = selectedCc17
        queueCit6Schedule.text = selectedCit6
        queueCit17Schedule.text = selectedCit17

        // Show queue screen
        homeScroll.visibility = View.GONE
        enrollmentScroll.visibility = View.GONE
        queueScroll.visibility = View.VISIBLE
    }
}
