package render

import math.Vec3f

import java.awt.image.BufferedImage

class ColourData(val data: Array[Vec3f], val width: Int, val height: Int) {
  def bufferedImage(): BufferedImage = {
    var img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB) 

    data.map(
      (a: Vec3f) => { 
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
  
    img
  }
}
