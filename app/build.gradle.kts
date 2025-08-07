plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.example.myhzapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.myhzapp"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        ndk {
            // Filter for architectures supported by Flutter
            //noinspection ChromeOsAbiSupport
            abiFilters += listOf("armeabi-v7a", "arm64-v8a")
        }
    }

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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.media3.exoplayer)
    implementation(libs.androidx.media3.exoplayer.hls)
    implementation(libs.androidx.media3.exoplayer.dash)
    implementation(libs.androidx.media3.exoplayer.rtsp)
    implementation(libs.androidx.media3.exoplayer.smoothstreaming)
    api(files("libs/flutter_release-1.0.aar"))
    api(files("libs/video_player_android_release-1.0.aar"))
    api(libs.flutter.embedding.release)
    api(libs.flutter.armeabi.release)
    api(libs.flutter.arm64.release)
    api(libs.flutter.x86.release)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}