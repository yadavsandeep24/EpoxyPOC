package com.zeptolearn.epoxypoc.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose

@Entity(
    tableName = "Screen_data"
)
data class Data(

    @ColumnInfo(name = "bgurl")
    @Expose
    val bgurl: String,

    @PrimaryKey
    @ColumnInfo(name = "id")
    @Expose
    val id: String,

    @ColumnInfo(name = "title")
    @Expose
    val title: String,

    @ColumnInfo(name = "titleColor")
    @Expose
    val titleColor:String
)