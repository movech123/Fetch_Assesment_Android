package com.fetch_assesment

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

// Retrofit client to handle network requests
sealed class APIResult<out T> {
    data class Success<out T>(val data: T) : APIResult<T>()
    data class Error(val message: String) : APIResult<Nothing>()
}
object RetroFitClient {
    private const val BASE_URL = "https://fetch-hiring.s3.amazonaws.com/"
    private val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    // Creating a Network client to handle requests
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(logging) // Made for logging errors to Logcat for debugging
        .build()

    // Using Moshi to parse JSON to Kotlin to data objects
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
    // Retrofit instance to make network requests using the Moshi converter
    val fetchService: FetchAPIService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)
            .build()
            .create(FetchAPIService::class.java)
    }
}