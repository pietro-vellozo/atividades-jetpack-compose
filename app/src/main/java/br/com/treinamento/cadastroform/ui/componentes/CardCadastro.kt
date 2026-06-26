package br.com.treinamento.cadastroform.ui.componentes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import br.com.treinamento.cadastroform.model.Cadastro

@Composable
fun CardCadastro(
    cadastro: Cadastro,
    selecionado: Boolean,
    onSelecionar: () -> Unit,
    modifier: Modifier = Modifier
) {
    val corDoCard = corDoNivel(cadastro.nivel)

    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = corDoCard)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = iconeDoNivel(cadastro.nivel),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(26.dp)
            )

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = cadastro.nome,
                    style = MaterialTheme.typography.titleMedium
                )

                Text(text = "Email: ${cadastro.email}")
                Text(text = "Nivel: ${cadastro.nivel}")

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(onClick = onSelecionar) {
                        Text(text = if (selecionado) "Selecionado" else "Selecionar")
                    }
                }
            }
        }
    }
}

fun iconeDoNivel(nivel: String): ImageVector {
    return when (nivel) {
        "Suporte" -> Icons.Filled.Build
        "Financeiro" -> Icons.Filled.Face
        "Administrativo" -> Icons.Filled.Face
        else -> Icons.Filled.Info
    }
}

fun corDoNivel(nivel: String): Color {
    return when (nivel) {
        "Suporte" -> Color(0xFFC8E6C9)
        "Financeiro" -> Color(0xFFFFCDD2)
        "Administrativo" -> Color(0xFFFFF9C4)
        else -> Color(0xFFE0E0E0)
    }
}