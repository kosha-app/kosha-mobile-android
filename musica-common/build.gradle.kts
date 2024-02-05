import kotlinx.kover.gradle.plugin.dsl.koverAndroidHtmlReportName
import kotlinx.kover.gradle.plugin.dsl.koverAndroidXmlReportName

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id("org.jetbrains.kotlinx.kover")
}

android {
    namespace = "com.musica.common"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            tasks {
                named("build") {
                    dependsOn(koverAndroidHtmlReportName("release"))
                    dependsOn("test")
                }
            }
        }

        debug {
            isMinifyEnabled = false
            tasks {
                named("build") {
                    dependsOn(koverAndroidHtmlReportName("debug"))
                    dependsOn("test")
                }
            }
        }
    }

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.mockito:mockito-core:5.7.0")
    testImplementation("com.nhaarman.mockitokotlin2:mockito-kotlin:2.2.0")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

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

//    Shared dependencies -- Start

    api("androidx.compose.ui:ui:1.4.3")
    api("androidx.compose.ui:ui-graphics")
    api("androidx.compose.ui:ui-tooling-preview")
    api("androidx.compose.material3:material3")
    api("androidx.compose.material:material:1.4.3")

    api("com.github.bumptech.glide:compose:1.0.0-alpha.1")
    api("com.google.accompanist:accompanist-systemuicontroller:0.30.1")
    api("com.android.volley:volley:1.2.1")
    api("com.google.code.gson:gson:2.10.1")
    api("androidx.compose.ui:ui:1.6.0-alpha02")
    api("androidx.navigation:navigation-compose:2.7.0-rc01")

    api("com.github.commandiron:WheelPickerCompose:1.1.11")

    // https://mvnrepository.com/artifact/org.mockito/mockito-core


//    Shared dependencies -- End

    implementation("com.google.dagger:hilt-android:2.47")
    kapt("com.google.dagger:hilt-compiler:2.47")
    implementation(kotlin("reflect"))

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
                "*Response",
                "*Response\$*",
                "*Request",
                "*Request\$*",
                "*Activity",
                "*Activity\$*",
                "*.databinding.*",
                "*.BuildConfig",
                "*HiltModules*",
                "*Factory",
                // hilt
                "*.di.*",
                "dagger.hilt.**",
                "hilt_aggregated_deps.*",
                "*_Factory"
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

tasks {
    named("build") {
        dependsOn(koverAndroidXmlReportName("debug"))
//        dependsOn()
    }
}