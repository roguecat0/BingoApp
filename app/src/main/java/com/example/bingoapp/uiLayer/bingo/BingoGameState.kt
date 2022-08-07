package com.example.bingoapp.uiLayer.bingo

import com.example.bingoapp.uiLayer.models.BingoGameUI
import com.example.bingoapp.uiLayer.models.isTextBig

data class BingoGameState(
    val bingoGame: BingoGameUI = BingoGameUI(),
    val textIsBig: Boolean = bingoGame.isTextBig()
)
