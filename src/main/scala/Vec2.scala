import scala.annotation.targetName
import scala.math.sqrt
import scala.math.Numeric.Implicits.*

class Vec2[T: Numeric](val x: T, val y: T) {
  val length: Double = sqrt(x.toDouble * x.toDouble + y.toDouble * y.toDouble)
  def normalized: Vec2d = Vec2(x.toDouble / length, y.toDouble / length)

  @targetName("add")
  def +(other: Vec2[T]): Vec2[T] = Vec2[T](this.x + other.x, this.y + other.y)
  @targetName("sub")
  def -(other: Vec2[T]): Vec2[T] = Vec2[T](this.x - other.x, this.y - other.y)
  def dot(other: Vec2[T]): T = this.x * other.x + this.y * other.y
  
  override def toString: String = s"Vec2(${x}, ${y})"
}

type Vec2i = Vec2[Int]
type Vec2f = Vec2[Float]
type Vec2d = Vec2[Double]