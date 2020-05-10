package com.example.todonotes.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.todonotes.utils.AppConstant
import com.example.todonotes.R
import com.example.todonotes.utils.StoredSession

class DetailActivity : AppCompatActivity() {

    lateinit var textViewHeading: TextView
    lateinit var textViewDescription: TextView
    lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        bindView()
        setTitle()
    }

    private fun bindView(){
        textViewHeading = findViewById(R.id.textViewHeading)
        textViewDescription = findViewById(R.id.textViewDescription)
        imageView = findViewById(R.id.imageView)

        var intent:Intent = intent
        textViewHeading.text = intent.getStringExtra(AppConstant.HEADING)
        textViewDescription.text = intent.getStringExtra(AppConstant.DESCRIPTION)
        val imagePath = intent.getStringExtra(AppConstant.IMAGE_PATH)
        if(!imagePath.isNullOrBlank()){
            Glide.with(this).load(imagePath).into(imageView)
        }
    }

    private fun setTitle(){
        StoredSession.init(this)
        var title:String? = StoredSession.readString(AppConstant.FULL_NAME)
        supportActionBar?.subtitle = "Hi, $title"
    }
}
