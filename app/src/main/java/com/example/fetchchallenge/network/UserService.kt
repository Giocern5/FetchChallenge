package com.example.fetchchallenge.network

import com.example.fetchchallenge.data.User
import retrofit2.Response
import retrofit2.http.GET

interface UserService {

    @GET("hiring.json")
    suspend fun getUsers(): Response<List<User>>
}