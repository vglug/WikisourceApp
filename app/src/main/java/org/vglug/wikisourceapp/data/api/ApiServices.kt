package org.vglug.wikisourceapp.data.api

import org.vglug.wikisourceapp.data.model.BookSearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {

    @GET("w/api.php?action=opensearch&format=json&formatversion=2&namespace=0|100|102|106|114")
    suspend fun searchBooks(
        @Query("search") term: String,
        @Query("limit") limit: Int = 50
    ):Response<BookSearchResponse>
}