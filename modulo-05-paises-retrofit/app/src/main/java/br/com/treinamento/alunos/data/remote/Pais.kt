package br.com.treinamento.alunos.data.remote

import com.google.gson.annotations.SerializedName

/**
 * Resposta padrao da API countriesnow.space:
 * { "error": false, "msg": "...", "data": [...] }
 */
data class RespostaPaises(
    val error: Boolean,
    val msg: String,
    val data: List<Pais>
)

data class Pais(
    @SerializedName("name")
    val nome: String,
    @SerializedName("unicodeFlag")
    val bandeira: String?,
    val iso2: String?,
    val iso3: String?
)
