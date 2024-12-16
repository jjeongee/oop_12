package com.example.toastout.utils

import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMapOptions
import com.naver.maps.map.overlay.Marker

fun Fragment.setupMapWithFirestore(mapFragmentId: Int, collectionName: String, documentName:String) {
    val mapFragment = childFragmentManager.findFragmentById(mapFragmentId) as MapFragment?
        ?: MapFragment.newInstance(NaverMapOptions()).also {
            childFragmentManager.beginTransaction().add(mapFragmentId, it).commit()
        }

    mapFragment.getMapAsync { naverMap ->
        // Firestore에서 데이터 로드 및 마커 추가
        val firestore = FirebaseFirestore.getInstance()

        //firestore에서 데이터 읽어오기
        firestore.collection(collectionName).document(documentName)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    document.data?.forEach { (key, value) ->
                        val coordinates = value.toString()
                            .removeSurrounding("[", "]")
                            .split(",")

                        if (coordinates.size == 2) {
                            val latitude = coordinates[0].trim().toDouble()
                            val longitude = coordinates[1].trim().toDouble()

                            // 지도에 마커 추가
                            val marker = Marker()
                            marker.position = LatLng(latitude, longitude)
                            marker.captionText = key
                            marker.map = naverMap
                        }
                    }
                } else {
                    Toast.makeText(context, "Firestore 문서가 비어 있습니다.", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(context, "Firestore 데이터 로드 실패: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }
}