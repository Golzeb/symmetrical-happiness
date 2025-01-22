import material.{DialectricMaterial, DiffuseMaterial, EmissionMaterial, MetalMaterial, TexturedDiffuseMaterial, TexturedMetalMaterial, VertexColourDiffuseMaterial}
import math.{Vec2, Vec3}
import render.{Camera, Object3D, RenderConfig, Vertex}
import util.{OBJLoader, rand}

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
  val camera = Camera(Vec3(0.0f, 0.7f, 5.0f), Vec3(0.0f, 0.0f, -1.0f), Vec2(width, height), 1.0f)

  val texture = ImageIO.read(new File("texture.jpg"))

  val textureHeight = 4.0f * texture.getHeight.toFloat / texture.getWidth.toFloat - 1.0f

  /*val objects = List(
    Object3D.Sphere(Vec3(0.0f, 120.5f, -1.0f), 100.0f, EmissionMaterial(Vec3(1.0f, 1.0f, 1.0f), Vec3(1.0f, 1.0f, 1.0f))),
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
  ).toArray*/

  val world = ((-5).until(5).flatMap(x => {
    (-5).until(5).map(y => {
      val mat = rand.nextFloat()
      val center = Vec3(x + 0.9f * rand.nextFloat(), 0.2f, y + 0.9f * rand.nextFloat())


      if mat < 0.5 then {
        val albedo = Vec3(rand.nextFloat(), rand.nextFloat(), rand.nextFloat())

        Object3D.Sphere(center, 0.2f, DiffuseMaterial(albedo))
      } else if mat < 0.80 then {
        val albedo = Vec3(rand.nextFloat(), rand.nextFloat(), rand.nextFloat())

        Object3D.Sphere(center, 0.2f, EmissionMaterial(albedo, Vec3.one[Float]))
      }else if mat < 0.95 then {
        val albedo = Vec3(rand.nextFloat(), rand.nextFloat(), rand.nextFloat())
        val fuzz = rand.nextFloat() * 0.5f

        Object3D.Sphere(center, 0.2f, MetalMaterial(albedo, fuzz))
      } else {
        val albedo = Vec3(rand.nextFloat(), rand.nextFloat(), rand.nextFloat())
        Object3D.Sphere(center, 0.2f, DialectricMaterial(1.5f, albedo))
      }
    }).toArray
  }).toArray
    :+ Object3D.Sphere(Vec3(0.0f, -1000.0f, -1.0f), 1000.0f, DiffuseMaterial(Vec3(0.8f, 0.8f, 0.8f)))
    :+ Object3D.Sphere(Vec3(0.0f, 1.0f, 0.0f), 1.0f, DialectricMaterial(1.5f, Vec3(1.0f, 1.0f, 1.0f)))
    :+ Object3D.Sphere(Vec3(-4.0f, 1.2f, 0.0f), 1.2f, DiffuseMaterial(Vec3(0.4f, 0.2f, 0.1f)))
    :+ Object3D.Sphere(Vec3(-8.0f, 1.4f, 0.0f), 1.4f, MetalMaterial(Vec3(0.7f, 0.6f, 0.5f), 0.0f))
  :+ Object3D.Triangle(
    Vertex(Vec3(2.0f, -1.0f, 0.0f), Vec2(0.0f, 1.0f), Vec3(1.0f, 0.0f, 0.0f)),
    Vertex(Vec3(6.0f, -1.0f, 0.0f), Vec2(1.0f, 1.0f), Vec3(0.0f, 1.0f, 0.0f)),
    Vertex(Vec3(2.0f, textureHeight, 0.0f), Vec2(0.0f, 0.0f), Vec3(0.0f, 0.0f, 1.0f)),
    TexturedMetalMaterial(Vec3.one[Float], texture, 0.4f))
  :+ Object3D.Triangle(
    Vertex(Vec3(6.0f, -1.0f, 0.0f), Vec2(1.0f, 1.0f), Vec3(1.0f, 0.0f, 0.0f)),
    Vertex(Vec3(6.0f, textureHeight, 0.0f), Vec2(1.0f, 0.0f), Vec3(0.0f, 1.0f, 0.0f)),
    Vertex(Vec3(2.0f, textureHeight, 0.0f), Vec2(0.0f, 0.0f), Vec3(0.0f, 0.0f, 1.0f)),
    TexturedMetalMaterial(Vec3.one[Float], texture, 0.4f)))
  ++ OBJLoader.loadOBJ("snowman.obj", Vec3(-3.0f, -1.0f, -3.0f), MetalMaterial(Vec3(1.0f, 1.0f, 1.0f), 1.0f))

  val renderConfig = RenderConfig(100, 100, Vec3(0.3f, 0.3f, 0.4f))
  val colorData = benchmark { camera.render(world, renderConfig) }

  println(s"Width: $width\nHeight: $height")

  //OutputDisplay.display(colorData)

  val output = new File("final.png")
  ImageIO.write(colorData.bufferedImage(), "png", output)
}
