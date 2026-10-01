# Overview

I created a networking system for my Pokémon Card Battle Android app. The program uses a Kotlin server and an Android client. The client can send requests to the server and display the responses in the app.

To use the program, first start the Kotlin server. Then start the Android app on the emulator. On the Rules screen, you can press different buttons to request information from the server.

Purpose of this project was to learn how client-server communication works and how to use networking in an Android application.

# Network Communication

The program uses a client-server architecture. The Kotlin program is the server, and the Android application is the client.

The program uses TCP sockets and port 5000.

The client sends simple text requests to the server. The server reads the request and sends a text response back to the Android app.

# Development Environment

I used Android Studio to develop and test the project. I also used an Android emulator to run the mobile application.

The project is written in Kotlin. I used Java socket classes such as ServerSocket and Socket for network communication and Jetpack Compose for the Android user interface.

# Useful Websites

* [Client–server model](https://en.wikipedia.org/wiki/Client%E2%80%93server_model)
* [Lesson: All About Sockets](https://docs.oracle.com/javase/tutorial/networking/sockets/index.html)

# Future Work

* Send more game information between the client and server.
* Improve connection error handling.
* Use networking as part of the actual Pokémon battles.