package com.rahul.networkclient

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.rahul.networkclient.constant.Constants
import com.rahul.networkclient.model.ErrorResponseModel
import com.rahul.networkclient.network.ApiResponse
import com.rahul.networkclient.network.ApiService
import com.rahul.networkclient.network.FailureHandler
import com.rahul.networkclient.util.GSONUtil
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

/**
 *  Created by Rahul Abrol at 9/8/2023
 */
@Singleton
class NetworkClient @Inject constructor(
    @ApplicationContext val context: Context,
    private val apiService: ApiService,
) {
    private val headerMap = HashMap<String, String>()
        get() {
            field[Constants.CONTENT_TYPE] = "application/json"
            return field
        }

    suspend fun <T : Any> getData(
        url: String,
        reqBody: Any?,
        c: Class<T>,
        apiResponse: ApiResponse<T, Throwable>,
        header: Map<String, String> = headerMap
    ) {
        val call: Call<ResponseBody> = if (reqBody != null) {
            apiService.getData(url, header, pojoToMap(reqBody))
        } else {
            apiService.getData(url, header)
        }

        call.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                triggerSuccessResponse(url, reqBody, response, apiResponse, c)
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                Log.e(TAG, "-----onFailure--------")
                apiResponse.onFailure(t)
                failureHandler?.onFailure(url, reqBody, t)
            }
        })
    }

    //--------------Util methods -----------------------//
    private fun <T : Any> triggerSuccessResponse(
        url: String,
        reqBody: Any?,
        response: Response<ResponseBody?>,
        apiResponse: ApiResponse<T, Throwable>,
        c: Class<T>
    ) {
        try {
            if (response.isSuccessful && response.body() != null) {
                val strResponse = response.body()?.string() ?: ""
                apiResponse.onSuccess(GSONUtil.fromJson<T>(strResponse, c))
                checkEmptyRes(url, reqBody, strResponse, true)
            } else {
                failureResponseCheckIn(response, url, reqBody)
                apiResponse.onFailure(null)
            }
        } catch (e: Exception) {
            println(e.message)
        }
    }

    private fun checkEmptyRes(url: String, reqBody: Any?, json: String, logNonFatalError: Boolean) {
        try {
            val jsonObject = JSONObject(json)
            var success = true
            when {
                jsonObject.has("status") -> {
                    success = jsonObject.getString("status").equals("ok", ignoreCase = true)
                }
            }
            if (!success) {
                var msg = "Something went wrong"
                showToast(msg)
                if (logNonFatalError && failureHandler != null) {
                    failureHandler?.onFailure(url, reqBody, json)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun failureResponseCheckIn(response: Response<*>?, url: String, reqBody: Any?) {
        try {
            if (response != null) {
                Log.e(TAG, "--------ERROR CODE ----EXCEPTION----------" + response.code())
                var errorJson = ""
                if (failureHandler != null) {
                    val errorResponseModel = ErrorResponseModel()
                    if (response.errorBody() != null) {
                        errorJson = response.errorBody()?.string() ?: ""
                        errorResponseModel.errorResponse = errorJson
                        errorResponseModel.responseCode = response.code()
                    } else {
                        errorResponseModel.errorResponse = "Null Response Error Body"
                        errorResponseModel.responseCode = 0
                    }
                    failureHandler?.onFailure(url, reqBody, errorResponseModel)
                }
                if (response.code() == 500) {
                    showToast("Server Error . Please Retry.")
                } else if (response.code() == 401) {
                    showToast("Unauthorized Login Failed.")
                } else if (response.code() == 400) {
                    showToast("Request Failed..Try again")
                } else {
                    showToast("Server Error :- ")
                }
            } else {
                showToast("No Network ")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun showToast(msg: String) {
        try {
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    companion object {
        private const val TAG = "NetworkClient"
        var failureHandler: FailureHandler? = null
        fun pojoToMap(obj: Any): java.util.HashMap<String, String> {
            var map = java.util.HashMap<String, String>()
            try {
                val json: String = if (obj is JSONObject) {
                    obj.toString()
                } else {
                    val gson = GsonBuilder().create()
                    gson.toJson(obj)
                }
                map = Gson().fromJson<java.util.HashMap<String, String>>(
                    json, java.util.HashMap::class.java
                )
                map[Constants.API_KEY] = Constants.API_KEY_VALUE
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return map
        }
    }
}