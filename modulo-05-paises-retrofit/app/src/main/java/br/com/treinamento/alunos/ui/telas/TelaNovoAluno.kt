package br.com.treinamento.alunos.ui.telas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import br.com.treinamento.alunos.data.remote.Pais
import br.com.treinamento.alunos.ui.componentes.SeletorPais
import br.com.treinamento.alunos.viewmodel.EstadoPaises

@Composable
fun TelaNovoAluno(
    estadoPaises: EstadoPaises,
    onTentarNovamente: () -> Unit,
    onCancelar: () -> Unit,
    onSalvar: (String, List<Double>, Pais?) -> Unit,
    modifier: Modifier = Modifier
) {
    val nomeState = remember { mutableStateOf("") }
    val notasState = remember { mutableStateListOf("", "") }
    val paisState = remember { mutableStateOf<Pais?>(null) }
    val erroState = remember { mutableStateOf<String?>(null) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(text = "Novo Aluno", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)

        OutlinedTextField(
            value = nomeState.value,
            onValueChange = {
                nomeState.value = it
                erroState.value = null
            },
            label = { Text("Nome") },
            isError = erroState.value != null && nomeState.value.isBlank(),
            modifier = Modifier.fillMaxWidth()
        )

        SeletorPais(
            estadoPaises = estadoPaises,
            paisSelecionado = paisState.value,
            onPaisSelecionado = { pais -> paisState.value = pais },
            onTentarNovamente = onTentarNovamente
        )

        notasState.forEachIndexed { indice, valor ->
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = valor,
                    onValueChange = {
                        notasState[indice] = it
                        erroState.value = null
                    },
                    label = { Text("Nota ${indice + 1}") },
                    isError = erroState.value != null && !valor.isNotaValida(),
                    modifier = Modifier.weight(1f)
                )

                if (notasState.size > 2) {
                    TextButton(onClick = { notasState.removeAt(indice) }) {
                        Text(text = "Remover")
                    }
                }
            }
        }

        Button(onClick = { notasState.add("") }, modifier = Modifier.fillMaxWidth()) {
            Text(text = "+ Adicionar nota")
        }

        erroState.value?.let { erro ->
            Text(text = erro, color = MaterialTheme.colorScheme.error)
        }

        RowButtons(onCancelar = onCancelar, onSalvar = {
            val nome = nomeState.value.trim()
            val notas = notasState.mapNotNull { valor -> valor.replace(',', '.').toDoubleOrNull() }

            if (nome.isBlank()) {
                erroState.value = "Informe o nome do aluno."
                return@RowButtons
            }

            if (notas.size != notasState.size || notas.any { nota -> nota !in 0.0..10.0 }) {
                erroState.value = "Informe apenas notas entre 0 e 10."
                return@RowButtons
            }

            onSalvar(nome, notas, paisState.value)
        })
    }
}

private fun String.isNotaValida(): Boolean {
    return replace(',', '.').toDoubleOrNull()?.let { nota -> nota in 0.0..10.0 } ?: false
}

@Composable
private fun RowButtons(onCancelar: () -> Unit, onSalvar: () -> Unit) {
    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        TextButton(onClick = onCancelar, modifier = Modifier.fillMaxWidth()) { Text(text = "Cancelar") }
        Button(onClick = onSalvar, modifier = Modifier.fillMaxWidth()) { Text(text = "Salvar") }
    }
}
