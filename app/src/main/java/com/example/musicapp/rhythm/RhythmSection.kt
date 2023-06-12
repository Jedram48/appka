package com.example.musicapp.rhythm

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.musicapp.R
import com.example.musicapp.db.ExerciseViewModel
import com.example.musicapp.models.ExerciseLog
import com.example.musicapp.rhythm.Metronome
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.LocalTime

class RhythmSection : Fragment(), LifecycleObserver {

    private lateinit var metronome : Metronome
    private var rhythmSequence: List<Boolean> by mutableStateOf(emptyList())
    private val navController: NavController by lazy { findNavController() }
    private lateinit var dbViewModel: ExerciseViewModel

    override fun onStart() {
        super.onStart()
        startMetronome()
        rhythmSequence = metronome.getRhythmSequence()
    }

    override fun onStop() {
        super.onStop()
        stopMetronome()
        dbViewModel.logExercise(ExerciseLog(0, 1, LocalDate.now(), LocalTime.now(), 10, 100 ))
    }

    private fun startMetronome() {
        viewLifecycleOwner.lifecycleScope.launch {
            withContext(Dispatchers.Default) {
                metronome.start()
            }
        }
    }

    private fun stopMetronome() {
        viewLifecycleOwner.lifecycleScope.launch {
            withContext(Dispatchers.Default) {
                metronome.stop()
            }
        }
    }



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dbViewModel = ViewModelProvider(this).get(ExerciseViewModel::class.java)
        metronome = Metronome(requireContext(), 60)
        return ComposeView(requireContext()).apply {
            setContent {
                RhythmFragmentUI(
                    rhythmSequence = rhythmSequence,
                    metronome = metronome,
                    navController = navController
                )
            }
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private fun onStartFragment() {
        startMetronome()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private fun onStopFragment() {
        stopMetronome()
    }
}

@Composable
fun RhythmFragmentUI(
    rhythmSequence: List<Boolean>,
    metronome: Metronome,
    navController: NavController
) {
    val metronomeTicks by remember { derivedStateOf { metronome.getMetronomeTicks() } }
    val lineCoordinates = remember { mutableStateListOf<Float>() }
    val colorState = remember { mutableStateListOf<Color>() }
    val checkBegin = remember { mutableStateOf(false) }
//    val ticks = remember { mutableStateOf(-1) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Rhythm Notation",
            style = MaterialTheme.typography.h4,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        MusicSheetBar( rhythmSequence, metronomeTicks )
        ProgressLine(pos = lineCoordinates, color = colorState)

        if(checkBegin.value && metronomeTicks[0]){
            metronome.stop()
            Box(
                modifier = Modifier
                    .fillMaxHeight(0.5f)
                    .fillMaxWidth()
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onPress = {
                                navController.navigate(R.id.action_rhythm_section_to_home)
                            }
                        )
                    }
                    .background(Color.LightGray)
            ) {
                Text(
                    text = "Go back",
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
        else{
            Box(
                modifier = Modifier
                    .fillMaxHeight(0.5f)
                    .fillMaxWidth()
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onPress = {
                                checkBegin.value = true
                                var color: Color = Color.Red
                                val duration = metronome.barDuration
                                val timeStamp = metronome.checkIfTick()
                                if (timeStamp) {
                                    color = Color.Green
                                }
                                lineCoordinates += (System.currentTimeMillis() - metronome.barStart) / duration
                                colorState += color
                                awaitRelease()
                            }
                        )
                    }
                    .background(Color.LightGray)
            ) {
                Text(
                    text = "Tap  Here",
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }

    }

}

@Composable
fun ProgressLine(pos: List<Float>,color: List<Color>){
    val paddingStart: Dp = 20.dp
    val paddingEnd: Dp = 20.dp
    Box (modifier = Modifier.padding(vertical = 16.dp)) {
        Canvas(modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.Center)) {
            val startY = size.height / 2
            val startX = size.width * (paddingStart.value / (size.width - paddingStart.value - paddingEnd.value))
            val endX = size.width - (size.width * (paddingEnd.value / (size.width - paddingStart.value - paddingEnd.value)))



            drawLine(
                color = Color.Gray,
                start = Offset(startX, startY),
                end = Offset(endX, startY),
                strokeWidth = 8f
            )

            if(pos.size == color.size){
                for (i in pos.indices) {
                    drawLine(
                        color = color[i],
                        start = Offset(startX + (endX - startX) * pos[i], startY - 20),
                        end = Offset(startX + (endX - startX) * pos[i], startY + 20),
                        strokeWidth = 8f
                    )
                }
            }
        }
    }
}
@Composable
fun MusicSheetBar(array: List<Boolean>, metronomeTicks: List<Boolean>) {
    val paddingStart: Dp = 20.dp
    val paddingEnd: Dp = 20.dp
    val width = DisplayMetrics().widthPixels.dp
    val rowPadding = width/32

    Box (modifier = Modifier.padding(vertical = 16.dp)) {
        Canvas(modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.Center)) {
            val startY = size.height / 2
            val startX = size.width * (paddingStart.value / (size.width - paddingStart.value - paddingEnd.value))
            val endX = size.width - (size.width * (paddingEnd.value / (size.width - paddingStart.value - paddingEnd.value)))

            drawLine(
                color = Color.Red,
                start = Offset(startX, startY),
                end = Offset(endX, startY),
                strokeWidth = 8f
            )

            drawLine(
                color = Color.Red,
                start = Offset(startX, startY - 20),
                end = Offset(startX, startY + 20),
                strokeWidth = 8f
            )

            drawLine(
                color = Color.Red,
                start = Offset(endX, startY + 20),
                end = Offset(endX, startY - 20),
                strokeWidth = 8f
            )

            drawLine(
                color = Color.Black,
                start = Offset(startX + 50, startY),
                end = Offset(endX - 50, startY),
                strokeWidth = 10f
            )

        }
        BoxWithConstraints ( modifier = Modifier.padding(start = paddingStart, end = paddingEnd)){
            val circleSpacing: Dp = 4.dp
            val circleSize = (maxWidth - (circleSpacing * 7) - (rowPadding * 2)) / 8

            Row(
                modifier = Modifier.padding(horizontal = rowPadding),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if(array.size == 8){
                    for ((ind, item) in array.withIndex()){
                        if(item){
                            val color = Color.White
                            var stroke = 0f
                            stroke = when (ind) {
                                0, 2, 4, 6 -> 7f
                                1, 3, 5, 7 -> 4f
                                else -> 0f
                            }
                            Box(
                                modifier = Modifier
                                    .size(circleSize)
                                    .background(color, CircleShape)
                                    .clip(CircleShape)
                                    .border(stroke.dp, Color.Black, CircleShape)
                            )
                            Spacer(modifier = Modifier.width(circleSpacing))
                        }
                        else {
                            Box(
                                modifier = Modifier.size(circleSize)
                            )
                            Spacer(modifier = Modifier.width(circleSpacing))
                        }
                    }
                }
            }
        }
    }
    val lineColor: Color = Color.Black
    val highlightedLineColor: Color = Color.Green
    val lineSpacing: Dp = 4.dp
    BoxWithConstraints ( modifier = Modifier.padding(vertical = 16.dp)) {
        val lineWidth = (maxWidth - (lineSpacing * 3) - (rowPadding * 2) - paddingStart - paddingEnd) / 4

        Row(
            modifier = Modifier
                .padding(horizontal = rowPadding)
                .padding(start = paddingStart, end = paddingEnd),
            verticalAlignment = Alignment.CenterVertically
        ) {
            metronomeTicks.forEachIndexed { _, tick ->
                val color = if (tick) highlightedLineColor else lineColor
                    Box(
                        modifier = Modifier
                            .height(5.dp)
                            .width(lineWidth)
                            .background(color)
                    )
                    Spacer(modifier = Modifier.width(lineSpacing))
            }
        }
    }
}








