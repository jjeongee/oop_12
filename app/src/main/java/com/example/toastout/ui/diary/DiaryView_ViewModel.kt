package com.example.toastout.ui.diary

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DiaryView_ViewModel : ViewModel() {
    val diaryEntries = MutableLiveData<List<DiaryEntry>>() // 일기 목록
    class DiaryEntry(
        val date: String,
        val day: String,
        val emotion: String,
        val text: String
    )
}