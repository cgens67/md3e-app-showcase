package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme =
  darkColorScheme(
    primary = GeoDarkPrimary,
    onPrimary = GeoDarkOnPrimary,
    primaryContainer = GeoDarkPrimaryContainer,
    onPrimaryContainer = GeoDarkOnPrimaryContainer,
    secondary = GeoDarkSecondary,
    onSecondary = GeoDarkOnSecondary,
    secondaryContainer = GeoDarkSecondaryContainer,
    onSecondaryContainer = GeoDarkOnSecondaryContainer,
    surface = GeoDarkSurface,
    onSurface = GeoDarkOnSurface,
    surfaceVariant = GeoDarkSurfaceVariant,
    onSurfaceVariant = GeoDarkOnSurfaceVariant,
    background = GeoDarkBackground,
    onBackground = GeoDarkOnSurface,
    outline = GeoDarkOutline,
    outlineVariant = GeoDarkOutlineVariant
  )

private val LightColorScheme =
  lightColorScheme(
    primary = GeoLightPrimary,
    onPrimary = GeoLightOnPrimary,
    primaryContainer = GeoLightPrimaryContainer,
    onPrimaryContainer = GeoLightOnPrimaryContainer,
    secondary = GeoLightSecondary,
    onSecondary = GeoLightOnSecondary,
    secondaryContainer = GeoLightOnSecondaryContainerReal,
    onSecondaryContainer = GeoLightOnSecondaryContainerReal,
    surface = GeoLightSurface,
    onSurface = GeoLightOnSurface,
    surfaceVariant = GeoLightSurfaceVariant,
    onSurfaceVariant = GeoLightOnSurfaceVariant,
    background = GeoLightBackground,
    onBackground = GeoLightOnSurface,
    outline = GeoLightOutline,
    outlineVariant = GeoLightOutlineVariant
  )

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  // Set default dynamicColor to false to respect the requested design theme
  dynamicColor: Boolean = false,
  content: @Composable () -> Unit,
) {
  val colorScheme =
    when {
      dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
        val context = LocalContext.current
        if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
      }

      darkTheme -> DarkColorScheme
      else -> LightColorScheme
    }

  MaterialTheme(colorScheme = colorScheme, typography = Typography, content = content)
}
