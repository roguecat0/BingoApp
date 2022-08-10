package com.example.bingoapp.common

import com.example.bingoapp.data.dataSource.Item

data class BingoItem(
    val name: String,
    val pressed: Boolean = false
){
    fun toItem(bid: Int) : Item =
        Item(
            name = name,
            bid = bid
        )
}
