import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.input.pointer.pointerMoveFilter
import androidx.compose.ui.res.imageResource

fun main() {

    Preview {
        val assets = listOf(
            imageResource("images/bullet.png"),
            imageResource("images/jetpack.png"),
            imageResource("images/alien.png"),
            imageResource("images/alienC.png"),
            imageResource("images/alienH.png"),
            imageResource("images/alien8.png"),
            imageResource("images/alienN.png")
        )

        val scene = remember { Scene() }
        scene.setupScene()
        val frameState = StepFrame {
            scene.update()
        }
        scene.render(frameState, assets)
    }
}


class Scene {

    private var sceneEntity = mutableStateListOf<SceneEntity>()
    val aliens = mutableListOf<Alien>()
    val stars = mutableListOf<Star>()
    private val spaceShip = SpaceShip()
    val bullets = mutableListOf<Bullet>()
    var alienCount = 8
    fun setupScene() {
        sceneEntity.clear()

        repeat(alienCount) { aliens.add(Alien(x = 80f + (it * 100f), y = 60f)) }
        sceneEntity.addAll(aliens)

        repeat(800 * 2) { stars.add(Star()) }
        sceneEntity.addAll(stars)

        sceneEntity.add(spaceShip)
    }

    fun update() {
        for (entity in sceneEntity) {
            entity.update(this)
        }
    }


    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun render(frameState: State<Long>, assets: List<ImageBitmap>) {

        Surface(color = Color.White) {
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.Black)
                    .combinedClickable(
                        onClick = {
                            val bullet = Bullet(spaceShip.x, spaceShip.y)
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

                for (star in stars) {
                    drawStar(star)
                }

                var nameCounter = 2
                for ((index, alien) in aliens.withIndex()) {
                    val alienBitmap = when {
                        index % 2 == 0 -> {
                            nameCounter += 1
                            assets.getOrNull(nameCounter) ?: assets.get(2)
                        }
                        else -> assets.get(2)
                    }
                    drawAlien(alienBitmap, alien)
                    alien.isDead = bullets.any { it.hits(alien) }
                }

                drawSpaceShip(assets.get(1), spaceShip)

                for (bullet in bullets) {
                    drawBullet(assets.get(1), bullet)
                }

                if (aliens.isEmpty()) {
                    alienCount += 2
                    nameCounter = 2
                    repeat(alienCount) { aliens.add(Alien(x = 80f + (it * 100f), y = 60f)) }
                    sceneEntity.addAll(aliens)
                }
            }
        }
    }
}


