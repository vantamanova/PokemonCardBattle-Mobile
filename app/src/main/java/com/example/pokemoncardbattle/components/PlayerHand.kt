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
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Display the current player's information.
        Text(
            text = "${player.name}'s turn",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Display an error when the selected card is invalid.
        if (errorMessage != null) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error
            )
        }

        // Display the current player's Pokemon cards.
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            cards.forEach { card ->
                PokemonCardView(
                    card = card,
                    modifier = Modifier.weight(1f),
                    onClick = {
                        // Send the selected card to GameScreen for validation.
                        onCardSelected(card)
                    }
                )
            }
        }
    }
}