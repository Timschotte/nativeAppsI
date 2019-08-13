package com.example.mymemory.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.mymemory.R
import com.example.mymemory.fragments.QuoteFragment

/**
 * An activity showing a that is fetched from a public API
 */
class QuoteActivity : AppCompatActivity(){


    /**
     * Creates the Activity
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quote)
    }

    /**
     * Launches the activity
     */
    fun launchMainActivity(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

}
