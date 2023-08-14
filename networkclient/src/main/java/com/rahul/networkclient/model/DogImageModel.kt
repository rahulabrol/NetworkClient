package com.rahul.networkclient.model

/**
 * Created by abrol at 09/08/23.
 */
data class DogImage(
    val message: String,
    val status: String,
)

data class DogImageList(
    val message: List<String>,
    val status: String,
)