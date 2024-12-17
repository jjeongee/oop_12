// Top-level build file where you can add configuration options common to all sub-projects/modules.


plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
}

buildscript {
    dependencies {
        // Google services 플러그인 추가
        classpath("com.google.gms:google-services:4.4.2") // 최신 버전 확인 필요
    }
}
