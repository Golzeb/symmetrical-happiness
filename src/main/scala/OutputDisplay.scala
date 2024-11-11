import scala.swing._
import javax.swing.ImageIcon
import java.awt.image.BufferedImage

object OutputDisplay {
  def display(colorData: Array[Vec3], width: Int, height: Int): Unit = {
    var img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)
    colorData.map(
      (a: Vec3) => { 
        val intValues = (
          (255.0 * a.x).toInt, 
          (255.0 * a.y).toInt, 
          (255.0 * a.z).toInt
        )

        (intValues(0) << 16) + (intValues(1) << 8) + intValues(2)
      }
    ).zip(
      for { i <- 0 until width; j <- 0 until height } yield (i, j)
    ).foreach(
      (color, index) => img.setRGB(index(0), index(1), color)
    )

    val nullIcon = new ImageIcon(new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB_PRE))

    Dialog.showMessage(title = "Output", message = new ImageIcon(img), icon = nullIcon)
  } 
}
