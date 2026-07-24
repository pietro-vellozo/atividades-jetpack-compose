package br.com.treinamento.navegacaoeventos.notas.model

data class Nota(
    val id: Int,
    val nome: String,
    val notas: List<Double>
) {
    val media: Double
        get() = if (notas.isEmpty()) 0.0 else notas.average()
}
