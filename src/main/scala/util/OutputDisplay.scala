package util

import render.ColourData

import java.awt.image.BufferedImage
import javax.swing.ImageIcon
import scala.swing.*

object OutputDisplay {
  def display(colorData: ColourData): Unit = {
    val img = colorData.bufferedImage();
    val nullIcon = new ImageIcon(new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB_PRE))

    Dialog.showMessage(title = "Output", message = new ImageIcon(img), icon = nullIcon)
  } 
}
