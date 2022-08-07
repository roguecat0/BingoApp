package com.example.bingoapp.uiLayer.bingoMaker.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WordItems(
    name: String,
    items: List<String>,
    editName: (String) -> Unit,
    addItem: () -> Unit,
    deleteItem: (String) -> Unit,
    clearFocus: () -> Unit
){
    OutlinedTextField(
        value = name,
        onValueChange = {
            editName(it) },
        label = { Text("Add bingo Items") },
        singleLine = true,
//      keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(
            onDone = {
                if(name.isNotEmpty())
                    addItem()
                else clearFocus()
            }
        )
    )
    LazyColumn(
        contentPadding = PaddingValues(vertical = 10.dp)
    ){
        items(items.reversed()) { item ->
            WordItem(item = item, deleteItem = deleteItem)
        }
        item(){
            Spacer(Modifier.height(70.dp))
        }
    }
}
@Composable
fun WordItem(item: String, deleteItem: (String) -> Unit){
    OutlinedCard(modifier = Modifier
        .padding(8.dp)
        .requiredWidth(220.dp)) {
        Row(modifier = Modifier
            .fillMaxSize()
            .align(Alignment.End)){
            Text(
                text = item,
                modifier = Modifier
                    .padding(top = 14.dp, bottom = 13.dp, start = 17.dp, end = 13.dp)
                    .fillMaxHeight()
                    .wrapContentHeight(Alignment.CenterVertically)
                    .width(130.dp),
                textAlign = TextAlign.Left,
                style = MaterialTheme.typography.labelLarge
            )
            TextButton(onClick = { deleteItem(item) }) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = null,
                    modifier = Modifier.padding(end = 0.dp)
                )
            }
        }

    }

}