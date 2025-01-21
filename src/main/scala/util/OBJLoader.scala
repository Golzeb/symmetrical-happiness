package util

import material.DiffuseMaterial
import math.{Vec2, Vec3, Vec3f}
import render.{Material, Object3D, Vertex}

import java.io.File
import java.util.Scanner
import scala.collection.mutable.ArrayBuffer

object OBJLoader {
  def loadOBJ(path: String, material: Material = DiffuseMaterial(Vec3.fill(0.8f))): Array[Object3D] = {
    val file = new File(path)

    val scanner = new Scanner(file)

    var vertices = ArrayBuffer[Vec3f]()
    var objects = ArrayBuffer[Object3D]()


    while scanner.hasNext do {
      val current_label = scanner.next()

      current_label match {
        case "v" => {
          val a = scanner.nextFloat()
          val b = scanner.nextFloat()
          val c = scanner.nextFloat()

          vertices.addOne(Vec3(a, b, c))
        }
        case "f" => {
          val a = scanner.nextInt() - 1
          val b = scanner.nextInt() - 1
          val c = scanner.nextInt() - 1

          objects.addOne(
            Object3D.Triangle(
              Vertex(vertices.apply(a), Vec2.zero[Float], Vec3(1.0f, 0.0f, 0.0f)),
              Vertex(vertices.apply(b), Vec2.zero[Float], Vec3(0.0f, 1.0f, 0.0f)),
              Vertex(vertices.apply(c), Vec2.zero[Float], Vec3(0.0f, 0.0f, 1.0f)),
              material,
            )
          )
        }
        case _ => {}
      }

      scanner.nextLine()
    }

    objects.toArray
  }
}
