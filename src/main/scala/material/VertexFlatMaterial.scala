package material

import scala.math.sqrt
import math.{Vec3, Vec3f}
import render.{Material, Object3D, Ray, RayHit}
import util.{calculateArea, randomUnitVector, reflect, reflectance, refract}

class VertexFlatMaterial extends Material {
  override def scatter(ray: Ray, rayHit: RayHit): Option[Ray] = {
    None
  }

  override def albedo(object3D: Object3D, point: Vec3f): Vec3f = {
    object3D match {
      case Object3D.Sphere(_, _, _) => {
        Vec3.one[Float]
      }
      case Object3D.Triangle(a, b, c, _) => {
        val aPos = a.position
        val bPos = b.position
        val cPos = c.position

        val abc = calculateArea(aPos, bPos, cPos)
        val pbc = calculateArea(point, bPos, cPos)
        val apc = calculateArea(aPos, point, cPos)
        val abp = calculateArea(aPos, bPos, point)

        val aMult = pbc / abc
        val bMult = apc / abc
        val cMult = abp / abc

        a.colour * aMult + b.colour * bMult + c.colour * cMult
      }
    }
  }
}
