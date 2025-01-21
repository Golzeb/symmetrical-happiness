package render

import math.{Vec2i, Vec3, Vec3f}
import util.rand

import scala.collection.parallel.CollectionConverters.*
import scala.math.sqrt

class RenderConfig(val samplesPerPixel: Int, val maxBounces: Int)

class Camera(origin: Vec3f, direction: Vec3f, viewportSize: Vec2i, focalLength: Float) {
  def render(objects: Array[Object3D], renderConfig: RenderConfig): ColourData = {
    val aspectRatio = viewportSize.x.toFloat / viewportSize.y.toFloat

    val halfWidth = viewportSize.x / 2;
    val halfHeight = viewportSize.y / 2;

    val offsetX = 1.0f / viewportSize.x.toFloat;
    val offsetY = 1.0f / viewportSize.y.toFloat;

    def generateRays(target: Vec3f): Array[Ray] = 0.until(renderConfig.samplesPerPixel).par.map(_ => {
      Ray(origin, (target + Vec3(rand.between(-offsetX, offsetX), rand.between(-offsetY, offsetY), 0.0f)) - origin)
    }).toArray

    def getRayColor(ray: Ray): Vec3f = {
      def rec(ray: Ray, bounces: Int): Vec3f = {
        if bounces < renderConfig.maxBounces then {
          val hit = objects.map(obj => obj.checkHit(ray)).min(RayHitOrdering)
          hit match
            case RayHit.Hit(t, object3d, normal, frontFace) => {
              val material = object3d.material
              val color = material.albedo(object3d, ray.getPoint(t))
              val light = material.light(object3d, ray.getPoint(t))

              val nextRay = material.scatter(ray, hit)
              if nextRay.isDefined then {
                color * rec(nextRay.get, bounces + 1)
              } else {
                light
              }
            }
            case RayHit.NoHit => {
              val direction = ray.direction.normalized.toVec3f
              val a = 0.5f * (direction.y + 1.0f)

              (Vec3(0.5f, 0.7f, 1.0f) * a) + Vec3(1.0f, 1.0f, 1.0f) * (1.0f - a)

              Vec3.zero[Float]
            }
        } else {
          Vec3.one[Float]
        }
      }

      rec(ray, 0)
    }

    val rays = (for { i <- -halfWidth until halfWidth; j <- -halfHeight until halfHeight }
      yield (i.toFloat, j.toFloat))
      .par
      .map((a: (Float, Float)) => {
        val (x, y) = a
        generateRays(Vec3(origin.x + aspectRatio * x / halfWidth.toFloat, origin.y + y / (-halfHeight.toFloat), origin.z + direction.z * focalLength))
      }).toArray

    var currentIndex = 0;

    val data = rays.par.map(rays => {

        val out = rays.par.map(ray => getRayColor(ray) * (1.0f / renderConfig.samplesPerPixel)).toList.fold(Vec3.zero[Float])((x, y) => x + y)

        synchronized{
          currentIndex += 1;
          if currentIndex % (viewportSize.x * viewportSize.y / 10) == 0 then println(s"${currentIndex}/${viewportSize.x * viewportSize.y}")
        }
        out
    }).map(x => Vec3(sqrt(x.x), sqrt(x.y), sqrt(x.z)).toVec3f).toArray

    new ColourData(data, viewportSize.x, viewportSize.y)
  }
}
