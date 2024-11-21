package com.example.toastout.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.toastout.data.repository.Authentication_Repository
import com.google.firebase.auth.FirebaseUser

class Auth_ViewModel(private val repository: Authentication_Repository) : ViewModel() {
    private val _loginResult = MutableLiveData<Result<FirebaseUser?>>()
    val loginResult: LiveData<Result<FirebaseUser?>> get() = _loginResult

    private val _registerResult = MutableLiveData<Result<FirebaseUser?>>()
    val registerResult: LiveData<Result<FirebaseUser?>> get() = _registerResult

    fun loginAuth(email: String, password: String){
        repository.loginFunction(email,password).observeForever {
            _loginResult.value = it
        }
    }
    fun registerAuth(email: String, password: String){
        repository.registerFunction(email,password).observeForever {
            _registerResult.value = it
        }
    }

    fun logoutAuth() {
        repository.logoutFunction()
    }
}