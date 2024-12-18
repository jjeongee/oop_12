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
import com.naver.maps.map.*
import com.naver.maps.map.util.FusedLocationSource

class Content_Fragment : Fragment(R.layout.fragment_recommend) {

    private lateinit var naverMapCafe: NaverMap
    private lateinit var naverMapWalk: NaverMap
    private lateinit var locationSource: FusedLocationSource

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000
    }

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

        // 위치 소스 초기화
        locationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)

        // 두 개의 지도 초기화
        initMap(R.id.map_fragment_cafe, "map_cafe")
        initMap(R.id.map_fragment_walk, "map_walk")
    }

    private fun initMap(frameLayoutId: Int, tag: String) {
        val mapFragment = childFragmentManager.findFragmentById(frameLayoutId) as MapFragment?
            ?: MapFragment.newInstance().also {
                childFragmentManager.beginTransaction()
                    .replace(frameLayoutId, it)
                    .commit()
            }

        // 지도 비동기 호출
        mapFragment.getMapAsync { naverMap ->
            when (tag) {
                "map_cafe" -> {
                    naverMapCafe = naverMap
                    setupMap(naverMapCafe)
                }
                "map_walk" -> {
                    naverMapWalk = naverMap
                    setupMap(naverMapWalk)
                }
            }
        }
    }

    private fun setupMap(naverMap: NaverMap) {
        naverMap.locationSource = locationSource

        // 위치 권한 확인
        if (ActivityCompat.checkSelfPermission(
                requireContext(), Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            naverMap.locationTrackingMode = LocationTrackingMode.Follow
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (::naverMapCafe.isInitialized) {
                    naverMapCafe.locationTrackingMode = LocationTrackingMode.Follow
                }
                if (::naverMapWalk.isInitialized) {
                    naverMapWalk.locationTrackingMode = LocationTrackingMode.Follow
                }
            } else {
                Log.d("Permission", "위치 권한 거부됨")
            }
        }
    }
}