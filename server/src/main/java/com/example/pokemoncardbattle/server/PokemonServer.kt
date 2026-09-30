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

    // Read the request sent by the client
    val reader = clientSocket.getInputStream().bufferedReader()
    val request = reader.readLine()

    println("Received request: $request")

    // Send a response back to the client
    val writer = clientSocket.getOutputStream().bufferedWriter()

    writer.write("HELLO CLIENT")
    writer.newLine()
    writer.flush()

    println("Response sent: HELLO CLIENT")

    clientSocket.close()
    serverSocket.close()
}