package com.example.smartnotespro.ui.addedit

import android.content.Intent
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

        val categories = listOf(
            "Personal",
            "Work",
            "University",
            "Ideas"
        )

        val spinnerAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            categories
        )

        binding.spinnerCategory.adapter =
            spinnerAdapter

        val title =
            intent.getStringExtra("title")

        val content =
            intent.getStringExtra("content")

        val category =
            intent.getStringExtra("category")

        noteId =
            intent.getIntExtra("id", 0)

        binding.editTitle.setText(title)

        binding.editContent.setText(content)

        val categoryPosition =
            categories.indexOf(category)

        if (categoryPosition >= 0) {

            binding.spinnerCategory
                .setSelection(categoryPosition)
        }

        binding.buttonSave.text =
            "Update Note"

        binding.buttonSave.setOnClickListener {

            val updatedTitle =
                binding.editTitle.text.toString()

            val updatedContent =
                binding.editContent.text.toString()

            val updatedCategory =
                binding.spinnerCategory
                    .selectedItem.toString()

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
        }

        // SHARE NOTE
        binding.buttonShare.setOnClickListener {

            val shareText =

                "Title: ${binding.editTitle.text}\n\n" +

                        "Content:\n${binding.editContent.text}\n\n" +

                        "Category: ${
                            binding.spinnerCategory.selectedItem
                        }"

            val shareIntent = Intent().apply {

                action = Intent.ACTION_SEND

                putExtra(
                    Intent.EXTRA_TEXT,
                    shareText
                )

                type = "text/plain"
            }

            startActivity(

                Intent.createChooser(
                    shareIntent,
                    "Share Note"
                )
            )
        }
    }
}