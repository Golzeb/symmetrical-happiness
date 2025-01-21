package material

import math.Vec3f
import render.{Material, Object3D, Ray, RayHit}

class EmissionMaterial(emission: Vec3f, albedo: Vec3f) extends Material {

  override def scatter(ray: Ray, rayHit: RayHit): Option[Ray] = {
    None
  }

  override def albedo(object3D: Object3D, point: Vec3f): Vec3f = {
    albedo
  }

  override def light(object3D: Object3D, point: Vec3f): Vec3f = {
    emission
  }
}
