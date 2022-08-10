package com.example.bingoapp.uiLayer.bingo.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bingoapp.common.BingoDimention

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BingoSettings(
    name: String, expanded: Boolean, dim: BingoDimention,
    editName: (String) -> Unit,
    clearFocus: () -> Unit,
    changeExpanded: () -> Unit, disableExpanded: () -> Unit,
    setDimension: (BingoDimention) -> Unit,
    random: Boolean, switchRandom: () -> Unit,
    isError: Boolean
) {
    Column {
        OutlinedTextField(
            value = name,
            onValueChange = {
                editName(it) },
            label = { Text("Bingo Name") },
            singleLine = true,
            isError = isError,
            keyboardActions = KeyboardActions(onDone = {clearFocus()})
        )
        Spacer(Modifier.height(15.dp))
        DropDownTextField(
            expanded = expanded,
            dim = dim,
            changeExpanded = changeExpanded,
            disableExpanded = disableExpanded,
            setDimension = setDimension
        )
        Spacer(Modifier.height(15.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.align(Alignment.End)
        ){
            Text(text = "Randomize Bingo:")
            Spacer(Modifier.width(70.dp))
            Switch(
                checked = random,
                onCheckedChange = {switchRandom()})
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDownTextField(expanded: Boolean, dim: BingoDimention,
                      changeExpanded: () -> Unit, disableExpanded: () -> Unit,
                      setDimension: (BingoDimention) -> Unit){
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            changeExpanded()}
    ) {
        OutlinedTextField(value = dim.dimStr,
            onValueChange = { },
            readOnly = true,
            label = { Text("Pick Dim") },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )},
            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors())
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                disableExpanded() }
        ) {
            BingoDimention.values().forEach { dimension ->
                DropdownMenuItem(
                    text = {Text(text= dimension.dimStr)},
                    onClick = { setDimension(dimension)
                        disableExpanded()}
                )
            }
        }
    }
}
@Composable
fun MakerButton(text: String, onClick: ()->Unit){
    Button(onClick = {onClick()}) {
        Text(text = text)
    }
}
@Preview(showBackground = true,
    widthDp = 200,
    heightDp = 400)
@Composable
fun BingoSettingsPreview() {
    Row{
        MakerButton(text = "add", onClick = {})
        MakerButton(text = "Finish", onClick = {})
    }

}