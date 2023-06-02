package com.rundgrun.stairs.data

import com.rundgrun.stairs.domain.builder.StairsConfig
import com.rundgrun.stairs.domain.repository.StairsConfigRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImplStairsConfigRepository @Inject constructor(): StairsConfigRepository  {
    override fun getStairsConfig(): StairsConfig {
        return StairsConfig(1f, 1f, 5)
    }
}