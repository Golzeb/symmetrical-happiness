import java.io.File
import javax.imageio.ImageIO

class Material(val albedo: Vec3f)

def generateDebugColors(width: Int, height: Int) = {
  for { i <- 0 until width; j <- 0 until height } yield Vec3(i / width.toFloat, 0.0f, j / height.toFloat)
}

def benchmark[T](fun: => T): T = {
  val start = System.currentTimeMillis()

  val out = fun

  val stop = System.currentTimeMillis()

  println(s"Time: ${stop - start}ms")

  out
}

@main
def main(width: Int, height: Int): Unit = {
  val camera = Camera(Vec3(0.0f, -1.0f, -15.0f), Vec3(0.0f, 0.0f, 1.0f), Vec2(width, height), 5.0f)

  /*val objects = List(
    //Object3D.Sphere(Vec3(0.0f, 2.0f, -15.0f), 1.0f, Material(Vec3(0.0f, 1.0f, 0.0f))),
    //Object3D.Sphere(Vec3(0.0f, 0.0f, -15.0f), 2.0f, Material(Vec3(1.0f, 0.0f, 0.0f))),
    //Object3D.Sphere(Vec3(4.0f, 2.0f, -15.0f), 1.0f, Material(Vec3(0.0f, 0.0f, 1.0f))),
    Object3D.Triangle(Vec3(-2.0f, 0.0f, -15.0f), Vec3(2.0f, 0.0f, -15.0f), Vec3(0.0f, 2.0f, -25.0f), Material(Vec3(1.0f, 1.0f, 0.0f)))
  ).toArray*/

  val objects = OBJLoader.loadOBJ("teapot.obj")

  val colorData = benchmark { camera.render(objects, 1) }

  println(s"Width: $width\nHeight: $height")

  OutputDisplay.display(colorData)

  val output = new File("final.png")
  ImageIO.write(colorData.bufferedImage(), "png", output)
}
