package com.example.bingoapp.uiLayer.bingo

import com.example.bingoapp.uiLayer.models.BingoGameUI

data class BingoMakerState(
    val bingoGame: BingoGameUI = BingoGameUI(),
    val settings: Boolean = true,
    val expandedMenu: Boolean = false,
    val edit: Boolean = false,
    val tempItem: String = "",
    val nameError: Boolean = false,
    val itemError: Boolean = false
)
