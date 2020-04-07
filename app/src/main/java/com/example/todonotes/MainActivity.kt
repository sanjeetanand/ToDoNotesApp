package com.example.todonotes

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todonotes.adaptor.NotesAdaptor
import com.example.todonotes.clicklisteners.ClickListners
import com.example.todonotes.model.Notes
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

        addNotes.setOnClickListener {
            viewAddNotes()
        }
    }

    private fun viewAddNotes(){
        var view:View = LayoutInflater.from(this).inflate(R.layout.dialog_add_notes,null)

        var heading:EditText = view.findViewById(R.id.textInputHeading)
        var description:EditText = view.findViewById(R.id.textInputDescription)
        var add:Button = view.findViewById(R.id.buttonAdd)

        var dialog:AlertDialog = AlertDialog.Builder(this).setView(view).setCancelable(false).create()
        dialog.show()

        add.setOnClickListener {
            var h:String = heading.text.toString()
            var d:String = description.text.toString()
            if(h.isNotBlank()){
                if (d.isNotBlank()){
                    var n:Notes = Notes(h, d)
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
    }

    private fun setUpRecyclerView() {
        var clickListners: ClickListners = object : ClickListners {
            override fun onClick(notes: Notes) {
                var intent:Intent = Intent(applicationContext,DetailActivity::class.java)
                intent.putExtra(AppConstant.HEADING, notes.heading)
                intent.putExtra(AppConstant.DESCRIPTION, notes.description)
                startActivity(intent)
            }
        }

        var adapter:NotesAdaptor = NotesAdaptor(notesList, clickListners)
        var linearLayoutManager:LinearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = adapter
    }

    private fun setTitle(){
        supportActionBar?.title = sp.getString(AppConstant.FULL_NAME,"")
    }

    private fun addWidgets(){
        addNotes = findViewById(R.id.addNotes)
        recyclerView = findViewById(R.id.recyclerView)
        sp = getSharedPreferences(AppConstant.SP_NAME, Context.MODE_PRIVATE)
    }
}
