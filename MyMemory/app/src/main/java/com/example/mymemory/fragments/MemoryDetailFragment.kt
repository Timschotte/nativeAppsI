package com.example.mymemory.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mymemory.R
import com.example.mymemory.model.Memory
import kotlinx.android.synthetic.main.memory_detail.view.*

/**
 * A fragment representing a single Memory detail screen.
 * This fragment is either contained in a [RagecomicListActivity]
 * in two-pane mode (on tablets) or a [RagecomicDetailActivity]
 * on handsets.
 */
class MemoryDetailFragment : Fragment() {

    /**
     * The memory this fragment is representing
     */
    private lateinit var memory: Memory

    /**
     * Creates the Fragment
     * - initialise dependencies
     * - restore state
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //We only allow constructing the fragment through newInstance
        // so we can force non-null arguments
        arguments!!.let {
            if (it.containsKey(ARG_MEMORY)) {
                // Load the comic specified by the fragment
                // arguments.
                memory = it.getSerializable(ARG_MEMORY) as Memory
            }
        }
    }

    /**
     * Creating the view for this fragment.
     * - update UI
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.memory_detail, container, false)

        // Show the comic content
        // The let construct calls the specified function block with this value as its argument
        // and returns its result.
        memory.let {
            rootView.date.text = it.quote.date
            rootView.title.text = it.title
        }
        return rootView
    }

    companion object {
        /**
         * The fragment argument representing the item ID that this fragment
         * represents.
         */
        const val ARG_MEMORY = "item_id"

        fun newInstance(memory: Memory): MemoryDetailFragment {
            val args = Bundle()
            args.putSerializable(ARG_MEMORY, memory)
            val fragment = MemoryDetailFragment()
            fragment.arguments = args

            return fragment
        }
    }
}