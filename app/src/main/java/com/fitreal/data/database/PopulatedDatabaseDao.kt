package com.fitreal.data.database

import androidx.room.Dao
import androidx.room.Query

@Dao
interface PopulatedDatabaseDao {
    @Query("SELECT isDatabasePopulated FROM populatedDatabase Limit 1")
    fun isDatabasePopulated(): Boolean?
    @Query("INSERT INTO populatedDatabase VALUES(1)")
    fun flagDatabaseAsPopulated()
}