package com.example.bingoapp.uiLayer.bingoMaker

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bingoapp.common.BingoDimention
import com.example.bingoapp.uiLayer.bingo.components.BingoSettings
import com.example.bingoapp.uiLayer.bingoMaker.components.WordItems


@Composable
fun BingoMaker(
    viewModel: BingoMakerViewModel = viewModel(),
    navToList: ()->Unit){
    val focusManager = LocalFocusManager.current
    Box{
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ){
            Text(
                text = "${viewModel.state.bingoGame.name}",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(30.dp))
            if(viewModel.state.settings){
                BingoSettings(name = viewModel.state.bingoGame.name,
                    expanded = viewModel.state.expandedMenu,
                    dim = viewModel.state.bingoGame.size,
                    editName = {name: String -> viewModel.editName(name)},
                    random = viewModel.state.bingoGame.random,
                    clearFocus = {focusManager.clearFocus()},
                    changeExpanded = {
                        Log.i("maker","change")
                        viewModel.changeExpanded()},
                    disableExpanded = {
                        Log.i("maker","disable")
                        viewModel.disableExpanded()},
                    setDimension = {
                            dim: BingoDimention ->
                        Log.i("maker","set dim")
                        viewModel.setDimensions(dim)},
                    switchRandom = {viewModel.switchRandom()}
                )

            } else{
                WordItems(
                    items = viewModel.state.bingoGame.items.map{it.name},
                    name = viewModel.state.tempItem,
                    editName = {name: String -> viewModel.editTempName(name)},
                    addItem = {viewModel.addItem()},
                    deleteItem = {item -> viewModel.deleteItem(item)},
                    clearFocus = {focusManager.clearFocus()},
                )
            }
        }
        ButtonRow(
            settings = viewModel.state.settings,
            navToList = navToList,
            addBingo = {viewModel.addBingo()},
            switchSettings = {viewModel.switchSettings()}
        )

    }
}
@Composable
fun ButtonRow(
    settings: Boolean,
    navToList: () -> Unit,
    addBingo: () -> Unit,
    switchSettings: () -> Unit
) {
    Box(
        contentAlignment = Alignment.BottomEnd,
        modifier = Modifier.fillMaxSize(),
    ){
        Row(modifier = Modifier
            .align(Alignment.BottomEnd)
            .padding(20.dp),

        ){
            Button(
                onClick = {
                    navToList()
                    addBingo()},
//                colors = ButtonDefaults.outlinedButtonColors(containerColor = MaterialTheme.colorScheme.surface),
                modifier = Modifier.padding(8.dp)) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    modifier = Modifier.padding(end = 4.dp)
                )
                Text(text="Add")
            }
            OutlinedButton(
                onClick = { switchSettings() },
                colors = ButtonDefaults
                    .outlinedButtonColors(containerColor = MaterialTheme.colorScheme.surface),
                modifier = Modifier.padding(8.dp).width(140.dp)) {
                Icon(
                    imageVector = if (settings) Icons.Default.Send
                        else Icons.Default.Close,
                    contentDescription = null,
                    modifier = Modifier.padding(end = 4.dp)
                )
                Text(text= if (settings) "Next" else "Previous")
            }
        }
    }

}