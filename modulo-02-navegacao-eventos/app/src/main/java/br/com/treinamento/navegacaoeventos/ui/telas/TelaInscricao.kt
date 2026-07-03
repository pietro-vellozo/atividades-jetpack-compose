package br.com.treinamento.navegacaoeventos.ui.telas

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import br.com.treinamento.navegacaoeventos.model.Evento

@Composable
fun TelaInscricao(
    evento: Evento?,
    onVoltar: () -> Unit,
    onConfirmar: (String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    if (evento == null) {
        TelaEventoNaoEncontrado(onVoltar = onVoltar, modifier = modifier)
        return
    }

    var nome by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    val formularioPreenchido = nome.isNotBlank() && email.isNotBlank()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Inscricao",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Text(text = "Evento: ${evento.nome}")

        OutlinedTextField(
            value = nome,
            onValueChange = { nome = it },
            label = { Text(text = "Nome") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(text = "Email") },
            modifier = Modifier.fillMaxWidth()
        )

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
                onClick = { onConfirmar(evento.nome, nome) },
                enabled = formularioPreenchido,
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "Confirmar")
            }
        }
    }
}