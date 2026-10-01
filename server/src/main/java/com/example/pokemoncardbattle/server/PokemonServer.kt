package com.example.pokemoncardbattle.server

import java.net.ServerSocket

fun main() {
    // Create a TCP server
    val serverSocket = ServerSocket(5000)

    println("Pokemon server started on port 5000")

    // Keep the server running and wait for client requests
    while (true) {

        println("Waiting for a client...")

        val clientSocket = serverSocket.accept()

        println("Client connected!")

        // Read the request sent by the client
        val reader = clientSocket.getInputStream().bufferedReader()
        val request = reader.readLine()

        println("Received request: $request")

        // Choose response based on client's request
        val response = when (request) {

            "GET_OBJECTIVE" ->
                "Win battles and finish the game with the highest score."

            "GET_PLAYING" ->
                "Each player receives 5 Pokemon cards. Players take turns playing one card."

            "GET_FOLLOW_TYPE" ->
                "The first card sets the lead type. If you have a card of that type, you must play it."

            "GET_TYPES" ->
                "Type advantage is checked first. If there is no type advantage, HP determines the winner."

            "GET_SCORING" ->
                "The winner of each battle earns 10 points. After 5 battles, the player with the highest score wins!"

            else ->
                "Unknown request"
        }

        // Send the response back to the client
        val writer = clientSocket.getOutputStream().bufferedWriter()
        writer.write(response)
        writer.newLine()
        writer.flush()

        println("Response sent: $response")

        // Close this client connection, but keep the server running
        clientSocket.close()
    }
}