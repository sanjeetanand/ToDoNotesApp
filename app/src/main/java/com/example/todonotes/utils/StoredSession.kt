package com.example.todonotes.utils

import android.content.Context
import android.content.SharedPreferences

object StoredSession {
    private var sp : SharedPreferences? = null

    fun init(context: Context){
        if(sp == null){
            sp = context.getSharedPreferences(AppConstant.SP_NAME, Context.MODE_PRIVATE)
        }
    }

    fun write(key:String, value:Boolean){
        val editor:SharedPreferences.Editor? = sp?.edit()
        editor?.putBoolean(key,value)
        editor?.apply()
    }

    fun read(key:String): Boolean? {
        return sp?.getBoolean(key,false)
    }

    fun write(key:String, value:String){
        val editor:SharedPreferences.Editor? = sp?.edit()
        editor?.putString(key,value)
        editor?.apply()
    }

    fun readString(key:String): String? {
        return sp?.getString(key,"")
    }
}