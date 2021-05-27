import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.IntSize
import kotlin.math.pow
import kotlin.math.sqrt

sealed class SceneEntity {
    abstract fun update(scene: Scene)
}


data class Alien(
    var x: Float,
    var y: Float,
    val radius: Float = 30f,
    val color: Color = listOf(Color.Red, Color.Blue, Color.LightGray, Color.Magenta).random(),
    var isDead: Boolean = false
) : SceneEntity() {
    var canvasWidth: Float = Window.WIDTH_VALUE
    var horizontalDirection = 5
    var verticalDirection = 0
    private val edge: Float get() = canvasWidth

    override fun update(scene: Scene) {
        if (isDead) {
            scene.aliens.remove(this)
        }

        if (x == edge || x == 0f) {
            horizontalDirection *= -1
            y += radius / 2
        }

        x += horizontalDirection
        y += verticalDirection
    }

}

fun DrawScope.drawAlien(alien: Alien) {
    val canvasWidth = size.width
    val canvasHeight = size.height
    val centerX = canvasWidth / 2
    val centerY = canvasHeight / 2
    alien.canvasWidth = canvasWidth

    drawCircle(
        color = alien.color,
        radius = alien.radius,
        center = Offset(alien.x, alien.y)
    )
}

data class SpaceShip(
    var x: Float = 0f,
    var y: Float = 0f,
) : SceneEntity() {
    override fun update(scene: Scene) {

    }
}

fun DrawScope.drawSpaceShip(spaceShip: SpaceShip) {
    val canvasWidth = size.width
    val canvasHeight = size.height
    val centerX = canvasWidth / 2
    val centerY = canvasHeight / 2
    spaceShip.y = canvasHeight - 80f
    drawRect(
        color = shipColor,
        size = Size(50f, 80f),
        topLeft = Offset(spaceShip.x - 25f, spaceShip.y)
    )
}

data class Bullet(
    var x: Float = 0f,
    var y: Float = 0f,
) : SceneEntity() {
    override fun update(scene: Scene) {
        if (y < 0) {
            // clean up bullet
            scene.bullets.remove(this)
        }
        y -= 10
    }

    fun hits(alien: Alien): Boolean {
        val distance = sqrt((alien.y - y) * (alien.y - y) + (alien.x - x) * (alien.x - x))
        return distance < alien.radius
    }
}

fun DrawScope.drawBullet(bullet: Bullet) {
    val canvasWidth = size.width
    val canvasHeight = size.height
    val centerX = canvasWidth / 2
    val centerY = canvasHeight / 2

    drawRect(
        color = shipColor,
        size = Size(16f, 16f),
        topLeft = Offset(bullet.x, bullet.y)
    )
}