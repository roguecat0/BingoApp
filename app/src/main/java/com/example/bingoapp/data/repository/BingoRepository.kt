package com.example.bingoapp.data.repository

import com.example.bingoapp.common.BingoDimention
import com.example.bingoapp.common.BingoItem
import com.example.bingoapp.common.GameMode
import com.example.bingoapp.data.dataSource.BingoDao
import com.example.bingoapp.data.dataSource.Entities.BingoEntity
import com.example.bingoapp.uiLayer.models.BingoGameUI
import com.example.bingoapp.uiLayer.models.BingoOptionUI
import com.example.bingoapp.uiLayer.models.toBingoValues
import com.example.bingoapp.uiLayer.models.toEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BingoRepository(
    private val bingoDao: BingoDao
    ) {

    // data base interaction
    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    fun addBingo(bingo: BingoGameUI) {
        coroutineScope.launch(Dispatchers.IO) {
            bingoDao.deleteBingoItems(bingo.id)
            bingoDao.addNewBingo(
                name = bingo.name,
                size = bingo.size.dimStr,
                random = bingo.random,
                mode = bingo.mode.name
            )
            val id = bingoDao.getMaxId()?:1
            bingoDao.addBingoItems(bingo.items.map { it.toItem(id) })

        }
    }
    fun deleteBingo(id: Int){
        coroutineScope.launch(Dispatchers.IO) {
            bingoDao.deleteBingoItems(id)
            bingoDao.deleteBingoById(id)
        }
    }
    suspend fun getBingoList() : List<BingoOptionUI> = withContext(Dispatchers.IO){
        bingoDao.getListBingos().map { it.toOptionUI() }
    }
    suspend fun getBingoInfo(id: Int) : BingoGameUI = withContext(Dispatchers.IO){
        val bingo = bingoDao.getBingoById(id)?: BingoEntity()
        val items = bingoDao.getBingoItems(id)
        BingoGameUI(
            name = bingo.name,
            id = bingo.id,
            size = BingoDimention.fromDim(bingo.size),
            items = items.map { it.toBingoItem() },
            random = bingo.random
        )
    }
    fun editBingo(bingo: BingoGameUI){
        coroutineScope.launch(Dispatchers.IO) {
            bingoDao.deleteBingoItems(bingo.id)
            bingoDao.updateBingo(bingo.toEntity())
            bingoDao.addBingoItems(bingo.items.map { it.toItem(bingo.id) })
        }
    }
    fun checkComplete(bingo: BingoGameUI) : Boolean =
        when(bingo.mode){
            GameMode.Classic -> classic(bingo)
            GameMode.Corners -> corners(bingo)
            else -> throw IllegalArgumentException("I dunno man")

        }

    private fun full(bingo: BingoGameUI): Boolean{
        val boolMap = bingo.items.map { it.pressed }
        boolMap.forEach(){
            if(!it) return false
        }
        return true
    }
    private fun classic(bingo: BingoGameUI): Boolean{
        val max = bingo.size.dim[0]-1
        val lines = createLines(max)
        lines.forEach(){ line ->
            for(i in line){
                if(!isCoordinatePressed(
                        bingo.items.map { bool ->
                            bool.pressed }
                        ,i, bingo.size.dim[1])){
                    break
                } else if (line.last() == i){
                    return true
                }
            }
        }
        return false
    }
    private fun createLines(max: Int): List<List<List<Int>>>{
        val lines = mutableListOf<List<List<Int>>>()
        val crossUp = mutableListOf<List<Int>>()
        val crossDown = mutableListOf<List<Int>>()
        for (i in 0..max){
            crossDown.add(listOf(i,i))
            crossUp.add(listOf(max-i,i))
            val tmpX = mutableListOf<List<Int>>()
            val tmpY = mutableListOf<List<Int>>()
            for(j in 0..max){
                tmpX.add(listOf(i,j))
                tmpY.add(listOf(j,i))
            }
            lines.add(tmpX)
            lines.add(tmpY)

        }
        lines.add(crossUp)
        lines.add(crossDown)
        return lines.toList()
    }
    private fun corners(bingo: BingoGameUI): Boolean{
        val size = bingo.size.dim.map { it-1 }
        val corners = listOf(
            listOf(0,0), listOf(0,size[1]),
            listOf(size[0],0), size
        )
        corners.forEach(){
            if(!isCoordinatePressed(
                    bingo.items.map { bool ->
                        bool.pressed },it, bingo.size.dim[1])) return false
        }
        return true
    }
    private fun isCoordinatePressed(
        boolMap: List<Boolean>, index: List<Int>,width: Int,
    ): Boolean{
        val i1d = index[0]*width+index[1]
        return boolMap[i1d]
    }
    suspend fun getBingoShareData(id: Int) : String {
        val bingo = getBingoInfo(id)
        return bingo.toBingoValues()
    }
    fun importToBingo(bingoValues: String) : BingoGameUI {
        val bList = bingoValues.replace("_Q"," ").split("-4-")
        val iList = bList.last().split("&X")
        return BingoGameUI(
            name = bList[0],
            size = BingoDimention.fromDim(bList[1]),
            random = bList[2].toBoolean(),
//         mode = GameMode.fromName(bList(3)),
            items = iList.map(){item -> BingoItem(name = item) }
        )
    }


}