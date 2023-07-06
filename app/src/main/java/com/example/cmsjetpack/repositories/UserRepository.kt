package com.example.cmsjetpack.repositories

import android.content.Context
import com.example.cmsjetpack.models.User
import com.example.cmsjetpack.network.SafeApiRequest
import com.example.cmsjetpack.network.api.UserInterface
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val api: UserInterface
) {
    suspend fun getUsers() = withContext(Dispatchers.IO) {
        SafeApiRequest.apiRequest(context) {
            api.getUsers()
        }
    }

    suspend fun getUserDetails(id: Int) = withContext(Dispatchers.IO) {
        SafeApiRequest.apiRequest(context) {
            api.getUserDetails(id)
        }
    }

    suspend fun createUser(user: User) = withContext(Dispatchers.IO) {
        SafeApiRequest.apiRequest(context) {
            api.createUser(user)
        }
    }

    suspend fun updateUser(id: Int, user: User) = withContext(Dispatchers.IO) {
        SafeApiRequest.apiRequest(context) {
            api.updateUser(id, user)
        }
    }

    suspend fun deleteUser(id: Int) = withContext(Dispatchers.IO) {
        SafeApiRequest.apiRequest(context) {
            api.deleteUser(id)
        }
    }
}