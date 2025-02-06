package com.example.dislexiaapp2025.repo.local.database

import com.example.dislexiaapp2025.model.Child

interface LocalRepo {
    suspend fun insertChild(child: Child)
    suspend fun updateChild(child: Child)
    suspend fun getAllChildren(): List<Child>
    suspend fun getChildById(id: Int): Child?
    suspend fun deleteChild(child: Child)

}