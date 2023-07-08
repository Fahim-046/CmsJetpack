package com.example.cmsjetpack.screens.user.edit

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
class UserEditViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {

    private val _eventSuccess = MutableStateFlow<Boolean>(false)

    val evenSuccess: StateFlow<Boolean>
        get() = _eventSuccess

    private val _userData = MutableStateFlow<User>(User("", "", 0, "", ""))

    val userData: StateFlow<User>
        get() = _userData

    private fun isValid(
        name: String,
        email: String,
        gender: String,
        status: String
    ): Boolean {
        if (name.isBlank()) {
            return false
        }
        if (email.isBlank()) {
            return false
        }
        if (gender.isBlank()) {
            return false
        }
        if (status.isBlank()) {
            return false
        }
        return true
    }

    fun userDetails(
        id: Int
    ) = viewModelScope.launch {
        try {
            val user = repository.getUserDetails(id)

            if (user != null) _userData.value = user
        } catch (e: ApiException) {
            Timber.e("${e.message}")
        }
        _eventSuccess.value = false
    }

    fun updateUser(
        id: Int,
        name: String,
        email: String,
        gender: String,
        status: String
    ) = viewModelScope.launch {
        if (!isValid(name, email, gender, status)) {
            return@launch
        }
        try {
            val response = repository.updateUser(id, User(email, gender, id, name, status))

            if (response != null) {
                _eventSuccess.value = true
                if (_eventSuccess.value) {
                    userDetails(id)
                }
            }
        } catch (e: ApiException) {
            Timber.e("${e.message}")
        }
    }
}
