package com.binjesus.kfhr_mobile.models

import java.time.format.DateTimeFormatter
import java.util.Date

data class Employee(
    val id: Int,
    val name: String,
    val password: String,
    val email: String,
    val dob: String,
    val gender: String,
    val profilePicURL: String,
    val nfcIdNumber: Int,
    val positionName: String?,
    val departmentName: Department?,
    val pointsEarned: Int,
    val isAdmin: Boolean
)




