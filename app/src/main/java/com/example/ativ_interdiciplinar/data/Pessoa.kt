package com.example.ativ_interdiciplinar.data

import com.google.gson.annotations.SerializedName

data class Pessoa(
    @SerializedName("id") val id: Int,
    @SerializedName("nome") val nome: String?,
    @SerializedName("email") val email: String?,
    @SerializedName("telefone") val telefone: String?,
    @SerializedName("cpf") val cpf: String?,
    @SerializedName("idade") val idade: Int?,
    @SerializedName("logradouro") val logradouro: String?,
    @SerializedName("numero") val numero: Int?,
    @SerializedName("complemento") val complemento: String?
)