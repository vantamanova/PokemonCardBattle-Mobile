plugins {
    id("java-library")
    alias(libs.plugins.jetbrains.kotlin.jvm)
    id("application")
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11
    }
}

application {
    mainClass.set("com.example.pokemoncardbattle.server.PokemonServerKt")
}