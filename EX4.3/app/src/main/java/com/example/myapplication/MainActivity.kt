package com.example.myapplication

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import java.util.logging.Logger

class MainActivity : AppCompatActivity() {



    private val PREFS_NAME = "lifeCycles"
    private val ON_CREATE_COUNT = "ON_CREATE_COUNT"
    private val ON_START_COUNT = "ON_START_COUNT"
    private val ON_RESUME_COUNT = "ON_RESUME_COUNT"
    private val ON_STOP_COUNT = "ON_STOP_COUNT"
    private val ON_RESTART_COUNT = "ON_RESTART_COUNT"
    private val ON_DESTROY_COUNT = "ON_DESTROY_COUNT"
    private val ON_PAUSE_COUNT = "ON_PAUSE_COUNT"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        increaseSharedPrefWithOne(ON_CREATE_COUNT)
        setContentView(R.layout.activity_main)
        Log.d("MainActivity", "onCreate triggered")
    }

    override fun onStart() {
        super.onStart()
        increaseSharedPrefWithOne(ON_START_COUNT)
        updateView()
        Log.d("MainActivity", "onStart triggered")
    }

    override fun onResume() {
        super.onResume()
        increaseSharedPrefWithOne(ON_RESUME_COUNT)
        updateView()
        Log.d("MainActivity", "onResume triggered")
    }

    override fun onPause() {
        super.onPause()
        increaseSharedPrefWithOne(ON_PAUSE_COUNT)
        Log.d("MainActivity", "onPause triggered")
    }

    override fun onStop() {
        super.onStop()
        increaseSharedPrefWithOne(ON_STOP_COUNT)
        Log.d("MainActivity", "onStop triggered")
    }

    override fun onRestart() {
        super.onRestart()
        increaseSharedPrefWithOne(ON_RESTART_COUNT)
        Log.d("MainActivity", "onRestart triggered")
    }

    override fun onDestroy() {
        increaseSharedPrefWithOne(ON_DESTROY_COUNT)
        super.onDestroy()

        Log.d("MainActivity", "onDestroy triggered")
    }

    fun print(msg: String){
        Log.d("Activity State ",msg)
    }

    fun updateView(){
        txtCreate.setText(getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).getString(ON_CREATE_COUNT , "0"))
        txtStart.setText(getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).getString(ON_START_COUNT , "0"))
        txtResume.setText(getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).getString(ON_RESUME_COUNT , "0"))
        txtStop.setText(getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).getString(ON_STOP_COUNT , "0"))
        txtRestart.setText(getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).getString(ON_RESTART_COUNT , "0"))
        txtDestroy.setText(getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).getString(ON_DESTROY_COUNT , "0"))
        txtPause.setText(getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).getString(ON_PAUSE_COUNT , "0"))
    }

    fun increaseSharedPrefWithOne(pref: String) {
        val currentCount : String = getStringValueForPref(pref)
        return getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit().putString(pref, (currentCount.toInt() + 1).toString()).apply()
    }

    fun getStringValueForPref(pref: String) : String{
        when(pref) {
            ON_CREATE_COUNT -> return getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).getString(ON_CREATE_COUNT , "0")!!
            ON_START_COUNT -> return getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).getString(ON_START_COUNT , "0")!!
            ON_RESUME_COUNT -> return getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).getString(ON_RESUME_COUNT , "0")!!
            ON_STOP_COUNT -> return getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).getString(ON_STOP_COUNT , "0")!!
            ON_RESTART_COUNT -> return getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).getString(ON_RESTART_COUNT , "0")!!
            ON_DESTROY_COUNT -> return getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).getString(ON_DESTROY_COUNT , "0")!!
            ON_PAUSE_COUNT -> return getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).getString(ON_PAUSE_COUNT , "0")!!
        }
        return "0"
    }

    fun launchSecondActivity(view: View) {
        Log.d(null, "Button clicked!")
        val intent = Intent(this, Main2Activity::class.java)
        startActivity(intent)
    }
}
