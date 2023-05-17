package com.rundgrun.stairs.ui

import android.content.Context
import android.opengl.GLES20.*
import android.opengl.GLSurfaceView
import android.opengl.Matrix
import com.rundgrun.stairs.R
import com.rundgrun.stairs.domain.FileUtils
import com.rundgrun.stairs.domain.MeshGenerator
import com.rundgrun.stairs.domain.ShaderUtils
import com.rundgrun.stairs.domain.TextureUtils
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

private const val POSITION_COUNT = 3
private const val TEXTURE_COUNT = 2
private const val STRIDE = (POSITION_COUNT
        + TEXTURE_COUNT) * 4

class OpenGLRenderer(private val context: Context) : GLSurfaceView.Renderer {

    private var rotateX: Float = 0.0f
    private var rotateY: Float = 0.0f
    private var scale: Float = 1.0f

    private var programId = 0
    private var vertexData: FloatBuffer? = null

    private val modelMatrix = FloatArray(16)
    private val viewMatrix = FloatArray(16)
    private val projectionMatrix = FloatArray(16)
    private val finalMatrix = FloatArray(16)

    private var positionLocation = 0
    private var textureLocation = 0
    private var textureUnitLocation = 0
    private var colorLocation = 0
    private var matrixLocation = 0

    //    private val vertices = FileUtils.convertObjToArrayOfLines(context, R.raw.whofhour)
//    private val vertices = FileUtils.convertObjToArrayWithTextures(context, R.raw.beam)
    private val vertices = MeshGenerator.createBeam()
    private var textureWood = 0
    private var textureMetal = 0
    private var textureMetal2 = 0

//    //Triangles
//    private val vertices = floatArrayOf(
//        -1f, -1f, 0f, 0f, 1f,
//        -1f, 1f, 0f, 0f, 0f,
//        1f, -1f, 0f, 1f, 1f,
//        -1f, 1f, 0f, 0f, 0f,
//        1f, 1f, 0f, 1f, 0f,
//        1f, -1f, 0f, 1f, 1f,
//    )

    init {
        vertexData = ByteBuffer
            .allocateDirect(vertices.size * 4)
            .order(ByteOrder.nativeOrder())
            .asFloatBuffer()
        vertexData?.put(vertices)
    }

    override fun onSurfaceCreated(arg0: GL10, arg1: EGLConfig) {
        glEnable(GL_DEPTH_TEST);
        glClearColor(1f, 1f, 1f, 1f)

        textureWood = TextureUtils.loadTexture(context, R.drawable.wood);
        textureMetal = TextureUtils.loadTexture(context, R.drawable.metal)
        textureMetal = TextureUtils.loadTexture(context, R.drawable.metal2)
        prepareProgram()
        initLocation()

        vertexData?.position(0)
        glVertexAttribPointer(
            positionLocation, POSITION_COUNT, GL_FLOAT,
            false, STRIDE, vertexData
        )
        glEnableVertexAttribArray(positionLocation)

        vertexData?.position(POSITION_COUNT);
        glVertexAttribPointer(
            textureLocation, TEXTURE_COUNT, GL_FLOAT,
            false, STRIDE, vertexData
        )
        glEnableVertexAttribArray(textureLocation)
        createViewMatrix()
    }

    override fun onSurfaceChanged(arg0: GL10, width: Int, height: Int) {
        glViewport(0, 0, width, height)
        createProjectionMatrix(width, height)
    }

    override fun onDrawFrame(arg0: GL10) {
        glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)
        applyTexture(textureWood)
        Matrix.setIdentityM(modelMatrix, 0)
        Matrix.translateM(modelMatrix, 0, 0f, 0f, -2f)
        Matrix.scaleM(modelMatrix, 0, scale, scale, scale)
        Matrix.rotateM(modelMatrix, 0, rotateX * 360, 0f, 1f, 0f)
        Matrix.rotateM(modelMatrix, 0, rotateY * 360, 1f, 0f, 0f)
        bindMatrix()
        glDrawArrays(GL_TRIANGLES, 0, vertices.size / (POSITION_COUNT + TEXTURE_COUNT))
    }


    private fun prepareProgram() {
        val vertexShaderId =
            ShaderUtils.createShader(context, GL_VERTEX_SHADER, R.raw.vertex_shader)
        val fragmentShaderId =
            ShaderUtils.createShader(context, GL_FRAGMENT_SHADER, R.raw.fragment_shader)
        programId = ShaderUtils.createProgram(vertexShaderId, fragmentShaderId)
        glUseProgram(programId)
    }

    private fun initLocation() {
        positionLocation = glGetAttribLocation(programId, "a_Position")
        textureLocation = glGetAttribLocation(programId, "a_Texture");
        textureUnitLocation = glGetUniformLocation(programId, "u_TextureUnit");
        colorLocation = glGetUniformLocation(programId, "u_Color")
        matrixLocation = glGetUniformLocation(programId, "u_Matrix")
    }

    private fun createProjectionMatrix(width: Int, height: Int) {
        val ratio: Float
        var left = -1.0f
        var right = 1.0f
        var bottom = -1.0f
        var top = 1.0f
        val near = 1.0f
        val far = 8.0f
        if (width > height) {
            ratio = width.toFloat() / height
            left *= ratio
            right *= ratio
        } else {
            ratio = height.toFloat() / width
            bottom *= ratio
            top *= ratio
        }
        Matrix.frustumM(projectionMatrix, 0, left, right, bottom, top, near, far)
    }

    private fun createViewMatrix() {
        val eyeX = 0f
        val eyeY = 0f
        val eyeZ = 1f

        val centerX = 0f
        val centerY = 0f
        val centerZ = 0f

        val upX = 0f
        val upY = 1f
        val upZ = 0f
        Matrix.setLookAtM(
            viewMatrix,
            0,
            eyeX,
            eyeY,
            eyeZ,
            centerX,
            centerY,
            centerZ,
            upX,
            upY,
            upZ
        )
    }

    private fun bindMatrix() {
        Matrix.multiplyMM(finalMatrix, 0, viewMatrix, 0, modelMatrix, 0);
        Matrix.multiplyMM(finalMatrix, 0, projectionMatrix, 0, finalMatrix, 0)
        glUniformMatrix4fv(matrixLocation, 1, false, finalMatrix, 0)
    }

    private fun applyTexture(texture: Int) {
        glActiveTexture(GL_TEXTURE0)
        glBindTexture(GL_TEXTURE_2D, 0)
        glBindTexture(GL_TEXTURE_2D, texture)
        glUniform1i(textureUnitLocation, 0)
    }

    fun rotate(x: Float, y: Float) {
        rotateX += x
        rotateY += y
    }

    fun scale(value: Float) {
        scale += value
    }
}