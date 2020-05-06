package com.example.todonotes.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.todonotes.utils.AppConstant
import com.example.todonotes.R

class LoginActivity : AppCompatActivity() {

    lateinit var fullNameEditText:EditText
    lateinit var userNameEditText:EditText
    lateinit var login:Button
    lateinit var sp:SharedPreferences
    lateinit var editor:SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        bindView()
        setUpSharedPref()

        login.setOnClickListener {
            var fullName = fullNameEditText.text.toString()
            var userName = userNameEditText.text.toString()
            if(fullName.isNotBlank()){
                if(userName.isNotBlank()) {
                    var intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    saveLoginStatus(fullName, userName)
                } else {
                    Toast.makeText(applicationContext,"Enter username",Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(applicationContext,"Enter full name",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun bindView(){
        login = findViewById(R.id.buttonLogin)
        fullNameEditText = findViewById(R.id.textInputFullName)
        userNameEditText = findViewById(R.id.textInputUserName)
    }

    private fun setUpSharedPref(){
        sp = getSharedPreferences(AppConstant.SP_NAME, Context.MODE_PRIVATE)
    }

    private fun saveLoginStatus(fullName:String, userName:String){
        editor = sp.edit()
        editor.putBoolean(AppConstant.IS_LOGGED_IN,true)
        editor.putString(AppConstant.FULL_NAME, fullName)
        editor.putString(AppConstant.USER_NAME, userName)
        editor.apply()
    }


}
