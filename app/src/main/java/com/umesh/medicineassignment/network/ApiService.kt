package com.umesh.medicineassignment.network

import com.umesh.medicineassignment.model.ApiResponse
import com.umesh.medicineassignment.model.Medicine
import retrofit2.http.GET

interface ApiService {
    @GET("9fd894a0-a39b-4df4-a126-66e1c97860b5") // Replace with your mock API endpoint
    suspend fun getMedicines(): ApiResponse

}