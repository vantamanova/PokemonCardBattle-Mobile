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
import com.example.pokemoncardbattle.PokemonCardView

// Displays the battle area, lead type, and cards played during the turn.
@Composable
fun BattleArea(
    leadType: String?,
    visiblePlayedCards: SnapshotStateList<Pair<Player, PokemonCard>>
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "BATTLE AREA",
            style = MaterialTheme.typography.headlineMedium
        )

        Text("Lead type: ${leadType ?: "Not selected"}")

        Spacer(modifier = Modifier.height(16.dp))

        // Display all cards played during the current turn.
        if (visiblePlayedCards.isNotEmpty()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                visiblePlayedCards.forEach { (player, card) ->
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = player.name,
                            style = MaterialTheme.typography.labelSmall
                        )

                        PokemonCardView(
                            card = card,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        } else {
            Text("Played cards will appear here")
        }
    }
}