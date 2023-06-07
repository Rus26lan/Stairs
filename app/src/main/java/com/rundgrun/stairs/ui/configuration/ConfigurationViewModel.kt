package com.rundgrun.stairs.ui.configuration

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rundgrun.stairs.domain.builder.StairsConfig
import com.rundgrun.stairs.domain.usecase.GetStairsConfigUseCase
import com.rundgrun.stairs.domain.usecase.SetStairsConfigUseCase
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
@HiltViewModel
class ConfigurationViewModel @Inject constructor(
    var setStairsConfigUseCase: SetStairsConfigUseCase,
    var getStairsConfigUseCase: GetStairsConfigUseCase
) : ViewModel() {

    private val _stairsConfig = MutableLiveData<StairsConfig>().apply {
        value = getStairsConfigUseCase.execute()
    }
    val stairsConfig: LiveData<StairsConfig> = _stairsConfig

    fun setStairsConfig(stairsConfig: StairsConfig){
        setStairsConfigUseCase.execute(stairsConfig)
    }
}