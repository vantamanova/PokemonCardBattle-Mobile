package com.example.pokemoncardbattle.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Display the winner of the current turn.
        if (turnWinner != null) {
            Text(
                text = "🏆 ${turnWinner.name} wins this turn!",
                style = MaterialTheme.typography.titleLarge
            )

            Text(
                text = "Score: ${turnWinner.score}",
                style = MaterialTheme.typography.titleMedium
            )
        }

        // Display the final game result when all cards have been played.
        if (gameWinners != null) {

            Spacer(modifier = androidx.compose.ui.Modifier.height(16.dp))

            Text(
                text = "GAME OVER!",
                style = MaterialTheme.typography.headlineMedium
            )

            if (gameWinners.size == 1) {

                // Display a single winner.
                val winner = gameWinners.first()

                Text("${winner.name} wins the game!")
                Text("Final score: ${winner.score}")

            } else {

                // Display tied winners.
                Text("It's a tie!")

                gameWinners.forEach { winner ->
                    Text("${winner.name}: ${winner.score} points")
                }
            }

            Spacer(modifier = androidx.compose.ui.Modifier.height(16.dp))

            // Display the final scores for all players.
            Text(
                text = "FINAL SCORES",
                style = MaterialTheme.typography.titleMedium
            )

            players.forEach { player ->
                Text("${player.name}: ${player.score} points")
            }
        }
    }
}