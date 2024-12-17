package com.example.toastoutapplication

import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore



class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Firebase 초기화
        FirebaseApp.initializeApp(this)
    }
}

// DiaryEntryActivity - 다이어리 작성 화면
class DiaryEntryActivity : AppCompatActivity() {

    private lateinit var editTextDiary: EditText
    private lateinit var emotionSpinner: Spinner
    private lateinit var saveButton: Button
    private var selectedDate: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diary_entry)

        selectedDate = intent.getStringExtra("selectedDate") ?: "Unknown Date"
        setTitle("Diary for $selectedDate")

        initializeViews()
        setupEmotionSpinner()

        saveButton.setOnClickListener {
            saveDiary()
        }

    }

    // View 초기화
    private fun initializeViews() {
        editTextDiary = findViewById(R.id.editTextDiary)
        emotionSpinner = findViewById(R.id.emotionSpinner)
        saveButton = findViewById(R.id.saveButton)
    }

    // 감정 선택 Spinner 초기화
    private fun setupEmotionSpinner() {
        val emotions = arrayOf("불안", "슬픔", "기쁨")
        val spinnerAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            emotions
        )
        emotionSpinner.adapter = spinnerAdapter
    }

    // 저장 버튼 클릭 시 일기 데이터 저장
    private fun saveDiary() {
        val content = editTextDiary.text.toString()
        val emotion = emotionSpinner.selectedItem.toString()

        if (content.isBlank()) {
            Toast.makeText(this, "일기 내용을 입력해주세요.", Toast.LENGTH_SHORT).show()
            return
        }
        else {
            Toast.makeText(this, "저장되었습니다.", Toast.LENGTH_SHORT).show()
            navigateToCalendar()
        }

        val diaryData = mapOf(
            "date" to selectedDate,
            "content" to content,
            "emotion" to emotion
        )

        val db = FirebaseFirestore.getInstance()
        db.collection("diary_entries")
            .document(selectedDate ?: "Unknown Date")
            .set(diaryData)
            .addOnSuccessListener {
                Log.d("DiaryEntryActivity", "Firestore 저장 성공")
                Toast.makeText(this, "일기가 저장되었습니다.", Toast.LENGTH_SHORT).show()
                navigateToCalendar() // 캘린더로 이동
            }
            .addOnFailureListener {
                Log.e("DiaryEntryActivity", "Firestore 저장 실패: ${it.message}")
                Toast.makeText(this, "저장에 실패했습니다: ${it.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun navigateToCalendar() {
        Log.d("DiaryEntryActivity", "navigateToCalendar() 호출됨")
        val intent = Intent(this, CalendarActivity::class.java).apply {
            putExtra("updatedDate", selectedDate) // 선택된 날짜 전달
        }
        startActivity(intent)
        finish()
    }

}


class CalendarActivity : AppCompatActivity() {

    private lateinit var calendarGridLayout: GridLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diary_view)

        calendarGridLayout = findViewById(R.id.calendarGridLayout)
        addCalendarButtons()

    }

    private fun addCalendarButtons() {
        for (day in 1..31) {
            val button = Button(this).apply {
                text = "$day"
                textSize = 16f
                setBackgroundResource(R.drawable.button_background) // 배경 리소스 설정
                setPadding(8, 8, 8, 8)
            }

            val params = GridLayout.LayoutParams().apply {
                width = 0
                height = GridLayout.LayoutParams.WRAP_CONTENT
                columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
                rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
                setMargins(8, 8, 8, 8) // 버튼 사이 간격 설정
            }

            button.layoutParams = params

            // 버튼 클릭 이벤트 설정
            button.setOnClickListener {
                val selectedDate = "2024-12-${"%02d".format(day)}"
                val intent = Intent(this@CalendarActivity, DiaryEntryActivity::class.java).apply {
                    putExtra("selectedDate", selectedDate)
                }
                startActivity(intent)
            }

            calendarGridLayout.addView(button)
        }
    }
}