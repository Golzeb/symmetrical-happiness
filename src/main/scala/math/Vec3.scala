package math

import scala.annotation.targetName
import scala.math.Numeric.Implicits.*
import scala.math.sqrt

class Vec3[T: Numeric] (val x: T, val y: T, val z: T) {
  val length: Double = sqrt(x.toDouble * x.toDouble + y.toDouble * y.toDouble + z.toDouble * z.toDouble)
  val lengthSquared: T = x * x + y * y * z * z
  def normalized: Vec3d = Vec3(x.toDouble / length, y.toDouble / length, z.toDouble / length)

  @targetName("add")
  def +(other: Vec3[T]): Vec3[T] = Vec3[T](this.x + other.x, this.y + other.y, this.z + other.z)
  @targetName("sub")
  def -(other: Vec3[T]): Vec3[T] = Vec3[T](this.x - other.x, this.y - other.y, this.z - other.z)
  def dot(other: Vec3[T]): T = this.x * other.x + this.y * other.y + this.z * other.z
  def cross(other: Vec3[T]): Vec3[T] = Vec3(this.y * other.z - this.z * other.y, this.z * other.x - this.x * other.z, this.x * other.y - this.y * other.x)

  @targetName("scale")
  def *(scalar: T): Vec3[T] = Vec3[T](this.x * scalar, this.y * scalar, this.z * scalar)
  @targetName("mult")
  def *(other: Vec3[T]): Vec3[T] = Vec3[T](this.x * other.x, this.y * other.y, this.z * other.z)

  def toVec3f: Vec3f = Vec3[Float](this.x.toFloat, this.y.toFloat, this.z.toFloat)

  override def toString: String = s"Vec3(${x}, ${y}, ${z})"
}

extension (f: Float) {
  @targetName("scaleVector")
  def *(vec: Vec3[Float]): Vec3[Float] = vec * f
}

object Vec3 {
  def zero[T](implicit num: Numeric[T]): Vec3[T] = Vec3(num.zero, num.zero, num.zero)
  def one[T](implicit num: Numeric[T]): Vec3[T] = Vec3(num.one, num.one, num.one)
  def fill[T: Numeric](v: T): Vec3[T] = Vec3(v, v, v)
}

type Vec3i = Vec3[Integer]
type Vec3f = Vec3[Float]
type Vec3d = Vec3[Double]
