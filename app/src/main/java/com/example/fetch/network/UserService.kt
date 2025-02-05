package com.example.fetch.network

import com.example.fetch.data.UserResponse
import retrofit2.http.GET
import retrofit2.Response

interface UserService {

    @GET("hiring.json")
    suspend fun getUsers(): Response<List<UserResponse>>

}