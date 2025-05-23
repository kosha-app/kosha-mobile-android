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
    signingConfigs {
        create("release") {
            storeFile = file("${project.rootDir}/keystore/release-keystores.jks")
            storePassword = "Bello@09"
            keyAlias = "release"
            keyPassword = "Bello@09"
        }
    }
    namespace = "com.musica.common"
    compileSdk = 35

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
            buildConfigField("String", "BASE_MCA_URL", "\"https://kosha-sit-h3dahzdcamh9h9bq.canadacentral-01.azurewebsites.net\"")
            signingConfig = signingConfigs.getByName("release")
            tasks {
                named("build") {
                    dependsOn(koverAndroidHtmlReportName("release"))
                    dependsOn("test")
                }
            }
        }

        debug {
            isMinifyEnabled = false
            buildConfigField("String", "BASE_MCA_URL", "\"https://kosha-sit-h3dahzdcamh9h9bq.canadacentral-01.azurewebsites.net\"")
            signingConfig = signingConfigs.getByName("debug")
            tasks {
                named("build") {
                    dependsOn(koverAndroidHtmlReportName("debug"))
                    dependsOn("test")
                }
            }
        }
    }

    kapt {
        correctErrorTypes = true
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.14"
    }

    lint.disable += "Instantiatable"

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

    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.activity:activity-compose")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

//    Shared dependencies -- Start

    api("androidx.compose.ui:ui")
    api("androidx.compose.ui:ui-graphics")
    api("androidx.compose.ui:ui-tooling-preview")
    api("androidx.compose.material3:material3")
    api("androidx.compose.material:material")

    api("com.github.bumptech.glide:compose:1.0.0-beta01")
    api("io.coil-kt.coil3:coil-compose:3.0.4")
    api("com.google.accompanist:accompanist-systemuicontroller:0.30.1")
    api("com.android.volley:volley:1.2.1")
    api("com.google.code.gson:gson:2.10.1")
    api("androidx.navigation:navigation-compose:2.7.6")

    val media3_version = "1.1.0"
    api("androidx.media3:media3-datasource-okhttp:$media3_version")
    api("androidx.media3:media3-exoplayer:$media3_version")
    api("androidx.media3:media3-ui:$media3_version")
    api("androidx.media3:media3-session:$media3_version")
    api("androidx.legacy:legacy-support-v4:1.0.0")

    api("com.github.commandiron:WheelPickerCompose:1.1.11")

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