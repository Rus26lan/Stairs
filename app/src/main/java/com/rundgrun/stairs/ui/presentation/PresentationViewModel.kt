package com.rundgrun.stairs.ui.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PresentationViewModel : ViewModel() {

    private val modelParameters = ModelParameters()

    private val _parameters = MutableLiveData<ModelParameters>().apply {
        value = modelParameters
    }
    val parameters: LiveData<ModelParameters> = _parameters

    private val _state = MutableLiveData<ParametersState>().apply {
        value = ParametersState.HIDE
    }
    val state: LiveData<ParametersState> = _state

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
        modelParameters.moveModelX = x
    }

    fun moveY(y: Float) {
        modelParameters.moveModelY = y
    }

    fun moveZ(z: Float) {
        modelParameters.moveModelZ = z
    }

    fun setState(state: ParametersState){
        va
    }

}