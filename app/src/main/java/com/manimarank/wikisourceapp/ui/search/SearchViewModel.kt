package com.manimarank.wikisourceapp.ui.search

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manimarank.wikisourceapp.data.api.ApiClient
import com.manimarank.wikisourceapp.data.model.search_response.BookListItem
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {

    private val _searchBookData: MutableLiveData<List<BookListItem>> = MutableLiveData()
    val searchBookData: LiveData<List<BookListItem>> = _searchBookData

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
            } catch (e: Exception) {
                e.printStackTrace()
                _searchBookData.value = ArrayList()
            } finally {
                apiLoading.value = false
            }
        }
    }
}