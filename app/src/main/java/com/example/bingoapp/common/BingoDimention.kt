package com.example.bingoapp.common

enum class BingoDimention(
    val dim: List<Int>,
    val dimStr: String) {
    d2b2(listOf(2,2),"2x2"),
    d2b3(listOf(2,3),"2x3"),
    d3b3(listOf(3,3),"3x3"),
    d3b4(listOf(3,4),"3x4"),
    d4b4(listOf(4,4),"4x4"),
    d4b5(listOf(4,5),"4x5"),
    d5b5(listOf(5,5),"5x5");
    companion object {
        fun fromDim(dim: String): BingoDimention =
            when(dim){
                "2x2" -> d2b2
                "2x3" -> d2b3
                "3x3" -> d3b3
                "3x4" -> d3b4
                "4x4" -> d4b4
                "4x5" -> d4b5
                "5x5" -> d5b5
                else -> throw IllegalArgumentException("Bingo is not recognized.")
            }
    }
}