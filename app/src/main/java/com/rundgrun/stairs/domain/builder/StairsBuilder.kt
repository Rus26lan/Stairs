package com.rundgrun.stairs.domain.builder

import com.rundgrun.stairs.domain.mesh.Mesh

object StairsBuilder {

    fun getStraightLadder(stairsConfig: StairsConfig):ArrayList<Mesh>  {
        val meshList = ArrayList<Mesh>()
        var scale = 1f
        if (stairsConfig.height > stairsConfig.landing) {
            scale = 1 / stairsConfig.height
        }
        return  meshList
    }
}