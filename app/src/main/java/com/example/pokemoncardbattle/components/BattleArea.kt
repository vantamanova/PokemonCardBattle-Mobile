package com.example.pokemoncardbattle.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "BATTLE AREA",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground
        )

        Text(
            text = "Lead type: ${leadType ?: "Not selected"}",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Reserve three equally sized slots for played cards.
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            repeat(3) { index ->

                val playedCard = visiblePlayedCards.getOrNull(index)

                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Display the player who played this card.
                    Text(
                        text = playedCard?.first?.name ?: "WAITING",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    if (playedCard != null) {

                        // Display the Pokemon in its assigned slot.
                        PokemonCardView(
                            card = playedCard.second,
                            modifier = Modifier.fillMaxWidth()
                        )

                    } else {

                        // Display an empty slot until the next card is played.
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(3f / 4f),
                            shape = RoundedCornerShape(10.dp),
                            color = MaterialTheme.colorScheme.surface,
                            border = BorderStroke(
                                1.dp,
                                MaterialTheme.colorScheme.outlineVariant
                            )
                        ) {
                            Box(
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "?",
                                    style = MaterialTheme.typography.headlineMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}