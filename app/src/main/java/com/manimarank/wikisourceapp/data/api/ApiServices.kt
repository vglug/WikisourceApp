package com.manimarank.wikisourceapp.data.api

import com.manimarank.wikisourceapp.data.model.language.LanguageResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiServices {

    // Get Wikipedia language list JSON - Static Link
    @GET("https://raw.githubusercontent.com/manimaran96/Spell4Wiki/master/files/base_data/spell4wiki_base.json")
    suspend fun fetchWikiLanguages(): Response<LanguageResponse?>
}