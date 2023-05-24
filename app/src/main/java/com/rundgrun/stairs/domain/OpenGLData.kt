package com.rundgrun.stairs.domain

import android.content.Context
import android.opengl.GLES20.*
import com.rundgrun.stairs.R
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer


const val POSITION_COUNT = 3
const val TEXTURE_COUNT = 2
const val STRIDE = (POSITION_COUNT
        + TEXTURE_COUNT) * 4

class OpenGLData(private val context: Context) {

    var baseProgram = prepareBaseProgram()

    val positionLocation = glGetAttribLocation(baseProgram, "a_Position")
    val textureLocation = glGetAttribLocation(baseProgram, "a_Texture");
    val textureUnitLocation = glGetUniformLocation(baseProgram, "u_TextureUnit");
    val colorLocation = glGetUniformLocation(baseProgram, "u_Color")
    val matrixLocation = glGetUniformLocation(baseProgram, "u_Matrix")

    val modelMatrix = FloatArray(16)
    val viewMatrix = FloatArray(16)
    val projectionMatrix = FloatArray(16)
    val finalMatrix = FloatArray(16)

    val textureWood = TextureUtils.loadTexture(context, R.drawable.wood);
    val textureMetal = TextureUtils.loadTexture(context, R.drawable.metal)
    val textureBackground = TextureUtils.loadTexture(context, R.drawable.street)

    val verticesBackground = floatArrayOf(
        -1f, 1f, 0.99f, 0f, 0f,
        1f, 1f, 0.99f, 1f, 0f,
        -1f, -1f, 0.99f, 0f, 1f,
        -1f, -1f, 0.99f, 0f, 1f,
        1f, 1f, 0.99f, 1f, 0f,
        1f, -1f, 0.99f, 1f, 1f,
    )

    val verticesRung = FileUtils.convertObjToArrayWithTextures(context, R.raw.rung)

    private val finishVertices = verticesBackground + verticesRung

    var vertexData: FloatBuffer? = ByteBuffer
        .allocateDirect(finishVertices.size * 4)
        .order(ByteOrder.nativeOrder())
        .asFloatBuffer().apply {
            this.put(finishVertices)
            this.position(0)
        }

    private fun prepareBaseProgram(): Int {
        val vertexShaderId =
            ShaderUtils.createShader(context, GL_VERTEX_SHADER, R.raw.vertex_shader)
        val fragmentShaderId =
            ShaderUtils.createShader(context, GL_FRAGMENT_SHADER, R.raw.fragment_shader)
        return ShaderUtils.createProgram(vertexShaderId, fragmentShaderId)
    }

}