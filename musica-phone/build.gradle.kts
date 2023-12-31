plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

val appCenterSdkVersion = "4.4.5"

android {
    namespace = "com.musica.phone"
    compileSdk = 34

    buildFeatures {
        buildConfig = true // Enable the buildConfig feature
    }

    defaultConfig {
        applicationId = "com.musica.phone"
        minSdk = 24
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
            buildConfigField("String", "BASE_MCA_URL", "\"https://kosha-app.azurewebsites.net/%s\"")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        debug {
            isMinifyEnabled = false
            buildConfigField("String", "BASE_MCA_URL", "\"https://kosha-app.azurewebsites.net/%s\"")
//            buildConfigField("String", "BASE_MCA_URL", "\"http://10.0.2.2:8080/%s\"")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation("androidx.activity:activity-compose:1.7.2")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.mockito:mockito-core:5.5.0")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    implementation("com.google.accompanist:accompanist-systemuicontroller:0.30.1")

    implementation("com.github.bumptech.glide:compose:1.0.0-alpha.1")

    implementation("com.android.volley:volley:1.2.1")

    implementation("com.google.code.gson:gson:2.10.1")

    implementation("com.google.dagger:hilt-android:2.47")
    kapt("com.google.dagger:hilt-compiler:2.47")

    implementation("androidx.compose.ui:ui:1.6.0-alpha02")
    implementation("androidx.navigation:navigation-compose:2.7.0-rc01")

    implementation(project(":musica-common"))
    implementation(project(":dashboard-module"))

    // App Centre

    implementation("com.microsoft.appcenter:appcenter-analytics:${appCenterSdkVersion}")
    implementation("com.microsoft.appcenter:appcenter-crashes:${appCenterSdkVersion}")
    implementation("com.microsoft.appcenter:appcenter-distribute:${appCenterSdkVersion}")
}

kapt {
    correctErrorTypes = true
}