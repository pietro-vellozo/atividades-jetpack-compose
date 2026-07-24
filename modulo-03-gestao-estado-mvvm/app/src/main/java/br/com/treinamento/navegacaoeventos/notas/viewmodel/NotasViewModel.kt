package br.com.treinamento.navegacaoeventos.notas.viewmodel

import androidx.lifecycle.ViewModel
import br.com.treinamento.navegacaoeventos.notas.model.Nota
import br.com.treinamento.navegacaoeventos.notas.model.exemploNotas
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class NotasViewModel : ViewModel() {
    private val _notas = MutableStateFlow<List<Nota>>(exemploNotas)
    val notas: StateFlow<List<Nota>> = _notas.asStateFlow()

    private var nextId = (exemploNotas.maxOfOrNull { it.id } ?: 0) + 1

    fun addNota(nome: String, notas: List<Double>) {
        val novo = Nota(id = nextId++, nome = nome, notas = notas)
        _notas.update { lista -> listOf(novo) + lista }
    }

    fun atualizarNota(id: Int, nome: String, notas: List<Double>) {
        _notas.update { lista ->
            lista.map { nota ->
                if (nota.id == id) nota.copy(nome = nome, notas = notas) else nota
            }
        }
    }

    fun buscarNota(id: Int): Nota? {
        return _notas.value.firstOrNull { nota -> nota.id == id }
    }

    fun calcularSituacao(media: Double): String {
        return when {
            media >= 7.0 -> "Aprovado"
            media >= 5.0 -> "Em recuperação"
            else -> "Reprovado"
        }
    }
}
