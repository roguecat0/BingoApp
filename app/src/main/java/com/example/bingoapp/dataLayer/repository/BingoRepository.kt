package com.example.bingoapp.dataLayer.repository

import com.example.bingoapp.common.GameMode
import com.example.bingoapp.dataLayer.dataSource.BingoData
import com.example.bingoapp.dataLayer.dataSource.ListItems
import com.example.bingoapp.dataLayer.dataSource.toGameUI
import com.example.bingoapp.dataLayer.dataSource.toOptionUI
import com.example.bingoapp.uiLayer.models.BingoGameUI
import com.example.bingoapp.uiLayer.models.BingoOptionUI
import com.example.bingoapp.uiLayer.models.toData

class BingoRepository {
    fun getBingoList() : List<BingoOptionUI>{
        return ListItems.BingoList.map { it.toOptionUI() }.toList()
    }
    fun getBingoInfo(id: Int) : BingoGameUI{
        return ListItems.getBingoInfo(id).toGameUI()
    }
    fun addBingo(bingo: BingoGameUI){
        val bd = bingo.copy(id = getMaxId()+1).toData()
        ListItems.BingoList.add(bd)

    }
    fun editBingo(bingo: BingoGameUI){
        deleteBingo(bingo.id)
        ListItems.BingoList.add(bingo.toData())
    }
    fun deleteBingo(id: Int){
        ListItems.BingoList.removeIf { b: BingoData -> b.id == id }
    }
    fun getMaxId() : Int{
        val idList = ListItems.BingoList.map { it.id }
        return idList.maxOrNull()?:0
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
}