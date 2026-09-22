package com.example.pokemoncardbattle

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

// Displays the main game screen with players and the battle area.
@Composable
fun GameScreen(onBack: () -> Unit) {
    val context = LocalContext.current

    // Load the Pokemon collection once.
    val pokemonCards = remember(context) {
        loadPokemon(context)
    }

    // Create players, shuffle the deck, and deal five cards once.
    val players = remember(pokemonCards) {
        val gamePlayers = createPlayers(listOf("Vera", "Daniil", "Sofia"))

        val deck = createDeck(pokemonCards)
        shuffleDeck(deck)
        dealCards(deck, gamePlayers)

        gamePlayers
    }

    val vera = players[0]
    val daniil = players[1]
    val sofia = players[2]

    // Create the game once.
    val game = remember { Game() }

    // Randomly choose the first player and establish the turn order.
    var turnOrder by remember(players) {
        val startingPlayer = chooseStartingPlayer(players)

        mutableStateOf(
            game.getTurnOrder(players, startingPlayer)
        )
    }

    // Track whose turn it is.
    var currentPlayerIndex by remember {
        mutableStateOf(0)
    }

    val currentPlayer = turnOrder[currentPlayerIndex]

    // Keep all players' hands in observable Compose state.
    val playerHands = remember(players) {
        players.associateWith { player ->
            mutableStateListOf<PokemonCard>().apply {
                addAll(player.hand)
            }
        }
    }

    // Get the current player's visible hand.
    val currentHand = playerHands.getValue(currentPlayer)

    // Keep played cards in observable Compose state.
    val visiblePlayedCards = remember {
        mutableStateListOf<Pair<Player, PokemonCard>>()
    }

    // Stores the type of the first card played in the turn.
    var leadType by remember { mutableStateOf<String?>(null) }

    // Stores a message when the player selects an invalid card.
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Controls whether the next player's hand is visible.
    var waitingForNextPlayer by remember {
        mutableStateOf(false)
    }

    // Stores the winner of the current turn.
    var turnWinner by remember {
        mutableStateOf<Player?>(null)
    }

    // Stores the final game winners, including ties.
    var gameWinners by remember {
        mutableStateOf<List<Player>?>(null)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        // Opponents
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("${daniil.name} — Score: ${daniil.score}")
            Text("Cards: ${daniil.hand.size}")

            Spacer(modifier = Modifier.height(16.dp))

            Text("${sofia.name} — Score: ${sofia.score}")
            Text("Cards: ${sofia.hand.size}")
        }

        // Battle area
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "BATTLE AREA",
                style = MaterialTheme.typography.headlineMedium
            )

            Text("Lead type: ${leadType ?: "Not selected"}")

            Spacer(modifier = Modifier.height(16.dp))

            // Display all cards played during the current turn.
            if (visiblePlayedCards.isNotEmpty()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    visiblePlayedCards.forEach { (player, card) ->
                        Column(
                            modifier = Modifier.weight(1f),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = player.name,
                                style = MaterialTheme.typography.labelSmall
                            )

                            PokemonCardView(
                                card = card,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            } else {
                Text("Played cards will appear here")
            }
        }

        // Current player
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Display player information only while the turn is active.
            if (turnWinner == null) {
                Text("${currentPlayer.name} — Score: ${currentPlayer.score}")
                Text("Current turn: ${currentPlayer.name}")
            }

            // Display five Pokemon cards.
            if (!waitingForNextPlayer) {

                if (errorMessage != null) {
                    Text(
                        text = errorMessage!!,
                        color = MaterialTheme.colorScheme.error
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    currentHand.forEach { card ->
                        PokemonCardView(
                            card = card,
                            modifier = Modifier.weight(1f),
                            onClick = {

                                if (currentPlayer !in game.playedCards) {

                                    val success = game.playCard(
                                        player = currentPlayer,
                                        card = card,
                                        leadType = leadType
                                    )

                                    if (success) {

                                        // The first played card establishes the lead type.
                                        if (leadType == null) {
                                            leadType = card.type
                                        }

                                        // Update the hand and battle area.
                                        currentHand.remove(card)

                                        // Add the selected card to the visible battle area.
                                        visiblePlayedCards.add(currentPlayer to card)

                                        // Clear previous errors.
                                        errorMessage = null

                                        // Hide the hand until the next player is ready.
                                        waitingForNextPlayer = true

                                    } else {

                                        // Explain why the selected card cannot be played.
                                        errorMessage = "You must play a $leadType Pokémon!"
                                    }
                                }
                            }
                        )
                    }
                }
            }

            if (waitingForNextPlayer && currentPlayerIndex < turnOrder.lastIndex) {

                Button(
                    onClick = {
                        // Move to the next player.
                        currentPlayerIndex++
                        waitingForNextPlayer = false
                        errorMessage = null
                    }
                ) {
                    Text("NEXT PLAYER")
                }
            }

            // Show the result button after all three players have played.
            if (
                waitingForNextPlayer &&
                currentPlayerIndex == turnOrder.lastIndex &&
                turnWinner == null
            ) {
                Button(
                    onClick = {
                        // Calculate the winner and update the score.
                        turnWinner = game.determineTurnWinner()

                        // Check whether all players have finished their cards.
                        if (players.all { it.hand.isEmpty() }) {
                            gameWinners = game.determineGameWinner(players)
                        }
                    }
                ) {
                    Text("SHOW RESULT")
                }
            }

            // Display the winner and their updated score.
            if (turnWinner != null) {

                Text(
                    text = "🏆 ${turnWinner!!.name} wins this turn!",
                    style = MaterialTheme.typography.titleLarge
                )

                Text(
                    text = "Score: ${turnWinner!!.score}",
                    style = MaterialTheme.typography.titleMedium
                )
            }

            // Start a new turn with the previous winner.
            if (turnWinner != null && players.any { it.hand.isNotEmpty() }) {

                Button(
                    onClick = {
                        // The winner starts the next turn.
                        turnOrder = game.getTurnOrder(players, turnWinner!!)

                        // Clear cards from the previous turn.
                        game.playedCards.clear()
                        visiblePlayedCards.clear()

                        // Reset turn information.
                        currentPlayerIndex = 0
                        leadType = null
                        errorMessage = null
                        waitingForNextPlayer = false
                        turnWinner = null
                    }
                ) {
                    Text("NEXT TURN")
                }
            }

            // Display the final game result when all cards have been played.
            if (gameWinners != null) {

                Text(
                    text = "GAME OVER!",
                    style = MaterialTheme.typography.headlineMedium
                )

                if (gameWinners!!.size == 1) {

                    // Display a single winner.
                    val winner = gameWinners!!.first()

                    Text("${winner.name} wins the game!")
                    Text("Final score: ${winner.score}")

                } else {

                    // Display tied winners.
                    Text("It's a tie!")

                    gameWinners!!.forEach { winner ->
                        Text("${winner.name}: ${winner.score} points")
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = onBack) {
                Text("BACK TO HOME")
            }
        }
    }
}