package br.com.treinamento.alunos.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import br.com.treinamento.alunos.data.Aluno
import br.com.treinamento.alunos.data.AlunoDao
import br.com.treinamento.alunos.data.AlunoDatabase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AlunosViewModel(private val dao: AlunoDao) : ViewModel() {

    val alunos: StateFlow<List<Aluno>> = dao.observarTodos()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    fun addAluno(nome: String, notas: List<Double>) {
        viewModelScope.launch {
            dao.inserir(Aluno(nome = nome, notas = notas))
        }
    }

    fun atualizarAluno(id: Int, nome: String, notas: List<Double>) {
        viewModelScope.launch {
            dao.atualizar(Aluno(id = id, nome = nome, notas = notas))
        }
    }

    fun removerAluno(aluno: Aluno) {
        viewModelScope.launch {
            dao.remover(aluno)
        }
    }

    fun calcularSituacao(media: Double): String {
        return when {
            media >= 7.0 -> "Aprovado"
            media >= 5.0 -> "Em recuperação"
            else -> "Reprovado"
        }
    }

    companion object {
        fun factory(context: Context): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                AlunosViewModel(AlunoDatabase.get(context).alunoDao())
            }
        }
    }
}
