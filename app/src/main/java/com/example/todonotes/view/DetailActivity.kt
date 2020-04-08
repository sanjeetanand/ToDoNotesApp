package com.example.todonotes.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.todonotes.utils.AppConstant
import com.example.todonotes.R

class DetailActivity : AppCompatActivity() {

    lateinit var textViewHeading: TextView
    lateinit var textViewDescription: TextView
    lateinit var complete:Button
    lateinit var delete:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        setTitle()
        addWidgets()

        complete.setOnClickListener {
            finish()
        }

        delete.setOnClickListener {
            finish()
        }
    }

    private fun addWidgets(){
        textViewHeading = findViewById(R.id.textViewHeading)
        textViewDescription = findViewById(R.id.textViewDescription)
        complete = findViewById(R.id.btnCompleted)
        delete = findViewById(R.id.btnDelete)

        var intent:Intent = intent
        textViewHeading.text = intent.getStringExtra(AppConstant.HEADING)
        textViewDescription.text = intent.getStringExtra(AppConstant.DESCRIPTION)
    }

    private fun setTitle(){
        var sp:SharedPreferences = getSharedPreferences(AppConstant.SP_NAME, Context.MODE_PRIVATE)
        var title:String = sp.getString(AppConstant.FULL_NAME,"")
        supportActionBar?.subtitle = "Hi, $title"
    }
}
