package com.example.cmsjetpack.network

import com.example.cmsjetpack.utils.Constants
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class ApiClient {
    companion object {
        private fun buildClient(): OkHttpClient {
            return OkHttpClient.Builder()
                .addInterceptor(
                    HttpLoggingInterceptor().apply {
                        this.level = HttpLoggingInterceptor.Level.BODY
                    },
                )
                .addInterceptor { chain ->
                    val request = chain.request().newBuilder()
                        .addHeader("Accept", "application/json")
                        .addHeader(
                            "Authorization",
                            "Bearer ${Constants.Token}",
                        )
                        .build()

                    chain.proceed(request)
                }
                .build()
        }

        /* Making instance of Retrofit */

        fun retrofitInstance(moshi: Moshi): Retrofit {
            return Retrofit.Builder()
                .client(buildClient())
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
        }
    }
}
