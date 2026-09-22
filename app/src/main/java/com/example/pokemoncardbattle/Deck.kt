package com.example.pokemoncardbattle

// Functions responsible for creating, shuffling, and dealing the game deck

// Creates a game deck from the Pokemon collection
fun createDeck(pokemonCollection: List<PokemonCard>): MutableList<PokemonCard> {
    val deck = pokemonCollection.toMutableList()

    return deck
}

// Randomizes the order of cards in the deck
fun shuffleDeck(deck: MutableList<PokemonCard>) {
    deck.shuffle()
}

// Deals the specified number of cards to each player's hand
// Maybe change it in the future
fun dealCards(
    deck: MutableList<PokemonCard>,
    players: List<Player>,
    cardsPerPlayer: Int = 5
) {
    require(deck.size >= players.size * cardsPerPlayer) {
        "Not enough cards in the deck."
    }

    repeat(cardsPerPlayer) {
        for (player in players) {
            val card = deck.removeAt(0)
            player.hand.add(card)
        }
    }
}