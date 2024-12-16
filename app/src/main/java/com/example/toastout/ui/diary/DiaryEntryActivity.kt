package com.example.toastout.ui.diary

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.widget.ArrayAdapter
import com.example.toastout.R


class DiaryEntryActivity : AppCompatActivity() {

    private lateinit var editTextDiary: EditText
    private lateinit var emotionSpinner: Spinner
    private lateinit var saveButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState) // super 호출을 가장 먼저 실행
        setContentView(R.layout.fragment_diary_entry) // 레이아웃 설정 후 뷰를 참조해야 합니다

        // View 요소 초기화
        val emotionSpinner: Spinner = findViewById(R.id.emotionSpinner)
        val editTextDiary: EditText = findViewById(R.id.editTextDiary)
        val saveButton: Button = findViewById(R.id.saveButton)

        // Spinner 설정
        val emotions = arrayOf("행복", "슬픔", "화남")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, emotions)
        emotionSpinner.adapter = adapter

        // 저장 버튼 클릭 이벤트
        saveButton.setOnClickListener {
            saveDiaryEntry(editTextDiary.text.toString(), emotionSpinner.selectedItem.toString())
        }
    }

    // 다이어리 저장 함수 예제
    private fun saveDiaryEntry(diaryText: String, emotion: String) {
        // Firebase에 저장하거나 다른 저장 로직을 처리하는 코드 작성
        Log.d("DiaryEntry", "내용: $diaryText, 기분: $emotion")
        Toast.makeText(this, "일기가 저장되었습니다!", Toast.LENGTH_SHORT).show()
    }

}
