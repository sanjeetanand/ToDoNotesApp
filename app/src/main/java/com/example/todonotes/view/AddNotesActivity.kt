package com.example.todonotes.view

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide
import com.example.todonotes.BuildConfig
import com.example.todonotes.R
import com.example.todonotes.utils.AppConstant
import com.example.todonotes.utils.StoredSession
import com.google.android.material.button.MaterialButton
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class AddNotesActivity : AppCompatActivity() {

    lateinit var heading:EditText
    lateinit var description:EditText
    lateinit var add:MaterialButton
    lateinit var cancel:MaterialButton
    lateinit var imageView:ImageView
    var picturePath = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_add_notes)

        bindView()
        setUpSharedPref()
        setTitle()
        clickListeners()

    }

    private fun bindView(){
        heading = findViewById(R.id.textInputHeading)
        description = findViewById(R.id.textInputDescription)
        add = findViewById(R.id.buttonAdd)
        cancel = findViewById(R.id.buttonCancel)
        imageView = findViewById(R.id.imageView)
    }

    private fun setUpSharedPref(){
        StoredSession.init(this)
    }

    private fun setTitle(){
        var title:String? =StoredSession.readString(AppConstant.FULL_NAME)
        supportActionBar?.subtitle = "Hi, $title"
    }

    private fun clickListeners(){

        add.setOnClickListener {
            var h:String = heading.text.toString()
            var d:String = description.text.toString()
            if(!h.isNullOrBlank()){
                if (!d.isNullOrBlank()){
                    if(!picturePath.isNullOrBlank()){
                        var intent = Intent()
                        intent.putExtra(AppConstant.HEADING,h)
                        intent.putExtra(AppConstant.DESCRIPTION,d)
                        intent.putExtra(AppConstant.IMAGE_PATH,picturePath)
                        setResult(Activity.RESULT_OK,intent)
                        finish()
                    } else {
                        Toast.makeText(this,"Please Select a Photo", Toast.LENGTH_SHORT)
                    }
                } else {
                    Toast.makeText(this,"Please Enter Description", Toast.LENGTH_SHORT)
                }
            } else {
                Toast.makeText(this,"Please Enter Heading", Toast.LENGTH_SHORT)
            }
        }

        cancel.setOnClickListener {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }

        imageView.setOnClickListener {
            if(checkAndRequestPermission()){
                imageSelectorDialog()
            }
        }
    }

    private fun checkAndRequestPermission() : Boolean{
        val cameraPermission = ContextCompat.checkSelfPermission(this,android.Manifest.permission.CAMERA)
        val storagePermission = ContextCompat.checkSelfPermission(this,android.Manifest.permission.READ_EXTERNAL_STORAGE)
        val listOfPermissions = ArrayList<String>()

        if(cameraPermission != PackageManager.PERMISSION_GRANTED){
            listOfPermissions.add(android.Manifest.permission.CAMERA)
        }

        if(storagePermission != PackageManager.PERMISSION_GRANTED){
            listOfPermissions.add(android.Manifest.permission.READ_EXTERNAL_STORAGE)
        }

        if(listOfPermissions.isNotEmpty()){
            ActivityCompat.requestPermissions(this,listOfPermissions.toTypedArray<String>(),AppConstant.PERMISSION_CODE)
            return false
        }
        return true;
    }

    override fun onRequestPermissionsResult(requestCode: Int,permissions: Array<out String>,grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            AppConstant.PERMISSION_CODE -> {
                if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    imageSelectorDialog()
                }
            }
        }
    }

    private fun imageSelectorDialog(){
        var view:View = LayoutInflater.from(this).inflate(R.layout.dialog_selector,null)
        var camera:TextView = view.findViewById(R.id.camera)
        var gallery:TextView = view.findViewById(R.id.gallery)

        var d: AlertDialog = AlertDialog.Builder(this).setView(view).setCancelable(true).create()

        camera.setOnClickListener {
            var cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            var photoFile:File? = createImage()
            if(photoFile != null){
                val photoURI = FileProvider.getUriForFile(this@AddNotesActivity,BuildConfig.APPLICATION_ID+".provider",photoFile)
                picturePath = photoFile.absolutePath
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                startActivityForResult(cameraIntent,AppConstant.CODE_CAMERA)
                d.hide()
            }
        }

        gallery.setOnClickListener {
            var intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent,AppConstant.CODE_GALLERY)
            d.hide()
        }

        d.show()
    }

    private fun createImage(): File {
        val timeStamp = SimpleDateFormat("yyyyMMddHHmmss").format(Date())
        val fileName = "ToDoNotes_"+timeStamp+"_"
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(fileName,".jpg",storageDir)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == Activity.RESULT_OK){
            when (requestCode) {
                AppConstant.CODE_GALLERY -> {

                    var imageSelected = data?.data
                    var filePath = arrayOf(MediaStore.Images.Media.DATA)
                    var c = contentResolver.query(imageSelected!!,filePath,null,null,null)
                    c?.moveToFirst()
                    var columnIndex = c?.getColumnIndex(filePath[0])
                    picturePath = c?.getString(columnIndex!!)!!
                    c.close()
                    Glide.with(this).load(picturePath).into(imageView)
                }
                AppConstant.CODE_CAMERA -> {
                    Glide.with(this).load(picturePath).into(imageView)
                }
            }
        }
    }
}
