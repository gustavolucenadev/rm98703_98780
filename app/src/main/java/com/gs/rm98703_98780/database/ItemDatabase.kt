package com.gs.rm98703_98780.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gs.rm98703_98780.model.ItemModel

@Database(entities = [ItemModel::class], version = 3)
abstract class ItemDatabase : RoomDatabase() {

    abstract fun itemDao(): ItemDao
}