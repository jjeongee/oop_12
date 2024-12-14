package com.example.toastout

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.naver.maps.map.MapFragment
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.NaverMap
import com.naver.maps.map.NaverMapOptions
import com.naver.maps.map.util.FusedLocationSource
import com.naver.maps.map.LocationTrackingMode

class Content_Fragment : Fragment(R.layout.fragment_recommend), OnMapReadyCallback {

    private lateinit var naverMap: NaverMap
    private lateinit var locationSource: FusedLocationSource
    private val handler = Handler(Looper.getMainLooper())
    private val locationUpdateInterval: Long = 3000 // 3초마다 업데이트

    private val locationUpdater = object : Runnable {
        override fun run() {
            if (::naverMap.isInitialized) {
                naverMap.locationTrackingMode = LocationTrackingMode.Follow
            }
            handler.postDelayed(this, locationUpdateInterval)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 위치 소스 초기화
        locationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)

        // 첫 번째 지도 (카페 추천)
        val mapFragmentCafe = childFragmentManager.findFragmentById(R.id.map_fragment_cafe) as MapFragment?
            ?: MapFragment.newInstance(NaverMapOptions()).also {
                childFragmentManager.beginTransaction().add(R.id.map_fragment_cafe, it).commit()
            }
        mapFragmentCafe.getMapAsync(this)

        // 두 번째 지도 (산책로 추천)
        val mapFragmentWalk = childFragmentManager.findFragmentById(R.id.map_fragment_walk) as MapFragment?
            ?: MapFragment.newInstance(NaverMapOptions()).also {
                childFragmentManager.beginTransaction().add(R.id.map_fragment_walk, it).commit()
            }
        mapFragmentWalk.getMapAsync(this)

//        // Recommend_Fragment로 이동하는 버튼 클릭 이벤트 설정
//        view.findViewById<View>(R.id.btn_go_to_recommend).setOnClickListener {
//            parentFragmentManager.beginTransaction()
//                .replace(R.id.fragment_container, Recommend_Fragment())
//                .addToBackStack(null)
//                .commit()
//        }
    }

    override fun onMapReady(naverMap: NaverMap) {
        this.naverMap = naverMap
        naverMap.locationSource = locationSource

        // 위치 권한 확인 및 요청
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        } else {
            startLocationUpdates()
        }
    }

    private fun startLocationUpdates() {
        handler.post(locationUpdater)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            locationSource.onRequestPermissionsResult(requestCode, permissions, grantResults)
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startLocationUpdates()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacks(locationUpdater)
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000
    }
}