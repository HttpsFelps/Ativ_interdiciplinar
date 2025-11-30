package com.example.ativ_interdiciplinar.repository

import com.example.ativ_interdiciplinar.data.Pessoa
import com.example.ativ_interdiciplinar.network.RetrofitInstance
import retrofit2.Response

class PessoaRepository {

    private val api = RetrofitInstance.api

    suspend fun getPessoas(): Response<List<Pessoa>> = api.getPessoas()

    suspend fun getPessoa(id: Int): Response<Pessoa> = api.getPessoa(id)

    suspend fun getPessoaById(id: Int): Response<Pessoa> = api.getPessoa(id)

    suspend fun createPessoa(payload: Map<String, Any>): Response<Pessoa> = api.createPessoa(payload)

    suspend fun updatePessoa(id: Int, payload: Map<String, Any>): Response<Pessoa> = api.updatePessoa(id, payload)

    suspend fun deletePessoa(id: Int): Response<Unit> = api.deletePessoa(id)
}