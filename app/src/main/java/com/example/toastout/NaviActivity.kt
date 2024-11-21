package com.example.toastout

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.toastout.ui.home.Home_Fragment
import com.example.toastout.ui.login.Login_Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class NaviActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navi)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        // 초기 화면 설정 (예: Home_Fragment)
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, Home_Fragment()).commit()

        // 하단 네비게이션 메뉴 클릭 이벤트 설정
        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> {
                    loadFragment(Home_Fragment())
                    true
                }
                R.id.nav_todo -> {
                    loadFragment(Todo_Fragment())
                    true
                }
                R.id.nav_diary -> {
                    loadFragment(Diary_Fragment())
                    true
                }
                R.id.nav_login -> {
                    loadFragment(Login_Fragment())
                    true
                }
                R.id.nav_content -> {
                    loadFragment(Content_Fragment())
                    true
                }
                else -> false
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}