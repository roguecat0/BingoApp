package com.example.bingoapp.common

enum class GameMode {
    Classic,Corners,Experimental,Full;
    companion object {
        fun fromSize(bingo: BingoDimention): GameMode =
            when (bingo.dim[0]==bingo.dim[1]) {
                true -> Classic
                false -> Corners
            }
        fun fromName(mode: String): GameMode =
            when (mode) {
                "Classic" -> Classic
                "Corners" -> Corners
                "Full" -> Full
                else -> throw IllegalArgumentException("Bingo is not recognized.")
            }
    }
}
