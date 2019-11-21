package org.sorted.chaos.process

import org.sorted.chaos.model.Wavefront

/**
  * This class represents the color of the Mesh, when not having a texture
  *
  * @param red the amount of red a value between 0 and 1
  * @param green the amount of green a value between 0 and 1
  * @param blue the amount of blue a value between 0 and 1
  */
final case class SolidColor(red: Float, green: Float, blue: Float) {
  def toArray: Array[Float] = Array(red, green, blue)
}

final case class Mesh(vertices: Array[Float], color: Array[Float])

object Mesh {
  private def empty = Mesh(Array.emptyFloatArray, Array.emptyFloatArray)

  def from(wavefront: Wavefront, color: SolidColor): Mesh = {
    val wavefrontVertices = wavefront.vertices
    val wavefrontFaces    = wavefront.faces

    wavefrontFaces.foldLeft(Mesh.empty) { (accumulator, triangleDef) =>
      {
        val index1 = triangleDef.indexOfPoint1
        val index2 = triangleDef.indexOfPoint2
        val index3 = triangleDef.indexOfPoint3

        val point1 = wavefrontVertices(index1)
        val point2 = wavefrontVertices(index2)
        val point3 = wavefrontVertices(index3)

        Mesh(
          vertices = accumulator.vertices ++ point1.toArray ++ point2.toArray ++ point3.toArray,
          color    = accumulator.color ++ color.toArray ++ color.toArray ++ color.toArray
        )
      }
    }
  }
}
