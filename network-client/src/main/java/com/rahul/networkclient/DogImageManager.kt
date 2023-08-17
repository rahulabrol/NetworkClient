package com.rahul.networkclient

import com.rahul.networkclient.model.DogImage
import com.rahul.networkclient.model.DogImageList
import com.rahul.networkclient.util.Resource

/**
 * Created by abrol at 16/08/23.
 */
interface DogImageManager {
    suspend fun getImage(): Resource<DogImage>
    suspend fun getImages(number: Int): Resource<DogImageList>
    suspend fun getNextImage(): Resource<DogImage>
    suspend fun getPreviousImage(): List<DogImage>
}