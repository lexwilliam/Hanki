import com.lexwilliam.dependencies.ProjectModules
import com.lexwilliam.dependencies.Dependencies

plugins {
    id("com.android.library")
    id("com.lexwilliam.android.plugin")
}

dependencies {
    implementation(project(ProjectModules.data))

    api(Dependencies.Room.room)
    api(Dependencies.Room.roomCompiler)

    implementation(Dependencies.Hilt.hiltAndroid)
    implementation(Dependencies.Hilt.hiltAndroidCompiler)
}