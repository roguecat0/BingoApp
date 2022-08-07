package com.example.bingoapp.uiLayer

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.example.bingoapp.uiLayer.bingo.BingoGame
import com.example.bingoapp.uiLayer.bingoList.BingoList
import com.example.bingoapp.uiLayer.bingoMaker.BingoMaker
import com.example.compose.BingoAppTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BingoApp(
    shareBingo: (String) -> Unit
){
    BingoAppTheme {
        val navController = rememberNavController()
        val backstackEntry = navController.currentBackStackEntryAsState()
        var heartState by remember { mutableStateOf(false)}
        Surface(
            color = MaterialTheme.colorScheme.surfaceVariant,
            modifier = Modifier.fillMaxSize()
        ){
            Scaffold(
                topBar = {
                    MediumTopAppBar(
                        title = {Text("")},
                        navigationIcon = {
                            IconButton(
                                onClick = { heartState = !heartState }
                            ) {
                                Icon(imageVector = if (heartState) Icons.Filled.Favorite
                                    else Icons.Filled.FavoriteBorder,
                                    contentDescription = "",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    )

                }
            ) { innerPadding ->
                BingoNavHost(
                    navController = navController,
                    modifier = Modifier.padding(innerPadding),
                    shareBingo = shareBingo
                )
            }

        }


    }

}
@Composable
fun BingoNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    shareBingo: (String) -> Unit
){
    NavHost(navController = navController,
    startDestination = Screen.BingoListScreen.name,
    modifier = modifier){
        composable(Screen.BingoListScreen.name){
            BingoList(
                onItemClick = { id: Int ->
                    navigateToBingoGame(navController,id) },
                onFabClick = {navController
                    .navigate("${Screen.BingoMakerScreen.name}/")},
                editBingo = {id: Int -> navigateToEditMaker(navController,id)},
                shareBingo = shareBingo
            )
        }
        val uri = "https://www.bingo.be"
        composable(
            route = "${Screen.BingoMakerScreen.name}/?BingoId={BingoId}",
            arguments = listOf(
                navArgument("BingoId") {
                    defaultValue = -1
                    type = NavType.IntType },
                navArgument("bingoValues"){
                    defaultValue = ""
                    type = NavType.StringType
                }
            ),
            deepLinks = listOf(navDeepLink { uriPattern = "$uri/{bingoValues}" })
        ) {

            BingoMaker(navToList = {navController.navigate(Screen.BingoListScreen.name)})
        }
        val bingoGameName = Screen.BingoGameScreen.name
        composable(
            route = "$bingoGameName/{BingoId}",
            arguments = listOf(
                navArgument("BingoId") {
                    type = NavType.IntType
                }
            )) {
            val bingoID: Int = it.arguments?.getInt("BingoId") ?: 2
            BingoGame(navToList = {navController.navigate(Screen.BingoListScreen.name)})

        }
    }

}

private fun navigateToBingoGame(navController: NavHostController, id: Int) {
    val bingoGameName = Screen.BingoGameScreen.name
    Log.i("nav game","${bingoGameName}/$id")
    navController.navigate("${bingoGameName}/$id")
}
private fun navigateToEditMaker(navController: NavHostController, id: Int) {
    Log.i("nav maker","${Screen.BingoMakerScreen.name}/?BingoId=$id")
    navController.navigate("${Screen.BingoMakerScreen.name}/?BingoId=$id")
}

@Preview(showBackground = true,
    widthDp = 300,
    heightDp = 700)
@Composable
fun AppPreview(){
    BingoApp({})
}