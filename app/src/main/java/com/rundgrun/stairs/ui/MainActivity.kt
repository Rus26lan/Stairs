package com.rundgrun.stairs.ui

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.opengl.GLSurfaceView
import android.os.Bundle
import android.view.GestureDetector
import android.view.GestureDetector.OnGestureListener
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), ScaleGestureDetector.OnScaleGestureListener,OnGestureListener {

    private var glSurfaceView: GLSurfaceView? = null
    private var renderer: OpenGLRenderer? = null
    private lateinit var gestureDetector: GestureDetector
    private lateinit var scaleDetector: ScaleGestureDetector

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gestureDetector = GestureDetector(this,this)
        scaleDetector = ScaleGestureDetector(this, this)
        if (!supportES2()) {
            Toast.makeText(this, "Not supported!", Toast.LENGTH_LONG).show()
        }
        glSurfaceView = GLSurfaceView(this)
        renderer = OpenGLRenderer(this)
        glSurfaceView?.setEGLContextClientVersion(2)
        glSurfaceView?.setRenderer(renderer)
        glSurfaceView?.setOnTouchListener { _, e ->
            scaleDetector.onTouchEvent(e)
            gestureDetector.onTouchEvent(e)
            true
        }
        setContentView(glSurfaceView)
    }

    override fun onPause() {
        super.onPause()
        glSurfaceView?.onPause()
    }

    override fun onResume() {
        super.onResume()
        glSurfaceView?.onResume()
    }

    private fun supportES2(): Boolean {
        val activityManager = getSystemService(ACTIVITY_SERVICE) as ActivityManager
        val configurationInfo = activityManager.deviceConfigurationInfo
        return configurationInfo.reqGlEsVersion >= 0x20000
    }

    override fun onScale(detector: ScaleGestureDetector): Boolean {
        val factor = detector.scaleFactor
        if (factor > 1){
            renderer?.scale(0.02f)
        } else{
            renderer?.scale(-0.02f)
        }
        return true
    }

    override fun onScaleBegin(detector: ScaleGestureDetector): Boolean {
        return true
    }

    override fun onScaleEnd(detector: ScaleGestureDetector) {
    }

    override fun onDown(e: MotionEvent): Boolean {
        return true
    }

    override fun onShowPress(e: MotionEvent) {
    }

    override fun onSingleTapUp(e: MotionEvent): Boolean {
        return true
    }

    override fun onScroll(
        e1: MotionEvent,
        e2: MotionEvent,
        distanceX: Float,
        distanceY: Float
    ): Boolean {
        if(!scaleDetector.isInProgress){
            renderer?.rotate(distanceX * 0.001f,distanceY* 0.001f)
        }
        return true
    }

    override fun onLongPress(e: MotionEvent) {

    }

    override fun onFling(
        e1: MotionEvent,
        e2: MotionEvent,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        return true
    }
}