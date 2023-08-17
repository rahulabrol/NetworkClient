package com.rahul.networkclient.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.rahul.networkclient.model.DogImage
import kotlinx.coroutines.flow.Flow

/**
 * Created by abrol at 16/08/23.
 */
@Dao
interface DogImageDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertToRoomDatabase(dogImage: DogImage): Long

    @Transaction
    @Query("SELECT * FROM dog_image")
    fun getPreviousDogImage(): Flow<List<DogImage>>
}