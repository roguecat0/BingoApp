package com.example.bingoapp.uiLayer.bingoMaker

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bingoapp.common.BingoDimention
import com.example.bingoapp.uiLayer.bingo.components.BingoSettings
import com.example.bingoapp.uiLayer.bingoMaker.components.WordItems
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun BingoMaker(
    navToList: ()->Unit,
    viewModel: BingoMakerViewModel = hiltViewModel()
){

    val focusManager = LocalFocusManager.current
    Box{
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ){
            Text(
                text = viewModel.state.bingoGame.name,
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
                    isError = viewModel.state.nameError,
                    clearFocus = {focusManager.clearFocus()},
                    changeExpanded = {
                        viewModel.changeExpanded()},
                    disableExpanded = {
                        viewModel.disableExpanded()},
                    setDimension = {
                            dim: BingoDimention ->
                        viewModel.setDimensions(dim)},
                    switchRandom = {viewModel.switchRandom()}
                )

            } else{
                WordItems(
                    items = viewModel.state.bingoGame.items.map{it.name},
                    name = viewModel.state.tempItem,
                    size = viewModel.state.bingoGame.size.dim,
                    isError = viewModel.state.itemError,
                    editName = {name: String -> viewModel.editTempName(name)},
                    addItem = {viewModel.addItem()},
                    deleteItem = {item -> viewModel.deleteItem(item)},
                    clearFocus = {focusManager.clearFocus()}
                )
            }
        }
        ButtonRow(
            settings = viewModel.state.settings,
            navToList = navToList,
            addBingo = {viewModel.tryAddBingo()},
            switchSettings = {viewModel.switchSettings()}
        )

    }
}
@Composable
fun ButtonRow(
    settings: Boolean,
    navToList: () -> Unit,
    addBingo: () -> Boolean,
    switchSettings: () -> Unit
) {
    val coroutineScope = CoroutineScope(Dispatchers.Main)
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
                    coroutineScope.launch(){
                        if (addBingo()){
                            delay(30)
                            navToList()
                        }

                    }},
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
                    imageVector = if (settings) Icons.Default.ArrowForward
                        else Icons.Default.ArrowBack,
                    contentDescription = null,
                    modifier = Modifier.padding(end = 4.dp)
                )
                Text(text= if (settings) "Next" else "Previous")
            }
        }
    }

}