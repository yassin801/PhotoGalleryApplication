plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("plugin.parcelize")
}

val composeVersion = "1.5.1"
val coilVersion = "2.5.0"
val koinVersion = "3.5.3"
val retrofitVersion = "2.9.0"
val junitVersion = "4.13.2"
val espressoVersion = "3.5.1"

android {
    namespace = "com.sample.photogalleryapplication"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.sample.photogalleryapplication"
        minSdk = 23
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = composeVersion
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // AndroidX
    val coreKtx = "androidx.core:core-ktx:1.12.0"
    val lifecycleRuntimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:2.7.0"
    val lifecycleViewModelCompose = "androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0"
    val lifecycleLiveDataKtx = "androidx.lifecycle:lifecycle-livedata-ktx:2.7.0"
    val activityCompose = "androidx.activity:activity-compose:1.8.2"
    val navigationCompose = "androidx.navigation:navigation-compose:2.7.7"
    val composeBom = "androidx.compose:compose-bom:2023.08.00"

    implementation(coreKtx)
    implementation(lifecycleRuntimeKtx)
    implementation(lifecycleViewModelCompose)
    implementation(lifecycleLiveDataKtx)
    implementation(activityCompose)
    implementation(platform(composeBom))

    // Compose
    val composeUi = "androidx.compose.ui:ui"
    val composeUiGraphics = "androidx.compose.ui:ui-graphics"
    val composeUiToolingPreview = "androidx.compose.ui:ui-tooling-preview"
    val composeMaterial3 = "androidx.compose.material3:material3"
    val composeUiTestJUnit4 = "androidx.compose.ui:ui-test-junit4"

    implementation(composeUi)
    implementation(composeUiGraphics)
    implementation(composeUiToolingPreview)
    implementation(composeMaterial3)
    implementation(navigationCompose)
    androidTestImplementation(composeUiTestJUnit4)

    // Coil
    implementation("io.coil-kt:coil-compose:$coilVersion")

    // Koin
    implementation("io.insert-koin:koin-android:$koinVersion")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation("com.squareup.retrofit2:converter-gson:$retrofitVersion")
    implementation("com.squareup.okhttp3:logging-interceptor:4.8.1")

    // Test
    testImplementation("junit:junit:$junitVersion")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:$espressoVersion")
    androidTestImplementation(platform(composeBom))
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}