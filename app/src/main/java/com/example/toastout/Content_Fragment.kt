package com.example.toastout

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.toastout.ui.caferecommend.JoyCafe_Fragment
import com.example.toastout.ui.caferecommend.AnxiousCafe_Fragment
import com.example.toastout.ui.caferecommend.SadCafe_Fragment
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.util.FusedLocationSource

class Content_Fragment : Fragment(R.layout.fragment_recommend), OnMapReadyCallback {

    private lateinit var naverMap: NaverMap
    private lateinit var locationSource: FusedLocationSource

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 버튼 클릭 시 다른 프래그먼트로 전환
        view.findViewById<View>(R.id.joyProfile).setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, JoyCafe_Fragment())
                .addToBackStack(null)
                .commit()
        }

        view.findViewById<View>(R.id.anxiousProfile).setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, AnxiousCafe_Fragment())
                .addToBackStack(null)
                .commit()
        }

        view.findViewById<View>(R.id.sadnessProfile).setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, SadCafe_Fragment())
                .addToBackStack(null)
                .commit()
        }

        // 네이버 지도 초기화
        initMap()
    }

    private fun initMap() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map_fragment_cafe) as MapFragment?
            ?: MapFragment.newInstance().also {
                childFragmentManager.beginTransaction()
                    .replace(R.id.map_fragment_cafe, it)
                    .commit()
            }
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(naverMap: NaverMap) {
        this.naverMap = naverMap

        // 위치 권한 확인
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                1000
            )
        } else {
            naverMap.locationTrackingMode = com.naver.maps.map.LocationTrackingMode.Follow
        }
    }
}