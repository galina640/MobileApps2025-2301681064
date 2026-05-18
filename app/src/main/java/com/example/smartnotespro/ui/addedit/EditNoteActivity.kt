package com.example.smartnotespro.ui.addedit

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.smartnotespro.data.local.Note
import com.example.smartnotespro.databinding.ActivityAddNoteBinding
import com.example.smartnotespro.viewmodel.NoteViewModel

class EditNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddNoteBinding

    private val viewModel: NoteViewModel
            by viewModels()

    private var noteId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding =
            ActivityAddNoteBinding.inflate(layoutInflater)

        setContentView(binding.root)

        // Categories
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

        // Get data
        val title =
            intent.getStringExtra("title")

        val content =
            intent.getStringExtra("content")

        val category =
            intent.getStringExtra("category")

        noteId =
            intent.getIntExtra("id", 0)

        // Set old values
        binding.editTitle.setText(title)

        binding.editContent.setText(content)

        // Show current category
        val position =
            categories.indexOf(category)

        if (position >= 0) {
            binding.spinnerCategory
                .setSelection(position)
        }

        // Change button text
        binding.buttonSave.text =
            "Update Note"

        // Update note
        binding.buttonSave.setOnClickListener {

            val updatedTitle =
                binding.editTitle.text.toString()

            val updatedContent =
                binding.editContent.text.toString()

            val updatedCategory =
                binding.spinnerCategory
                    .selectedItem.toString()

            if (updatedTitle.isNotEmpty()
                && updatedContent.isNotEmpty()
            ) {

                val updatedNote = Note(
                    id = noteId,
                    title = updatedTitle,
                    content = updatedContent,
                    category = updatedCategory,
                    date = System.currentTimeMillis()
                )

                viewModel.update(updatedNote)

                Toast.makeText(
                    this,
                    "Note Updated",
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