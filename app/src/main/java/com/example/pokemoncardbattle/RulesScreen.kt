package com.example.pokemoncardbattle

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pokemoncardbattle.components.BackToHomeButton
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import kotlin.concurrent.thread
import androidx.activity.ComponentActivity
import com.example.pokemoncardbattle.components.RuleRequestButton

// Displays the game rules and instructions.
@Composable
fun RulesScreen(
    onBack: () -> Unit
) {
    var serverResponse by remember {
        mutableStateOf("")
    }

    var selectedRequest by remember {
        mutableStateOf<String?>(null)
    }

    val context = LocalContext.current

    // Sends a request to the server and receives the response.
    fun requestFromServer(request: String) {
        selectedRequest = request
        serverResponse = "Loading..."

        thread {
            try {
                val client = PokemonClient()
                val response = client.connectToServer(request)

                (context as ComponentActivity).runOnUiThread {
                    serverResponse = response
                }
            } catch (e: Exception) {
                (context as ComponentActivity).runOnUiThread {
                    serverResponse = "Could not connect to server."
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 28.dp, vertical = 150.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Screen title.
        Text(
            text = "HOW TO PLAY",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(28.dp))

        // Game rules.
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {

            RuleRequestButton(
                title = "OBJECTIVE",
                response = if (selectedRequest == "GET_OBJECTIVE") serverResponse else null,
                onClick = { requestFromServer("GET_OBJECTIVE") }
            )

            Spacer(modifier = Modifier.height(10.dp))

            RuleRequestButton(
                title = "PLAYING A CARD",
                response = if (selectedRequest == "GET_PLAYING") serverResponse else null,
                onClick = { requestFromServer("GET_PLAYING") }
            )

            Spacer(modifier = Modifier.height(10.dp))

            RuleRequestButton(
                title = "FOLLOW THE TYPE",
                response = if (selectedRequest == "GET_FOLLOW_TYPE") serverResponse else null,
                onClick = { requestFromServer("GET_FOLLOW_TYPE") }
            )

            Spacer(modifier = Modifier.height(10.dp))

            RuleRequestButton(
                title = "TYPE ADVANTAGE",
                response = if (selectedRequest == "GET_TYPES") serverResponse else null,
                onClick = { requestFromServer("GET_TYPES") }
            )

            Spacer(modifier = Modifier.height(10.dp))

            RuleRequestButton(
                title = "SCORING",
                response = if (selectedRequest == "GET_SCORING") serverResponse else null,
                onClick = { requestFromServer("GET_SCORING") }
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        BackToHomeButton(
            onBack = onBack
        )
    }
}