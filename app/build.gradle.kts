plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.bookdonationapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.bookdonationapp"
        minSdk = 27
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
    buildFeatures{
        viewBinding = true
    }
}

dependencies {

    // Core Android Libraries
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    // Google Play Services
    implementation("com.google.android.gms:play-services-maps:19.0.0")
    implementation("com.google.android.gms:play-services-location:21.0.1") // Example version

    // Networking Libraries
    implementation("com.android.volley:volley:1.2.1") // Replace libs.volley if needed
    implementation("com.squareup.retrofit2:retrofit:2.9.0") // Replace libs.retrofit
    implementation("com.squareup.retrofit2:converter-gson:2.9.0") // Replace libs.retrofit2.converter.gson
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.1") // Replace libs.logging.interceptor

    // Firebase
    implementation("com.google.firebase:firebase-auth:23.0.0")

    // Navigation
    implementation("androidx.navigation:navigation-fragment-ktx:2.8.2")
    implementation("androidx.navigation:navigation-ui-ktx:2.8.2") // If using UI components

    // QR Code Libraries
    implementation("com.google.zxing:core:3.4.1")
    implementation("com.journeyapps:zxing-android-embedded:4.3.0")

    // JSON Handling
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("androidx.activity:activity:1.8.0")

    // Testing Libraries
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // Additional Libraries
    implementation("androidx.activity:activity-ktx:1.8.0")
    implementation("androidx.contentpager:contentpager:1.0.0") // Example, replace as needed
    implementation("com.google.maps.android:android-maps-utils:2.3.0") // Replace libs.android.maps.utils

    // Optional: View Binding
    // No additional dependencies required if enabled via buildFeatures

}
