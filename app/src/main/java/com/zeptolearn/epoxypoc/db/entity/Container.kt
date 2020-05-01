package com.zeptolearn.epoxypoc.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose

@Entity(
    tableName = "Container"
)

data class Container(

    @ColumnInfo(name = "ColCount")
    @Expose
    var ColCount: Int,

    @PrimaryKey
    @ColumnInfo(name = "ViewType")
    @Expose
    var Viewtype: String,

    @ColumnInfo(name = "bgcolor")
    @Expose
    var bgcolor: String,

    @ColumnInfo(name = "title")
    @Expose
    var title: String,

    @Ignore
    @Expose
    val `data`: List<Data>
){
    constructor() : this(0,"","","", emptyList())
}