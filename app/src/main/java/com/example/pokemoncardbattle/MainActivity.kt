package com.example.pokemoncardbattle

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pokemoncardbattle.ui.theme.PokemonCardBattleTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PokemonCardBattleTheme {
                var showGame by remember { mutableStateOf(false) }

                if (showGame) {
                    GameScreen(onBack = { showGame = false })
                } else {
                    HomeScreen(onStart = { showGame = true })
                }
            }
        }
    }
}

// Displays the main menu of the game.
@Composable
fun HomeScreen(onStart: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "POKÉMON CARD BATTLE",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(onClick = onStart) {
            Text("START GAME")
        }
    }
}