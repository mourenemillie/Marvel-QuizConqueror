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
        Quest("1", "01", "KOTA TERKUNCI", 1, listOf("EASY"), imageResId = R.drawable.tempat_newyork, villainImageId = R.drawable.red_skull),
        Quest("2", "02", "LABORATORIUM RAHASIA", 2, listOf("MEDIUM"), imageResId = R.drawable.tempat_lab, villainImageId = R.drawable.dr_doom),
        Quest("3", "03", "HUTAN TERLARANG", 3, listOf("HARD"), imageResId = R.drawable.tempat_hutan, villainImageId = R.drawable.hela),
        Quest("4", "04", "KASTEL DOOM", 2, listOf("MEDIUM"), imageResId = R.drawable.tempat_kastel, villainImageId = R.drawable.mysterio),
        Quest("5", "05", "MARKAS PLANET", 4, listOf("EXPERT"), imageResId = R.drawable.tempat_planet, villainImageId = R.drawable.kang_the_conqueror),
        Quest("6", "06", "GERBANG GALAXY", 5, emptyList(), isSuperHard = true, imageResId = R.drawable.tempat_galaxy, villainImageId = R.drawable.thanos)
    )

    val mockQuestions = listOf(
        Question("1", "Apa satuan internasional (SI) untuk besaran suhu?", listOf("Celsius", "Fahrenheit", "Kelvin", "Reamur"), 2, "Fisika"),
        Question("2", "Bagian sel yang berfungsi sebagai tempat respirasi sel adalah?", listOf("Inti sel", "Mitokondria", "Ribosom", "Lisosom"), 1, "Biologi"),
        Question("3", "Perubahan wujud zat dari gas menjadi padat disebut?", listOf("Mencair", "Menguap", "Mengkristal", "Menyublim"), 2, "Kimia"),
        Question("4", "Planet yang dijuluki sebagai planet merah adalah?", listOf("Venus", "Mars", "Jupiter", "Saturnus"), 1, "Astronomi"),
        Question("5", "Alat yang digunakan untuk mengukur tekanan udara adalah?", listOf("Termometer", "Higrometer", "Barometer", "Anemometer"), 2, "Fisika"),
        Question("6", "Enzim yang berfungsi mengubah amilum menjadi glukosa di dalam mulut adalah?", listOf("Pepsin", "Ptialin", "Lipase", "Renin"), 1, "Biologi"),
        Question("7", "Zat yang menyebabkan warna hijau pada daun disebut?", listOf("Hemoglobin", "Klorofil", "Melanin", "Karoten"), 1, "Biologi"),
        Question("8", "Bunyi yang frekuensinya lebih dari 20.000 Hz disebut?", listOf("Infrasonik", "Audiosonik", "Ultrasonik", "Supersonik"), 2, "Fisika"),
        Question("9", "Simbol kimia untuk unsur besi adalah?", listOf("Au", "Ag", "Fe", "Cu"), 2, "Kimia"),
        Question("10", "Hubungan antara dua makhluk hidup yang saling menguntungkan disebut?", listOf("Simbiosis Parasitisme", "Simbiosis Komensalisme", "Simbiosis Mutalisme", "Predasi"), 2, "Biologi"),
        Question("11", "Proses penguapan air melalui pori-pori daun tumbuhan disebut?", listOf("Fotosintesis", "Transpirasi", "Respirasi", "Oksidasi"), 1, "Biologi"),
        Question("12", "Hukum yang menyatakan bahwa gaya berbanding lurus dengan massa dan percepatan adalah?", listOf("Hukum Newton I", "Hukum Newton II", "Hukum Newton III", "Hukum Archimedes"), 1, "Fisika"),
        Question("13", "Asam lambung (HCl) berfungsi untuk?", listOf("Mencerna lemak", "Membunuh kuman", "Mengaktifkan ptyalin", "Menyerap air"), 1, "Biologi"),
        Question("14", "Benda yang dapat ditarik kuat oleh magnet disebut?", listOf("Paramagnetik", "Diamagnetik", "Feromagnetik", "Nonmagnetik"), 2, "Fisika"),
        Question("15", "Lapisan atmosfer yang paling dekat dengan bumi adalah?", listOf("Stratosfer", "Mesosfer", "Troposfer", "Eksosfer"), 2, "Astronomi")
    )

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(onNavigateToHeroSelection = { navController.navigate("hero") })
        }
        composable("hero") {
            HeroSelectionScreen(
                user = user,
                onHeroSelected = { hero ->
                    user = user.copy(selectedHeroImageId = hero.imageResId)
                },
                onNavigate = { route -> navController.navigate(route) }
            )
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
                villainImageId = quest.villainImageId,
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
