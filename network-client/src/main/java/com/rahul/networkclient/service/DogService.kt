package com.rahul.networkclient.service

import com.rahul.networkclient.model.DogImage
import com.rahul.networkclient.model.DogImageList
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by abrol at 16/08/23.
 */
interface DogService {

    @GET("image/random")
    suspend fun getRandomDogImage(): DogImage

    @GET("image/random/{number}")
    suspend fun getRandomDogImages(@Path("number") number: Int): DogImageList
}