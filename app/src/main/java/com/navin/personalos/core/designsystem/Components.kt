package com.navin.personalos.core.designsystem

import android.animation.ValueAnimator
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.*
import androidx.compose.ui.semantics.*
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable fun Eyebrow(text: String) { Text(text.uppercase(),style=MaterialTheme.typography.labelSmall,color=MaterialTheme.colorScheme.primary) }
@Composable fun PageTitle(title: String, eyebrow: String? = null, reflective: Boolean = false) {
    Column(verticalArrangement=Arrangement.spacedBy(8.dp)) { eyebrow?.let { Eyebrow(it) };Text(title,style=MaterialTheme.typography.headlineLarge,fontFamily=if(reflective) ReflectiveFont else null) }
}
@Composable fun CalmCard(modifier: Modifier = Modifier, tone: Int = 0, onClick: (() -> Unit)? = null, content: @Composable ColumnScope.() -> Unit) {
    val color=when(tone) { 1 -> MaterialTheme.colorScheme.primaryContainer;2 -> MaterialTheme.colorScheme.tertiaryContainer;3 -> MaterialTheme.colorScheme.secondaryContainer;else -> MaterialTheme.colorScheme.surface }
    Surface(modifier=modifier.fillMaxWidth().then(if(onClick!=null) Modifier.clickable(onClick=onClick) else Modifier),shape=RoundedCornerShape(if(tone==0) 20.dp else 24.dp),color=color,border=BorderStroke(1.dp,MaterialTheme.colorScheme.outlineVariant)) {
        Column(Modifier.padding(if(tone==0) 16.dp else 20.dp),verticalArrangement=Arrangement.spacedBy(10.dp),content=content)
    }
}
@Composable fun PrimaryButton(text: String, enabled: Boolean = true, onClick: () -> Unit) {
    Button(onClick=onClick,enabled=enabled,modifier=Modifier.fillMaxWidth().heightIn(min=56.dp),shape=RoundedCornerShape(18.dp)) { Text(text) }
}
@Composable fun SectionTitle(text: String) { Text(text,style=MaterialTheme.typography.titleLarge,modifier=Modifier.padding(top=8.dp)) }
@Composable fun Field(label: String,value: String,onChange: (String)->Unit,multiline: Boolean=false) {
    OutlinedTextField(value=value,onValueChange=onChange,label={Text(label)},modifier=Modifier.fillMaxWidth(),shape=RoundedCornerShape(18.dp),minLines=if(multiline) 3 else 1,singleLine=!multiline)
}
@Composable fun QuietEmpty(title: String,body: String,action: String,onAction: ()->Unit,companion: Boolean=false) {
    CalmCard(tone=1) { if(companion) Friend(Modifier.align(Alignment.CenterHorizontally));Text(title,style=MaterialTheme.typography.titleLarge);Text(body,color=MaterialTheme.colorScheme.onSurfaceVariant);PrimaryButton(action,onClick=onAction) }
}

/** Project-owned vector artwork. Business data never depends on this decorative state machine. */
@Composable fun Friend(modifier: Modifier = Modifier,reducedMotion: Boolean=false,context: String="idle") {
    var tap by remember { mutableIntStateOf(0) };var reacting by remember { mutableStateOf(false) }
    val states=listOf("wave","curious","completion","reading","focused","reflective","sleepy")
    LaunchedEffect(tap) { if(tap>0) { reacting=true;delay(750);reacting=false } }
    val state=if(reacting) states[(tap-1)%states.size] else context
    val motion = !reducedMotion && ValueAnimator.areAnimatorsEnabled()
    val colors=MaterialTheme.colorScheme
    Canvas(modifier.size(180.dp,165.dp).semantics { contentDescription="Tiny Observatory Friend. Tap for a gentle greeting.";role=Role.Button;stateDescription=if(reacting) state else "Resting" }.clickable { tap++ }) {
        val scale=size.width/180f
        scale(scale,scale,pivot=Offset.Zero) {
            drawOval(colors.primary.copy(alpha=.10f),Offset(14f,135f),Size(152f,22f))
            val skin=Color(0xFFE9B99B);val hair=Color(0xFF3C302C);val sage=Color(0xFF667A61);val trousers=Color(0xFFD8CFBF)
            drawRoundRect(trousers,Offset(49f,122f),Size(46f,22f),CornerRadius(12f));drawRoundRect(trousers,Offset(91f,122f),Size(43f,22f),CornerRadius(12f))
            drawRoundRect(sage,Offset(53f,78f),Size(76f,56f),CornerRadius(23f))
            drawCircle(skin,29f,Offset(91f,53f))
            val hairPath=Path().apply { moveTo(62f,54f);cubicTo(52f,22f,73f,12f,91f,19f);cubicTo(121f,10f,128f,32f,120f,53f);lineTo(111f,39f);cubicTo(91f,43f,81f,28f,66f,47f);close() }
            drawPath(hairPath,hair)
            if(state in listOf("sleepy","reflective")) {
                drawLine(hair,Offset(77f,56f),Offset(83f,56f),2f,StrokeCap.Round);drawLine(hair,Offset(101f,56f),Offset(107f,56f),2f,StrokeCap.Round)
            } else { drawCircle(hair,2.2f,Offset(80f,54f));drawCircle(hair,2.2f,Offset(104f,54f)) }
            drawArc(Color(0xFF825C50),10f,160f,false,Offset(86f,59f),Size(12f,9f),style=Stroke(1.6f))
            drawLine(sage,Offset(59f,89f),Offset(43f,113f),14f,StrokeCap.Round)
            val wave=state in listOf("wave","completion")
            val hand=if(wave) Offset(142f,if(motion) 56f else 63f) else Offset(137f,115f)
            drawLine(sage,Offset(122f,88f),hand,13f,StrokeCap.Round);drawCircle(skin,6f,hand)
            if(state=="reading" || state=="focused") { drawRoundRect(colors.secondaryContainer,Offset(66f,101f),Size(52f,24f),CornerRadius(3f));drawLine(colors.outline,Offset(92f,103f),Offset(92f,123f),1f) }
            if(state=="curious") { drawLine(colors.secondary,Offset(143f,25f),Offset(143f,36f),3f,StrokeCap.Round);drawCircle(colors.secondary,1.6f,Offset(143f,42f)) }
        }
    }
}

