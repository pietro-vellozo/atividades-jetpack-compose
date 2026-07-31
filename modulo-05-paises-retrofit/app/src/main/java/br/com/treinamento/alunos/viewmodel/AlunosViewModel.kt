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
import br.com.treinamento.alunos.data.remote.CountriesNowApi
import br.com.treinamento.alunos.data.remote.CountriesNowClient
import br.com.treinamento.alunos.data.remote.Pais
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

sealed interface EstadoPaises {
    object Carregando : EstadoPaises
    data class Sucesso(val paises: List<Pais>) : EstadoPaises
    data class Erro(val mensagem: String) : EstadoPaises
}

class AlunosViewModel(
    private val dao: AlunoDao,
    private val api: CountriesNowApi
) : ViewModel() {

    val alunos: StateFlow<List<Aluno>> = dao.observarTodos()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    private val _estadoPaises = MutableStateFlow<EstadoPaises>(EstadoPaises.Carregando)
    val estadoPaises: StateFlow<EstadoPaises> = _estadoPaises.asStateFlow()

    init {
        carregarPaises()
    }

    fun carregarPaises() {
        _estadoPaises.value = EstadoPaises.Carregando
        viewModelScope.launch {
            try {
                val resposta = api.listarPaises()
                if (resposta.error) {
                    _estadoPaises.value = EstadoPaises.Erro(resposta.msg)
                } else {
                    _estadoPaises.value = EstadoPaises.Sucesso(
                        resposta.data.sortedBy { pais -> pais.nome.lowercase() }
                    )
                }
            } catch (excecao: Exception) {
                _estadoPaises.value = EstadoPaises.Erro(
                    "Falha ao carregar países. Verifique sua conexão."
                )
            }
        }
    }

    fun addAluno(nome: String, notas: List<Double>, nacionalidade: Pais?) {
        viewModelScope.launch {
            dao.inserir(
                Aluno(
                    nome = nome,
                    notas = notas,
                    nacionalidade = nacionalidade?.nome,
                    bandeira = nacionalidade?.bandeira
                )
            )
        }
    }

    fun atualizarAluno(id: Int, nome: String, notas: List<Double>, nacionalidade: Pais?) {
        viewModelScope.launch {
            dao.atualizar(
                Aluno(
                    id = id,
                    nome = nome,
                    notas = notas,
                    nacionalidade = nacionalidade?.nome,
                    bandeira = nacionalidade?.bandeira
                )
            )
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
                AlunosViewModel(
                    dao = AlunoDatabase.get(context).alunoDao(),
                    api = CountriesNowClient.api
                )
            }
        }
    }
}
