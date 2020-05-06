package com.example.todonotes.adaptor

import android.content.res.ColorStateList
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.todonotes.clicklisteners.ClickListeners
import com.example.todonotes.R
import com.example.todonotes.db.Notes
import com.google.android.material.floatingactionbutton.FloatingActionButton

class NotesAdaptor(list: ArrayList<com.example.todonotes.db.Notes>, clickListeners: ClickListeners) : RecyclerView.Adapter<NotesAdaptor.ViewHolder>() {

    var notesList:List<Notes> = list
    var clickListeners: ClickListeners = clickListeners

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textViewHeading:TextView = itemView.findViewById(R.id.textViewHeading)
        var textViewDescription:TextView = itemView.findViewById(R.id.textViewDescription)
        var imageView:ImageView = itemView.findViewById(R.id.imageView)
        var tickBox:FloatingActionButton = itemView.findViewById(R.id.tickBox)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesAdaptor.ViewHolder {
        var view:View = LayoutInflater.from(parent.context).inflate(R.layout.notes_adaptor_layout,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return notesList.size
    }

    override fun onBindViewHolder(holder: NotesAdaptor.ViewHolder, position: Int) {
        var notes:Notes = notesList[position]
        holder.textViewHeading.text = notes.heading
        holder.textViewDescription.text = notes.description
        Glide.with(holder.itemView).load(notes.imagePath).into(holder.imageView)

        if(notes.status){
            holder.tickBox.backgroundTintList = ColorStateList.valueOf(0xFFB50373.toInt())
        } else {
            holder.tickBox.backgroundTintList = ColorStateList.valueOf(0xFF120D0D.toInt())
        }

        holder.itemView.setOnClickListener {
            clickListeners.onClick(notes)
        }
        holder.tickBox.setOnClickListener{
            if(notes.status){
                notes.status = false
                holder.tickBox.backgroundTintList = ColorStateList.valueOf(0xFF120D0D.toInt())
            } else {
                notes.status = true
                holder.tickBox.backgroundTintList = ColorStateList.valueOf(0xFFB50373.toInt())
            }
            clickListeners.onUpdate(notes)
        }
    }
}