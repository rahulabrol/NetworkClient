package com.rahul.networkclient.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * Created by abrol at 09/08/23.
 */
@Entity(tableName = "dog_image")
data class DogImage(
    @ColumnInfo(name = "Id")
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @ColumnInfo(name = "Url")
    @SerializedName("message") val imageUrl: String,
    @SerializedName("status") val status: String,
)

data class DogImageList(
    @SerializedName("message") val message: List<String> = emptyList(),
    @SerializedName("status") val status: String? = null,
)