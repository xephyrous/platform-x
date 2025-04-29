package org.xephyrous.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import org.jetbrains.compose.resources.painterResource
import org.xephyrous.components.AlertBox
import org.xephyrous.components.clickableOutlineTextTitleless
import org.xephyrous.components.defaultScreen
import org.xephyrous.components.viewPanel
import org.xephyrous.data.ViewModel
import platformx.composeapp.generated.resources.Courses
import platformx.composeapp.generated.resources.Res

data class CourseData(
    val courseCode: String,
    val title: String,
    val description: String,
    val professor: String,
    val time: String
)

val courseList = listOf(
    CourseData("CS36000", "Software Engineering", "The course presents the common forms of the software life cycle, which are used throughout the commercial, industrial, institutional, and governmental communities when a single development effort is appropriate. " +
            "Topics include software development models, project planning, management, communication, analysis, design, testing, and implementation"
        , "Dr. Amal Khalifa", "MWF 1:30–2:45 PM"),

    CourseData("CS36400", "Introduction to Database Systems", "Theory and application of database systems for information organization and retrieval based on the relational model. Includes database models, query languages, data dependencies, normal forms, and database design. " +
            "Projects include use of commercial mainframe and microcomputer database software."
        , "Thomas Bolinger", "TR 9:00–10:15 AM"),

    CourseData("CS37400", "Computer Networks", "The design and implementation of data communications networks. Topics include network topologies; message, circuit and packet switching; broadcast, satellite and local area networks; routing;" +
            "the OSI model with emphasis on the network, transport, and session layers."
        , "Jonathan Rusert", "MW 3:00–4:15 PM"),

    CourseData("CS44500", "Computer Security", "A survey of the fundamentals of computer security. Topics include risks and vulnerabilities, policy formation, controls and protection methods, survey of malicious logic, database security, encryption, authentication, intrusion detection," +
            " network and system security issues, personnel and physical security issues, security design principles, and issues of law and privacy."
        , "Dr. Zesheng Chen", "F 10:00–12:50 PM")
)

@Composable
fun Courses(coroutineScope: CoroutineScope, viewModel: ViewModel, alertHandler: AlertBox) {
    var showDialog by remember { mutableStateOf(false) }
    var selectedCourse by remember { mutableStateOf<CourseData?>(null) }

    defaultScreen(
        coroutineScope, viewModel, title = "Courses", painter = painterResource(Res.drawable.Courses), alertHandler = alertHandler
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            courseList.forEach { course ->
                CourseSummaryCard(course) {
                    selectedCourse = course
                    showDialog = true
                }
            }
        }
    }

    selectedCourse?.let {
        viewPanel("COURSE OVERVIEW", DpSize(0.dp, 0.dp), DpSize(1200.dp, 600.dp), showDialog, closeHandler = { showDialog = false }) {
            Column(Modifier.fillMaxSize().padding(20.dp).verticalScroll(rememberScrollState())) {
                Text(
                    text = it.courseCode + ": " + it.title,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = it.description,
                    fontSize = 18.sp,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text("Professor: ${it.professor}", fontSize = 16.sp, color = Color.White)
                Text("Time: ${it.time}", fontSize = 16.sp, color = Color.White)
            }
        }
    }
}

@Composable
fun CourseSummaryCard(course: CourseData, onClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(10.dp),
        elevation = 8.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable(onClick = onClick)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(course.courseCode, fontSize = 16.sp, fontWeight = FontWeight.Medium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(course.title, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }
    }
}
