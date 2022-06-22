package com.rma.savefy.models

data class Results(
    val Type: String,
    val Amount: String,
    val Description: String,
    val Year: String,
    val Month: String,
    var Day: String,
    val Hour: String
)
