package com.binjesus.kfhr_mobile.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.binjesus.kfhr_mobile.R
import com.binjesus.kfhr_mobile.models.Employee
import java.util.Date

@Composable
fun EmployeeDirectoryScreen(employees: List<Employee>, onEmployeeClick: (Employee) -> Unit) {
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }

    Scaffold(
        bottomBar = { BottomNavigationBar() }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.White)
        ) {
            // Top Bar with profile, search, and notifications
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                    painter = rememberImagePainter("https://example.com/profile.jpg"),
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                )
                Text(
                    text = "Directory",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
                IconButton(onClick = { /* Handle search click */ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.search), // Replace with actual icon resource
                        contentDescription = "Search",
                        tint = Color.Black,
                        modifier = Modifier.size(24.dp)
                    )
                }
                IconButton(onClick = { /* Handle notifications click */ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.bell), // Replace with actual icon resource
                        contentDescription = "Notifications",
                        tint = Color.Black,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            // Search Bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Search") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(24.dp)
            )

            // Employee List
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                items(employees.filter {
                    it.name.contains(searchQuery.text, ignoreCase = true)
                }) { employee ->
                    EmployeeListItem(employee, onEmployeeClick)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@Composable
fun EmployeeListItem(employee: Employee, onEmployeeClick: (Employee) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onEmployeeClick(employee) }
    ) {
        Image(
            painter = rememberImagePainter(employee.profilePicURL),
            contentDescription = "Profile Picture",
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(Color.Gray),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                text = employee.name,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = employee.role,
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun BottomNavigationBar() {
    val items = listOf(
        BottomNavItem("HOME", R.drawable.home, true),
        BottomNavItem("ATTENDANCE", R.drawable.security, false),
        BottomNavItem("LEAVES", R.drawable.document, false),
        BottomNavItem("CERTS", R.drawable.onlinecertificate, false),
        BottomNavItem("DIRECTORY", R.drawable.agenda, false)
    )

    BottomNavigation(
        backgroundColor = Color(0xFF4CAF50)
    ) {
        items.forEach { item ->
            BottomNavigationItem(
                selected = item.selected,
                onClick = { /* Handle navigation click */ },
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
                        fontWeight = if (item.selected) FontWeight.Bold else FontWeight.Normal
                    )
                },
                modifier = Modifier.width(70.dp) // Adjust this width to reduce space
            )
        }
    }
}

data class BottomNavItem(
    val label: String,
    val iconRes: Int,
    val selected: Boolean
)



@Preview(showBackground = true)
@Composable
fun PreviewEmployeeDirectoryScreen() {
    val employees = listOf(
        Employee(1,  "Feras Alshadad", "Role", "email@example.com", "123456789", Date(), "Male", "https://example.com/profile1.jpg", 123, 1, 1, 100),
        Employee(2,  "Abdullah Bin Essa", "Role", "email@example.com", "123456789", Date(), "Male", "https://example.com/profile2.jpg", 124, 2, 2, 100),
        Employee(3,  "Othman Alkous", "Role", "email@example.com", "123456789", Date(), "Male", "https://example.com/profile3.jpg", 125, 3, 3, 100),
        // Add more employees as needed
    )
    EmployeeDirectoryScreen(employees) { employee ->
        // Handle employee click (navigate to detail screen)
    }
}
