package com.example.marvelquizconqueror.presentation.quest

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.marvelquizconqueror.R
import com.example.marvelquizconqueror.core.theme.*
import com.example.marvelquizconqueror.domain.model.Question
import com.example.marvelquizconqueror.domain.model.User

@Composable
fun QuizScreen(
    user: User,
    questTitle: String,
    imageResId: Int?,
    villainImageId: Int?,
    questions: List<Question>,
    onFinish: (Int, Int) -> Unit,
    onRevive: () -> Boolean, // Fungsi untuk memproses pembayaran topup
    onClose: () -> Unit
) {
    var currentQuestionIndex by remember { mutableStateOf(0) }
    var score by remember { mutableStateOf(0) }
    var lives by remember { mutableStateOf(3) }
    var villainHealth by remember { mutableStateOf(100f) }
    var isGameOver by remember { mutableStateOf(false) }
    var isVictory by remember { mutableStateOf(false) }

    val currentQuestion = questions.getOrNull(currentQuestionIndex)
    val damagePerQuestion = 100f / questions.size

    if (isGameOver || isVictory) {
        QuizResultScreen(
            user = user,
            isVictory = isVictory,
            score = score,
            coinsGained = if (isVictory) 20 else 0,
            xpGained = if (isVictory) 100 else 20,
            onClose = { 
                if (isVictory) onFinish(score, 20) else onClose()
            },
            onRevive = {
                if (onRevive()) {
                    lives = 3
                    isGameOver = false
                }
            }
        )
    } else {
        Scaffold(
            containerColor = BackgroundDark,
            topBar = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onClose) {
                        Icon(Icons.Default.Close, contentDescription = "Close", tint = TextWhite)
                    }
                    Text(
                        text = questTitle.uppercase(),
                        color = RedPrimary,
                        fontWeight = FontWeight.Black,
                        fontSize = 18.sp
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        // User Hero Image
                        Image(
                            painter = painterResource(id = user.selectedHeroImageId ?: R.drawable.hero_figure),
                            contentDescription = "Selected Hero",
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                                .border(1.dp, GoldYellow, CircleShape),
                            contentScale = ContentScale.Crop
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        repeat(3) { index ->
                            Icon(
                                imageVector = Icons.Default.Favorite,
                                contentDescription = null,
                                tint = if (index < lives) Color.Red else Color.Gray,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
            }
        ) { innerPadding ->
            if (currentQuestion != null) {
                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Environment & Combat Area
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(260.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(CardDark),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = imageResId ?: R.drawable.tempat_street),
                            contentDescription = "Environment",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Brush.verticalGradient(listOf(Color.Transparent, Color.Black.copy(alpha = 0.7f))))
                        )

                        // Villain Image & Health Bar
                        Column(
                            modifier = Modifier.align(Alignment.Center),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            // Villain Health Bar
                            Box(
                                modifier = Modifier
                                    .width(120.dp)
                                    .height(8.dp)
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(Color.Gray.copy(alpha = 0.5f))
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth(villainHealth / 100f)
                                        .fillMaxHeight()
                                        .background(Color.Red)
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Image(
                                painter = painterResource(id = villainImageId ?: R.drawable.red_skull),
                                contentDescription = "Villain",
                                modifier = Modifier
                                    .size(150.dp)
                                    .graphicsLayer {
                                        // Optional: Add some animation or effect
                                    },
                                contentScale = ContentScale.Fit
                            )
                        }

                        Text(
                            text = "PERTANYAAN ${currentQuestionIndex + 1}/${questions.size}",
                            color = TextWhite,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.align(Alignment.BottomStart).padding(12.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Question
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .background(CardLighter)
                            .border(1.dp, BluePrimary.copy(alpha = 0.3f), RoundedCornerShape(16.dp))
                            .padding(24.dp)
                    ) {
                        Text(
                            text = currentQuestion.text,
                            color = TextWhite,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Options
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        currentQuestion.options.forEachIndexed { index, option ->
                            OptionButton(
                                text = option,
                                onClick = {
                                    if (index == currentQuestion.correctAnswerIndex) {
                                        score += 50
                                        villainHealth -= damagePerQuestion
                                        if (currentQuestionIndex < questions.size - 1) {
                                            currentQuestionIndex++
                                        } else {
                                            // Semua soal terjawab benar, villain darahnya 0
                                            villainHealth = 0f
                                            isVictory = true
                                        }
                                    } else {
                                        lives--
                                        if (lives <= 0) {
                                            isGameOver = true
                                        } else if (currentQuestionIndex < questions.size - 1) {
                                            currentQuestionIndex++
                                        } else {
                                            // Soal terakhir tapi salah jawab, darah villain tidak berkurang 
                                            // tapi soal sudah habis, user tetap menang jika masih ada nyawa
                                            isVictory = true
                                        }
                                    }
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
fun QuizResultScreen(
    user: User,
    isVictory: Boolean,
    score: Int,
    coinsGained: Int,
    xpGained: Int,
    onClose: () -> Unit,
    onRevive: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize().background(BackgroundDark)) {
        // Hero Background - Use selected hero for victory, otherwise fallback
        Image(
            painter = painterResource(
                id = if (isVictory) {
                    user.selectedHeroImageId ?: R.drawable.hero_figure
                } else {
                    R.drawable.captain_amerika
                }
            ),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            alpha = 0.4f
        )
        
        // Gradient Overlay
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Brush.verticalGradient(listOf(Color.Transparent, BackgroundDark)))
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = if (isVictory) "MISI BERHASIL!" else "GAME OVER",
                color = if (isVictory) GoldYellow else RedPrimary,
                fontSize = 32.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = 2.sp
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = if (isVictory) "Kamu telah mengalahkan musuh!" else "Jangan menyerah, Avenger!",
                color = TextWhite,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Stats Card
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(CardDark.copy(alpha = 0.9f))
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ResultStatRow(label = "SKOR", value = score.toString(), color = BluePrimary)
                HorizontalDivider(color = Color.DarkGray, modifier = Modifier.padding(vertical = 12.dp))
                ResultStatRow(label = "XP", value = "+$xpGained", color = GoldYellow)
                ResultStatRow(label = "COINS", value = "+$coinsGained", color = Color.Yellow)
            }

            Spacer(modifier = Modifier.height(48.dp))

            if (!isVictory) {
                Button(
                    onClick = onRevive,
                    colors = ButtonDefaults.buttonColors(containerColor = GoldYellow),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth().height(56.dp)
                ) {
                    Icon(Icons.Default.Refresh, contentDescription = null, tint = Color.Black)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "TOP UP NYAWA (50 COINS)",
                        color = Color.Black,
                        fontWeight = FontWeight.Black,
                        fontSize = 16.sp
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
            }

            Button(
                onClick = onClose,
                colors = ButtonDefaults.buttonColors(containerColor = if (isVictory) BluePrimary else Color.DarkGray),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth().height(56.dp)
            ) {
                Text(
                    text = if (isVictory) "LANJUTKAN" else "KEMBALI KE MENU",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }
        }
    }
}

@Composable
fun ResultStatRow(label: String, value: String, color: Color) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, color = Color.Gray, fontWeight = FontWeight.Bold)
        Text(text = value, color = color, fontWeight = FontWeight.Black, fontSize = 20.sp)
    }
}

@Composable
fun OptionButton(text: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(CardDark)
            .border(1.dp, Color.DarkGray, RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .padding(horizontal = 24.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = text,
            color = TextWhite,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
