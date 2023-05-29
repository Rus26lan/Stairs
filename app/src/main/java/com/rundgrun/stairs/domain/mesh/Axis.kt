package com.rundgrun.stairs.domain.mesh

import android.opengl.GLES20
import android.opengl.GLES20.*
import android.opengl.Matrix
import com.rundgrun.stairs.domain.OpenGLData
import com.rundgrun.stairs.domain.POSITION_COUNT
import com.rundgrun.stairs.domain.TEXTURE_COUNT

class Axis(
data: OpenGLData,
    x: Float = 0f,
y: Float = 0f,
z: Float = 0f,
xScale: Float = 1f,
yScale: Float = 1f,
zScale: Float = 1f,
xAngle: Float = 0f,
yAngle: Float= 0f,
zAngle: Float= 0f
) : Mesh(
data,
x, y, z,
xScale, yScale, zScale,
xAngle,yAngle, zAngle
) {
    override fun draw() {
        Matrix.setIdentityM(data.modelMatrix, 0)
        bindMatrix()
        applyTexture(data.textureWood)
        glDrawArrays(
            GL_TRIANGLES,
            data.verticesBackground.size / (POSITION_COUNT + TEXTURE_COUNT),
            data.verticesRung.size / (POSITION_COUNT + TEXTURE_COUNT)
        )
    }
}