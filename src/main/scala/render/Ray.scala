package render

import math.Vec3f
import render.Object3D

class Ray(val origin: Vec3f, val direction: Vec3f) {
  def getPoint(t: Float): Vec3f = origin + (direction * t)
}

enum RayHit {
  case Hit(val t: Float, val object3d: Object3D, val normal: Vec3f, val frontFace: Boolean)
  case NoHit
}

object RayHitOrdering extends Ordering[RayHit] {
  override def compare(x: RayHit, y: RayHit): Int = {
    (x, y) match {
      case (RayHit.Hit(t1, _, _, _), RayHit.Hit(t2, _, _, _)) => {
        t1.compare(t2)
      }
      case (RayHit.Hit(_, _, _, _), RayHit.NoHit) => {
        -1
      }
      case (RayHit.NoHit, RayHit.Hit(_, _, _, _)) => {
        1
      }
      case _ => {
        0
      }
    }
  }
}
