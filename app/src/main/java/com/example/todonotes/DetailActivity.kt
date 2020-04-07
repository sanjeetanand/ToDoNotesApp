package com.example.todonotes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class DetailActivity : AppCompatActivity() {

    lateinit var textViewHeading: TextView
    lateinit var textViewDescription: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        textViewHeading = findViewById(R.id.textViewHeading)
        textViewDescription = findViewById(R.id.textViewDescription)

        var intent:Intent = getIntent()
        textViewHeading.text = intent.getStringExtra(AppConstant.HEADING)
        textViewDescription.text = intent.getStringExtra(AppConstant.DESCRIPTION)
    }
}
