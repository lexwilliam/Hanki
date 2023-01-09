// Top-level build file where you can add configuration options common to all sub-projects/modules.
// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    repositories {
        google()
        mavenCentral()
        maven { setUrl("https://jitpack.io") }
    }
    dependencies {
        classpath(BuildDependencies.buildGradle)
        classpath(BuildDependencies.kotlinGradle)
        classpath(BuildDependencies.gms)
        classpath(BuildDependencies.hiltGradle)
        classpath(BuildDependencies.googleSecret)
        classpath(BuildDependencies.navigationSafeArgs)
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven { setUrl("https://jitpack.io") }
    }
}

task("clean") {
    delete(rootProject.buildDir)
}