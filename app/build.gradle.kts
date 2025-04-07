import shadow.bundletool.com.android.tools.r8.internal.li

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.google.services)
    alias(libs.plugins.jetbrains.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.dagger.hilt.android)
}

android {
    namespace = "com.fitreal"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.fitreal"
        minSdk = 24
        targetSdk = 35
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    //ktx Serialization
    implementation(libs.kotlinx.serialization.json)

    //Compose Navigation
    implementation(libs.androidx.compose.navigation)
    implementation(libs.hilt.navigation.compose)

    //HILT
    implementation(libs.dagger.hilt.android)
    ksp(libs.dagger.hilt.compiler)

    //FIREBASE
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.authentication)

    //ROOM
    implementation(libs.room.runtime)
    ksp(libs.room.compiler)
    implementation(libs.room.ktx)

    //CREDENTIALS
    implementation(libs.androidx.credentials)
    implementation(libs.playservices.credentials.authentication)
    implementation(libs.googleid)

    //CAMERAX
    implementation(libs.androidx.camera.lifecycle)
    implementation(libs.androidx.camera.view)
    implementation(libs.androidx.camera.camera2)
    implementation(libs.androidx.camera.extensions)

    //COIL
    implementation(libs.coil)

    //Accompanist Permissions
    implementation(libs.accompanist.permissions)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}