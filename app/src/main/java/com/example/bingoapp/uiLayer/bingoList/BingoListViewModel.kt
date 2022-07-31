package com.example.bingoapp.uiLayer.bingoList

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.bingoapp.dataLayer.dataSource.BingoOption

import com.example.bingoapp.dataLayer.repository.BingoListRepository

class BingoListViewModel(

) : ViewModel() {
    private val repository = BingoListRepository()
    var state by  mutableStateOf(BingoListState())
        private set



//    business logic
    private fun getBingoList(){
        state = BingoListState(repository.getBingoList())
    }
    fun getBingoInfo(id: Int) : BingoOption {
        return repository.getBingoInfo(id)
    }
    init{
        getBingoList()
    }


}