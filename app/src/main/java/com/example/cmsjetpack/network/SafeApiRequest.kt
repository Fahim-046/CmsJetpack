package com.example.cmsjetpack.network

import android.content.Context
import java.net.HttpURLConnection
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response
import timber.log.Timber

object SafeApiRequest {
    suspend fun <T : Any?> apiRequest(context: Context, call: suspend () -> Response<T>): T? {
        try {
            val response = call.invoke()

            if (response.isSuccessful &&
                response.code() >= HttpURLConnection.HTTP_OK &&
                response.code() < HttpURLConnection.HTTP_MULT_CHOICE
            ) {
                return response.body()
            } else {
                val error = response.errorBody()?.string()

                val message = StringBuilder()

                error?.let {
                    try {
                        message.append(JSONObject(it).getString("message"))
                    } catch (e: JSONException) {
                    }
                    message.append("\n")
                }
                message.append("Error Code: ${response.code()}")

                Timber.e("SafeApiRequest: ApiException: $message")

                throw ApiException(message.toString())
            }
        } catch (e: Exception) {
            e.printStackTrace()

            throw ApiException(e.message ?: "Unknown Error!")
        }
    }
}
