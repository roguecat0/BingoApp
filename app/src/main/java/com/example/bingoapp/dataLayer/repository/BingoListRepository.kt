package com.example.bingoapp.dataLayer.repository

import com.example.bingoapp.dataLayer.dataSource.BingoOption
import com.example.bingoapp.dataLayer.dataSource.ListItems
import com.example.bingoapp.dataLayer.dataSource.toUI
import com.example.bingoapp.uiLayer.models.BingoOptionUI

class BingoListRepository {
    fun getBingoList() : List<BingoOptionUI>{
        return ListItems.BingoList.map { it.toUI() }
    }
    fun getBingoInfo(id: Int) : BingoOption{
        return ListItems.getBingoInfo(id)
    }
}