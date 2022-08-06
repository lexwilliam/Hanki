plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
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
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(Dependencies.AndroidX.coreKtx)
    implementation(Dependencies.AndroidX.appCompat)
    implementation(Dependencies.AndroidX.material)
    implementation(Dependencies.AndroidX.constraintLayout)

    implementation(Dependencies.Navigation.navigationFragment)
    implementation(Dependencies.Navigation.navigationUi)

    testImplementation(Dependencies.Testing.junit)
    androidTestImplementation(Dependencies.Testing.junitExt)
    androidTestImplementation(Dependencies.Testing.espressoCore)
}