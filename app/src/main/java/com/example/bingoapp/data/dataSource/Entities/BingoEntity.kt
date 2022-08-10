package com.example.bingoapp.data.dataSource.Entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.bingoapp.uiLayer.models.BingoOptionUI

@Entity(tableName = "bingoList")
data class BingoEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    val name: String = "",
    val size: String = "",
    val random: Boolean = true,
    val mode: String = ""
){
    fun toOptionUI() : BingoOptionUI =
        BingoOptionUI(
            name = name,
            size = size,
            id = id
        )
}