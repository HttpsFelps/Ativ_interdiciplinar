package com.example.ativ_interdiciplinar.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ativ_interdiciplinar.data.Pessoa
import com.example.ativ_interdiciplinar.repository.PessoaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PessoaViewModel : ViewModel() {

    private val repository = PessoaRepository()

    private val _pessoas = MutableStateFlow<List<Pessoa>>(emptyList())
    val pessoas: StateFlow<List<Pessoa>> = _pessoas

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun loadPessoas() {
        viewModelScope.launch {
            try {
                val response = repository.getPessoas()
                if (response.isSuccessful) {
                    _pessoas.value = response.body() ?: emptyList()
                    _error.value = null
                } else {
                    _error.value = "Erro: ${response.code()} - ${response.message()}"
                }
            } catch (e: Exception) {
                _error.value = "Exceção: ${e.localizedMessage}"
            }
        }
    }

    // Exemplos de uso para criar, atualizar, deletar:
    fun createPessoa(payload: Map<String, Any>, onFinished: (() -> Unit)? = null) {
        viewModelScope.launch {
            try {
                val resp = repository.createPessoa(payload)
                if (resp.isSuccessful) {
                    loadPessoas()
                } else {
                    _error.value = "Erro ao criar: ${resp.code()} ${resp.message()}"
                }
            } catch (e: Exception) {
                _error.value = e.localizedMessage
            } finally {
                onFinished?.invoke()
            }
        }
    }

    fun updatePessoa(id: Int, payload: Map<String, Any>, onFinished: (() -> Unit)? = null) {
        viewModelScope.launch {
            try {
                val resp = repository.updatePessoa(id, payload)
                if (resp.isSuccessful) {
                    loadPessoas()
                } else {
                    _error.value = "Erro ao atualizar: ${resp.code()} ${resp.message()}"
                }
            } catch (e: Exception) {
                _error.value = e.localizedMessage
            } finally {
                onFinished?.invoke()
            }
        }
    }

    fun deletePessoa(id: Int, onFinished: (() -> Unit)? = null) {
        viewModelScope.launch {
            try {
                val resp = repository.deletePessoa(id)
                if (resp.isSuccessful) {
                    loadPessoas()
                } else {
                    _error.value = "Erro ao deletar: ${resp.code()} ${resp.message()}"
                }
            } catch (e: Exception) {
                _error.value = e.localizedMessage
            } finally {
                onFinished?.invoke()
            }
        }
    }

    fun getPessoaById(id: Int, onResult: (Pessoa?) -> Unit) {
        viewModelScope.launch {
            try {
                val resp = repository.getPessoaById(id)
                if (resp.isSuccessful) {
                    onResult(resp.body())
                    _error.value = null
                } else {
                    _error.value = "Erro ao buscar: ${resp.code()} ${resp.message()}"
                    onResult(null)
                }
            } catch (e: Exception) {
                _error.value = "Exceção: ${e.localizedMessage}"
                onResult(null)
            }
        }
    }
}