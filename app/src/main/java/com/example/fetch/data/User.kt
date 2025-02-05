package com.example.fetch.data

data class UserResponse(
    val id: Int,
    val listId: Int,
    val name: String?
)

data class User(
    val id: Int,
    val listId: Int,
    val name: String
)

//ext for convert to UserData
fun UserResponse.toUser(): User {
    return User(
        id = id ?: 0, // generate a UUI or use something temp if not set
        listId = listId ?: 0, // same as above^^
        name = name ?: ""
    )
}
