import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

object Window {
    val DEBUG = true
    val WIDTH = if (DEBUG) 800 else 1200
    val HEIGHT = if (DEBUG) 400 else 800
    val WIDTH_VALUE = WIDTH.dp.value
    val HEIGHT_VALUE = HEIGHT.dp.value
}

val backgroundColor = Color(0xffe6e6fa)
val shipColor = Color(0xff8a2be2)
