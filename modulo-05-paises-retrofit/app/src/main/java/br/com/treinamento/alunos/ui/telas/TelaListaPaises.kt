package br.com.treinamento.alunos.ui.telas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import br.com.treinamento.alunos.viewmodel.EstadoPaises

/**
 * Listagem de paises consumidos da API countriesnow.space via Retrofit,
 * exibindo a bandeira (emoji unicode) e o nome de cada pais.
 */
@Composable
fun TelaListaPaises(
    estadoPaises: EstadoPaises,
    onTentarNovamente: () -> Unit,
    onVoltar: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(text = "Países", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)

        when (estadoPaises) {
            is EstadoPaises.Carregando -> {
                Box(modifier = Modifier.fillMaxWidth().padding(32.dp), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            is EstadoPaises.Erro -> {
                Text(text = estadoPaises.mensagem, color = MaterialTheme.colorScheme.error)
                Button(onClick = onTentarNovamente, modifier = Modifier.fillMaxWidth()) {
                    Text(text = "Tentar novamente")
                }
            }

            is EstadoPaises.Sucesso -> {
                val buscaState = remember { mutableStateOf("") }
                val paisesFiltrados = estadoPaises.paises.filter { pais ->
                    pais.nome.contains(buscaState.value.trim(), ignoreCase = true)
                }

                OutlinedTextField(
                    value = buscaState.value,
                    onValueChange = { buscaState.value = it },
                    label = { Text("Buscar país") },
                    modifier = Modifier.fillMaxWidth()
                )

                Text(text = "${paisesFiltrados.size} países")

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(paisesFiltrados, key = { pais -> pais.nome }) { pais ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = pais.bandeira.orEmpty(), style = MaterialTheme.typography.headlineSmall)
                                Text(
                                    text = pais.nome,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }
        }

        TextButton(onClick = onVoltar, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Voltar")
        }
    }
}
