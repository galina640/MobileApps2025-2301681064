package com.example.smartnotespro.ui.addedit

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.smartnotespro.data.local.Note
import com.example.smartnotespro.databinding.ActivityAddNoteBinding
import com.example.smartnotespro.viewmodel.NoteViewModel
import android.widget.ArrayAdapter

class AddNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddNoteBinding

    private val viewModel: NoteViewModel
            by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding =
            ActivityAddNoteBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val categories = listOf(
            "Personal",
            "Work",
            "University",
            "Ideas"
        )

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            categories
        )

        binding.spinnerCategory.adapter = adapter

        binding.buttonSave.setOnClickListener {

            val title =
                binding.editTitle.text.toString()

            val content =
                binding.editContent.text.toString()

            if (title.isNotEmpty()
                && content.isNotEmpty()
            ) {

                val note = Note(
                    title = title,
                    content = content,
                    category =
                        binding.spinnerCategory.selectedItem.toString(),
                    date = System.currentTimeMillis()
                )

                viewModel.insert(note)

                Toast.makeText(
                    this,
                    "Note Saved",
                    Toast.LENGTH_SHORT
                ).show()

                finish()

            } else {

                Toast.makeText(
                    this,
                    "Fill all fields",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}