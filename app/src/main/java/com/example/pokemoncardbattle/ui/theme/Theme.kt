package com.example.pokemoncardbattle.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

// Defines the colors used throughout the game.
private val GameColorScheme = darkColorScheme(
    primary = GamePrimary,
    onPrimary = GameBackground,

    secondary = GameSecondary,
    onSecondary = GameBackground,

    background = GameBackground,
    onBackground = GameText,

    surface = GameSurface,
    onSurface = GameText,

    surfaceVariant = GameSurfaceVariant,
    onSurfaceVariant = GameTextSecondary,

    error = GameError,
    onError = GameBackground
)

// Applies the custom dark theme to the entire application.
@Composable
fun PokemonCardBattleTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = GameColorScheme,
        typography = Typography,
        content = content
    )
}