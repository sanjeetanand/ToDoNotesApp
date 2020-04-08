package com.example.todonotes.adaptor

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.todonotes.clicklisteners.ClickListners
import com.example.todonotes.R
import com.example.todonotes.db.Notes
import com.google.android.material.floatingactionbutton.FloatingActionButton

class NotesAdaptor(list: ArrayList<com.example.todonotes.db.Notes>, clickListners: ClickListners) : RecyclerView.Adapter<NotesAdaptor.ViewHolder>() {

    var notesList:List<Notes> = list
    var clickListners: ClickListners = clickListners

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textViewHeading:TextView = itemView.findViewById(R.id.textViewHeading)
        var textViewDescription:TextView = itemView.findViewById(R.id.textViewDescription)
        //var checkBoxMarkStatus:CheckBox = itemView.findViewById(R.id.checkBoxMarkStatus)
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
        //holder.checkBoxMarkStatus.isChecked = notes.status
        if(notes.status){
            holder.tickBox.backgroundTintList = ColorStateList.valueOf(0xFFB50373.toInt())
        } else {
            holder.tickBox.backgroundTintList = ColorStateList.valueOf(0xFF120D0D.toInt())
        }

        holder.itemView.setOnClickListener {
            clickListners.onClick(notes)
        }
        holder.tickBox.setOnClickListener{
            if(notes.status){
                notes.status = false
                holder.tickBox.backgroundTintList = ColorStateList.valueOf(0xFF120D0D.toInt())

            } else {
                notes.status = true
                holder.tickBox.backgroundTintList = ColorStateList.valueOf(0xFFB50373.toInt())
            }
            //holder.checkBoxMarkStatus.isChecked = notes.status
            clickListners.onUpdate(notes)
        }

        /*holder.checkBoxMarkStatus.setOnCheckedChangeListener(object :  CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                notes.status = isChecked
                if(notes.status){
                    holder.tickBox.backgroundTintList = ColorStateList.valueOf(0xFFB50373.toInt())
                } else {
                    holder.tickBox.backgroundTintList = ColorStateList.valueOf(0xFF120D0D.toInt())
                }
                clickListners.onUpdate(notes)
            }

        })*/
    }
}