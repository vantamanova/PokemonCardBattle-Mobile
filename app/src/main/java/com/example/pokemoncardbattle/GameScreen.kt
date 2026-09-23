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
import com.example.pokemoncardbattle.components.Scoreboard
import com.example.pokemoncardbattle.components.BattleArea
import com.example.pokemoncardbattle.components.PlayerHand
import com.example.pokemoncardbattle.components.GameResults
import com.example.pokemoncardbattle.components.GameControls
import androidx.compose.foundation.background


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
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 12.dp, vertical = 100.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Display the scoreboard for all three players.
        Scoreboard(
            players = players,
            playerHands = playerHands
        )

        Spacer(modifier = Modifier.height(70.dp))

        // Battle area.
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.TopCenter
        ) {
            BattleArea(
                leadType = leadType,
                visiblePlayedCards = visiblePlayedCards
            )
        }

        // Current player
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Display the current player's hand.
            if (!waitingForNextPlayer && turnWinner == null) {

                PlayerHand(
                    player = currentPlayer,
                    cards = currentHand,
                    errorMessage = errorMessage,
                    onCardSelected = { card ->

                        // Prevent players from playing more than one card per turn.
                        if (currentPlayer !in game.playedCards) {

                            // Validate the selected card using our existing game logic.
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

            // Display turn results and final game results.
            GameResults(
                turnWinner = turnWinner,
                gameWinners = gameWinners,
                players = players
            )

            // Display navigation and game progression buttons.
            GameControls(
                showNextPlayer = waitingForNextPlayer &&
                        currentPlayerIndex < turnOrder.lastIndex,

                showResult = waitingForNextPlayer &&
                        currentPlayerIndex == turnOrder.lastIndex &&
                        turnWinner == null,

                showNextTurn = turnWinner != null &&
                        players.any { it.hand.isNotEmpty() },

                onNextPlayer = {
                    // Move to the next player.
                    currentPlayerIndex++
                    waitingForNextPlayer = false
                    errorMessage = null
                },

                onShowResult = {
                    // Calculate the winner and update the score.
                    turnWinner = game.determineTurnWinner()

                    // Check whether all players have finished their cards.
                    if (players.all { it.hand.isEmpty() }) {
                        gameWinners = game.determineGameWinner(players)
                    }
                },

                onNextTurn = {
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
                },

                onBack = onBack
            )

            Spacer(modifier = Modifier.height(32.dp))

        }
    }
}