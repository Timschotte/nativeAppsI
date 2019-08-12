package com.example.mymemory.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.mymemory.R
/**
 * An activity representing a screen with 3 options
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun launchQuoteActivity(view: View) {
        val intent = Intent(this, QuoteActivity::class.java)
        startActivity(intent)
    }

    fun launchMemoryListActivity(view: View) {
        val intent = Intent(this, MemoryListActivity::class.java)
        startActivity(intent)
    }
}
