package com.rundgrun.stairs.ui.presentation

import android.content.Context
import android.opengl.GLES20.*
import android.opengl.GLSurfaceView
import android.opengl.Matrix
import com.rundgrun.stairs.data.ImplStairsConfigRepository
import com.rundgrun.stairs.domain.*
import com.rundgrun.stairs.domain.builder.StairsBuilder
import com.rundgrun.stairs.domain.builder.StairsConfig
import com.rundgrun.stairs.domain.mesh.Background
import com.rundgrun.stairs.domain.mesh.Mesh
import com.rundgrun.stairs.domain.mesh.Rung
import com.rundgrun.stairs.domain.repository.StairsConfigRepository
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class OpenGLRenderer @Inject constructor(
    @ActivityContext val context: Context
    ) : GLSurfaceView.Renderer {
    @Inject lateinit var stairsConfigRepository: StairsConfigRepository

    lateinit var data: OpenGLData
    var parameters: ModelParameters = ModelParameters()
    lateinit var backgroundMesh: Background
    lateinit var stairsBuilder: StairsBuilder
    lateinit var meshList: ArrayList<Mesh>

    override fun onSurfaceCreated(arg0: GL10, arg1: EGLConfig) {
        glEnable(GL_DEPTH_TEST);
        glClearColor(1f, 1f, 1f, 1f)
        data = OpenGLData(context)
        stairsBuilder = StairsBuilder(data)
        meshList = stairsBuilder.getStraightLadder(stairsConfigRepository.getStairsConfig())
        glUseProgram(data.baseProgram)
        backgroundMesh = Background(data)

        glVertexAttribPointer(
            data.positionLocation, POSITION_COUNT, GL_FLOAT,
            false, STRIDE, data.vertexData
        )
        glEnableVertexAttribArray(data.positionLocation)

        data.vertexData?.position(POSITION_COUNT);
        glVertexAttribPointer(
            data.textureLocation, TEXTURE_COUNT, GL_FLOAT,
            false, STRIDE, data.vertexData
        )
        glEnableVertexAttribArray(data.textureLocation)
        createViewMatrix()
    }

    override fun onSurfaceChanged(arg0: GL10, width: Int, height: Int) {
        glViewport(0, 0, width, height)
        createProjectionMatrix(width, height)
    }

    override fun onDrawFrame(arg0: GL10) {
        glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)
        if (parameters.showBackground) {
            resetMatrix()
            backgroundMesh.draw()
        }
        Matrix.setIdentityM(data.allModelMatrix, 0)
        Matrix.translateM(
            data.allModelMatrix,
            0,
            parameters.moveModelX,
            parameters.moveModelY,
            -1f + parameters.moveModelZ
        )
        Matrix.scaleM(
            data.allModelMatrix,
            0,
            parameters.scaleModel,
            parameters.scaleModel,
            parameters.scaleModel
        )
        Matrix.rotateM(data.allModelMatrix, 0, parameters.rotateModelY, 0f, 1f, 0f)
        Matrix.rotateM(data.allModelMatrix, 0, parameters.rotateModelZ, 0f, 0f, 1f)
        Matrix.rotateM(data.allModelMatrix, 0, parameters.rotateModelX, 1f, 0f, 0f)
        meshList.forEach {
            it.draw()
        }
    }

    private fun createProjectionMatrix(width: Int, height: Int) {
        val ratio: Float
        var left = -0.1f
        var right = 0.1f
        var bottom = -0.1f
        var top = 0.1f
        val near = 0.1f
        val far = 200.0f
        if (width > height) {
            ratio = width.toFloat() / height
            left *= ratio
            right *= ratio
        } else {
            ratio = height.toFloat() / width
            bottom *= ratio
            top *= ratio
        }
        Matrix.frustumM(data.projectionMatrix, 0, left, right, bottom, top, near, far)
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
            data.viewMatrix,
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
        Matrix.multiplyMM(data.modelMatrix, 0, data.modelMatrix, 0, data.allModelMatrix, 0);
        Matrix.multiplyMM(data.finalMatrix, 0, data.viewMatrix, 0, data.modelMatrix, 0);
        Matrix.multiplyMM(data.finalMatrix, 0, data.projectionMatrix, 0, data.finalMatrix, 0)
        glUniformMatrix4fv(data.matrixLocation, 1, false, data.finalMatrix, 0)
    }

    private fun resetMatrix() {
        Matrix.setIdentityM(data.finalMatrix, 0)
        Matrix.scaleM(data.finalMatrix, 0, 2f, 1f, 1f)
        glUniformMatrix4fv(data.matrixLocation, 1, false, data.finalMatrix, 0)
    }
}