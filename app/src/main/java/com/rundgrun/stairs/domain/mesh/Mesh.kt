package com.rundgrun.stairs.domain.mesh

import android.opengl.GLES20.*
import android.opengl.Matrix
import com.rundgrun.stairs.domain.OpenGLData

abstract class Mesh(
    val data: OpenGLData,
    val x: Float,
    val y: Float,
    val z: Float,
    val height: Float,
    val width: Float,
    val length: Float
) {
    abstract fun draw()

    fun applyTexture(texture: Int) {
        glActiveTexture(GL_TEXTURE0)
        glBindTexture(GL_TEXTURE_2D, 0)
        glBindTexture(GL_TEXTURE_2D, texture)
        glUniform1i(data.textureUnitLocation, 0)
    }
    fun bindMatrix() {
        Matrix.multiplyMM(data.finalMatrix, 0, data.allModelMatrix, 0,data.modelMatrix , 0);
        Matrix.multiplyMM(data.finalMatrix, 0, data.viewMatrix, 0, data.finalMatrix, 0);
        Matrix.multiplyMM(data.finalMatrix, 0, data.projectionMatrix, 0, data.finalMatrix, 0)
        glUniformMatrix4fv(data.matrixLocation, 1, false, data.finalMatrix, 0)
    }
}