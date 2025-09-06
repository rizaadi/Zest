plugins {
    alias(libs.plugins.zest.android.application)
    alias(libs.plugins.zest.android.application.compose)
    alias(libs.plugins.zest.hilt) // This now applies KAPT and hilt-compiler
    alias(libs.plugins.ksp)       // Keep for other KSP processors (e.g., Room)
}

android {
    namespace = "com.zephysus.zest"

    defaultConfig {
        applicationId = "com.zephysus.zest"
        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
    }
    packaging {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }
}

dependencies {
    implementation(projects.core)
    implementation(projects.data.local)

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.hilt.navigation.compose) // Keep: Hilt specific for navigation
    implementation(libs.androidx.lifecycle.runtimeCompose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.tracing.ktx)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.lazy.swipe.cards)
    implementation(libs.compose.swipeable.cards)
    implementation(libs.androidx.work.runtime.ktx)
    implementation(libs.androidx.hilt.common) // Keep: May be needed if not covered by hilt.android/core
    implementation(libs.androidx.hilt.work)   // Keep: Specific for Hilt + WorkManager integration

    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)

    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}