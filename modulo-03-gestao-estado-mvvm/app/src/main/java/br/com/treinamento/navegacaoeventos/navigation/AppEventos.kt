package br.com.treinamento.navegacaoeventos.navigation

import android.net.Uri
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import br.com.treinamento.navegacaoeventos.model.listaEventos
import br.com.treinamento.navegacaoeventos.ui.telas.TelaConfirmacao
import br.com.treinamento.navegacaoeventos.ui.telas.TelaDetalheEvento
import br.com.treinamento.navegacaoeventos.ui.telas.TelaInicio
import br.com.treinamento.navegacaoeventos.ui.telas.TelaInscricao
import br.com.treinamento.navegacaoeventos.ui.telas.TelaListaEventos

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppEventos(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .safeDrawingPadding(),
        topBar = {
            TopAppBar(
                title = { Text(text = "Eventos") }
            )
        }
    ) { paddingInterno ->
        NavHost(
            navController = navController,
            startDestination = Tela.Inicio.rota,
            modifier = Modifier.padding(paddingInterno)
        ) {
            composable(Tela.Inicio.rota) {
                TelaInicio(
                    onVerEventos = { navController.navigate(Tela.Eventos.rota) }
                )
            }

            composable(Tela.Eventos.rota) {
                TelaListaEventos(
                    eventos = listaEventos,
                    onEventoClick = { eventoId ->
                        navController.navigate(Tela.Detalhe.criarRota(eventoId))
                    }
                )
            }

            composable(
                route = Tela.Detalhe.rota,
                arguments = listOf(navArgument("eventoId") { type = NavType.IntType })
            ) { entrada ->
                val eventoId = entrada.arguments?.getInt("eventoId") ?: 0
                val evento = listaEventos.firstOrNull { item -> item.id == eventoId }

                TelaDetalheEvento(
                    evento = evento,
                    onVoltar = { navController.popBackStack() },
                    onInscrever = { id ->
                        navController.navigate(Tela.Inscricao.criarRota(id))
                    }
                )
            }

            composable(
                route = Tela.Inscricao.rota,
                arguments = listOf(navArgument("eventoId") { type = NavType.IntType })
            ) { entrada ->
                val eventoId = entrada.arguments?.getInt("eventoId") ?: 0
                val evento = listaEventos.firstOrNull { item -> item.id == eventoId }

                TelaInscricao(
                    evento = evento,
                    onVoltar = { navController.popBackStack() },
                    onConfirmar = { eventoNome, participante ->
                        navController.navigate(Tela.Confirmacao.criarRota(eventoNome, participante))
                    }
                )
            }

            composable(
                route = Tela.Confirmacao.rota,
                arguments = listOf(
                    navArgument("eventoNome") { type = NavType.StringType },
                    navArgument("participante") { type = NavType.StringType }
                )
            ) { entrada ->
                val eventoNome = Uri.decode(entrada.arguments?.getString("eventoNome") ?: "")
                val participante = Uri.decode(entrada.arguments?.getString("participante") ?: "")

                TelaConfirmacao(
                    eventoNome = eventoNome,
                    participante = participante,
                    onInicio = {
                        navController.navigate(Tela.Inicio.rota) {
                            popUpTo(Tela.Inicio.rota) { inclusive = true }
                        }
                    },
                    onEventos = {
                        navController.navigate(Tela.Eventos.rota) {
                            popUpTo(Tela.Eventos.rota) { inclusive = true }
                        }
                    }
                )
            }
        }
    }
}