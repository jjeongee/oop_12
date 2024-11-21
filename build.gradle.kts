// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
<<<<<<< HEAD
}
=======
    alias(libs.plugins.google.gms.google.services) apply false
}

// 프로젝트 루트 build.gradle.kts 파일

allprojects {
    repositories {
        google()
        mavenCentral()
        maven("https://repository.map.naver.com/archive/maven") // 네이버 지도 SDK 저장소 추가
    }
}



>>>>>>> 3c45077 (하하하 컨텐츠 추천에서 음악 미리듣기까지 내가 해냄)
