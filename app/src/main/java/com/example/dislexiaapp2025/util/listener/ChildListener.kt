package com.example.dislexiaapp2025.util.listener

import com.example.dislexiaapp2025.model.Child

interface ChildListener {
    fun onClick(child: Child)
    fun onDelete(position: Int, child: Child)
}