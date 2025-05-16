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
        namespace = "com.musica.dashboard"
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
                signingConfig = signingConfigs.getByName("debug")
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
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))

    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    implementation("androidx.activity:activity-compose")

    testImplementation("junit:junit:4.13.2")
    testImplementation("org.jetbrains.kotlin:kotlin-reflect")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.8.0-RC2")
    testImplementation("app.cash.turbine:turbine:1.0.0")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    implementation("com.google.dagger:hilt-android:2.47")
    kapt("com.google.dagger:hilt-compiler:2.47")

    implementation(project(":musica-common"))
    implementation(project(":kosha-api"))
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
                "*Response",
                "*Response\$*",
                "*Request",
                "*Request\$*",
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
    }
}