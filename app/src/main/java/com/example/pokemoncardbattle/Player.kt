package com.example.pokemoncardbattle

// Describes one player
class Player(
    val id: String,
    val name: String,
    val email: String? = null,
    val hand: MutableList<PokemonCard> = mutableListOf(),
    var score: Int = 0
) {
}

// Creates players from a list of player names
fun createPlayers(playerNames: List<String>): List<Player> {
    return playerNames.mapIndexed { index, name ->
        Player(
            id = "p${index + 1}",
            name = name
        )
    }
}

// Randomly chooses the player who starts the game
fun chooseStartingPlayer(players: List<Player>): Player {
    return players.random()
}