package com.example.mymemory.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.mymemory.R
import com.example.mymemory.fragments.AddEditMemoryFragment
import com.example.mymemory.model.Memory
/**
 * An activity representing a screen to edit or create a Memory object
 */
class AddEditMemoryActivity : AppCompatActivity() {
    /**
     * Creates the Activity
     * - if a memory is provided on creation, the memory will be pass on to the AddEditMemory fragment
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_memory)

        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.

            val detailFragment: AddEditMemoryFragment

            //If we get a Memory as an argument, we will show the memory on the screen so we can edit it, else, we create a new memory-screen
            if(intent.getSerializableExtra(AddEditMemoryFragment.ARG_MEMORY) != null){
                detailFragment = AddEditMemoryFragment.newInstance(
                    intent.getSerializableExtra(AddEditMemoryFragment.ARG_MEMORY) as Memory
                )
            }else{
                detailFragment = AddEditMemoryFragment()
            }

            supportFragmentManager.beginTransaction()
                .replace(R.id.add_edit_memory_fragment_container, detailFragment, "AddEditMemoryFragment")
                .commit()
        }

    }
}
