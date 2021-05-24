package com.bwawczak.datz_ratz.models

import java.io.Serializable

data class User(
    val id: String = "",
    var firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val phone: String = "",
    val snakes: ArrayList<Snake> = arrayListOf()


) : Serializable

