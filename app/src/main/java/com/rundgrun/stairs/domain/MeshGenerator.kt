package com.rundgrun.stairs.domain

object MeshGenerator {

    fun createBeam(
        length: Float = 5.0f,
        width: Float = 1.0f,
        thickness: Float = 0.1f
    ): FloatArray {
        val list = ArrayList<Float>()
        val vertexOriginal = Vertex(0f, 0f, 0f)
        val vertexUpLeft = vertexOriginal.translate(0f, width, 0f)
        val vertexUpRight = vertexOriginal.translate(thickness, width, 0f)
        val vertexRightDown = vertexOriginal.translate(width, 0f, 0f)
        val vertexRightUp = vertexOriginal.translate(width, thickness, 0f)
        val vertexCenter = vertexOriginal.translate(thickness, thickness, 0f)

        val vertexOriginal2 = Vertex(0f, 0f, length)
        val vertexUpLeft2 = vertexOriginal2.translate(0f, width, 0f)
        val vertexUpRight2 = vertexOriginal2.translate(thickness, width, 0f)
        val vertexRightDown2 = vertexOriginal2.translate(width, 0f, 0f)
        val vertexRightUp2 = vertexOriginal2.translate(width, thickness, 0f)
        val vertexCenter2 = vertexOriginal2.translate(thickness, thickness, 0f)

        list.addCordsLeft(vertexOriginal)
        list.addCordsLeft(vertexUpLeft)
        list.addCordsLeft(vertexCenter)
        list.addCordsLeft(vertexUpLeft)
        list.addCordsLeft(vertexUpRight)
        list.addCordsLeft(vertexCenter)
        list.addCordsLeft(vertexOriginal)
        list.addCordsLeft(vertexCenter)
        list.addCordsLeft(vertexRightDown)
        list.addCordsLeft(vertexCenter)
        list.addCordsLeft(vertexRightUp)
        list.addCordsLeft(vertexRightDown)

        list.addCordsLeft(vertexOriginal2)
        list.addCordsLeft(vertexUpLeft2)
        list.addCordsLeft(vertexCenter2)
        list.addCordsLeft(vertexUpLeft2)
        list.addCordsLeft(vertexUpRight2)
        list.addCordsLeft(vertexCenter2)
        list.addCordsLeft(vertexOriginal2)
        list.addCordsLeft(vertexCenter2)
        list.addCordsLeft(vertexRightDown2)
        list.addCordsLeft(vertexCenter2)
        list.addCordsLeft(vertexRightUp2)
        list.addCordsLeft(vertexRightDown2)

        list.addCordsUp(vertexOriginal)
        list.addCordsUp(vertexOriginal2)
        list.addCordsUp(vertexRightDown2)
        list.addCordsUp(vertexRightDown2)
        list.addCordsUp(vertexRightDown)
        list.addCordsUp(vertexOriginal)

        list.addCordsUp(vertexCenter)
        list.addCordsUp(vertexCenter2)
        list.addCordsUp(vertexRightUp2)
        list.addCordsUp(vertexRightUp2)
        list.addCordsUp(vertexRightUp)
        list.addCordsUp(vertexCenter)

        list.addCordsUp(vertexUpLeft)
        list.addCordsUp(vertexUpLeft2)
        list.addCordsUp(vertexUpRight)
        list.addCordsUp(vertexUpRight)
        list.addCordsUp(vertexUpLeft2)
        list.addCordsUp(vertexUpRight2)

        list.addCordsBack(vertexOriginal2)
        list.addCordsBack(vertexUpLeft2)
        list.addCordsBack(vertexOriginal)
        list.addCordsBack(vertexUpLeft2)
        list.addCordsBack(vertexUpLeft)
        list.addCordsBack(vertexOriginal)

        list.addCordsBack(vertexUpRight)
        list.addCordsBack(vertexUpRight2)
        list.addCordsBack(vertexCenter)
        list.addCordsBack(vertexCenter)
        list.addCordsBack(vertexUpRight2)
        list.addCordsBack(vertexCenter2)

        list.addCordsBack(vertexRightUp)
        list.addCordsBack(vertexRightUp2)
        list.addCordsBack(vertexRightDown2)
        list.addCordsBack(vertexRightDown2)
        list.addCordsBack(vertexRightDown)
        list.addCordsBack(vertexRightUp)

        return FloatArray(list.size) { list[it] }
    }


}

fun ArrayList<Float>.addCordsLeft(vertex: Vertex) {
    this.add(vertex.x)
    this.add(vertex.y)
    this.add(vertex.z)
    this.add(vertex.x)
    this.add(vertex.y)
}

fun ArrayList<Float>.addCordsUp(vertex: Vertex) {
    this.add(vertex.x)
    this.add(vertex.y)
    this.add(vertex.z)
    this.add(vertex.z)
    this.add(vertex.x)
}

fun ArrayList<Float>.addCordsBack(vertex: Vertex) {
    this.add(vertex.x)
    this.add(vertex.y)
    this.add(vertex.z)
    this.add(vertex.z)
    this.add(vertex.y)
}