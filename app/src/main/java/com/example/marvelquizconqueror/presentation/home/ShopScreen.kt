package com.example.marvelquizconqueror.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.marvelquizconqueror.core.theme.*
import com.example.marvelquizconqueror.domain.model.User
import com.example.marvelquizconqueror.presentation.common.MarvelBottomNav
import com.example.marvelquizconqueror.presentation.common.MarvelTopBar

@Composable
fun ShopScreen(
    user: User,
    onBuyLives: (Int, Int) -> Unit, // count, cost
    onNavigate: (String) -> Unit
) {
    Scaffold(
        topBar = { MarvelTopBar(user = user) },
        bottomBar = { MarvelBottomNav(currentRoute = "toko", onNavigate = onNavigate) },
        containerColor = BackgroundDark
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = null,
                    tint = RedPrimary,
                    modifier = Modifier.size(32.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "TOKO AVENGERS",
                    color = TextWhite,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 1.sp
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "PULIHKAN NYAWA",
                color = BluePrimary,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
            
            Spacer(modifier = Modifier.height(12.dp))

            ShopItem(
                title = "1 Nyawa",
                description = "Pulihkan 1 nyawa untuk lanjut bermain",
                cost = 50,
                icon = { Icon(Icons.Default.Favorite, null, tint = Color.Red, modifier = Modifier.size(32.dp)) },
                onClick = { onBuyLives(1, 50) }
            )
            
            Spacer(modifier = Modifier.height(12.dp))

            ShopItem(
                title = "Paket 3 Nyawa",
                description = "Pulihkan semua nyawa sekaligus",
                cost = 120,
                icon = { 
                    Row {
                        repeat(3) { Icon(Icons.Default.Favorite, null, tint = Color.Red, modifier = Modifier.size(24.dp)) }
                    }
                },
                onClick = { onBuyLives(3, 120) }
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "ITEM LAINNYA (SEGERA HADIR)",
                color = Color.Gray,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
        }
    }
}

@Composable
fun ShopItem(
    title: String,
    description: String,
    cost: Int,
    icon: @Composable () -> Unit,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(CardDark)
            .border(1.dp, Color.DarkGray, RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFF1A1A1A)),
                contentAlignment = Alignment.Center
            ) {
                icon()
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(text = title, color = TextWhite, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(text = description, color = Color.Gray, fontSize = 12.sp)
            }
            
            Button(
                onClick = onClick,
                colors = ButtonDefaults.buttonColors(containerColor = GoldYellow),
                contentPadding = PaddingValues(horizontal = 12.dp),
                shape = RoundedCornerShape(4.dp)
            ) {
                Text(text = "$cost COINS", color = Color.Black, fontWeight = FontWeight.Black, fontSize = 12.sp)
            }
        }
    }
}
