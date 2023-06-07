package com.rundgrun.stairs.ui.configuration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.rundgrun.stairs.databinding.FragmentConfigurationBinding
import com.rundgrun.stairs.domain.builder.StairsConfig
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConfigurationFragment : Fragment() {

    private var _binding: FragmentConfigurationBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ConfigurationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentConfigurationBinding.inflate(inflater, container, false)
        val root: View = binding.root

        viewModel.stairsConfig.observe(viewLifecycleOwner) {
        }
        binding.button.setOnClickListener {
            viewModel.setStairsConfig(StairsConfig(
                binding.height.text.toString().toFloat(),
                binding.width.text.toString().toFloat(),
                binding.rung.text.toString().toInt()))
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}