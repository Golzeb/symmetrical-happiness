package math

import scala.annotation.targetName
import scala.math.Numeric.Implicits.*
import scala.math.sqrt

class Vec2[T: Numeric](val x: T, val y: T) {
  val length: Double = sqrt(x.toDouble * x.toDouble + y.toDouble * y.toDouble)
  def normalized: Vec2d = Vec2(x.toDouble / length, y.toDouble / length)

  @targetName("add")
  def +(other: Vec2[T]): Vec2[T] = Vec2[T](this.x + other.x, this.y + other.y)
  @targetName("sub")
  def -(other: Vec2[T]): Vec2[T] = Vec2[T](this.x - other.x, this.y - other.y)
  def dot(other: Vec2[T]): T = this.x * other.x + this.y * other.y

  @targetName("scale")
  def *(scalar: T): Vec2[T] = Vec2[T](this.x * scalar, this.y * scalar)

  override def toString: String = s"Vec2(${x}, ${y})"
}

object Vec2 {
  def zero[T](implicit num: Numeric[T]): Vec2[T] = Vec2(num.zero, num.zero)
  def one[T](implicit num: Numeric[T]): Vec2[T] = Vec2(num.one, num.one)
}

type Vec2i = Vec2[Int]
type Vec2f = Vec2[Float]
type Vec2d = Vec2[Double]