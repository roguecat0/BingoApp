package com.example.bingoapp.uiLayer.models

import com.example.bingoapp.common.BingoDimention
import com.example.bingoapp.common.BingoItem
import com.example.bingoapp.common.GameMode
import com.example.bingoapp.data.dataSource.Entities.BingoEntity

data class BingoGameUI(
    val name: String = "",
    val id: Int = -1,
    val size: BingoDimention = BingoDimention.d2b2,
    val items: List<BingoItem> = emptyList(),
    var random: Boolean = true,
    var complete: Boolean = false,
    val mode: GameMode = GameMode.fromSize(size)
)
fun BingoGameUI.isTextBig(): Boolean = size.dim[0]*size.dim[1]>=20

fun BingoGameUI.toEntity(): BingoEntity {
    return BingoEntity(
        name = name,
        size = size.dimStr,
        id = id,
        random = random,
        mode = mode.name
    )
}
fun BingoGameUI.toBingoValues(): String {
    var bingoStr = "$name-4-${size.dimStr}-4-$random-4-${mode.name}-4-"
    items.forEach(){
        bingoStr += "${it.name}&X"
    }
    return bingoStr.dropLast(2).replace(" ","_Q")
}
