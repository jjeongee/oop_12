package com.example.toastout.ui.main

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.toastout.R
import com.example.toastout.ui.login.Login_Fragment
import com.naver.maps.map.* // 네이버 지도 관련 클래스 임포트
import com.naver.maps.map.util.FusedLocationSource // 현재 위치 추적 도구를 위한 클래스

class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    // 네이버 지도 객체를 나중에 초기화할 변수
    private lateinit var naverMap: NaverMap

    // 위치 추적을 위해 사용되는 FusedLocationSource 객체
    private lateinit var locationSource: FusedLocationSource

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // 화면 레이아웃 설정

        // 1. 로그인 화면 실행
        if (savedInstanceState == null) { // 화면이 처음 실행되었을 때만 로그인 화면을 띄움
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, Login_Fragment()) // R.id.fragment_container에 Login_Fragment 추가
                .commit()
        }

        // 2. FusedLocationSource 초기화 (위치 권한 요청용)
        locationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)

        // 3. 네이버 지도 설정
        val fm = supportFragmentManager
        val mapFragment = fm.findFragmentById(R.id.map_fragment) as MapFragment? // 기존 MapFragment 찾기
            ?: MapFragment.newInstance().also { // 없으면 새로운 MapFragment 생성
                fm.beginTransaction().add(R.id.map_fragment, it).commit()
            }
        mapFragment.getMapAsync(this) // 네이버 지도 비동기 호출 (콜백으로 onMapReady 실행)
    }

    // 4. 네이버 지도가 준비되면 호출되는 콜백 함수
    override fun onMapReady(naverMap: NaverMap) {
        this.naverMap = naverMap // 네이버 지도 객체 초기화
        naverMap.locationSource = locationSource // 네이버 지도에 위치 추적 소스 설정

        // 5. 현재 위치 추적 모드 설정
        naverMap.locationTrackingMode = LocationTrackingMode.Follow

        // 6. 위치 변경 리스너 추가
        naverMap.addOnLocationChangeListener { location ->
            // 현재 위치가 바뀔 때마다 위도와 경도를 Toast 메시지로 보여줌
            Toast.makeText(this, "현재 위치: ${location.latitude}, ${location.longitude}", Toast.LENGTH_SHORT).show()
        }
    }

    // 7. 위치 권한 요청 결과 처리
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (locationSource.onRequestPermissionsResult(requestCode, permissions, grantResults)) {
            if (!locationSource.isActivated) {
                // 위치 권한이 거부된 경우 지도 위치 추적을 비활성화함
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