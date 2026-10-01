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
import kotlin.concurrent.thread

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        android.util.Log.d("PokemonNetwork", "MAIN ACTIVITY STARTED")

        // Test connection to the Pokemon server
        // runs the networking work separately from the UI thread
        var objectiveResponse by mutableStateOf("Loading objective...")
        var playingResponse by mutableStateOf("Loading playing rules...")
        var followTypeResponse by mutableStateOf("Loading type rules...")
        var typesResponse by mutableStateOf("Loading type advantages...")
        var scoringResponse by mutableStateOf("Loading scoring...")

        thread {
            try {
                val client = PokemonClient()

                // Request the five types of game information from the server
                val objective = client.connectToServer("GET_OBJECTIVE")
                val playing = client.connectToServer("GET_PLAYING")
                val followType = client.connectToServer("GET_FOLLOW_TYPE")
                val types = client.connectToServer("GET_TYPES")
                val scoring = client.connectToServer("GET_SCORING")

                runOnUiThread {
                    objectiveResponse = objective
                    playingResponse = playing
                    followTypeResponse = followType
                    typesResponse = types
                    scoringResponse = scoring
                }
            } catch (e: Exception) {
                android.util.Log.e(
                    "PokemonNetwork",
                    "Connection failed: ${e.message}",
                    e
                )
            }
        }

        setContent {
            PokemonCardBattleTheme {
                var currentScreen by remember { mutableStateOf("home") }

                when (currentScreen) {
                    "game" -> {
                        GameScreen(
                            onBack = { currentScreen = "home" }
                        )
                    }

                    "rules" -> {
                        RulesScreen(
                            onBack = { currentScreen = "home" },
                            objectiveResponse = objectiveResponse,
                            playingResponse = playingResponse,
                            followTypeResponse = followTypeResponse,
                            typesResponse = typesResponse,
                            scoringResponse = scoringResponse
                        )
                    }

                    else -> {
                        HomeScreen(
                            onStart = { currentScreen = "game" },
                            onRules = { currentScreen = "rules" }
                        )
                    }
                }
            }
        }
    }
}

// Displays the main menu of the game.
@Composable
fun HomeScreen(
    onStart: () -> Unit,
    onRules: () -> Unit
) {

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
                        text = "START THE GAME",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = androidx.compose.ui.unit.TextUnit(
                            1f,
                            androidx.compose.ui.unit.TextUnitType.Sp
                        )
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedButton(
                    onClick = onRules,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                ) {
                    Text(
                        text = "HOW TO PLAY",
                        style = MaterialTheme.typography.titleLarge,
                    )
                }
            }
        }
    }
}