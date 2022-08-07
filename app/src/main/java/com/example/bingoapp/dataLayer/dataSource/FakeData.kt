package com.example.bingoapp.dataLayer.dataSource

import android.util.Log
import com.example.bingoapp.common.BingoDimention
import com.example.bingoapp.common.BingoItem
import com.example.bingoapp.common.GameMode
import com.example.bingoapp.uiLayer.models.BingoGameUI
import com.example.bingoapp.uiLayer.models.BingoOptionUI

data class BingoData(
    val name: String,
    val id: Int,
    val size: BingoDimention,
    val items: List<BingoItem>,
    val random: Boolean = true,
    val complete: Boolean = false,
    val mode: GameMode = GameMode.fromSize(size)
)
fun BingoData.toOptionUI(): BingoOptionUI {
    return BingoOptionUI(
        name = name,
        size = size.dimStr,
        id = id
    )
}
fun BingoData.toGameUI(): BingoGameUI {
    return BingoGameUI(
        name = name,
        size = size,
        id = id,
        items = items,
        random = random,
        complete = complete,
        mode = mode
    )
}
fun BingoData.toBingoValues(): String {
    var bingoStr = "$name-4-${size.dimStr}-4-$random-4-${mode.name}-4-"
    items.forEach(){
        bingoStr += "${it.name}&X"
    }
    return bingoStr.dropLast(2).replace(" ","_Q")
}

object ListItems {
    val BingoList: MutableList<BingoData> = mutableListOf(
        BingoData(
            "movies",
            0,
            BingoDimention.d3b3,
            listOf(
                BingoItem("a"),
                BingoItem("b"),
                BingoItem("c"),
                BingoItem("d"),
                BingoItem("e"),
                BingoItem("f"),
                BingoItem("g"),
                BingoItem("h"),
                BingoItem("i"))
        ),
        BingoData(
            "makers",
            1,
            BingoDimention.d2b2,
            listOf(BingoItem("a"),
                BingoItem("b"),
                BingoItem("c"),
                BingoItem("d"))
        ),
        BingoData(
            "games",
            2,
            BingoDimention.d2b3,
            listOf(
                BingoItem("hi my friend"),
                BingoItem("fuck"),
                BingoItem("breakin the wall in the summer"),
                BingoItem("what"),
                BingoItem("are you doing"),
                BingoItem("f")
            )
        )
    )
    fun getBingoInfo(id: Int): BingoData {
        Log.i("data1","id: $id")

        val bd = BingoList.first() { it.id == id }
        Log.i("data2","id: $bd")
        return bd
    }
}

