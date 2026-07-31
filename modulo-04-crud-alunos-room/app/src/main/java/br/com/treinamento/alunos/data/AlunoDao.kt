package br.com.treinamento.alunos.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface AlunoDao {

    @Query("SELECT * FROM alunos ORDER BY nome COLLATE NOCASE")
    fun observarTodos(): Flow<List<Aluno>>

    @Insert
    suspend fun inserir(aluno: Aluno)

    @Update
    suspend fun atualizar(aluno: Aluno)

    @Delete
    suspend fun remover(aluno: Aluno)
}
