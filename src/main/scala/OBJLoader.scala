import java.io.File
import java.util.Scanner
import scala.collection.mutable.ArrayBuffer

object OBJLoader {
  def loadOBJ(path: String): Array[Object3D] = {
    val file = new File(path)

    val scanner = new Scanner(file)

    var vertices = ArrayBuffer[Vec3f]()
    var objects = ArrayBuffer[Object3D]()


    while scanner.hasNext do {
      val current_label = scanner.next().charAt(0)

      current_label match {
        case 'v' => {
          val a = scanner.nextFloat()
          val b = scanner.nextFloat()
          val c = scanner.nextFloat()

          vertices.addOne(Vec3(a, b, c))
        }
        case 'f' => {
          val a = scanner.nextInt() - 1
          val b = scanner.nextInt() - 1
          val c = scanner.nextInt() - 1

          objects.addOne(
            Object3D.Triangle(
              vertices.apply(a),
              vertices.apply(b),
              vertices.apply(c),
              Material(Vec3(1.0f, 1.0f, 1.0f)))
          )
        }
      }

      scanner.nextLine()
    }

    objects.toArray
  }
}
