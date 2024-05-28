import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.binjesus.kfhr_mobile.R
import com.binjesus.kfhr_mobile.models.Attendance
import com.binjesus.kfhr_mobile.models.Employee
import com.binjesus.kfhr_mobile.models.LateMinutesLeft
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(navController: NavHostController, employee: Employee, attendance: Attendance, lateMinutesLeft: LateMinutesLeft) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Image(
                        painter = rememberImagePainter(data = employee.profilePicURL),
                        contentDescription = "Profile Picture",
                        modifier = Modifier
                            .size(70.dp)
                            .background(Color.Gray, shape = CircleShape),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.width(12.dp))

                    Card(
                        shape = RoundedCornerShape(8.dp),
                        elevation = 4.dp,
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 6.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(8.dp),
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(
                                text = employee.name,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = employee.role,
                                fontSize = 14.sp,
                                color = Color.Gray
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(24.dp))

                    IconButton(onClick = { navController.navigate("Notifications") }) {
                        Icon(
                            painter = painterResource(id = R.drawable.bell), // Replace with actual icon resource
                            contentDescription = "Notifications",
                            tint = Color.Black,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Card(
                    shape = RoundedCornerShape(8.dp),
                    elevation = 4.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        val timeLeft = calculateTimeLeft(attendance.checkOutDateTime)
                        Text(
                            text = timeLeft,
                            fontSize = 36.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Time left till check out",
                            fontSize = 16.sp,
                            color = Color.Gray
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    CheckInOutCard(time = attendance.checkInDateTime, label = "Check In")
                    CheckInOutCard(time = attendance.checkOutDateTime, label = "Check Out")
                }

                Spacer(modifier = Modifier.height(16.dp))

                Card(
                    shape = RoundedCornerShape(8.dp),
                    elevation = 4.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.clock), // Replace with actual image resource
                            contentDescription = "Clock",
                            modifier = Modifier.size(40.dp) // Smaller size
                        )
                        Text(
                            text = "Late Minutes Left : ${lateMinutesLeft.time}",
                            fontSize = 16.sp,
                            color = Color.Gray
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            NFCNav(
                navController = navController,
                modifier = Modifier
                    .align(Alignment.BottomEnd) // Align to the bottom right
                    .padding(16.dp) // Add padding from the edges
            )
        }
}

@Composable
fun CheckInOutCard(time: Date, label: String) {
    val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    val formattedTime = timeFormat.format(time)
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp,
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = formattedTime,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = label,
                fontSize = 18.sp,
                color = Color.Gray
            )
        }
    }
}


fun calculateTimeLeft(checkOutDateTime: Date): String {
    val currentTime = Date()
    val diff = checkOutDateTime.time - currentTime.time
    val hours = (diff / (1000 * 60 * 60)).toInt()
    val minutes = ((diff / (1000 * 60)) % 60).toInt()
    val seconds = ((diff / 1000) % 60).toInt()
    return String.format("%02d:%02d:%02d hrs", hours, minutes, seconds)
}

@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen() {
    val navController = rememberNavController()
    val employee = Employee(
        id = 1,
        name = "Abdullah Bin Essa",
        role = "Forssah Tech Trainee",
        email = "abdullah@example.com",
        phone = "123456789",
        dob = Date(),
        gender = "Male",
        profilePicURL = "https://example.com/profile.jpg",
        nfcIdNumber = 123,
        positionId = 1,
        departmentId = 1,
        pointsEarned = 100
    )
    val attendance = Attendance(
        id = 1,
        employeeId = 1,
        checkInDateTime = Date(),
        checkOutDateTime = Date(Date().time + 3600000) // 1 hour later
    )
    val lateMinutesLeft = LateMinutesLeft(
        id = 1,
        employeeId = 1,
        time = 27,
        month = Date()
    )
    HomeScreen(navController, employee, attendance, lateMinutesLeft)
}
