package com.manimarank.wikisourceapp.ui.main

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manimarank.wikisourceapp.data.api.ApiClient
import com.manimarank.wikisourceapp.data.model.language.LanguageData
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {
    private val _languageData: MutableLiveData<List<LanguageData>> = MutableLiveData()
    val languageData: LiveData<List<LanguageData>> = _languageData

    val apiLoading: MutableLiveData<Boolean> = MutableLiveData()

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