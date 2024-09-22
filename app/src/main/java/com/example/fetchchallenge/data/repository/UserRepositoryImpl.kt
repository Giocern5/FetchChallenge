package com.example.fetchchallenge.data.repository

import com.example.fetchchallenge.network.UserService
import javax.inject.Inject
import  com.example.fetchchallenge.data.Result
import com.example.fetchchallenge.data.User
import java.io.IOException

class UserRepositoryImpl  @Inject constructor(private val userService: UserService): UserRepository {

    override suspend fun getUsers(): Result<List<User>> {
        return try {
            val response = userService.getUsers()
            if (response.isSuccessful) {
                val info = response.body()
                if(info != null) {
                    Result.Success(info)
                } else {
                    Result.Error(Exception("Empty Response"))
                }

            } else {
                Result.Error(Exception("Error in UserRepositoryImpl"))
            }
        } catch (e: IOException) {
            Result.Error(e)
        }
    }

}