package com.bwawczak.datz_ratz.models

import java.io.Serializable

class LogItem(
    val weight: String = "",
    val mealDate: String = "",
    val lastMeal: String = "",
    val isInShed: Boolean = false
) : Serializable {}