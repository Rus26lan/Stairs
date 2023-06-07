package com.rundgrun.stairs.domain.builder

import com.rundgrun.stairs.domain.OpenGLData
import com.rundgrun.stairs.domain.mesh.Mesh
import com.rundgrun.stairs.domain.mesh.MetalProfile
import com.rundgrun.stairs.domain.mesh.Rung

class StairsBuilder(val data: OpenGLData) {

    fun getStraightLadder(stairsConfig: StairsConfig): ArrayList<Mesh> {
        val meshList = ArrayList<Mesh>()

        val height = stairsConfig.height
        val landing = stairsConfig.landing
        val rungLanding = stairsConfig.rungLanding
        val steps = stairsConfig.steps

        val rungIntervalHeight = height / steps
        val rungIntervalLanding = landing / steps
        var startXRung = -landing/2
        var startYRung = -height/2

        for (i in 0 until stairsConfig.steps) {
            meshList.add(
                Rung(
                    data,
                    x = startXRung,
                    y = startYRung,
                    zScale = rungLanding
                )
            )
            meshList.add(
                MetalProfile(
                    data,
                    x = startXRung,
                    y = startYRung - 0.5f,
                    xScale = 0.62f,
                    yScale = 0.5f,
                    zScale = 0.2f
                )
            )
//            meshList.add(
//                MetalProfile(
//                    data,
//                    x = startXRung,
//                    y = startYRung - 0.5f,
//                    xScale = 1f,
//                    yScale = 0.5f,
//                    zScale = 0.2f,
//                    yAngle = 90f
//                )
//            )
//            meshList.add(
//                MetalProfile(
//                    data,
//                    x = startXRung -1.5f,
//                    y = startYRung - 2.5f,
//                    xScale = 0.62f,
//                    yScale = 0.5f,
//                    zScale = 0.2f,
//                    zAngle = 90f
//                )
//            )
            startXRung += rungIntervalLanding
            startYRung += rungIntervalHeight
        }
        return meshList
    }
}