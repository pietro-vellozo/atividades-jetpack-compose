package br.com.treinamento.navegacaoeventos.model

data class Evento(
    val id: Int,
    val nome: String,
    val categoria: String,
    val data: String,
    val horario: String,
    val local: String,
    val vagas: Int,
    val descricao: String
)