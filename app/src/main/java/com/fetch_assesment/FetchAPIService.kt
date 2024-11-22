package com.fetch_assesment

// API service interface to define endpoints
import retrofit2.http.GET
interface FetchAPIService {
    @GET("/hiring.json")
    suspend fun getItems(): List<Item>

}