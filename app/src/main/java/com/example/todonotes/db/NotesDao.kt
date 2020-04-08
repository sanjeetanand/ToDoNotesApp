package com.example.todonotes.db

import androidx.room.*

@Dao
interface  NotesDao {

    @Query("Select * from notesData")
    fun getAll():List<Notes>

    @Insert(onConflict = androidx.room.OnConflictStrategy.REPLACE)
    fun insert(notes:Notes)

    @Update
    fun updateNotes(notes:Notes)

    @Delete
    fun delete(notes:Notes)
}