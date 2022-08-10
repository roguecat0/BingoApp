package com.example.bingoapp.uiLayer.bingoList

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bingoapp.data.repository.BingoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BingoListViewModel @Inject constructor(
    private val repository: BingoRepository
) : ViewModel() {
    var state by  mutableStateOf(BingoListState())
        private set

//    business logic
    private fun getBingoList(){
        viewModelScope.launch {
            state = BingoListState(repository.getBingoList())
        }
    }
    fun deleteBingo(id: Int){
        viewModelScope.launch {
            repository.deleteBingo(id)
            delay(20)
            state = BingoListState(repository.getBingoList())
        }
    }
    fun shareData(id: Int, shareBingo: (String) -> Unit) {
        viewModelScope.launch {
            val data: String = repository.getBingoShareData(id)
            delay(30)
            shareBingo(data)
        }
    }
    fun switchExpanded(id: Int){
        val list = state.bingoList.map {
            if (it.id == id) it.copy(menu = !it.menu)
            else it
        }
        state = state.copy(bingoList = list)
    }
    fun expandedOff(id: Int){
        getBingoList()
    }
    init{
        getBingoList()
    }


}