package com.example.pokemoncardbattle.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

// Displays navigation and game progression buttons.
@Composable
fun GameControls(
    showNextPlayer: Boolean,
    showResult: Boolean,
    showNextTurn: Boolean,
    onNextPlayer: () -> Unit,
    onShowResult: () -> Unit,
    onNextTurn: () -> Unit,
    onBack: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        // Allow the next player to take their turn.
        if (showNextPlayer) {
            Button(onClick = onNextPlayer) {
                Text("NEXT PLAYER")
            }
        }

        // Display the result after all players have played.
        if (showResult) {
            Button(onClick = onShowResult) {
                Text("SHOW RESULT")
            }
        }

        // Start a new turn with the previous winner.
        if (showNextTurn) {
            Button(onClick = onNextTurn) {
                Text("NEXT TURN")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Return to the home screen.
        Button(onClick = onBack) {
            Text("BACK TO HOME")
        }
    }
}