package com.example.toastout.ui.main

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.toastout.R
import com.example.toastout.ui.login.Login_Fragment
import com.naver.maps.map.* // 네이버 지도 관련 클래스 임포트
import com.naver.maps.map.util.FusedLocationSource // 현재 위치 추적 도구를 위한 클래스

class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var naverMap: NaverMap

    // 위치 추적용
    private lateinit var locationSource: FusedLocationSource

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // 메인 화면

        // 1. 위치 권한 요청용
        locationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)

        // 2. 네이버 지도 설정
        val fm = supportFragmentManager
        val mapFragment = fm.findFragmentById(R.id.map_fragment) as MapFragment? // 기존 MapFragment 찾기
            ?: MapFragment.newInstance().also { // 없으면 새롭게 생성 -> 근데 안 씀
                fm.beginTransaction().add(R.id.map_fragment, it).commit()
            }
        mapFragment.getMapAsync(this)
    }

    // 3. 네이버 지도가 준비되면 호출되는 콜백 함수
    override fun onMapReady(naverMap: NaverMap) {
        this.naverMap = naverMap
        naverMap.locationSource = locationSource

        // 4. 현재 위치 추적
        naverMap.locationTrackingMode = LocationTrackingMode.Follow
    }

    // 5. 위치 권한 요청 결과 처리
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (locationSource.onRequestPermissionsResult(requestCode, permissions, grantResults)) {
            if (!locationSource.isActivated) {
                // 위치 권한 짤리면 안 해야지 뭐...
                naverMap.locationTrackingMode = LocationTrackingMode.None
            }
            return
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    companion object {
        // 위치 권한 요청에 사용할 코드
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000
    }
}