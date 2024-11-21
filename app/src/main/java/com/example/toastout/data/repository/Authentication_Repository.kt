package com.example.toastout.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class Authentication_Repository {
    private val firebaseAuth : FirebaseAuth = FirebaseAuth.getInstance()

    fun loginFunction(email: String, password:String) : LiveData<Result<FirebaseUser?>>{
        val result = MutableLiveData<Result<FirebaseUser?>>()
        firebaseAuth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    //예외처리 : 저장된 데이터가 없을때
                    firebaseAuth.currentUser?.let {
                        result.value = Result.success(it)
                        Log.i("로그인","로그인 성공")

                    } ?:run{
                        result.value = Result.failure(task.exception ?: Exception("user가 null입니다"))
                    }
                } else {
                    result.value = Result.failure(task.exception ?: Exception("알수 없는에러"))
                    Log.e("로그인","로그인 실패!")
                }

            }
        return result
    }
    fun registerFunction(email:String, password: String): LiveData<Result<FirebaseUser?>>{
        val result = MutableLiveData<Result<FirebaseUser?>>()
        firebaseAuth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful){
                    if (task.isSuccessful) {
                        firebaseAuth.signOut() // 회원가입 후 자동 로그인 방지
                        result.value = Result.success(null) // 회원가입 성공을 알리기 위해 null 전달
                    }
                } else {
                    result.value = Result.failure(task.exception ?: Exception("알수 없는 에러"))
                }
            }
        return result
    }

    fun logoutFunction(){
        firebaseAuth.signOut()
    }
}