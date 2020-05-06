package com.example.todonotes.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.todonotes.R
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.ParsedRequestListener
import com.example.todonotes.adaptor.BlogAdaptor
import com.example.todonotes.model.JsonResponse

class BlogActivity : AppCompatActivity() {

    lateinit var recyclerViewBlog: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blog)

        bindViews()
        getBlog()
    }

    private fun bindViews(){
        recyclerViewBlog = findViewById(R.id.recyclerViewBlog)
    }

    private fun getBlog() {
        AndroidNetworking.get("http://www.mocky.io/v2/5926ce9d11000096006ccb30")
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsObject(JsonResponse::class.java, object: ParsedRequestListener<JsonResponse> {
                override fun onResponse(response: JsonResponse?) {
                    setUpRecyclerView(response)
                }

                override fun onError(anError: ANError?) {
                    Log.e("BlogActivity", anError?.localizedMessage)
                }

            })
    }

    private fun setUpRecyclerView(response: JsonResponse?){
        val blogAdapter = BlogAdaptor(response!!.data)
        var linearLayoutManager: LinearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        recyclerViewBlog.layoutManager = linearLayoutManager
        recyclerViewBlog.adapter = blogAdapter
    }
}
