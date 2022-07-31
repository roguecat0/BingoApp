package com.example.bingoapp.dataLayer.dataSource

import com.example.bingoapp.uiLayer.models.BingoOptionUI

data class BingoOption(
    val name: String,
    val id: Int,
    val size: List<Int>
)
fun BingoOption.toUI(): BingoOptionUI {
    return BingoOptionUI(
        name = name,
        size = size
    )
}

object ListItems {
    val BingoList: List<BingoOption> = listOf(
        BingoOption(
            "movies",
            0,
            listOf(3,3)
        ),
        BingoOption(
            "makers",
            1,
            listOf(4,4)
        )
    )
    fun getBingoInfo(id: Int): BingoOption {
        return BingoList.first() { it.id == id }
    }
}

