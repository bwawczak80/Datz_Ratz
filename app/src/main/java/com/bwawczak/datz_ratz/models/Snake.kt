package com.bwawczak.datz_ratz.models

import java.io.Serializable

class Snake(

    val snakeName: String = "",
    val morph: String = "",
    val logs: ArrayList<LogItem>
) : Serializable {}