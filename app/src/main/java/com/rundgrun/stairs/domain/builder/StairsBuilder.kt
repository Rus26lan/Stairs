package com.rundgrun.stairs.domain.builder

import com.rundgrun.stairs.domain.OpenGLData
import com.rundgrun.stairs.domain.mesh.Mesh
import com.rundgrun.stairs.domain.mesh.Rung

class StairsBuilder(val data: OpenGLData) {

    fun getStraightLadder(stairsConfig: StairsConfig): ArrayList<Mesh> {
        val meshList = ArrayList<Mesh>()
        var scale = 1f
        if (stairsConfig.height > stairsConfig.landing) {
            scale = 1 / stairsConfig.height
        }
        val rungInterval = 10f / stairsConfig.steps
        var startXRung = 10f
        var startYRung = -10f
        for (i in 0 until stairsConfig.steps) {
            meshList.add(
                Rung(
                    data,
                    x = startXRung,
                    y = startYRung,
                    width = 0.1f,
                    height = 0.1f,
                    length = 0.1f
                )
            )
            startXRung -= rungInterval
            startYRung += rungInterval
        }
        return meshList
    }
}