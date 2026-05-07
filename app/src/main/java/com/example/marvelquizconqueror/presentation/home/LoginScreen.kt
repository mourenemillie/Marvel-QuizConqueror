package com.example.marvelquizconqueror.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.marvelquizconqueror.core.theme.*

@Composable
fun LoginScreen(
    onNavigateToHeroSelection: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(CardLighter, BackgroundDark)
                )
            )
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Marvel Quest Title Placeholder
            Text(
                text = "MARVEL\nQUEST",
                color = Color(0xFFFFCCCC), // Lighter red
                fontSize = 48.sp,
                fontWeight = FontWeight.Black,
                textAlign = TextAlign.Center,
                lineHeight = 48.sp,
                letterSpacing = 2.sp,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            // Avengers Logo Placeholder
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(Color.DarkGray)
                    .border(2.dp, Color.Gray, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                // If the user has an SVG, they can use Icon(painterResource(R.drawable.ic_avengers)) here
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "Logo",
                    tint = RedPrimary,
                    modifier = Modifier.size(40.dp)
                )
            }

            Spacer(modifier = Modifier.height(48.dp))

            // Info Card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(CardLighter)
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "BELAJAR LAYAKNYA\nPAHLAWAN",
                    color = GoldYellow,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Description Text
            Text(
                text = "Buka potensi Anda. Kuasai keahlian untuk mengalahkan penjahat dan tingkatkan pengetahuan Anda.",
                color = TextWhite,
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(64.dp))

            // Start Button
            Button(
                onClick = onNavigateToHeroSelection,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(Color(0xFFFF5252), Color(0xFFFF8A80))
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "MULAI PETUALANGAN",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = "Arrow",
                        tint = Color.Black
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Login Link
            TextButton(onClick = { /* Handle login */ }) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(color = Color.Gray)) {
                            append("Sudah punya pahlawan? ")
                        }
                        withStyle(style = SpanStyle(color = BluePrimary)) {
                            append("Masuk")
                        }
                    },
                    fontSize = 12.sp
                )
            }
        }
    }
}
