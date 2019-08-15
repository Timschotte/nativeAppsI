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
import org.jetbrains.anko.onComplete
import java.text.SimpleDateFormat
import java.util.*

/**
 * A fragment that displays the details of a Memory
 */
class AddEditMemoryFragment : Fragment() {

    /**
     * The memory this fragment is representing
     */
    private lateinit var memory: Memory

    /**
     * The memoryViewModel to retrieve & persist our models
     */
    private lateinit var memoryViewModel: MemoryViewModel

    /**
     * The quoteViewModel used to retrieve the quote of the day from a public API
     */
    private lateinit var quoteViewModel: QuoteViewModel

    /**
     * This will create the fragment and initialize the memoryViewModel. We don't initialize the quoteviewModel yet,
     * because this would result in an API-call and we only want that unless we really need to
     */
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

        //A simple dateformatter
        val sdf = SimpleDateFormat("yyyy-MM-dd")

        //We make sure the optionsMenu is displayed on top of the screen
        setHasOptionsMenu(true)

        //If 'arguments' is initialized and contains the 'ARG_MEMORY'-key, then this fragment will be used to edit a memory
        arguments?.let {
            if (it.containsKey(ARG_MEMORY)) {
                // Load the memory specified by the fragment
                // arguments.
                memory = it.getSerializable(ARG_MEMORY) as Memory
                //set the correct title for the options bar
                if(activity is AppCompatActivity){
                    (activity as AppCompatActivity).supportActionBar?.setTitle("Edit Memory-item")
                }
                //fill in all the properties of the memory
                rootView.edit_text.setText(memory.memoryText)
                rootView.edit_title.setText(memory.title)
                rootView.memoryDate.setText(memory.date)
                //we retrieve the quote from the repository
                doAsync {
                    if(memoryViewModel.getQuoteForDate(memory.date) != null){
                        val quote = memoryViewModel.getQuoteForDate(memory.date)!!.quote
                        onComplete {
                            rootView.quoteText.setText(quote)
                        }
                    }else{
                        onComplete {
                            rootView.quoteText.setText("Quote not available")
                        }
                    }

                }

            }else{
                //if arguments is initialized but doesn't contain a memory, we setup the fragment to create a new Memory
                fillNewMemoryFields(rootView, sdf)

            }
        }
        //If 'arguments' isn't initialized, this fragment will be used to create a new memory
        if(arguments == null){
            fillNewMemoryFields(rootView, sdf)
        }


        return rootView
    }

    /**
     * This will initialize the view for a new memory
     */
    private fun fillNewMemoryFields(rootView: View, sdf: SimpleDateFormat) {
        //We give the optionsbar the correct title
        if (activity is AppCompatActivity) {
            (activity as AppCompatActivity).supportActionBar?.setTitle("Add new Memory-item")
        }
        //We enter today's date
        val todaysDate: String = sdf.format(Date())
        rootView.memoryDate.setText(todaysDate)
        //We retrieve the quote of the day, we first check if we already have today's qotd in our repository, else,
        // we initialize the quoteViewModel and fetch if from the internet
        doAsync {
            val qotd: Quote? = memoryViewModel.getQuoteForDate(todaysDate)
            onComplete {
                if(qotd != null){
                    rootView.quoteText.setText(qotd.quote)
                }else{
                    rootView.quoteText.setText("Trying to retrieve the quote of the day")
                    quoteViewModel = ViewModelProviders.of(this.weakRef.get()!!).get(QuoteViewModel::class.java)
                    val quoteObjectObserver = Observer<Quote> { newQuote ->
                        processQuote(newQuote, rootView)
                    }

                    quoteViewModel.getQuoteObject().observe(this.weakRef.get()!!, quoteObjectObserver)
                }
            }

        }


    }
    /**
     * When we retrieve the quote, we update the textfield and add the quote to the repository
     */
    private fun processQuote(newQuote: Quote, rootView: View) {
        doAsync {
            memoryViewModel.insertQuote(newQuote)
        }
        rootView.quoteText.setText(newQuote.quote)
    }

    /**
     * We load the optionsMenu, specific to this fragment
     */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_memory_menu, menu);
        super.onCreateOptionsMenu(menu, inflater)
    }

    /**
     * We try to save the Memory
     */
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.save_memory -> {
                saveMemory()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    /**
     * We try to save the memory, first we do a check if all fields are filled in, then we push it to the repository
     */
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

    /**
     * Used to pass a Memory to this fragment
     */
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
