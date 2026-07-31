package br.com.treinamento.alunos.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "alunos")
data class Aluno(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nome: String,
    val notas: List<Double>,
    val nacionalidade: String? = null,
    val bandeira: String? = null
) {
    val media: Double
        get() = if (notas.isEmpty()) 0.0 else notas.average()
}
