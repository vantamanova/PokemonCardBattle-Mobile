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

// Displays the current player's hand and handles card selection.
@Composable
fun PlayerHand(
    player: Player,
    cards: SnapshotStateList<PokemonCard>,
    errorMessage: String?,
    onCardSelected: (PokemonCard) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Display the current player's information.
        Text(
            text = "${player.name}'s turn",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Display an error when the selected card is invalid.
        if (errorMessage != null) {
            Text(
                text = errorMessage,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.error
            )
        }

        // Display the current player's Pokemon cards.
        BoxWithConstraints(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            // Calculate card width based on the space available for five cards.
            val cardWidth = (maxWidth - 16.dp) / 5

            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                cards.forEach { card ->
                    PokemonCardView(
                        card = card,
                        modifier = Modifier.width(cardWidth),
                        onClick = {
                            // Send the selected card to GameScreen for validation.
                            onCardSelected(card)
                        }
                    )
                }
            }
        }
    }
}