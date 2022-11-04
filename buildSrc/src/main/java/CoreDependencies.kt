object Dependencies {

    object AndroidX {
        const val coreKtx = "androidx.core:core-ktx:1.8.0"
        const val appCompat = "androidx.appcompat:appcompat:1.5.1"
        const val material = "com.google.android.material:material:1.7.0"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.1.4"
        const val recyclerView = "androidx.recyclerview:recyclerview:1.2.1"
        const val recyclerViewSelection = "androidx.recyclerview:recyclerview-selection:1.1.0"
    }

    object Navigation {
        const val navigationFragment = "androidx.navigation:navigation-fragment-ktx:2.5.1"
        const val navigationUi = "androidx.navigation:navigation-ui-ktx:2.5.1"
    }

    object Firebase {
        const val bom = "com.google.firebase:firebase-bom:30.3.2"
        const val auth = "com.google.firebase:firebase-auth-ktx"
        const val analytics = "com.google.firebase:firebase-analytics-ktx"
        const val firestore = "com.google.firebase:firebase-firestore-ktx"
    }

    object Testing {
        const val junit = "junit:junit:4.13.2"
        const val junitExt = "androidx.test.ext:junit:1.1.3"
        const val espressoCore = "androidx.test.espresso:espresso-core:3.4.0"
    }

    object Hilt {
        const val hilt = "com.google.dagger:hilt-android:2.44"
        const val hiltCompiler = "com.google.dagger:hilt-android-compiler:2.44"
    }

    const val kotlinxCoroutines =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0"

    const val kotlinReflect =
        "org.jetbrains.kotlin:kotlin-reflect:1.7.20"

    const val timber = "com.jakewharton.timber:timber:4.7.1"
    const val playServiceAuth = "com.google.android.gms:play-services-auth:20.2.0"
    const val coil = "io.coil-kt:coil:2.1.0"
    const val jodaTime = "joda-time:joda-time:2.10.14"

}