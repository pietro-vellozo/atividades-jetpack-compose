package br.com.treinamento.alunos.ui.telas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import br.com.treinamento.alunos.data.Aluno
import br.com.treinamento.alunos.data.remote.Pais
import br.com.treinamento.alunos.ui.componentes.SeletorPais
import br.com.treinamento.alunos.viewmodel.EstadoPaises

@Composable
fun TelaDetalheAluno(
    aluno: Aluno?,
    estadoPaises: EstadoPaises,
    onTentarNovamente: () -> Unit,
    calcularSituacao: (Double) -> String,
    onVoltar: () -> Unit,
    onSalvar: (String, List<Double>, Pais?) -> Unit,
    modifier: Modifier = Modifier
) {
    val editandoState = remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        if (aluno == null) {
            Text(text = "Aluno nao encontrado", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            Button(onClick = onVoltar, modifier = Modifier.fillMaxWidth()) {
                Text(text = "Voltar")
            }
        } else {
            if (editandoState.value) {
                FormularioEdicaoAluno(
                    aluno = aluno,
                    estadoPaises = estadoPaises,
                    onTentarNovamente = onTentarNovamente,
                    onCancelar = { editandoState.value = false },
                    onSalvar = { nome, notas, pais ->
                        onSalvar(nome, notas, pais)
                        editandoState.value = false
                    }
                )
            } else {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(text = aluno.nome, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                    IconButton(onClick = { editandoState.value = true }) {
                        Text(text = "✎")
                    }
                }

                if (aluno.nacionalidade != null) {
                    Text(text = "Nacionalidade: ${aluno.bandeira.orEmpty()} ${aluno.nacionalidade}".trim())
                }

                aluno.notas.forEachIndexed { indice, valor ->
                    Text(text = "Nota ${indice + 1}: %.1f".format(valor))
                }

                Text(text = "Media: %.1f".format(aluno.media))
                Text(text = "Situacao: ${calcularSituacao(aluno.media)}")

                Button(onClick = onVoltar, modifier = Modifier.fillMaxWidth()) {
                    Text(text = "Voltar")
                }
            }
        }
    }
}

@Composable
private fun FormularioEdicaoAluno(
    aluno: Aluno,
    estadoPaises: EstadoPaises,
    onTentarNovamente: () -> Unit,
    onCancelar: () -> Unit,
    onSalvar: (String, List<Double>, Pais?) -> Unit
) {
    val nomeState = remember { mutableStateOf(aluno.nome) }
    val notasState = remember { mutableStateListOf(*aluno.notas.map { valor -> valor.toString() }.toTypedArray()) }
    val paisState = remember {
        mutableStateOf(
            aluno.nacionalidade?.let { nome ->
                Pais(nome = nome, bandeira = aluno.bandeira, iso2 = null, iso3 = null)
            }
        )
    }
    val erroState = remember { mutableStateOf<String?>(null) }

    LaunchedEffect(aluno.id) {
        nomeState.value = aluno.nome
        notasState.clear()
        notasState.addAll(aluno.notas.map { valor -> valor.toString() })
        paisState.value = aluno.nacionalidade?.let { nome ->
            Pais(nome = nome, bandeira = aluno.bandeira, iso2 = null, iso3 = null)
        }
    }

    Text(text = "Editar aluno", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)

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
                isError = erroState.value != null && !valor.isNotaValidaDetalhe(),
                modifier = Modifier.weight(1f)
            )

            if (notasState.size > 1) {
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

    TextButton(onClick = onCancelar, modifier = Modifier.fillMaxWidth()) {
        Text(text = "Cancelar")
    }

    Button(
        onClick = {
            val nome = nomeState.value.trim()
            val notas = notasState.mapNotNull { valor -> valor.replace(',', '.').toDoubleOrNull() }

            if (nome.isBlank()) {
                erroState.value = "Informe o nome do aluno."
                return@Button
            }

            if (notas.size != notasState.size || notas.any { valor -> valor !in 0.0..10.0 }) {
                erroState.value = "Informe apenas notas entre 0 e 10."
                return@Button
            }

            onSalvar(nome, notas, paisState.value)
        },
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = "Salvar")
    }
}

private fun String.isNotaValidaDetalhe(): Boolean {
    return replace(',', '.').toDoubleOrNull()?.let { nota -> nota in 0.0..10.0 } ?: false
}
