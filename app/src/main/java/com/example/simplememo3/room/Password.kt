package com.example.simplememo3.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Password(
    @PrimaryKey val id: Int = 1, // 항상 한 개의 비밀번호만 저장 (고정 id)
    val password: String
)