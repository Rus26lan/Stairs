package com.rundgrun.stairs.ui.presentation

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.os.Bundle
import android.view.*
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.rundgrun.stairs.databinding.FragmentPresentationBinding
import com.rundgrun.stairs.databinding.PartModelRotateBinding
import com.rundgrun.stairs.databinding.PartModelTranslateBinding


class PresentationView : Fragment(), ScaleGestureDetector.OnScaleGestureListener,
    GestureDetector.OnGestureListener {

    private var _binding: FragmentPresentationBinding? = null
    private val binding get() = _binding!!

    private var _bindingRotate: PartModelRotateBinding? = null
    private val bindingRotate get() = _bindingRotate!!

    private var _bindingTranslate: PartModelTranslateBinding? = null
    private val bindingTranslate get() = _bindingTranslate!!

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
        _bindingRotate = PartModelRotateBinding.bind(binding.root)
        _bindingTranslate = PartModelTranslateBinding.bind(binding.root)
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
            viewModel.setState(ParametersState.ROTATE)
        }
        binding.imageTranslate.setOnClickListener {
            viewModel.setState(ParametersState.TRANSLATE)
        }

        viewModel.parameters.observe(viewLifecycleOwner) {
            renderer?.parameters = it
        }

        viewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                ParametersState.ROTATE -> {
                    bindingRotate.modelRotate.visibility = View.VISIBLE
                    bindingTranslate.modelTranslate.visibility = View.GONE
                    binding.imageRotate.visibility = View.VISIBLE
                    binding.imageTranslate.visibility = View.VISIBLE
                }
                ParametersState.TRANSLATE -> {
                    bindingRotate.modelRotate.visibility = View.GONE
                    bindingTranslate.modelTranslate.visibility = View.VISIBLE
                    binding.imageRotate.visibility = View.VISIBLE
                    binding.imageTranslate.visibility = View.VISIBLE
                }
                ParametersState.HIDE -> {
                    bindingRotate.modelRotate.visibility = View.GONE
                    bindingTranslate.modelTranslate.visibility = View.GONE
                    binding.imageRotate.visibility = View.GONE
                    binding.imageTranslate.visibility = View.GONE
                }
            }
        }

        bindingRotate.rotateX.setOnSeekBarChangeListener(SeekBarAdapter{
                viewModel.rotateX(3.6f * it)
        })
        bindingRotate.rotateY.setOnSeekBarChangeListener(SeekBarAdapter{
                viewModel.rotateY(3.6f * it)
        })
        bindingRotate.rotateZ.setOnSeekBarChangeListener(SeekBarAdapter{
                viewModel.rotateZ(3.6f * it)
        })
        bindingRotate.scale.setOnSeekBarChangeListener(SeekBarAdapter{
                viewModel.scale(0.02f * it)
        })
        bindingTranslate.moveX.setOnSeekBarChangeListener(SeekBarAdapter{
                viewModel.moveX(0.2f * it)
        })
        bindingTranslate.moveY.setOnSeekBarChangeListener(SeekBarAdapter{
                viewModel.moveY(0.2f * it)
        })
        bindingTranslate.moveZ.setOnSeekBarChangeListener(SeekBarAdapter{
                viewModel.moveZ(0.1f * it)
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
        _bindingRotate = null
        _bindingTranslate = null
    }

    private fun supportES2(): Boolean {
        val activityManager =
            activity?.getSystemService(AppCompatActivity.ACTIVITY_SERVICE) as ActivityManager
        val configurationInfo = activityManager.deviceConfigurationInfo
        return configurationInfo.reqGlEsVersion >= 0x20000
    }

    override fun onScale(detector: ScaleGestureDetector): Boolean {

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
        viewModel.setState(ParametersState.HIDE)
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