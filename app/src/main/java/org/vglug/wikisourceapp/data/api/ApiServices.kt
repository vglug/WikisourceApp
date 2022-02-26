package org.vglug.wikisourceapp.data.api

import org.vglug.wikisourceapp.data.model.BookSearchResponse
import org.vglug.wikisourceapp.data.model.language.LanguageResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {

    @GET("w/api.php?action=opensearch&format=json&formatversion=2&namespace=0|100|102|106|114")
    suspend fun searchBooks(
        @Query("search") term: String,
        @Query("limit") limit: Int = 50
    ):Response<BookSearchResponse>

    // Get Wikipedia language list JSON - Static Link
    @GET("https://raw.githubusercontent.com/manimaran96/Spell4Wiki/master/files/base_data/spell4wiki_base.json")
    suspend fun fetchWikiLanguages(): Response<LanguageResponse?>
}