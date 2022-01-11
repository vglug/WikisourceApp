package org.vglug.wikisourceapp.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import org.vglug.wikisourceapp.R
import org.vglug.wikisourceapp.databinding.ActivityMainBinding
import org.vglug.wikisourceapp.ui.reader.ReaderActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)


        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        binding.apply {
            btnTamil.setOnClickListener {
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
            }

            btnOpenUrl.setOnClickListener {
                if (editUrl.text?.isNotEmpty() == true) {
                    openUrl(editUrl.text.toString())
                } else {
                    Snackbar.make(it, "Please enter valid url", Snackbar.LENGTH_LONG).show()
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