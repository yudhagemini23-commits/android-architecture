package com.ina17.android_architecture

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ina17.android_architecture.features.hero.presentation.HeroDetailScreen
import com.ina17.android_architecture.features.hero.presentation.HeroScreen
import com.ina17.android_architecture.features.home.presentation.HomeScreen
import com.ina17.android_architecture.ui.theme.AndroidarchitectureTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidarchitectureTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        val navController = rememberNavController()

                        NavHost(navController = navController, startDestination = "home") {
                            composable("home") {
                                HomeScreen(
                                    onNavigate = {
                                        navController.navigate("hero_list")
                                    }
                                )
                            }
                            composable("hero_list") {
                                HeroScreen(
                                    onHeroClick = { heroId ->
                                        navController.navigate("hero_detail/$heroId")
                                    }
                                )
                            }
                            composable(
                                route = "hero_detail/{heroId}",
                                arguments = listOf(navArgument("heroId") {
                                    type = NavType.IntType
                                })
                            ) {
                                HeroDetailScreen(
                                    onBackClick = {
                                        navController.popBackStack()
                                    }
                                )
                            }

                        }
                    }
                }
            }
        }
    }

    @Composable
    fun Greeting(name: String, modifier: Modifier = Modifier) {
        Text(
            text = "Hello $name!",
            modifier = modifier
        )
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        AndroidarchitectureTheme {
            Greeting("Android")
        }
    }
}