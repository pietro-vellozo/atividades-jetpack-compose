package br.com.treinamento.navegacaoeventos.ui.telas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import br.com.treinamento.navegacaoeventos.model.Evento
import br.com.treinamento.navegacaoeventos.ui.componentes.LinhaInformacao

@Composable
fun TelaDetalheEvento(
    evento: Evento?,
    onVoltar: () -> Unit,
    onInscrever: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    if (evento == null) {
        TelaEventoNaoEncontrado(onVoltar = onVoltar, modifier = modifier)
        return
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = evento.nome,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        LinhaInformacao(titulo = "Categoria", valor = evento.categoria)
        LinhaInformacao(titulo = "Data", valor = "${evento.data} - ${evento.horario}")
        LinhaInformacao(titulo = "Local", valor = evento.local)
        LinhaInformacao(titulo = "Vagas", valor = evento.vagas.toString())
        LinhaInformacao(titulo = "Descricao", valor = evento.descricao)

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            TextButton(
                onClick = onVoltar,
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "Voltar")
            }

            Button(
                onClick = { onInscrever(evento.id) },
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "Inscrever")
            }
        }
    }
}

@Composable
fun TelaEventoNaoEncontrado(
    onVoltar: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Evento nao encontrado")
        TextButton(onClick = onVoltar) {
            Text(text = "Voltar")
        }
    }
}