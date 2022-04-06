package com.manimarank.wikisourceapp.data.model.language


import com.google.gson.annotations.SerializedName

data class LanguageResponse(
    @SerializedName("language_wise_data")
    val languageData: List<LanguageData>
)