package com.example.cmsjetpack.screens.user.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cmsjetpack.models.User
import com.example.cmsjetpack.network.ApiException
import com.example.cmsjetpack.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    private val _items = MutableStateFlow<List<User>>(emptyList())

    val items: StateFlow<List<User>>
        get() = _items

    fun showUserList() = viewModelScope.launch {
        try {
            val userResponse = userRepository.getUsers()

            _items.value = userResponse!!
        } catch (e: ApiException) {
            Timber.e("The error is $e")
        }
    }
}
