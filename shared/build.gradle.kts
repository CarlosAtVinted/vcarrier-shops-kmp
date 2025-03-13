import org.jetbrains.kotlin.gradle.dsl.JvmTarget

import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.maven.publish)
}

version = "1.0.0"
group = "com.vinted.shops.kmp"

kotlin {
    androidTarget {
        compilations.all {
            compileTaskProvider.configure {
                compilerOptions {
                    jvmTarget.set(JvmTarget.JVM_1_8)
                }
            }
        }

        publishAllLibraryVariants()
    }

    val xcf = XCFramework("VGoShopsKMP")
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "VGoShopsKMP"
            xcf.add(this)
            isStatic = true
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(libs.timber)
        }

        commonMain.dependencies {
            api(libs.datadogKmp.logs)
            implementation(libs.datadogKmp.rum)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

android {
    namespace = "com.vinted.shops.kmp"
    compileSdk = 35
    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        buildConfig = true
    }
}
