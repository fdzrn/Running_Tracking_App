package com.androiddevs.runningappyt.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.androiddevs.runningappyt.utils.BitmapConverter

@Database(entities = [Run::class], version = 1)
@TypeConverters(BitmapConverter::class)
abstract class RunningDatabase : RoomDatabase() {
    abstract fun getRunDao(): RunDao
}