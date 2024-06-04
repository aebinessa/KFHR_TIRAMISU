package com.binjesus.kfhr_mobile.composables

import ApplyForLeaveScreen
import HomeScreen
import NFCIdScreen
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.binjesus.kfhr_mobile.models.Employee
import com.binjesus.kfhr_mobile.utils.Route
import com.binjesus.kfhr_mobile.viewmodel.KFHRViewModel

@Composable
fun ScreensNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = Route.HomeRoute,
    viewModel: KFHRViewModel = viewModel()// <-- Added ViewModel parameter here

) {
    Scaffold(
        modifier = modifier,
        //if you want to add the bell notification for all pages add it here

        bottomBar = {
            BottomNavBar(navController)
        }
    ) {
        NavHost(
            modifier = modifier.padding(it),
            navController = navController,
            startDestination = startDestination
        ) {
            composable(Route.NFCRoute) {
                val employee = Employee(
                    id = 8332246,
                    name = "Abdullah Essa Bin Essa",
                    password = "qwerty",
                    email = "abdullah@example.com",
                    dob = "",
                    gender = 1,
                    profilePicURL = "https://example.com/profile.jpg",
                    nfcIdNumber = 123,
                    positionId  = 1,
                    departmentId = 1,
                    pointsEarned = 100,
                    isAdmin = false
                )

                NFCIdScreen(navController = navController, employee = employee)
            }

            composable(Route.EmployeeDetailRoute) {
                EmployeeDetailScreen(navController, viewModel.selectedDirectoryEmployee!!)
            }

            composable(Route.MyCertificatesRoute) {
                MyCertificates(navController, viewModel)
            }

            composable(Route.SubmitCertificateRoute) {
                CertificateSubmissionScreen(navController, viewModel)
            }

            composable(Route.NotificationsRoute) {
                NotificationScreen(viewModel)
            }

            composable(Route.HomeRoute) {
                HomeScreen(navController, viewModel)
            }

            composable(Route.AttendanceRoute) {
                AttendanceList(navController, viewModel)
            }
            composable(Route.DirectoryRoute) {
                viewModel.getEmployees()
                EmployeeDirectoryScreen(navController, viewModel)
            }

            composable(Route.ApplyLeavesRoute) {
                ApplyForLeaveScreen(navController, viewModel)
            }
            composable(Route.MyLeavesRoute) {
                MyLeavesScreen(navController, viewModel)
            }


            composable(Route.RecommendedCertificatesRoute) {
                viewModel.getRecommendedCertificates()
                val context = LocalContext.current
                RecommendedCertificatesScreen(
                    navController, viewModel
                ) { certificate ->
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(certificate.organizationWebsite))
                    context.startActivity(intent)
                }
            }

        }
    }
}
