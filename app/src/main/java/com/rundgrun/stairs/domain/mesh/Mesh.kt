package com.rundgrun.stairs.domain.mesh

abstract class Mesh(
    val x: Float,
    val y: Float,
    val height: Float,
    val width: Float,
    val length: Float
) {
    abstract fun draw()
}