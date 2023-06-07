package com.rundgrun.stairs.domain.usecase

import com.rundgrun.stairs.domain.builder.StairsConfig
import com.rundgrun.stairs.domain.mesh.Mesh
import com.rundgrun.stairs.domain.repository.StairsConfigRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SetStairsConfigUseCase @Inject constructor() {
    @Inject
    lateinit var stairsConfigRepository: StairsConfigRepository
    fun execute(stairsConfig: StairsConfig) {
        stairsConfigRepository.setStairsConfig(stairsConfig)
    }
}