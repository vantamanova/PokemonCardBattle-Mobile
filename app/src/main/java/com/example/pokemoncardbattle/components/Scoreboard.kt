package com.example.pokemoncardbattle.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pokemoncardbattle.Player
import com.example.pokemoncardbattle.PokemonCard

// Displays the scoreboard for all three players.
@Composable
fun Scoreboard(
    players: List<Player>,
    playerHands: Map<Player, SnapshotStateList<PokemonCard>>
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "SCOREBOARD",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            players.forEach { player ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = player.name,
                        style = MaterialTheme.typography.titleMedium
                    )

                    Text("Score: ${player.score}")

                    Text("Cards: ${playerHands.getValue(player).size}")
                }
            }
        }
    }
}