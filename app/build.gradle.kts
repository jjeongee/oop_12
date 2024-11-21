plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
<<<<<<< HEAD
}

android {
    namespace = "com.example.toastoutapplication"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.toastoutapplication"
        minSdk = 24
        targetSdk = 34
=======
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.example.toastout"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.toastout"
        minSdk = 24
        targetSdk = 35
>>>>>>> 3c45077 (하하하 컨텐츠 추천에서 음악 미리듣기까지 내가 해냄)
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

<<<<<<< HEAD
=======
    viewBinding {
        enable = true
    }

    buildFeatures{
        dataBinding=true
        buildConfig = true
    }

>>>>>>> 3c45077 (하하하 컨텐츠 추천에서 음악 미리듣기까지 내가 해냄)
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
<<<<<<< HEAD

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
=======
    implementation(libs.firebase.auth)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation("com.naver.maps:map-sdk:3.19.1")
    implementation("com.google.android.gms:play-services-location:21.0.1")
    implementation(platform("com.google.firebase:firebase-bom:33.6.0"))
    implementation("com.google.firebase:firebase-analytics-ktx:22.1.2")
    implementation ("androidx.navigation:navigation-fragment-ktx:2.5.3")
    implementation ("androidx.navigation:navigation-ui-ktx:2.5.3")
    implementation ("androidx.navigation:navigation-runtime-ktx:2.2.2")
    implementation ("androidx.navigation:navigation-compose:2.4.0-alpha05")
    // Feature module support for Fragments
    implementation("androidx.navigation:navigation-dynamic-features-fragment:2.8.0")
    // Testing Navigation
    androidTestImplementation("androidx.navigation:navigation-testing:2.8.0")



>>>>>>> 3c45077 (하하하 컨텐츠 추천에서 음악 미리듣기까지 내가 해냄)
}
