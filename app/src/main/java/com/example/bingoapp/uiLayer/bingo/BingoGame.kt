package com.example.bingoapp.uiLayer.bingo

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bingoapp.common.BingoItem

@Composable
fun BingoGame(
    viewModel: BingoGameViewModel = viewModel(),
    navToList: ()-> Unit){
    val state: BingoGameState = viewModel.state
    Log.i("game",state.bingoGame.items.map { it.pressed }.toString())
    Box{
        GameUI(
            rows = state.bingoGame.size.dim[1],
            itemList = state.bingoGame.items,
            switchPressed = {item -> viewModel.switchPressed(item)},
            checkComplete = {viewModel.checkComplete()},
            isBig = viewModel.state.textIsBig
        )
        if(state.bingoGame.complete){
            CompleteAlert(
                resetGame = { viewModel.resetGame() },
                completeOff = { viewModel.completeOff() },
                backToBingoList = navToList
            )
        }
    }
}

@Composable
fun GameUI(
    rows: Int,
    itemList: List<BingoItem>,
    switchPressed: (BingoItem)->Unit,
    checkComplete: ()-> Unit,
    isBig: Boolean
) {

    LazyVerticalGrid(columns = GridCells.Fixed(rows),
        contentPadding = PaddingValues(8.dp)){
        items(itemList){ item ->
            BingoPick(item,switchPressed,checkComplete,isBig)
        }
    }
}
@Composable
fun BingoPick(
    item: BingoItem,
    switchPressed: (BingoItem)->Unit,
    checkComplete: ()-> Unit,
    isBig: Boolean

){
    OutlinedCard(
        colors = CardDefaults.
        cardColors(containerColor =
        if (!item.pressed) MaterialTheme.colorScheme.surfaceVariant
        else MaterialTheme.colorScheme.tertiaryContainer),
        modifier = Modifier
            .requiredHeight(120.dp)
            .clickable {
                switchPressed(item)
                checkComplete()}
            .padding(3.dp)


    ){
        Text(
            text = item.name,
            style = if(isBig) MaterialTheme.typography.bodySmall
                else MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxSize()
                .wrapContentHeight(Alignment.CenterVertically)
                .padding(
                    start = 3.dp,
                    end = 3.dp,
                    top = 10.dp,
                    bottom = 10.dp
                )
        )
    }
}

@Composable
fun CompleteAlert(
    resetGame: () -> Unit,
    completeOff: () -> Unit,
    backToBingoList: () -> Unit
) {
    AlertDialog(
        onDismissRequest = {completeOff() },
        title = {
            Text(text = "You Won!")
        },
        text = {
            Text("click on background to stay on screen")
        },
        confirmButton = {
            Button(
                onClick = {
                    resetGame()
                }) {
                Text("restart Game")
            }
        },
        dismissButton = {
            Button(

                onClick = {
                    backToBingoList()
                }) {
                Text("return to menu")
            }
        }
    )
}
@Preview(widthDp = 200, heightDp = 600)
@Composable
fun BingoPickPreview(){
    BingoPick(
        item = BingoItem("yolo"),
        switchPressed = {it},
        checkComplete = {},
        isBig = true
    )
}

@Preview(heightDp = 850, widthDp = 390, showBackground = true)
@Composable
fun BingoGamePreview(){
    Surface(
        color = MaterialTheme.colorScheme.surface
    ) {
        val li = listOf(
            "hello","goodbye","always snows",
            "fuck","what","am I doing","today",
            "nothing I guess","your right").map { BingoItem(it) }
        GameUI(rows = 3,
            itemList = li,
            switchPressed = {it},
            checkComplete = {},
            isBig = false
        )
    }


}