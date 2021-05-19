import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.IntSize

sealed class SceneEntity {
    abstract fun update(scene: Scene)
}


data class Alien(
    val canvasHeight: Float = 0f,
    val canvasWidth: Float = 0f,
    val x: Float = (0..canvasWidth.toInt()).random().toFloat(),
    val y: Float = 100f,
    val color: Color = listOf(Color.Red, Color.Blue, Color.LightGray, Color.Magenta).random()
) : SceneEntity() {

    override fun update(scene: Scene) {

    }

}

fun DrawScope.drawAlien(alien: Alien) {
    val canvasWidth = size.width
    val canvasHeight = size.height
    val centerX = canvasWidth / 2
    val centerY = canvasHeight / 2

    drawCircle(
        color = alien.color,
        radius = 60f,
        center = Offset(alien.x, alien.y)
    )
}

data class SpaceShip(
    val x: Float,
    val y: Float,
) : SceneEntity() {
    override fun update(scene: Scene) {

    }
}

fun DrawScope.drawSpaceShip(mouseXY: Pair<Float, Float>) {
    val canvasWidth = size.width
    val canvasHeight = size.height
    val centerX = canvasWidth / 2
    val centerY = canvasHeight / 2

    drawRect(
        color = shipColor,
        size = Size(50f, 80f),
        topLeft = Offset(mouseXY.first - 25f, canvasHeight - 80f)
    )
}