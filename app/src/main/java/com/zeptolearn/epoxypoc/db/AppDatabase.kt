package com.zeptolearn.epoxypoc.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zeptolearn.epoxypoc.db.dao.ContainersDao
import com.zeptolearn.epoxypoc.db.entity.Container


@Database(entities = [Container::class], version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun getContainerDao(): ContainersDao

    companion object {
        const val DATABASE_NAME: String = "epoxypoc"
    }

}








