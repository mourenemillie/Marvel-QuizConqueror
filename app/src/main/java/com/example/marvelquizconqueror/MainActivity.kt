package com.example.marvelquizconqueror

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.marvelquizconqueror.core.theme.MarvelQuizConquerorTheme
import com.example.marvelquizconqueror.domain.model.Question
import com.example.marvelquizconqueror.domain.model.Quest
import com.example.marvelquizconqueror.domain.model.User
import com.example.marvelquizconqueror.presentation.character.HeroSelectionScreen
import com.example.marvelquizconqueror.presentation.character.ProfileScreen
import com.example.marvelquizconqueror.presentation.home.LoginScreen
import com.example.marvelquizconqueror.presentation.home.ShopScreen
import com.example.marvelquizconqueror.presentation.home.WorldSelectionScreen
import com.example.marvelquizconqueror.presentation.quest.QuestListScreen
import com.example.marvelquizconqueror.presentation.quest.QuizScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideSystemBars()
        enableEdgeToEdge()
        setContent {
            MarvelQuizConquerorTheme {
                MarvelQuestApp()
            }
        }
    }

    private fun hideSystemBars() {
        val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)
        windowInsetsController.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
    }
}

@Composable
fun MarvelQuestApp() {
    val navController = rememberNavController()
    val context = LocalContext.current

    // Mock Data State
    var user by remember {
        mutableStateOf(
            User(
                name = "AVENGER",
                level = 1,
                xp = 0,
                coins = 150,
                energy = 3
            )
        )
    }

    val mockQuests = listOf(
        Quest("1", "01", "KOTA TERKUNCI", 1, listOf("EASY"), imageResId = R.drawable.tempat_newyork),
        Quest("2", "02", "LABORATORIUM RAHASIA", 2, listOf("MEDIUM"), imageResId = R.drawable.tempat_lab),
        Quest("3", "03", "HUTAN TERLARANG", 3, listOf("HARD"), imageResId = R.drawable.tempat_hutan),
        Quest("4", "04", "KASTEL DOOM", 2, listOf("MEDIUM"), imageResId = R.drawable.tempat_kastel),
        Quest("5", "05", "MARKAS PLANET", 4, listOf("EXPERT"), imageResId = R.drawable.tempat_planet),
        Quest("6", "06", "GERBANG GALAXY", 5, emptyList(), isSuperHard = true, imageResId = R.drawable.tempat_galaxy)
    )

    val mockQuestions = listOf(
        Question("1", "Siapa identitas asli Iron Man?", listOf("Tony Stark", "Steve Rogers", "Bruce Banner", "Thor"), 0, "General"),
        Question("2", "Apa senjata utama Thor?", listOf("Perisai", "Mjolnir", "Busur Panah", "Web Shooter"), 1, "General"),
        Question("3", "Siapa musuh bebuyutan Captain America di Perang Dunia II?", listOf("Thanos", "Loki", "Red Skull", "Ultron"), 2, "History"),
        Question("4", "Warna batu Mind Stone adalah?", listOf("Merah", "Biru", "Kuning", "Hijau"), 2, "Items"),
        Question("5", "Siapa raja dari Wakanda?", listOf("T'Challa", "M'Baku", "Erik Killmonger", "W'Kabi"), 0, "Geography"),
        Question("6", "Siapa paman Spider-Man yang memberikan pesan 'Power and Responsibility'?", listOf("Uncle Sam", "Uncle Ben", "Uncle Bob", "Uncle Phil"), 1, "Characters"),
        Question("7", "Apa nama planet asal Thanos?", listOf("Earth", "Titan", "Xandar", "Sakaar"), 1, "Cosmic"),
        Question("8", "Siapa anggota Avengers yang ahli memanah?", listOf("Hawkeye", "Black Widow", "Falcon", "Ant-Man"), 0, "Characters"),
        Question("9", "Apa nama AI yang diciptakan Tony Stark sebelum JARVIS?", listOf("FRIDAY", "EDITH", "JARVIS", "Homer"), 2, "Tech"),
        Question("10", "Siapa yang menghancurkan perisai Captain America di Endgame?", listOf("Loki", "Thanos", "Ultron", "Hela"), 1, "Movies")
    )

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(onNavigateToHeroSelection = { navController.navigate("hero") })
        }
        composable("hero") {
            HeroSelectionScreen(user = user, onNavigate = { route -> navController.navigate(route) })
        }
        composable("dunia") {
            WorldSelectionScreen(
                user = user,
                onNavigate = { route -> navController.navigate(route) },
                onWorldClick = { navController.navigate("quest_list") }
            )
        }
        composable("quest_list") {
            QuestListScreen(
                user = user,
                quests = mockQuests,
                onQuestClick = { quest ->
                    if (!quest.isLocked) {
                        navController.navigate("quiz/${quest.id}")
                    }
                },
                onNavigate = { route -> navController.navigate(route) }
            )
        }
        composable(
            "quiz/{questId}",
            arguments = listOf(navArgument("questId") { type = NavType.StringType })
        ) { backStackEntry ->
            val questId = backStackEntry.arguments?.getString("questId")
            val quest = mockQuests.find { it.id == questId } ?: mockQuests[0]
            
            QuizScreen(
                user = user,
                questTitle = quest.title,
                imageResId = quest.imageResId,
                questions = mockQuestions.shuffled().take(5),
                onFinish = { score, coinsGained ->
                    user = user.copy(
                        xp = user.xp + (score * 2),
                        coins = user.coins + coinsGained,
                        level = (user.xp + (score * 2)) / 500 + 1
                    )
                    navController.popBackStack()
                },
                onRevive = {
                    if (user.coins >= 50) {
                        user = user.copy(coins = user.coins - 50)
                        Toast.makeText(context, "Nyawa dipulihkan! -50 Coins", Toast.LENGTH_SHORT).show()
                        true
                    } else {
                        Toast.makeText(context, "Coins tidak cukup untuk Top Up!", Toast.LENGTH_SHORT).show()
                        false
                    }
                },
                onClose = { navController.popBackStack() }
            )
        }
        composable("profil") {
            ProfileScreen(user = user, onNavigate = { route -> navController.navigate(route) })
        }
        composable("toko") {
            ShopScreen(
                user = user,
                onBuyLives = { count, cost ->
                    if (user.coins >= cost) {
                        user = user.copy(
                            coins = user.coins - cost,
                            energy = user.energy + count
                        )
                        Toast.makeText(context, "Berhasil membeli $count nyawa!", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Coins tidak cukup!", Toast.LENGTH_SHORT).show()
                    }
                },
                onNavigate = { route -> navController.navigate(route) }
            )
        }
    }
}
