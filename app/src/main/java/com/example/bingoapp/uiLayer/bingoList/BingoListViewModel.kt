package com.example.bingoapp.uiLayer.bingoList

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.bingoapp.dataLayer.repository.BingoRepository

class BingoListViewModel(

) : ViewModel() {
    private val repository = BingoRepository()
    var state by  mutableStateOf(BingoListState())
        private set



//    business logic
    private fun getBingoList(){
        state = BingoListState(repository.getBingoList())
    }
    fun deleteBingo(id: Int){
        repository.deleteBingo(id)
        getBingoList()
    }
    fun getBingoShareData(id: Int) : String {
        return repository.getBingoShareData(id)
    }
    fun switchExpanded(id: Int){
        val list = state.bingoList.map {
            if (state.bingoList.indexOf(it) == id) it.copy(menu = !it.menu)
            else it
        }
        state = state.copy(bingoList = list)
    }
    fun expandedOff(id: Int){
        val list = state.bingoList.map {
            if (state.bingoList.indexOf(it) == id) it.copy(menu = false)
            else it
        }
        state = state.copy(bingoList = list)
    }
    init{
        getBingoList()
    }


}