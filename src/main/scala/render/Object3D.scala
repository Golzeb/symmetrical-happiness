package render

import math.{Vec2f, Vec3, Vec3f}

import scala.math.{abs, sqrt}

class Vertex(val position: Vec3f, val uv: Vec2f, val colour: Vec3f)

trait Hittable {
  def checkHit(ray: Ray): RayHit
}

trait Material {
  def scatter(ray: Ray, rayHit: RayHit): Option[Ray]
  def albedo(object3D: Object3D, point: Vec3f): Vec3f
  def light(object3D: Object3D, point: Vec3f): Vec3f = Vec3.zero[Float]
}
//class Material(val albedo: Vec3f, val vertexColourBehaviour: MaterialVertexColourBehaviour = Ignore, materialType: MaterialType = MaterialType.Flat)

enum Object3D(_material: Material) extends Hittable {
  case Sphere(val center: Vec3f, val radius: Float, override val material: Material) extends Object3D(material)
  /*
  TODO
    Triangle points should be vertices that store additional data like UV/Color
    Extend material class to allow for special properties e.g. colored by vertex color or textured via UV
    and material texture
  */
  case Triangle(val a: Vertex, val b: Vertex, val c: Vertex, override val material: Material) extends Object3D(material)

  def material: Material = _material

  def checkHit(ray: Ray): RayHit = this match {
    case Sphere(center, radius, _) => {
      val difference = center - ray.origin
      val a = ray.direction.dot(ray.direction)
      val b = -2.0f * ray.direction.dot(difference)
      val c = difference.dot(difference) - radius * radius
      val discriminant = b * b - 4.0f * a * c

      val padding = 0.001f

      if discriminant >= 0 then {
        val sqrtd = sqrt(discriminant).toFloat

        val t1 = (-b - sqrtd) / (2.0f * a)
        val t2 = (-b + sqrtd) / (2.0f * a)

        if t1 >= padding then {
          val outwardNormal = this.normal(ray.getPoint(t1))
          val frontFace = ray.direction.dot(outwardNormal) < 0.0f

          val normal = if frontFace then outwardNormal else (outwardNormal * -1.0f)

          RayHit.Hit(t1, this, normal, frontFace)
        } else if t2 >= padding then {
          val outwardNormal = this.normal(ray.getPoint(t2))
          val frontFace = ray.direction.dot(outwardNormal) < 0.0f

          val normal = if frontFace then outwardNormal else (outwardNormal * -1.0f)

          RayHit.Hit(t2, this, normal, frontFace)
        } else {
          RayHit.NoHit
        }
      } else {
        RayHit.NoHit
      }
    }
    case Triangle(aVert, bVert, cVert, _) => {
      val precision = 0.00000001f

      val a = aVert.position
      val b = bVert.position
      val c = cVert.position

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
        val outwardNormal = this.normal(ray.getPoint(t))
        val frontFace = ray.direction.dot(outwardNormal) < 0.0f

        val normal = if frontFace then outwardNormal else (outwardNormal * -1.0f)

        RayHit.Hit(t, this, normal, frontFace)
      } else {
        RayHit.NoHit
      }
    }
    case _ => RayHit.NoHit
  }

  def normal(point: Vec3f): Vec3f = this match {
    case Sphere(center, radius, _) => {
      (point - center) * (1.0f / radius)
    }
    case Triangle(a, b, c, _) => {
      val ab = (a.position - b.position)
      val ac = (a.position - c.position)

      ab.cross(ac).normalized.toVec3f
    }
    case _ => Vec3.zero[Float]
  }
}

