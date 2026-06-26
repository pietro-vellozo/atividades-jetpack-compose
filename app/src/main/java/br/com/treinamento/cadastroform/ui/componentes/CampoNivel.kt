package br.com.treinamento.cadastroform.ui.componentes

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CampoNivel(
    nivelSelecionado: String,
    niveis: List<String>,
    onNivelSelecionado: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var menuAberto by remember { mutableStateOf(false) }

    Box(modifier = modifier.fillMaxWidth()) {
        ExposedDropdownMenuBox(
            expanded = menuAberto,
            onExpandedChange = { menuAberto = !menuAberto },
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = nivelSelecionado,
                onValueChange = {},
                readOnly = true,
                label = { Text(text = "Nivel") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = menuAberto)
                },
                modifier = Modifier
                    .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                    .fillMaxWidth()
            )

            ExposedDropdownMenu(
                expanded = menuAberto,
                onDismissRequest = { menuAberto = false }
            ) {
                niveis.forEach { nivel ->
                    DropdownMenuItem(
                        text = { Text(text = nivel) },
                        onClick = {
                            onNivelSelecionado(nivel)
                            menuAberto = false
                        }
                    )
                }
            }
        }
    }
}