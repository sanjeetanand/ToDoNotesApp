package com.example.todonotes.clicklisteners

import com.example.todonotes.db.Notes

interface ClickListeners {
    fun onClick(notes: Notes)
    fun onUpdate(notes: Notes)
}