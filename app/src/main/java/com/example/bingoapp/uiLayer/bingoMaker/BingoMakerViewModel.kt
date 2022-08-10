package com.example.bingoapp.uiLayer.bingoMaker

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bingoapp.common.BingoDimention
import com.example.bingoapp.common.BingoItem
import com.example.bingoapp.common.GameMode
import com.example.bingoapp.data.repository.BingoRepository
import com.example.bingoapp.uiLayer.bingo.BingoMakerState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BingoMakerViewModel @Inject constructor(
    private val repository: BingoRepository,
    private  val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val id: Int = savedStateHandle["BingoId"]?:-1
    private val bingoValues: String = savedStateHandle["bingoValues"]?:""
    var state by mutableStateOf(BingoMakerState())
        private set

//    business logic
    private fun editMakerState(id: Int) {
        viewModelScope.launch {
            val bi = repository.getBingoInfo(id)
            state = BingoMakerState(bingoGame = bi, edit=true)
        }

    }
    private fun createNewState(){
        state = BingoMakerState()
    }
    private fun createStateFromImport(){
        state = BingoMakerState(bingoGame = repository.importToBingo(bingoValues))
    }
    fun editName(name: String){
        val game = state.bingoGame.copy(name = name)
        state = state.copy(bingoGame = game)
        state = state.copy(nameError = isNameError())
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
    fun tryAddBingo() : Boolean {
        if (isNameError() && isItemError())
            state = state.copy(settings = true,
                nameError = true,
                itemError = true
            )
        else if (isNameError())
            state = state.copy(settings = true,
                nameError = true
            )
        else if (isItemError())
            state = state.copy(settings = false,
                itemError = true
            )
        else {
            addBingo()
            return true
        }
        return false
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
    fun isNameError(): Boolean = state.bingoGame.name.isEmpty()
    fun isItemError() : Boolean {
        val len = state.bingoGame.size.dim[0]*state.bingoGame.size.dim[1]
        Log.i("item","len: $len, size : ${state.bingoGame.items.size}")
        return len != state.bingoGame.items.size
    }

    fun editTempName(tmp: String){
        state = state.copy(tempItem = tmp)
    }
    fun addItem(){
        val newList = state.bingoGame.items.toMutableList()
        newList.add(BingoItem(state.tempItem))

        val game = state.bingoGame.copy(items = newList.toList())
        state = state.copy(bingoGame = game, tempItem = "")
        state = state.copy(itemError = isItemError())
    }
    fun deleteItem(item: String){
        val newList = state.bingoGame.items.toMutableList()
        newList.removeIf { bingoItem : BingoItem -> bingoItem.name == item }
        val game = state.bingoGame.copy(items = newList.toList())
        state = state.copy(bingoGame = game, tempItem = "")
        state = state.copy(itemError = isItemError())
    }
    init{
        if(id==-1){
            if(bingoValues.isEmpty()){
                createNewState()
            } else{
                createStateFromImport()
            }
        } else {
            editMakerState(id)
        }
    }
}