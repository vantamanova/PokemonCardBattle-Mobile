package com.example.pokemoncardbattle

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

// Returns a color based on the Pokemon's type.
fun pokemonTypeColor(type: String): Color {
    return when (type.lowercase()) {
        "grass" -> Color(0xFF80E27E)
        "fire" -> Color(0xFFFF7043)
        "water" -> Color(0xFF40C4FF)
        "electric" -> Color(0xFFFFD740)
        "psychic" -> Color(0xFFEA80FC)
        "poison" -> Color(0xFFBA68C8)
        "bug" -> Color(0xFFAED581)
        "ground" -> Color(0xFFD7A86E)
        "rock" -> Color(0xFFBCAAA4)
        "fighting" -> Color(0xFFFF8A65)
        "ghost" -> Color(0xFF9575CD)
        "fairy" -> Color(0xFFF48FB1)
        else -> Color(0xFFCFD8DC)
    }
}

// Displays a Pokemon card on the screen.
@Composable
fun PokemonCardView(
    card: PokemonCard,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    val context = LocalContext.current
    val typeColor = pokemonTypeColor(card.type)

    // Load the Pokemon image from the assets folder.
    val pokemonImage = remember(card.image) {
        try {
            context.assets.open(card.image).use { input ->
                BitmapFactory.decodeStream(input)?.asImageBitmap()
            }
        } catch (e: Exception) {
            null
        }
    }

    Card(
        modifier = modifier
            .padding(2.dp)
            .aspectRatio(3f / 4f)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF101820)
        ),
        border = BorderStroke(1.5.dp, typeColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(6.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Display the Pokemon's name.
            Text(
                text = card.name,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.labelMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            // Display the Pokemon artwork.
            if (pokemonImage != null) {
                Image(
                    bitmap = pokemonImage,
                    contentDescription = card.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentScale = ContentScale.Fit
                )
            }

            // Display the Pokemon's type and HP.
            Column {
                Text(
                    text = card.type,
                    color = typeColor,
                    style = MaterialTheme.typography.labelSmall,
                    maxLines = 1
                )

                Text(
                    text = "HP ${card.hp}",
                    color = Color.White,
                    style = MaterialTheme.typography.labelSmall,
                    maxLines = 1
                )
            }
        }
    }
}