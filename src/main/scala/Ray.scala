class Ray(val origin: Vec3f, val direction: Vec3f) {
  def getPoint(t: Float): Vec3f = origin + (t * direction)
}
