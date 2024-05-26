plugins {
    alias(libs.plugins.androidApplication)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.mediconnect"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.mediconnect"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.database)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    implementation("androidx.recyclerview:recyclerview:1.2.1") // RecyclerView
    implementation("com.firebaseui:firebase-ui-database:7.1.1") // FirebaseUI for Realtime Database
    implementation("com.google.android.material:material:1.4.0") // Material Components for Android
    implementation("androidx.cardview:cardview:1.0.0") // CardView
    implementation ("com.github.bumptech.glide:glide:4.16.0")//glide
    //by user dependency
    implementation("com.firebaseui:firebase-ui-firestore:8.0.2")
}