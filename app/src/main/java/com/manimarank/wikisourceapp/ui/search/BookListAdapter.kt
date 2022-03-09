package com.manimarank.wikisourceapp.ui.search

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.manimarank.wikisourceapp.data.model.search_response.BookListItem
import com.manimarank.wikisourceapp.databinding.ItemBookListBinding
import com.manimarank.wikisourceapp.ui.reader.ReaderActivity

class BookListAdapter(private val mList: List<BookListItem>) : RecyclerView.Adapter<BookListAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemBookListBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemBookListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            with(mList[position]){
                binding.txtName.text = this.name
                binding.root.setOnClickListener {
                    it.context.startActivity(Intent(it.context, ReaderActivity::class.java).apply {
                        putExtra("url", url)
                    })
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }
}