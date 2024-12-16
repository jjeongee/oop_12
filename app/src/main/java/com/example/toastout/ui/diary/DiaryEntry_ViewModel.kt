package com.example.toastout.ui.diary

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DiaryEntry_ViewModel: ViewModel() {
    val date = MutableLiveData<String>()
    val day = MutableLiveData<String>()
    val emotion = MutableLiveData<String>()
    val diaryText = MutableLiveData<String>()

    //TODO : firebase 저장 로직 만들기
}