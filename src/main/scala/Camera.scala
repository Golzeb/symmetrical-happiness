import scala.collection.parallel.CollectionConverters._

class Camera(origin: Vec3f, direction: Vec3f, viewportSize: Vec2i, focalLength: Float) {
  def render(objects: Array[Object3D], iterations: Int): ColorData = {
    val aspectRatio = viewportSize.x.toFloat / viewportSize.y.toFloat

    val halfWidth = viewportSize.x / 2;
    val halfHeight = viewportSize.y / 2;

    def generateRay(direction: Vec3f): Ray = Ray(origin, direction - origin)
    def getClosestHit(ray: Ray): RayHit =
      objects.par.map(obj => obj.checkHit(ray))
        .min(RayHitOrdering)

    val rays = (for { i <- -halfWidth until halfWidth; j <- -halfHeight until halfHeight }
      yield (i.toFloat, j.toFloat))
      .par
      .map((a: (Float, Float)) => {
        val (x, y) = a
        generateRay(Vec3(aspectRatio * x / halfWidth.toFloat, y / (-halfHeight.toFloat), origin.z + direction.z * focalLength))
      }).toArray

    val data = rays.par.map(ray => {
      getClosestHit(ray) match
        case RayHit.Hit(t, object3d) => /*object3d.material.albedo*/ Vec3(1.0f, 1.0f, 1.0f) * (1.0f / t) //0.5f * (object3d.normalVector(ray.getPoint(t)) + Vec3(1.0f, 1.0f, 1.0f))
        case _ => Vec3[Float](0.0f, 0.0f, 0.0f)
    }).toArray

    new ColorData(data, viewportSize.x, viewportSize.y)
  }
}
