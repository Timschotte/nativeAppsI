package com.example.mymemory.activities

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.*
import com.example.mymemory.R
import com.example.mymemory.fragments.AddEditMemoryFragment
import com.example.mymemory.model.Memory
import com.example.mymemory.model.Quote
import com.example.mymemory.ui.MemoryViewModel
import com.example.mymemory.ui.QuoteViewModel
import kotlinx.android.synthetic.main.activity_memory_list.*
import kotlinx.android.synthetic.main.memory_list.*
import kotlinx.android.synthetic.main.memory_list_content.view.*
import org.jetbrains.anko.doAsync

class MemoryListActivity : AppCompatActivity(){
    private var twoPane: Boolean = false

    //private var memories: List<Memory>? = null
    private lateinit var memoryViewModel: MemoryViewModel
    //private lateinit var quoteViewModel: QuoteViewModel

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
        //quoteViewModel = ViewModelProviders.of(this).get(QuoteViewModel::class.java)
        memoryViewModel.getAllMemories().observe(this, Observer<List<Memory>> {
            adapter.submitList(it)
        })

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT.or(ItemTouchHelper.RIGHT)) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                doAsync{
                    memoryViewModel.deleteMemory(adapter.getMemory(viewHolder.adapterPosition))
                }
                Toast.makeText(baseContext, "Memory Removed!", Toast.LENGTH_LONG).show()
            }
        }
        ).attachToRecyclerView(memory_list)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.memory_list_menu, menu)
        //true means it will be displayed
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.delete_all_memories -> {
                doAsync {
                    memoryViewModel.deleteAllMemories()
                }
                Toast.makeText(baseContext, "All memories deleted!", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.load_sample_memories -> {
                createMemories()
                Toast.makeText(baseContext, "Sample memories loaded!", Toast.LENGTH_SHORT).show()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }


    /**
     * Starts the Activity
     * - Allocate resources
     * - register click listeners
     * - update UI
     */
    override fun onStart() {
        super.onStart()

        fab.setOnClickListener { view ->
            val intent = Intent(this, AddEditMemoryActivity::class.java).apply {
            }
            startActivity(intent)
        }



    }

    private fun createMemories(){
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
            doAsync {
                memoryViewModel.insertMemory(theMemory)
                memoryViewModel.insertQuote(theQuote)
            }
        }
    }


    /**
     * Stops the Activity
     * - unregister listeners
     * - release allocated resources
     */
    override fun onStop() {
        super.onStop()

        fab.setOnClickListener(null)
        //memory_list.adapter = null
    }

    /**
     * Starts a new activity that will display the selected comic in more detail.
     * This function is only called in portrait mode.
     */
    private fun startNewActivityForDetail(item: Memory) {
        val intent = Intent(this, AddEditMemoryActivity::class.java).apply {
            putExtra(AddEditMemoryFragment.ARG_MEMORY, item)
        }
        startActivity(intent)
    }

    /**
     * Replaces the current (or empty) fragment in the detail container
     * with a fragment that displays the selected memory.
     * This function is only called in landscape mode.
     */
    private fun placeMemoryInDetailFragment(item: Memory) {
        val fragment = AddEditMemoryFragment.newInstance(item)
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

        //we use a ListAdapter instead of a normal adapter so we don't neem to update the entire list everytime.
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
            //get the memory
            val memory: Memory = getItem(position)

            //fill the layout with data
            holder.title.text = memory.title
            holder.memoryText.text = memory.memoryText
            holder.date.text = memory.date

            with(holder.itemView) {
                tag = memory // Save the memory represented by this view
                setOnClickListener(onClickListener)
            }
        }

        //we create this function so we can pass the memory to the viewmodel to delete it
        fun getMemory(position: Int): Memory{
            return getItem(position)
        }

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val title: TextView = view.title
            val date: TextView = view.date
            val memoryText: TextView = view.memoryText
        }
    }
}