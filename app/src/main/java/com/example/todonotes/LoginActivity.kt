package com.example.todonotes

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class LoginActivity : AppCompatActivity() {

    lateinit var fullName:EditText
    lateinit var userName:EditText
    lateinit var login:Button
    lateinit var sp:SharedPreferences
    lateinit var editor:SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        addWidgets()

        login.setOnClickListener {

            var full_name = fullName.text.toString()
            var user_name = userName.text.toString()

            if(full_name.isNotBlank()){
                if(user_name.isNotBlank()) {

                    var intent: Intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)

                    saveLoginStatus(full_name, user_name)
                } else {
                    Toast.makeText(applicationContext,"Enter username",Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(applicationContext,"Enter full name",Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun saveLoginStatus(full_name:String, user_name:String){
        editor = sp.edit()
        editor.putBoolean(AppConstant.IS_LOGGED_IN,true)
        editor.putString(AppConstant.FULL_NAME, full_name)
        editor.putString(AppConstant.USER_NAME, user_name)
        editor.apply()
    }

    private fun addWidgets(){
        login = findViewById(R.id.buttonLogin)
        fullName = findViewById(R.id.textInputFullName)
        userName = findViewById(R.id.textInputUserName)

        sp = getSharedPreferences(AppConstant.SP_NAME, Context.MODE_PRIVATE)
    }
}
