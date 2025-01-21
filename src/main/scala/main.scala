import material.{DialectricMaterial, DiffuseMaterial, EmissionMaterial, MetalMaterial, TexturedDiffuseMaterial, TexturedMetalMaterial, VertexColourDiffuseMaterial}
import math.{Vec2, Vec3}
import render.{Camera, Object3D, RenderConfig, Vertex}

import java.io.File
import javax.imageio.ImageIO

def benchmark[T](fun: => T): T = {
  val start = System.currentTimeMillis()

  val out = fun

  val stop = System.currentTimeMillis()

  println(s"Time: ${stop - start}ms")

  out
}

@main
def main(width: Int, height: Int): Unit = {
  val camera = Camera(Vec3(0.0f, 0.0f, 1.0f), Vec3(0.0f, 0.0f, -1.0f), Vec2(width, height), 1.0f)

  val texture = ImageIO.read(new File("texture.jpg"))

  val objects = List(
    Object3D.Sphere(Vec3(0.0f, 0.0f, -1.2f), 0.5f, DiffuseMaterial(Vec3(0.1f, 0.2f, 0.5f))),
    Object3D.Sphere(Vec3(-1.0f, 0.0f, -1.0f), 0.5f, EmissionMaterial(Vec3(0.0f, 1.0f, 0.0f), Vec3.one[Float])),
    Object3D.Sphere(Vec3(-1.0f, 0.0f, -1.0f), 0.4f, DialectricMaterial(1.0f / 1.5f, Vec3(1.0f, 0.0f, 0.0f))),
    Object3D.Sphere(Vec3(1.0f, 0.0f, -1.0f), 0.5f, EmissionMaterial(Vec3(1.0f, 0.0f, 0.0f), Vec3(1.0f, 1.0f, 1.0f))),
    Object3D.Sphere(Vec3(0.0f, -100.5f, -1.0f), 100.0f, DiffuseMaterial(Vec3(0.8f, 0.8f, 0.0f))),
    Object3D.Triangle(
      Vertex(Vec3(-2.0f, -1.0f, -2.0f), Vec2(0.0f, 1.0f), Vec3(1.0f, 0.0f, 0.0f)),
      Vertex(Vec3(2.0f, -1.0f, -2.0f), Vec2(1.0f, 1.0f), Vec3(0.0f, 1.0f, 0.0f)),
      Vertex(Vec3(-2.0f, 3.0f, -2.0f), Vec2(0.0f, 0.0f), Vec3(0.0f, 0.0f, 1.0f)),
      TexturedMetalMaterial(Vec3.one[Float], texture, 0.4f)),
    Object3D.Triangle(
      Vertex(Vec3(2.0f, -1.0f, -2.0f), Vec2(1.0f, 1.0f), Vec3(1.0f, 0.0f, 0.0f)),
      Vertex(Vec3(2.0f, 3.0f, -2.0f), Vec2(1.0f, 0.0f), Vec3(0.0f, 1.0f, 0.0f)),
      Vertex(Vec3(-2.0f, 3.0f, -2.0f), Vec2(0.0f, 0.0f), Vec3(0.0f, 0.0f, 1.0f)),
      TexturedMetalMaterial(Vec3.one[Float], texture, 0.4f)),
  ).toArray

  val renderConfig = RenderConfig(100, 100)
  val colorData = benchmark { camera.render(objects, renderConfig) }

  println(s"Width: $width\nHeight: $height")

  //OutputDisplay.display(colorData)

  val output = new File("final.png")
  ImageIO.write(colorData.bufferedImage(), "png", output)
}
