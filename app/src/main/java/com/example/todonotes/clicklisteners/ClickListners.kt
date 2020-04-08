package com.example.todonotes.clicklisteners

import com.example.todonotes.db.Notes

interface ClickListners {
    fun onClick(notes: Notes)
    fun onUpdate(notes: Notes)
    fun onDelete(notes: Notes)
}