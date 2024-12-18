package com.example.toastout.ui.caferecommend

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.toastout.R
import com.example.toastout.utils.setupMapWithFirestore

class AnxiousCafe_Fragment : Fragment(R.layout.fragment_cafe) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupMapWithFirestore(R.id.map_view,"recommendCafe","anxious")
    }
}
