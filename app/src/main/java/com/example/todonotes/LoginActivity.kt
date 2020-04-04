package com.example.todonotes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class LoginActivity : AppCompatActivity() {

    lateinit var fullName:EditText
    lateinit var userName:EditText
    lateinit var login:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        login = findViewById(R.id.buttonLogin)
        fullName = findViewById(R.id.textInputFullName)
        userName = findViewById(R.id.textInputUserName)

        login.setOnClickListener {

            var full_name = fullName.text.toString()
            var user_name = userName.text.toString()

            if(full_name?.length > 0 && user_name?.length > 0){
                var intent:Intent = Intent(this,MainActivity::class.java)
                intent.putExtra("fullName", full_name)
                intent.putExtra("userName", user_name)
                startActivity(intent)
            } else {
                var toast:Toast = Toast.makeText(applicationContext,"Enter Values",Toast.LENGTH_SHORT)
                toast.show()
            }

        }
    }
}
