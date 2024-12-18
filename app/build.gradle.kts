plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.example.ideatapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.ideatapp"
        minSdk = 30
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
        buildConfigField("String", "BASE_URL", "\"https://ideat-api.janggarfals.xyz/\"")
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
        buildConfig = true
    }

}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)


    //material3
    implementation (libs.material)

    //retrofit
    implementation(libs.github.glide)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.logging.interceptor)

    //coroutine
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)
    //viewModel scope
    implementation( libs.androidx.lifecycle.runtime.ktx)

    //lifecycle scope
    implementation(libs.androidx.fragment.ktx)

    //Lifecycle
    implementation (libs.androidx.lifecycle.common)
    implementation (libs.androidx.lifecycle.runtime.ktx.v261)
    implementation (libs.androidx.lifecycle.livedata.ktx)

    //koin
    implementation( libs.koin.android)
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.datastore.core)

}