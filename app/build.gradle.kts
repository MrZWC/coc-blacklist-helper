import org.jetbrains.kotlin.konan.properties.hasProperty
import java.util.Properties

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    id("org.jetbrains.kotlin.kapt")
    id("androidx.room")
}

android {
    namespace = "com.zwc.cocblacklisthelper"
    compileSdk = 34
    defaultConfig {
        applicationId = "com.zwc.cocblacklisthelper"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    signingConfigs {
        val properties =  Properties()
        properties.load(project.rootProject.file("local.properties").inputStream())
        if (!properties.hasProperty("key.alias")) {
            throw IllegalArgumentException("key.alias not found")
        }
        if (!properties.hasProperty("key.password")) {
            throw IllegalArgumentException("key.password not found")
        }
        if (!properties.hasProperty("key.store.password")) {
            throw IllegalArgumentException("key.store.password not found")
        }
       val keyAliasString= properties.getProperty("key.alias")
       val keyPasswordString= properties.getProperty("key.password")
       val keyStorePasswordString= properties.getProperty("key.store.password")
        create("releaseConfig") {
            keyAlias = keyAliasString
            keyPassword = keyPasswordString
            storeFile = file("../keystore/release_key.jks")
            storePassword = keyStorePasswordString
        }
        create("debugConfig") {
            keyAlias = keyAliasString
            keyPassword = keyPasswordString
            storeFile = file("../keystore/release_key.jks")
            storePassword = keyStorePasswordString
        }
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            signingConfig = signingConfigs["releaseConfig"]
            isDebuggable = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs["debugConfig"]
            isDebuggable = true
        }
    }
    dataBinding {
        enable = true
    }
    viewBinding {
        enable = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    room {
        schemaDirectory("$projectDir/schemas")
    }
}
kapt {
    generateStubs = true
}
dependencies {
    implementation(project(":baseLibrary"))
    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)
    implementation(libs.androidx.activity)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation("io.github.MrZWC:roundlayout:1.0.0")
    implementation(libs.timber)
    //log
    implementation(libs.klog)
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    // To use Kotlin annotation processing tool (kapt)
    kapt(libs.room.compiler)
}