package com.example.smartnotespro.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.smartnotespro.data.local.Note
import com.example.smartnotespro.databinding.ItemNoteBinding

class NoteAdapter(
    private val onItemClick: (Note) -> Unit
) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    private var notes = emptyList<Note>()

    class NoteViewHolder(
        val binding: ItemNoteBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NoteViewHolder {

        val binding = ItemNoteBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: NoteViewHolder,
        position: Int
    ) {

        val current = notes[position]

        holder.binding.textTitle.text =
            current.title

        holder.binding.textContent.text =
            current.content

        holder.binding.textCategory.text =
            current.category

        holder.itemView.setOnClickListener {
            onItemClick(current)
        }
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    fun setData(noteList: List<Note>) {
        notes = noteList
        notifyDataSetChanged()
    }
}