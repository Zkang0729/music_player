package com.test.musicplayer

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import com.test.musicplayer.R.id.toolbar
import com.test.musicplayer.module.Post
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.playlist_layout.*

import java.util.ArrayList

class PlayList : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.playlist_layout)
        setSupportActionBar(toolbar)
        val posts: ArrayList<Post> = ArrayList()
        for (i in 1..100) {
            posts.add(Post("Harry Kang" + i, "My name is Harry Kang!", "Harry Kang",
                "https://picsum.photos/600/300/?random&"))
        }
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = CustomAdapter(posts)
    }
}