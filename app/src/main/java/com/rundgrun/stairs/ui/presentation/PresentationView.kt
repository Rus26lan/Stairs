package com.rundgrun.stairs.ui.presentation

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.os.Bundle
import android.view.*
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.rundgrun.stairs.databinding.FragmentPresentationBinding
import com.rundgrun.stairs.databinding.PartModelRotateBinding


class PresentationView : Fragment(), ScaleGestureDetector.OnScaleGestureListener,
    GestureDetector.OnGestureListener {

    private var _binding: FragmentPresentationBinding? = null
    private val binding get() = _binding!!

    private var _bindingModel: PartModelRotateBinding? = null
    private val bindingModel get() = _bindingModel!!

    private var renderer: OpenGLRenderer? = null

    private lateinit var gestureDetector: GestureDetector
    private lateinit var scaleDetector: ScaleGestureDetector
    private lateinit var viewModel: PresentationViewModel

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPresentationBinding.inflate(inflater, container, false)
        _bindingModel = PartModelRotateBinding.bind(binding.root)
        val root: View = binding.root

        viewModel =
            ViewModelProvider(this)[PresentationViewModel::class.java]

        if (!supportES2()) {
            Toast.makeText(requireContext(), "Not supported!", Toast.LENGTH_LONG).show()
        }

        gestureDetector = GestureDetector(requireContext(), this)
        scaleDetector = ScaleGestureDetector(requireContext(), this)

        binding.glSurfaceView.setEGLContextClientVersion(2)
        renderer = OpenGLRenderer(requireContext())
        binding.glSurfaceView.setRenderer(renderer)
        binding.glSurfaceView.setOnTouchListener { _, e ->
            scaleDetector.onTouchEvent(e)
            gestureDetector.onTouchEvent(e)
            true
        }
        binding.imageRotate.setOnClickListener {

        }
        binding.imageTranslate.setOnClickListener {

        }
        viewModel.parameters.observe(viewLifecycleOwner) {
            renderer?.parameters = it
        }

        bindingModel.rotateX.setOnSeekBarChangeListener(object: OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                viewModel.rotateX(3.6f * progress)
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
        bindingModel.rotateY.setOnSeekBarChangeListener(object: OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                viewModel.rotateY(3.6f * progress)
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
        bindingModel.rotateZ.setOnSeekBarChangeListener(object: OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                viewModel.rotateZ(3.6f * progress)
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
        bindingModel.scale.setOnSeekBarChangeListener(object: OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                viewModel.scale(0.02f * progress)
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
        return root
    }

    override fun onPause() {
        super.onPause()
        binding.glSurfaceView.onPause()
    }

    override fun onResume() {
        super.onResume()
        binding.glSurfaceView.onResume()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        _bindingModel = null
    }

    private fun supportES2(): Boolean {
        val activityManager =
            activity?.getSystemService(AppCompatActivity.ACTIVITY_SERVICE) as ActivityManager
        val configurationInfo = activityManager.deviceConfigurationInfo
        return configurationInfo.reqGlEsVersion >= 0x20000
    }

    override fun onScale(detector: ScaleGestureDetector): Boolean {
//        val factor = detector.scaleFactor
//        if (factor > 1) {
//            viewModel.scale(0.02f)
//        } else {
//            viewModel.scale(-0.02f)
//        }
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
//        if (!scaleDetector.isInProgress) {
//            viewModel.rotateX(distanceX * 0.001f)
//            viewModel.rotateY(distanceY * 0.001f)
//        }
        return true
    }

    override fun onLongPress(e: MotionEvent) {
        when(bindingModel.modelTranslation.visibility){
            View.GONE -> bindingModel.modelTranslation.visibility = View.VISIBLE
            View.VISIBLE -> bindingModel.modelTranslation.visibility = View.GONE
        }
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