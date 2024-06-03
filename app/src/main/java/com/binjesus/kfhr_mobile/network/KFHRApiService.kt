package com.binjesus.kfhr_mobile.network

import com.binjesus.kfhr_mobile.models.Certificate
import com.binjesus.kfhr_mobile.models.Employee
import com.binjesus.kfhr_mobile.models.RecommendedCertificate
import com.binjesus.kfhr_mobile.models.TokenResponse
import com.binjesus.kfhr_mobile.models.requests.LeaveRequest
import com.binjesus.kfhr_mobile.models.requests.LoginRequest
import com.binjesus.kfhr_mobile.models.responses.LoginResponse
import com.binjesus.kfhr_mobile.utils.Endpoint
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface KFHRApiService {
    @POST(Endpoint.sigInEndpoint)
    suspend fun signIn(@Body request: LoginRequest): Response<TokenResponse>

    @GET(Endpoint.getEmployeesEndpoint)
    suspend fun getEmployees(@Header("Authorization") token: String): List<Employee>

    @GET(Endpoint.getRecommendedCertificatesEndpoint)
    suspend fun getRecommendedCertificates(@Header("Authorization") token: String): List<RecommendedCertificate>

    @POST("api/certificates")
    suspend fun submitCertificate(@Header("Authorization") token: String, @Body certificate: Certificate): Response<Void>
    @POST("api/admin/leavesResponse")
    suspend fun applyForLeave(@Body leaveRequest: LeaveRequest): Response<Void>
}