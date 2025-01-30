import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.hilt.android)
}

android {
    namespace = "com.example.travelmate"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.travelmate"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        val localProperties = Properties()
        val localPropertiesFile = rootProject.file("local.properties")
        if (localPropertiesFile.exists()) localPropertiesFile.inputStream().use {
            localProperties.load(it)
        }

        buildConfigField(
            type = "String",
            name = "SECRET_APP",
            value = localProperties["SECRETAPP"].toString()
        )
        buildConfigField(
            type = "String",
            name = "USER_ID",
            value = localProperties["USERID"].toString()
        )
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
        buildConfig = true
    }
}

dependencies {
    // splash screen
    implementation(libs.androidx.core.splashscreen)

    // retrofit
    implementation(libs.retrofit)

    // gson converter
    implementation(libs.converter.gson)

    // logging interceptor
    implementation(libs.logging.interceptor)

    // hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)

    // data store
    implementation(libs.androidx.datastore.preferences)

    // coil
    implementation(libs.coil)
    implementation(libs.coil.network.okhttp)

    // shimmer
    implementation(libs.shimmer)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.activity)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}