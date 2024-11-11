class Ray(origin: Vec3, direction: Vec3)
class Material(albedo: Vec3)

enum RayHit {
  case Hit(hit: Boolean, t: Double, object3d: Object3D)
  case NoHit
}

trait Hittable {
  def checkHit(ray: Ray): RayHit
}

enum Object3D(material: Material) extends Hittable {
  case Sphere(center: Vec3, radius: Double, material: Material) extends Object3D(material)

  def checkHit(ray: Ray): RayHit = this match {
    case Sphere(center, radius, _) => RayHit.NoHit
    case _ => RayHit.NoHit
  }
}

def generateDebugColors(width: Int, height: Int) = {
  for { i <- 0 until width; j <- 0 until height } yield Vec3(i / width.toDouble, 0.0, j / height.toDouble)
}

@main
def main(width: Int, height: Int): Unit = {
  val camera = Camera(Vec3(0, 0, 0), Vec3(0, 0, -1))
  OutputDisplay.display(generateDebugColors(width, height).toArray, width, height)

  println(s"Hello world! ${width}x${height}")
}
