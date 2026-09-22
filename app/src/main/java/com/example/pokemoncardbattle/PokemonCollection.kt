package com.example.pokemoncardbattle

import android.content.Context

// Responsible for creating a Pokemon collection

// Loads the Pokemon collection from the CSV file
fun loadPokemon(context: Context): List<PokemonCard> {

    val lines = context.assets.open("pokemon.csv")
        .bufferedReader()
        .use { it.readLines() }

    val dataLines = lines.drop(1)

    val pokemonCards = mutableListOf<PokemonCard>()

    for (line in dataLines) {
        val values = line.split(",")

        val pokemon = PokemonCard(
            number = values[0].toInt(),
            name = values[1],
            hp = values[2].toInt(),
            type = values[3],
            image = values[4]
        )

        pokemonCards.add(pokemon)
    }

    return pokemonCards
}

