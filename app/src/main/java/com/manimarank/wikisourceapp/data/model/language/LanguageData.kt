package com.manimarank.wikisourceapp.data.model.language

import com.google.gson.annotations.SerializedName

data class LanguageData(
    val code: String,
    val dir: String,
    val lang: String,
    @SerializedName("local_lang")
    val localLang: String
)