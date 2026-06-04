package com.example.marvelquizconqueror.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.marvelquizconqueror.core.theme.*
import com.example.marvelquizconqueror.domain.model.User

@Composable
fun MarvelTopBar(user: User, modifier: Modifier = Modifier) {
    // Logic for XP progress (example: 1000 XP per level)
    val xpInCurrentLevel = user.xp % 1000
    val xpProgress = xpInCurrentLevel / 1000f

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(CardLighter)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Left Section: Profile Icon & App Name
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .border(2.dp, RedPrimary, CircleShape)
                    .background(Color.DarkGray),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Profile",
                    tint = RedPrimary,
                    modifier = Modifier.size(24.dp)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(
                    text = "MARVEL",
                    color = RedPrimary,
                    fontWeight = FontWeight.Black,
                    fontSize = 14.sp,
                    letterSpacing = 1.sp
                )
                Text(
                    text = "QUEST",
                    color = TextWhite,
                    fontWeight = FontWeight.Black,
                    fontSize = 14.sp,
                    letterSpacing = 1.sp
                )
            }
        }

        // Middle Section: Name, XP, Coins, Energy
        Column(
            modifier = Modifier.weight(1f).padding(horizontal = 12.dp)
        ) {
            Text(
                text = user.name.uppercase(),
                color = RedPrimary,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                maxLines = 1
            )
            Spacer(modifier = Modifier.height(4.dp))
            // Progress Bar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(Color.Gray)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(xpProgress)
                        .fillMaxHeight()
                        .background(BluePrimary)
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("XP: ${user.xp}", color = TextWhite, fontSize = 10.sp)
                Text("🪙 ${user.coins}", color = TextWhite, fontSize = 10.sp)
                Text("⚡ ${user.energy}", color = TextWhite, fontSize = 10.sp)
            }
        }

        // Right Section: Level
        Text(
            text = user.level.toString(),
            color = RedPrimary,
            fontWeight = FontWeight.Black,
            fontSize = 32.sp
        )
    }
}
