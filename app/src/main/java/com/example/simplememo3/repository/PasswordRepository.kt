package com.example.simplememo3.repository

import com.example.simplememo3.room.Password
import com.example.simplememo3.room.PasswordDao

class PasswordRepository(private val passwordDao: PasswordDao) {
    suspend fun insertPassword(password: Password) {
        passwordDao.insertPassword(password)
    }
    suspend fun getPassword(): Password? {
        return passwordDao.getPassword()
    }
}