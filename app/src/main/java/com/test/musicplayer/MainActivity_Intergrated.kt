package com.test.musicplayer

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.ListView
import android.widget.ProgressBar
import com.test.musicplayer.module.Post
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.playlist_layout.*
import okhttp3.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

import java.io.IOException
import java.util.ArrayList
import java.util.HashMap

class MainActivity_Intergrated : AppCompatActivity() {


    private val client = OkHttpClient()
    /**
     * The [android.support.v4.view.PagerAdapter] that will provide
     * fragments for each of the sections. We use a
     * [FragmentPagerAdapter] derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * [android.support.v4.app.FragmentStatePagerAdapter].
     */
    /**
     * The [ViewPager] that will host the section contents.
     */
    private val mViewPager: ViewPager? = null
    private var authToken: String? = null
    private val myGenreMap = HashMap<String, Int>()
    private val progressBar: ProgressBar? = null
    val eventDatasForQueue: List<EventData> = ArrayList()

    /* internal var listview: ListView */

    internal val playListArray = ArrayList<String>()
    internal val songArray = ArrayList<SongInfo>()

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId

        /*
        if (id == R.id.action_logout) {
            AuthenticationClient.clearCookies(this);
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        */
        return super.onOptionsItemSelected(item)
    }


    /**
     * A [FragmentPagerAdapter] that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    /*inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getCount(): Int {
            // Show 3 total pages.
            return 2
        }

        override fun getPageTitle(position: Int): CharSequence? {
            when (position) {
                0 -> return "Discover"
                1 -> return "Schedule"
            }
            return null
        }

        override fun getItem(position: Int): Fragment {
            if (position == 0) {
                val args = Bundle()
                args.putString("token", intent.getStringExtra("AUTH_TOKEN"))
                val fragment = DiscoverFragment.newInstance()
                fragment.setArguments(args)
                return fragment
            } else {
                return ScheduleFragment.newInstance()
            }
        }

    }*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.playlist_layout)
        val context = this
        recyclerView.layoutManager = LinearLayoutManager(context)


        //progressBar = findViewById(R.id.progress_bar);


        this.authToken = this.intent.getStringExtra("AUTH_TOKEN")

        Log.v("Auth", authToken)

        val request = Request.Builder()
            .url("https://api.spotify.com/v1/me/playlists")
            .addHeader("Authorization", "Bearer " + authToken!!)
            .build()

        client.newCall(request).enqueue(object : Callback {
            internal var handler = Handler(context.mainLooper)

            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {

                if (!response.isSuccessful) throw IOException("Unexpected code $response")

                val json = response.body().string()

                Log.v("Response", json)

                try {
                    val jsonObject = JSONObject(json)
                    val playList = jsonObject.getJSONArray("items")
                    for (i in 0 until playList.length()){
                        val playListId = playList.getJSONObject(i).getString("id")
                        playListArray.add(playListId)
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }

                for (j in playListArray.indices) {
                    val request2 = Request.Builder()
                        .url("https://api.spotify.com/v1/playlists/" + playListArray[j] + "/tracks")
                        .addHeader("Authorization", "Bearer " + authToken!!)
                        .build()

                    client.newCall(request2).enqueue(object : Callback {
                        internal var handler = Handler(context.mainLooper)

                        override fun onFailure(call: Call, e: IOException) {
                            e.printStackTrace()
                        }

                        @Throws(IOException::class)
                        override fun onResponse(call: Call, response: Response) {

                            if (!response.isSuccessful) throw IOException("Unexpected code $response")

                            val json2 = response.body().string()

                            Log.v("Response", json2)

                            try {
                                val jsonObject2 = JSONObject(json2)
                                val items2 = jsonObject2.getJSONArray("items")
                                for (k in 0 until items2.length()) {
                                    val albumName = items2.getJSONObject(k).getJSONObject("track").getJSONObject("album")
                                        .getString("name")
                                    val artistName = items2.getJSONObject(k).getJSONObject("track").getJSONArray("artists")
                                        .getJSONObject(0).getString("name")
                                    val songName = items2.getJSONObject(k).getJSONObject("track").getString("name")
                                    val websiteURL = items2.getJSONObject(k).getJSONObject("track").getString("preview_url")
                                    songArray.add(SongInfo(songName, artistName, albumName, websiteURL))
                                }
                            } catch (e: JSONException) {
                                e.printStackTrace()
                            }

                            /* setSupportActionBar(toolbar) */
                            val posts: ArrayList<Post> = ArrayList()
                            for (i in 0 until songArray.size) {
                                posts.add(
                                    Post(songArray[i].getSongName(), songArray[i].getArtistName(), songArray[i].getAlbumName(),
                                        songArray[i].getURL())
                                )
                            }

                            runOnUiThread {
                                // Stuff that updates the UI
                                recyclerView.adapter = CustomAdapter(posts)
                            }
                        }
                    })
                }

            }
        })

    }

    companion object {
        private val CLIENT_ID = "6c6016da831348e18df08257e074c9c4"
    }
}



