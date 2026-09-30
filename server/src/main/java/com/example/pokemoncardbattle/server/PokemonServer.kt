package com.example.pokemoncardbattle.server

import java.net.ServerSocket

fun main() {
    // Create a TCP server
    val serverSocket = ServerSocket(5000)

    println("Pokemon server started on port 5000")
    println("Waiting for a client...")

    // Wait until a client connects
    val clientSocket = serverSocket.accept()

    println("Client connected!")

    clientSocket.close()
    serverSocket.close()
}