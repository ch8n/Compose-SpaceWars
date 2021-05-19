import androidx.compose.desktop.Window
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.IntSize
import org.jetbrains.skija.Bitmap
import org.jetbrains.skija.IRect
import org.jetbrains.skija.TextLine


/**
 * To support instant preview (replacement for android's @Preview annotation)
 */
fun Preview(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Window(
        title = "Compose-SpaceWars-Debug",
        size = IntSize(Window.WIDTH, Window.HEIGHT),
        resizable = false,
        centered = true,
    ) {
        MaterialTheme() {
            Surface(modifier = modifier.fillMaxSize()) {
                content()
            }
        }
    }
}

fun Float.mapRange(fromRange: Pair<Float, Float>, toRange: Pair<Float, Float>): Float {
    val (minRange, maxRange) = fromRange
    val (minMappedRange, maxMappedRange) = toRange
    val rangePercentage = (this / maxRange) * 100
    val mappedValue = (rangePercentage / 100) * (minMappedRange + maxMappedRange)
    return mappedValue
}

fun DrawScope.drawBitmap(bitmap: Bitmap) {
    drawIntoCanvas { canvas ->
        canvas.nativeCanvas.drawBitmapRect(
            bitmap,
            IRect(0, 0, size.width.toInt(), size.height.toInt()).toRect()
        )
    }
}

fun DrawScope.drawText(text: String) {
}