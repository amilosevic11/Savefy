package com.rma.savefy.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.rma.savefy.models.Emails

@Dao
interface EmailsDao {
    @Insert(onConflict = REPLACE)
    suspend fun insert(email: Emails)

    @Query("SELECT `email` FROM emails")
    suspend fun getAll() : List<String>

    @Query("DELETE FROM emails")
    suspend fun deleteAll()
}