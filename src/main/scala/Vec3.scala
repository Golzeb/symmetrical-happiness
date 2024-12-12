import scala.annotation.targetName
import scala.math.sqrt
import scala.math.Numeric.Implicits.*

class Vec3[T: Numeric] (val x: T, val y: T, val z: T) {
  val length: Double = sqrt(x.toDouble * x.toDouble + y.toDouble * y.toDouble + z.toDouble * z.toDouble)
  def normalized: Vec3d = Vec3(x.toDouble / length, y.toDouble / length, z.toDouble / length)

  @targetName("add")
  def +(other: Vec3[T]): Vec3[T] = Vec3[T](this.x + other.x, this.y + other.y, this.z + other.z)
  @targetName("sub")
  def -(other: Vec3[T]): Vec3[T] = Vec3[T](this.x - other.x, this.y - other.y, this.z - other.z)
  def dot(other: Vec3[T]): T = this.x * other.x + this.y * other.y + this.z * other.z
  def cross(other: Vec3[T]): Vec3[T] = Vec3(this.y * other.z - this.z * other.y, this.z * other.x - this.x * other.z, this.x * other.y - this.y * other.x)

  @targetName("scale")
  def *(scalar: T): Vec3[T] = Vec3[T](this.x * scalar, this.y * scalar, this.z * scalar)

  def toVec3f: Vec3f = Vec3[Float](this.x.toFloat, this.y.toFloat, this.z.toFloat)

  override def toString: String = s"Vec3(${x}, ${y}, ${z})"
}

extension (f: Float) {
  @targetName("scaleVector")
  def *(vec: Vec3[Float]): Vec3[Float] = vec * f
}

object Vec3 {
  def zero[T: Numeric]: Vec3[T] = Vec3(0.asInstanceOf[T], 0.asInstanceOf[T], 0.asInstanceOf[T])
  def one[T: Numeric]: Vec3[T] = Vec3(1.asInstanceOf[T], 1.asInstanceOf[T], 1.asInstanceOf[T])
}

type Vec3i = Vec3[Integer]
type Vec3f = Vec3[Float]
type Vec3d = Vec3[Double]
