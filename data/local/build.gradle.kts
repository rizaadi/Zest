plugins {
    alias(libs.plugins.zest.android.library)
    alias(libs.plugins.zest.hilt)
    alias(libs.plugins.zest.android.room)
}

android {
    namespace = "com.zephysus.data.local"
}

dependencies {
    api(libs.androidx.dataStore)
    api(projects.core.datastoreProto)
    api(projects.core.model)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}