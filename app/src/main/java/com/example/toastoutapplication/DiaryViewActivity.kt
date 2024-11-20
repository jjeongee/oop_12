package com.example.toastoutapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.GridView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class DiaryViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diary_view)

        // 캘린더의 날짜를 터치했을 때 일기 작성 페이지로 이동
        val gridView = findViewById<GridView>(R.id.calendarGridView)
        gridView.adapter = CalendarAdapter(this)

        gridView.setOnItemClickListener { _, _, position, _ ->
            // 해당 날짜의 일기로 이동
            val intent = Intent(this, DiaryEntryActivity::class.java)
            intent.putExtra("selectedDate", position + 1) // 날짜 정보를 넘겨줌 (예: position + 1)
            startActivity(intent)
        }
    }
}


class CalendarAdapter(private val context: Context) : BaseAdapter() {

    private val daysInMonth = 30 // 월간 일수 (예시용으로 30일로 설정)

    override fun getCount(): Int {
        return daysInMonth
    }

    override fun getItem(position: Int): Any? {
        return null
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_calendar_day, parent, false)
        val dayTextView = view.findViewById<TextView>(R.id.dayText)
        dayTextView.text = (position + 1).toString()

        return view
    }
}
