package com.zephysus.zest

/**
 * This is shared between :app and :benchmarks module to provide configurations type safety.
 */
enum class ZestBuildType(val applicationIdSuffix: String? = null) {
    DEBUG(".debug"),
    RELEASE,
}
