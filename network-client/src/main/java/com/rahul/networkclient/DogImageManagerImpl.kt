package com.rahul.networkclient

import com.rahul.networkclient.database.DogImageDao
import com.rahul.networkclient.model.DogImage
import com.rahul.networkclient.model.DogImageList
import com.rahul.networkclient.service.DogService
import com.rahul.networkclient.util.Resource
import kotlinx.coroutines.flow.first
import javax.inject.Inject

/**
 * Created by abrol at 16/08/23.
 */
internal class DogImageManagerImpl @Inject constructor(
    private val apiService: DogService,
    private val dogImageDao: DogImageDao,
) : DogImageManager {

    override suspend fun getImage(): Resource<DogImage> {
        val response = try {
            apiService.getRandomDogImage()
        } catch (e: Exception) {
            return Resource.Error("Error while fetching dog image is ${e.message}")
        }
        //insert here dog image
        dogImageDao.insertToRoomDatabase(response)
        return Resource.Success(response)
    }

    override suspend fun getImages(number: Int): Resource<DogImageList> {
        val response = try {
            apiService.getRandomDogImages(number)
        } catch (e: Exception) {
            return Resource.Error("Error while fetching dog image is ${e.message}")
        }
        return Resource.Success(response)
    }

    override suspend fun getNextImage(): Resource<DogImage> {
        val response = try {
            apiService.getRandomDogImage()
        } catch (e: Exception) {
            return Resource.Error("Error while fetching dog image is ${e.message}")
        }
        //insert here dog image
        dogImageDao.insertToRoomDatabase(response)
        return Resource.Success(response)
    }

    override suspend fun getPreviousImage(): List<DogImage> {
        return dogImageDao.getPreviousDogImage().first()
    }
}