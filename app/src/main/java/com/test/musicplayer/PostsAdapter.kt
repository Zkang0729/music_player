package com.test.musicplayer

import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.test.musicplayer.R.id.playButton
import com.test.musicplayer.module.Post
import kotlinx.android.synthetic.main.songs_layout.view.*

/*class PostsAdapter(val posts: ArrayList<Post>, val context:Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.bindItems(posts[position])
    }

    override fun getItemCount() = posts.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.songs_layout, parent, false)
        return ViewHolder(view)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(user: Post) {
            itemView.songName.text=user.songName
            itemView.artistName.text=user.artistName
            itemView.albumName.text=user.albumName
        }
    }
}*/
class CustomAdapter(val userList: ArrayList<Post>) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.songs_layout, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: CustomAdapter.ViewHolder, position: Int) {
        holder.bindItems(userList[position])
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return userList.size
    }

    //the class is holding the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(user: Post) {
            itemView.songName.text = user.songName
            itemView.artistName.text = user.artistName
            itemView.albumName.text = user.albumName
            val url = user.songs
            val mediaPlayer: MediaPlayer? = MediaPlayer().apply {
                AudioManager.STREAM_MUSIC
                setDataSource(url)
                prepare() // might take long! (for buffering, etc)
                start()
            }
        }

    }
}