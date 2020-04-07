package com.example.todonotes

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class SplashActivity : AppCompatActivity() {

    lateinit var sp:SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        sp = getSharedPreferences(AppConstant.SP_NAME, Context.MODE_PRIVATE)

        checkLoginStatus()
    }

    private fun checkLoginStatus() {
        var isLoggedIn:Boolean = sp.getBoolean(AppConstant.IS_LOGGED_IN,false)

        if(isLoggedIn){

            var intent:Intent = Intent(this,MainActivity::class.java)
            startActivity(intent)

        } else {

            var intent:Intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)

        }
    }
}
