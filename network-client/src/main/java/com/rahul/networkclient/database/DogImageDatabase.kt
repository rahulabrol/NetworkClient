package com.rahul.networkclient.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rahul.networkclient.model.DogImage

/**
 * Created by abrol at 16/08/23.
 */
@Database(entities = [DogImage::class], version = 1, exportSchema = false)
abstract class DogImageDatabase : RoomDatabase() {
    abstract fun dogImageDao(): DogImageDao
}