import org.scalatest.funsuite.AnyFunSuite
import scala.math.sqrt


extension (self: Double) {
  def precision: Double = 0.0000000000001;
  infix def deq(other: Double): Boolean = (self - other).abs < precision 
}

extension (self: Vec3) {
  infix def veq(other: Vec3): Boolean = (self.x deq other.x) && (self.y deq other.y) && (self.z deq other.z)
}

class Vec3Test extends AnyFunSuite {
  test("Vec3.+") {
    val a = Vec3(1.0, -1.0, 1.0)
    val b = Vec3(-1.0, 1.0, -1.0) 
    
    assert((a + b) veq Vec3(0.0, 0.0, 0.0))
  }

  test("Vec3.-") {
    val a = Vec3(1.0, -1.0, 1.0)
    val b = Vec3(-1.0, 1.0, -1.0) 
    
    assert((a - b) veq Vec3(2.0, -2.0, 2.0))
  }

  test("Vec3.dot") {
    val a = Vec3(1.0, -1.0, 1.0)
    val b = Vec3(-1.0, 1.0, -1.0) 
 
    assert(a.dot(b) deq -3.0) 
  }

  test("Vec3.normalized") {
    val a = Vec3(3.0, 0.0, 0.0)
    val b = Vec3(0.0, 4.0, 0.0)
    val c = Vec3(0.0, 0.0, 5.2)

    assert(a.normalized veq Vec3(1.0, 0.0, 0.0))
    assert(b.normalized veq Vec3(0.0, 1.0, 0.0))
    assert(c.normalized veq Vec3(0.0, 0.0, 1.0))
  }

  test("Vec3.length") {
    val a = Vec3(1.0, 0.0, 0.0)
    val b = Vec3(0.0, 2.0, 0.0)
    val c = Vec3(0.0, 0.0, 3.4)

    assert(a.length deq 1.0)
    assert(b.length deq 2.0)
    assert(c.length deq 3.4)
  }
}
