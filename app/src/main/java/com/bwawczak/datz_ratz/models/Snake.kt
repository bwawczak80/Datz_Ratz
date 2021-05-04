package com.bwawczak.datz_ratz.models

import java.io.Serializable

class Snake(

    val id: String = "",
    val snakeName: String = "",
    val morph: String = "",
    val logs: ArrayList<LogItem>
) : Serializable {}