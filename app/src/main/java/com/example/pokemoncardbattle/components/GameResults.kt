package com.example.pokemoncardbattle.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import com.example.pokemoncardbattle.Player

// Displays the winner of the current turn and the final game results.
@Composable
fun GameResults(
    turnWinner: Player?,
    gameWinners: List<Player>?,
    players: List<Player>
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Display the winner of the current turn.
        if (turnWinner != null) {
            Text(
                text = "🏆 ${turnWinner.name} wins this turn!",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )

            Text(
                text = "Score: ${turnWinner.score}",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        // Display the final game result when all cards have been played.
        if (gameWinners != null) {

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "GAME OVER!",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )

            if (gameWinners.size == 1) {

                // Display a single winner.
                val winner = gameWinners.first()

                Text(
                    text = "${winner.name} wins the game!",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Text(
                    text = "Final score: ${winner.score}",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

            } else {

                // Display tied winners.
                Text(
                    text = "It's a tie!",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )

                gameWinners.forEach { winner ->
                    Text(
                        text = "${winner.name}: ${winner.score} points",
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

        }
    }
}