plugins {
    alias(libs.plugins.zest.android.library)
    alias(libs.plugins.zest.hilt)
}

android {
    namespace = "com.zephysus.core"
}

dependencies {
    api(projects.data.local)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}