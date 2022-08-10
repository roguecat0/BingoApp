package com.example.bingoapp.data.dataSource

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.bingoapp.data.dataSource.Entities.BingoEntity
import com.example.bingoapp.data.dataSource.Entities.BingoItemEntity

@Dao
interface BingoDao {
    @Query("SELECT * FROM bingoList")
    suspend fun getListBingos() : List<BingoEntity>

    @Query("SELECT * FROM bingoList WHERE id = :id")
    suspend fun getBingoById(id:Int): BingoEntity?

    @Query("SELECT MAX(id) FROM bingoList")
    suspend fun getMaxId(): Int?

    @Query("DELETE FROM bingoList WHERE id = :id")
    suspend fun deleteBingoById(id:Int)

    @Query("SELECT * FROM bingoItemList WHERE bid = :id")
    suspend fun getBingoItems(id:Int): List<BingoItemEntity>

    @Query("DELETE FROM bingoItemList WHERE bid = :id")
    suspend fun deleteBingoItems(id:Int)

    @Query("INSERT INTO bingoList(name,size,random,mode) VALUES(:name,:size,:random,:mode)")
    suspend fun addNewBingo(name: String, size: String,random: Boolean,mode:String)

    @Insert(entity = BingoItemEntity::class)
    suspend fun addBingoItems(items : List<Item>)

    @Update
    suspend fun updateBingo(bingoEntity: BingoEntity)
}