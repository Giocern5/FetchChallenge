package com.example.fetchchallenge.data.repository

import  com.example.fetchchallenge.data.Result
import com.example.fetchchallenge.data.User

interface UserRepository {
    suspend fun getUsers(): Result<List<User>>
}