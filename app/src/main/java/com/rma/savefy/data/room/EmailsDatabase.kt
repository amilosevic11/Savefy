package com.rma.savefy.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rma.savefy.models.Emails

@Database(entities = [Emails::class], version = 1, exportSchema = false)
abstract class EmailsDatabase: RoomDatabase() {
    abstract fun emailsDao(): EmailsDao
}