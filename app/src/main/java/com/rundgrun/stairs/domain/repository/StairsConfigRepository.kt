package com.rundgrun.stairs.domain.repository

import com.rundgrun.stairs.domain.builder.StairsConfig

interface StairsConfigRepository {
    fun getStairsConfig(): StairsConfig
    fun setStairsConfig(stairsConfig: StairsConfig)
}