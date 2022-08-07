package com.example.bingoapp.common

enum class GameMode {
    Classic,Corners,Experimental;
    companion object {
        fun fromSize(bingo: BingoDimention): GameMode =
            when (bingo.dim[0]==bingo.dim[1]) {
                true -> Classic
                false -> Corners
                else -> throw IllegalArgumentException("Bingo is not recognized.")
            }
    }
}
