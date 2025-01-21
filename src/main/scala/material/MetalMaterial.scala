package material

import scala.math.sqrt
import math.Vec3f
import render.{Material, Object3D, Ray, RayHit}
import util.{randomUnitVector, reflect, reflectance, refract}

class MetalMaterial(albedo: Vec3f, fuzz: Float = 0.0f) extends Material {
  override def scatter(ray: Ray, rayHit: RayHit): Option[Ray] = {
    rayHit match
      case RayHit.Hit(t, object3d, normal, frontFace) => {
        Some(Ray(ray.getPoint(t), reflect(ray.direction, normal) + (randomUnitVector() * fuzz)))
      }
      case RayHit.NoHit => None
  }
  override def albedo(object3D: Object3D, point: Vec3f): Vec3f = {
    albedo
  }
}
