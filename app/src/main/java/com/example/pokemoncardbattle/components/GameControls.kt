package com.example.pokemoncardbattle.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        // Allow the next player to take their turn.
        if (showNextPlayer) {
            Button(
                onClick = onNextPlayer,
                modifier = Modifier.fillMaxWidth(0.75f),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("NEXT PLAYER")
            }
        }

        // Display the result after all players have played.
        if (showResult) {
            Button(
                onClick = onShowResult,
                modifier = Modifier.fillMaxWidth(0.75f),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("SHOW RESULT")
            }
        }

        // Start a new turn with the previous winner.
        if (showNextTurn) {
            Button(
                onClick = onNextTurn,
                modifier = Modifier.fillMaxWidth(0.75f),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("NEXT TURN")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Return to the home screen.
        OutlinedButton(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth(0.75f),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = MaterialTheme.colorScheme.onSurfaceVariant
            )
        ) {
            Text("BACK TO HOME")
        }
    }
}