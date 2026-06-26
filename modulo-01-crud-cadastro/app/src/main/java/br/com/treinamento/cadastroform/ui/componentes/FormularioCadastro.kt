package br.com.treinamento.cadastroform.ui.componentes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FormularioCadastro(
    nome: String,
    email: String,
    nivel: String,
    niveis: List<String>,
    cadastroSelecionado: Boolean,
    onNomeChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onNivelChange: (String) -> Unit,
    onCadastrar: () -> Unit,
    onAtualizar: () -> Unit,
    onRemover: () -> Unit,
    onLimpar: () -> Unit,
    modifier: Modifier = Modifier
) {
    val formularioPreenchido = nome.isNotBlank() && email.isNotBlank()

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (cadastroSelecionado) "Editar cadastro" else "Novo cadastro",
                style = MaterialTheme.typography.titleMedium
            )

            TextButton(onClick = onLimpar) {
                Text(text = if (cadastroSelecionado) "Voltar" else "Limpar")
            }
        }

        OutlinedTextField(
            value = nome,
            onValueChange = onNomeChange,
            label = { Text(text = "Nome") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = email,
            onValueChange = onEmailChange,
            label = { Text(text = "Email") },
            modifier = Modifier.fillMaxWidth()
        )

        CampoNivel(
            nivelSelecionado = nivel,
            niveis = niveis,
            onNivelSelecionado = onNivelChange
        )

        Spacer(modifier = Modifier.height(4.dp))

        if (cadastroSelecionado) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = onAtualizar,
                    enabled = formularioPreenchido,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "Atualizar")
                }

                TextButton(
                    onClick = onRemover,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "Remover")
                }
            }
        } else {
            Button(
                onClick = onCadastrar,
                enabled = formularioPreenchido,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Cadastrar")
            }
        }
    }
}