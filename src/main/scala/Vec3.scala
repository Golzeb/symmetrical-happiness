import scala.math.sqrt

class Vec3(val x: Double, val y: Double, val z: Double) {
  val length: Double = sqrt(x * x + y * y + z * z) 
  def normalized: Vec3 = Vec3(x / length, y / length, z / length)
}
