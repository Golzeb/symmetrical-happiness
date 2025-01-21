package material

import scala.math.sqrt
import math.Vec3f
import render.{Material, Object3D, Ray, RayHit}
import util.{calculateArea, randomUnitVector, reflect, reflectance, refract}

class VertexColourDiffuseMaterial(albedo: Vec3f) extends Material {

  override def scatter(ray: Ray, rayHit: RayHit): Option[Ray] = {
    rayHit match
      case RayHit.Hit(t, object3d, normal, frontFace) => {
        Some(Ray(ray.getPoint(t), normal + randomUnitVector()))
      }
      case RayHit.NoHit => None
  }
  override def albedo(object3D: Object3D, point: Vec3f): Vec3f = {
    object3D match {
      case Object3D.Sphere(_, _, _) => {
        albedo
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

        val rgb = a.colour * aMult + b.colour * bMult + c.colour * cMult

        if rgb.x.isNaN then {
          //println(s"$abc $pbc $apc $abp $aPos $bPos $cPos $point")
        }

        rgb
      }
    }
  }
}
