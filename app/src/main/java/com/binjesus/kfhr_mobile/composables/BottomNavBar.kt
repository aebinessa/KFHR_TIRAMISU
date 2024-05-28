package com.binjesus.kfhr_mobile.composables

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.binjesus.kfhr_mobile.R

@Composable
fun BottomNavBar(navController: NavController) {
    val items = listOf(
        BottomNavItem("HOME", R.drawable.home, "HomeScreen"),
        BottomNavItem("ATTENDANCE", R.drawable.security, "AttendanceScreen"),
        BottomNavItem("LEAVES", R.drawable.document, "LeavesScreen"),
        BottomNavItem("CERTS", R.drawable.onlinecertificate, "CertsScreen"),
        BottomNavItem("DIRECTORY", R.drawable.agenda, "DirectoryScreen")
    )

    BottomNavigation(
        backgroundColor = Color(0xFF4CAF50)
    ) {
        items.forEach { item ->
            BottomNavigationItem(
                selected = false,
                onClick = { navController.navigate(item.route) },
                icon = {
                    Icon(
                        painter = painterResource(id = item.iconRes),
                        contentDescription = item.label,
                        modifier = Modifier.size(20.dp)
                    )
                },
                label = {
                    Text(
                        text = item.label,
                        fontSize = 8.sp,
                        fontWeight = FontWeight.Normal
                    )
                },
                modifier = Modifier.width(70.dp)
            )
        }
    }
}

data class BottomNavItem(
    val label: String,
    val iconRes: Int,
    val route: String
)