package com.manimarank.wikisourceapp.data.api

import com.manimarank.wikisourceapp.data.model.language.LanguageResponse
import com.manimarank.wikisourceapp.data.model.search_response.BookSearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {

    // Get Wikipedia language list JSON - Static Link
    @GET("https://raw.githubusercontent.com/manimaran96/Spell4Wiki/master/files/base_data/spell4wiki_base.json")
    suspend fun fetchWikiLanguages(): Response<LanguageResponse?>

    @GET("w/api.php?action=opensearch&format=json&formatversion=2&namespace=0|100|102|106|114")
    suspend fun searchBooks(
        @Query("search") term: String,
        @Query("limit") limit: Int = 50
    ):Response<BookSearchResponse>
}