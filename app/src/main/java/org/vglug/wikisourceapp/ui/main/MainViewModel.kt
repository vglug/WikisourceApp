package org.vglug.wikisourceapp.ui.main

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.vglug.wikisourceapp.data.api.ApiClient
import org.vglug.wikisourceapp.data.model.BookListItem
import org.vglug.wikisourceapp.data.model.language.LanguageData

class MainViewModel: ViewModel() {

    private val _searchBookData: MutableLiveData<List<BookListItem>> = MutableLiveData()
    val searchBookData: LiveData<List<BookListItem>> = _searchBookData

    private val _languageData: MutableLiveData<List<LanguageData>> = MutableLiveData()
    val languageData: LiveData<List<LanguageData>> = _languageData

    val apiLoading: MutableLiveData<Boolean> = MutableLiveData()
    fun fetchBookList(context: Context, code: String, term: String) {
        viewModelScope.launch {
            try {
                apiLoading.value = true
                val apiData = ApiClient.get(context, code).searchBooks(term).body()
                val nameList = apiData?.filterIsInstance<List<String>>()?.elementAt(0)
                val urlList = apiData?.filterIsInstance<List<String>>()?.elementAt(2)
                val resData = ArrayList<BookListItem>()
                if (nameList != null && urlList != null) {
                    (nameList.zip(urlList)).forEach {
                         resData.add(BookListItem(it.first, null, it.second))
                    }
                }
                _searchBookData.value = resData
                apiLoading.value = false
            } catch (e: Exception) {
                e.printStackTrace()
                _searchBookData.value = ArrayList()
                apiLoading.value = false
            }
        }
    }

    fun fetchLanguages(context: Context) {
        viewModelScope.launch {
            var list: List<LanguageData>? = null
            try {
                apiLoading.value = true
                val languageResponse = ApiClient.get(context).fetchWikiLanguages()
                list = languageResponse.body()?.languageData
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _languageData.value = list ?: ArrayList<LanguageData>().apply {
                    add(LanguageData("en", "ltr", "English", "English"))
                }
                apiLoading.value = false
            }
        }
    }

}