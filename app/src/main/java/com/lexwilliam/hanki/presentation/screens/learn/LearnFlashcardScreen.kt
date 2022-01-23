package com.lexwilliam.hanki.presentation.screens.learn

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.lexwilliam.hanki.model.FlashcardPresentation
import com.lexwilliam.hanki.presentation.common.fakeFlashcard
import com.lexwilliam.hanki.presentation.common.fakeFlashcardList
import java.util.*
import kotlin.math.roundToInt

@Composable
fun LearnFlashcardScreen() {

}

@Composable
fun FlashcardToolBar() {

}

@Composable
fun FlashcardCount(
    totalFinish: Int,
    totalFlashcard: Int
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "$totalFinish/$totalFlashcard")
    }
}

@Composable
fun FlashcardStack(
    flashcards: List<FlashcardPresentation>
) {
    val shuffled = flashcards.shuffled()
    var stack = ArrayDeque<FlashcardPresentation>()
    var knowIt = ArrayDeque<FlashcardPresentation>()
    var forgotIt = ArrayDeque<FlashcardPresentation>()

    shuffled.forEach { flashcard ->
        stack.push(flashcard)
    }

    var currentCard by remember { mutableStateOf(stack.peek()) }
    var cardState by remember { mutableStateOf(false) }
    FlashcardView(flashcard = currentCard, rotated = cardState, onRotate = { cardState = it })
    

}

enum class BoxState { Front, Back }

@Composable
fun FlashcardView(
    flashcard: FlashcardPresentation,
    rotated: Boolean,
    onRotate: (Boolean) -> Unit
) {
    val transitionData = updateTransitionData(
        if (rotated) BoxState.Back else BoxState.Front
    )

    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }

    Column {
        Text(text = offsetX.toString())
        Text(text = offsetY.toString())
        Card(
            Modifier
                .fillMaxWidth(.8f)
                .fillMaxHeight(.6f)
                .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        change.consumeAllChanges()
                        offsetX += dragAmount.x
                        offsetY += dragAmount.y
                    }
                }
                .graphicsLayer {
                    rotationY = transitionData.rotation
                    cameraDistance = 8 * density
                }
                .clickable { onRotate(!rotated) },
            backgroundColor = MaterialTheme.colors.background
        )
        {
            Column(
                Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = if (rotated) flashcard.answer else flashcard.question,
                    modifier = Modifier
                        .graphicsLayer {
                            alpha =
                                if (rotated) transitionData.animateBack else transitionData.animateFront
                            rotationY = transitionData.rotation
                        }
                )
            }

        }
    }
}


private class TransitionData(
    rotation: State<Float>,
    animateFront: State<Float>,
    animateBack: State<Float>
) {
    val rotation by rotation
    val animateFront by animateFront
    val animateBack by animateBack
}


@Composable
private fun updateTransitionData(boxState: BoxState): TransitionData {
    val transition = updateTransition(boxState, label = "")
    val rotation = transition.animateFloat(
        transitionSpec = {
            tween(500)
        },
        label = ""
    ) { state ->
        when (state) {
            BoxState.Front -> 0f
            BoxState.Back -> 180f
        }
    }

    val animateFront = transition.animateFloat(
        transitionSpec = {
            tween(500)
        },
        label = ""
    ) { state ->
        when (state) {
            BoxState.Front -> 1f
            BoxState.Back -> 0f
        }
    }
    val animateBack = transition.animateFloat(
        transitionSpec = {
            tween(500)
        },
        label = ""
    ) { state ->
        when (state) {
            BoxState.Front -> 0f
            BoxState.Back -> 1f
        }
    }

    return remember(transition) { TransitionData(rotation, animateFront, animateBack) }
}

@ExperimentalMaterialApi
@Preview
@Composable
fun SlideTestPreview() {
    val width = 96.dp
    val squareSize = 48.dp

    val swipeableState = rememberSwipeableState(0)
    val sizePx = with(LocalDensity.current) { squareSize.toPx() }
    val anchors = mapOf(0f to 0, sizePx to 1) // Maps anchor points (in px) to states0

    Box(
        modifier = Modifier
            .width(width)
            .swipeable(
                state = swipeableState,
                anchors = anchors,
                thresholds = { _, _ -> FractionalThreshold(0.3f) },
                orientation = Orientation.Horizontal
            )
            .background(Color.LightGray)
    ) {
        Box(
            Modifier
                .offset { IntOffset(swipeableState.offset.value.roundToInt(), 0) }
                .size(squareSize)
                .background(Color.DarkGray)
        )
    }
}

@Preview
@Composable
fun AnimatingBoxPreview() {
    var state by remember { mutableStateOf(false) }
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        FlashcardView(flashcard = fakeFlashcard, rotated = state, onRotate = { state = it })
    }
}