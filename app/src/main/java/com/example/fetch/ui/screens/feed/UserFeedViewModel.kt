package com.example.fetch.ui.screens.feed

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fetch.data.User
import com.example.fetch.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import  com.example.fetch.data.Result

@HiltViewModel
class UserFeedViewModel @Inject constructor(
    private val repo: UserRepository
) : ViewModel() {

    companion object {
        const val TAG = "UserFeedViewModel"
    }

    private val _originalUsers = MutableStateFlow<List<User>>(emptyList())
    private val _sortedUsers = MutableStateFlow<Map<Int, List<User>>>(emptyMap())
    val sortedUsers: StateFlow<Map<Int, List<User>>> get() = _sortedUsers

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading.asStateFlow()

    fun fetchUsers() {
        viewModelScope.launch {
            _isLoading.value = true
            when (val result = withContext(Dispatchers.IO) { repo.getUsers() }) {
                is Result.Success -> {
                    Log.e(TAG, result.data.toString())
                    _originalUsers.value = result.data
                    _sortedUsers.value = sortUsers(result.data)
                }
                is Result.Error -> {
                    Log.e(TAG, result.exception.message.toString())
                }
            }
            _isLoading.value = false
        }
    }

    private suspend fun sortUsers(users: List<User>): Map<Int, List<User>> =
        withContext(Dispatchers.Default) {
            users
                .groupBy { it.listId }
                .mapValues { entry -> entry.value.sortedBy { it.id } }
                .toSortedMap()
        }
}