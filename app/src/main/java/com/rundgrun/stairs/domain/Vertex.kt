package com.rundgrun.stairs.domain

data class Vertex(
    val x: Float,
    val y: Float,
    val z: Float
) {
    fun translate(x: Float, y: Float, z: Float): Vertex = Vertex(this.x + x, this.y + y, this.z + z)
}