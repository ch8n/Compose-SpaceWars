import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerMoveFilter

fun main() {

    Preview {
        val scene = remember { Scene() }
        scene.setupScene()
        val frameState = StepFrame {
            scene.update()
        }
        scene.render(frameState)
    }
}


class Scene {

    private var sceneEntity = mutableStateListOf<SceneEntity>()


    fun setupScene() {
        sceneEntity.clear()

        //sceneEntity.addAll(drops)
    }


    fun update() {
        for (entity in sceneEntity) {
            entity.update(this)
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun render(frameState: State<Long>) {
        var mouseXY by remember { mutableStateOf(0f to 0f) }
        Box {
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.Black)
                    .combinedClickable(
                        onClick = {

                        }
                    )
                    .pointerMoveFilter(onMove = {
                        val (x, y) = it
                        mouseXY = x to y
                        true
                    }),
            ) {

                drawAlien(Alien(size.height, size.width))
                val stepFrame = frameState.value
                drawSpaceShip(mouseXY)
            }
        }
    }
}


