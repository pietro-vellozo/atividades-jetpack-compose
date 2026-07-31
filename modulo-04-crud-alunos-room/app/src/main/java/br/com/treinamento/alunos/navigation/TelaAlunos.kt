package br.com.treinamento.alunos.navigation

sealed class TelaAlunos(val rota: String) {
    object Lista : TelaAlunos("alunos/lista")
    object Novo : TelaAlunos("alunos/novo")

    object Detalhe : TelaAlunos("alunos/detalhe/{alunoId}") {
        fun criarRota(alunoId: Int): String {
            return "alunos/detalhe/$alunoId"
        }
    }
}
