import kotlinx.kover.gradle.plugin.dsl.koverAndroidXmlReportName

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.gms.google-services")
    id("com.google.firebase.appdistribution")
    id("com.google.dagger.hilt.android")
    id("org.jetbrains.kotlinx.kover")
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
        versionName = "0.1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            buildConfigField("String", "BASE_MCA_URL", "\"https://kosha-app.azurewebsites.net/%s\"")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            tasks {
                named("build") {
                    dependsOn(koverAndroidXmlReportName("release"))
                    dependsOn("test")
                }
            }
        }

        debug {
            isMinifyEnabled = false
            buildConfigField(
                "String",
                "BASE_MCA_URL",
                "\"https://kosha-app-developer.azurewebsites.net/%s\""
            )
            tasks {
                named("build") {
                    dependsOn(koverAndroidXmlReportName("debug"))
                    dependsOn("test")
                }
            }
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
    testImplementation("org.mockito:mockito-core:5.7.0")
    testImplementation("com.nhaarman.mockitokotlin2:mockito-kotlin:2.2.0")

    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    implementation(platform("com.google.firebase:firebase-bom:32.7.1"))

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

koverReport {
    // filters for all report types of all build variants
    filters {
        excludes {
            classes(
                "*Fragment",
                "*Fragment\$*",
                "*Activity",
                "*Activity\$*",
                "*.databinding.*",
                "*.BuildConfig"
            )
            annotatedBy(
                "androidx.compose.runtime.Composable"
            )
        }
    }

    verify {
        rule("Basic Line Coverage") {
            isEnabled = true
            bound {
                minValue = 80 // Minimum coverage percentage
                maxValue = 100 // Maximum coverage percentage (optional)
                metric = kotlinx.kover.gradle.plugin.dsl.MetricType.LINE
                aggregation = kotlinx.kover.gradle.plugin.dsl.AggregationType.COVERED_PERCENTAGE
            }
        }
    }


//    androidReports("release") {
//        // filters for all report types only of 'release' build type
//        filters {
//            excludes {
//                classes(
//                    "*Fragment",
//                    "*Fragment\$*",
//                    "*Activity",
//                    "*Activity\$*",
//                    "*.databinding.*",
//                    "*.BuildConfig",
//
//                    // excludes debug classes
//                    "*.DebugUtil"
//                )
//            }
//        }
//    }

}