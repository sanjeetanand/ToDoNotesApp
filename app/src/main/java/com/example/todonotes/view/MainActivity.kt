package com.example.todonotes.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todonotes.NotesApp
import com.example.todonotes.utils.AppConstant
import com.example.todonotes.R
import com.example.todonotes.adaptor.NotesAdaptor
import com.example.todonotes.clicklisteners.ClickListners
import com.example.todonotes.db.Notes
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    lateinit var addNotes:FloatingActionButton
    lateinit var recyclerView: RecyclerView
    lateinit var sp:SharedPreferences
    var notesList:ArrayList<Notes> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addWidgets()
        setTitle()
        getDataFromDatabase()

        addNotes.setOnClickListener {
            viewAddNotesDialog()
        }
        setUpRecyclerView()
    }

    private fun addWidgets(){
        addNotes = findViewById(R.id.addNotes)
        recyclerView = findViewById(R.id.recyclerView)
        sp = getSharedPreferences(AppConstant.SP_NAME, Context.MODE_PRIVATE)
    }

    private fun setTitle(){
        var title:String = sp.getString(AppConstant.FULL_NAME,"")
        supportActionBar?.subtitle = "Hi, $title"
    }

    private fun getDataFromDatabase() {
        val notesApp = applicationContext as NotesApp
        val notesDao = notesApp.getNotesDb().notesDao()
        notesList.addAll(notesDao.getAll())
    }

    private fun setUpRecyclerView() {
        var clickListners: ClickListners = object : ClickListners {
            override fun onClick(notes: Notes) {
                var intent:Intent = Intent(applicationContext, DetailActivity::class.java)
                intent.putExtra(AppConstant.HEADING, notes.heading)
                intent.putExtra(AppConstant.DESCRIPTION, notes.description)
                startActivity(intent)
            }

            override fun onUpdate(notes: Notes) {
                val notesApp = applicationContext as NotesApp
                val notesDao = notesApp.getNotesDb().notesDao()
                notesDao.updateNotes(notes)
            }

            override fun onDelete(notes: Notes) {
                val notesApp = applicationContext as NotesApp
                val notesDao = notesApp.getNotesDb().notesDao()
                notesDao.delete(notes)
                notesList.remove(notes)
                setUpRecyclerView()
            }
        }

        var adapter:NotesAdaptor = NotesAdaptor(notesList, clickListners)
        var linearLayoutManager:LinearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = adapter
    }

    private fun viewAddNotesDialog(){
        var view:View = LayoutInflater.from(this).inflate(R.layout.dialog_add_notes,null)

        var heading:EditText = view.findViewById(R.id.textInputHeading)
        var description:EditText = view.findViewById(R.id.textInputDescription)
        var add:Button = view.findViewById(R.id.buttonAdd)
        var cancel:Button = view.findViewById(R.id.buttonCancel)

        var dialog:AlertDialog = AlertDialog.Builder(this).setView(view).setCancelable(false).create()
        dialog.show()

        add.setOnClickListener {
            var h:String = heading.text.toString()
            var d:String = description.text.toString()
            if(h.isNotBlank()){
                if (d.isNotBlank()){
                    var n:Notes = Notes(heading = h, description = d)

                    addNotesToDb(n)
                    notesList.add(n)
                    setUpRecyclerView()
                    dialog.hide()
                } else {
                    Toast.makeText(this,"Please Enter Description",Toast.LENGTH_SHORT)
                }
            } else {
                Toast.makeText(this,"Please Enter Heading",Toast.LENGTH_SHORT)
            }
        }

        cancel.setOnClickListener {
            dialog.hide()
        }
    }

    private fun addNotesToDb(notes:Notes) {
        val notesApp = applicationContext as NotesApp
        val notesDao = notesApp.getNotesDb().notesDao()
        notesDao.insert(notes)
    }
}
