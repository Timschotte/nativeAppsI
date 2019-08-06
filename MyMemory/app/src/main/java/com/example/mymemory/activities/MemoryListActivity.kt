package com.example.mymemory.activities

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mymemory.R
import com.example.mymemory.fragments.MemoryDetailFragment
import com.example.mymemory.model.Memory
import com.example.mymemory.model.Quote
import com.example.mymemory.persistence.MemoryRepository
import com.example.mymemory.ui.MemoryViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_memory_list.*
import kotlinx.android.synthetic.main.memory_list.*
import kotlinx.android.synthetic.main.memory_list_content.view.*
import javax.inject.Inject

class MemoryListActivity : AppCompatActivity(){
    private var twoPane: Boolean = false

    //private var memories: List<Memory>? = null
    private lateinit var memoryViewModel: MemoryViewModel

    //@Inject
    //lateinit var memoryRepository: MemoryRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //activity_memory_list will point to a different layout depending on the current
        //  device configuration
        setContentView(R.layout.activity_memory_list)

        if (memory_detail_container != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            twoPane = true
        }

        //instead of providing the static list in the constructor, we observe the liveData-list
        var adapter = SimpleItemRecyclerViewAdapter(this, twoPane)

        memory_list.adapter = adapter
        memory_list.layoutManager = LinearLayoutManager(this)

        memoryViewModel = ViewModelProviders.of(this).get(MemoryViewModel::class.java)

        memoryViewModel.getAllMemories().observe(this, Observer<List<Memory>> {
            adapter.submitList(it)
        })

    }
    /**
     * Starts the Activity
     * - Allocate resources
     * - register click listeners
     * - update UI
     */
    override fun onStart() {
        super.onStart()

        //memories = createMemories()

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }



    }

    private fun createMemories(): List<Memory> {
        val memoryList = mutableListOf<Memory>()

        val resources = applicationContext.resources
        val quoteTexts = resources.getStringArray(R.array.quoteText)
        val lengths = resources.getStringArray(R.array.length)
        var authors = resources.getStringArray(R.array.author)
        val tags = resources.getStringArray(R.array.tags)
        val categories = resources.getStringArray(R.array.category)
        val dates = resources.getStringArray(R.array.date)
        val titles = resources.getStringArray(R.array.title)
        val ids = resources.getStringArray(R.array.id)
        val memoryTexts = resources.getStringArray(R.array.memoryText)
        val memoryTitles = resources.getStringArray(R.array.memoryTitle)
        val memoryIDs = resources.getStringArray(R.array.memoryID)

        for (i in 0 until quoteTexts.size) {
            val theQuote = Quote(quoteTexts[i], lengths[i].toInt(), authors[i], listOf(tags[i]), categories[i], dates[i], titles[i], ids[i])
            val theMemory = Memory(memoryIDs[i], memoryTexts[i], theQuote.date, memoryTitles[i])
            memoryList.add(theMemory)
        }
        return memoryList
    }


    /**
     * Stops the Activity
     * - unregister listeners
     * - release allocated resources
     */
    override fun onStop() {
        super.onStop()

        fab.setOnClickListener(null)
        memory_list.adapter = null
    }

    /**
     * Starts a new activity that will display the selected comic in more detail.
     * This function is only called in portrait mode.
     */
    private fun startNewActivityForDetail(item: Memory) {
        val intent = Intent(this, MemoryDetailActivity::class.java).apply {
            putExtra(MemoryDetailFragment.ARG_MEMORY, item)
        }
        startActivity(intent)
    }

    /**
     * Replaces the current (or empty) fragment in the detail container
     * with a fragment that displays the selected memory.
     * This function is only called in landscape mode.
     */
    private fun placeMemoryInDetailFragment(item: Memory) {
        val fragment = MemoryDetailFragment.newInstance(item)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.memory_detail_container, fragment)
            .commit()
    }


    /***********************************************************************************************
     * Recyclerview
     *
     ***********************************************************************************************
     */
    class SimpleItemRecyclerViewAdapter(private val parentActivity: MemoryListActivity,
                                        private val twoPane: Boolean) :
        ListAdapter<Memory, SimpleItemRecyclerViewAdapter.ViewHolder>(DIFF_CALLBACK) {

        companion object {
            private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Memory>() {
                override fun areItemsTheSame(oldItem: Memory, newItem: Memory): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(oldItem: Memory, newItem: Memory): Boolean {
                    return oldItem.memoryText == newItem.memoryText && oldItem.date == newItem.date
                            && oldItem.title == newItem.title
                }
            }
        }

        private val onClickListener: View.OnClickListener

        init {
            onClickListener = View.OnClickListener { v ->
                // Every view has a tag that can be used to store data related to that view
                // Here each item in the RecyclerView keeps a reference to the Memory it represents.
                // This allows us to reuse a single listener for all items in the list
                val item = v.tag as Memory
                if (twoPane) {
                    parentActivity.placeMemoryInDetailFragment(item)
                } else {
                    parentActivity.startNewActivityForDetail(item)
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.memory_list_content, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val memory = getItem(position)
            holder.name.text = memory.date

            with(holder.itemView) {
                tag = memory // Save the memory represented by this view
                setOnClickListener(onClickListener)
            }
        }

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val name: TextView = view.listname
        }
    }
}