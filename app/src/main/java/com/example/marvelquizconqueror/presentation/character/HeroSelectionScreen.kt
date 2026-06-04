package com.example.marvelquizconqueror.presentation.character

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import com.example.marvelquizconqueror.R
import com.example.marvelquizconqueror.core.theme.*
import com.example.marvelquizconqueror.domain.model.Hero
import com.example.marvelquizconqueror.domain.model.User
import com.example.marvelquizconqueror.presentation.common.MarvelBottomNav
import com.example.marvelquizconqueror.presentation.common.MarvelTopBar
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HeroSelectionScreen(
    user: User,
    onNavigate: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val heroes = listOf(
        Hero("1", "CAPTAIN AMERICA", "LEADER", "Simbol kebebasan dengan perisai vibranium.", "Strategi", 90, R.drawable.captain_amerika),
        Hero("2", "IRON MAN", "TECH", "Genius, milyarder, dengan armor tempur canggih.", "Teknologi", 95, R.drawable.ironman),
        Hero("3", "WOLVERINE", "TANK", "Mutan dengan kemampuan regenerasi luar biasa.", "Daya Tahan", 88, R.drawable.wolverine),
        Hero("4", "SCARLET WITCH", "MAGE", "Mampu memanipulasi realitas dengan Chaos Magic.", "Sihir", 98, R.drawable.scarlet_witch),
        Hero("5", "SPIDER-MAN NOIR", "STEALTH", "Pahlawan dari era 1930-an yang penuh misteri.", "Kelincahan", 85, R.drawable.spiderman_noir),
        Hero("6", "DOCTOR STRANGE", "MYSTIC", "Sorcerer Supreme pelindung dimensi kita.", "Mistik", 96, R.drawable.doctor_strange),
        Hero("7", "DAREDEVIL", "FIGHTER", "The Man Without Fear dengan indra yang tajam.", "Refleks", 82, R.drawable.daredevil),
        Hero("8", "PUNISHER", "TACTICAL", "Ahli strategi tempur dengan persenjataan lengkap.", "Taktis", 80, R.drawable.punisher),
        Hero("9", "VENOM", "ANTI-HERO", "Simbiot alien dengan kekuatan fisik luar biasa.", "Kekuatan", 92, R.drawable.venom),
        Hero("10", "THANOS", "TITAN", "Mad Titan yang mencari keseimbangan alam semesta.", "Kosmik", 99, R.drawable.thanos),
        Hero("11", "HELA", "VILLAIN", "Dewi Kematian dari Asgard.", "Kematian", 94, R.drawable.hela),
        Hero("12", "DR. DOOM", "SUPREME", "Penguasa Latveria dengan ilmu sihir dan teknologi.", "Intelek", 97, R.drawable.dr_doom),
        Hero("13", "MYSTERIO", "ILLUSION", "Ahli efek khusus dan manipulasi visual.", "Ilusi", 75, R.drawable.mysterio),
        Hero("14", "RED SKULL", "COMMANDER", "Musuh bebuyutan Captain America dari Hydra.", "Kepemimpinan", 84, R.drawable.red_skull),
        Hero("15", "KINGPIN", "CRIME BOSS", "Penguasa dunia kriminal New York City.", "Otoritas", 86, R.drawable.the_kingpin),
        Hero("16", "KANG", "CONQUEROR", "Penjelajah waktu yang ingin menguasai garis waktu.", "Waktu", 98, R.drawable.kang_the_conqueror)
    )

    val pagerState = rememberPagerState(pageCount = { heroes.size })

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
        ) {
            Column(modifier = Modifier.padding(start = 24.dp, end = 24.dp, top = 16.dp)) {
                Text(
                    text = "PILIH\nKARAKTERMU",
                    color = RedPrimary,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Black,
                    lineHeight = 36.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Gunakan kekuatan mereka untuk menaklukkan setiap tantangan edukasi.",
                    color = Color(0xFFFF8A80),
                    fontSize = 14.sp
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Carousel Pager
            HorizontalPager(
                state = pagerState,
                contentPadding = PaddingValues(horizontal = 60.dp),
                pageSpacing = 16.dp,
                modifier = Modifier.weight(1f)
            ) { page ->
                val hero = heroes[page]
                HeroCard(
                    hero = hero,
                    modifier = Modifier.graphicsLayer {
                        val pageOffset = (
                                (pagerState.currentPage - page) + pagerState
                                    .currentPageOffsetFraction
                                ).absoluteValue
                        
                        // Apply transformations for a 3D-like carousel effect
                        alpha = lerp(
                            start = 0.5f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        )
                        scaleY = lerp(
                            start = 0.85f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        )
                        scaleX = lerp(
                            start = 0.9f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        )
                    }
                )
            }

            // Pager Indicator
            Row(
                Modifier
                    .height(32.dp)
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(heroes.size) { iteration ->
                    val color = if (pagerState.currentPage == iteration) RedPrimary else Color.Gray
                    val width = if (pagerState.currentPage == iteration) 24.dp else 8.dp
                    Box(
                        modifier = Modifier
                            .padding(2.dp)
                            .clip(CircleShape)
                            .background(color)
                            .width(width)
                            .height(6.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            val selectedHero = heroes[pagerState.currentPage]
            
            Column(modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp)) {
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
                        text = "KONFIRMASI ${selectedHero.name}",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun HeroCard(hero: Hero, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .clip(RoundedCornerShape(24.dp))
            .border(2.dp, RedPrimary, RoundedCornerShape(24.dp))
            .background(CardDark)
    ) {
        Image(
            painter = painterResource(id = hero.imageResId ?: R.drawable.hero_figure),
            contentDescription = hero.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        
        // Dark Gradient Overlay for text readability
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.9f)),
                        startY = 400f
                    )
                )
        )

        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(24.dp)
        ) {
            Text(
                text = hero.role,
                color = TextWhite,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .background(BluePrimary)
                    .padding(horizontal = 10.dp, vertical = 4.dp)
                    .clip(RoundedCornerShape(4.dp))
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = hero.name,
                color = TextWhite,
                fontSize = 30.sp,
                fontWeight = FontWeight.Black,
                lineHeight = 32.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = hero.description,
                color = Color.LightGray,
                fontSize = 14.sp,
                lineHeight = 20.sp
            )
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(hero.stats.uppercase(), color = GoldYellow, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                Text("${hero.powerLevel}% POWER", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(8.dp))
            // Power Progress Bar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(Color.White.copy(alpha = 0.2f))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(hero.powerLevel / 100f)
                        .fillMaxHeight()
                        .background(
                            Brush.horizontalGradient(
                                listOf(BluePrimary, Color(0xFF00E5FF))
                            )
                        )
                )
            }
        }
    }
}
