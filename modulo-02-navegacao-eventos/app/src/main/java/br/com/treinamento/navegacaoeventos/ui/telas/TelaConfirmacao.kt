package br.com.treinamento.navegacaoeventos.ui.telas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun TelaConfirmacao(
    eventoNome: String,
    participante: String,
    onInicio: () -> Unit,
    onEventos: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Inscricao confirmada",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "$participante, sua inscricao no evento $eventoNome foi registrada.",
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = onInicio,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Voltar ao inicio")
        }

        TextButton(onClick = onEventos) {
            Text(text = "Ver outros eventos")
        }
    }
}