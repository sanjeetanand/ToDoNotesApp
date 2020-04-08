package com.example.todonotes.clicklisteners

interface ClickListners {
    fun onClick(notes: com.example.todonotes.db.Notes)
    fun onUpdate(notes: com.example.todonotes.db.Notes)
}