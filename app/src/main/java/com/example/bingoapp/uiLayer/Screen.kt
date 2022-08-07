package com.example.bingoapp.uiLayer

enum class Screen {
    BingoListScreen(),
    BingoMakerScreen(),
    BingoGameScreen();

    companion object{
        fun fromRoute(route : String): Screen =
            when (route?.substringBefore("/")) {
                BingoListScreen.name -> BingoListScreen
                BingoMakerScreen.name -> BingoMakerScreen
                BingoGameScreen.name -> BingoGameScreen
                null -> BingoListScreen
                else -> throw IllegalArgumentException("Route $route is not recognized.")
            }
    }
}