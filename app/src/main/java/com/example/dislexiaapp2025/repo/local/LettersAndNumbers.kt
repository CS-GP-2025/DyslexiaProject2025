package com.example.dislexiaapp2025.repo.local

import com.example.dislexiaapp2025.model.Letter
import com.example.dislexiaapp2025.R

object LettersAndNumbers {
    //val lettersInOrder:String="ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    //val numbersInOrder:String="012345678910"

    val letters = listOf(
        Letter(R.drawable.a_img,"A","letter"),
        Letter(R.drawable.b_img,"B","letter"),
        Letter(R.drawable.c_img,"C","letter"),
        Letter(R.drawable.d_img,"D","letter"),
        Letter(R.drawable.e_img,"E","letter"),
        Letter(R.drawable.f_img,"F","letter"),
        Letter(R.drawable.g_img,"G","letter"),
        Letter(R.drawable.h_img,"H","letter"),
    )
    val numbers = listOf(
        Letter(R.drawable.zero_img,"0","number"),
        Letter(R.drawable.one_img,"1","number"),
        Letter(R.drawable.two_img,"2","number"),
        Letter(R.drawable.three_img,"3","number"),
        Letter(R.drawable.four_img,"4","number"),
        Letter(R.drawable.five_img,"5","number"),
        Letter(R.drawable.six_img,"6","number"),
        Letter(R.drawable.seven_img,"7","number")
    )




}