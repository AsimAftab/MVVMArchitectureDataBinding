plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
   // alias(libs.plugins.google.gms.google.services)
    id("com.google.gms.google-services")
    id("org.jetbrains.kotlin.kapt")

}

android {
    namespace = "com.example.mvvmarchitecturedatabinding"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.mvvmarchitecturedatabinding"
        minSdk = 24
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
        dataBinding=true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.play.services.tflite.acceleration.service)
    implementation(libs.firebase.auth)
    implementation(libs.play.services.auth)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(libs.firebase.auth)
    implementation(libs.glide)
    kapt(libs.compiler)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.play.services.auth)
    implementation(libs.androidx.lifecycle.livedata.ktx)

}