package material

import math.{Vec3, Vec3f}
import render.{Material, Object3D, Ray, RayHit}
import util.*

import java.awt.image.BufferedImage
import scala.math.sqrt

class TexturedDiffuseMaterial(albedo: Vec3f, texture: BufferedImage) extends Material {

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

        val uv = a.uv * aMult + b.uv * bMult + c.uv * cMult

        val rgb = texture.getRGB((0.0f.max(uv.x).min(1.0f) * texture.getWidth()).toInt, (0.0f.max(uv.y).min(1.0f) * texture.getHeight()).toInt)

        val (ri, gi, bi) = ((rgb >> 16) & 0xFF, (rgb >> 8) & 0xFF, rgb & 0xFF)
        val (rf, gf, bf) = (ri.toFloat / 255.0f, gi.toFloat / 255.0f, bi.toFloat / 255.0f)

        Vec3(rf, gf, bf)
      }
    }
  }
}
