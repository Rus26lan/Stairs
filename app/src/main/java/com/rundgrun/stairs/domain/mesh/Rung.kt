package com.rundgrun.stairs.domain.mesh

import android.opengl.GLES20.*
import android.opengl.Matrix
import com.rundgrun.stairs.domain.OpenGLData
import com.rundgrun.stairs.domain.POSITION_COUNT
import com.rundgrun.stairs.domain.TEXTURE_COUNT


class Rung(
    data: OpenGLData,
    x: Float = 0f,
    y: Float = 0f,
    z: Float = 0f,
    height: Float = 1f,
    width: Float = 1f,
    length: Float = 1f
) : Mesh(
    data,
    x, y, z,
    height, width,
    length
) {
    override fun draw() {
        applyTexture(data.textureWood)
        Matrix.setIdentityM(data.modelMatrix, 0)
        Matrix.scaleM(data.modelMatrix, 0, length, width, height)
        Matrix.translateM(data.modelMatrix, 0, z, y, x)
//        Matrix.rotateM(data.modelMatrix, 0, data.rotateX * 360, 0f, 1f, 0f)
//        Matrix.rotateM(data.modelMatrix, 0, data.rotateY * 360, 1f, 0f, 0f)
        bindMatrix()
        glDrawArrays(
            GL_TRIANGLES,
            data.verticesBackground.size / (POSITION_COUNT + TEXTURE_COUNT),
            data.verticesRung.size / (POSITION_COUNT + TEXTURE_COUNT)
        )
    }
}