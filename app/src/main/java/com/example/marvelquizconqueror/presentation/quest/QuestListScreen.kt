package com.example.marvelquizconqueror.presentation.quest

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
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
import com.example.marvelquizconqueror.domain.model.Quest
import com.example.marvelquizconqueror.domain.model.User
import com.example.marvelquizconqueror.presentation.common.MarvelTopBar

@Composable
fun QuestListScreen(
    user: User,
    quests: List<Quest>,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = { MarvelTopBar(user = user) },
        containerColor = BackgroundDark,
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Zone Title
            Text(
                text = "ZONA 1: INVASI",
                color = RedPrimary,
                fontSize = 28.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = 2.sp,
                modifier = Modifier.padding(bottom = 12.dp) // Added red shadow/glow in real implementation
            )

            // Sector Progress
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(CardDark)
                    .padding(16.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "PENYELESAIAN SEKTOR",
                            color = BluePrimary,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "45%",
                            color = GoldYellow,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    // Progress Bar
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(Color.DarkGray)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(0.45f)
                                .fillMaxHeight()
                                .background(BluePrimary)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Quest List
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(quests) { quest ->
                    QuestItem(quest = quest)
                }
            }
        }
    }
}

@Composable
fun QuestItem(quest: Quest) {
    val backgroundColor = if (quest.isSuperHard) RedPrimary else if (quest.isLocked) Color(0xFF424242) else CardDark
    val textColor = if (quest.isSuperHard) Color.White else if (quest.isLocked) Color.Gray else TextWhite
    val borderColor = if (quest.isSuperHard) Color(0xFFFF8A80) else Color.Transparent

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(backgroundColor)
            .border(
                width = if (quest.isSuperHard) 2.dp else 0.dp,
                color = borderColor,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Index Box
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(if (quest.isSuperHard) RedDark else if (quest.isLocked) Color(0xFF555555) else Color(0xFF333333)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = quest.orderIndex,
                    color = if (quest.isSuperHard) Color.Black else BluePrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Text Content
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = quest.title,
                    color = textColor,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
                if (quest.isSuperHard) {
                    Text(
                        text = "SUPER HARD",
                        color = Color.LightGray,
                        fontSize = 12.sp
                    )
                } else {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        // Stars
                        Row {
                            for (i in 1..3) {
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = null,
                                    tint = if (i <= quest.difficultyStars) GoldYellow else Color.DarkGray,
                                    modifier = Modifier.size(12.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        // Tags
                        Text(
                            text = quest.tags.joinToString("   "),
                            color = if (quest.isLocked) Color.Gray else Color.White,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            // Right Icon/Action
            if (quest.isCompleted) {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF333333))
                        .border(2.dp, GoldYellow, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Completed",
                        tint = GoldYellow,
                        modifier = Modifier.size(16.dp)
                    )
                }
            } else if (quest.isLocked) {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Locked",
                    tint = Color.Gray,
                    modifier = Modifier.size(20.dp)
                )
            } else if (quest.isSuperHard) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(Color(0xFFFFCCCC))
                        .padding(horizontal = 12.dp, vertical = 6.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "MAIN",
                        color = RedDark,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}
