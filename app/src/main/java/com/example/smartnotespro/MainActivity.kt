package com.example.smartnotespro

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smartnotespro.databinding.ActivityMainBinding
import com.example.smartnotespro.ui.home.NoteAdapter
import com.example.smartnotespro.viewmodel.NoteViewModel
import android.content.Intent
import com.example.smartnotespro.ui.addedit.AddNoteActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.appcompat.widget.SearchView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: NoteViewModel
            by viewModels()

    private lateinit var adapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding =
            ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        adapter = NoteAdapter { note ->

            val intent = Intent(
                this,
                com.example.smartnotespro
                    .ui.addedit.EditNoteActivity::class.java
            )

            intent.putExtra("id", note.id)
            intent.putExtra("title", note.title)
            intent.putExtra("content", note.content)

            startActivity(intent)
        }

        binding.recyclerView.layoutManager =
            LinearLayoutManager(this)

        binding.recyclerView.adapter = adapter

        val deleteIcon: Drawable? =
            ContextCompat.getDrawable(
                this,
                android.R.drawable.ic_menu_delete
            )

        val swipeBackground =
            ColorDrawable(Color.parseColor("#D32F2F"))

        ItemTouchHelper(

            object : ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
            ) {

                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(
                    viewHolder: RecyclerView.ViewHolder,
                    direction: Int
                ) {

                    val note =
                        viewModel.allNotes.value?.get(
                            viewHolder.adapterPosition
                        )

                    if (note != null) {
                        viewModel.delete(note)
                    }
                }
                override fun onChildDraw(
                    c: Canvas,
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    dX: Float,
                    dY: Float,
                    actionState: Int,
                    isCurrentlyActive: Boolean
                ) {

                    val itemView = viewHolder.itemView

                    swipeBackground.setBounds(
                        itemView.left,
                        itemView.top,
                        itemView.right,
                        itemView.bottom
                    )

                    swipeBackground.draw(c)

                    deleteIcon?.let {

                        val iconMargin =
                            (itemView.height - it.intrinsicHeight) / 2

                        val iconTop =
                            itemView.top +
                                    (itemView.height - it.intrinsicHeight) / 2

                        val iconBottom =
                            iconTop + it.intrinsicHeight

                        val iconLeft =
                            itemView.right -
                                    iconMargin -
                                    it.intrinsicWidth

                        val iconRight =
                            itemView.right - iconMargin

                        it.setBounds(
                            iconLeft,
                            iconTop,
                            iconRight,
                            iconBottom
                        )

                        it.draw(c)
                    }

                    super.onChildDraw(
                        c,
                        recyclerView,
                        viewHolder,
                        dX,
                        dY,
                        actionState,
                        isCurrentlyActive
                    )
                }
            }

        ).attachToRecyclerView(binding.recyclerView)

        viewModel.allNotes.observe(this) { notes ->
            adapter.setData(notes)
        }
        binding.fabAdd.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    AddNoteActivity::class.java
                )
            )
        }
        binding.searchView.setOnQueryTextListener(

            object : SearchView.OnQueryTextListener {

                override fun onQueryTextSubmit(
                    query: String?
                ): Boolean {
                    return false
                }

                override fun onQueryTextChange(
                    newText: String?
                ): Boolean {

                    val searchQuery =
                        "%${newText}%"

                    viewModel.searchNotes(searchQuery)
                        .observe(this@MainActivity) {

                            adapter.setData(it)
                        }

                    return true
                }
            }
        )
    }
}