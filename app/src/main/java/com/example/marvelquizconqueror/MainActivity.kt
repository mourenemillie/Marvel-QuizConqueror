package com.example.marvelquizconqueror

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.marvelquizconqueror.core.theme.MarvelQuizConquerorTheme
import com.example.marvelquizconqueror.domain.model.Quest
import com.example.marvelquizconqueror.domain.model.User
import com.example.marvelquizconqueror.presentation.character.HeroSelectionScreen
import com.example.marvelquizconqueror.presentation.home.LoginScreen
import com.example.marvelquizconqueror.presentation.quest.QuestListScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MarvelQuizConquerorTheme {
                MarvelQuestApp()
            }
        }
    }
}

@Composable
fun MarvelQuestApp() {
    val navController = rememberNavController()

    // Mock Data based on Figma design
    val mockUser = User(
        name = "RAGAH MUJAHIDIN",
        level = 45,
        xp = 12450,
        coins = 120,
        energy = 80
    )

    val mockQuests = listOf(
        Quest("1", "01", "PINGGIRAN KOTA", 3, listOf("SD", "SMP", "SMA"), isCompleted = true),
        Quest("2", "02", "TEROWONGAN KERETA", 2, listOf("SD", "SMP", "SMA"), isCompleted = true),
        Quest("3", "03", "STREET FIGHT", 3, emptyList(), isSuperHard = true),
        Quest("4", "04", "KERIBUTAN DI ATAP", 0, listOf("SD", "SMP", "SMA"), isLocked = true),
        Quest("5", "05", "SELAMATKAN ORANG", 0, listOf("SD", "SMP", "SMA"), isLocked = true),
        Quest("6", "06", "SIDE QUEST", 0, listOf("SD", "SMP", "SMA"), isLocked = true)
    )

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(
                onNavigateToHeroSelection = {
                    navController.navigate("hero")
                }
            )
        }
        composable("hero") {
            HeroSelectionScreen(
                user = mockUser,
                onNavigate = { route ->
                    if (route == "quest_list") {
                        navController.navigate("quest_list")
                    } else if (route == "hero" || route == "dunia" || route == "toko" || route == "profil") {
                        // Navigation from bottom bar, optionally handle it
                    }
                }
            )
        }
        composable("quest_list") {
            QuestListScreen(
                user = mockUser,
                quests = mockQuests
            )
        }
    }
}