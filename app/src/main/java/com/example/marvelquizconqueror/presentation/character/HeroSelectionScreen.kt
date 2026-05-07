package com.example.marvelquizconqueror.presentation.character

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.marvelquizconqueror.R
import com.example.marvelquizconqueror.core.theme.*
import com.example.marvelquizconqueror.domain.model.Hero
import com.example.marvelquizconqueror.domain.model.User
import com.example.marvelquizconqueror.presentation.common.MarvelBottomNav
import com.example.marvelquizconqueror.presentation.common.MarvelTopBar

@Composable
fun HeroSelectionScreen(
    user: User,
    onNavigate: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = { MarvelTopBar(user = user) },
        bottomBar = { MarvelBottomNav(currentRoute = "hero", onNavigate = onNavigate) },
        containerColor = BackgroundDark,
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "PILIH\nPAHLAWANMU",
                color = RedPrimary,
                fontSize = 32.sp,
                fontWeight = FontWeight.Black,
                lineHeight = 36.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Pilih pahlawan untuk memulai petualangan\nkinetik selanjutnya. Masing-masing\nmemiliki kemampuan unik untuk\nmemanipulasi grid edukasi.",
                color = Color(0xFFFF8A80),
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Hero Carousel (Simplified Placeholder)
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.weight(1f)
            ) {
                item {
                    HeroCard(
                        hero = Hero(
                            id = "1",
                            name = "ARMOR BORG",
                            role = "CYBERNETICS",
                            description = "Ahli manipulasi digital dan perisai sintetis.",
                            stats = "Inti Logika",
                            powerLevel = 85
                        ),
                        isActive = true
                    )
                }
                item {
                    HeroCard(
                        hero = Hero(
                            id = "2",
                            name = "AQUA...", // Partial view
                            role = "KINETIC",
                            description = "...",
                            stats = "...",
                            powerLevel = 50
                        ),
                        isActive = false
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { onNavigate("quest_list") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(Color(0xFFFF5252), Color(0xFFFF8A80))
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "PILIH ARMOR BORG",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Composable
fun HeroCard(hero: Hero, isActive: Boolean) {
    val width = if (isActive) 240.dp else 100.dp
    val borderColor = if (isActive) RedPrimary else Color.Gray

    Box(
        modifier = Modifier
            .width(width)
            .fillMaxHeight()
            .clip(RoundedCornerShape(8.dp))
            .border(2.dp, borderColor, RoundedCornerShape(8.dp))
            .background(CardDark) // Image placeholder
    ) {
        if (isActive) {
            Image(
                painter = painterResource(id = R.drawable.hero_figure),
                contentDescription = hero.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        } else {
            Image(
                painter = painterResource(id = R.drawable.enemy_sprite_placeholder),
                contentDescription = hero.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
        
        // Gradient overlay for text readability
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color(0xCC000000)),
                        startY = 300f
                    )
                )
        )

        if (isActive) {
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp)
            ) {
                Text(
                    text = hero.role,
                    color = TextWhite,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .background(BluePrimary)
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = hero.name,
                    color = TextWhite,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Black
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = hero.description,
                    color = Color.LightGray,
                    fontSize = 12.sp
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(hero.stats, color = Color.White, fontSize = 10.sp)
                    Text("${hero.powerLevel}%", color = Color.White, fontSize = 10.sp)
                }
                Spacer(modifier = Modifier.height(4.dp))
                // Power Progress Bar
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(Color.Gray)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(hero.powerLevel / 100f)
                            .fillMaxHeight()
                            .background(BluePrimary)
                    )
                }
            }
        }
    }
}
