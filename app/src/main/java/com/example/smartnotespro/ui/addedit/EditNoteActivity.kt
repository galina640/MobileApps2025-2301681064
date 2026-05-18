package com.example.smartnotespro.ui.addedit

import android.os.Bundle
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

        val title =
            intent.getStringExtra("title")

        val content =
            intent.getStringExtra("content")

        noteId =
            intent.getIntExtra("id", 0)

        binding.editTitle.setText(title)
        binding.editContent.setText(content)

        binding.buttonSave.text = "Update Note"

        binding.buttonSave.setOnClickListener {

            val updatedTitle =
                binding.editTitle.text.toString()

            val updatedContent =
                binding.editContent.text.toString()

            val updatedNote = Note(
                id = noteId,
                title = updatedTitle,
                content = updatedContent,
                category = "General",
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
    }
}