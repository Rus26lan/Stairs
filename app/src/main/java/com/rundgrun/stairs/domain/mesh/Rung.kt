package com.rundgrun.stairs.domain.mesh

import android.opengl.GLES20.*
import com.rundgrun.stairs.ui.POSITION_COUNT
import com.rundgrun.stairs.ui.TEXTURE_COUNT

class Rung(
    x: Float,
    y: Float,
    height: Float,
    width: Float,
    length: Float
) : Mesh(
    x,
    y, height, width,
    length
) {
    override fun draw() {
        glDrawArrays(
            GL_TRIANGLES,
            verticesBackground.size / (POSITION_COUNT + TEXTURE_COUNT),
            vertices.size / (POSITION_COUNT + TEXTURE_COUNT)
        )
    }
}