package com.example.cmsjetpack.network.api

import com.example.cmsjetpack.models.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserInterface {
    @GET("v2/users")
    suspend fun getUsers(): Response<List<User>>

    @GET("v2/users/{id}")
    suspend fun getUserDetails(@Path("id") userId: Int): Response<User>

    @POST("v2/users")
    suspend fun createUser(@Body userItem: User): Response<User>

    @PUT("v2/users/{id}")
    suspend fun updateUser(
        @Path("id") userId: Int,
        @Body userItem: User,
    ): Response<User>

    @DELETE("v2/users/{id}")
    suspend fun deleteUser(@Path("id") userId: Int): Response<User>
}
