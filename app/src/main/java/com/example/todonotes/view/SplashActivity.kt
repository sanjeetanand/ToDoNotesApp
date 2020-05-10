package com.example.todonotes.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.todonotes.utils.AppConstant
import com.example.todonotes.R
import com.example.todonotes.onboarding.OnBoardingActivity
import com.example.todonotes.utils.StoredSession
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId

class SplashActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        setUpSharedPref()

        //getFCMToken()

        checkLoginStatus()
    }

    private fun setUpSharedPref() {
        StoredSession.init(this)
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
        val isLoggedIn: Boolean? = StoredSession.read(AppConstant.IS_LOGGED_IN)
        val isOnBoard:Boolean? = StoredSession.read(AppConstant.IS_ON_BOARD)
        if(isLoggedIn!!){
            var intent:Intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        } else {
            if(isOnBoard!!){
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
