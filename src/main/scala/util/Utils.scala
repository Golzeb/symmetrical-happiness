package util

import math.{Vec3, Vec3f}

import scala.annotation.targetName
import scala.math.{abs, pow, sqrt}
import scala.util.Random

val rand = new Random()

extension (f: Float) {
  @targetName("scaleVector")
  def *(vec: Vec3[Float]): Vec3[Float] = vec * f
}

def calculateArea(a: Vec3f, b: Vec3f, c: Vec3f): Float = {
  val ab = (a - b)
  val ac = (a - c)

  val x1 = ab.y * ac.z - ab.z * ac.y
  val x2 = ab.z * ac.x - ab.x * ac.z
  val x3 = ab.x * ac.y - ab.y * ac.x
  val s = 0.5f * sqrt(x1 * x1 + x2 * x2 + x3 * x3).toFloat

  s
}

def randomUnitVector(): Vec3f = {
  val vector = Vec3[Float](rand.between(-1.0f, 1.0f), rand.between(-1.0f, 1.0f), rand.between(-1.0f, 1.0f))
  vector.normalized.toVec3f
}

def reflect(vector: Vec3f, normal: Vec3f) = {
  vector - 2.0f * normal.dot(vector) * normal
}

def refract(uv: Vec3f, normal: Vec3f, etaiEtat: Float): Vec3f = {
  val k = 1.0f - etaiEtat * etaiEtat * (1.0f - uv.dot(normal) * uv.dot(normal))

  if k < 0.0f then {
    Vec3.zero[Float]
  } else {
    etaiEtat * uv - (etaiEtat * uv.dot(normal) + sqrt(k).toFloat) * normal
  }
}

def reflectance(cosine: Float, refractionIndex: Float): Float = {
  val t = (1.0f - refractionIndex) / (1.0f + refractionIndex)
  val r0 = t * t

  r0 + (1.0f - r0) * pow(1.0f - cosine, 5).toFloat
}
