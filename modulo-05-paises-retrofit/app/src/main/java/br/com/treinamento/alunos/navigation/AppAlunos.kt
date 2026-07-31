package br.com.treinamento.alunos.navigation

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
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import br.com.treinamento.alunos.ui.telas.TelaDetalheAluno
import br.com.treinamento.alunos.ui.telas.TelaListaAlunos
import br.com.treinamento.alunos.ui.telas.TelaListaPaises
import br.com.treinamento.alunos.ui.telas.TelaNovoAluno
import br.com.treinamento.alunos.viewmodel.AlunosViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppAlunos(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val context = LocalContext.current.applicationContext
    val viewModel: AlunosViewModel = viewModel(factory = AlunosViewModel.factory(context))
    val alunos = viewModel.alunos.collectAsState().value
    val estadoPaises = viewModel.estadoPaises.collectAsState().value

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .safeDrawingPadding(),
        topBar = { TopAppBar(title = { Text(text = "Gerenciar Alunos") }) }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = TelaAlunos.Lista.rota,
            modifier = Modifier.padding(padding)
        ) {
            composable(TelaAlunos.Lista.rota) {
                TelaListaAlunos(
                    alunos = alunos,
                    calcularSituacao = { media -> viewModel.calcularSituacao(media) },
                    onNovoAluno = { navController.navigate(TelaAlunos.Novo.rota) },
                    onVerPaises = { navController.navigate(TelaAlunos.Paises.rota) },
                    onAlunoClick = { alunoId -> navController.navigate(TelaAlunos.Detalhe.criarRota(alunoId)) },
                    onRemoverAluno = { aluno -> viewModel.removerAluno(aluno) }
                )
            }

            composable(
                route = TelaAlunos.Detalhe.rota,
                arguments = listOf(navArgument("alunoId") { type = NavType.IntType })
            ) { entrada ->
                val alunoId = entrada.arguments?.getInt("alunoId") ?: 0

                TelaDetalheAluno(
                    aluno = alunos.firstOrNull { aluno -> aluno.id == alunoId },
                    estadoPaises = estadoPaises,
                    onTentarNovamente = { viewModel.carregarPaises() },
                    calcularSituacao = { media -> viewModel.calcularSituacao(media) },
                    onVoltar = { navController.popBackStack() },
                    onSalvar = { nome, notasAtualizadas, pais ->
                        viewModel.atualizarAluno(alunoId, nome, notasAtualizadas, pais)
                    }
                )
            }

            composable(TelaAlunos.Novo.rota) {
                TelaNovoAluno(
                    estadoPaises = estadoPaises,
                    onTentarNovamente = { viewModel.carregarPaises() },
                    onCancelar = { navController.popBackStack() },
                    onSalvar = { nome, notasAluno, pais ->
                        viewModel.addAluno(nome, notasAluno, pais)
                        navController.popBackStack()
                    }
                )
            }

            composable(TelaAlunos.Paises.rota) {
                TelaListaPaises(
                    estadoPaises = estadoPaises,
                    onTentarNovamente = { viewModel.carregarPaises() },
                    onVoltar = { navController.popBackStack() }
                )
            }
        }
    }
}
