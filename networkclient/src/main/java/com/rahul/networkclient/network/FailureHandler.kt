package com.rahul.networkclient.network

/**
 * Created by abrol at 09/08/23.
 */
interface FailureHandler {
    fun <T> onFailure(url: String, reqBody: Any?, t: T)
}