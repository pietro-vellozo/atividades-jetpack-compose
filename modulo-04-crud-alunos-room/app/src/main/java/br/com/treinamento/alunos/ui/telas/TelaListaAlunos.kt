package br.com.treinamento.alunos.ui.telas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import br.com.treinamento.alunos.data.Aluno

@Composable
fun TelaListaAlunos(
    alunos: List<Aluno>,
    calcularSituacao: (Double) -> String,
    onNovoAluno: () -> Unit,
    onAlunoClick: (Int) -> Unit,
    onRemoverAluno: (Aluno) -> Unit,
    modifier: Modifier = Modifier
) {
    val alunoParaRemover = remember { mutableStateOf<Aluno?>(null) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(text = "Alunos", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)

        Button(onClick = onNovoAluno) {
            Text(text = "Novo aluno")
        }

        if (alunos.isEmpty()) {
            Text(text = "Nenhum aluno cadastrado.")
        }

        LazyColumn(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(alunos, key = { aluno -> aluno.id }) { aluno ->
                Card(
                    onClick = { onAlunoClick(aluno.id) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            Text(text = aluno.nome, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                            Text(text = "Media: %.1f".format(aluno.media))
                            Text(text = calcularSituacao(aluno.media))
                        }

                        TextButton(onClick = { alunoParaRemover.value = aluno }) {
                            Text(text = "Remover", color = MaterialTheme.colorScheme.error)
                        }
                    }
                }
            }
        }
    }

    alunoParaRemover.value?.let { aluno ->
        AlertDialog(
            onDismissRequest = { alunoParaRemover.value = null },
            title = { Text(text = "Remover aluno") },
            text = { Text(text = "Deseja remover ${aluno.nome}?") },
            confirmButton = {
                TextButton(onClick = {
                    onRemoverAluno(aluno)
                    alunoParaRemover.value = null
                }) {
                    Text(text = "Remover", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { alunoParaRemover.value = null }) {
                    Text(text = "Cancelar")
                }
            }
        )
    }
}
