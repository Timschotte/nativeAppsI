package com.example.mymemory.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import com.example.mymemory.R
import com.example.mymemory.databinding.FragmentQuoteBinding
import com.example.mymemory.model.Quote
import com.example.mymemory.ui.QuoteViewModel
import kotlinx.android.synthetic.main.fragment_add_memory.view.*
import kotlinx.android.synthetic.main.fragment_quote.*
import kotlinx.android.synthetic.main.fragment_quote.view.*

/**
 * This displays the quote of the day in the center of the screen
 */
class QuoteFragment : Fragment() {

    /**
     * The viewmodel used to retrieve the quote of the day from the internet
     */
    private lateinit var viewModel: QuoteViewModel

    /**
     * We create the view
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootView = inflater.inflate(R.layout.fragment_quote, container, false)

        viewModel = ViewModelProviders.of(activity!!).get(QuoteViewModel::class.java)

        rootView.quoteTxt.setText("Trying to retrieve the Quote of the Day ...")

        //Observes the livedate from the viewmodel and displays it when it changes
        val quoteObjectObserver = Observer<Quote> { newQuote ->
            rootView.quoteTxt.setText(newQuote.quote)
        }

        //attach the observer to the livedata
        viewModel.getQuoteObject().observe(this, quoteObjectObserver)
        return rootView
    }
}
