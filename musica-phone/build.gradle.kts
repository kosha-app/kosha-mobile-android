import kotlinx.kover.gradle.plugin.dsl.koverAndroidXmlReportName
import java.io.FileInputStream
import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.gms.google-services")
    id("com.google.firebase.appdistribution")
    id("com.google.dagger.hilt.android")
    id("org.jetbrains.kotlinx.kover")
}

val props = Properties()
props.load(FileInputStream("${project.rootDir}/version.properties"))

props.forEach { (key, value) ->
    project.ext.set(key.toString(), value)
}

fun versionCode(): Int {
    val major = project.ext.get("majorVersion").toString().toInt()
    val minor = project.ext.get("minorVersion").toString().toInt()
    val patch = project.ext.get("patchVersion").toString().toInt()

    return major * 10000 + minor * 100 + patch
}

val versionString =
    "${project.ext.get("majorVersion")}.${project.ext.get("minorVersion")}.${project.ext.get("patchVersion")}"


val appCenterSdkVersion = "4.4.5"

android {
    signingConfigs {
        create("release") {
            storeFile = file("${project.rootDir}/keystore/release-keystores.jks")
            storePassword = "Bello@09"
            keyAlias = "release"
            keyPassword = "Bello@09"
        }
    }
    namespace = "com.musica.phone"
    compileSdk = 35

    buildFeatures {
        buildConfig = true // Enable the buildConfig feature
    }

    defaultConfig {
        applicationId = "com.musica.phone"
        minSdk = 24
        targetSdk = 34
        versionCode = versionCode()
        versionName = versionString

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            firebaseAppDistribution {
                serviceCredentialsFile = System.getenv("GOOGLE_APPLICATION_CREDENTIALS")
                artifactType = "APK"
            }

            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
            tasks {
                named("build") {
                    dependsOn(koverAndroidXmlReportName("release"))
                    dependsOn("test")
                }
            }
        }

        debug {
            firebaseAppDistribution {
                serviceCredentialsFile = System.getenv("GOOGLE_APPLICATION_CREDENTIALS")
                artifactType = "APK"
                appId = "1:159476609332:android:02625c98c6cb6fd5eeb561" // Replace this
            }
            isMinifyEnabled = false
            signingConfig = signingConfigs.getByName("debug")
            tasks {
                named("build") {
                    dependsOn(koverAndroidXmlReportName("debug"))
                    dependsOn("test")
                }
            }
        }
    }

    kapt {
        correctErrorTypes = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    testOptions {
        unitTests.isReturnDefaultValues = true
    }

    lint.disable += "Instantiatable"

    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.14"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.activity:activity-compose")
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.mockito:mockito-core:5.7.0")
    testImplementation("io.mockk:mockk:1.13.9")
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

    implementation(project(":musica-common"))
    implementation(project(":dashboard-module"))

    implementation("com.squareup.okhttp3:okhttp:4.12.0")

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
                "*Factory",
                "*Fragment\$*",
                "*Activity",
                "*Activity\$*",
                "*Response",
                "*Response\$*",
                "*Request",
                "*Request\$*",
                "*.databinding.*",
                "*.BuildConfig",
                "*HiltModules*",
                // hilt
                "*.di.*",
                "dagger.hilt.**",
                "hilt_aggregated_deps.*",
                "*_Factory",
                "com.musica.phone.servicelayer.Service"
            )
            annotatedBy("com.musica.common.compose.Exclude")
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
}
