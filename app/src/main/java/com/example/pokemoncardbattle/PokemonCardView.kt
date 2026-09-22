package com.example.pokemoncardbattle

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.foundation.clickable

// Displays a Pokemon card on the screen.
@Composable
fun PokemonCardView(
    card: PokemonCard,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {

    Card(
        modifier = modifier
            .padding(2.dp)
            .clickable(onClick = onClick)
    ) {
        Column(
            modifier = Modifier.padding(4.dp)
        ) {
            Text(
                text = card.name,
                style = MaterialTheme.typography.labelSmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = "HP ${card.hp}",
                style = MaterialTheme.typography.labelSmall
            )

            Text(
                text = card.type,
                style = MaterialTheme.typography.labelSmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}