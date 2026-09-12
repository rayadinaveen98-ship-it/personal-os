package com.navin.personalos.core.designsystem

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import com.navin.personalos.R
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

private val Light = lightColorScheme(primary=Color(0xFF667A61),onPrimary=Color.White,primaryContainer=Color(0xFFDDE4DA),onPrimaryContainer=Color(0xFF252521),background=Color(0xFFF5F1E8),onBackground=Color(0xFF252521),surface=Color(0xFFFCF9F3),onSurface=Color(0xFF252521),surfaceVariant=Color(0xFFEEE9DE),onSurfaceVariant=Color(0xFF4E4E48),outline=Color(0xFF6B6A63),outlineVariant=Color(0xFFE1DCD1),secondary=Color(0xFF667A61),secondaryContainer=Color(0xFFEFE3CE),onSecondaryContainer=Color(0xFF252521),tertiaryContainer=Color(0xFFE6E1EA),onTertiaryContainer=Color(0xFF252521),error=Color(0xFF8E5147),onError=Color.White)
private val Dark = darkColorScheme(primary=Color(0xFF9BAE95),onPrimary=Color(0xFF171816),primaryContainer=Color(0xFF2B2C27),onPrimaryContainer=Color(0xFFF5F1E8),background=Color(0xFF171816),onBackground=Color(0xFFF5F1E8),surface=Color(0xFF22231F),onSurface=Color(0xFFF5F1E8),surfaceVariant=Color(0xFF2B2C27),onSurfaceVariant=Color(0xFFC7C5BC),outline=Color(0xFFA7A69E),outlineVariant=Color(0xFF3A3B35),secondary=Color(0xFFC8A96B),secondaryContainer=Color(0xFF2B2C27),onSecondaryContainer=Color(0xFFF5F1E8),tertiaryContainer=Color(0xFF2B2C27),onTertiaryContainer=Color(0xFFF5F1E8),error=Color(0xFFB98170),onError=Color(0xFF171816))
val ReflectiveFont = FontFamily(Font(R.font.newsreader))
@Composable fun PersonalTheme(mode: String = "SYSTEM", content: @Composable () -> Unit) {
    val dark = mode == "DARK" || mode == "SYSTEM" && isSystemInDarkTheme()
    val sans = FontFamily(Font(R.font.manrope))
    MaterialTheme(colorScheme=if(dark) Dark else Light, typography=Typography(
        headlineLarge=TextStyle(fontFamily=sans,fontSize=34.sp,lineHeight=40.sp,fontWeight=FontWeight.Bold),
        headlineMedium=TextStyle(fontFamily=sans,fontSize=30.sp,lineHeight=36.sp,fontWeight=FontWeight.Bold),
        titleLarge=TextStyle(fontFamily=sans,fontSize=22.sp,lineHeight=28.sp,fontWeight=FontWeight.SemiBold),
        titleMedium=TextStyle(fontFamily=sans,fontSize=18.sp,lineHeight=24.sp,fontWeight=FontWeight.SemiBold),
        bodyLarge=TextStyle(fontFamily=sans,fontSize=16.sp,lineHeight=24.sp),
        bodyMedium=TextStyle(fontFamily=sans,fontSize=15.sp,lineHeight=22.sp),
        labelLarge=TextStyle(fontFamily=sans,fontSize=15.sp,lineHeight=20.sp,fontWeight=FontWeight.SemiBold),
        labelMedium=TextStyle(fontFamily=sans,fontSize=13.sp,lineHeight=18.sp,fontWeight=FontWeight.Medium),
        labelSmall=TextStyle(fontFamily=sans,fontSize=12.sp,lineHeight=16.sp,fontWeight=FontWeight.SemiBold)
    ), content=content)
}
