package com.test.musicplayer

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.util.Log
import android.view.Menu
import android.view.MenuItem

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        LoginButton.setOnClickListener {
            val password = passwordField.text.toString()
            Log.d("Harry", "Password is: $password")
            if (password == "Ffckffck5678910"){
                Log.d("Harry", "Success! Your password is valid! :)")
                startActivity(Intent(this,PlayList::class.java))
            }
        }
    }
}
