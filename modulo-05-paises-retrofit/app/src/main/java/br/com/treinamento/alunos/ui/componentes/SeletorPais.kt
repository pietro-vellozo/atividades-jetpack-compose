package br.com.treinamento.alunos.ui.componentes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.treinamento.alunos.data.remote.Pais
import br.com.treinamento.alunos.viewmodel.EstadoPaises

/**
 * Campo de selecao de nacionalidade: mostra o pais escolhido e abre um
 * dialogo com busca para escolher entre os paises retornados pela API.
 */
@Composable
fun SeletorPais(
    estadoPaises: EstadoPaises,
    paisSelecionado: Pais?,
    onPaisSelecionado: (Pais?) -> Unit,
    onTentarNovamente: () -> Unit,
    modifier: Modifier = Modifier
) {
    val dialogoAberto = remember { mutableStateOf(false) }

    Column(modifier = modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(4.dp)) {
        OutlinedTextField(
            value = paisSelecionado?.let { pais -> "${pais.bandeira.orEmpty()} ${pais.nome}".trim() } ?: "",
            onValueChange = {},
            readOnly = true,
            enabled = false,
            label = { Text("Nacionalidade (opcional)") },
            placeholder = { Text("Toque para escolher o país") },
            colors = androidx.compose.material3.OutlinedTextFieldDefaults.colors(
                disabledTextColor = MaterialTheme.colorScheme.onSurface,
                disabledBorderColor = MaterialTheme.colorScheme.outline,
                disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                disabledPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant
            ),
            modifier = Modifier
                .fillMaxWidth()
                .clickable { dialogoAberto.value = true }
        )

        if (paisSelecionado != null) {
            TextButton(onClick = { onPaisSelecionado(null) }) {
                Text(text = "Limpar nacionalidade")
            }
        }
    }

    if (dialogoAberto.value) {
        DialogoSelecaoPais(
            estadoPaises = estadoPaises,
            onPaisSelecionado = { pais ->
                onPaisSelecionado(pais)
                dialogoAberto.value = false
            },
            onTentarNovamente = onTentarNovamente,
            onFechar = { dialogoAberto.value = false }
        )
    }
}

@Composable
private fun DialogoSelecaoPais(
    estadoPaises: EstadoPaises,
    onPaisSelecionado: (Pais) -> Unit,
    onTentarNovamente: () -> Unit,
    onFechar: () -> Unit
) {
    val buscaState = remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onFechar,
        title = { Text(text = "Escolha o país") },
        text = {
            when (estadoPaises) {
                is EstadoPaises.Carregando -> {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is EstadoPaises.Erro -> {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(text = estadoPaises.mensagem, color = MaterialTheme.colorScheme.error)
                        TextButton(onClick = onTentarNovamente) {
                            Text(text = "Tentar novamente")
                        }
                    }
                }

                is EstadoPaises.Sucesso -> {
                    val paisesFiltrados = estadoPaises.paises.filter { pais ->
                        pais.nome.contains(buscaState.value.trim(), ignoreCase = true)
                    }

                    Column(
                        modifier = Modifier.fillMaxHeight(0.7f),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = buscaState.value,
                            onValueChange = { buscaState.value = it },
                            label = { Text("Buscar país") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        if (paisesFiltrados.isEmpty()) {
                            Text(text = "Nenhum país encontrado.")
                        }

                        LazyColumn(modifier = Modifier.fillMaxWidth()) {
                            items(paisesFiltrados, key = { pais -> pais.nome }) { pais ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable { onPaisSelecionado(pais) }
                                        .padding(vertical = 12.dp, horizontal = 4.dp),
                                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(text = pais.bandeira.orEmpty(), style = MaterialTheme.typography.titleLarge)
                                    Text(text = pais.nome, style = MaterialTheme.typography.bodyLarge)
                                }
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onFechar) {
                Text(text = "Fechar")
            }
        }
    )
}
