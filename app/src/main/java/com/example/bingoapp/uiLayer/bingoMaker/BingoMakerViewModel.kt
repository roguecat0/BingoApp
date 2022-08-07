package com.example.bingoapp.uiLayer.bingoMaker

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.bingoapp.common.BingoDimention
import com.example.bingoapp.common.BingoItem
import com.example.bingoapp.common.GameMode
import com.example.bingoapp.dataLayer.repository.BingoRepository
import com.example.bingoapp.uiLayer.bingo.BingoMakerState
import com.example.bingoapp.uiLayer.models.BingoGameUI

class BingoMakerViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val id: Int = savedStateHandle["BingoId"]?:-1
    private val repository = BingoRepository()
    var state by mutableStateOf(BingoMakerState())
        private set



//    business logic
    private fun getBingoInfo(id: Int) : BingoGameUI {
        return repository.getBingoInfo(id)
    }
    private fun editMakerState(id: Int) {
        state = BingoMakerState(bingoGame = getBingoInfo(id), edit=true)
    }
    private fun createNewState(){
        state = BingoMakerState()
    }
    fun editName(name: String){
        val game = state.bingoGame.copy(name = name)
        state = state.copy(bingoGame = game)
    }
    fun disableExpanded(){
        state = state.copy(expandedMenu = false)
    }
    fun changeExpanded(){
        state = state.copy(expandedMenu = !state.expandedMenu)
    }
    fun switchRandom(){
        val game = state.bingoGame.copy(random = !state.bingoGame.random)
        state = state.copy(bingoGame = game)
    }
    fun switchSettings(){
        state = state.copy(settings = !state.settings)
    }
    fun setDimensions(dim: BingoDimention){
        val game = state.bingoGame.copy(size = dim, mode = GameMode.fromSize(dim))
        state = state.copy(bingoGame = game)
    }
    fun addBingo(){
        if(state.bingoGame.random){
            val newList = state.bingoGame.items.toMutableList()
            newList.shuffle()
            val game = state.bingoGame.copy(items = newList.toList())
            state = state.copy(bingoGame = game, tempItem = "")
        }

        if(state.edit){
            repository.editBingo(state.bingoGame)
        }

        else{
            repository.addBingo(state.bingoGame)
        }

    }
    fun editTempName(tmp: String){
        state = state.copy(tempItem = tmp)
    }
    fun addItem(){
        val newList = state.bingoGame.items.toMutableList()
        newList.add(BingoItem(state.tempItem))
        val game = state.bingoGame.copy(items = newList.toList())
        state = state.copy(bingoGame = game, tempItem = "")
    }
    fun deleteItem(item: String){
        val newList = state.bingoGame.items.toMutableList()
        newList.removeIf { bingoItem : BingoItem -> bingoItem.name == item }
        val game = state.bingoGame.copy(items = newList.toList())
        state = state.copy(bingoGame = game, tempItem = "")
    }
    init{
        Log.i("view1","id: $id")

        if(id==-1){
            createNewState()
            Log.i("viewNew",state.toString())
        } else {
            editMakerState(id)
            Log.i("viewEdit",state.toString())
        }
    }
}