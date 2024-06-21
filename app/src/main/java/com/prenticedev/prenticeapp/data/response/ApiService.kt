package com.prenticedev.prenticeapp.data.response

import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @POST("v1/compare/salary")
    suspend fun compareSalary(
        @Body compareRequest: CompareSalaryRequest
    ): CompareSalaryResponse
}

data class CompareSalaryRequest(
    val roles_compare_salary: List<String>,
    val companies_compare_salary: List<String>,
    val locations_compare_salary: List<String>
)