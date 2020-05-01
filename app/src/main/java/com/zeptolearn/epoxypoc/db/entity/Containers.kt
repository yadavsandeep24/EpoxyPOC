package com.zeptolearn.epoxypoc.db.entity

import com.google.gson.annotations.Expose


data class Containers(
    @Expose
    val Containers: List<Container>,
    @Expose
    val header: String
)