package com.rahul.networkclient.network

/**
 * Created by abrol at 09/08/23.
 */
interface ApiResponse<T : Any, E : Throwable?> {
    fun onSuccess(response: T)
    fun onFailure(error: E?)
}