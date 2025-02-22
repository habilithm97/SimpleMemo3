package com.example.simplememo3.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.simplememo3.repository.PasswordRepository
import com.example.simplememo3.room.Password
import com.example.simplememo3.room.PasswordDatabase
import kotlinx.coroutines.launch

class PasswordViewModel(application: Application) : AndroidViewModel(application) {
    private val passwordRepository: PasswordRepository

    init {
        val passwordDao = PasswordDatabase.getInstance(application).passwordDao()
        passwordRepository = PasswordRepository(passwordDao)
    }

    fun insertPassword(password: Password) {
        viewModelScope.launch {
            passwordRepository.insertPassword(password)
        }
    }

    suspend fun getPassword(): Password? {
        return passwordRepository.getPassword()
    }
}