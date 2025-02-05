package com.example.fetch.data.repository

import com.example.fetch.data.User
import com.example.fetch.network.UserService
import  com.example.fetch.data.Result
import com.example.fetch.data.toUser
import javax.inject.Inject
import java.io.IOException

class UserRepositoryImpl  @Inject constructor(private val userService: UserService): UserRepository {

    override suspend fun getUsers(): Result<List<User>> {
        return try {
            val response = userService.getUsers()
            if (response.isSuccessful) {
                val info = response.body()
                if(info != null) {
                    val users = info.filter { !it.name.isNullOrBlank() }.map { it.toUser() }
                    Result.Success(users)
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