package com.example.pokemoncardbattle

import android.util.Log
import java.net.Socket

class PokemonClient {

    fun connectToServer() {
        Log.d("PokemonNetwork", "Trying to connect...")

        // 10.0.2.2 lets the Android emulator connect to the host computer
        val socket = Socket("127.0.0.1", 5000)

        Log.d("PokemonNetwork", "Connected to Pokemon server!")

        socket.close()
    }
}