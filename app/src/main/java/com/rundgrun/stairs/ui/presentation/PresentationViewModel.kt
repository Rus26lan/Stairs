package com.rundgrun.stairs.ui.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rundgrun.stairs.domain.builder.StairsConfig
import com.rundgrun.stairs.domain.usecase.GetStairsConfigUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PresentationViewModel @Inject constructor(
    var getStairsConfigUseCase: GetStairsConfigUseCase
) : ViewModel() {

    private val modelParameters = ModelParameters()
    private var lastState = ParametersState.ROTATE
    private var isHide: Boolean = false

    private val _parameters = MutableLiveData<ModelParameters>().apply {
        value = modelParameters
    }
    val parameters: LiveData<ModelParameters> = _parameters

    private val _state = MutableLiveData<ParametersState>().apply {
        value = lastState
    }
    val state: LiveData<ParametersState> = _state

    private val _stairsConfig = MutableLiveData<StairsConfig>().apply {
        value = getStairsConfigUseCase.execute()
    }
    val stairsConfig: LiveData<StairsConfig> = _stairsConfig


    fun rotateX(x: Float) {
        modelParameters.rotateModelX = x
    }

    fun rotateY(y: Float) {
        modelParameters.rotateModelY = y
    }

    fun rotateZ(z: Float) {
        modelParameters.rotateModelZ = z
    }

    fun scale(value: Float) {
        modelParameters.scaleModel = value
    }

    fun moveX(x: Float) {
        modelParameters.moveModelX = x - 5
    }

    fun moveY(y: Float) {
        modelParameters.moveModelY = y - 5
    }

    fun moveZ(z: Float) {
        modelParameters.moveModelZ = -z
    }

    fun setState(state: ParametersState) {
        when (state) {
            ParametersState.HIDE -> {
                if (isHide) {
                    _state.value = lastState
                } else {
                    lastState = _state.value ?: ParametersState.ROTATE
                    _state.value = state
                }
                isHide = !isHide
            }
            ParametersState.ROTATE -> {
                lastState = state
                _state.value = state
            }
            ParametersState.TRANSLATE -> {
                lastState = state
                _state.value = state
            }
        }
    }
}