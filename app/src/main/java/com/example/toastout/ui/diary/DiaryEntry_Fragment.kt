package com.example.toastout.ui.diary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.toastout.R
import com.example.toastout.databinding.FragmentDiaryBinding
import com.example.toastout.databinding.FragmentDiaryEntryBinding

class DiaryEntry_Fragment: Fragment() {
    private lateinit var binding: FragmentDiaryEntryBinding
    private val diaryEntryViewModel: DiaryEntry_ViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_diary_entry, container, false)
        binding.diaryEntryViewModel = diaryEntryViewModel
        binding.lifecycleOwner = viewLifecycleOwner


        // Spinner 설정
        val emotions = arrayOf("행복", "슬픔", "화남")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, emotions)
        binding.emotionSpinner.adapter = adapter

        // 저장 버튼 클릭 이벤트
        // TODO : firebase database에 일기 내용 저장하는 로직 구현 @DiaryEntry_ViewModel
//        binding.saveButton.setOnClickListener {
//            diaryEntryViewModel.saveEntry()
//            Toast.makeText(requireContext(), "일기가 저장되었습니다!", Toast.LENGTH_SHORT).show()
//        }

        return binding.root
    }
}