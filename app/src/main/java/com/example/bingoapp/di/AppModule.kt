package com.example.bingoapp.di

import android.app.Application
import androidx.room.Room
import com.example.bingoapp.data.dataSource.AppRoomDataBase
import com.example.bingoapp.data.repository.BingoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideWordInfoRepository(
        db: AppRoomDataBase
    ): BingoRepository {
        return BingoRepository(db.bingoDao)
    }

    @Provides
    @Singleton
    fun provideWordInfoDatabase(app: Application): AppRoomDataBase {
        return Room.databaseBuilder(
            app, AppRoomDataBase::class.java, "bingo_db"
        ).fallbackToDestructiveMigration()
            .build()
    }
}