plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id ("kotlin-kapt")
}

android {
    namespace = "com.example.dislexiaapp2025"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.dislexiaapp2025"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
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
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    //glide
    implementation("com.github.bumptech.glide:glide:4.16.0")
    kapt ("com.github.bumptech.glide:compiler:4.13.0")
    androidTestImplementation(libs.androidx.espresso.core)
    //material design icons
    implementation ("com.google.android.material:material:1.10.0")
    //room
    val  room_version = "2.6.1"
    implementation ("androidx.room:room-runtime:$room_version")
    kapt ("androidx.room:room-compiler:$room_version") // استخدم kapt مع Kotlin
    implementation ("androidx.room:room-ktx:$room_version") // دعم Kotlin Extensions
    //lottie animation
        implementation (libs.lottie)
    //coroutines
    // كوروتين الأساسية
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")

    // دعم كوروتين للأندرويد
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    // لو بتستخدم Room مع كوروتين
    implementation ("androidx.room:room-ktx:2.6.1")



}