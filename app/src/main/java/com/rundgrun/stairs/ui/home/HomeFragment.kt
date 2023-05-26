package com.rundgrun.stairs.ui.home

import android.app.ActivityManager
import android.opengl.GLSurfaceView
import android.os.Bundle
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.rundgrun.stairs.databinding.FragmentHomeBinding
import com.rundgrun.stairs.ui.OpenGLRenderer


class HomeFragment : Fragment(), ScaleGestureDetector.OnScaleGestureListener,
    GestureDetector.OnGestureListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private var glSurfaceView: GLSurfaceView? = null
    private var renderer: OpenGLRenderer? = null


    private lateinit var gestureDetector: GestureDetector
    private lateinit var scaleDetector: ScaleGestureDetector

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        if (!supportES2()) {
            Toast.makeText(requireContext(), "Not supported!", Toast.LENGTH_LONG).show()
        }

        gestureDetector = GestureDetector(requireContext(),this)
        scaleDetector = ScaleGestureDetector(requireContext(), this)

        glSurfaceView = binding.glSurfaceView
        renderer = OpenGLRenderer(requireContext())
        glSurfaceView?.setEGLContextClientVersion(2)
        glSurfaceView?.setRenderer(renderer)
        glSurfaceView?.setOnTouchListener { _, e ->
            scaleDetector.onTouchEvent(e)
            gestureDetector.onTouchEvent(e)
            true
        }

        homeViewModel.text.observe(viewLifecycleOwner) {

        }
        return root
    }

    override fun onPause() {
        super.onPause()
        glSurfaceView?.onPause()
    }

    override fun onResume() {
        super.onResume()
        glSurfaceView?.onResume()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun supportES2(): Boolean {
        val activityManager = activity?.getSystemService(AppCompatActivity.ACTIVITY_SERVICE) as ActivityManager
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