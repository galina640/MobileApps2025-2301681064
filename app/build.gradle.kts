plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.example.smartnotespro"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.smartnotespro"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner =
            "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false

            proguardFiles(
                getDefaultProguardFile(
                    "proguard-android-optimize.txt"
                ),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility =
            JavaVersion.VERSION_11

        targetCompatibility =
            JavaVersion.VERSION_11
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
    implementation(libs.androidx.constraintlayout)

    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(
        libs.androidx.espresso.core
    )

    // RecyclerView
    implementation(
        "androidx.recyclerview:recyclerview:1.3.2"
    )

    // Lifecycle
    implementation(
        "androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.1"
    )

    implementation(
        "androidx.lifecycle:lifecycle-livedata-ktx:2.8.1"
    )

    // Coroutines
    implementation(
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.1"
    )

    // Room Database
    implementation(
        "androidx.room:room-runtime:2.6.1"
    )

    implementation(
        "androidx.room:room-ktx:2.6.1"
    )

    ksp(
        "androidx.room:room-compiler:2.6.1"
    )

    // Coil
    implementation(
        "io.coil-kt:coil:2.6.0"
    )
}