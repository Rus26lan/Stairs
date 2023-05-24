package com.rundgrun.stairs.domain.mesh

import android.opengl.GLES20.*
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
        glDrawArrays(
            GL_TRIANGLES,
            data.verticesBackground.size / (POSITION_COUNT + TEXTURE_COUNT),
            data.verticesRung.size / (POSITION_COUNT + TEXTURE_COUNT)
        )
    }
}