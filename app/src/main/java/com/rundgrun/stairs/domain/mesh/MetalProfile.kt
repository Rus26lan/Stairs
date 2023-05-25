package com.rundgrun.stairs.domain.mesh

import android.opengl.GLES20.*
import android.opengl.Matrix
import com.rundgrun.stairs.domain.OpenGLData
import com.rundgrun.stairs.domain.POSITION_COUNT
import com.rundgrun.stairs.domain.TEXTURE_COUNT

class MetalProfile(
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
        applyTexture(data.textureMetal)
        Matrix.setIdentityM(data.modelMatrix, 0)
        Matrix.translateM(data.modelMatrix, 0, x, y, z)
        Matrix.rotateM(data.modelMatrix, 0, xAngle , 1f, 0f, 0f)
        Matrix.rotateM(data.modelMatrix, 0, yAngle, 0f, 1f, 0f)
        Matrix.rotateM(data.modelMatrix, 0, zAngle, 0f, 0f, 1f)
        Matrix.scaleM(data.modelMatrix, 0, xScale, yScale, zScale)
        bindMatrix()
        glDrawArrays(
            GL_TRIANGLES,
            (data.verticesBackground.size + data.verticesRung.size) / (POSITION_COUNT + TEXTURE_COUNT),
            data.verticesMetalProfile.size / (POSITION_COUNT + TEXTURE_COUNT)
        )
    }
}