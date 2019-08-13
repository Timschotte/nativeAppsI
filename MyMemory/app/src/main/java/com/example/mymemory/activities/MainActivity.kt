package com.example.mymemory.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.mymemory.R
/**
 * An activity representing a screen with 3 options, each option launches a different Activity
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    /**
     * Launches the Quote-activity
     */
    fun launchQuoteActivity(view: View) {
        val intent = Intent(this, QuoteActivity::class.java)
        startActivity(intent)
    }

    /**
     * Launches the Memorylist-activity
     */
    fun launchMemoryListActivity(view: View) {
        val intent = Intent(this, MemoryListActivity::class.java)
        startActivity(intent)
    }

    /**
     * Launches the AddEditMemoryActivity
     */
    fun launchAddEditMemoryActivity(view: View) {
        val intent = Intent(this, AddEditMemoryActivity::class.java)
        startActivity(intent)
    }
}
