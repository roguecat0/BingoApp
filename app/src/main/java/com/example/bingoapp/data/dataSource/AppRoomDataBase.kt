package com.example.bingoapp.data.dataSource

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.bingoapp.data.dataSource.Entities.BingoEntity
import com.example.bingoapp.data.dataSource.Entities.BingoItemEntity

@Database(entities =
    [(BingoEntity::class), (BingoItemEntity::class)],
    version = 1
)
abstract class AppRoomDataBase: RoomDatabase() {

    abstract val bingoDao: BingoDao
    //.fallbackToDestructiveMigration()

}