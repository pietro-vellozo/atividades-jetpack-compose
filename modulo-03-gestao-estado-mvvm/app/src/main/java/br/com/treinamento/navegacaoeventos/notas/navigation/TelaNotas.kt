package br.com.treinamento.navegacaoeventos.notas.navigation

sealed class TelaNotas(val rota: String) {
    object Lista : TelaNotas("notas/lista")
    object Nova : TelaNotas("notas/nova")

    object Detalhe : TelaNotas("notas/detalhe/{notaId}") {
        fun criarRota(notaId: Int): String {
            return "notas/detalhe/$notaId"
        }
    }
}
