package com.example.bingoapp.uiLayer.models

import com.example.bingoapp.common.BingoDimention
import com.example.bingoapp.common.BingoItem
import com.example.bingoapp.common.GameMode
import com.example.bingoapp.dataLayer.dataSource.BingoData

data class BingoGameUI(
    val name: String = "",
    val id: Int = -1,
    val size: BingoDimention = BingoDimention.d2b2,
    val items: List<BingoItem> = emptyList(),
    var random: Boolean = true,
    var complete: Boolean = false,
    val mode: GameMode = GameMode.fromSize(size)
)
fun BingoGameUI.toData(): BingoData {
    return BingoData(
        name = name,
        size = size,
        id = id,
        items = items,
        random = random,
        complete = complete,
        mode = mode
    )
}
fun BingoGameUI.isTextBig(): Boolean = size.dim[0]*size.dim[1]>=20
