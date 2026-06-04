package com.example.marvelquizconqueror.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.marvelquizconqueror.domain.model.User
import com.example.marvelquizconqueror.presentation.common.MarvelBottomNav
import com.example.marvelquizconqueror.presentation.common.MarvelTopBar

data class WorldZone(
    val id: String,
    val name: String,
    val description: String,
    val progress: Float,
    val isLocked: Boolean = false,
    val imageResId: Int
)

@Composable
fun WorldSelectionScreen(
    user: User,
    onNavigate: (String) -> Unit,
    onWorldClick: (String) -> Unit
) {
    val zones = listOf(
        WorldZone(
            id = "1",
            name = "ZONA 1: KOTA NEW YORK",
            description = "Hentikan invasi di jalanan kota.",
            progress = 0.8f,
            isLocked = false,
            imageResId = R.drawable.tempat_newyork
        ),
        WorldZone(
            id = "2",
            name = "ZONA 2: LABORATORIUM",
            description = "Sabotase rencana musuh di lab.",
            progress = 0.3f,
            isLocked = false,
            imageResId = R.drawable.tempat_lab
        ),
        WorldZone(
            id = "3",
            name = "ZONA 3: HUTAN WAKANDA",
            description = "Pertempuran di pedalaman hutan.",
            progress = 0f,
            isLocked = false,
            imageResId = R.drawable.tempat_hutan
        ),
        WorldZone(
            id = "4",
            name = "ZONA 4: KASTEL DOOM",
            description = "Serbu benteng pertahanan musuh.",
            progress = 0f,
            isLocked = true,
            imageResId = R.drawable.tempat_kastel
        ),
        WorldZone(
            id = "5",
            name = "ZONA 5: LUAR ANGKASA",
            description = "Pertempuran antar galaksi.",
            progress = 0f,
            isLocked = true,
            imageResId = R.drawable.tempat_galaxy
        ),
        WorldZone(
            id = "6",
            name = "ZONA 6: PLANET ASGARD",
            description = "Selamatkan dunia para dewa.",
            progress = 0f,
            isLocked = true,
            imageResId = R.drawable.tempat_planet
        )
    )

    Scaffold(
        topBar = { MarvelTopBar(user = user) },
        bottomBar = { MarvelBottomNav(currentRoute = "dunia", onNavigate = onNavigate) },
        containerColor = BackgroundDark
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "PILIH DUNIA",
                color = RedPrimary,
                fontSize = 32.sp,
                fontWeight = FontWeight.Black
            )
            Spacer(modifier = Modifier.height(16.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(1),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                items(zones) { zone ->
                    WorldZoneCard(zone = zone, onClick = { if (!zone.isLocked) onWorldClick(zone.id) })
                }
            }
        }
    }
}

@Composable
fun WorldZoneCard(zone: WorldZone, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .clip(RoundedCornerShape(12.dp))
            .border(
                width = 2.dp,
                color = if (zone.isLocked) Color.Gray else RedPrimary,
                shape = RoundedCornerShape(12.dp)
            )
            .clickable { onClick() }
    ) {
        Image(
            painter = painterResource(id = zone.imageResId),
            contentDescription = zone.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
            alpha = if (zone.isLocked) 0.5f else 1f
        )
        
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.9f)),
                        startY = 100f
                    )
                )
        )

        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp)
        ) {
            Text(
                text = zone.name,
                color = TextWhite,
                fontSize = 20.sp,
                fontWeight = FontWeight.Black
            )
            Text(
                text = zone.description,
                color = Color.LightGray,
                fontSize = 12.sp
            )
            
            if (!zone.isLocked) {
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(6.dp)
                            .clip(RoundedCornerShape(3.dp))
                            .background(Color.DarkGray)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(zone.progress)
                                .fillMaxHeight()
                                .background(BluePrimary)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "${(zone.progress * 100).toInt()}%",
                        color = BluePrimary,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            } else {
                Text(
                    text = "TERKUNCI",
                    color = Color.Gray,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}
