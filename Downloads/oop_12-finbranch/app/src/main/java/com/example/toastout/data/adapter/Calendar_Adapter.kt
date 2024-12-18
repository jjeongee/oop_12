//package com.example.toastout.data.adapter
//
//import android.content.Context
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.BaseAdapter
//import android.widget.TextView
//import com.example.toastout.R
//
//class Calendar_Adapter(private val context:Context) : BaseAdapter() {
//    private val daysInMonth = 30 // 월간 일수 (예시용으로 30일로 설정)
//
//    override fun getCount(): Int {
//        return daysInMonth
//    }
//
//    override fun getItem(position: Int): Any? {
//        return null
//    }
//
//    override fun getItemId(position: Int): Long {
//        return position.toLong()
//    }
//
//    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
//        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_calendar_day, parent, false)
//        val dayTextView = view.findViewById<TextView>(R.id.dayText)
//        dayTextView.text = (position + 1).toString()
//
//        return view
//    }
//}