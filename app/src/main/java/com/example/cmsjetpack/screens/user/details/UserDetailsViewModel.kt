package com.example.cmsjetpack.screens.user.details

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
class UserDetailsViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {

    private val _userData =
        MutableStateFlow<User>(User("", "", 0, "", ""))

    val userData: StateFlow<User>
        get() = _userData

    fun getUserData(id: Int) = viewModelScope.launch {
        try {
            val response = repository.getUserDetails(id)

            _userData.value = response!!
        } catch (e: ApiException) {
            Timber.e("The error is $e")
        }
    }
}
