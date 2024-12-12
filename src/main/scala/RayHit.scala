import RayHit.NoHit

enum RayHit {
  case Hit(val t: Float, val object3d: Object3D)
  case NoHit
}

object RayHitOrdering extends Ordering[RayHit] {
  override def compare(x: RayHit, y: RayHit): Int = {
    (x, y) match {
      case (RayHit.Hit(t1, _), RayHit.Hit(t2, _)) => {
        t1.compare(t2)
      }
      case (RayHit.Hit(_, _), RayHit.NoHit) => {
        -1
      }
      case (RayHit.NoHit, RayHit.Hit(_, _)) => {
        1
      }
      case _ => {
        0
      }
    }
  }
}
