package com.example.toastout.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.toastout.R

class Home_Fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // fragment_home 레이아웃을 Inflate
        return inflater.inflate(


            R.layout.fragment_home, container, false)
    }
}