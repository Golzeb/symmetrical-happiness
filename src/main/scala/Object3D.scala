import scala.math.sqrt

enum Object3D(_material: Material) extends Hittable {
  case Sphere(val center: Vec3f, val radius: Float, override val material: Material) extends Object3D(material)
  /*
  TODO
    Triangle points should be vertices that store additional data like UV/Color
    Extend material class to allow for special properties e.g. colored by vertex color or textured via UV
    and material texture
  */
  case Triangle(val a: Vec3f, val b: Vec3f, val c: Vec3f, override val material: Material) extends Object3D(material)

  def material: Material = _material

  def checkHit(ray: Ray): RayHit = this match {
    case Sphere(center, radius, _) => {
      val difference = center - ray.origin
      val a = ray.direction.dot(ray.direction)
      val b = -2.0f * ray.direction.dot(difference)
      val c = difference.dot(difference) - radius * radius
      val discriminant = b * b - 4.0f * a * c

      val t1 = (-b - sqrt(discriminant).toFloat) / (2.0f * a)
      val t2 = (-b + sqrt(discriminant).toFloat) / (2.0f * a)

      val tMin = t1.min(t2)

      if discriminant >= 0 && (t1 >= 0 || t2 >= 0) then RayHit.Hit(tMin, this) else RayHit.NoHit
    }
    case Triangle(a, b, c, _) => {
      val precision = 0.000001f

      val e1 = b - a
      val e2 = c - a

      val ray_cross_e2 = ray.direction.cross(e2)
      val det = e1.dot(ray_cross_e2)

      if det > -precision && det < precision then {
        return RayHit.NoHit
      }

      val inv_det = 1.0f / det
      val s = ray.origin - a
      val u = inv_det * s.dot(ray_cross_e2)

      if u < 0.0f || u > 1.0f then {
          return RayHit.NoHit
      }

      val s_cross_e1 = s.cross(e1)
      val v = inv_det * ray.direction.dot(s_cross_e1)

      if v < 0.0f || (u + v) > 1.0f then {
        return RayHit.NoHit
      }

      val t = inv_det * e2.dot(s_cross_e1)

      if t > precision then {
        RayHit.Hit(t, this)
      } else {
        RayHit.NoHit
      }
    }
    case _ => RayHit.NoHit
  }

  def normalVector(point: Vec3f): Vec3f = this match {
    case Sphere(center, _, _) => {
      (point - center).normalized.toVec3f
    }
    case _ => Vec3.zero[Float]
  }
}

