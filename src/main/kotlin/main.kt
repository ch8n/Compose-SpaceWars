import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerMoveFilter
import androidx.compose.ui.unit.dp

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
    val aliens = mutableListOf<Alien>()
    private val spaceShip = SpaceShip()
    val bullets = mutableListOf<Bullet>()
    fun setupScene() {
        sceneEntity.clear()
        repeat(8) { aliens.add(Alien(x = 80f + (it * 100f), y = 60f)) }
        sceneEntity.addAll(aliens)
        sceneEntity.add(spaceShip)
    }

    fun update() {
        for (entity in sceneEntity) {
            entity.update(this)
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun render(frameState: State<Long>) {
        Surface(color = Color.White) {
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.Black)
                    .combinedClickable(
                        onClick = {
                            val bullet = Bullet(spaceShip.x, spaceShip.y) // y needs to be height
                            sceneEntity.add(bullet)
                            bullets.add(bullet)
                        }
                    )
                    .pointerMoveFilter(onMove = {
                        val (x, y) = it
                        spaceShip.x = x
                        true
                    }),
            ) {
                val stepFrame = frameState.value

                for (alien in aliens) {
                    drawAlien(alien)
                    alien.isDead = bullets.any { it.hits(alien) }
                }
                drawSpaceShip(spaceShip)
                for (bullet in bullets) {
                    drawBullet(bullet)
                }

                if (aliens.isEmpty()) {
                    repeat(8) { aliens.add(Alien(x = 80f + (it * 100f), y = 60f)) }
                    sceneEntity.addAll(aliens)
                }
            }
        }
    }
}


