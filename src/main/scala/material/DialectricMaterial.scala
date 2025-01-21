package material

import scala.math.sqrt
import math.Vec3f
import render.{Material, Object3D, Ray, RayHit}
import util.{rand, randomUnitVector, reflect, reflectance, refract}

class DialectricMaterial(refractionIndex: Float, albedo: Vec3f) extends Material {

  override def scatter(ray: Ray, rayHit: RayHit): Option[Ray] = {
    rayHit match
      case RayHit.Hit(t, object3d, normal, frontFace) => {
        val point = ray.getPoint(t)

        val unitDirection = ray.direction.normalized.toVec3f

        val cosTheta = 1.0f.min((unitDirection * -1.0f).dot(normal))
        val sinTheta = sqrt(1.0f - cosTheta * cosTheta)

        val ri = if frontFace then (1.0f / refractionIndex) else refractionIndex

        val cannotRefract = ri * sinTheta > 1.0f;

        val direction = if cannotRefract || reflectance(cosTheta, ri) > rand.nextFloat() then {
          reflect(unitDirection, normal)
        } else {
          refract(unitDirection, normal, ri)
        }

        Some(Ray(point, direction))
      }
      case RayHit.NoHit => None
  }

  override def albedo(object3D: Object3D, point: Vec3f): Vec3f = {
    albedo
  }
}
