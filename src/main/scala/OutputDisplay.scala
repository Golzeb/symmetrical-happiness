import scala.swing._
import javax.swing.ImageIcon
import java.awt.image.BufferedImage

object OutputDisplay {
  def display(colorData: ColorData): Unit = {
    val img = colorData.bufferedImage();
    val nullIcon = new ImageIcon(new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB_PRE))

    Dialog.showMessage(title = "Output", message = new ImageIcon(img), icon = nullIcon)
  } 
}
