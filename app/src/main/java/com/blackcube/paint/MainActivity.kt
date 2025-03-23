package com.blackcube.paint

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalComposeUiApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val colors = listOf(Color.Black, Color.Blue, Color.Green, Color.Yellow, Color.Red, Color.LightGray)

            Column {
                ///////////////////////////////  ЕСЛИ УСПЕЕМ  //////////////////////////////////////
                var selectedColor by remember { mutableStateOf(colors.first()) }
                Row(
                    modifier = Modifier
                        .background(Color.DarkGray)
                        .fillMaxWidth()
                        .padding(top = 8.dp, bottom = 8.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    colors.forEach {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                                .background(it)
                                .clickable {
                                    selectedColor = it
                                }
                        )
                    }
                }
                ///////////////////////////////  ЕСЛИ УСПЕЕМ  //////////////////////////////////////

                ///////////////////////////////  ОБЯЗАТЕЛЬНО  //////////////////////////////////////
                DrawingScreen()
                ///////////////////////////////  ОБЯЗАТЕЛЬНО  //////////////////////////////////////
            }
        }
    }

    ///////////////////////////////  ОБЯЗАТЕЛЬНО  //////////////////////////////////////
    @ExperimentalComposeUiApi
    @Composable
    fun DrawingScreen() {
        // Объявляем переменную состояния для хранения точек, по которым проведёт пользователь
        var points by remember { mutableStateOf<List<Offset>>(emptyList()) }

        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.LightGray)
                .pointerInput(Unit) {
                    // Обрабатываем жесты перетаскивания пальцем
                    detectDragGestures(
                        onDragStart = {
                            points = listOf(it)
                        },
                        onDrag = { change, _ ->
                            points = points + change.position
                        }
                    )
                }
        ) {
            if (points.size > 1) {
                // Создаем новый путь для рисования
                val path = Path().apply {
                    // Устанавливаем первую точку в качестве начала линии
                    moveTo(points.first().x, points.first().y)
                    // Для остальных точек добавляем линии, соединяя их последовательно
                    points.drop(1).forEach { point ->
                        lineTo(point.x, point.y)
                    }
                }

                drawPath(path, Color.DarkGray, style = Stroke(width = 2.dp.toPx()))
            }
        }
    }
}
