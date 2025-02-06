package com.example.dislexiaapp2025.repo.local

import android.content.Context
import com.example.dislexiaapp2025.model.User

class SharedPref(context: Context) {
    private val sharedPref=context.getSharedPreferences("mode",Context.MODE_PRIVATE)
    private val profilePref=context.getSharedPreferences("profile",Context.MODE_PRIVATE)

    fun setImage(uri:String){
        profilePref.edit().putString("imageUri",uri).apply()
    }
    fun getImage():String?{
        return profilePref.getString("imageUri","")
    }

    fun setProfile(name:String,email:String,password:String,login:Boolean){
        profilePref.edit().putString("name",name).apply()
        profilePref.edit().putString("email",email).apply()
        profilePref.edit().putString("password",password).apply()
        profilePref.edit().putBoolean("login",login).apply()
    }
    fun getProfileDetails(): User {
        val name=profilePref.getString("name","")
        val email=profilePref.getString("email","")
        val password=profilePref.getString("password","")
        val login=profilePref.getBoolean("login",false)
        val imageUri=profilePref.getString("imageUri","")
        return User(name!!,login,imageUri!!,email!!,password!!)
    }

    fun setMode(mode:String){
        sharedPref.edit().putString("mode",mode).apply()
    }
    fun getMode():String?{
        return sharedPref.getString("mode","beginner")
    }

}