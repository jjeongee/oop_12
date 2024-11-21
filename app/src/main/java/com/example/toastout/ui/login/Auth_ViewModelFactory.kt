package com.example.toastout.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.toastout.data.repository.Authentication_Repository

class Auth_ViewModelFactory(private val repository: Authentication_Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(Auth_ViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return Auth_ViewModel(repository) as T
        }
        throw IllegalArgumentException("Auth_ViewModel를 찾을수 없습니다")
        return super.create(modelClass)
    }
}