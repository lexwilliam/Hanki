plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    compileSdk = AndroidSettings.compileSdk

    defaultConfig {
        applicationId = "com.lexwilliam.hanki"
        minSdk = AndroidSettings.minSdk
        targetSdk = AndroidSettings.targetSdk
        versionCode = 1
        versionName = AndroidSettings.appVersionName

        testInstrumentationRunner = AndroidSettings.testInstrumentationRunner
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

    packagingOptions {
        resources.excludes.addAll(
            listOf(
                "META-INF/DEPENDENCIES",
                "META-INF/LICENSE",
                "META-INF/LICENSE.txt",
                "META-INF/license.txt",
                "META-INF/NOTICE",
                "META-INF/NOTICE.txt",
                "META-INF/notice.txt",
                "META-INF/ASL2.0",
                "META-INF/*.kotlin_module",
                "META-INF/AL2.0",
                "META-INF/LGPL2.1",
                "META-INF/gradle/incremental.annotation.processors"
            )
        )
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
}

dependencies {
    implementation(project(ProjectModules.featureHome))
    implementation(project(ProjectModules.featurePacks))
    implementation(project(ProjectModules.featureExplore))
    implementation(project(ProjectModules.featureAdd))
    implementation(project(ProjectModules.featureAuth))
    implementation(project(ProjectModules.featurePackDetail))
    implementation(project(ProjectModules.featureEdit))
    implementation(project(ProjectModules.featureFlashcard))
    implementation(project(ProjectModules.featureResult))
    implementation(project(ProjectModules.domain))
    implementation(project(ProjectModules.data))
    implementation(project(ProjectModules.core))


    implementation(Dependencies.AndroidX.coreKtx)
    implementation(Dependencies.AndroidX.appCompat)
    implementation(Dependencies.AndroidX.material)
    implementation(Dependencies.AndroidX.constraintLayout)

    implementation(Dependencies.Navigation.navigationFragment)
    implementation(Dependencies.Navigation.navigationUi)

    implementation(platform(Dependencies.Firebase.bom))
    implementation(Dependencies.Firebase.analytics)

    implementation(Dependencies.Hilt.hilt)
    kapt(Dependencies.Hilt.hiltCompiler)

    implementation(Dependencies.timber)
    implementation(Dependencies.jodaTime)

    testImplementation(Dependencies.Testing.junit)
    androidTestImplementation(Dependencies.Testing.junitExt)
    androidTestImplementation(Dependencies.Testing.espressoCore)
}