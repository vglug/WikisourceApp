package org.vglug.wikisourceapp.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.readystatesoftware.chuck.Chuck
import org.vglug.wikisourceapp.R
import org.vglug.wikisourceapp.data.model.BookListItem
import org.vglug.wikisourceapp.data.model.language.LanguageData
import org.vglug.wikisourceapp.databinding.ActivityMainBinding
import org.vglug.wikisourceapp.extensions.makeGone
import org.vglug.wikisourceapp.extensions.makeVisible
import org.vglug.wikisourceapp.utils.AppConstants


class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    lateinit var viewModel: MainViewModel

    lateinit var bookListAdapter: BookListAdapter
    private val bookList = ArrayList<BookListItem>()

    private var selectedLanguageData: LanguageData? = null

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.fetchLanguages(applicationContext)
        binding.apply {

            recyclerViewBooksList.layoutManager = LinearLayoutManager(this@MainActivity)
            bookListAdapter = BookListAdapter(bookList)
            recyclerViewBooksList.adapter = bookListAdapter

            viewModel.languageData.observe(this@MainActivity, {
                setupSpinner()
            })

            viewModel.searchBookData.observe(this@MainActivity, {
                bookList.clear()
                if (it.isNotEmpty()) {
                    bookList.addAll(it)
                    bookListAdapter.notifyDataSetChanged()
                }
            })

            viewModel.apiLoading.observe(this@MainActivity, {
                progress.makeGone()
                recyclerViewBooksList.makeGone()
                txtError.makeGone()
                when {
                    it -> {
                        progress.makeVisible()
                    }
                    bookList.isEmpty() -> {
                        txtError.makeVisible()
                    }
                    else -> {
                        recyclerViewBooksList.makeVisible()
                    }
                }
            })

            btnSearch.setOnClickListener {
                Log.e("TAG", "Lang Data: ${selectedLanguageData}")
                if (editBookTerm.text?.isNotEmpty() == true) {
                    val code = selectedLanguageData?.code ?: AppConstants.DEFAULT_LANGUAGE_CODE
                    viewModel.fetchBookList(applicationContext, code, editBookTerm.text.toString())
                } else {
                    Snackbar.make(it, "Please enter valid search term", Snackbar.LENGTH_LONG).show()
                }
            }


        }
    }

    private fun setupSpinner() {
        binding.run {
            val languageDataForSpinner = viewModel.languageData.value?.map { it.lang + "(${it.code})"} ?: ArrayList()
            val adapter = ArrayAdapter(applicationContext, R.layout.item_language, languageDataForSpinner.toTypedArray())
            spinnerLanguage.setAdapter(adapter)
            spinnerLanguage.setText(languageDataForSpinner.firstOrNull().toString(), false)
            selectedLanguageData = viewModel.languageData.value?.firstOrNull()
            spinnerLanguage.setOnItemClickListener { _, _, position, _ ->
                selectedLanguageData = viewModel.languageData.value?.elementAtOrNull(position)
                if (editBookTerm.text?.isNotEmpty() == true)
                    btnSearch.performClick()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                startActivity(Chuck.getLaunchIntent(applicationContext))
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}