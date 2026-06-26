package br.com.treinamento.cadastroform.ui.telas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.treinamento.cadastroform.model.Cadastro
import br.com.treinamento.cadastroform.ui.componentes.CardCadastro
import br.com.treinamento.cadastroform.ui.componentes.FormularioCadastro
import br.com.treinamento.cadastroform.ui.theme.CadastroFormTheme

@Composable
fun TelaCadastro(modifier: Modifier = Modifier) {
    val niveis = listOf("Suporte", "Financeiro", "Administrativo")

    var nome by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var nivel by remember { mutableStateOf(niveis.first()) }
    var cadastros by remember { mutableStateOf(listOf<Cadastro>()) }
    var cadastroSelecionadoId by remember { mutableStateOf<Int?>(null) }
    var proximoId by remember { mutableStateOf(1) }

    fun limparFormulario() {
        nome = ""
        email = ""
        nivel = niveis.first()
        cadastroSelecionadoId = null
    }

    fun cadastrar() {
        if (nome.isBlank() || email.isBlank()) return

        val novoCadastro = Cadastro(
            id = proximoId,
            nome = nome,
            email = email,
            nivel = nivel
        )

        cadastros = cadastros + novoCadastro
        proximoId++
        limparFormulario()
    }

    fun selecionar(cadastro: Cadastro) {
        nome = cadastro.nome
        email = cadastro.email
        nivel = cadastro.nivel
        cadastroSelecionadoId = cadastro.id
    }

    fun atualizar() {
        val idSelecionado = cadastroSelecionadoId ?: return

        cadastros = cadastros.map { cadastro ->
            if (cadastro.id == idSelecionado) {
                Cadastro(
                    id = cadastro.id,
                    nome = nome,
                    email = email,
                    nivel = nivel
                )
            } else {
                cadastro
            }
        }

        limparFormulario()
    }

    fun remover() {
        val idSelecionado = cadastroSelecionadoId ?: return

        cadastros = cadastros.filter { cadastro -> cadastro.id != idSelecionado }
        limparFormulario()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Cadastro de Pessoas",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        FormularioCadastro(
            nome = nome,
            email = email,
            nivel = nivel,
            niveis = niveis,
            cadastroSelecionado = cadastroSelecionadoId != null,
            onNomeChange = { nome = it },
            onEmailChange = { email = it },
            onNivelChange = { nivel = it },
            onCadastrar = { cadastrar() },
            onAtualizar = { atualizar() },
            onRemover = { remover() },
            onLimpar = { limparFormulario() }
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Cadastros",
            style = MaterialTheme.typography.titleMedium
        )

        if (cadastros.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Nenhum cadastro ainda")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(cadastros) { cadastro ->
                    CardCadastro(
                        cadastro = cadastro,
                        selecionado = cadastro.id == cadastroSelecionadoId,
                        onSelecionar = { selecionar(cadastro) }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TelaCadastroPreview() {
    CadastroFormTheme {
        TelaCadastro()
    }
}