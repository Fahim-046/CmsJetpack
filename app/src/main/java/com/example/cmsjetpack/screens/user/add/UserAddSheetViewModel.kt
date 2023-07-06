package com.example.cmsjetpack.screens.user.add

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
class UserAddSheetViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {
    private val _eventSuccess = MutableStateFlow<Boolean>(false)

    val eventSuccess: StateFlow<Boolean>
        get() = _eventSuccess

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

    fun addUser(
        name: String,
        email: String,
        gender: String,
        status: String
    ) = viewModelScope.launch {
        if (!isValid(name, email, gender, status)) {
            return@launch
        }

        try {
            val newUser = repository.createUser(
                User(
                    email,
                    gender,
                    0,
                    name,
                    status
                )
            )
            if (newUser != null) _eventSuccess.value = true
        } catch (e: ApiException) {
            Timber.e(e.message ?: "Unknown error!")
        }
    }
}
