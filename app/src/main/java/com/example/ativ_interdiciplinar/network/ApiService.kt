package com.example.ativ_interdiciplinar.network

import com.example.ativ_interdiciplinar.data.Pessoa
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @GET("Pessoas")
    suspend fun getPessoas(): Response<List<Pessoa>>

    @GET("Pessoas/{id}")
    suspend fun getPessoa(@Path("id") id: Int): Response<Pessoa>

    @POST("Pessoas")
    suspend fun createPessoa(@Body body: Map<String, @JvmSuppressWildcards Any>): Response<Pessoa>

    @PUT("Pessoas/{id}")
    suspend fun updatePessoa(@Path("id") id: Int, @Body body: Map<String, @JvmSuppressWildcards Any>): Response<Pessoa>

    @DELETE("Pessoas/{id}")
    suspend fun deletePessoa(@Path("id") id: Int): Response<Unit>
}