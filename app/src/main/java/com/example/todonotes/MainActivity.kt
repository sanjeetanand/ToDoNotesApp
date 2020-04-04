package com.example.todonotes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var intent: Intent = intent
        var fullName:String = intent.getStringExtra("fullName")
        var userName:String = intent.getStringExtra("userName")

        supportActionBar?.title = fullName
    }
}
