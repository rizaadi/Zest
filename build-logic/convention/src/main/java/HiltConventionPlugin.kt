package com.zephysus.zest.convention

import com.zephysus.zest.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies

class HiltConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            // Apply the KAPT plugin
            apply(plugin = "org.jetbrains.kotlin.kapt")

            dependencies {
                "implementation"(libs.findLibrary("hilt.core").get())
                "kapt"(libs.findLibrary("hilt.compiler").get())
            }

            // Add support for Jvm Module
            pluginManager.withPlugin("org.jetbrains.kotlin.jvm") {
                // Hilt core is already added above
            }

            /** Add support for Android modules */
            pluginManager.withPlugin("com.android.base") {
                apply(plugin = "dagger.hilt.android.plugin")
                dependencies {
                    "implementation"(libs.findLibrary("hilt.android").get())
                    "implementation"(libs.findLibrary("androidx-hilt-work").get())
                }
            }
        }
    }
}