import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import java.lang.Math.pow
import kotlin.math.pow
import kotlin.math.sqrt

sealed class SceneEntity {
    abstract fun update(scene: Scene)
}

fun randomX(canvasWidth: Int) = (0..canvasWidth).random().toFloat()
fun randomValue(max: Int) = (1..max).random().toFloat()
fun randomFallSpeed(z: Float) = z.mapRange(0f to 20f, 4f to 10f)
fun randomY() = (-500..-50).random().toFloat()
fun randomZ() = (1..10).random().toFloat()
fun randomDropThickness(z: Float) = z.mapRange(0f to 20f, 1f to 5f)

data class Star(
    var canvasWidth: Float = Window.WIDTH_VALUE,
    var canvasHeight: Float = Window.HEIGHT_VALUE,
    var x: Float = randomX(canvasWidth.toInt()),
    var y: Float = randomY(),
    var z: Float = randomZ(),
    var length: Float = randomX(3),
    var thickness: Float = randomDropThickness(z),
) : SceneEntity() {
    var fallSpeed: Float = randomFallSpeed(z)
    val height get() = canvasHeight

    override fun update(scene: Scene) {
        y += fallSpeed
        if (y > height) {
            reset()
        }
    }

    private fun reset() {
        x = randomX(canvasWidth.toInt())
        y = 0f
        length = randomValue(3)
        fallSpeed = randomFallSpeed(z)
        thickness = randomDropThickness(z)
    }

}


fun DrawScope.drawStar(star: Star) {
    val canvasWidth = size.width
    val canvasHeight = size.height
    star.canvasWidth = canvasWidth
    star.canvasHeight = canvasHeight
    drawLine(
        color = Color.White,
        start = Offset(star.x, star.y),
        end = Offset(star.x, star.y + star.length),
        strokeWidth = star.thickness,
    )
}


data class Alien(
    var x: Float,
    var y: Float,
    val radius: Float = 30f,
    val color: Color = listOf(Color.Red, Color.Blue, Color.LightGray, Color.Magenta).random(),
) : SceneEntity() {

    var isDead: Boolean = false
    var canvasWidth: Float = Window.WIDTH_VALUE
    private var horizontalDirection = 5
    private var verticalDirection = 0
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

fun DrawScope.drawAlien(alienImage: ImageBitmap, alien: Alien) {
    val canvasWidth = size.width
    val canvasHeight = size.height
    val centerX = canvasWidth / 2
    val centerY = canvasHeight / 2
    alien.canvasWidth = canvasWidth
    drawImage(alienImage, Offset(alien.x, alien.y))
}

data class SpaceShip(
    var x: Float = 0f,
    var y: Float = 0f,
) : SceneEntity() {
    override fun update(scene: Scene) {

    }
}

fun DrawScope.drawSpaceShip(jetpackImage: ImageBitmap, spaceShip: SpaceShip) {
    val canvasWidth = size.width
    val canvasHeight = size.height
    val centerX = canvasWidth / 2
    val centerY = canvasHeight / 2
    spaceShip.y = canvasHeight - 80f

    val bitmapX = spaceShip.x - jetpackImage.width.toFloat() / 2
    val bitmapY = spaceShip.y - jetpackImage.height.toFloat() / 2
    drawImage(jetpackImage, Offset(bitmapX, bitmapY))
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
        val distance = sqrt((y - alien.y).toDouble().pow(2) + (x - alien.x).toDouble().pow(2))
        return distance < (alien.radius * 2)
    }
}

fun DrawScope.drawBullet(bulletImage: ImageBitmap, bullet: Bullet) {
    val canvasWidth = size.width
    val canvasHeight = size.height
    val centerX = canvasWidth / 2
    val centerY = canvasHeight / 2

    val bitmapX = bullet.x - bulletImage.width.toFloat() / 2
    val bitmapY = bullet.y - bulletImage.height.toFloat() / 2
    drawImage(bulletImage, Offset(bitmapX, bitmapY))
}