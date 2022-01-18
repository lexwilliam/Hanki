import com.lexwilliam.dependencies.ProjectModules
import com.lexwilliam.dependencies.Dependencies

plugins {
    id("com.android.library")
    id("com.lexwilliam.android.plugin")
}

dependencies {
    implementation(project(ProjectModules.data))

    implementation(Dependencies.Room.room)
    kapt(Dependencies.Room.roomCompiler)
    implementation(Dependencies.Room.roomKtx)

    implementation(Dependencies.Hilt.hiltAndroid)
    implementation(Dependencies.Hilt.hiltAndroidCompiler)
}