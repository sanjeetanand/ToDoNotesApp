package com.example.todonotes.view

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.work.Constraints
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.example.todonotes.NotesApp
import com.example.todonotes.utils.AppConstant
import com.example.todonotes.R
import com.example.todonotes.adaptor.NotesAdaptor
import com.example.todonotes.clicklisteners.ClickListeners
import com.example.todonotes.db.Notes
import com.example.todonotes.utils.StoredSession
import com.example.todonotes.workmanager.MyWorker
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    lateinit var addNotes:FloatingActionButton
    lateinit var recyclerView: RecyclerView
    var notesList:ArrayList<Notes> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bindViews()
        setUpSharedPref()
        setTitle()
        getDataFromDatabase()
        clickListeners()
        setUpRecyclerView()
        setUpWorkManager()
    }

    private fun bindViews(){
        addNotes = findViewById(R.id.addNotes)
        recyclerView = findViewById(R.id.recyclerView)
    }

    private fun setUpSharedPref(){
        StoredSession.init(this)
    }

    private fun setTitle(){
        var title:String? = StoredSession.readString(AppConstant.FULL_NAME)
        supportActionBar?.subtitle = "Hi, $title"
    }

    private fun getDataFromDatabase() {
        val notesApp = applicationContext as NotesApp
        val notesDao = notesApp.getNotesDb().notesDao()
        notesList.addAll(notesDao.getAll())
    }

    private fun clickListeners(){
        addNotes.setOnClickListener {
            var intent:Intent = Intent(this@MainActivity,AddNotesActivity::class.java)
            startActivityForResult(intent,AppConstant.CODE_ACTIVITY)
        }
    }

    private fun setUpRecyclerView() {
        var clickListeners: ClickListeners = object : ClickListeners {

            override fun onClick(notes: Notes) {
                var intent:Intent = Intent(this@MainActivity, DetailActivity::class.java)
                intent.putExtra(AppConstant.HEADING, notes.heading)
                intent.putExtra(AppConstant.DESCRIPTION, notes.description)
                intent.putExtra(AppConstant.IMAGE_PATH,notes.imagePath)
                startActivity(intent)
            }

            override fun onUpdate(notes: Notes) {
                val notesApp = applicationContext as NotesApp
                val notesDao = notesApp.getNotesDb().notesDao()
                notesDao.updateNotes(notes)
            }
        }

        var adapter:NotesAdaptor = NotesAdaptor(notesList, clickListeners)
        var linearLayoutManager:LinearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = adapter
    }

    private fun setUpWorkManager() {
        val constraint = Constraints.Builder()
            .build()
        val request = PeriodicWorkRequest.Builder(MyWorker::class.java, 1, TimeUnit.DAYS)
            .setConstraints(constraint)
            .build()
        WorkManager.getInstance(this).enqueue(request)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == AppConstant.CODE_ACTIVITY){
            if(resultCode == Activity.RESULT_OK){
                var heading = data?.getStringExtra(AppConstant.HEADING)
                var description = data?.getStringExtra(AppConstant.DESCRIPTION)
                var imagePath = data?.getStringExtra(AppConstant.IMAGE_PATH)

                var notes =
                    Notes(heading = heading!!, description = description!!, imagePath = imagePath!!)
                addNotesToDb(notes)
                notesList.add(notes)
                recyclerView.adapter?.notifyItemInserted(notesList.size - 1)
            }
        }
    }

    private fun addNotesToDb(notes:Notes) {
        val notesApp = applicationContext as NotesApp
        val notesDao = notesApp.getNotesDb().notesDao()
        notesDao.insert(notes)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true;
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item?.itemId == R.id.blog){
            val intent = Intent(this, BlogActivity::class.java)
            startActivity(intent)
        }else if(item?.itemId == R.id.logout){
            logout()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun logout() {
        StoredSession.write(AppConstant.IS_LOGGED_IN, false)
        startActivity(Intent(this@MainActivity, LoginActivity::class.java))
        finish()
    }

}
