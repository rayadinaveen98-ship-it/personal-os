package com.navin.personalos.core.designsystem

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.unit.dp

/** Original coherent 24-unit line symbols. Adjacent tab text supplies the accessible label. */
@Composable fun NavigationSymbol(tab: String,selected: Boolean) {
    val onPrimary=MaterialTheme.colorScheme.onPrimary
    val color=if(selected || tab=="capture") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
    Canvas(Modifier.size(24.dp)) {
        withTransform({scale(size.width/24f,size.height/24f,pivot=Offset.Zero)}) {
            val stroke=Stroke(1.65f,cap=StrokeCap.Round)
            when(tab) {
                "today" -> {
                    drawCircle(color,4.5f,Offset(12f,12f),style=stroke)
                    for(i in 0..7) {val angle=i*Math.PI/4;val dx=kotlin.math.cos(angle).toFloat();val dy=kotlin.math.sin(angle).toFloat();drawLine(color,Offset(12f+dx*8,12f+dy*8),Offset(12f+dx*10,12f+dy*10),1.65f,StrokeCap.Round)}
                }
                "plan" -> {drawRoundRect(color,Offset(3f,4f),Size(18f,17f),CornerRadius(3f),style=stroke);drawLine(color,Offset(3f,9f),Offset(21f,9f),1.65f);drawLine(color,Offset(8f,2f),Offset(8f,6f),1.65f,StrokeCap.Round);drawLine(color,Offset(16f,2f),Offset(16f,6f),1.65f,StrokeCap.Round);drawLine(color,Offset(7f,14f),Offset(10f,17f),1.65f,StrokeCap.Round);drawLine(color,Offset(10f,17f),Offset(17f,12f),1.65f,StrokeCap.Round)}
                "capture" -> {drawCircle(color,11f,Offset(12f,12f));val contrast=onPrimary;drawLine(contrast,Offset(6f,12f),Offset(18f,12f),1.8f,StrokeCap.Round);drawLine(contrast,Offset(12f,6f),Offset(12f,18f),1.8f,StrokeCap.Round)}
                "journey" -> {val p=Path().apply {moveTo(3f,4f);lineTo(8f,4f);quadraticTo(12f,4f,12f,7f);quadraticTo(12f,4f,16f,4f);lineTo(21f,4f);lineTo(21f,20f);lineTo(16f,20f);quadraticTo(12f,20f,12f,22f);quadraticTo(12f,20f,8f,20f);lineTo(3f,20f);close()};drawPath(p,color,style=stroke);drawLine(color,Offset(12f,7f),Offset(12f,20f),1.65f)}
                else -> {drawCircle(color,4f,Offset(12f,7f),style=stroke);drawArc(color,180f,180f,false,Offset(4f,13f),Size(16f,16f),style=stroke)}
            }
        }
    }
}
