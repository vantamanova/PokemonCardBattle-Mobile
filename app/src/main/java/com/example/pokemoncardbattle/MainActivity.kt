package com.example.pokemoncardbattle

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.pokemoncardbattle.ui.theme.PokemonCardBattleTheme
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PokemonCardBattleTheme {
                var showGame by remember { mutableStateOf(false) }

                if (showGame) {
                    GameScreen(onBack = { showGame = false })
                } else {
                    HomeScreen(onStart = { showGame = true })
                }
            }
        }
    }
}

// Displays the main menu of the game.
@Composable
fun HomeScreen(onStart: () -> Unit) {

    val context = LocalContext.current

    // Pikachu artwork
    val pikachuImage = remember {
        try {
            context.assets.open("images/25.png").use { input ->
                BitmapFactory.decodeStream(input)?.asImageBitmap()
            }
        } catch (e: Exception) {
            null
        }
    }

    val gold = MaterialTheme.colorScheme.primary
    val secondaryText = MaterialTheme.colorScheme.onSurfaceVariant

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        // Atmospheric background lighting.
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.75f)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF17283D),
                            Color(0xFF101B2A),
                            MaterialTheme.colorScheme.background
                        )
                    )
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 28.dp, vertical = 150.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            // Game name
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "POKÉMON",
                    style = MaterialTheme.typography.displayMedium,
                    fontWeight = FontWeight.Black,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center
                )

                Text(
                    text = "CARD BATTLE GAME",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Light,
                    color = gold,
                    textAlign = TextAlign.Center,
                    letterSpacing = androidx.compose.ui.unit.TextUnit(
                        2f,
                        androidx.compose.ui.unit.TextUnitType.Sp
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Decorative divider.
                Box(
                    modifier = Modifier
                        .width(80.dp)
                        .height(1.dp)
                        .background(gold)
                )
            }

            // Main Pokemon illustration.
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {

                // lighting behind the Pokemon
                Box(
                    modifier = Modifier
                        .size(280.dp)
                        .background(
                            brush = Brush.radialGradient(
                                colors = listOf(
                                    Color(0x55D8B878),
                                    Color(0x222C4765),
                                    Color.Transparent
                                )
                            ),
                            shape = CircleShape
                        )
                )

                // frame.
                Box(
                    modifier = Modifier
                        .size(260.dp)
                        .border(
                            width = 1.dp,
                            color = gold.copy(alpha = 0.35f),
                            shape = CircleShape
                        )
                )

                if (pikachuImage != null) {
                    Image(
                        bitmap = pikachuImage,
                        contentDescription = "Pikachu",
                        modifier = Modifier
                            .size(290.dp),
                        contentScale = ContentScale.Fit
                    )
                }
            }

            // Navigation
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Start a new game
                Button(
                    onClick = onStart,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(58.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = gold,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {

                    Text(
                        text = "ENTER THE ARENA",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = androidx.compose.ui.unit.TextUnit(
                            1f,
                            androidx.compose.ui.unit.TextUnitType.Sp
                        )
                    )
                }
            }
        }
    }
}