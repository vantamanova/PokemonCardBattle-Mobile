package com.example.pokemoncardbattle

import android.util.Log
import java.net.Socket

class PokemonClient {

    fun connectToServer(request: String): String {
        Log.d("PokemonNetwork", "Trying to connect...")

        // Connect to the server through the ADB reverse port
        val socket = Socket("127.0.0.1", 5000)

        Log.d("PokemonNetwork", "Connected to Pokemon server!")

        // Send request to the server
        val writer = socket.getOutputStream().bufferedWriter()
        writer.write(request)
        writer.newLine()
        writer.flush()

        // Receive response from the server
        val reader = socket.getInputStream().bufferedReader()
        val response = reader.readLine()

        Log.d("PokemonNetwork", "Received response: $response")

        socket.close()
        return response
    }
}