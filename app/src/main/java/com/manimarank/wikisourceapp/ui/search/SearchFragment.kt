package com.manimarank.wikisourceapp.ui.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.manimarank.wikisourceapp.data.model.search_response.BookListItem
import com.manimarank.wikisourceapp.databinding.FragmentSearchBinding
import com.manimarank.wikisourceapp.extensions.makeGone
import com.manimarank.wikisourceapp.extensions.makeInVisible
import com.manimarank.wikisourceapp.extensions.makeVisible
import com.manimarank.wikisourceapp.utils.AppConstants

class SearchFragment : Fragment() {

    lateinit var bookListAdapter: BookListAdapter
    private val bookList = ArrayList<BookListItem>()

    private lateinit var viewModel: SearchViewModel
    private var _binding: FragmentSearchBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[SearchViewModel::class.java]
        _binding = FragmentSearchBinding.inflate(inflater, container, false)

        _binding?.run {
            recyclerViewBooksList.layoutManager = LinearLayoutManager(this@SearchFragment.requireContext())
            bookListAdapter = BookListAdapter(bookList)
            recyclerViewBooksList.adapter = bookListAdapter

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(searchTerm: String?): Boolean {
                    Log.e("TAG", "Lang Data: ${AppConstants.DEFAULT_LANGUAGE_CODE}")
                    if (searchTerm?.length ?: 0 > 0) {
                        val code = AppConstants.DEFAULT_LANGUAGE_CODE
                        viewModel.fetchBookList(requireContext(), code, searchTerm!!)
                    }
                    return true
                }

            })

        }

        initLiveDataObservers()

        return binding.root
    }

    private fun initLiveDataObservers() {
        viewModel.searchBookData.observe(this, {
            bookList.clear()
            if (it.isNotEmpty()) {
                bookList.addAll(it)
                bookListAdapter.notifyDataSetChanged()
            }
        })

        viewModel.apiLoading.observe(this, {
            binding.progress.makeInVisible()
            binding.recyclerViewBooksList.makeGone()
            binding.txtError.makeGone()
            when {
                it -> {
                    binding.progress.makeVisible()
                }
                bookList.isEmpty() -> {
                    binding.txtError.makeVisible()
                }
                else -> {
                    binding.recyclerViewBooksList.makeVisible()
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}