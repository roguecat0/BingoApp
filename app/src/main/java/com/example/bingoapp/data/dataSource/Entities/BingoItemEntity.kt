package com.example.bingoapp.data.dataSource.Entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.bingoapp.common.BingoItem

@Entity(
    tableName = "bingoItemList",
    foreignKeys = [
        ForeignKey(
            entity = BingoEntity::class,
            childColumns = ["bid"],
            parentColumns = ["id"]
)])
data class BingoItemEntity(
    @PrimaryKey(autoGenerate = true)
    var iid: Long = 0L,
    var name: String = "",
    var bid: Int = 0
){
    fun toBingoItem(): BingoItem =
        BingoItem(
            name = name
        )

}
