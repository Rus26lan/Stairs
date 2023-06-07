package com.rundgrun.stairs.data

import com.rundgrun.stairs.domain.builder.StairsConfig
import com.rundgrun.stairs.domain.repository.StairsConfigRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImplStairsConfigRepository @Inject constructor(): StairsConfigRepository  {

    var currentStairsConfig: StairsConfig = StairsConfig(1f, 1f, 5)

    override fun getStairsConfig(): StairsConfig {
        return currentStairsConfig
    }
    override fun setStairsConfig(stairsConfig: StairsConfig){
        currentStairsConfig = stairsConfig
    }
}