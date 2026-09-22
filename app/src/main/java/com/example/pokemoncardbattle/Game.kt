package com.example.pokemoncardbattle

class Game {

    // For storing cards currently in play
    val playedCards = mutableMapOf<Player, PokemonCard>()

    // Creates the player order beginning with the selected starting player
    fun getTurnOrder(players: List<Player>, startingPlayer: Player): List<Player> {
        val startIndex = players.indexOf(startingPlayer)
        val playersFromStart = players.drop(startIndex)
        val playersBeforeStart = players.take(startIndex)
        val turnOrder = playersFromStart + playersBeforeStart

        return turnOrder
    }

    // Checks if the selected card follows the lead-type rule
    fun isValidCard(player: Player, card: PokemonCard, leadType: String?): Boolean {

        // Check if the player has leading type pokemon
        val hasLeadType = leadType != null && player.hand.any { it.type == leadType }

        // Check if the card actually exists in the player's hand
        if (card !in player.hand) {
            return false
        }

        // Check if the card is a legal move
        if (hasLeadType && card.type != leadType) {
            return false
        }

        return true
    }

    // Removes the selected card from the player's hand and stores it in play
    fun playCard(player: Player, card: PokemonCard, leadType: String?): Boolean {

        if (!isValidCard(player, card, leadType)) {
            return false
        }

        player.hand.remove(card)
        playedCards[player] = card

        return true
    }
    // Determines the winner of the current turn and updates their score
    fun determineTurnWinner(): Player {
        // first played card
        val firstCard = playedCards.values.first()

        // get the lead type from that first card
        val leadType = firstCard.type

        // temporary winner
        var winningPlayer = playedCards.keys.first()
        var winningCard = firstCard

        // compare cards
        for ((player, card) in playedCards) {
            // don't compare first card to itself
            if (card == firstCard) {
                continue
            }

            // Check if the new card is strong against the lead type
            val cardBeatsLead = isStrongAgainst(card.type, leadType)

            // Check if the current winning card is strong against the lead type
            val winningCardBeatsLead = isStrongAgainst(winningCard.type, leadType)

            // A card that is strong against the lead type beats a lead-type card
            if (cardBeatsLead && !winningCardBeatsLead) {
                winningPlayer = player
                winningCard = card
            }

            // If both cards are strong against the lead type, higher HP wins
            else if (cardBeatsLead && card.hp > winningCard.hp) {
                winningPlayer = player
                winningCard = card
            }

            // If neither card beats the lead type, only lead-type cards can compete by HP
            else if (
                !cardBeatsLead &&
                !winningCardBeatsLead &&
                card.type == leadType &&
                winningCard.type == leadType &&
                card.hp > winningCard.hp
            ) {
                winningPlayer = player
                winningCard = card
            }
        }

        // Increase winner's score
        winningPlayer.score += 10
        return winningPlayer
    }

    // Checks whether one pokemon type is strong against another
    fun isStrongAgainst(attackingType: String, defendingType: String): Boolean {
        // compare types
        val strengths = mapOf(
            "Electric" to listOf("Water"),
            "Water" to listOf("Fire", "Rock", "Ground"),
            "Fire" to listOf("Grass", "Bug"),
            "Grass" to listOf("Water", "Ground", "Rock"),
            "Ground" to listOf("Electric", "Fire", "Poison", "Rock"),
            "Rock" to listOf("Fire", "Bug"),
            "Fighting" to listOf("Normal", "Rock"),
            "Psychic" to listOf("Fighting", "Poison"),
            "Ghost" to listOf("Psychic", "Ghost"),
            "Poison" to listOf("Grass", "Fairy"),
            "Bug" to listOf("Grass", "Psychic"),
            "Fairy" to listOf("Fighting")
        )

        // return T or F
        return defendingType in (strengths[attackingType] ?: emptyList())
    }

    // Finds the player or players with the highest final score
    fun determineGameWinner(players: List<Player>): List<Player> {
        // finds highest score among all
        val highestScore = players.maxOf { it.score }

        // finds the player/players with that score
        val winners = players.filter { it.score == highestScore }

        return winners
    }
}