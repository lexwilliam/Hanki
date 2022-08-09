plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = AndroidSettings.compileSdk

    defaultConfig {
        minSdk = AndroidSettings.minSdk
        targetSdk = AndroidSettings.targetSdk

        testInstrumentationRunner = AndroidSettings.testInstrumentationRunner
        proguardFiles(file("consumer-rules.pro"))
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                file("proguard-rules.pro")
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(project(ProjectModules.domain))

    implementation(platform(Dependencies.Firebase.bom))
    implementation(Dependencies.Firebase.firestore)

    implementation(Dependencies.Hilt.hilt)
    kapt(Dependencies.Hilt.hiltCompiler)

    implementation(Dependencies.timber)

    testImplementation(Dependencies.Testing.junit)
    androidTestImplementation(Dependencies.Testing.junitExt)
}