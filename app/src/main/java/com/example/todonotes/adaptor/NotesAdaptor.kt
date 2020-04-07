package com.example.todonotes.adaptor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todonotes.clicklisteners.ClickListners
import com.example.todonotes.R
import com.example.todonotes.model.Notes

class NotesAdaptor(list:List<Notes>, clickListners: ClickListners) : RecyclerView.Adapter<NotesAdaptor.ViewHolder>() {

    var notesList:List<Notes> = list
    var clickListners: ClickListners = clickListners

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textViewHeading:TextView = itemView.findViewById(R.id.textViewHeading)
        var textViewDescription:TextView = itemView.findViewById(R.id.textViewDescription)

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
        holder.itemView.setOnClickListener {
            clickListners.onClick(notes)
        }
    }
}