package com.example.mymemory.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.mymemory.R
import com.example.mymemory.model.Memory
import com.example.mymemory.model.Quote
import com.example.mymemory.ui.MemoryViewModel
import com.example.mymemory.ui.QuoteViewModel
import kotlinx.android.synthetic.main.fragment_add_memory.*
import kotlinx.android.synthetic.main.fragment_add_memory.view.*
import org.jetbrains.anko.doAsync
import java.text.SimpleDateFormat
import java.util.*


class AddEditMemoryFragment : Fragment() {

    /**
     * The memory this fragment is representing
     */
    private lateinit var memory: Memory

    private lateinit var memoryViewModel: MemoryViewModel

    private lateinit var quoteViewModel: QuoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        memoryViewModel = ViewModelProviders.of(this).get(MemoryViewModel::class.java)

    }

    /**
     * Creating the view for this fragment.
     * - update UI
     */
    @SuppressLint("SimpleDateFormat")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_add_memory, container, false)

        if(activity is AppCompatActivity){
            (activity as AppCompatActivity).supportActionBar?.setTitle("Add new Memory-item")
        }
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        setHasOptionsMenu(true)

        arguments?.let {
            if (it.containsKey(ARG_MEMORY)) {
                // Load the memory specified by the fragment
                // arguments.
                memory = it.getSerializable(AddEditMemoryFragment.ARG_MEMORY) as Memory
                if(activity is AppCompatActivity){
                    (activity as AppCompatActivity).supportActionBar?.setTitle("Edit Memory-item")
                }
                rootView.edit_text.setText(memory.memoryText)
                rootView.edit_title.setText(memory.title)
                rootView.memoryDate.setText(memory.date)
                doAsync {
                    if(memoryViewModel.getQuoteForDate(memory.date) != null){
                        rootView.quoteText.setText(memoryViewModel.getQuoteForDate(memory.date)!!.quote)
                    }else{
                        rootView.quoteText.setText("Quote not available")
                    }

                }

            }else{
                fillNewMemoryFields(rootView, sdf)
            }
        }
        if(arguments == null){
            fillNewMemoryFields(rootView, sdf)
        }


        return rootView
    }

    private fun fillNewMemoryFields(rootView: View, sdf: SimpleDateFormat) {

        if (activity is AppCompatActivity) {
            (activity as AppCompatActivity).supportActionBar?.setTitle("Add new Memory-item")
        }
        val todaysDate: String = sdf.format(Date())
        rootView.memoryDate.setText(todaysDate)
        doAsync {
            val qotd: Quote? = memoryViewModel.getQuoteForDate(todaysDate)
            if(qotd != null){
                rootView.quoteText.setText(qotd.quote)
            }else{
                quoteViewModel = ViewModelProviders.of(this.weakRef.get()!!).get(QuoteViewModel::class.java)
                val quoteOfTheDayObserver = Observer<String> { newQOTD ->
                    rootView.quoteText.setText(newQOTD)
                }

                quoteViewModel.getQuoteText().observe(this.weakRef.get()!!, quoteOfTheDayObserver)
            }
        }


    }

    //create the menu so the save icon can be used
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_memory_menu, menu);
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.save_memory -> {
                saveMemory()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun saveMemory() {
        if (edit_title.text.toString().trim().isBlank() || edit_text.text.toString().trim().isBlank()) {
            Toast.makeText(context, "Can not insert empty memory!", Toast.LENGTH_LONG).show()
            return
        }
        if(this::memory.isInitialized){
            doAsync {
                memoryViewModel.insertMemory(
                    Memory(
                        id = memory.id,
                        memoryText = edit_text.text.toString().trim(),
                        title = edit_title.text.toString(),
                        date = memoryDate.text.toString()
                    )
                )
            }
        }else{
            doAsync{
                memoryViewModel.insertMemory(Memory(memoryText = edit_text.text.toString().trim(), title = edit_title.text.toString(), date = memoryDate.text.toString() ))
            }
        }
        activity?.onBackPressed()



    }

    companion object {
        /**
         * The fragment argument representing the item ID that this fragment
         * represents.
         */
        const val ARG_MEMORY = "item_id"

        fun newInstance(memory: Memory): AddEditMemoryFragment {
            val args = Bundle()
            args.putSerializable(ARG_MEMORY, memory)
            val fragment = AddEditMemoryFragment()
            fragment.arguments = args

            return fragment
        }
    }


}
