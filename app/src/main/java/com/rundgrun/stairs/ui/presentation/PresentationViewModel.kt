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
}