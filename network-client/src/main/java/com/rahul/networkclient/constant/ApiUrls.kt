package com.rahul.networkclient.constant

import android.util.Log

/**
 * Created by abrol at 15/08/23.
 */
object ApiUrls {
    var baseUrl: String? = null
    fun initialize(baseURL: String) {
        Log.i(TAG, "initialize: $baseURL")
        baseUrl = baseURL
    }

    private const val TAG = "ApiUrls"
}