package com.zephysus.zest.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

/**
 * A class to model background colors for Zest app.
 */
@Immutable
data class ZestBackgroundTheme(
    val color: Color = Color.Unspecified,
)

/**
 * A composition local for [ZestBackgroundTheme].
 */
val LocalZestBackgroundTheme = staticCompositionLocalOf { ZestBackgroundTheme() }

/**
 * Light background theme
 */
val LightZestBackgroundTheme = ZestBackgroundTheme(
    color = whiteBg2,
)

/**
 * Dark background theme
 */
val DarkZestBackgroundTheme = ZestBackgroundTheme(
    color = blackBg2,
)

private val DarkColorScheme = darkColorScheme(
    primary = Teal500,
    onPrimary = Teal950,
    primaryContainer = Teal800,
    onPrimaryContainer = Teal100,

    // Secondary Colors (Orange accent)
    secondary = Orange400,
    onSecondary = Neutral950,
    secondaryContainer = Orange600,
    onSecondaryContainer = Orange100,

    // Tertiary Colors (Coral accent)
    tertiary = Coral400,
    onTertiary = blackBg2,
    tertiaryContainer = Coral600,
    onTertiaryContainer = Coral100,

    // Error Colors
    error = Error400,
    onError = Error950,
    errorContainer = Error800,
    onErrorContainer = Error100,

    // Background Colors
    background = blackBg,
    onBackground = Neutral100,

    // Surface Colors
    surface = blackBg2,
    onSurface = Neutral100,
    surfaceVariant = NeutralVariant900,
    onSurfaceVariant = NeutralVariant300,
    surfaceTint = Teal500,

    // Container Colors
    surfaceContainer = Neutral900,
    surfaceContainerHigh = Neutral800,
    surfaceContainerHighest = Neutral700,
    surfaceContainerLow = Neutral950,
    surfaceContainerLowest = Neutral950,

    // Outline Colors
    outline = borderDark,
    outlineVariant = NeutralVariant800,

    // Other Colors
    scrim = Neutral950,
    inverseSurface = Neutral100,
    inverseOnSurface = Neutral900,
    inversePrimary = Teal700,
    surfaceBright = Neutral700,
    surfaceDim = Neutral950,
)

private val LightColorScheme = lightColorScheme(
    primary = Teal700, // TrueTurquoise
    onPrimary = whiteBg,
    primaryContainer = Teal100,
    onPrimaryContainer = Teal950,

    // Secondary Colors (Orange accent)
    secondary = Orange600,
    onSecondary = whiteBg,
    secondaryContainer = Orange200,
    onSecondaryContainer = Neutral950,

    // Tertiary Colors (Coral accent)
    tertiary = Coral600,
    onTertiary = whiteBg,
    tertiaryContainer = Coral200,
    onTertiaryContainer = Neutral950,

    // Error Colors
    error = Error600,
    onError = whiteBg,
    errorContainer = Error100,
    onErrorContainer = Error950,

    // Background Colors
    background = whiteBg,
    onBackground = Neutral950, // OffBlack

    // Surface Colors
    surface = whiteBg2,
    onSurface = Neutral950,
    surfaceVariant = NeutralVariant100,
    onSurfaceVariant = NeutralVariant700,
    surfaceTint = Teal700,

    // Container Colors
    surfaceContainer = NeutralVariant100,
    surfaceContainerHigh = NeutralVariant200,
    surfaceContainerHighest = NeutralVariant300,
    surfaceContainerLow = whiteBg,
    surfaceContainerLowest = whiteBg,

    // Outline Colors
    outline = borderLight,
    outlineVariant = NeutralVariant200,

    // Other Colors
    scrim = Neutral950,
    inverseSurface = Neutral900,
    inverseOnSurface = Neutral200,
    inversePrimary = Teal300,
    surfaceBright = whiteBg,
    surfaceDim = NeutralVariant100,
)

@Composable
fun ZestTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val backgroundTheme = when {
        darkTheme -> DarkZestBackgroundTheme
        else -> LightZestBackgroundTheme
    }

    CompositionLocalProvider(
        LocalZestBackgroundTheme provides backgroundTheme,
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}