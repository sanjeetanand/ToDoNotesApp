package com.example.todonotes.onboarding

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.example.todonotes.R
import com.example.todonotes.utils.AppConstant
import com.example.todonotes.view.LoginActivity

class OnBoardingActivity : AppCompatActivity(), OnBoardingOneFragment.OnNextClick, OnBoardingTwoFragment.OnOptionClick {

    lateinit var viewPager:ViewPager
    lateinit var sp:SharedPreferences
    lateinit var editor:SharedPreferences.Editor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding)
        bindViews()
    }

    private fun bindViews(){
        viewPager = findViewById(R.id.viewPager)
        sp = getSharedPreferences(AppConstant.SP_NAME, Context.MODE_PRIVATE)
        val adapter = FragmentAdaptor(supportFragmentManager)
        viewPager.adapter = adapter
    }

    override fun onClick() {
        viewPager.currentItem = 1
    }

    override fun onOptionBack() {
        viewPager.currentItem = 0
    }

    override fun onOptionDone() {
        editor = sp.edit()
        editor.putBoolean(AppConstant.IS_ON_BOARD,true)
        editor.apply()
        val intent = Intent(this@OnBoardingActivity,LoginActivity::class.java)
        startActivity(intent)
    }
}
