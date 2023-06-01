package com.rundgrun.stairs.domain.repository

import com.rundgrun.stairs.domain.mesh.Mesh

interface MeshDataRepository {
    fun getCurrentMeshList(): ArrayList<Mesh>
}