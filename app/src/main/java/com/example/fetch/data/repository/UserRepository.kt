package com.example.fetch.data.repository

import com.example.fetch.data.User
import  com.example.fetch.data.Result

interface UserRepository {
    suspend fun getUsers(): Result<List<User>>
}