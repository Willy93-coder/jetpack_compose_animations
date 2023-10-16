package com.example.jetpackcomposeanimations

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlin.random.Random.Default.nextInt

@Composable
fun ColorAnimationSimple() {
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceAround) {
        var firstColor by remember { mutableStateOf(false) }
        val realColor = if (firstColor) Color.Red else Color.Yellow
        Box(modifier = Modifier
            .size(100.dp)
            .background(realColor)
            .clickable { firstColor = !firstColor }
        )

        var secondColor by remember { mutableStateOf(false) }
        val realColor2 by animateColorAsState(targetValue = if (secondColor) Color.Red else Color.Yellow)
        Box(modifier = Modifier
            .size(100.dp)
            .background(realColor2)
            .clickable { secondColor = !secondColor }
        )
    }
}

@Composable
fun ColorAnimationAdvance() {
    var firstColor by remember { mutableStateOf(false) }
    var showBox by remember { mutableStateOf(true) }
    val realColor by animateColorAsState(
        targetValue = if (firstColor) Color.Red else Color.Yellow,
        animationSpec = tween(durationMillis = 2000),
        label = "ColorAnimation",
        finishedListener = { showBox = false } // Esto se llama cuando la animacion haya terminado
    )
    if (showBox) {
        Box(modifier = Modifier
            .size(100.dp)
            .background(realColor)
            .clickable { firstColor = !firstColor }
        )
    }
}

@Composable
fun SizeAnimation() {
    var smallSize by rememberSaveable { mutableStateOf(true) }
    val size by animateDpAsState(
        targetValue = if (smallSize) 50.dp else 100.dp,
        label = "sizeAnimation",
        animationSpec = tween(durationMillis = 500),
        finishedListener = {
            if (!smallSize) {
                // Aqui iria la logica de como queremos que se comporte
            }
        }
    )
    Box(modifier = Modifier
        .size(size)
        .background(Color.Cyan)
        .clickable { smallSize = !smallSize }
    )
}

@Composable
fun VisibilityAnimation() {
    var isVisible by rememberSaveable { mutableStateOf(true) }
    Column(modifier = Modifier.fillMaxSize()) {
        Button(onClick = { isVisible = !isVisible }) {
            Text("Mostrar/Ocultar")
        }
        Spacer(modifier = Modifier.size(50.dp))
        AnimatedVisibility(visible = isVisible) {
            Box(
                modifier = Modifier
                    .size(150.dp)
                    .background(Color.Red)
            )
        }
    }
}

@Composable
fun CrossFadeExampleAnimation() {
    var myComponentType: ComponentType by remember { mutableStateOf(ComponentType.Text) }
    Column(Modifier.fillMaxSize()) {
        Button(onClick = { myComponentType = getComponentTypeRandom() }) {
            Text(text = "Cambiar componente")
        }
        Crossfade(targetState = myComponentType, label = "") { myComponentType ->
            when (myComponentType) {
                ComponentType.Image -> Icon(Icons.Default.Share, contentDescription = "")
                ComponentType.Text -> Text(text = "SOY UN COMPONENTE")
                ComponentType.Box -> Box(
                    modifier = Modifier
                        .size(150.dp)
                        .background(Color.Red)
                )

                ComponentType.Error -> Text(text = "ERROR")
            }
        }
    }
}

fun getComponentTypeRandom(): ComponentType {
    return when (nextInt(from = 0, until = 3)) {
        0 -> ComponentType.Image
        1 -> ComponentType.Text
        2 -> ComponentType.Box
        else -> ComponentType.Error
    }
}

enum class ComponentType() {
    Image, Text, Box, Error
}