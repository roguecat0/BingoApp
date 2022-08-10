package com.example.bingoapp.uiLayer.bingo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bingoapp.common.BingoItem
import com.example.bingoapp.data.repository.BingoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BingoGameViewModel @Inject constructor(
    private val repository: BingoRepository,
    private  val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val id: Int = savedStateHandle["BingoId"]?:0
    var state by  mutableStateOf(BingoGameState())
        private set
//    business logic
    private fun setGameState(id: Int) {
        viewModelScope.launch {
            val bi = repository.getBingoInfo(id)
            state = BingoGameState(bi)
        }
    }
    fun switchPressed(item: BingoItem){
        val i = state.bingoGame.items.indexOf(item)
        val list = state.bingoGame.items.map {
            if (state.bingoGame.items.indexOf(it) == i) item.copy(pressed = !item.pressed)
            else it
        }
        val game = state.bingoGame.copy(items = list)
        state = state.copy(bingoGame = game)
    }
    fun checkComplete(){
        val complete = repository.checkComplete(state.bingoGame)
        val game = state.bingoGame.copy(complete = complete)
        state = state.copy(bingoGame = game)
    }
    fun resetGame(){
        val list = state.bingoGame.items.map { it.copy(pressed = false)}
        val game = state.bingoGame.copy(complete = false, items = list)
        state = state.copy(bingoGame = game)
    }
    fun completeOff(){
        val game = state.bingoGame.copy(complete = false)
        state = state.copy(bingoGame = game)
    }
    init{
        setGameState(id)
    }


}