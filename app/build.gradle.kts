import org.jetbrains.kotlin.konan.properties.hasProperty
import java.util.Properties

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    id("org.jetbrains.kotlin.kapt")
}

android {
    namespace = "com.zwc.cocblacklisthelper"
    compileSdk = libs.versions.compileSdk.get().toInt()
    defaultConfig {
        applicationId = "com.zwc.cocblacklisthelper"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = 8
        versionName = "3.1.1"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        flavorDimensions += listOf("default")
    }
    signingConfigs {
        val properties = Properties()
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
        val keyAliasString = properties.getProperty("key.alias")
        val keyPasswordString = properties.getProperty("key.password")
        val keyStorePasswordString = properties.getProperty("key.store.password")
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
            isMinifyEnabled = true
            signingConfig = signingConfigs["releaseConfig"]
            isDebuggable = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            isMinifyEnabled = true
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
    applicationVariants.all {
        outputs.all {
            if (this is com.android.build.gradle.internal.api.ApkVariantOutputImpl)
                if (outputFile.name.endsWith(".apk")) {
                    var flavorName = flavorName
                    if (flavorName != "dev") {
                        flavorName = ""
                    } else {
                        flavorName = ".dev"
                    }
                    var bType = ""
                    if (buildType.name == com.android.builder.core.BuilderConstants.DEBUG) {
                        bType = ".debug"
                    }
                    outputFileName =
                        "悟空COC黑名单阵型工具v" + defaultConfig.versionName + flavorName + bType + ".apk"
                }

        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    productFlavors {
        create("dev") {
            dimension = "default"
            applicationIdSuffix = ".dev"
            manifestPlaceholders += mutableMapOf("app_name" to "悟空COCdev")
        }
        create("prod") {
            dimension = "default"
            manifestPlaceholders += mutableMapOf("app_name" to "悟空COC")
        }
    }
}
kapt {
    generateStubs = true
}
dependencies {
    implementation(project(":baseLibrary"))
    implementation(project(":databaselibrary"))
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
    //图片
    implementation("com.davemorrissey.labs:subsampling-scale-image-view-androidx:3.10.0")
}