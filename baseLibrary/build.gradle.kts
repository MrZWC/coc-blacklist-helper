plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.kapt")
}

android {
    namespace = "com.zwc.baselibrary"
    compileSdk = libs.versions.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }
    dataBinding {
        enable = true
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
    packagingOptions {
        resources.excludes += "META-INF/*" + "META-INF/NOTICE" + "META-INF/LICENSE" + "META-INF/INDEX.LIST"
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

kapt {
    generateStubs = true
}
dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    //rx管理View的生命周期
    api(libs.rxlifecycle)
    api(libs.rxlifecycle.components)

    //rxbinding
    api(libs.rxbinding) {
        exclude("group", "com.android.support,io.reactivex.rxjava")
    }
    //rxjava
    api(libs.bundles.rxjava)
    //glide
    api(libs.glide)
    kapt(libs.glide.compiler)
    //recyclerview的databinding套装
    api(libs.bundles.bindingcollectionadapter) {
        exclude("group", "com.android.support")
    }
    val lifecycle_version = "2.5.1"
    api("androidx.lifecycle:lifecycle-viewmodel:$lifecycle_version")
    api("androidx.lifecycle:lifecycle-common-java8:$lifecycle_version")
    //api "androidx.lifecycle:lifecycle-viewmodel-savedstate:$lifecycle_version"
    api("androidx.lifecycle:lifecycle-livedata:$lifecycle_version")
    // ViewModel
    api("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")
    // LiveData
    api("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version")
    // Saved state module for ViewModel
    api("androidx.lifecycle:lifecycle-viewmodel-savedstate:$lifecycle_version")
    //协程
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.2")

    api("io.github.MrZWC:viewdialog:2.0.2")
    //
    api("io.github.idonans.systeminsets:systeminsets:2.0.3")
    api("io.github.MrZWC:lang:2.0.13")
    api("org.tukaani:xz:1.9")
    //gson
    api(libs.gson)
}