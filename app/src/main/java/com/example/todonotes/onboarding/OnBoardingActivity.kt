package com.example.todonotes.onboarding

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.example.todonotes.R
import com.example.todonotes.utils.AppConstant
import com.example.todonotes.utils.StoredSession
import com.example.todonotes.view.LoginActivity

class OnBoardingActivity : AppCompatActivity(), OnBoardingOneFragment.OnNextClick, OnBoardingTwoFragment.OnOptionClick {

    lateinit var viewPager:ViewPager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding)
        bindViews()
        setUpSharedPref()
    }

    private fun bindViews(){
        viewPager = findViewById(R.id.viewPager)
        val adapter = FragmentAdaptor(supportFragmentManager)
        viewPager.adapter = adapter
    }

    private fun setUpSharedPref(){
        StoredSession.init(this)
    }

    override fun onClick() {
        viewPager.currentItem = 1
    }

    override fun onOptionBack() {
        viewPager.currentItem = 0
    }

    override fun onOptionDone() {
        StoredSession.write(AppConstant.IS_ON_BOARD,true)
        val intent = Intent(this@OnBoardingActivity,LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}
