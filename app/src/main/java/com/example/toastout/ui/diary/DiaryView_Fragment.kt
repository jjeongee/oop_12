package com.example.toastout.ui.diary

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.toastout.R
import com.example.toastout.data.adapter.Calendar_Adapter
import com.example.toastout.databinding.FragmentDiaryViewBinding

class DiaryView_Fragment: Fragment() {
    private lateinit var binding: FragmentDiaryViewBinding
    private val diaryViewViewModel: DiaryView_ViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_diary_view, container, false)
        binding.diaryViewViewModel = diaryViewViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        // 캘린더 GridView 설정
        binding.calendarGridView.adapter = Calendar_Adapter(requireContext())
        binding.calendarGridView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            // 선택한 날짜에 대한 일기 작성 페이지로 이동
            val intent = Intent(requireContext(), DiaryEntry_Fragment::class.java)
            intent.putExtra("selectedDate", position + 1) // 날짜 정보 전달
            startActivity(intent)
        }

        return binding.root
    }
}