package com.rahul.networkclient.network

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.QueryMap
import retrofit2.http.Url

/**
 * Created by abrol at 09/08/23.
 */
interface ApiService {
    @GET
    suspend fun getData(
        @Url url: String,
        @HeaderMap headerMap: Map<String, String>?,
        @QueryMap queryMap: Map<String, String>?
    ): Call<ResponseBody>

    @GET
    suspend fun getData(
        @Url url: String,
        @HeaderMap headerMap: Map<String, String>?
    ): Call<ResponseBody>
}