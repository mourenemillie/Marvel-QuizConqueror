package com.example.marvelquizconqueror.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.marvelquizconqueror.core.theme.BluePrimary
import com.example.marvelquizconqueror.core.theme.CardLighter
import com.example.marvelquizconqueror.core.theme.TextWhite

sealed class BottomNavItem(val title: String, val icon: ImageVector, val route: String) {
    object Dunia : BottomNavItem("DUNIA", Icons.Default.Language, "dunia")
    object Hero : BottomNavItem("HERO", Icons.Default.Security, "hero")
    object Toko : BottomNavItem("TOKO", Icons.Default.ShoppingBag, "toko")
    object Profil : BottomNavItem("PROFIL", Icons.Default.Person, "profil")
}

@Composable
fun MarvelBottomNav(
    currentRoute: String,
    onNavigate: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val items = listOf(
        BottomNavItem.Dunia,
        BottomNavItem.Hero,
        BottomNavItem.Toko,
        BottomNavItem.Profil
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            .background(CardLighter)
            .padding(vertical = 8.dp, horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        items.forEach { item ->
            val isSelected = currentRoute == item.route
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .clickable { onNavigate(item.route) }
                    .background(if (isSelected) BluePrimary else Color.Transparent)
                    .padding(horizontal = 24.dp, vertical = 8.dp)
            ) {
                Icon(
                    imageVector = item.icon,
                    contentDescription = item.title,
                    tint = if (isSelected) TextWhite else Color.Gray,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = item.title,
                    color = if (isSelected) TextWhite else Color.Gray,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
