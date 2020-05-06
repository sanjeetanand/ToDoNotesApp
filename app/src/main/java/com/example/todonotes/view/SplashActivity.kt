package com.example.todonotes.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.todonotes.utils.AppConstant
import com.example.todonotes.R
import com.example.todonotes.onboarding.OnBoardingActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId

class SplashActivity : AppCompatActivity() {

    lateinit var sp:SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        setUpSharedPref()
        getFCMToken()

        checkLoginStatus()
    }

    private fun setUpSharedPref() {
        sp = getSharedPreferences(AppConstant.SP_NAME, Context.MODE_PRIVATE)
    }

    private fun getFCMToken() {
        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    return@OnCompleteListener
                }

                val token = task.result?.token
            })
    }

    private fun checkLoginStatus() {
        val isLoggedIn:Boolean = sp.getBoolean(AppConstant.IS_LOGGED_IN,false)
        val isOnBoard = sp.getBoolean(AppConstant.IS_ON_BOARD,false)
        if(isLoggedIn){
            var intent:Intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        } else {
            if(isOnBoard){
                val intent = Intent(this@SplashActivity, LoginActivity::class.java)
                startActivity(intent)
            } else {
                val intent = Intent(this@SplashActivity, OnBoardingActivity::class.java)
                startActivity(intent)
            }
        }
        finish()
    }

}
