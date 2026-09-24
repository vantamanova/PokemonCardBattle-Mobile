package com.example.pokemoncardbattle

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pokemoncardbattle.components.BackToHomeButton

// Displays the game rules and instructions.
@Composable
fun RulesScreen(
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 28.dp, vertical = 150.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Screen title.
        Text(
            text = "HOW TO PLAY",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(28.dp))

        // Game rules.
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {

            RuleSection(
                title = "OBJECTIVE",
                description = "Win battles and finish the game with the highest score."
            )

            RuleSection(
                title = "PLAYING A CARD",
                description = "Each player receives 5 Pokémon cards. Players take turns playing one card."
            )

            RuleSection(
                title = "FOLLOW THE TYPE",
                description = "The first card sets the lead type. If you have a card of that type, you must play it."
            )

            RuleSection(
                title = "WINNING A BATTLE",
                description = "Type advantage is checked first. If there is no type advantage, HP determines the winner."
            )

            RuleSection(
                title = "SCORING",
                description = "The winner of each battle earns 10 points. After 5 battles, the player with the highest score wins!",
                showDivider = false
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        BackToHomeButton(
            onBack = onBack
        )
    }
}


// Displays one section of the game rules.
@Composable
fun RuleSection(
    title: String,
    description: String,
    showDivider: Boolean = true
) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.primary
    )

    Spacer(modifier = Modifier.height(5.dp))

    Text(
        text = description,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onBackground
    )

    if (showDivider) {
        Spacer(modifier = Modifier.height(12.dp))

        HorizontalDivider(
            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.25f)
        )

        Spacer(modifier = Modifier.height(12.dp))
    }
}