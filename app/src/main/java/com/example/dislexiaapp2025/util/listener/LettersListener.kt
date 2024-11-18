package com.example.dislexiaapp2025.util.listener

import com.example.dislexiaapp2025.model.Letter

interface LettersListener {
    fun OnClick(letter: Letter, position: Int)
}