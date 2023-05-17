package com.rundgrun.stairs.domain

import android.content.Context
import android.content.res.Resources.NotFoundException
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

object FileUtils {
    fun readTextFromRaw(context: Context, resourceId: Int): String {
        val stringBuilder = StringBuilder()
        try {
            var bufferedReader: BufferedReader? = null
            try {
                val inputStream = context.resources.openRawResource(resourceId)
                bufferedReader = BufferedReader(InputStreamReader(inputStream))
                bufferedReader.forEachLine {
                    stringBuilder.append(it)
                    stringBuilder.append("\r\n")
                }
            } finally {
                bufferedReader?.close()
            }
        } catch (ioex: IOException) {
            ioex.printStackTrace()
        } catch (nfex: NotFoundException) {
            nfex.printStackTrace()
        }
        return stringBuilder.toString()
    }

    fun convertObjToArray(context: Context, resourceId: Int): FloatArray {
        val vertexList = ArrayList<Vertex>()
        val indexList = ArrayList<Int>()
        val sortList = ArrayList<Float>()
        try {
            var bufferedReader: BufferedReader? = null
            try {
                val inputStream = context.resources.openRawResource(resourceId)
                bufferedReader = BufferedReader(InputStreamReader(inputStream))
                bufferedReader.forEachLine {
                    val array = it.split(" ")
                    if (array[0] == "v") {
                        val x = array[1].toFloat()
                        val y = array[2].toFloat()
                        val z = array[3].toFloat()
                        val vertex = Vertex(x, y, z)
                        vertexList.add(vertex)
                    }
                    if (array[0] == "f") {
                        val x = array[1].split("/")[0].toInt()
                        val y = array[2].split("/")[0].toInt()
                        val z = array[3].split("/")[0].toInt()
                        indexList.add(x)
                        indexList.add(y)
                        indexList.add(z)
                    }
                }
                for (index in 0 until indexList.size) {
                    val vertex = vertexList[indexList[index] - 1]
                    sortList.add(vertex.x)
                    sortList.add(vertex.y)
                    sortList.add(vertex.z)
                }
            } finally {
                bufferedReader?.close()
            }
        } catch (ioex: IOException) {
            ioex.printStackTrace()
        } catch (nfex: NotFoundException) {
            nfex.printStackTrace()
        }
        return FloatArray(sortList.size) { sortList[it] }
    }

    fun convertObjToArrayWithTextures(context: Context, resourceId: Int): FloatArray {
        val vertexList = ArrayList<Vertex>()
        val textureVertexList = ArrayList<TextureVertex>()
        val faceList = ArrayList<Face>()
        val sortList = ArrayList<Float>()
        try {
            var bufferedReader: BufferedReader? = null
            try {
                val inputStream = context.resources.openRawResource(resourceId)
                bufferedReader = BufferedReader(InputStreamReader(inputStream))
                bufferedReader.forEachLine {
                    val array = it.split(" ")
                    if (array[0] == "v") {
                        val x = array[1].toFloat()
                        val y = array[2].toFloat()
                        val z = array[3].toFloat()
                        val vertex = Vertex(x, y, z)
                        vertexList.add(vertex)
                    }
                    if (array[0] == "vt") {
                        val x = array[1].toFloat()
                        val y = array[2].toFloat()
                        val textureVertex = TextureVertex(x, y)
                        textureVertexList.add(textureVertex)
                    }
                    if (array[0] == "f") {
                        val vOne = array[1].split("/")[0].toInt()
                        val vtOne = array[1].split("/")[1].toInt()
                        val faceOne = Face(vOne,vtOne,0)
                        faceList.add(faceOne)

                        val vTwo = array[2].split("/")[0].toInt()
                        val vtTwo = array[2].split("/")[1].toInt()
                        val faceTwo = Face(vTwo,vtTwo,0)
                        faceList.add(faceTwo)

                        val vThree = array[3].split("/")[0].toInt()
                        val vtThree = array[3].split("/")[1].toInt()
                        val faceThree = Face(vThree,vtThree,0)
                        faceList.add(faceThree)
                    }
                }
                for (index in 0 until faceList.size) {
                    val currentFace = faceList[index]
                    val vertex = vertexList[ currentFace.vertexIndex - 1]
                    sortList.add(vertex.x)
                    sortList.add(vertex.y)
                    sortList.add(vertex.z)
                    val textureVertex = textureVertexList[currentFace.textureIndex -1]
                    sortList.add(textureVertex.x)
                    sortList.add(textureVertex.y)
                }
            } finally {
                bufferedReader?.close()
            }
        } catch (ioex: IOException) {
            ioex.printStackTrace()
        } catch (nfex: NotFoundException) {
            nfex.printStackTrace()
        }
        return FloatArray(sortList.size) { sortList[it] }
    }

    fun convertObjToArrayOfLines(context: Context, resourceId: Int): FloatArray {
        val vertexList = ArrayList<Vertex>()
        val indexList = ArrayList<Int>()
        val sortList = ArrayList<Float>()
        try {
            var bufferedReader: BufferedReader? = null
            try {
                val inputStream = context.resources.openRawResource(resourceId)
                bufferedReader = BufferedReader(InputStreamReader(inputStream))
                bufferedReader.forEachLine {
                    val array = it.split(" ")
                    if (array[0] == "v") {
                        val x = array[1].toFloat()
                        val y = array[2].toFloat()
                        val z = array[3].toFloat()
                        val vertex = Vertex(x, y, z)
                        vertexList.add(vertex)
                    }
                    if (array[0] == "f") {
                        val x = array[1].split("/")[0].toInt()
                        val y = array[2].split("/")[0].toInt()
                        val z = array[3].split("/")[0].toInt()
                        indexList.add(x)
                        indexList.add(y)
                        indexList.add(z)
                    }
                }
                var indexOfVertices = 1
                var verticesOne = Vertex(0f,0f,0f)
                for (index in 0 until indexList.size) {
                    val vertex = vertexList[indexList[index] - 1]
                    if (indexOfVertices == 1){
                        verticesOne = vertex
                        sortList.add(vertex.x)
                        sortList.add(vertex.y)
                        sortList.add(vertex.z)
                        indexOfVertices++
                    } else if(indexOfVertices == 2){
                        sortList.add(vertex.x)
                        sortList.add(vertex.y)
                        sortList.add(vertex.z)
                        sortList.add(vertex.x)
                        sortList.add(vertex.y)
                        sortList.add(vertex.z)
                        indexOfVertices++
                    }
                    else if(indexOfVertices == 3){
                        sortList.add(vertex.x)
                        sortList.add(vertex.y)
                        sortList.add(vertex.z)
                        sortList.add(vertex.x)
                        sortList.add(vertex.y)
                        sortList.add(vertex.z)
                        sortList.add(verticesOne.x)
                        sortList.add(verticesOne.y)
                        sortList.add(verticesOne.z)
                        indexOfVertices = 1
                    }
                }
            } finally {
                bufferedReader?.close()
            }
        } catch (ioex: IOException) {
            ioex.printStackTrace()
        } catch (nfex: NotFoundException) {
            nfex.printStackTrace()
        }
        return FloatArray(sortList.size) { sortList[it] }
    }
}