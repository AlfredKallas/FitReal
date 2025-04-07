package com.fitreal.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.fitreal.R

// Custom Font
val RobotoFont = FontFamily(
    Font(R.font.roboto_regular, FontWeight.Normal),
    Font(R.font.roboto_medium, FontWeight.Medium),
    Font(R.font.roboto_bold, FontWeight.Bold)
)

// Define Typography Styles
val AppTypography = Typography(
    titleLarge = TextStyle(
        fontFamily = RobotoFont,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = RobotoFont,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    labelSmall = TextStyle(
        fontFamily = RobotoFont,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp
    )
)