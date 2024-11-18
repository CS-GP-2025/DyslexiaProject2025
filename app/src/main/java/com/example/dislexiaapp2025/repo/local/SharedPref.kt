package com.example.dislexiaapp2025.repo.local

import android.content.Context

class SharedPref(context: Context) {
    private val sharedPref=context.getSharedPreferences("mode",Context.MODE_PRIVATE)

    fun setMode(mode:String){
        sharedPref.edit().putString("mode",mode).apply()
    }
    fun getMode():String?{
        return sharedPref.getString("mode","beginner")
    }
}