package com.example.fetchchallenge.features.userfeed

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fetchchallenge.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import  com.example.fetchchallenge.data.Result
import com.example.fetchchallenge.data.User


@HiltViewModel
class UserFeedViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {

    companion object {
        const val TAG = "UserFeedViewModel"
    }

    private val _userFeedData = MutableLiveData<Map<Int, List<User>>>()
    val userFeedData: LiveData<Map<Int, List<User>>> get() = _userFeedData
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    fun fetchUsers() {
        _isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = repository.getUsers()) {
                is Result.Success -> {
                    val groupedAndSortedUsers = groupAndSortUsers(result.data)
                    _userFeedData.postValue(groupedAndSortedUsers)
                    _isLoading.postValue( false)
                }
                is Result.Error -> {
                    Log.e(TAG, result.exception.message.toString())
                    _isLoading.postValue( false)
                }
            }
        }
    }

    private fun groupAndSortUsers(users: List<User>): Map<Int, List<User>> {
        return users
            .filter { user ->
                !user.name.isNullOrEmpty()
            }.groupBy { it.listId }
            .mapValues { entry ->
                entry.value.sortedBy { user -> user.id }
            }.toSortedMap()
    }

}