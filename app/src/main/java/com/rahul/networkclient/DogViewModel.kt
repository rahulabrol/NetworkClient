package com.rahul.networkclient

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


/**
 * Created by abrol at 09/08/23.
 */
@HiltViewModel
class DogViewModel @Inject constructor() : ViewModel() {

    private val nextImageLiveData: MutableLiveData<String> = MutableLiveData<String>()
    private val prevImageLiveData: MutableLiveData<String> = MutableLiveData<String>()

   internal fun getNextImage(): LiveData<String> {
        return nextImageLiveData
    }

    internal fun doAction() {
        // depending on the action, do necessary business logic calls and update the
        // userLiveData.
    }
}