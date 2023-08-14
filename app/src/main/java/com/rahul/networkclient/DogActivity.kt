package com.rahul.networkclient

import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint


/**
 * Activity to handle UI of the screen.
 *
 * Created by abrol at 09/08/23.
 */
@AndroidEntryPoint
class DogActivity : AppCompatActivity(), OnClickListener {
    private lateinit var imageView: ImageView
    private lateinit var editText: EditText
    private val viewModel: DogViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dog_activity)

        //views
        imageView = findViewById(R.id.imageView)
        editText = findViewById(R.id.editText)
        findViewById<Button>(R.id.btnPrevious).setOnClickListener(this)
        findViewById<Button>(R.id.btnNext).setOnClickListener(this)
        findViewById<Button>(R.id.btnSubmit).setOnClickListener(this)

        observeData()
    }

    private fun observeData() {
        viewModel.getNextImage().observe(this) {
            Glide.with(this).load(it).placeholder(android.R.drawable.picture_frame).into(imageView)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnPrevious -> {

            }

            R.id.btnNext -> {
                viewModel.doAction()
            }

            R.id.btnSubmit -> {}
        }
    }
}