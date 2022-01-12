package org.vglug.wikisourceapp.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import org.vglug.wikisourceapp.R
import org.vglug.wikisourceapp.databinding.ActivityMainBinding
import org.vglug.wikisourceapp.ui.reader.ReaderActivity

import android.widget.AutoCompleteTextView

import android.widget.ArrayAdapter
import org.vglug.wikisourceapp.data.LangUtils


class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)



        binding.apply {
            val adapter = ArrayAdapter(applicationContext, R.layout.item_language, LangUtils.getWikiLangList().map { it.name }.toTypedArray())
            spinnerLanguage.setAdapter(adapter)
            spinnerLanguage.setText(LangUtils.getWikiLangList().map { it.name }.first().toString(), false)
            var selectedLangPos = 0
            spinnerLanguage.setOnItemClickListener { _, _, position, _ -> selectedLangPos = position }

            /*btnTamil.setOnClickListener {
                val url = "https://ta.m.wikisource.org/wiki/பழங்காலத்_தமிழர்_வாணிகம்"
                openUrl(url)
            }

            btnBengali.setOnClickListener {
                val url = "https://bn.wikisource.org/wiki/ঘরে-বাইরে"
                openUrl(url)
            }

            btnEnglish.setOnClickListener {
                val url = "https://en.wikisource.org/wiki/The_Practice_of_Diplomacy"
                openUrl(url)
            }*/

            btnSearch.setOnClickListener {
                Log.e("TAG", "Lang Data: ${LangUtils.getWikiLangList().elementAt(selectedLangPos)}")
                if (editBookTerm.text?.isNotEmpty() == true) {
                    openUrl(editBookTerm.text.toString())
                } else {
                    Snackbar.make(it, "Please enter valid search term", Snackbar.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun openUrl(url: String) {
        startActivity(Intent(applicationContext, ReaderActivity::class.java).apply {
            putExtra("url", url)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}