package material

import scala.math.sqrt
import math.Vec3f
import render.{Material, Object3D, Ray, RayHit}
import util.{randomUnitVector, reflect, reflectance, refract}

class DiffuseMaterial(albedo: Vec3f) extends Material {

  override def scatter(ray: Ray, rayHit: RayHit): Option[Ray] = {
    rayHit match
      case RayHit.Hit(t, object3d, normal, frontFace) => {
        Some(Ray(ray.getPoint(t), normal + randomUnitVector()))
      }
      case RayHit.NoHit => None
  }
  override def albedo(object3D: Object3D, point: Vec3f): Vec3f = {
    albedo
  }
}
