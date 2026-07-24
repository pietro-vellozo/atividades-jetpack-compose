package br.com.treinamento.navegacaoeventos.notas.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import br.com.treinamento.navegacaoeventos.notas.viewmodel.NotasViewModel
import br.com.treinamento.navegacaoeventos.ui.telas.TelaDetalheNota
import br.com.treinamento.navegacaoeventos.ui.telas.TelaListaNotas
import br.com.treinamento.navegacaoeventos.ui.telas.TelaNovaNota

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNotas(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val viewModel: NotasViewModel = viewModel()
    val notas = viewModel.notas.collectAsState().value

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .safeDrawingPadding(),
        topBar = { TopAppBar(title = { Text(text = "Gerenciar Notas") }) }
    ) { padding ->
        NavHost(navController = navController, startDestination = TelaNotas.Lista.rota, modifier = Modifier.padding(padding)) {
            composable(TelaNotas.Lista.rota) {
                TelaListaNotas(
                    notas = notas,
                    calcularSituacao = { media -> viewModel.calcularSituacao(media) },
                    onNovaNota = { navController.navigate(TelaNotas.Nova.rota) },
                    onNotaClick = { notaId -> navController.navigate(TelaNotas.Detalhe.criarRota(notaId)) }
                )
            }

            composable(
                route = TelaNotas.Detalhe.rota,
                arguments = listOf(navArgument("notaId") { type = NavType.IntType })
            ) { entrada ->
                val notaId = entrada.arguments?.getInt("notaId") ?: 0

                TelaDetalheNota(
                    nota = notas.firstOrNull { nota -> nota.id == notaId },
                    calcularSituacao = { media -> viewModel.calcularSituacao(media) },
                    onVoltar = { navController.popBackStack() },
                    onSalvar = { nome, notasAtualizadas ->
                        viewModel.atualizarNota(notaId, nome, notasAtualizadas)
                    }
                )
            }

            composable(TelaNotas.Nova.rota) {
                TelaNovaNota(
                    onCancelar = { navController.popBackStack() },
                    onSalvar = { nome, notasAluno ->
                        viewModel.addNota(nome, notasAluno)
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}
