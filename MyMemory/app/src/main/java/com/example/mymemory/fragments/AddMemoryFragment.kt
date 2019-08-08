package com.example.mymemory.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders

import com.example.mymemory.R
import com.example.mymemory.model.Memory
import com.example.mymemory.ui.MemoryViewModel
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_add_memory.*
import kotlinx.android.synthetic.main.fragment_add_memory.view.*
import kotlinx.android.synthetic.main.memory_detail.*
import org.jetbrains.anko.doAsync
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*


class AddMemoryFragment : Fragment() {

    private lateinit var memoryViewModel: MemoryViewModel

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

        rootView.memoryDate.text = sdf.format(Date())
        setHasOptionsMenu(true)
        return rootView
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
        doAsync{
            memoryViewModel.insert(Memory(memoryText = edit_text.text.toString().trim(), title = edit_title.text.toString(), date = memoryDate.text.toString() ))
        }


    }


}
